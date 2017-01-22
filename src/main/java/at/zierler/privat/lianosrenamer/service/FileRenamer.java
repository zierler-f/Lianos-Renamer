package at.zierler.privat.lianosrenamer.service;import at.zierler.privat.lianosrenamer.domain.Episode;import at.zierler.privat.lianosrenamer.domain.LookupEpisode;import at.zierler.privat.lianosrenamer.domain.Show;import at.zierler.privat.lianosrenamer.exceptions.LianosRenamerException;import at.zierler.privat.lianosrenamer.helper.FileExtensionGetter;import at.zierler.privat.lianosrenamer.helper.UrlAssembler;import java.io.File;import java.nio.file.Path;import java.util.HashMap;import java.util.InputMismatchException;import java.util.List;import java.util.Scanner;import java.util.regex.Matcher;import java.util.regex.Pattern;public class FileRenamer {    private final JsonHandler jsonHandler = new JsonHandler();    private final FileHandler fileHandler = new FileHandler();    private HashMap<String, Integer> givenAnswers = new HashMap<>();    public void renameFiles(List<File> files) throws LianosRenamerException {        List<Path> videoFiles = fileHandler.getListOfVideoFilesByListOfFiles(files);        videoFiles.forEach(this::renameFile);    }    private void renameFile(Path path) {        System.out.println("Now processing " + path.toAbsolutePath().toString() + ".");        File file = new File(path.toAbsolutePath().toString());        LookupEpisode lookupEpisode = getLookupEpisodeByFile(file);        if (lookupEpisode == null) {            return;        }        List<Show> possibleShows;        try {            possibleShows = jsonHandler.getAllShowsByURL(UrlAssembler.assembleShowQueryUrlByShowName(lookupEpisode.getShow().getName()));        } catch (LianosRenamerException e) {            System.out.println("Couldn't get possible shows.");            return;        }        String showFolderKey = getKeyByParentAndPossibleShows(file.getParentFile(), possibleShows);        int selectedShowNumber;        if (givenAnswers.containsKey(showFolderKey)) {            selectedShowNumber = givenAnswers.get(showFolderKey);        } else if (possibleShows.size() == 1) {            selectedShowNumber = 0;        } else if (possibleShows.size() > 1) {            selectedShowNumber = letUserChooseSeries(possibleShows);            givenAnswers.put(showFolderKey, selectedShowNumber);        } else {            System.out.println("Couldn't find a show with " + lookupEpisode.getShow().getName() + " in it.");            return;        }        Show selectedShow = possibleShows.get(selectedShowNumber);        Episode episode;        try {            episode = jsonHandler.getEpisodeByURL(UrlAssembler.assembleEpisodeQueryUrlByLookupEpisode(lookupEpisode, selectedShow.getId()));        } catch (LianosRenamerException e) {            System.out.println("Couldn't find the episode.");            return;        }        File newFile = generateNewFileNameByShowNameAndEpisodeAndOriginalFile(selectedShow.getName(), episode, file);        if (file.renameTo(newFile))            System.out.println("Successfully renamed " + file.getAbsolutePath() + " to " + newFile.getAbsolutePath() + "!");        else            System.out.println("Couldn't rename " + file.getAbsolutePath() + " to " + newFile.getAbsolutePath());    }    private String getKeyByParentAndPossibleShows(File parent, List<Show> possibleShows) {        StringBuilder sb = new StringBuilder();        sb.append(parent.getAbsolutePath().hashCode());        StringBuilder showsSb = new StringBuilder();        possibleShows.forEach(show -> showsSb.append(show.getName().hashCode()));        sb.append(showsSb.toString().hashCode());        return sb.toString();    }    private File generateNewFileNameByShowNameAndEpisodeAndOriginalFile(String showName, Episode episode, File originalFile) {        String seasonNumberFormatted = String.format("%02d", episode.getSeason());        String episodeNumberFormatted = String.format("%02d", episode.getNumber());        String newFileName = showName + " - S" + seasonNumberFormatted + "E" + episodeNumberFormatted + " - " + episode.getName() +                "." + FileExtensionGetter.getFileExtension(originalFile.toPath());        return new File(originalFile.getParentFile(), newFileName.replaceAll("/", "_"));    }    private LookupEpisode getLookupEpisodeByFile(File file) {        String absolutePath = file.getAbsolutePath();        if (!new File(absolutePath).exists()) {            System.out.println(absolutePath + " is not a file.");            return null;        }        Pattern pattern = Pattern.compile("(^.*)(?:(?i)s([0-9]{1,2})(?i)e([0-9]{1,2})|(?:([0-9]{1,2})(?i)x([0-9]{1,2})))");        Matcher matcher = pattern.matcher(file.getName());        if (matcher.find()) {            String showName = matcher.group(1).replaceAll("[^A-Za-z ]", " ").replaceAll("(?i)and ", "")                    .replaceAll("(?i)the ", "").trim().replaceAll(" ", "-").toLowerCase();            int seasonNumber = Integer.parseInt(matcher.group(2) == null ? matcher.group(4) : matcher.group(2));            int episodeNumber = Integer.parseInt(matcher.group(3) == null ? matcher.group(5) : matcher.group(3));            Show show = new Show();            show.setName(showName);            LookupEpisode lookupEpisode = new LookupEpisode();            lookupEpisode.setShow(show);            lookupEpisode.setSeasonNumber(seasonNumber);            lookupEpisode.setEpisodeNumber(episodeNumber);            return lookupEpisode;        } else {            System.out.println("Couldn't find relevant information in filename of file " + file.getName() + ". Please make sure the name matches standard patterns.");            return null;        }    }    private int letUserChooseSeries(List<Show> shows) {        System.out.println("More than one show with matching names was found.\n" +                "Please choose a show by typing the number next to it and hitting enter.");        final int[] index = {1};        shows.forEach(s -> {            int premiereYear = s.getPremiereYear();            String premiereYearString = premiereYear != -1 ? " (" + premiereYear + ")" : "";            System.out.println("[" + index[0] + "] " + s.getName() + premiereYearString);            index[0]++;        });        Scanner scanner = new Scanner(System.in);        System.out.print("Number: ");        int n;        try {            n = scanner.nextInt();            if (n > shows.size() || n < 1) {                throw new InputMismatchException("Please use a number between 1 and " + shows.size() + ".");            }        } catch (InputMismatchException e) {            System.out.println("Invalid input.");            return letUserChooseSeries(shows);        }        return n - 1;    }}
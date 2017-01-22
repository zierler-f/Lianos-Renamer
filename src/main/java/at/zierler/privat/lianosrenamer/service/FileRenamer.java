package at.zierler.privat.lianosrenamer.service;

import at.zierler.privat.lianosrenamer.LianosRenamerException;
import at.zierler.privat.lianosrenamer.domain.Episode;
import at.zierler.privat.lianosrenamer.domain.LookupEpisode;
import at.zierler.privat.lianosrenamer.domain.Show;
import at.zierler.privat.lianosrenamer.helper.FileExtensionGetter;
import at.zierler.privat.lianosrenamer.helper.UrlAssembler;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileRenamer {

    final Logger logger = Logger.getLogger(FileRenamer.class.getName());
    private final JsonHandler jsonHandler = new JsonHandler();
    private final FileHandler fileHandler = new FileHandler();
    private HashMap<String, Integer> givenAnswers = new HashMap<>();

    public void renameFiles(List<File> files) throws LianosRenamerException {
        List<Path> videoFiles = fileHandler.getListOfVideoFilesByListOfFiles(files);
        videoFiles.forEach(this::renameFile);
    }

    private File renameFile(Path path) {
        File file = new File(path.toString());
        String filename = file.getName();
        String absolutePath = file.getAbsolutePath();
        logger.log(Level.INFO, "Now processing " + absolutePath + ".");
        if (!file.isFile()) {
            logger.log(Level.WARNING, absolutePath + " is not a file.");
        } else {
            LookupEpisode lookupEpisode = getLookupEpisodeByFileName(filename);
            if (lookupEpisode == null) {
                logger.log(Level.WARNING, "Couldn't find relevant information in filename of file " + filename + ". Please make sure the name matches standard patterns.");
            } else {
                String showName = lookupEpisode.getShow().getName();
                try {
                    List<Show> possibleShows = jsonHandler.getAllShowsByURL(UrlAssembler.assembleShowQueryUrlByShowName(showName));
                    String showFolderKey = getKeyByParentAndPossibleShows(file.getParentFile(), possibleShows);
                    int userAnswer;
                    Show selectedShow;
                    if (givenAnswers.containsKey(showFolderKey)) {
                        userAnswer = givenAnswers.get(showFolderKey);
                        selectedShow = possibleShows.get(userAnswer);
                    } else {
                        if (possibleShows.size() == 1) {
                            selectedShow = possibleShows.get(0);
                        } else if (possibleShows.isEmpty()) {
                            logger.log(Level.WARNING, "Couldn't find a show with " + showName + " in it.");
                            return file;
                        } else {
                            userAnswer = letUserChooseSeries(possibleShows);
                            selectedShow = possibleShows.get(userAnswer);
                            givenAnswers.put(showFolderKey, userAnswer);
                        }
                    }
                    Episode episode = jsonHandler.getEpisodeByURL(UrlAssembler.assembleEpisodeQueryUrlByLookupEpisode(lookupEpisode, selectedShow.getId()));
                    File newFile = generateNewFileNameByShowNameAndEpisodeAndOriginalFile(selectedShow.getName(), episode, file);
                    if (file.renameTo(newFile))
                        logger.log(Level.FINEST, "Successfully renamed " + file.getAbsolutePath() + " to " + newFile.getAbsolutePath() + "!");
                } catch (LianosRenamerException e) {
                    logger.log(Level.WARNING, "Couldn't rename " + absolutePath + " due to following error: ", e);
                }
            }
        }
        return file;
    }

    private String getKeyByParentAndPossibleShows(File parent, List<Show> possibleShows) {
        StringBuilder sb = new StringBuilder();
        sb.append(parent.getAbsolutePath().hashCode());
        StringBuilder showsSb = new StringBuilder();
        possibleShows.forEach(show -> showsSb.append(show.getName().hashCode()));
        sb.append(showsSb.toString().hashCode());
        return sb.toString();
    }

    private File generateNewFileNameByShowNameAndEpisodeAndOriginalFile(String showName, Episode episode, File originalFile) {
        String seasonNumberFormatted = String.format("%02d", episode.getSeason());
        String episodeNumberFormatted = String.format("%02d", episode.getNumber());
        String newFileName = showName + " - S" + seasonNumberFormatted + "E" + episodeNumberFormatted + " - " + episode.getName() +
                "." + FileExtensionGetter.getFileExtension(originalFile.toPath());
        return new File(originalFile.getParentFile(), newFileName.replaceAll("/", "_"));
    }

    private LookupEpisode getLookupEpisodeByFileName(String filename) {
        Pattern pattern = Pattern.compile("(^.*)(?:(?i)s([0-9]{1,2})(?i)e([0-9]{1,2})|(?:([0-9]{1,2})(?i)x([0-9]{1,2})))");
        Matcher matcher = pattern.matcher(filename);
        if (matcher.find()) {
            String showName = matcher.group(1).replaceAll("[^A-Za-z ]", " ").replaceAll("(?i)and ", "")
                    .replaceAll("(?i)the ", "").trim().replaceAll(" ", "-").toLowerCase();
            int seasonNumber = Integer.parseInt(matcher.group(2) == null ? matcher.group(4) : matcher.group(2));
            int episodeNumber = Integer.parseInt(matcher.group(3) == null ? matcher.group(5) : matcher.group(3));
            Show show = new Show();
            show.setName(showName);
            LookupEpisode lookupEpisode = new LookupEpisode();
            lookupEpisode.setShow(show);
            lookupEpisode.setSeasonNumber(seasonNumber);
            lookupEpisode.setEpisodeNumber(episodeNumber);
            return lookupEpisode;
        }
        return null;
    }

    private int letUserChooseSeries(List<Show> shows) {
        logger.log(Level.INFO, "More than one show with matching names was found.\n" +
                "Please choose a show by typing the number next to it and hitting enter.");
        final int[] index = {1};
        shows.forEach(s -> {
            int premiereYear = s.getPremiereYear();
            String premiereYearString = premiereYear != -1 ? " (" + premiereYear + ")" : "";
            logger.log(Level.INFO, "[" + index[0] + "] " + s.getName() + premiereYearString);
            index[0]++;
        });
        Scanner scanner = new Scanner(System.in);
        logger.log(Level.ALL, "Number: ");
        int n;
        try {
            n = scanner.nextInt();
            if (n > shows.size() || n < 1) {
                throw new InputMismatchException("Please use a number between 1 and " + shows.size() + ".");
            }
        } catch (InputMismatchException e) {
            logger.log(Level.WARNING, "Invalid input.", e);
            return letUserChooseSeries(shows);
        }
        return n - 1;
    }

}

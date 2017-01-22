package at.zierler.privat.lianosrenamer.service;

import at.zierler.privat.lianosrenamer.LianosRenamerException;
import at.zierler.privat.lianosrenamer.domain.Episode;
import at.zierler.privat.lianosrenamer.domain.LianosFile;
import at.zierler.privat.lianosrenamer.domain.LookupEpisode;
import at.zierler.privat.lianosrenamer.domain.Show;
import at.zierler.privat.lianosrenamer.helper.FileExtensionGetter;

import java.io.File;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileRenamer {

    private final JsonHandler jsonHandler = new JsonHandler();
    private final FileHandler fileHandler = new FileHandler();

    public void renameFiles(List<File> files) throws LianosRenamerException {
        List<Path> videoFiles = fileHandler.getListOfVideoFilesByListOfFiles(files);
        videoFiles.forEach(this::renameFile);
    }

    private LianosFile renameFile(Path path) {
        LianosFile file = new LianosFile(path.toString());
        String filename = file.getName();
        String absolutePath = file.getAbsolutePath();
        System.out.println("Now processing " + absolutePath + ".");
        if (!file.isFile()) {
            System.out.println(absolutePath + " is not a file.");
        } else {
            LookupEpisode lookupEpisode = getLookupEpisodeByFileName(filename);
            if (lookupEpisode == null) {
                System.out.println("Couldn't find relevant information in filename of file " + filename + ". Please make sure the name matches standard patterns.");
            } else {
                String showName = lookupEpisode.getShow().getName();
                try {
                    List<Show> possibleShows = jsonHandler.getAllShowsByURL(UrlAssembler.assembleShowQueryUrlByShowName(showName));
                    Show selectedShow;
                    if (possibleShows.size() == 1) {
                        selectedShow = possibleShows.get(0);
                    } else if (possibleShows.size() < 1) {
                        System.out.println("Couldn't find a show with " + showName + " in it.");
                        return file;
                    } else {
                        selectedShow = possibleShows.get(letUserChooseSeries(possibleShows));
                    }
                    Episode episode = jsonHandler.getEpisodeByUrl(UrlAssembler.assembleEpisodeQueryUrlByLookupEpisode(lookupEpisode, selectedShow.getId()));
                    LianosFile newFile = generateNewFileNameByShowNameAndEpisodeAndOriginalFile(selectedShow.getName(), episode, file);
                    if(file.renameTo(newFile))
                        System.out.println("Successfully renamed " + file.getAbsolutePath() + " to " + newFile.getAbsolutePath() + "!");
                } catch (LianosRenamerException e) {
                    System.out.println("Couldn't rename " + absolutePath + " due to following error: ");
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    private LianosFile generateNewFileNameByShowNameAndEpisodeAndOriginalFile(String showName, Episode episode, LianosFile originalFile) {
        String seasonNumberFormatted = String.format("%02d", episode.getSeason());
        String episodeNumberFormatted = String.format("%02d", episode.getNumber());
        return new LianosFile(originalFile.getParentFile(), showName + " - S" + seasonNumberFormatted + "E" + episodeNumberFormatted + " - " + episode.getName() +
                "." + FileExtensionGetter.getFileExtension(originalFile.toPath()));
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
        System.out.println("More than one show with matching names was found.\n" +
                "Please choose a show by typing the number next to it and hitting enter.");
        final int[] index = {1};
        shows.forEach(s -> {
            System.out.println("[" + index[0] + "] " + s.getName() + " (" + s.getPremiereYear() + ")");
            index[0]++;
        });
        Scanner scanner = new Scanner(System.in);
        System.out.print("Number: ");
        int n;
        try {
            n = scanner.nextInt();
            if (n > shows.size() || n < 1) {
                throw new InputMismatchException();
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please use a number between 1 and " + shows.size() + ".");
            return letUserChooseSeries(shows);
        }
        return n - 1;
    }

}

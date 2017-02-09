package at.zierler.privat.lianosrenamer.service;

import at.zierler.privat.lianosrenamer.exceptions.LianosRenamerException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
 * Handles files, walks directories and finds all video files recursively
 */
public class FileHandler {

    private static final Logger logger = Logger.getLogger(FileHandler.class.getName());
    private List<String> videoExtensions;
    private List<File> inputFiles;

    private FileHandler(List<String> videoExtensions, List<File> inputFiles) {
        this.videoExtensions = videoExtensions;
        this.inputFiles = inputFiles;
    }

    public static FileHandler of(List<File> inputFiles) throws LianosRenamerException {
        Properties properties = new Properties();
        InputStream inputStream = FileHandler.class.getClassLoader().getResourceAsStream("application.properties");
        if (inputStream == null) {
            throw new LianosRenamerException("Couldn't find properties file and read video extension.",
                    new FileNotFoundException("Properties file not found."));
        }
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new LianosRenamerException("Error while reading properties file.", e);
        }
        if (!properties.containsKey("video-extensions")) {
            throw new LianosRenamerException("Key \"video-extensions\" not found in properties file.");
        }
        List<String> videoExtensions = Arrays.asList(properties.getProperty("video-extensions").split(","));
        return new FileHandler(videoExtensions, inputFiles);
    }

    public List<Path> getListOfVideoFiles() throws LianosRenamerException {
        List<Path> videoFiles = new ArrayList<>();
        for (File f : inputFiles) {
            addAllFilesToList(f, videoFiles);
        }
        return videoFiles;
    }

    private List<Path> addAllFilesToList(File inputFile, List<Path> pathList) throws LianosRenamerException {
        try {
            pathList.addAll(Files.walk(Paths.get(inputFile.getPath())).filter(this::isVideoFile).collect(Collectors.toList()));
        } catch (IOException e) {
            throw new LianosRenamerException("There was a problem while walking files.", e);
        }
        return pathList;
    }

    private boolean isVideoFile(Path path) {
        return videoExtensions.contains(FileExtensionSplitter.of(path).getFileExtension());
    }

}

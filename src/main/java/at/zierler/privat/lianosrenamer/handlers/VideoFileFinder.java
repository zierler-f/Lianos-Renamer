package at.zierler.privat.lianosrenamer.handlers;

import at.zierler.privat.lianosrenamer.domain.FileExt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class VideoFileFinder implements Function {

    /**
     * List of all video extensions the program knows
     */

    private static final List<String> knownVideoExtensions = loadKnownVideoExtensions();
    /**
     * List of files/directories to search for video files in
     */

    private List<File> files;

    /**
     * sets instance variable files to provided directories/files to search
     *
     * @param files list of files/directories
     */

    public VideoFileFinder(List<File> files) {
        this.files = new ArrayList<>(files);
    }

    /**
     * loads video extensions from application.properties file and returns default video extensions if file doesn't exist
     *
     * @return list of known video extensions
     */

    private static List<String> loadKnownVideoExtensions() {
        List<String> defaultKnownVideoExtensions = Arrays.asList("mp4", "mkv", "flv", "wmv", "avi");
        Properties properties = new Properties();
        InputStream inputStream = VideoFileFinder.class.getClassLoader().getResourceAsStream("application.properties");
        if (inputStream == null) {
            return defaultKnownVideoExtensions;
        }
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            return defaultKnownVideoExtensions;
        }
        if (!properties.containsKey("video-extensions")) {
            return defaultKnownVideoExtensions;
        }
        return Arrays.asList(properties.getProperty("video-extensions").split(","));
    }

    /**
     * walk all provided files/directories, find all video-files and return them
     *
     * @return all video files in provided files/directories
     */

    @Override
    public List<FileExt> doJob() {
        return files
                .parallelStream()
                .flatMap(file -> {
                    try {
                        return Files
                                .walk(file.toPath())
                                .map(FileExt::new)
                                .filter(this::isVideoFile);
                    } catch (IOException e) {
                        throw new RuntimeException("Error during walking of files.");
                    }
                })
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * checks if a file's extension matches one of the known video file extensions
     *
     * @param fileExt a file
     * @return if the file is a video file
     */

    private boolean isVideoFile(FileExt fileExt) {
        return knownVideoExtensions.contains(fileExt.getExtension());
    }

}

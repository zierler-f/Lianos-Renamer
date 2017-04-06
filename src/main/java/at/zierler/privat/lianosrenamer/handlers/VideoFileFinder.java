package at.zierler.privat.lianosrenamer.handlers;

import at.zierler.privat.lianosrenamer.domain.FileExt;
import at.zierler.privat.lianosrenamer.domain.FilesExt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class VideoFileFinder implements Supplier<Set<FileExt>> {

    /**
     * List of all video extensions the program knows
     */

    private static final List<String> knownVideoExtensions = loadKnownVideoExtensions();
    private final Logger logger = Logger.getLogger(this.getClass().getName());
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
    public Set<FileExt> get() {
        return files
                .parallelStream()
                .flatMap(file -> FilesExt.walkSafe(file.toPath())
                        .map(FileExt::new)
                        .filter(this::isVideoFile)
                        .peek(fileExt -> logger.log(Level.INFO, "Found video file at path: " + fileExt.getAbsolutePath())))
                .collect(Collectors.toSet());
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

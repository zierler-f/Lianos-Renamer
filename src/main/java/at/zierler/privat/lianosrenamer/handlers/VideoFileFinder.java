package at.zierler.privat.lianosrenamer.handlers;

import at.zierler.privat.lianosrenamer.domain.FileExt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class VideoFileFinder implements Function {

    /**
     * List of all video extensions the program knows
     */
    private final List<String> knownVideoExtensions;
    /**
     * List of files/directories to search for video files in
     */
    private List<File> files;

    {
        Properties properties = new Properties();
        InputStream inputStream = VideoFileFinder.class.getClassLoader().getResourceAsStream("application.properties");
        if (inputStream == null) {
            throw new RuntimeException("Properties file not found.");
        }
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading properties file.", e);
        }
        if (!properties.containsKey("video-extensions")) {
            throw new RuntimeException("Key \"video-extensions\" not found in properties file.");
        }
        knownVideoExtensions = Arrays.asList(properties.getProperty("video-extensions").split(","));
    }

    /**
     * sets instance variable files to provided directories/files to search
     *
     * @param files list of files/directories
     */

    public VideoFileFinder(List<File> files) {
        this.files = new ArrayList<>(files);
    }

    @Override
    public List<FileExt> doJob() {
        List<FileExt> videoFiles = new ArrayList<>();
        files.forEach(file -> {
            try {
                videoFiles.addAll(Files.walk(Paths.get(file.getAbsolutePath())).map(path -> new FileExt(path.toString())).filter(this::isVideoFile).collect(Collectors.toList()));
            } catch (IOException e) {
                throw new RuntimeException("Error during walking of files.");
            }
        });
        return videoFiles;
    }

    private boolean isVideoFile(FileExt fileExt) {
        return knownVideoExtensions.contains(fileExt.getExtension());
    }

}

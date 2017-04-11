package at.zierler.privat.lianosrenamer.helpers;

import at.zierler.privat.lianosrenamer.handlers.VideoFileFinder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * Extension class for {@link Files}
 */

public class PathHelper {


    /**
     * List of all video extensions the program knows
     */

    private static final List<String> knownVideoExtensions = loadKnownVideoExtensions();

    /**
     * loads video extensions from application.properties file and returns default video extensions if file or key in file doesn't exist
     *
     * @return list of known video extensions
     */

    private static List<String> loadKnownVideoExtensions() {
        String knownVideoExtensions = "mp4,mkv,flv,wmv,avi";
        Properties properties = new Properties();
        try (InputStream fis = VideoFileFinder.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(fis);
            knownVideoExtensions = properties.getProperty("video-extensions", knownVideoExtensions);
        } catch (IOException | NullPointerException ignored) {
        }
        return Arrays.asList(knownVideoExtensions.split(","));

    }

    /**
     * Runs Files.walk method, but re-throws IOException as RuntimeException for easier handling in streams
     *
     * @param path the starting file
     * @return the {@link Stream} of {@link Path}
     */

    public static Stream<Path> walkSafe(Path path) {
        try {
            return Files.walk(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * checks if a file's extension matches one of the known video file extensions
     *
     * @param path a file
     * @return if the file is a video file
     */

    public static boolean isVideoFile(Path path) {
        return knownVideoExtensions.contains(getExtensionPlain(path));
    }

    /**
     * extracts the extension of a file, if one exists (just the extension, without a leading dot)
     *
     * @param path path to a file
     * @return extension of file (without leading dot)
     */

    public static String getExtensionPlain(Path path) {
        if (Files.isDirectory(path)) {
            return null;
        }
        Path pathFileName = path.getFileName();
        if (pathFileName == null)
            throw new RuntimeException("Couldn't extract filename from file at path: " + path.toString());
        String[] parts = pathFileName.toString().split("\\.");
        if (parts.length < 2) {
            return "";
        }
        return parts[parts.length - 1];
    }

    /**
     * extracts the extension of a file, if one exists (extension with leading dot)
     *
     * @param path path to a file
     * @return extension of file (with leading dot)
     */

    public static String getExtension(Path path) {
        String extensionPlain = getExtensionPlain(path);
        return extensionPlain == null || extensionPlain.equals("") ? extensionPlain : "." + extensionPlain;
    }


    /**
     * checks if file exists, prints corresponding message and returns true/false
     *
     * @param path existing/non-existing File
     * @return boolean depending on file's existence
     */

    public static Path toRealPathSafe(Path path) {
        try {
            return path.toRealPath();
        } catch (IOException e) {
            throw new IllegalArgumentException("Not all files passed to the program exist on the file system.", e);
        }
    }

}

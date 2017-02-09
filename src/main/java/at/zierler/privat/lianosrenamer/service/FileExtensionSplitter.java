package at.zierler.privat.lianosrenamer.service;

import java.nio.file.Path;

/**
 * Finds the extension of files
 */

public class FileExtensionSplitter {

    /**
     * Path to File
     */

    private Path path;

    private FileExtensionSplitter(Path path) {
        this.path = path;
    }

    /**
     * Returns a new FileExtensionSplitter containing path to a file
     *
     * @param path path to a file
     * @return new FileExtensionSplitter containing path to file
     */

    public static FileExtensionSplitter of(Path path) {
        return new FileExtensionSplitter(path);
    }

    /**
     * Returns file extension of file at path
     *
     * @return file extension
     */

    public String getFileExtension() {
        if (path == null) {
            return "";
        }
        String[] parts = path.getFileName().toString().split("\\.");
        if (parts.length < 2) {
            return "";
        }
        return parts[parts.length - 1];
    }

}

package at.zierler.privat.lianosrenamer.service;

import java.nio.file.Path;

public class FileExtensionGetter {

    private FileExtensionGetter() {
        throw new IllegalAccessError("Objects of the type FileExtensionGetter cannot be created.");
    }

    public static String getFileExtension(Path path) {
        if (path == null)
            return null;
        String[] parts = path.getFileName().toString().split("\\.");
        if (parts.length < 2)
            return null;
        return parts[parts.length - 1];
    }

}

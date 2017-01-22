package at.zierler.privat.lianosrenamer.helper;

import java.nio.file.Path;

public class FileExtensionGetter {

    private FileExtensionGetter() {
        throw new IllegalAccessError("Objects of the type FileExtensionGetter cannot be created.");
    }

    public static String getFileExtension(Path path) {
        String[] parts = path.getFileName().toString().split("\\.");
        return parts[parts.length - 1];
    }

}

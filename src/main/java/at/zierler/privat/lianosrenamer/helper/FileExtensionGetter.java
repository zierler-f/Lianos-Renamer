package at.zierler.privat.lianosrenamer.helper;

import java.nio.file.Path;

public class FileExtensionGetter {

    public static String getFileExtension(Path path) {
        String[] parts = path.getFileName().toString().split("\\.");
        return parts[parts.length - 1];
    }

}

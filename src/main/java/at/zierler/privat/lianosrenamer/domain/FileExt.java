package at.zierler.privat.lianosrenamer.domain;

import java.io.File;

public class FileExt extends File {

    private String extension;

    public FileExt(String pathname) {
        super(pathname);
        this.extension = findExtension();
    }

    private String findExtension() {
        String[] parts = this.toPath().getFileName().toString().split("\\.");
        if (parts.length < 2) {
            return "";
        }
        return parts[parts.length - 1];
    }


    public String getExtension() {
        return extension;
    }
}

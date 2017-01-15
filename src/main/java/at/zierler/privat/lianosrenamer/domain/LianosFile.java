package at.zierler.privat.lianosrenamer.domain;

import java.io.File;

public class LianosFile extends File {

    private String originalPath;

    public LianosFile(String pathname) {
        super(pathname);
        originalPath = pathname;
    }
}

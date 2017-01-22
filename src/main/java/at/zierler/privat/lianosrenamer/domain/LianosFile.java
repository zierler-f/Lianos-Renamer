package at.zierler.privat.lianosrenamer.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class LianosFile extends File {

    private String originalPath;

    public LianosFile(String pathname) {
        super(pathname);
        originalPath = pathname;
    }

    public LianosFile(File file){
        super(file.getAbsolutePath());
        originalPath = file.getAbsolutePath();
    }

    public  LianosFile(File parent, String child){
        super(parent,child);
    }
}

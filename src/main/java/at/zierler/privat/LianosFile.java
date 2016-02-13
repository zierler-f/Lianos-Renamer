package at.zierler.privat;

import at.zierler.privat.exceptions.LianosRenamerException;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by florian on 2/8/16.
 */
public class LianosFile extends File {

    private final FileType type;
    private final LianosFile originalFile;

    public LianosFile(String pathname){
        super(pathname);
        this.type = getFileTypeByPath(pathname); //Set type according to getFileTypeByPath() automatically. SingleFile, Folder or NonExistent are possible outcomes.
        this.originalFile = this;
    }

    public LianosFile(File file){
        super(file.getAbsolutePath());
        this.type = getFileTypeByPath(file.getAbsolutePath()); //Set type according to getFileTypeByPath() automatically. SingleFile, Folder or NonExistent are possible outcomes.
        this.originalFile = this;
    }

    public LianosFile(File file, LianosFile originalFile){
        super(file.getAbsolutePath());
        this.type = getFileTypeByPath(file.getAbsolutePath()); //Set type according to getFileTypeByPath() automatically. SingleFile, Folder or NonExistent are possible outcomes.
        this.originalFile = originalFile;
    }

    private FileType getFileTypeByPath(String path){
        File file = new File(path);
        if(file.exists()) {
            if (file.isFile()) {
                return FileType.SingleFile;
            } else {
                return FileType.Folder;
            }
        }
        else{
            return FileType.NonExistent;
        }
    }

    public String getFileExtension(){
        if(getName().lastIndexOf(".") != -1 && getName().lastIndexOf(".") != 0) {
            return getName().substring(getName().lastIndexOf(".") + 1);
        }
        else{
            return "";
        }
    }

    public FileType getType() {
        return type;
    }

    public LianosFile getOriginalFile(){
        return originalFile;
    }
}

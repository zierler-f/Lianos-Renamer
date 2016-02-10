package at.zierler.privat;

import at.zierler.privat.exceptions.LianosRenamerException;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by florian on 2/8/16.
 */
public class LianosFile extends File {

    private final FileType type;

    public LianosFile(String pathname) throws LianosRenamerException {
        super(pathname);
        this.type = getFileTypeByPath(pathname); //Set type according to getFileTypeByPath() automatically. SingleFile, Folder or NonExistent are possible outcomes.
    }

    private FileType getFileTypeByPath(String path) throws LianosRenamerException {
        File file = new File(path);
        if(file.exists()) {
            if (file.isFile()) {
                return FileType.SingleFile;
            } else if (file.isDirectory()) {
                return FileType.Folder;
            } else {
                throw new LianosRenamerException("Unexpected Error. Path exists but is neither a file nor a folder.");
            }
        }
        else{
            return FileType.NonExistent; //Used to print an error later
        }
    }

    public FileType getType() {
        return type;
    }
}

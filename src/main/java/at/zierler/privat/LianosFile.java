package at.zierler.privat;

import java.io.File;

/**
 * Created by florian on 2/8/16.
 */
public class LianosFile extends File {

    private FileType type;

    public LianosFile(String pathname) throws LianosRenamerException {
        super(pathname);
        this.type = getFileTypeByPath(pathname);
    }

    private FileType getFileTypeByPath(String path) throws LianosRenamerException {
        File file = new File(path);
        if(file.exists()) {
            if (file.isFile()) {
                return FileType.File;
            } else if (file.isDirectory()) {
                return FileType.Folder;
            } else {
                throw new LianosRenamerException("Unexpected Error. Path exists but is neither a file nor a folder.");
            }
        }
        else{
            return FileType.NonExistent;
        }
    }

    public FileType getType() {
        return type;
    }
}

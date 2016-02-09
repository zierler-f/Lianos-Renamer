package at.zierler.privat;

import at.zierler.privat.exceptions.LianosRenamerException;

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
                return FileType.SingleFile;
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

    public void handle(){
        switch (this.getType()){
            case SingleFile: handleSingleFile(); break;
            case Folder: handleFolder(); break;
            case NonExistent: System.out.println(this.getAbsolutePath() + " is not a file.");
        }
    }

    private void handleFolder() {
        System.out.println("Folder: " + this.getAbsolutePath());
    }

    private void handleSingleFile() {
        System.out.println("File: "+ this.getAbsolutePath());
    }

    public FileType getType() {
        return type;
    }
}

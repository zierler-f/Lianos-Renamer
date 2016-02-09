package at.zierler.privat;

import at.zierler.privat.exceptions.LianosRenamerException;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public void handle() throws LianosRenamerException {
        switch (this.getType()){
            case SingleFile: handleSingleFile(); break;
            case Folder: handleFolder(); break;
            case NonExistent: System.out.println(this.getAbsolutePath() + " is not a file."); break;
            default: throw new LianosRenamerException("Unexpected Error. File Type not found.");
        }
    }

    private void handleFolder() {
        System.out.println("Folder: " + this.getAbsolutePath());
    }

    private void handleSingleFile() {
        String allowedFiletypes = ".*.mkv|.*.mp4|.*.flv|.*.avi|.*.wmv";
        String filename = this.getName();
        if(filename.matches(allowedFiletypes)){
            System.out.println(filename);
            String patternStr = "s[0-9]{1,3}e[0-9]{1,3}|[0-9]{1,3}x[0-9]{1,3}";
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(filename);
            if(matcher.find()){
                System.out.println(filename.substring(matcher.start(),matcher.end()));
            }
        }
        else{
            System.out.println(this.getAbsolutePath() + " is not a video-file.");
        }
    }

    public FileType getType() {
        return type;
    }
}

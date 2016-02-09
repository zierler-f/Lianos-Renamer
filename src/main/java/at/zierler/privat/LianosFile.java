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
        String allowedFiletypes = ".*.mkv|.*.mp4|.*.flv|.*.avi|.*.wmv"; //Regex for possible video file-types.
        if(getName().matches(allowedFiletypes)){
            System.out.println("Now processing " + getAbsolutePath());
            Pattern seasonEpisodePattern = Pattern.compile("(?i)s[0-9]{1,3}.*(?i)e[0-9]{1,3}|[0-9]{1,3}(?i)X[0-9]{1,3}"); //Find either s<Number> e<Number> or <Number>x<Number> in filename. Case doesn't matter here.
            Matcher seasonEpisodeMatcher = seasonEpisodePattern.matcher(getName());
            if(seasonEpisodeMatcher.find()){ //Depends on if the matcher got a result or not.
                String seasonEpisodeContainer = getName().substring(seasonEpisodeMatcher.start(),seasonEpisodeMatcher.end());
                System.out.println(seasonEpisodeContainer);
            }
            else{
                System.out.println(getAbsolutePath() + " did not contain information about episode and/or season number.");
            }
        }
        else{
            System.out.println(getAbsolutePath() + " is not a video-file.");
        }
    }

    public FileType getType() {
        return type;
    }
}

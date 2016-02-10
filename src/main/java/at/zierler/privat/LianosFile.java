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

    private void handleFolder() throws LianosRenamerException {
        for(File file:this.listFiles()){
            LianosFile lFile = new LianosFile(file.getAbsolutePath());
            lFile.handle();
        }
    }

    private void handleSingleFile() throws LianosRenamerException {
        String allowedFiletypes = ".*.mkv|.*.mp4|.*.flv|.*.avi|.*.wmv"; //Regex for possible video file-types.
        if(getName().matches(allowedFiletypes)){
            System.out.println("Now processing " + getAbsolutePath());
            String seriesName;
            int seasonNumber = -1;
            int episodeNumber = -1;

            String seasonXEpisodePattern = "[0-9]{1,3}(?i)x[0-9]{1,3}"; //Pattern for files which contain season and episode like <Season-Number>x<Episode-Number>
            String sSeasonPattern = "(?i)s[0-9]{1,3}"; //Pattern for files which contain season like s<Season-Number>
            String eEpisodePattern = "(?i)e[0-9]{1,3}"; //Pattern for files which contain episode like e<Episode-Number>
            String sSeasonEEpisodePattern = sSeasonPattern + ".*" + eEpisodePattern; //Pattern for files which contain season and episode like s<Season-Number>...e<Episode-Number>

            Pattern seasonEpisodePattern = Pattern.compile(seasonXEpisodePattern + "|" + sSeasonEEpisodePattern); //Compiled Pattern using both Pattern-Strings from above.
            Matcher seasonEpisodeMatcher = seasonEpisodePattern.matcher(getName());

            if(seasonEpisodeMatcher.find()){ //Depends on if the matcher got a result or not.
                String seasonEpisodeContainerString = getName().substring(seasonEpisodeMatcher.start(),seasonEpisodeMatcher.end());
                String seriesNameContainerString = getName().substring(0,seasonEpisodeMatcher.start());

                seriesNameContainerString = seriesNameContainerString.replaceAll("[^A-Za-z ]"," ").trim();
                seriesNameContainerString = seriesNameContainerString.replaceAll(" and ",""); //TVMaze doesn't work with 'and' in the request
                seriesName = seriesNameContainerString.replaceAll(" ","-").toLowerCase();

                if(seasonEpisodeContainerString.matches(seasonXEpisodePattern)){ //Check if season and episode are like <number>x<number>
                    String[] splitSE = seasonEpisodeContainerString.split("(?i)x");
                    seasonNumber = Integer.parseInt(splitSE[0]); //Set Season Number by taking first value of splitSE.
                    episodeNumber= Integer.parseInt(splitSE[1]); //Set Episode Number by taking second value of splitSE.
                }

                else if(seasonEpisodeContainerString.matches(sSeasonEEpisodePattern)){ //Check if season and episode are like s<number>*e<number
                    Matcher seasonMatcher = Pattern.compile(sSeasonPattern).matcher(seasonEpisodeContainerString);
                    Matcher episodeMatcher = Pattern.compile(eEpisodePattern).matcher(seasonEpisodeContainerString);

                    if(seasonMatcher.find() && episodeMatcher.find()){
                        String[] splitS = seasonEpisodeContainerString.substring(seasonMatcher.start(),seasonMatcher.end()).split("(?i)s");
                        String[] splitE = seasonEpisodeContainerString.substring(episodeMatcher.start(),episodeMatcher.end()).split("(?i)e");

                        seasonNumber = Integer.parseInt(splitS[1]); //Set Season Number by taking second value of splitS.
                        episodeNumber = Integer.parseInt(splitE[1]); //Set Episode Number by taking second value of splitS.
                    }

                    else{
                        throw new LianosRenamerException("Unexpected Error. Season or episode number not found.");
                    }
                }

                else{
                    throw new LianosRenamerException("Unexpected Error. Season and/or episode number didn't match any pattern.");
                }

                Episode episode = new Episode(seriesName,seasonNumber,episodeNumber);

                if(episode.getEpisodeTitle() == null){
                    System.out.println("No episode title found for " + episode.getSeriesName() + " Season " + episode.getSeasonNumber() + " Episode " + episode.getEpisodeNumber());
                    return;
                }
                else{
                    System.out.println("Found episode title '" + episode.getEpisodeTitle() + "' for " + getAbsolutePath());

                    String seasonStringFormatted = String.format("%02d", episode.getSeasonNumber());
                    String episodeStringFormatted = String.format("%02d", episode.getEpisodeNumber());

                    String fileExtension = getName().substring(getName().lastIndexOf('.'),getName().length());

                    File newFile = new File(getParentFile().getAbsolutePath()+"/"+episode.getSeriesName()+" - S" + seasonStringFormatted + "E" + episodeStringFormatted + " - " + episode.getEpisodeTitle() + fileExtension);

                    this.renameTo(newFile);

                    System.out.println("\033[0;1m" + "Finished processing " + newFile.getAbsolutePath() + "\033[0;0m");
                }
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

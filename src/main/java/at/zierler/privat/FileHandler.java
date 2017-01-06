package at.zierler.privat;

import at.zierler.privat.exceptions.LianosRenamerException;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileHandler {

    private int curFolderAnswer;
    private ArrayList<LianosFile> fileList;

    public FileHandler(){
        setCurFolderAnswer(-1);
        fileList = new ArrayList<LianosFile>();
    }

    public ArrayList<LianosFile> handle(LianosFile file) throws LianosRenamerException {
        switch (file.getType()){
            case SingleFile: fileList.add(handleSingleFile(file)); break;
            case Folder: handleFolder(file); break;
            case NonExistent: System.out.println(file.getAbsolutePath() + " is not a file."); break;
            default: throw new LianosRenamerException("Unexpected Error. File Type not found.");
        }
        return fileList;
    }

    private void handleFolder(LianosFile folder) throws LianosRenamerException {
        this.curFolderAnswer = -1;
        for(File curFile:folder.listFiles()){
            handle(new LianosFile(curFile));
        }
    }

    private LianosFile handleSingleFile(LianosFile file) throws LianosRenamerException {
        String allowedFiletypes = ".*\\.mkv|.*\\.mp4|.*\\.flv|.*\\.avi|.*\\.wmv"; //Regex for possible video file-types.
        if(file.getName().matches(allowedFiletypes)){
            System.out.println("Now processing " + file.getAbsolutePath());
            String seriesName;
            int seasonNumber = -1;
            int episodeNumber = -1;

            String seasonXEpisodePattern = "[0-9]{1,3}(?i)x[0-9]{1,3}"; //Pattern for files which contain season and episode like <Season-Number>x<Episode-Number>
            String sSeasonPattern = "(?i)s[0-9]{1,3}"; //Pattern for files which contain season like s<Season-Number>
            String eEpisodePattern = "(?i)e[0-9]{1,3}"; //Pattern for files which contain episode like e<Episode-Number>
            String sSeasonEEpisodePattern = sSeasonPattern + ".*" + eEpisodePattern; //Pattern for files which contain season and episode like s<Season-Number>...e<Episode-Number>

            Pattern seasonEpisodePattern = Pattern.compile(seasonXEpisodePattern + "|" + sSeasonEEpisodePattern); //Compiled Pattern using both Pattern-Strings from above.
            Matcher seasonEpisodeMatcher = seasonEpisodePattern.matcher(file.getName());

            if(seasonEpisodeMatcher.find()){ //Depends on if the matcher got a result or not.
                String seasonEpisodeContainerString = file.getName().substring(seasonEpisodeMatcher.start(),seasonEpisodeMatcher.end());
                String seriesNameContainerString = file.getName().substring(0,seasonEpisodeMatcher.start());

                seriesNameContainerString = seriesNameContainerString.replaceAll("[^A-Za-z ]"," ").trim();
                seriesNameContainerString = seriesNameContainerString.replaceAll("(?i)and ",""); //TVMaze doesn't work with 'and' in the request
                seriesNameContainerString = seriesNameContainerString.replaceAll("(?i)the ",""); //TVMaze doesn't work with 'and' in the request
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

                Episode episode = new Episode(seriesName,seasonNumber,episodeNumber,this);

                if(episode.getEpisodeTitle() == null){
                    System.out.println("No episode title found for " + episode.getSeriesName() + " Season " + episode.getSeasonNumber() + " Episode " + episode.getEpisodeNumber());
                }
                else{
                    System.out.println("Found episode title '" + episode.getEpisodeTitle() + "' for " + file.getAbsolutePath());

                    String seasonStringFormatted = String.format("%02d", episode.getSeasonNumber()); //Format Season number to have 2 digits
                    String episodeStringFormatted = String.format("%02d", episode.getEpisodeNumber()); // Format Episdoe number to have 2 digits

                    String newFileName = episode.getSeriesName() +
                            " - S" + seasonStringFormatted +
                            "E" + episodeStringFormatted + " - " +
                            episode.getEpisodeTitle() +
                            "." + file.getFileExtension();

                    LianosFile newFile = new LianosFile(new File(file.getParentFile(),newFileName),file);

                    file.renameTo(newFile); //Renaming to new name

                    System.out.println("\033[0;1m" + "Finished processing " + newFile.getAbsolutePath() + "\033[0;0m");
                    return newFile;
                }
            }
            else{
                System.out.println(file.getAbsolutePath() + " did not contain information about episode and/or season number.");
            }
        }
        else{
            System.out.println(file.getAbsolutePath() + " is not a video-file.");
        }
        return file;
    }

    public int getCurFolderAnswer() {
        return curFolderAnswer;
    }

    public void setCurFolderAnswer(int curFolderAnswer) {
        this.curFolderAnswer = curFolderAnswer;
    }
}

package at.zierler.privat.lianosrenamer.service;

import at.zierler.privat.lianosrenamer.domain.Episode;
import at.zierler.privat.lianosrenamer.domain.Show;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileRenamer {

    /*public void renameFiles(List<Path> paths){
        paths.forEach(this::renameFile);
    }

    public File renameFile(Path path){
        LianosFile file = new LianosFile(path.toString());
        String filename = file.getName();
        String absolutePath = file.getAbsolutePath();
        System.out.println("Now processing " + absolutePath + ".");
        if(!file.isFile()){
            System.out.println(absolutePath + " is not a file.");
        }
        else {
            Episode episode = getShowByFileName(filename);
        }
        return file;
    }*/

    public Episode getShowByFileName(String filename) {
        Pattern pattern = Pattern.compile("^.*?((((?i)s[0-9]{1,2})((?i)e[0-9]{1,2}))|([0-9]{1,2})((?i)x[0-9]{1,2}))");
        Matcher matcher = pattern.matcher(filename);
        if (matcher.find()) {
            String matchedString = matcher.group();
            String matchedEpisode = matcher.group(1);
            String name = matchedString.replace(matchedEpisode, "").replaceAll("[^A-Za-z ]", " ").replaceAll("(?i)and ", "")
                    .replaceAll("(?i)the ", "").trim().replaceAll(" ", "-").toLowerCase();
            Pattern seasonEpisodePattern = Pattern.compile("^(?i)[A-Z]*([0-9]{1,2})(?i)[A-Z]*([0-9]{1,2})");
            Matcher seasonEpisodeMatcher = seasonEpisodePattern.matcher(matchedEpisode);
            if (seasonEpisodeMatcher.find()) {
                int seasonNumber = Integer.parseInt(seasonEpisodeMatcher.group(1));
                int episodeNumber = Integer.parseInt(seasonEpisodeMatcher.group(2));
                Show show = new Show();
                show.setName(name);
                Episode episode = new Episode();
                episode.setShow(show);
                episode.setSeasonNumber(seasonNumber);
                episode.setEpisodeNumber(episodeNumber);
                return episode;
            }
        }
        return null;
    }

}

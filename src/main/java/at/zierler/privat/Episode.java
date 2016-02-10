package at.zierler.privat;

/**
 * Created by florian on 2/10/16.
 */
public class Episode {

    private String seriesName;
    private int seasonNumber;
    private int episodeNumber;
    private String episodeTitle;

    public Episode(String seriesName, int seasonNumber, int episodeNumber){
        this.seriesName = seriesName;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.episodeTitle = downloadEpisodeTitle();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Series Name: ");
        sb.append(getSeriesName());
        sb.append("\n");
        sb.append("Season: ");
        sb.append(getSeasonNumber());
        sb.append("\n");
        sb.append("Episode: ");
        sb.append(getEpisodeNumber());
        sb.append("\n");
        sb.append("Episode Title: ");
        sb.append(getEpisodeTitle());
        return sb.toString();
    }

    private String downloadEpisodeTitle(){
        return "";
    }

    public String getSeriesName() {
        return seriesName;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public String getEpisodeTitle() {
        return episodeTitle;
    }
}

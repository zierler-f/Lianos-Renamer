package at.zierler.privat;

import at.zierler.privat.exceptions.LianosRenamerException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by florian on 2/10/16.
 */
public class Episode {

    private String seriesName;
    private int seasonNumber;
    private int episodeNumber;
    private String episodeTitle;

    public Episode(String seriesName, int seasonNumber, int episodeNumber) throws LianosRenamerException {
        this.seriesName = seriesName;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.episodeTitle = downloadEpisodeTitle();
    }

    private String downloadEpisodeTitle() throws LianosRenamerException {
        String urlString = "http://api.tvmaze.com/search/shows?q=" + getSeriesName();
        JSONArray resultArray = new JSONArray(sendGetRequest(urlString));
        int showId = -1;
        switch (resultArray.length()){
            case 0:  System.out.println("No shows found.");
            case 1:  showId = resultArray.getJSONObject(0).getJSONObject("show").getInt("id");
                     setSeriesName(resultArray.getJSONObject(0).getJSONObject("show").getString("name")); break;
            default: int numberInArray = userChooseSeries(resultArray);
                     showId = resultArray.getJSONObject(numberInArray).getJSONObject("show").getInt("id");
                     setSeriesName(resultArray.getJSONObject(numberInArray).getJSONObject("show").getString("name")); break;
        }
        if(showId == -1){
            return "";
        }
        urlString = "http://api.tvmaze.com/shows/" + showId +"/episodebynumber?season=" + getSeasonNumber() + "&number=" + getEpisodeNumber();
        JSONObject resultObject = new JSONObject(sendGetRequest(urlString));
        return resultObject.getString("name");
    }

    private int userChooseSeries(JSONArray series){
        System.out.println("Please choose a Series be selecting number next to it:");
        for(int i = 0; i<series.length(); i++){
            System.out.println("[" + (i+1) + "] " + series.getJSONObject(i).getJSONObject("show").getString("name"));
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Number: ");
        int n = scanner.nextInt();
        return n-1;
    }

    public String sendGetRequest(String urlString) throws LianosRenamerException {
        try {
            URL url = new URL(urlString);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            String s = "";

            while ((line = br.readLine()) != null) {
                s += line;
            }

            br.close();
            return s;
        } catch (IOException e) {
            throw new LianosRenamerException("Unexpected Error. " + e.getMessage());
        }
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

    public void setSeriesName(String seriesName){
        this.seriesName = seriesName;
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
}

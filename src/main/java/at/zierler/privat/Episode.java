package at.zierler.privat;

import at.zierler.privat.exceptions.LianosRenamerException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by florian on 2/10/16.
 */
public class Episode {

    private String seriesName;
    private int seasonNumber;
    private int episodeNumber;
    private String episodeTitle;
    private FileHandler handler;

    public Episode(String seriesName, int seasonNumber, int episodeNumber, FileHandler handler) throws LianosRenamerException {
        this.seriesName = seriesName;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.handler = handler;
        this.episodeTitle = downloadEpisodeTitle();
    }

    private String downloadEpisodeTitle() throws LianosRenamerException {
        String urlString = "http://api.tvmaze.com/search/shows?q=" + getSeriesName();
        String resultString = sendGetRequest(urlString); //Get results from TVMaze
        if(resultString == null){
            return null;
        }
        JSONArray resultArray = new JSONArray(resultString);
        int showId = -1;
        switch (resultArray.length()){
            case 0:
                return null;
            case 1:
                showId = resultArray.getJSONObject(0).getJSONObject("show").getInt("id");
                setSeriesName(resultArray.getJSONObject(0).getJSONObject("show").getString("name")); break;
            default:
                int numberInArray;
                if(handler.getCurFolderAnswer() == -1){
                    numberInArray = userChooseSeries(resultArray);
                }
                else{
                    numberInArray = handler.getCurFolderAnswer();
                }
                showId = resultArray.getJSONObject(numberInArray).getJSONObject("show").getInt("id");
                setSeriesName(resultArray.getJSONObject(numberInArray).getJSONObject("show").getString("name"));
                handler.setCurFolderAnswer(numberInArray); break;
        }
        urlString = "http://api.tvmaze.com/shows/" + showId +"/episodebynumber?season=" + getSeasonNumber() + "&number=" + getEpisodeNumber();
        resultString = sendGetRequest(urlString);
        if(resultString == null){
            return null;
        }
        JSONObject resultObject = new JSONObject(resultString);
        return resultObject.getString("name");
    }

    private int userChooseSeries(JSONArray series){
        System.out.println("Please choose a Series be selecting number next to it:");
        for(int i = 0; i<series.length(); i++){
            int yearPremiered = Integer.parseInt((series.getJSONObject(i).getJSONObject("show").getString("premiered").split("-"))[0]);
            System.out.println("[" + (i+1) + "] " + series.getJSONObject(i).getJSONObject("show").getString("name") + " (" + yearPremiered + ")");
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Number: ");
        int n = -1;
        try{
            n = scanner.nextInt();
            if(n > series.length() || n < 1){
                throw new InputMismatchException();
            }
        }
        catch(InputMismatchException e){
            System.out.println("Invalid input. Please use a number between 1 and " + series.length() + "." );
            return userChooseSeries(series);
        }
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
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            br.close();
            return result;
        } catch (IOException e) {
            System.out.println("Season " + getSeasonNumber() + " Episode " + getEpisodeNumber() + " doesn't exist for " + getSeriesName() + ".");
            return null;
        }
    }

    @Override
    public String toString(){
        return "Series Name: " + getSeriesName() + "\t" + "Season: " + getSeasonNumber() + "\t" + "Episode: " + getEpisodeNumber() + "Episode Title: " +getEpisodeTitle();
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
}

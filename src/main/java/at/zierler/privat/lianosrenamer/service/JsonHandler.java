package at.zierler.privat.lianosrenamer.service;

import at.zierler.privat.lianosrenamer.domain.Episode;
import at.zierler.privat.lianosrenamer.domain.Show;
import at.zierler.privat.lianosrenamer.exceptions.LianosRenamerException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsonHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Show> getAllShowsByURL(String urlString) throws LianosRenamerException {
        try {
            URL url = new URL(urlString);
            return getAllShowsByURL(url);
        } catch (MalformedURLException e) {
            throw new LianosRenamerException("Provided String doesn't seem to be an URL. Please check.", e);
        }
    }

    private List<Show> getAllShowsByURL(URL url) throws LianosRenamerException {
        List<Show> shows = new ArrayList<>();
        try {
            objectMapper.readTree(url).forEach(n -> shows.add(objectMapper.convertValue(n.get("show"), Show.class)));
            return shows;
        } catch (IOException e) {
            throw new LianosRenamerException("Couldn't parse provided JSON to a list of Show Objects. Please check your input.", e);
        }
    }

    public Episode getEpisodeByURL(String urlString) throws LianosRenamerException {
        try {
            URL url = new URL(urlString);
            return getEpisodeByURL(url);
        } catch (MalformedURLException e) {
            throw new LianosRenamerException("Provided String doesn't seem to be an URL. Please check.", e);
        }
    }

    private Episode getEpisodeByURL(URL url) throws LianosRenamerException {
        try {
            return objectMapper.readValue(url, Episode.class);
        } catch (IOException e) {
            throw new LianosRenamerException("Couldn't parse provided JSON to an Episode Objects. Please check your input.", e);
        }
    }
}

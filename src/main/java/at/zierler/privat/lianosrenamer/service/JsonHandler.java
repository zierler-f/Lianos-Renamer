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

    private URL url;
    private ObjectMapper objectMapper;

    private JsonHandler(URL url, ObjectMapper objectMapper) {
        this.url = url;
        this.objectMapper = objectMapper;
    }

    public static JsonHandler of(String url) throws LianosRenamerException {
        URL u;
        try {
            u = new URL(url);
        } catch (MalformedURLException e) {
            throw new LianosRenamerException("Provided String doesn't seem to be an URL.", e);
        }
        return new JsonHandler(u, new ObjectMapper());
    }

    public List<Show> getAllShowsByURL() throws LianosRenamerException {
        List<Show> shows = new ArrayList<>();
        try {
            objectMapper.readTree(url).forEach(n -> shows.add(objectMapper.convertValue(n.get("show"), Show.class)));
            return shows;
        } catch (IOException e) {
            throw new LianosRenamerException("Couldn't parse provided JSON to a list of Show Objects. Please check your input.", e);
        }
    }

    public Episode getEpisodeByURL() throws LianosRenamerException {
        try {
            return objectMapper.readValue(url, Episode.class);
        } catch (IOException e) {
            throw new LianosRenamerException("Couldn't parse provided JSON to an Episode Objects. Please check your input.", e);
        }
    }
}

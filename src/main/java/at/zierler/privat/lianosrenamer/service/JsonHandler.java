package at.zierler.privat.lianosrenamer.service;

import at.zierler.privat.lianosrenamer.LianosRenamerException;
import at.zierler.privat.lianosrenamer.domain.json.Show;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Show getShowByJson(String json) throws LianosRenamerException {
        try {
            return objectMapper.readValue(json, Show.class);
        } catch (IOException e) {
            throw new LianosRenamerException("Couldn't parse provided JSON to a Show object. Please check your input.", e);
        }
    }

    public List<Show> getAllShowsByJson(String json) throws LianosRenamerException {
        List<Show> shows = new ArrayList<>();
        try {
            for (JsonNode showArrNode : objectMapper.readTree(json)) {
                shows.add(objectMapper.convertValue(showArrNode.get("show"), Show.class));
            }
            return shows;
        } catch (IOException e) {
            throw new LianosRenamerException("Couldn't parse provided JSON to a Show object. Please check your input.", e);
        }
    }

}

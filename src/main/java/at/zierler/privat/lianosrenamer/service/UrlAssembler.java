package at.zierler.privat.lianosrenamer.service;

import at.zierler.privat.lianosrenamer.domain.LookupEpisode;

import java.net.URL;

public class UrlAssembler {

    private static final String baseUrlForShowQuery = "http://api.tvmaze.com/search/shows?q=";
    private static final String baseUrlForEpisodeQuery = "http://api.tvmaze.com/shows/";

    public static String assembleShowQueryUrlByShowName(String showName){
        return baseUrlForShowQuery + showName;
    }

    public static String assembleEpisodeQueryUrlByLookupEpisode(LookupEpisode lookupEpisode, int showId){
        return baseUrlForEpisodeQuery + showId +
                "/episodebynumber?season=" + lookupEpisode.getSeasonNumber() +
                "&number=" + lookupEpisode.getEpisodeNumber();
    }

}

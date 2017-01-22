package at.zierler.privat.lianosrenamer.helper;

import at.zierler.privat.lianosrenamer.domain.LookupEpisode;

public class UrlAssembler {

    private static final String BASE_URL = "http://api.tvmaze.com/";
    private static final String SUB_URL_FOR_POSSIBLE_SHOWS_QUERY = BASE_URL + "search/shows?q=";
    private static final String SUB_URL_FOR_EPISODE_QUERY = BASE_URL + "shows/";

    private UrlAssembler() {
        throw new IllegalAccessError("Objects of the type UrlAssembler cannot be created.");
    }

    public static String assembleShowQueryUrlByShowName(String showName) {
        return SUB_URL_FOR_POSSIBLE_SHOWS_QUERY + showName;
    }

    public static String assembleEpisodeQueryUrlByLookupEpisode(LookupEpisode lookupEpisode, int showId) {
        return SUB_URL_FOR_EPISODE_QUERY + showId +
                "/episodebynumber?season=" + lookupEpisode.getSeasonNumber() +
                "&number=" + lookupEpisode.getEpisodeNumber();
    }

}

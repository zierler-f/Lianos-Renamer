package at.zierler.privat.lianosrenamer.service;

import at.zierler.privat.lianosrenamer.domain.Episode;
import at.zierler.privat.lianosrenamer.domain.Show;
import at.zierler.privat.lianosrenamer.exceptions.LianosRenamerException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

public class JsonHandlerTest extends Assert {

    private JsonHandler jsonHandler;

    @Before
    public void setup() {
        jsonHandler = new JsonHandler();
    }

    @Test(expected = LianosRenamerException.class)
    public void testGetAllShowsByJsonFails() throws LianosRenamerException {
        jsonHandler.getAllShowsByURL("<{this is not a json object!");
    }

    @Test
    public void testGetAllShowsByURL() throws LianosRenamerException {
        String url = "http://api.tvmaze.com/search/shows?q=\"csi\"";
        List<Show> actual = jsonHandler.getAllShowsByURL(url);
        Show firstShow = actual.get(0);
        assertThat(actual.size(), is(10));
        assertThat(firstShow.getId(), is(735));
        assertThat(firstShow.getName(), is("CSI: Cyber"));
        assertThat(firstShow.getPremiereYear(), is(2015));
        assertThat(firstShow.getPremiered(), is(LocalDate.of(2015, 3, 4)));
    }

    @Test(expected = LianosRenamerException.class)
    public void testGetAllShowsByURLFailsWrongUrl() throws LianosRenamerException {
        jsonHandler.getAllShowsByURL("not an url");
    }

    @Test(expected = LianosRenamerException.class)
    public void testGetAllShowsByURLFailsNotJson() throws LianosRenamerException {
        jsonHandler.getAllShowsByURL("http://api.tvmaze.com/");
    }

    @Test
    public void testGetEpisodeByURL() throws LianosRenamerException {
        String url = "http://api.tvmaze.com/shows/431/episodebynumber?season=1&number=1";
        Episode episode = jsonHandler.getEpisodeByURL(url);
        assertThat(episode.getName(), is("The One Where It All Began"));
        assertThat(episode.getSeason(), is(1));
        assertThat(episode.getNumber(), is(1));
    }

    @Test(expected = LianosRenamerException.class)
    public void testGetEpisodeByURLFailsURL() throws LianosRenamerException {
        jsonHandler.getEpisodeByURL("not an url either");
    }

    @Test(expected = LianosRenamerException.class)
    public void testGetEpisodeByURLFailsNotJson() throws LianosRenamerException {
        jsonHandler.getEpisodeByURL("http://api.tvmaze.com/");
    }

}

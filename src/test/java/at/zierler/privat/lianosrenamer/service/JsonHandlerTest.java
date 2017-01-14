package at.zierler.privat.lianosrenamer.service;

import at.zierler.privat.lianosrenamer.LianosRenamerException;
import at.zierler.privat.lianosrenamer.domain.json.Show;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;

public class JsonHandlerTest extends Assert {

    private JsonHandler jsonHandler;

    @Before
    public void setup() {
        jsonHandler = new JsonHandler();
    }

    @Test
    public void testGetShowByJson() throws LianosRenamerException {
        String json = "{\n" +
                "      \"id\": 177,\n" +
                "      \"url\": \"http://www.tvmaze.com/shows/177/pretty-little-liars\",\n" +
                "      \"name\": \"Pretty Little Liars\"}";
        Show actual = jsonHandler.getShowByJson(json);
        assertThat(actual.getId(), is(177));
        assertThat(actual.getName(), is("Pretty Little Liars"));
    }

    @Test(expected = LianosRenamerException.class)
    public void testGetShowByJsonFails() throws LianosRenamerException {
        jsonHandler.getShowByJson("<{this is not a json object!");
    }

    @Test
    public void testGetAllShowsByJson() throws LianosRenamerException {
        String json = "[\n" +
                "  {\n" +
                "    \"score\": 24.543335,\n" +
                "    \"show\": {\n" +
                "      \"id\": 81,\n" +
                "      \"url\": \"http://www.tvmaze.com/shows/81/criminal-minds\",\n" +
                "      \"name\": \"Criminal Minds\"\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"score\": 19.011427,\n" +
                "    \"show\": {\n" +
                "      \"id\": 3032,\n" +
                "      \"url\": \"http://www.tvmaze.com/shows/3032/criminal-minds-beyond-borders\",\n" +
                "      \"name\": \"Criminal Minds: Beyond Borders\"\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"score\": 18.822334,\n" +
                "    \"show\": {\n" +
                "      \"id\": 1020,\n" +
                "      \"url\": \"http://www.tvmaze.com/shows/1020/criminal-minds-suspect-behavior\",\n" +
                "      \"name\": \"Criminal Minds: Suspect Behavior\"\n" +
                "    }\n" +
                "  }\n" +
                "]";
        List<Show> actual = jsonHandler.getAllShowsByJson(json);
        assertThat(actual.size(), is(3));
        assertThat(actual.get(0).getId(), is(81));
        assertThat(actual.get(0).getName(), is("Criminal Minds"));
        assertThat(actual.get(1).getId(), is(3032));
        assertThat(actual.get(1).getName(), is("Criminal Minds: Beyond Borders"));
        assertThat(actual.get(2).getId(), is(1020));
        assertThat(actual.get(2).getName(), is("Criminal Minds: Suspect Behavior"));
    }

    @Test(expected = LianosRenamerException.class)
    public void testGetAllShowsByJsonFails() throws LianosRenamerException {
        jsonHandler.getAllShowsByURL("<{this is not a json object!");
    }

    @Test
    public void testGetAllShowsByURL() throws LianosRenamerException {
        String url = "http://api.tvmaze.com/search/shows?q=\"csi\"";
        List<Show> actual = jsonHandler.getAllShowsByURL(url);
        assertThat(actual.size(),is(10));
        assertThat(actual.get(0).getId(),is(735));
        assertThat(actual.get(0).getName(),is("CSI: Cyber"));
    }

    @Test(expected = LianosRenamerException.class)
    public void testGetAllShowsByURLFailsWrongUrl() throws LianosRenamerException{
        jsonHandler.getAllShowsByURL("not an url");
    }

    @Test(expected = LianosRenamerException.class)
    public void testGetAllShowsByURLFailsNotJson() throws LianosRenamerException{
        jsonHandler.getAllShowsByURL("http://api.tvmaze.com/");
    }

}

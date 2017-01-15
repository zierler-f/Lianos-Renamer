package at.zierler.privat.lianosrenamer.service;

import at.zierler.privat.lianosrenamer.LianosRenamerException;
import at.zierler.privat.lianosrenamer.domain.Episode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.hamcrest.CoreMatchers.is;

public class FileRenamerTest extends Assert {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private FileRenamer fileRenamer;

    @Before
    public void setup() {
        fileRenamer = new FileRenamer();
    }

    @Test
    public void testGetShowByFilename() throws LianosRenamerException {
        Episode pll = fileRenamer.getShowByFileName("pretty.little.liars.4x12.mkv");
        Episode sc = fileRenamer.getShowByFileName("second.chance.s01e01.mkv");
        Episode rp = fileRenamer.getShowByFileName("Royal.Pains.S06E10.HDTV.x264-KILLERS.mp4");
        Episode sal = fileRenamer.getShowByFileName("Secrets-and-lies.01X02.flv");
        assertThat(pll.getShow().getName(), is("pretty-little-liars"));
        assertThat(pll.getSeasonNumber(), is(4));
        assertThat(pll.getEpisodeNumber(), is(12));
        assertThat(sc.getShow().getName(), is("second-chance"));
        assertThat(sc.getSeasonNumber(), is(1));
        assertThat(sc.getEpisodeNumber(), is(1));
        assertThat(rp.getShow().getName(), is("royal-pains"));
        assertThat(rp.getSeasonNumber(), is(6));
        assertThat(rp.getEpisodeNumber(), is(10));
        assertThat(sal.getShow().getName(), is("secrets-lies"));
        assertThat(sal.getSeasonNumber(), is(1));
        assertThat(sal.getEpisodeNumber(), is(2));
    }


}

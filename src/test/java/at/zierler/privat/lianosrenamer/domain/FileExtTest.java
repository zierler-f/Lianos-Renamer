package at.zierler.privat.lianosrenamer.domain;

import at.zierler.privat.lianosrenamer.BaseTest;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

public class FileExtTest extends BaseTest {

    @Test
    public void testExtension() {
        assertThat(new FileExt("test.mp3").getExtension(), is("mp3"));
        assertThat(new FileExt("test.mp4").getExtension(), is("mp4"));
        assertThat(new FileExt("test.txt").getExtension(), is("txt"));
        assertThat(new FileExt("test").getExtension(), is(""));
    }

}

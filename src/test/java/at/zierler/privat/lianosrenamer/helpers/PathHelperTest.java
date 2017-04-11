package at.zierler.privat.lianosrenamer.helpers;

import at.zierler.privat.lianosrenamer.BaseTest;
import org.junit.Test;

import java.nio.file.Paths;

import static org.hamcrest.Matchers.is;

public class PathHelperTest extends BaseTest {

    @Test
    public void testExtension() {
        assertThat(PathHelper.getExtensionPlain(Paths.get("test.mp3")), is("mp3"));
        assertThat(PathHelper.getExtensionPlain(Paths.get("test.mp4")), is("mp4"));
        assertThat(PathHelper.getExtensionPlain(Paths.get("test.mkv")), is("mkv"));
        assertThat(PathHelper.getExtensionPlain(Paths.get("test.txt")), is("txt"));
        assertThat(PathHelper.getExtensionPlain(Paths.get("file")), is(""));
        assertThat(PathHelper.getExtension(Paths.get("test.mp3")), is(".mp3"));
        assertThat(PathHelper.getExtension(Paths.get("test.mp4")), is(".mp4"));
        assertThat(PathHelper.getExtension(Paths.get("test.mkv")), is(".mkv"));
        assertThat(PathHelper.getExtension(Paths.get("test.txt")), is(".txt"));
        assertThat(PathHelper.getExtension(Paths.get("file")), is(""));
    }

}

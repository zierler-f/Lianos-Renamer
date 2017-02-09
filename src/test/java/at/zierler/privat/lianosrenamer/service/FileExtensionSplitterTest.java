package at.zierler.privat.lianosrenamer.service;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;

public class FileExtensionSplitterTest extends Assert {

    @Test
    public void testGetFileExtension() {
        assertThat(FileExtensionSplitter.of(new File("file.txt").toPath()).getFileExtension(), is("txt"));
    }

    @Test
    public void testGetFileExtensionMultipleDots() {
        assertThat(FileExtensionSplitter.of(new File("file1.2.3....mp4").toPath()).getFileExtension(), is("mp4"));
    }

    @Test
    public void testGetFileExtensionNoExtension() {
        assertThat(FileExtensionSplitter.of(new File("file").toPath()).getFileExtension(), is(""));
    }

    @Test
    public void testGetFileExtensionEmpty() {
        assertThat(FileExtensionSplitter.of(null).getFileExtension(), is(""));
    }

}

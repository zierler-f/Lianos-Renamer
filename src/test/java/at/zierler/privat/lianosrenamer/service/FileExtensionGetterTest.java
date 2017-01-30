package at.zierler.privat.lianosrenamer.service;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;

public class FileExtensionGetterTest extends Assert {

    @Test
    public void testGetFileExtension() {
        assertThat(FileExtensionGetter.getFileExtension(new File("file.txt").toPath()), is("txt"));
    }

    @Test
    public void testGetFileExtensionMultipleDots() {
        assertThat(FileExtensionGetter.getFileExtension(new File("file1.2.3....mp4").toPath()), is("mp4"));
    }

    @Test
    public void testGetFileExtensionNoExtension() {
        assertNull(FileExtensionGetter.getFileExtension(new File("file").toPath()));
    }

    @Test
    public void testGetFileExtensionEmpty() {
        assertNull(FileExtensionGetter.getFileExtension(null));
    }

}

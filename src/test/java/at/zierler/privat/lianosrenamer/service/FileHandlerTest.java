package at.zierler.privat.lianosrenamer.service;

import at.zierler.privat.lianosrenamer.LianosRenamerException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

public class FileHandlerTest extends Assert{

    @Rule
    public TemporaryFolder testDir = new TemporaryFolder();

    private FileHandler fileHandler;

    @Before
    public void setup(){
        fileHandler = new FileHandler();
    }

    @Test
    public void testGetListOfVideoFilesByListOfFiles() throws IOException, LianosRenamerException {
        List<File> inputFiles = new ArrayList<>();
        File dir1 = testDir.newFolder("dir1");
        File videoFile1 = testDir.newFile("test.wmv");
        File videoFile2 = new File(dir1,"test.mkv");
        File normalFile1 = new File(dir1,"test.mp3");
        assertTrue(videoFile2.createNewFile());
        assertTrue(normalFile1.createNewFile());
        File subdir1 = new File(dir1,"subdir1");
        assertTrue(subdir1.mkdir());
        File normalFile2 = new File(subdir1,"test.txt");
        assertTrue(normalFile2.createNewFile());
        File subdir2 = new File(subdir1,"subdir2");
        assertTrue(subdir2.mkdir());
        File videoFile3 = new File(subdir2,"test.mp4");
        assertTrue(videoFile3.createNewFile());
        inputFiles.add(videoFile1);
        inputFiles.add(dir1);
        List<Path> resultFiles = fileHandler.getListOfVideoFilesByListOfFiles(inputFiles);
        assertThat(resultFiles.size(),is(3));
        assertTrue(resultFiles.contains(videoFile1.toPath()));
        assertTrue(resultFiles.contains(videoFile2.toPath()));
        assertTrue(resultFiles.contains(videoFile3.toPath()));
    }

}

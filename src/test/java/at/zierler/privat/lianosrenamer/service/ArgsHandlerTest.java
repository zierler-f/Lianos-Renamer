package at.zierler.privat.lianosrenamer.service;

import at.zierler.privat.lianosrenamer.BaseTest;
import at.zierler.privat.lianosrenamer.exceptions.LianosRenamerException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

public class ArgsHandlerTest extends BaseTest {

    @Test
    public void testGetListOfVideoFilesByListOfFiles() throws IOException, LianosRenamerException {
        List<File> inputFiles = new ArrayList<>();
        File dir1 = testDir.newFolder("dir1");
        File dir2 = testDir.newFolder("dir2");
        File videoFile1 = testDir.newFile("test.wmv");
        File videoFile2 = testDir.newFile("test.mp4");
        File normalFile1 = new File(dir1, "test.mp3");
        assertTrue(normalFile1.createNewFile());
        File subdir1 = new File(dir1, "subdir1");
        assertTrue(subdir1.mkdir());
        File subdir2 = new File(subdir1, "subdir2");
        assertTrue(subdir2.mkdir());
        File subdir3 = new File(subdir2, "subdir3");
        assertTrue(subdir3.mkdir());
        File videoFile3 = new File(subdir3, "test.flv");
        File normalFile2 = new File(subdir3, "test.xml");
        assertTrue(videoFile3.createNewFile());
        assertTrue(normalFile2.createNewFile());
        File subdir4 = new File(subdir3, "subdir4");
        assertTrue(subdir4.mkdir());
        File videoFile4 = new File(subdir4, "test.mkv");
        File normalFile3 = new File(subdir4, "test.json");
        assertTrue(videoFile4.createNewFile());
        assertTrue(normalFile3.createNewFile());
        File videoFile5 = new File(dir2, "test1.mkv");
        File videoFile6 = new File(dir2, "test1.flv");
        File normalFile4 = new File(dir2, "test.class");
        assertTrue(videoFile5.createNewFile());
        assertTrue(videoFile6.createNewFile());
        assertTrue(normalFile4.createNewFile());
        String[] argsList = new String[4];
        argsList[0] = dir1.getAbsolutePath();
        argsList[1] = dir2.getAbsolutePath();
        argsList[2] = videoFile1.getAbsolutePath();
        argsList[3] = videoFile2.getAbsolutePath();
        List<File> resultFiles = ArgsHandler.of(argsList).getFileList();
        assertThat(resultFiles.size(), is(4));
        assertTrue(resultFiles.contains(videoFile1));
        assertTrue(resultFiles.contains(videoFile2));
        assertTrue(resultFiles.contains(dir1));
        assertTrue(resultFiles.contains(dir2));
    }

}

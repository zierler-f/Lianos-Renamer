package at.zierler.privat.lianosrenamer;

import at.zierler.privat.lianosrenamer.exceptions.LianosRenamerException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class MainTest extends BaseTest {

    @Test
    public void mainTest() throws IOException, LianosRenamerException {
        File folder1 = testDir.newFolder();
        File file1 = testDir.newFile("pretty-little-liars.s07e10.mp4");
        File file2 = new File(folder1, "riverdale.1x1.mkv");
        assertTrue(file2.createNewFile());
        String[] args = {folder1.getAbsolutePath(), file1.getAbsolutePath()};
        Main.main(args);
        File expectedFile1 = new File(folder1, "Riverdale - S01E01 - Chapter One - The River's Edge.mkv");
        File expectedFile2 = new File(testDir.getRoot(), "Pretty Little Liars - S07E10 - The Darkest Knight.mp4");
        assertTrue(expectedFile1.exists());
        assertTrue(expectedFile2.exists());
        assertFalse(file1.exists());
        assertFalse(file2.exists());
    }

}

package at.zierler.privat.lianosrenamer.service;

import at.zierler.privat.lianosrenamer.BaseTest;
import at.zierler.privat.lianosrenamer.exceptions.LianosRenamerException;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileRenamerTest extends BaseTest {

    @Test(expected = LianosRenamerException.class)
    public void testWithNonExistentFile() throws LianosRenamerException {
        File file = new File("non-existing-file");
        List<File> files = new ArrayList<>();
        files.add(file);
        FileRenamer.of(files).rename();
    }

    @Test
    public void testWithNonVideoFile() throws LianosRenamerException, IOException {
        File file = new File(testDir.newFile("test.txt").getAbsolutePath());
        List<File> files = new ArrayList<>();
        files.add(file);
        assertTrue(file.exists());
        FileRenamer.of(files).rename();
        assertTrue(file.exists());
    }

    @Test
    public void testWithSubfolderWith2NonVideoFiles() throws LianosRenamerException, IOException {
        File createdFolder = testDir.newFolder("test-folder");
        File createdFile1 = testDir.newFile("test-folder/test1.txt");
        File createdFile2 = testDir.newFile("test-folder/test2.txt");
        List<File> files = new ArrayList<>();
        files.add(createdFile1);
        files.add(createdFile2);
        assertTrue(createdFile1.exists());
        assertTrue(createdFile2.exists());
        FileRenamer.of(files).rename();
        assertTrue(createdFile1.exists());
        assertTrue(createdFile2.exists());
    }

    @Test
    public void testWithSubfolderWith1UnrenameableVideoFileAnd1NonVideoFile() throws IOException, LianosRenamerException {
        File createdFolder = testDir.newFolder("test-folder");
        File createdFile1 = testDir.newFile("test-folder/test1.txt");
        File createdFile2 = testDir.newFile("test-folder/test2.mkv");
        List<File> files = new ArrayList<>();
        files.add(createdFile1);
        files.add(createdFile2);
        assertTrue(createdFile1.exists());
        assertTrue(createdFile2.exists());
        FileRenamer.of(files).rename();
        assertTrue(createdFile1.exists());
        assertTrue(createdFile2.exists());
    }

    @Test
    public void testWithSubfolderWith1RenamebaleVideoFileAnd1NonVideoFile() throws IOException, LianosRenamerException {
        File createdFolder = testDir.newFolder("test-folder");
        File createdFile1 = testDir.newFile("test-folder/test1.txt");
        File createdFile2 = testDir.newFile("test-folder/pretty.little.liars.4x12.mkv");
        List<File> files = new ArrayList<>();
        files.add(createdFile1);
        files.add(createdFile2);
        assertTrue(createdFile1.exists());
        assertTrue(createdFile2.exists());
        FileRenamer.of(files).rename();
        assertTrue(createdFile1.exists());
        assertFalse(createdFile2.exists());
        assertTrue(new File(createdFolder, "Pretty Little Liars - S04E12 - Now You See Me, Now You Don't.mkv").exists());
    }

    @Test
    public void testWithVideoFileWhereEpisodeNameContainsSlash() throws LianosRenamerException, IOException {
        File createdFolder = testDir.newFolder("test-folder");
        File createdFile1 = testDir.newFile("test-folder/Royal.Pains.S06E10.HDTV.x264-KILLERS.mp4");
        List<File> files = new ArrayList<>();
        files.add(createdFile1);
        assertTrue(createdFile1.exists());
        FileRenamer.of(files).rename();
        assertFalse(createdFile1.exists());
        assertTrue(new File(createdFolder, "Royal Pains - S06E10 - Good Air - Bad Air.mp4").exists());
    }

    @Test
    public void testWith1VideoFileOfShowWithMultipleAnswers() throws IOException, LianosRenamerException {
        File createdFolder = testDir.newFolder("test-folder");
        File createdFile1 = testDir.newFile("test-folder/friends.s01e01.mkv");
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        List<File> files = new ArrayList<>();
        files.add(createdFile1);
        assertTrue(createdFile1.exists());
        FileRenamer.of(files).rename();
        assertFalse(createdFile1.exists());
        assertTrue(new File(createdFolder, "Friends - S01E01 - The One Where It All Began.mkv").exists());
    }

    @Test
    public void testWith2VideoFilesOfSameShowInOneFolder() throws IOException, LianosRenamerException {
        File createdFolder = testDir.newFolder("test-folder");
        File createdFile1 = testDir.newFile("test-folder/second.chance.s01e01.mkv");
        File createdFile2 = testDir.newFile("test-folder/second.chance.s01e03.mp4");
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        List<File> files = new ArrayList<>();
        files.add(createdFile1);
        files.add(createdFile2);
        assertTrue(createdFile1.exists());
        assertTrue(createdFile2.exists());
        FileRenamer.of(files).rename();
        assertFalse(createdFile1.exists());
        assertFalse(createdFile2.exists());
        assertTrue(new File(createdFolder, "Second Chance - S01E01 - Suitable Donor.mkv").exists());
        assertTrue(new File(createdFolder, "Second Chance - S01E03 - From Darkness, the Sun.mp4").exists());
    }

    @Test(expected = LianosRenamerException.class)
    public void testRenamerFailsNoEpisode() throws LianosRenamerException {
        ArrayList<File> files = new ArrayList<>();
        files.add(new File("pretty-little-liars-3x99.mp4"));
        FileRenamer.of(files).rename();
    }

    @Test(expected = LianosRenamerException.class)
    public void testRenamerFailsNoShow() throws LianosRenamerException {
        ArrayList<File> files = new ArrayList<>();
        files.add(new File("this is not a show.mp4"));
        FileRenamer.of(files).rename();
    }

    @Test(expected = LianosRenamerException.class)
    public void testRenamerFailsNotWriteable() throws LianosRenamerException {
        ArrayList<File> files = new ArrayList<>();
        files.add(new File("riverdale.1x02.mp4"));
        FileRenamer.of(files).rename();
    }

}

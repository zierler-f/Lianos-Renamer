package at.zierler.privat.lianosrenamer.service;

import at.zierler.privat.lianosrenamer.exceptions.LianosRenamerException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileRenamerTest extends Assert {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private FileRenamer fileRenamer;

    @Before
    public void setup() {
        fileRenamer = new FileRenamer();
    }

    @Test(expected = LianosRenamerException.class)
    public void testWithNonExistentFile() throws LianosRenamerException {
        File file = new File("non-existing-file");
        List<File> files = new ArrayList<>();
        files.add(file);
        fileRenamer.renameFiles(files);
    }

    @Test
    public void testWithNonVideoFile() throws LianosRenamerException, IOException {
        File file = new File(folder.newFile("test.txt").getAbsolutePath());
        List<File> files = new ArrayList<>();
        files.add(file);
        assertTrue(file.exists());
        fileRenamer.renameFiles(files);
        assertTrue(file.exists());
    }

    @Test
    public void testWithSubfolderWith2NonVideoFiles() throws LianosRenamerException, IOException {
        File createdFolder = folder.newFolder("test-folder");
        File createdFile1 = folder.newFile("test-folder/test1.txt");
        File createdFile2 = folder.newFile("test-folder/test2.txt");
        List<File> files = new ArrayList<>();
        files.add(createdFile1);
        files.add(createdFile2);
        assertTrue(createdFile1.exists());
        assertTrue(createdFile2.exists());
        fileRenamer.renameFiles(files);
        assertTrue(createdFile1.exists());
        assertTrue(createdFile2.exists());
    }

    @Test
    public void testWithSubfolderWith1UnrenameableVideoFileAnd1NonVideoFile() throws IOException, LianosRenamerException {
        File createdFolder = folder.newFolder("test-folder");
        File createdFile1 = folder.newFile("test-folder/test1.txt");
        File createdFile2 = folder.newFile("test-folder/test2.mkv");
        List<File> files = new ArrayList<>();
        files.add(createdFile1);
        files.add(createdFile2);
        assertTrue(createdFile1.exists());
        assertTrue(createdFile2.exists());
        fileRenamer.renameFiles(files);
        assertTrue(createdFile1.exists());
        assertTrue(createdFile2.exists());
    }

    @Test
    public void testWithSubfolderWith1RenamebaleVideoFileAnd1NonVideoFile() throws IOException, LianosRenamerException {
        File createdFolder = folder.newFolder("test-folder");
        File createdFile1 = folder.newFile("test-folder/test1.txt");
        File createdFile2 = folder.newFile("test-folder/pretty.little.liars.4x12.mkv");
        List<File> files = new ArrayList<>();
        files.add(createdFile1);
        files.add(createdFile2);
        assertTrue(createdFile1.exists());
        assertTrue(createdFile2.exists());
        fileRenamer.renameFiles(files);
        assertTrue(createdFile1.exists());
        assertFalse(createdFile2.exists());
        assertTrue(new File(createdFolder, "Pretty Little Liars - S04E12 - Now You See Me, Now You Don't.mkv").exists());
    }

    @Test
    public void testWithVideoFileWhereEpisodeNameContainsSlash() throws LianosRenamerException, IOException {
        File createdFolder = folder.newFolder("test-folder");
        File createdFile1 = folder.newFile("test-folder/Royal.Pains.S06E10.HDTV.x264-KILLERS.mp4");
        List<File> files = new ArrayList<>();
        files.add(createdFile1);
        assertTrue(createdFile1.exists());
        fileRenamer.renameFiles(files);
        assertFalse(createdFile1.exists());
        assertTrue(new File(createdFolder, "Royal Pains - S06E10 - Good Air_Bad Air.mp4").exists());
    }

    @Test
    public void testWith1VideoFileOfShowWithMultipleAnswers() throws IOException, LianosRenamerException {
        File createdFolder = folder.newFolder("test-folder");
        File createdFile1 = folder.newFile("test-folder/friends.s01e01.mkv");
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        List<File> files = new ArrayList<>();
        files.add(createdFile1);
        assertTrue(createdFile1.exists());
        fileRenamer.renameFiles(files);
        assertFalse(createdFile1.exists());
        assertTrue(new File(createdFolder, "Friends - S01E01 - The One Where It All Began.mkv").exists());
    }

    @Test
    public void testWith2VideoFilesOfSameShowInOneFolder() throws IOException, LianosRenamerException {
        File createdFolder = folder.newFolder("test-folder");
        File createdFile1 = folder.newFile("test-folder/second.chance.s01e01.mkv");
        File createdFile2 = folder.newFile("test-folder/second.chance.s01e03.mp4");
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        List<File> files = new ArrayList<>();
        files.add(createdFile1);
        files.add(createdFile2);
        assertTrue(createdFile1.exists());
        assertTrue(createdFile2.exists());
        fileRenamer.renameFiles(files);
        assertFalse(createdFile1.exists());
        assertFalse(createdFile2.exists());
        assertTrue(new File(createdFolder, "Second Chance - S01E01 - Suitable Donor.mkv").exists());
        assertTrue(new File(createdFolder, "Second Chance - S01E03 - From Darkness, the Sun.mp4").exists());
    }

}

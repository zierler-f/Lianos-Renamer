package at.zierler.privat.lianosrenamer.service;

import at.zierler.privat.lianosrenamer.LianosRenamerException;
import at.zierler.privat.lianosrenamer.domain.LianosFile;
import at.zierler.privat.lianosrenamer.domain.LookupEpisode;
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
        LianosFile file = new LianosFile("non-existing-file");
        List<File> files = new ArrayList<>();
        files.add(file);
        fileRenamer.renameFiles(files);
    }

    @Test
    public void testWithNonVideoFile() throws LianosRenamerException, IOException {
        LianosFile file = new LianosFile(folder.newFile("test.txt").getAbsolutePath());
        List<File> files = new ArrayList<>();
        files.add(file);
        assertTrue(file.exists());
        fileRenamer.renameFiles(files);
        assertTrue(file.exists());
    }

    @Test
    public void testWithSubfolderWith2NonVideoFiles() throws LianosRenamerException, IOException {
        LianosFile createdFolder = new LianosFile(folder.newFolder("test-folder"));
        LianosFile createdFile1 = new LianosFile(folder.newFile("test-folder/test1.txt"));
        LianosFile createdFile2 = new LianosFile(folder.newFile("test-folder/test2.txt"));
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
        LianosFile createdFolder = new LianosFile(folder.newFolder("test-folder"));
        LianosFile createdFile1 = new LianosFile(folder.newFile("test-folder/test1.txt"));
        LianosFile createdFile2 = new LianosFile(folder.newFile("test-folder/test2.mkv"));
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
        LianosFile createdFolder = new LianosFile(folder.newFolder("test-folder"));
        LianosFile createdFile1 = new LianosFile(folder.newFile("test-folder/test1.txt"));
        LianosFile createdFile2 = new LianosFile(folder.newFile("test-folder/pretty.little.liars.4x12.mkv"));
        List<File> files = new ArrayList<>();
        files.add(createdFile1);
        files.add(createdFile2);
        assertTrue(createdFile1.exists());
        assertTrue(createdFile2.exists());
        fileRenamer.renameFiles(files);
        assertTrue(createdFile1.exists());
        assertFalse(createdFile2.exists());
        assertTrue(new LianosFile(createdFolder,"Pretty Little Liars - S04E12 - Now You See Me, Now You Don't.mkv").exists());
    }

}

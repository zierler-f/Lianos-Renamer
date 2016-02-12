package at.zierler.privat.test;

import at.zierler.privat.FileHandler;
import at.zierler.privat.LianosFile;
import at.zierler.privat.exceptions.LianosRenamerException;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by florian on 2/12/16.
 */
public class TestLianosRenamer {

    FileHandler fileHandler;
    @Rule
    public TemporaryFolder folder= new TemporaryFolder();

    @Before
    public void setup(){
        fileHandler = new FileHandler();
    }

    @Test
    public void testWithNonExistentFile() throws LianosRenamerException {
        LianosFile file = new LianosFile("non-existing-file");
        Assert.assertTrue(fileHandler.handle(file).isEmpty());
    }

    @Test
    public void testWithNonVideoFile() throws LianosRenamerException, IOException {
        LianosFile createdFile = new LianosFile(folder.newFile("test.txt"));
        ArrayList<LianosFile> resultList = fileHandler.handle(createdFile);
        Assert.assertFalse(resultList.isEmpty());
        Assert.assertEquals(1,resultList.size());
        Assert.assertEquals(resultList.get(0),resultList.get(0).getOriginalFile());
    }

    @Test
    public void testWithSubfolderWith2NonVideoFiles() throws LianosRenamerException, IOException {
        LianosFile createdFolder = new LianosFile(folder.newFolder("test-folder"));
        LianosFile createdFile1 = new LianosFile(folder.newFile("test-folder/test1.txt"));
        LianosFile createdFile2 = new LianosFile(folder.newFile("test-folder/test2.txt"));
        ArrayList<LianosFile> resultList = fileHandler.handle(createdFolder);
        Assert.assertFalse(resultList.isEmpty());
        Assert.assertEquals(2,resultList.size());
        for(LianosFile file:resultList){
            Assert.assertEquals(file,file.getOriginalFile());
        }
    }

    @Test
    public void testWithSubfolderWith1UnrenameableVideoFileAnd1NonVideoFile() throws IOException, LianosRenamerException {
        LianosFile createdFolder = new LianosFile(folder.newFolder("test-folder"));
        LianosFile createdFile1 = new LianosFile(folder.newFile("test-folder/test1.txt"));
        LianosFile createdFile2 = new LianosFile(folder.newFile("test-folder/test2.mkv"));
        ArrayList<LianosFile> resultList = fileHandler.handle(createdFolder);
        Assert.assertFalse(resultList.isEmpty());
        Assert.assertEquals(2,resultList.size());
        for(LianosFile file:resultList){
            Assert.assertEquals(file,file.getOriginalFile());
        }
    }

    @Test
    public void testWithSubfolderWith1RenamebaleVideoFileAnd1NonVideoFile() throws IOException, LianosRenamerException {
        LianosFile createdFolder = new LianosFile(folder.newFolder("test-folder"));
        LianosFile createdFile1 = new LianosFile(folder.newFile("test-folder/test1.txt"));
        LianosFile createdFile2 = new LianosFile(folder.newFile("test-folder/pretty.little.liars.4x12.mkv"));
        ArrayList<LianosFile> resultList = fileHandler.handle(createdFolder);
        Assert.assertFalse(resultList.isEmpty());
        Assert.assertEquals(2,resultList.size());
        Assert.assertEquals(createdFile1,resultList.get(0));
        Assert.assertNotEquals(createdFile2,resultList.get(1));
        Assert.assertEquals("Pretty Little Liars - S04E12 - Now You See Me, Now You Don't.mkv",resultList.get(1).getName());
    }

}

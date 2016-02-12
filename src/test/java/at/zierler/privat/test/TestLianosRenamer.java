package at.zierler.privat.test;

import at.zierler.privat.FileHandler;
import at.zierler.privat.LianosFile;
import at.zierler.privat.Main;
import at.zierler.privat.exceptions.LianosRenamerException;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;

/**
 * Created by florian on 2/12/16.
 */
public class TestLianosRenamer {

    FileHandler fileHandler;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Rule
    public TemporaryFolder folder= new TemporaryFolder();

    @Before
    public void setup(){
        fileHandler = new FileHandler();
    }

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void testWithNonExistentFile() throws LianosRenamerException {
        LianosFile file = new LianosFile("non-existing-file");
        fileHandler.handle(file);
        Assert.assertEquals(file.getAbsolutePath() + " is not a file.",printResult());
    }

    @Test
    public void testWithNonVideoFile() throws LianosRenamerException, IOException {
        File createdFile= folder.newFile("test.txt");
        fileHandler.handle(new LianosFile(createdFile.getAbsolutePath()));
        Assert.assertEquals(createdFile.getAbsolutePath() + " is not a video-file.",printResult());
    }

    private String printResult(){
        return outContent.toString().trim();
    }

}

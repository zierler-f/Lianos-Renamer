package at.zierler.privat.lianosrenamer;

import org.junit.Before;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;

public abstract class FileTest extends BaseTest {

    protected String testFolder;

    @Before
    public void setup() throws IOException {
        TemporaryFolder temporaryFolder = new TemporaryFolder();
        temporaryFolder.create();
        testFolder = temporaryFolder.getRoot().getAbsolutePath();
    }


}

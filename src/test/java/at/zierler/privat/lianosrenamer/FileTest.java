package at.zierler.privat.lianosrenamer;

import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public abstract class FileTest extends BaseTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

}

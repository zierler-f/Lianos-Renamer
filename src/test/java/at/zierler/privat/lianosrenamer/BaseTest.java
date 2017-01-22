package at.zierler.privat.lianosrenamer;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class BaseTest extends Assert {

    @Rule
    public TemporaryFolder testDir = new TemporaryFolder();

    @Test
    public void testTempDirExists() {
        assertTrue(testDir.getRoot().exists());
    }

}

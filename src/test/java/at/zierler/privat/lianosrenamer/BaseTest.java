package at.zierler.privat.lianosrenamer;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public class BaseTest extends Assert {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

}

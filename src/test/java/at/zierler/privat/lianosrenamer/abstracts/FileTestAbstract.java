package at.zierler.privat.lianosrenamer.abstracts;

import org.junit.rules.TemporaryFolder;

import java.io.File;

public abstract class FileTestAbstract extends BaseTest implements FileTest {

    protected String testFolderPathName = createAndGetTestFolder();

    private String createAndGetTestFolder() {
        TemporaryFolder temporaryFolder = createAndGetTemporaryFolder();
        File temporaryFolderFile = getFileOfTemporaryFolder(temporaryFolder);
        String temporaryFolderPathName = getTemporaryFolderPathName(temporaryFolderFile);
        return temporaryFolderPathName;
    }

    private TemporaryFolder createAndGetTemporaryFolder() {
        TemporaryFolder temporaryFolder = new TemporaryFolder();
        return temporaryFolder;
    }

    private File getFileOfTemporaryFolder(TemporaryFolder temporaryFolder) {
        return temporaryFolder.getRoot();
    }

    private String getTemporaryFolderPathName(File temporaryFolderFile) {
        return temporaryFolderFile.getAbsolutePath();
    }
}

package at.zierler.privat.lianosrenamer.handlers;

import at.zierler.privat.lianosrenamer.BaseTest;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class ArgumentHandlerTest extends BaseTest {

    @Test
    public void testArgumentHandler() throws IOException {
        File dir1 = temporaryFolder.newFolder("dir1");
        temporaryFolder.newFolder("dir2");
        File file1 = temporaryFolder.newFile("file1.txt");
        String[] args = {dir1.getAbsolutePath(), "/dev/null/file1", file1.getAbsolutePath(), "/dev/null/file2"};
        List<File> result = new ArgumentHandler(args).doJob();
        assertThat(result, contains(dir1, file1));
        assertThat(result.size(), is(2));
    }

}

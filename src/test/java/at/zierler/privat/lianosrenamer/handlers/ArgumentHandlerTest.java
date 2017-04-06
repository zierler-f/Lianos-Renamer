package at.zierler.privat.lianosrenamer.handlers;

import at.zierler.privat.lianosrenamer.FileTest;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class ArgumentHandlerTest extends FileTest {

    @Test
    public void testArgumentHandler() throws IOException {
        File dir11 = temporaryFolder.newFolder("dir11");
        File file11 = temporaryFolder.newFile("file11.txt");
        String[] args = {dir11.getAbsolutePath(), file11.getAbsolutePath(), file11.getAbsolutePath()};
        Set<File> result = new ArgumentHandler(args).get().collect(Collectors.toSet());
        assertThat(result, hasItem(dir11));
        assertThat(result, hasItem(file11));
        assertThat(result.size(), is(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArgumentHandlerFailsNoArgs() {
        new ArgumentHandler(new String[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArgumentHandlerFailsWrongArg() throws IOException {
        File file21 = temporaryFolder.newFile("file21.txt");
        String[] args = {file21.getAbsolutePath(), "/dev/null/file"};
        List<File> result = new ArgumentHandler(args).get().collect(Collectors.toList());
        assertThat(result.size(), is(1));
    }

}

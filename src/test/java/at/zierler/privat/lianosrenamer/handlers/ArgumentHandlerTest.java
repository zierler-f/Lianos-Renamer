package at.zierler.privat.lianosrenamer.handlers;

import at.zierler.privat.lianosrenamer.FileTest;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class ArgumentHandlerTest extends FileTest {

    @Test
    public void testArgumentHandler() throws IOException {
        Path dir11 = Files.createDirectory(Paths.get(testFolder, "dir11"));
        Path file11 = Files.createFile(Paths.get(testFolder, "file11"));
        String[] args = {dir11.toString(), file11.toString(), file11.toString()};
        Set<Path> result = new ArgumentHandler().apply(args).collect(Collectors.toSet());
        assertThat(result, hasItem(dir11));
        assertThat(result, hasItem(file11));
        assertThat(result.size(), is(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArgumentHandlerFailsNoArgs() {
        new ArgumentHandler().apply(new String[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArgumentHandlerFailsWrongArg() throws IOException {
        Path file21 = Files.createFile(Paths.get(testFolder, "file21"));
        String[] args = {file21.toString(), "/dev/null/file"};
        List<Path> result = new ArgumentHandler().apply(args).collect(Collectors.toList());
        assertThat(result.size(), is(1));
    }

}

package at.zierler.privat.lianosrenamer.service;

import at.zierler.privat.lianosrenamer.BaseTest;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

public class ArgumentsToPathSetConverterTest extends BaseTest {

    private ArgumentsToPathSetConverter argumentsToPathSetConverter;

    @Before
    public void setup() {
        this.argumentsToPathSetConverter = new ArgumentsToPathSetConverter();
    }

    @Test
    public void testConvertReturnsEmptySetForNoArguments() {
        assertThat(argumentsToPathSetConverter
                        .convert(new String[0])
                        .size(),
                is(0));
    }

    @Test
    public void testConvertReturnsSetWithAllArgumentsProvided() {
        String[] args = {"file1", "file2"};
        assertThat(argumentsToPathSetConverter
                        .convert(args),
                containsInAnyOrder(
                        Paths.get("file1"),
                        Paths.get("file2")
                ));
    }

    @Test
    public void testConvertReturnsSetWith1ArgumentForSameArgumentTwice() {
        String[] args = {"file", "file"};
        assertThat(argumentsToPathSetConverter
                        .convert(args)
                        .size(),
                is(1));
    }

}

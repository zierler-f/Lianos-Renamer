package at.zierler.privat.lianosrenamer.handlers;

import at.zierler.privat.lianosrenamer.helpers.PathHelper;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * converts array of arguments, to list of files
 */

public class ArgumentHandler implements Function<String[], Stream<Path>> {

    /**
     * goes through each provided argument and returns args pointing to an existing file on the file system as a list
     *
     * @return list of existing files, provided to args
     */

    @Override
    public Stream<Path> apply(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("At least one argument has to be provided.");
        }
        return Arrays
                .stream(args)                   //create Stream from argument array
                .map(Paths::get)                //create Path for each argument
                .map(PathHelper::toRealPathSafe)  //check if all paths exist
                .distinct();                    //remove duplicates
    }
}

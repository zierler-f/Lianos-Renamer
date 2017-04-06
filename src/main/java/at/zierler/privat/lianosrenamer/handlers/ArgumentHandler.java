package at.zierler.privat.lianosrenamer.handlers;

import java.io.File;
import java.util.Arrays;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * converts array of arguments, to list of files
 */

public class ArgumentHandler implements Function<String[], Stream<File>> {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * goes through each provided argument and returns args pointing to an existing file on the file system as a list
     *
     * @return list of existing files, provided to args
     */

    @Override
    public Stream<File> apply(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("At least one argument has to be provided.");
        }
        return Arrays
                .stream(args)                   //create Stream from argument array
                .map(File::new)                 //create File for each argument
                .filter(this::argExistsAsFile)  //drop arguments which don't refer to a path on the file system
                .distinct();                    //remove duplicates
    }

    /**
     * checks if file exists, prints corresponding message and returns true/false
     *
     * @param file existing/non-existing File
     * @return boolean depending on file's existence
     */

    private boolean argExistsAsFile(File file) {
        if (file.isFile()) {
            logger.log(Level.INFO, "Found single file at path: " + file.getAbsolutePath());
        } else if (file.isDirectory()) {
            logger.log(Level.INFO, "Found directory at path: " + file.getAbsolutePath());
        } else {
            throw new IllegalArgumentException(file.getAbsolutePath() + " does not refer to a file or directory.");
        }
        return true;
    }
}

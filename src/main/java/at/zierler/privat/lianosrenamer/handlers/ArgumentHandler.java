package at.zierler.privat.lianosrenamer.handlers;

import java.io.File;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * converts array of arguments, to list of files
 */

public class ArgumentHandler implements Supplier<Set<File>> {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private String[] args;

    /**
     * sets instance String array args to copy of provided arguments, if more than one argument is provided
     *
     * @param args provided arguments
     */

    public ArgumentHandler(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Please provide at least on argument.");
        }
        this.args = Arrays.copyOf(args, args.length);
    }

    /**
     * goes through each provided argument and returns args pointing to an existing file on the file system as a list
     *
     * @return list of existing files, provided to args
     */

    @Override
    public Set<File> get() {
        return Arrays
                .stream(args)                   //create Stream from argument array
                .map(File::new)                 //create File for each argument
                .filter(this::argExistsAsFile)  //drop arguments which don't refer to a path on the file system
                .collect(Collectors.toSet());   //return list
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

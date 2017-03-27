package at.zierler.privat.lianosrenamer.handlers;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ArgumentHandler implements Function {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private String[] args;

    public ArgumentHandler(String[] args) {
        this.args = args;
    }

    /**
     * goes through each provided argument and returns args pointing to an existing file on the file system as a list
     *
     * @return list of existing files, provided to args
     */

    @Override
    public List<File> doJob() {
        return Arrays
                .stream(args)                   //create Stream from argument array
                .map(File::new)                 //create File for each argument
                .filter(this::argExistsAsFile)  //drop arguments which don't refer to a path on the file system
                .distinct()                     //remove duplicate entries
                .collect(Collectors.toList());  //return list
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
            return true;
        } else if (file.isDirectory()) {
            logger.log(Level.INFO, "Found directory at path: " + file.getAbsolutePath());
            return true;
        } else {
            logger.log(Level.WARNING, "Neither a file nor a directory was found at path: " + file.getAbsolutePath() +
                    ". Continuing with other paths.");
            return false;
        }
    }

}

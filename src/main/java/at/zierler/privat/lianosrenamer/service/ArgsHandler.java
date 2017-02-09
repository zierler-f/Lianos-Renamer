package at.zierler.privat.lianosrenamer.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Handles arguments and converts them to other forms
 */
public class ArgsHandler {

    /**
     * Array of arguments passed to the program
     */

    private String[] args;

    private ArgsHandler(String[] args) {
        this.args = args;
    }

    /**
     * Returns new ArgsHandler with passed arguments
     *
     * @param args all program arguments
     * @return new ArgsHandler containing list of arguments
     */

    public static ArgsHandler of(String[] args) {
        return new ArgsHandler(args);
    }

    /**
     * converts array of arguments, to list of files
     *
     * @return list of files
     */

    public List<File> getFileList() {
        List<String> argsList = Arrays.asList(args);
        List<File> files = new ArrayList<>();
        argsList.forEach(arg -> files.add(new File(arg)));
        return files;
    }

}

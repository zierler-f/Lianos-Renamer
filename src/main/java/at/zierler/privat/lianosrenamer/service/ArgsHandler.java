package at.zierler.privat.lianosrenamer.service;

import at.zierler.privat.lianosrenamer.LianosRenamerException;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArgsHandler {

    private final FileHandler fileHandler = new FileHandler();

    public List<Path> getListOfVideoFilesByArgs(String[] args) throws LianosRenamerException {
        List<String> argsList = Arrays.asList(args);
        List<File> files = new ArrayList<>();
        argsList.forEach(arg -> files.add(new File(arg)));
        return fileHandler.getListOfVideoFilesByListOfFiles(files);
    }

}

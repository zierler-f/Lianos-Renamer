package at.zierler.privat.lianosrenamer.service;

import at.zierler.privat.lianosrenamer.exceptions.LianosRenamerException;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArgsHandler {

    private ArgsHandler() {
        throw new IllegalAccessError("Objects of the type ArgsHandler cannot be created.");
    }

    public static List<File> getListOfFilesByArgs(String[] args) throws LianosRenamerException {
        List<String> argsList = Arrays.asList(args);
        List<File> files = new ArrayList<>();
        argsList.forEach(arg -> files.add(new File(arg)));
        return files;
    }

}

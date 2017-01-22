package at.zierler.privat.lianosrenamer.service;

import at.zierler.privat.lianosrenamer.exceptions.LianosRenamerException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileHandler {

    private static final List<String> videoExtensions = Arrays.asList("mp4", "mkv", "wmv", "flv");

    private FileHandler() {
        throw new IllegalAccessError("Objects of the type FileHandler cannot be created.");
    }

    public static List<Path> getListOfVideoFilesByListOfFiles(List<File> inputFiles) throws LianosRenamerException {
        List<Path> videoFiles = new ArrayList<>();
        for (File f : inputFiles) {
            addAllFilesToList(f, videoFiles);
        }
        return videoFiles;
    }

    private static List<Path> addAllFilesToList(File inputFile, List<Path> pathList) throws LianosRenamerException {
        try {
            pathList.addAll(Files.walk(Paths.get(inputFile.getPath())).filter(FileHandler::isVideoFile).collect(Collectors.toList()));
        } catch (IOException e) {
            throw new LianosRenamerException("There was a problem while walking files.", e);
        }
        return pathList;
    }

    private static boolean isVideoFile(Path path) {
        return videoExtensions.contains(FileExtensionGetter.getFileExtension(path));
    }

}

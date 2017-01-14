package at.zierler.privat.lianosrenamer.service;

import at.zierler.privat.lianosrenamer.LianosRenamerException;

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

    List<String> videoExtensions = Arrays.asList("mp4", "mkv", "wmv", "flv");

    public List<Path> getListOfVideoFilesByListOfFiles(List<File> inputFiles) throws LianosRenamerException {
        List<Path> videoFiles = new ArrayList<>();
        for (File f : inputFiles) {
            List<Path> dirFiles;
            try {
                dirFiles = Files.walk(Paths.get(f.getPath())).filter(this::isVideoFile).collect(Collectors.toList());
            } catch (IOException e) {
                throw new LianosRenamerException("There was a problem while walking files.", e);
            }
            videoFiles.addAll(dirFiles);
        }
        return videoFiles;
    }

    private boolean isVideoFile(Path path) {
        String[] tmpSplit = path.getFileName().toString().split("\\.");
        String extension = tmpSplit[tmpSplit.length - 1];
        return videoExtensions.contains(extension);
    }

}

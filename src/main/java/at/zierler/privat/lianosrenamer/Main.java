package at.zierler.privat.lianosrenamer;

import at.zierler.privat.lianosrenamer.domain.FileExt;
import at.zierler.privat.lianosrenamer.handlers.ArgumentHandler;
import at.zierler.privat.lianosrenamer.handlers.VideoFileFinder;

import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        Stream<FileExt> videoFiles = new VideoFileFinder().apply(new ArgumentHandler().apply(args));
        if (videoFiles.count() < 1) {
            throw new IllegalArgumentException("With the provided directories and files, not one video file could be found");
        }


    }

}

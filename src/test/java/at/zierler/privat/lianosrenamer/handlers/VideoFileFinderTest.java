package at.zierler.privat.lianosrenamer.handlers;

import at.zierler.privat.lianosrenamer.FileTest;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class VideoFileFinderTest extends FileTest {


    @Test
    public void testVideoFileFinder() throws IOException {
        Path videoFile1 = Files.createFile(Paths.get(testFolder, "videoFile1.mp4"));
        Path videoFile2 = Files.createFile(Paths.get(testFolder, "videoFile2.mkv"));
        Path nonVideoFile1 = Files.createFile(Paths.get(testFolder, "file1.txt"));
        Path dir1 = Files.createDirectory(Paths.get(testFolder, "dir1"));
        Path dir2 = Files.createDirectory(Paths.get(testFolder, "dir2"));

        Path dir1VideoFile3 = Files.createFile(Paths.get(dir1.toString(), "videoFile3.flv"));
        Path dir1dir3 = Files.createDirectory(Paths.get(dir1.toString(), "dir3"));
        Path dir2VideoFile4 = Files.createFile(Paths.get(dir1.toString(), "videoFile1.avi"));

        Path dir1Dir3VideoFile5 = Files.createFile(Paths.get(dir1dir3.toString(), "videoFile5.wmv"));
        Files.createFile(Paths.get(dir1dir3.toString(), "file2.mp3"));

        Set<Path> result = new VideoFileFinder()
                .apply(Stream.of(videoFile1, videoFile1, videoFile2, nonVideoFile1, dir1, dir2))
                .collect(Collectors.toSet());

        assertThat(result.size(), is(5));
        assertThat(result, hasItem(videoFile1));
        assertThat(result, hasItem(videoFile2));
        assertThat(result, hasItem(dir1VideoFile3));
        assertThat(result, hasItem(dir2VideoFile4));
        assertThat(result, hasItem(dir1Dir3VideoFile5));
    }

}

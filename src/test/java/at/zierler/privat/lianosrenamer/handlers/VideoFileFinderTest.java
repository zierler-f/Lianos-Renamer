package at.zierler.privat.lianosrenamer.handlers;

import at.zierler.privat.lianosrenamer.FileTest;
import at.zierler.privat.lianosrenamer.domain.FileExt;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class VideoFileFinderTest extends FileTest {

    @Test
    public void testVideoFileFinder() throws IOException {
        List<File> input = new ArrayList<>();

        File videoFile1 = temporaryFolder.newFile("videoFile1.mp4");
        File videoFile2 = temporaryFolder.newFile("videoFile2.mkv");
        File nonVideoFile1 = temporaryFolder.newFile("file1.txt");
        File dir1 = temporaryFolder.newFolder("dir1");
        File dir2 = temporaryFolder.newFolder("dir2");

        File dir1VideoFile3 = new File(dir1, "videoFile3.flv");
        File dir1Dir3 = new File(dir1, "dir3");
        File dir2VideoFile4 = new File(dir2, "videoFile4.avi");

        assertTrue(dir1VideoFile3.createNewFile());
        assertTrue(dir1Dir3.mkdir());
        assertTrue(dir2VideoFile4.createNewFile());

        File dir1Dir3VideoFile5 = new File(dir1Dir3, "videoFile5.wmv");
        File dir1Dir3NonVideoFile2 = new File(dir1Dir3, "file2.mp3");

        assertTrue(dir1Dir3VideoFile5.createNewFile());
        assertTrue(dir1Dir3NonVideoFile2.createNewFile());

        input.add(videoFile1);
        input.add(videoFile1);
        input.add(videoFile2);
        input.add(nonVideoFile1);
        input.add(dir1);
        input.add(dir2);

        Set<FileExt> result = new VideoFileFinder(input.stream()).get().collect(Collectors.toSet());

        assertThat(result.size(), is(5));
        assertThat(result, hasItem(new FileExt(videoFile1.getAbsolutePath())));
        assertThat(result, hasItem(new FileExt(videoFile2.getAbsolutePath())));
        assertThat(result, hasItem(new FileExt(dir1VideoFile3.getAbsolutePath())));
        assertThat(result, hasItem(new FileExt(dir2VideoFile4.getAbsolutePath())));
        assertThat(result, hasItem(new FileExt(dir1Dir3VideoFile5.getAbsolutePath())));
    }

}

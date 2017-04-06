package at.zierler.privat.lianosrenamer.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Extension class for {@link Files}
 */

public class FilesExt {

    /**
     * Runs Files.walk method, but re-throws IOException as RuntimeException for easier handling in streams
     *
     * @param path the starting file
     * @return the {@link Stream} of {@link Path}
     */

    public static Stream<Path> walkSafe(Path path) {
        try {
            return Files.walk(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

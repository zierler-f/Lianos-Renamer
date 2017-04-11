package at.zierler.privat.lianosrenamer.handlers;

import at.zierler.privat.lianosrenamer.helpers.PathHelper;

import java.nio.file.Path;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class VideoFileFinder implements Function<Stream<Path>, Stream<Path>> {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * walk all provided files/directories, find all video-files and return them
     *
     * @return all video files in provided files/directories
     */

    @Override
    public Stream<Path> apply(Stream<Path> paths) {
        return paths
                .flatMap(path -> PathHelper.walkSafe(path)
                        .filter(PathHelper::isVideoFile)
                        .distinct());
    }

}

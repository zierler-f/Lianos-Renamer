package at.zierler.privat.lianosrenamer.service.arguments;

import at.zierler.privat.lianosrenamer.service.abstracts.Converter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ArgumentsToPathSetConverter implements Converter<String[], Set<Path>> {

    @Override
    public Set<Path> convert(String[] args) {
        Stream<String> argsStream = new ArgumentsToStringStreamConverter().convert(args);
        Stream<Path> pathsStream = convertStreamOfArgsToStreamOfPaths(argsStream);
        Set<Path> setOfPaths = convertStreamOfPathsToSet(pathsStream);
        return setOfPaths;
    }

    private Stream<Path> convertStreamOfArgsToStreamOfPaths(Stream<String> argsStream) {
        return argsStream.map(Paths::get);
    }

    private Set<Path> convertStreamOfPathsToSet(Stream<Path> pathsStream) {
        return pathsStream.collect(Collectors.toSet());
    }

}

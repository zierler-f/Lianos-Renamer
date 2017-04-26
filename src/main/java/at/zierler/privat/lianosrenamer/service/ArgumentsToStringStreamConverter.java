package at.zierler.privat.lianosrenamer.service;

import java.util.stream.Stream;

import static java.util.Arrays.stream;

public class ArgumentsToStringStreamConverter implements Converter<String[], Stream<String>> {

    @Override
    public Stream<String> convert(String[] args) {
        return stream(args);
    }
}

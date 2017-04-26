package at.zierler.privat.lianosrenamer.service.arguments;

import at.zierler.privat.lianosrenamer.service.abstracts.Converter;

import java.util.stream.Stream;

import static java.util.Arrays.stream;

class ArgumentsToStringStreamConverter implements Converter<String[], Stream<String>> {

    @Override
    public Stream<String> convert(String[] args) {
        return stream(args);
    }
}

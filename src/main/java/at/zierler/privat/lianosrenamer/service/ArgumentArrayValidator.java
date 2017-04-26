package at.zierler.privat.lianosrenamer.service;

import java.util.stream.Stream;

/**
 * Validates all arguments passed to the program as a String array
 */

public class ArgumentArrayValidator implements Validator<String[]> {

    @Override
    public boolean isValid(String[] args) {
        return atLeastOneArgumentProvided(args) && allArgsAreValid(args);
    }

    private boolean atLeastOneArgumentProvided(String[] args) {
        return args.length > 0;
    }

    private boolean allArgsAreValid(String[] args) {
        Stream<String> argsStream = new ArgumentsToStringStreamConverter().convert(args);
        Stream<Boolean> argsValidityStream = convertArgsStreamToBooleanStreamContainingArgsValidity(argsStream);
        boolean noInvalidArgs = onlyTrueValuesInArgsValidityBooleanStream(argsValidityStream);
        return noInvalidArgs;
    }

    private Stream<Boolean> convertArgsStreamToBooleanStreamContainingArgsValidity(Stream<String> argsStream) {
        SingleArgumentValidator validator = new SingleArgumentValidator();
        return argsStream.map(validator::isValid);
    }

    private boolean onlyTrueValuesInArgsValidityBooleanStream(Stream<Boolean> argsValidityStream) {
        return argsValidityStream.allMatch(b -> b.equals(true));
    }


}

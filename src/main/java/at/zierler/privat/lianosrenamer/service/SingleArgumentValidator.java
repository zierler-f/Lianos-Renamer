package at.zierler.privat.lianosrenamer.service;

/**
 * checks if a single argument (passed as a String) is valid
 */

public class SingleArgumentValidator implements Validator<String> {

    @Override
    public boolean isValid(String arg) {
        return argumentIsNotNull(arg) && trimmedArgumentIsNotEmpty(arg);
    }

    private boolean argumentIsNotNull(String arg) {
        return !argumentIsNull(arg);
    }

    private boolean argumentIsNull(String arg) {
        return arg == null;
    }

    private boolean trimmedArgumentIsNotEmpty(String arg) {
        return !trimmedArgumentIsEmpty(arg);
    }

    private boolean trimmedArgumentIsEmpty(String arg) {
        return getTrimmedArgument(arg).isEmpty();
    }

    private String getTrimmedArgument(String arg) {
        return arg.trim();
    }

}

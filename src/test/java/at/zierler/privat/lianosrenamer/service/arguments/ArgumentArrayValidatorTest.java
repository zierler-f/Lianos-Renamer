package at.zierler.privat.lianosrenamer.service.arguments;

import at.zierler.privat.lianosrenamer.abstracts.ValidatorTestAbstract;
import at.zierler.privat.lianosrenamer.service.arguments.ArgumentArrayValidator;
import org.junit.Before;
import org.junit.Test;

public class ArgumentArrayValidatorTest extends ValidatorTestAbstract<ArgumentArrayValidator> {

    @Before
    public void setup() {
        validator = new ArgumentArrayValidator();
    }

    @Test
    public void testIsValidIsFalseWithEmptyArray() {
        assertFalse(validator.isValid(new String[0]));
    }

    @Test
    public void testIsValidIsTrueWith1NonNullString() {
        String[] args = {"arg"};
        assertTrue(validator.isValid(args));
    }

    @Test
    public void testisValidIsFalseWithArrayWhichContainsOneValueWhichIsNull() {
        assertFalse(validator.isValid(new String[1]));
    }

    @Test
    public void testIsValidIsFalseWith1ValidAnd1NullArgument() {
        String[] args = {"arg", null};
        assertFalse(validator.isValid(args));
    }

    @Override
    public ArgumentArrayValidator getValidator() {
        return new ArgumentArrayValidator();
    }
}

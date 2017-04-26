package at.zierler.privat.lianosrenamer.service;

import at.zierler.privat.lianosrenamer.ValidatorTestAbstract;
import org.junit.Test;

public class SingleArgumentValidatorTest extends ValidatorTestAbstract<SingleArgumentValidator> {

    @Test
    public void testIsValidWithValidArgument() {
        assertTrue(validator.isValid("test"));
    }

    @Test
    public void testIsValidFailsWithNullArgument() {
        assertFalse(validator.isValid(null));
    }

    @Test
    public void testIsValidFailsWithEmptyArgument() {
        assertFalse(validator.isValid(""));
    }

    @Test
    public void testIsValidFailsWithArgumentWhichOnlyContainsSpaces() {
        assertFalse(validator.isValid("   "));
    }

    @Override
    public SingleArgumentValidator getValidator() {
        return new SingleArgumentValidator();
    }
}

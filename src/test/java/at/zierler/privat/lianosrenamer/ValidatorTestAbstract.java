package at.zierler.privat.lianosrenamer;

import at.zierler.privat.lianosrenamer.service.Validator;
import org.junit.Assert;

public abstract class ValidatorTestAbstract<T extends Validator> extends Assert implements ValidatorTest<T> {

    protected T validator = getValidator();

}

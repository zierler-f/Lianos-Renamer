package at.zierler.privat.lianosrenamer;

import at.zierler.privat.lianosrenamer.service.Validator;

public interface ValidatorTest<T extends Validator> {

    T getValidator();

}

package at.zierler.privat.lianosrenamer.abstracts;

import at.zierler.privat.lianosrenamer.service.abstracts.Validator;

public interface ValidatorTest<T extends Validator> {

    T getValidator();

}

package at.zierler.privat.lianosrenamer.service;

public interface Validator<T> {

    boolean isValid(T t);

}

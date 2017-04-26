package at.zierler.privat.lianosrenamer.service;

public interface Converter<T, U> {

    U convert(T t);

}

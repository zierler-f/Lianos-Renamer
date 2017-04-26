package at.zierler.privat.lianosrenamer.service.abstracts;

public interface Converter<T, U> {

    U convert(T t);

}

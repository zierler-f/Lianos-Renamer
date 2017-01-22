package at.zierler.privat.lianosrenamer;

import at.zierler.privat.lianosrenamer.exceptions.LianosRenamerException;
import at.zierler.privat.lianosrenamer.service.ArgsHandler;
import at.zierler.privat.lianosrenamer.service.FileRenamer;

public class Main {

    private Main() {
        throw new IllegalAccessError("Objects of the type Main cannot be created.");
    }

    public static void main(String[] args) throws LianosRenamerException {
        FileRenamer.renameFiles(ArgsHandler.getListOfFilesByArgs(args));
    }

}

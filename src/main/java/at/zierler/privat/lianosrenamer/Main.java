package at.zierler.privat.lianosrenamer;

import at.zierler.privat.lianosrenamer.exceptions.LianosRenamerException;
import at.zierler.privat.lianosrenamer.service.ArgsHandler;
import at.zierler.privat.lianosrenamer.service.FileRenamer;

public class Main {

    public static void main(String[] args) throws LianosRenamerException {
        new FileRenamer().renameFiles(new ArgsHandler().getListOfFilesByArgs(args));
    }

}

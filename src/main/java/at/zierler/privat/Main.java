package at.zierler.privat;

import at.zierler.privat.exceptions.LianosRenamerException;

/**
 * Created by florian on 2/8/16.
 */
public class Main {
    public static void main(String[] args) throws LianosRenamerException {
        System.out.println("[>>> - [[[ Welcome to Lianos Renamer ]]] - <<<]");
        if (args.length == 0){
            System.out.println("No files found to rename.");
            return;
        }
        for(String arg:args){
            LianosFile f = new LianosFile(arg);
            FileHandler fh = new FileHandler();
            fh.handle(f);
        }
        System.exit(0);
    }
}

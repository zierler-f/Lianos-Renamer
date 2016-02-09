package at.zierler.privat;

/**
 * Created by florian on 2/8/16.
 */
public class Main {
    public static void main(String[] args) throws LianosRenamerException {
        System.out.println("Welcome to Lianos Renamer!");
        if (args.length == 0){
            System.out.println("No files found to rename.");
            return;
        }
        for(String arg:args){
            LianosFile f = new LianosFile(arg);
            System.out.println(f.getType());
        }
    }
}

package at.zierler.privat.lianosrenamer.domain;

import java.io.File;
import java.nio.file.Path;

/**
 * Extension class for {@link File}
 */

public class FileExt extends File {

    private String extension;

    /**
     * creates new FileExt from pathname
     *
     * @param pathname path on file system
     */

    public FileExt(String pathname) {
        super(pathname);
    }

    /**
     * creates new FileExt from path
     *
     * @param path path on file system
     */

    public FileExt(Path path) {
        super(path.toString());
    }

    /**
     * checks if extension has already been set, sets it if not, and returns extension
     *
     * @return extension of file
     */

    public String getExtension() {
        if (extension == null) {
            extension = findExtension();
        }
        return extension;
    }

    /**
     * finds extension of file, if one exists
     *
     * @return file extension
     */

    private String findExtension() {
        if (this.isDirectory()) {
            return null;
        }
        String[] parts = this.getName().split("\\.");
        if (parts.length < 2) {
            return "";
        }
        return parts[parts.length - 1];
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FileExt)) return false;
        FileExt that = (FileExt) o;
        return super.equals(o) && this.getExtension().equals(that.getExtension());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}

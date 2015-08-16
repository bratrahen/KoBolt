package filesystem.local;

import filesystem.Pathname;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalPathname implements Pathname {
    protected final File file;

    public LocalPathname(File file) {
        this.file = file;
    }

    LocalPathname(String pathname) {
        file = new File(pathname);
    }

    @Override
    public String getBasename(){
        return file.getName();
    }

    @Override
    public void rename(String name) {
        File dst = new File(file.getParent() + File.separator + name);
        boolean isRenamed = file.renameTo(dst);
        if (!isRenamed)
            throw new RuntimeException("Failed to rename " + file.getAbsolutePath() + " to " + dst.getAbsolutePath());
    }

    @Override
    public boolean exists() {
        return file.exists();
    }

    @Override
    public void delete() throws IOException {
        Files.delete(Paths.get(file.getAbsolutePath()));
    }

    @Override
    public String relativize(Pathname other) {
        Path relativized = getPath(other).relativize(getPath(this));
        return convertToUnix(relativized.toString());
    }

    protected Path getPath(Pathname path){
        return Paths.get(path.getAbsolutePath());
    }

    @Override
    public String getAbsolutePath() {
        return convertToUnix(file.getAbsolutePath());
    }

    String convertToUnix(String path) {
        String unixPath = convertSeparatorToSlash(path);
        return removeLastSlash(unixPath);
    }

    private String convertSeparatorToSlash(String path) {
        switch (File.separator) {
            case "/":
                return path;
            case "\\":
                return path.replaceAll("\\\\", "/");
            default:
                return path.replaceAll(File.separator, "/");
        }
    }

    private String removeLastSlash(String path) {
        boolean isEndedWithSlash = path.lastIndexOf("/") + 1 == path.length();
        if (isEndedWithSlash)
            return path.substring(0, path.length() - 1);
        else
            return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocalPathname)) return false;

        LocalPathname that = (LocalPathname) o;

        return file.equals(that.file);
    }

    @Override
    public String toString() {
        return file.getAbsolutePath();
    }

    @Override
    public int hashCode() {
        return file.hashCode();
    }
}

package filesystem.local;

import filesystem.Directory;
import filesystem.FileSystem;
import filesystem.Pathname;

import java.io.IOException;

public class LocalFileSystem implements FileSystem {
    public String separator() {
        return null;
    }

    @Override
    public Pathname createPathname(String path) {
        return new LocalPathname("src/IntegrationTests/resources");
    }

    @Override
    public Directory getDirectory(String path) {
        return new LocalDirectory(path);
    }

    @Override
    public File getFile(String path) {
        return new LocalFile(path);
    }
}

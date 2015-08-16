package filesystem;

import filesystem.local.File;

public interface FileSystem {
    String separator();

    Pathname createPathname(String path);

    Directory getDirectory(String path);
    File getFile(String path);
}

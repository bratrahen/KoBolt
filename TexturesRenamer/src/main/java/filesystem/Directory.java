package filesystem;

import filesystem.local.File;

import java.io.IOException;
import java.util.Set;

/**
 * Created by rahen on 2015-08-14.
 */
public interface Directory extends Pathname {
    Set<Directory> getSubDirectories();

    Set<Directory> getLowestLevelSubDirectories();

    Set<File> getFiles();

    Set<File> getFiles(String endsWith);
}

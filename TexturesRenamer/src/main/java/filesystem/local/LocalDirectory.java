package filesystem.local;

import filesystem.Directory;

import java.util.HashSet;
import java.util.Set;

public class LocalDirectory extends LocalPathname implements Directory {
    LocalDirectory(String path) {
        this(new java.io.File(path));
    }

    LocalDirectory(java.io.File theDirectory) {
        super(theDirectory);

        if (file.exists() && !file.isDirectory())
            throw new RuntimeException(file.getAbsolutePath() + " is not a directory");
    }


    @Override
    public Set<filesystem.local.File> getFiles() {
        return getFiles("");
    }

    public Set<filesystem.local.File> getFiles(String endsWith) {
        Set<filesystem.local.File> result = new HashSet<>();
        for (java.io.File theFile : file.listFiles()){
            if (theFile.getName().endsWith(endsWith))
                result.add(new LocalFile(theFile));
        }

        return result;
    }

    public Set<Directory> getSubDirectories() {
        java.io.File[] directories = file.listFiles(java.io.File::isDirectory);

        Set<Directory> result = new HashSet<>();
        for (java.io.File theDirectory : directories){
            result.add(new LocalDirectory(theDirectory));
        }

        return result;
    }

    @Override
    public Set<Directory> getLowestLevelSubDirectories() {
        return getLowestLevelSubDirectories(this);
    }

    private Set<Directory> getLowestLevelSubDirectories(Directory directory) {
        HashSet<Directory> result = new HashSet<>();

        Set<Directory> subDirectories = directory.getSubDirectories();
        if (subDirectories.isEmpty()){
            result.add(directory);
            return result;
        } else {
            for (Directory theSubDirectory : subDirectories){
                result.addAll(getLowestLevelSubDirectories(theSubDirectory));
            }

            return result;
        }
    }
}

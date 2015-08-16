package filesystem.local;

import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LocalFile extends LocalPathname implements File{
    public LocalFile(String path) {
        this(new java.io.File(path));
    }

    public LocalFile(java.io.File file) {
        super(file);

        if (file.exists() && file.isDirectory())
            throw new RuntimeException(file.getAbsolutePath() + " is not a file");
    }

    @Override
    public String readAll() throws IOException {
        return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
    }

    @Override
    public void save(String data) throws IOException {
        FileUtils.writeStringToFile(file, data);
    }
}

package filesystem;

import java.io.IOException;

/**
 * Created by rahen on 2015-08-14.
 */
public interface Pathname {
    String getAbsolutePath();
    String getBasename();
    void rename(String name);
    boolean exists();
    void delete() throws IOException;

    String relativize(Pathname root);
}

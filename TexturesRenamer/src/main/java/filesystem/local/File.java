package filesystem.local;

import filesystem.Pathname;

import java.io.IOException;

/**
 * Created by rahen on 2015-08-14.
 */
public interface File extends Pathname{
    String readAll() throws IOException;
    void save(String data) throws IOException;
}

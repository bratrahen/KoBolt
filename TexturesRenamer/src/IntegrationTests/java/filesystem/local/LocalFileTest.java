package filesystem.local;

import org.junit.Test;

import java.nio.file.Files;

import static org.junit.Assert.*;

/**
 * Created by rahen on 2015-08-15.
 */
public class LocalFileTest {

    @Test
    public void testReadAll() throws Exception {
        String expected =
                "file_1_line_1\n" +
                "file_1_line_2\n" +
                "file_1_line_3";

        LocalFile file = new LocalFile("src/IntegrationTests/resources/root_1/models/game_1/model_1/lod0/file1.txt");
        assertEquals(expected, file.readAll());
    }

    @Test
    public void testSaveAndDelete() throws Exception {
        String data =
                "line_1\n" +
                "line2\n" +
                "line3\n";

        LocalFile file = new LocalFile("src/IntegrationTests/resources/tmp.txt");

        assertFalse(file.exists());

        file.save(data);
        assertTrue(file.exists());
        assertEquals(data, file.readAll());

        file.delete();
        assertFalse(file.exists());
    }
}
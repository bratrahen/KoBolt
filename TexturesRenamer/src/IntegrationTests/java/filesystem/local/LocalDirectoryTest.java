package filesystem.local;

import filesystem.Directory;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by rahen on 2015-08-14.
 */
public class LocalDirectoryTest {
    @Test
    public void testGeSubDirectories() throws Exception {
        Set<Directory> expected = new HashSet<>(Arrays.asList(
                new LocalDirectory("src/IntegrationTests/resources/root_1/models/game_1/model_1/"),
                new LocalDirectory("src/IntegrationTests/resources/root_1/models/game_1/model_2/"),
                new LocalDirectory("src/IntegrationTests/resources/root_1/models/game_1/model_3/")));

        Directory resourcesDirectory = new LocalDirectory("src/IntegrationTests/resources/root_1/models/game_1/");
        assertEquals(expected, resourcesDirectory.getSubDirectories());
    }

    @Test
    public void testGetLowestLevelSubDirectories() throws IOException {
        Set<Directory> expected = new HashSet<>(Arrays.asList(
                new LocalDirectory("src/IntegrationTests/resources/root_1/models/game_1/model_1/lod0"),
                new LocalDirectory("src/IntegrationTests/resources/root_1/models/game_1/model_1/lod1"),
                new LocalDirectory("src/IntegrationTests/resources/root_1/models/game_1/model_2"),
                new LocalDirectory("src/IntegrationTests/resources/root_1/models/game_1/model_3")));

        Directory directory = new LocalDirectory("src/IntegrationTests/resources/root_1/models/game_1/");
        assertEquals(expected, directory.getLowestLevelSubDirectories());
    }

    @Test
    public void testGetFilesAll(){
        Set<File> expected = new HashSet<>(Arrays.asList(
                new LocalFile("src/IntegrationTests/resources/root_1/models/game_1/model_1/lod0/file1.txt"),
                new LocalFile("src/IntegrationTests/resources/root_1/models/game_1/model_1/lod0/file2.txt"),
                new LocalFile("src/IntegrationTests/resources/root_1/models/game_1/model_1/lod0/file.jpg")));

        Directory directory = new LocalDirectory("src/IntegrationTests/resources/root_1/models/game_1/model_1/lod0");
        assertEquals(expected, directory.getFiles());
    }

    @Test
    public void testGetFilesTxt(){
        Set<File> expected = new HashSet<>(Arrays.asList(
                new LocalFile("src/IntegrationTests/resources/root_1/models/game_1/model_1/lod0/file1.txt"),
                new LocalFile("src/IntegrationTests/resources/root_1/models/game_1/model_1/lod0/file2.txt")));

        Directory directory = new LocalDirectory("src/IntegrationTests/resources/root_1/models/game_1/model_1/lod0");
        assertEquals(expected, directory.getFiles(".txt"));
    }

    @Test
    public void testConvertToUnix() {
        assertEquals("C:/programs/games/myGame", new LocalPathname("").convertToUnix("C:\\programs\\games\\myGame\\"));
        assertEquals("C:/programs/games/myGame/game.exe", new LocalPathname("").convertToUnix("C:\\programs\\games\\myGame\\game.exe"));
    }
}
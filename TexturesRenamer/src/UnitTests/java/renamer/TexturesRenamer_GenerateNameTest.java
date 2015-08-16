package renamer;

import filesystem.Directory;
import filesystem.local.File;
import filesystem.local.LocalFileSystem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by rahen on 2015-08-14.
 */
@RunWith(Parameterized.class)
public class TexturesRenamer_GenerateNameTest {
    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][]{
                        {"/resources/models/game"},
                        {"/resources/models/game/"},
                        {"\\resources\\models\\game"},
                        {"\\resources\\models\\game\\"}
                });
    }

    private final String root;

    public TexturesRenamer_GenerateNameTest(String root) {
        this.root = root;
    }

    @Test
    public void testGenerateName() throws IOException {
        LocalFileSystem fileSystem = new LocalFileSystem();
        Directory root = fileSystem.getDirectory(this.root);
        File file = fileSystem.getFile("/resources/models/game/faction/model/texture.ext");
        assertEquals("for root = " + this.root, "faction_model_texture.ext", TexturesRenamer.generateName(root, file));
    }
}
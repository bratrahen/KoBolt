package renamer;

import filesystem.Directory;
import filesystem.FileSystem;
import filesystem.Pathname;
import filesystem.local.File;
import filesystem.local.LocalFileSystem;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TexturesRenamer {
    private final Set<Directory> roots = new HashSet<Directory>();

    public TexturesRenamer(String[] roots, FileSystem fileSystem) throws IOException {
        for (String theRoot : roots) {
            this.roots.add(fileSystem.getDirectory(theRoot));
        }
    }

    public static void main(String[] args) {
        try {
            TexturesRenamer renamer = new TexturesRenamer(args, new LocalFileSystem());
            renamer.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void run() throws IOException {
        System.out.println("Starting...");
        for (Directory theRoot : roots) {
            for (Directory theModel : theRoot.getLowestLevelSubDirectories()) {
                HashMap<String, String> oldNameToNewName = new HashMap<>();
                for (File theTexture : theModel.getFiles(".jpg")) {
                    String newTextureName = generateName(theRoot, theTexture);
                    oldNameToNewName.put(theTexture.getBasename(), newTextureName);
                    theTexture.rename(newTextureName);
                }

                for (File theMaterial : theModel.getFiles(".mtl")) {
                    String materialData = theMaterial.readAll();
                    materialData = materialData.replaceAll("\\.bmp\\b", "\\.jpg");

                    for (String oldName : oldNameToNewName.keySet()) {
                        String newName = oldNameToNewName.get(oldName);
                        materialData = materialData.replaceAll("\\b" + oldName + "\\b", newName);
                    }
                    theMaterial.save(materialData);
                }
            }
        }
        System.out.println("Finished");
    }

    static String generateName(Pathname root, File file) {
        String relativizedPath = file.relativize(root);
        String newFileName = relativizedPath.replaceAll("/| ", "_");
        return newFileName;
    }
}

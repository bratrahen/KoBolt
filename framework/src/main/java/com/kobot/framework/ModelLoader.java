package com.kobot.framework;

import com.threed.jpct.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FilenameFilter;

public class ModelLoader {

    private final String resourcesDir;
    private String subPath;

    public ModelLoader(@NotNull String resourcesDir) {
        this.resourcesDir = resourcesDir;
    }

    public Object3D load(@NotNull String name, float scale) {
        return load(name, name, scale);
    }

    public Object3D load(@NotNull String subPath, @NotNull String name, float scale) {
        this.subPath = subPath;

        loadTextures();
        return loadModel(name, scale);
    }

    private String getModelDir(){
        return resourcesDir + File.separator + subPath;
    }

    private void loadTextures() {
        File folder = new File(getModelDir());
        String[] textures = folder.list(getJpgFilter());

        if (textures == null) {
            return;
        }

        for (int i = 0; i < textures.length; ++i) {
            TextureManager.getInstance().addTexture(textures[i], new Texture(folder + File.separator + textures[i]));
        }
    }

    private Object3D    loadModel(@NotNull String modelName, float scale) {
        final String objectFilePath = getModelDir() + File.separator + modelName + ".obj";
        final String materialFilePath = getModelDir() + File.separator + modelName + ".mtl";

        Object3D[] parts = Loader.loadOBJ(objectFilePath, materialFilePath, scale);
        Object3D model = new Object3D(0);
        for (int i = 0; i < parts.length; i++) {
            parts[i].setCenter(SimpleVector.ORIGIN);
            parts[i].rotateX((float) (-.5 * Math.PI));
            parts[i].rotateMesh();
            parts[i].setRotationMatrix(new Matrix());
            model = Object3D.mergeObjects(model, parts[i]);
            model.build();
        }
        return model;
    }

    private FilenameFilter getJpgFilter() {
        return new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".jpg");
            }
        };
    }
}

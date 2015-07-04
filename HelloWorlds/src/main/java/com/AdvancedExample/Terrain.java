package com.AdvancedExample;

import com.kobot.objects.StaticObject;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;

import java.io.Serializable;

public class Terrain extends StaticObject implements Serializable {
    public Terrain(String path) {
        setModel(Primitives.getPlane(20, 30));

        TextureManager.getInstance().addTexture("grass", new Texture(path + "example/GrassSample2.jpg"));
        getModel().setTexture("grass");

        getModel().rotateX((float) Math.PI / 2f);
        getModel().setSpecularLighting(true);
        getModel().setCollisionMode(Object3D.COLLISION_CHECK_OTHERS);

        // Deform the plane
        getModel().getMesh().setVertexController(new Mod(), false);
        getModel().getMesh().applyVertexController();
        getModel().getMesh().removeVertexController();

        getModel().build();
        getModel().compileAndStrip();
    }
}
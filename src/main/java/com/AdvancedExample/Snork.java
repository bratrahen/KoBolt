package com.AdvancedExample;

import com.kobot.objects.AnimatedObject;
import com.threed.jpct.*;

import java.io.Serializable;

public class Snork extends AnimatedObject implements Serializable {
    float ind = 0;

    public Snork(String path) {
        setModel(Loader.loadMD2(path + "example/snork.md2", 0.8f));

        TextureManager.getInstance().addTexture("disco", new Texture(path + "example/disco.jpg"));
        getModel().setTexture("disco");
        getModel().build();
        getModel().compile(true, true, true, false, 2000);

        getModel().translate(0, -25, -50);
        getModel().setCollisionMode(Object3D.COLLISION_CHECK_SELF);
    }


    @Override
    public void animate(long ticks) {
        if (ticks > 0) {
            float ft = (float) ticks;
            ind += 0.02f * ft;
            if (ind > 1) {
                ind -= 1;
            }
            getModel().animate(ind, 2);
            getModel().rotateY(-0.02f * ft);
            getModel().translate(0, -50, 0);
            SimpleVector dir = getModel().getXAxis();
            dir.scalarMul(ft);
            dir = getModel().checkForCollisionEllipsoid(dir, new SimpleVector(5, 20, 5), 5);
            getModel().translate(dir);
            dir = getModel().checkForCollisionEllipsoid(new SimpleVector(0, 100, 0), new SimpleVector(5, 20, 5), 1);
            getModel().translate(dir);
        }
    }
}
package com.AdvancedExample;

import com.kobot.common.ModelLoader;
import com.kobot.objects.StaticObject;

import java.io.Serializable;

public class DerelictShip extends StaticObject implements Serializable{
    final float SCALE = 2;

    public DerelictShip(String path) {
        setModel(new ModelLoader(path).load("AdvancedSupportFrigate", SCALE));

        getModel().translate(200, 200, 0);
        getModel().rotateX(-(float) Math.PI / 5f);
    }
}
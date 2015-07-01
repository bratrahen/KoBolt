package com.kobot.framework.objects.graphic.console;

import com.kobot.framework.objects.graphic.common.Display;
import com.kobot.framework.objects.physics.GameObject;

import java.util.ArrayList;
import java.util.Collection;

public class ConsoleDisplay implements Display {
    Collection<GameObject> objects = new ArrayList<GameObject>();

    public void add(GameObject object) {
        objects.add(object);
    }

    public void render() {
        for (GameObject object : objects) {
            System.out.println(object.getClass().getSimpleName() + "@" + object.hashCode() + " = " + object.getPosition());
        }
    }
}

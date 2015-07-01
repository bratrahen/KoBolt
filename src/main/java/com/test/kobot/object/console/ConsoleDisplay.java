package com.test.kobot.object.console;
import com.test.kobot.Display;
import com.test.kobot.object.GameObject;

import java.util.ArrayList;
import java.util.Collection;

public class ConsoleDisplay implements Display {
    Collection<GameObject> objects = new ArrayList<GameObject>();

    public void add(GameObject object) {
        objects.add(object);
    }

    public void render() {
        for (GameObject object : objects){
            System.out.println( object.getClass().getSimpleName() + "@" + object.hashCode() + " = " + object.getPosition());
        }
    }
}

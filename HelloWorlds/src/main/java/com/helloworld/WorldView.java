package com.helloworld;

import com.kobot.objects.GameObject;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.World;

public abstract class WorldView {
    private final World world = new World();

    public World getWorld() {
        return world;
    }

    protected abstract void render(FrameBuffer buffer);

    public void addObject(GameObject object) {

    }
}

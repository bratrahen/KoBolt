package com.helloworld;

import com.AdvancedExample.*;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Lights;
import com.threed.jpct.Projector;
import com.threed.jpct.util.Light;
import com.threed.jpct.util.ShadowHelper;

public class AdvancedExampleView extends WorldView {
    private static final String FILE_PATH = "F:/1.ProgramowanieProjekty/HelloJpct/src/main/resources/models/";

    private final Sky sky;

    private final Terrain terrain = new Terrain(FILE_PATH);
    private final Rock rock = new Rock(FILE_PATH);
    private final Snork snork = new Snork(FILE_PATH);
    private final DerelictShip ship = new DerelictShip(FILE_PATH);


    private Light sun = new Light(getWorld());
    private ShadowHelper sh = null;
    private Projector projector = null;

    public AdvancedExampleView() {
        getWorld().setAmbientLight(30, 30, 30);
        getWorld().getLights().setRGBScale(Lights.RGB_SCALE_2X);

        sun.setIntensity(250, 250, 250);
        sun.setAttenuation(800);

        terrain.addToLocation(getWorld());
        rock.addToLocation(getWorld());
        snork.addToLocation(getWorld());
        ship.addToLocation(getWorld());

        sky = new Sky(FILE_PATH, terrain.getPosition());
    }

    @Override
    protected void render(FrameBuffer buffer) {
        sky.render(buffer);
        getWorld().renderScene(buffer);
        getWorld().draw(buffer);
    }
}

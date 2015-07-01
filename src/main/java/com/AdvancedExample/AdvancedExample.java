package com.AdvancedExample;

import java.awt.*;

import com.kobot.common.Ticker;
import com.threed.jpct.*;
import com.threed.jpct.util.*;

public class AdvancedExample implements IPaintListener {
    private static final String FILE_PATH = "";

    private static final long serialVersionUID = 1L;

    private final Terrain terrain = new Terrain(FILE_PATH);
    private final Rock rock = new Rock(FILE_PATH);
    private final Snork snork = new Snork(FILE_PATH);
    private final DerelictShip ship = new DerelictShip(FILE_PATH);

    private GhostControls controls;

    private FrameBuffer buffer = null;

    private World world = null;
    private Sky sky;

    private Light sun = null;
    private ShadowHelper sh = null;
    private Projector projector = null;

    private Ticker ticker = new Ticker(15);

    public static void main(String[] args) throws Exception {
        Config.glVerbose = true;
        AdvancedExample cd = new AdvancedExample();
        cd.init();
        cd.gameLoop();
    }

    public AdvancedExample() {
        Config.glAvoidTextureCopies = true;
        Config.maxPolysVisible = 1000;
        Config.glColorDepth = 24;
        Config.glFullscreen = false;
        Config.farPlane = 4000;
        Config.glShadowZBias = 0.8f;
        Config.lightMul = 1;
        Config.collideOffset = 500;
        Config.glTrilinear = true;
    }

    public void finishedPainting() {

    }

    public void startPainting() {
    }

    private void init() throws Exception {
        // Initialize frame buffer
        buffer = new FrameBuffer(800, 600, FrameBuffer.SAMPLINGMODE_NORMAL);
        buffer.disableRenderer(IRenderer.RENDERER_SOFTWARE);
        buffer.enableRenderer(IRenderer.RENDERER_OPENGL, IRenderer.MODE_OPENGL);
        buffer.setPaintListener(this);

        // Initialize worlds
        world = new World();
        world.setAmbientLight(30, 30, 30);
        world.getLights().setRGBScale(Lights.RGB_SCALE_2X);

        // Initialize mappers
        controls = new GhostControls(world, buffer);

        // Initialize shadow helper


        createSun();

        // Setup dynamic light source



        // Load/create and setup objects
        terrain.addToLocation(world);
        rock.addToLocation(world);
        snork.addToLocation(world);
        ship.addToLocation(world);

        sky = new Sky(FILE_PATH, terrain.getPosition());

        sh.addCaster(snork.getModel());
        sh.addCaster(rock.getModel());
        sh.addCaster(ship.getModel());

        sh.addReceiver(terrain.getModel());
    }

    private void gameLoop() throws Exception {

        SimpleVector offset = new SimpleVector(1, 0, -1).normalize();
        while (controls.isDoLoop()) {

            long ticks = ticker.getTicks();
            if (ticks > 0) {
                // animate the snork and the dome

                snork.animate(ticks);

                // move the camera

                controls.pollControls();
                controls.move(ticks);

                // Update the skydome
                sky.update(ticks, world.getCamera().getBack().cloneMatrix());
            }

            moveSun(snork.getPosition(), ticks, offset, terrain.getPosition());
            // drawScene the projector for the shadow map

            // render the scene

            buffer.clear();
            sky.render(buffer);
            drawScene();
            buffer.update();
            buffer.displayGLOnly();

            // print out the fps to the console


        }

        // exit...

        System.exit(0);
    }

    private void drawScene() {
        sh.drawScene();
    }

    private void moveSun(SimpleVector pos, long ticks, SimpleVector offset, SimpleVector centerOfTheWorld) {
        if (ticks > 0){
            offset.rotateY(0.007f * ticks);
        }
        projector.lookAt(centerOfTheWorld);
        projector.setPosition(pos);
        projector.moveCamera(new SimpleVector(0, -1, 0), 200);
        projector.moveCamera(offset, 215);
        sun.setPosition(projector.getPosition());
        // drawScene the shadow map
        sh.updateShadowMap();
    }

    private void createSun() {
        sun = new Light(world);
        sun.setIntensity(250, 250, 250);
        sun.setAttenuation(800);

        projector = new Projector();
        projector.setFOV(1.5f);
        projector.setYFOV(1.5f);

        sh = new ShadowHelper(world, buffer, projector, 2048);
        sh.setCullingMode(false);
        sh.setAmbientLight(new Color(30, 30, 30));
        sh.setLightMode(true);
        sh.setBorder(1);
    }
}
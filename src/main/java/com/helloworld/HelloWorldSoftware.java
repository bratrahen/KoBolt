package com.helloworld;

import com.AdvancedExample.GhostControls;
import com.kobot.common.Ticker;
import com.threed.jpct.*;
import com.kobot.common.ModelLoader;

public class HelloWorldSoftware implements IPaintListener{
//    private static final String FILE_PATH = File.separator + "models";
    private static final String FILE_PATH = "F:\\1.ProgramowanieProjekty\\HelloJpct\\src\\main\\resources\\models";
    private static final String THING_NAME = "AdvancedSupportFrigate";
    private int thingScale = 1;//end
    private World world;
    private FrameBuffer buffer;
    private Object3D thing;
    private final GhostControls controls;

    public HelloWorldSoftware() throws Exception {
        Config.glVerbose = true;
        Config.glAvoidTextureCopies = true;
        Config.maxPolysVisible = 1000;
        Config.glColorDepth = 24;
        Config.glFullscreen = false;
        Config.farPlane = 4000;
        Config.glShadowZBias = 0.8f;
        Config.lightMul = 1;
        Config.collideOffset = 500;
        Config.glTrilinear = true;


        buffer = new FrameBuffer(800, 600, FrameBuffer.SAMPLINGMODE_NORMAL);
        buffer.disableRenderer(IRenderer.RENDERER_SOFTWARE);
        buffer.enableRenderer(IRenderer.RENDERER_OPENGL, IRenderer.MODE_OPENGL);
        buffer.setPaintListener(this);

        world = new World();
        world.setAmbientLight(150, 150, 150);

        controls = new GhostControls(world, buffer);

        thing = new ModelLoader(FILE_PATH).load(THING_NAME, thingScale);
        thing.build();
        world.addObject(thing);

        world.getCamera().setPosition(0, 0, -600);
        world.getCamera().lookAt(thing.getTransformedCenter());
    }

    public static void main(String[] args) throws Exception {
        new HelloWorldSoftware().loop();
    }

    private void loop() throws Exception {

        Ticker ticker = new Ticker(15);
        while (controls.isDoLoop()) {


            long ticks = ticker.getTicks();
            if (ticks > 0){
                controls.pollControls();
                controls.move(ticks);
            }

            buffer.clear(java.awt.Color.BLUE);
            world.renderScene(buffer);
            world.draw(buffer);
            buffer.update();
            buffer.displayGLOnly();


            Thread.sleep(10);
        }
        buffer.disableRenderer(IRenderer.RENDERER_OPENGL);
        buffer.dispose();
        System.exit(0);
    }

    public void startPainting() {

    }

    public void finishedPainting() {

    }
}
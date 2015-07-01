package com.test.kobot.object.jpct;

import com.AdvancedExample.GhostControls;
import com.test.kobot.Display;
import com.test.kobot.object.GameObject;
import com.threed.jpct.*;
import com.threed.jpct.util.Light;
import org.jetbrains.annotations.NotNull;

public class JpctDisplay implements IPaintListener, Display {
    static {
        configJpct();
    }

    private final FrameBuffer buffer;
    private final World world;
    private final GhostControls controls;

    public JpctDisplay() {
        buffer = createFrameBuffer();
        world = createWorld();
        controls = new GhostControls(world, buffer);
    }

    private FrameBuffer createFrameBuffer() {
        FrameBuffer result = new FrameBuffer(800, 600, FrameBuffer.SAMPLINGMODE_NORMAL);
        result.disableRenderer(IRenderer.RENDERER_SOFTWARE);
        result.enableRenderer(IRenderer.RENDERER_OPENGL, IRenderer.MODE_OPENGL);
        result.setPaintListener(this);

        return result;
    }

    private World createWorld() {
        World result = new World();
        result.setAmbientLight(30, 30, 30);
        result.getLights().setRGBScale(Lights.RGB_SCALE_2X);

        Light sun = new Light(result);
        sun.setIntensity(250, 250, 250);
        sun.setAttenuation(800);
        sun.setPosition(new SimpleVector(0, -100, 0));

        return result;
    }

    private static void configJpct() {
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
    }

    public void add(@NotNull GameObject object) {
        if (object.getMotionState() instanceof JpctMotionState) {
            JpctMotionState jpctMotionState = (JpctMotionState) object.getMotionState();
            world.addObject(jpctMotionState.getObject3D());
        } else {
            throw new IllegalArgumentException(JpctDisplay.class.getSimpleName() + " requires " + JpctMotionState.class.getSimpleName());
        }
    }

    public void render() {
        controls.pollControls();
        controls.move(1);

        buffer.clear();
        world.renderScene(buffer);
        world.draw(buffer);
        buffer.update();
        buffer.displayGLOnly();
    }

    public void startPainting() {

    }

    public void finishedPainting() {

    }
}

package com.kobot.framework.objects.graphic.jpct;

import com.kobot.framework.objects.graphic.common.Display;
import com.kobot.framework.objects.physics.GameObject;
import com.threed.jpct.*;
import com.threed.jpct.util.Light;
import org.jetbrains.annotations.NotNull;

public class JpctDisplay implements Display {
    static {
        configJpct();
    }

    private final FrameBuffer buffer;
    private final World world;

    public JpctDisplay() {
        buffer = createFrameBuffer();
        world = createWorld();
    }

    private FrameBuffer createFrameBuffer() {
        FrameBuffer result = new FrameBuffer(800, 600, FrameBuffer.SAMPLINGMODE_NORMAL);
        result.disableRenderer(IRenderer.RENDERER_SOFTWARE);
        result.enableRenderer(IRenderer.RENDERER_OPENGL, IRenderer.MODE_OPENGL);

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
        buffer.clear();
        world.renderScene(buffer);
        world.draw(buffer);
        buffer.update();
        buffer.displayGLOnly();
    }
}

package com.helloworld;

import com.AdvancedExample.GhostControls;
import com.kobot.common.Ticker;
import com.threed.jpct.*;

public abstract class Game implements IPaintListener {
    private final FrameBuffer buffer;
    private GhostControls controls;
    private final Ticker ticker = new Ticker(15);

    protected abstract WorldView getWorldView();
    protected abstract WorldModel getWorldModel();

    public Game() {
        configJpct();
        this.buffer = createFrameBuffer();
    }

    public void initControls(){
        controls = new GhostControls(getWorldView().getWorld(), buffer);
    }

    private void configJpct(){
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

    private FrameBuffer createFrameBuffer() {
        FrameBuffer buffer = new FrameBuffer(800, 600, FrameBuffer.SAMPLINGMODE_NORMAL);
        buffer.disableRenderer(IRenderer.RENDERER_SOFTWARE);
        buffer.enableRenderer(IRenderer.RENDERER_OPENGL, IRenderer.MODE_OPENGL);
        buffer.setPaintListener(this);

        return buffer;
    }

    protected final void gameLoop() throws Exception {
        while (controls.isDoLoop()) {
            long ticks = ticker.getTicks();
            if (ticks > 0) {
                controls.pollControls();
                controls.move(ticks);
            }

            getWorldModel().update(ticks);

            buffer.clear();
            getWorldView().render(buffer);
            buffer.update();
            buffer.displayGLOnly();
        }
    }

    public void startPainting() {

    }

    public void finishedPainting() {

    }
}

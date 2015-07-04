package com.helloworld;

import com.AdvancedExample.GhostControls;
import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.Transform;
import com.kobot.common.Ticker;
import com.kobot.objects.Cuboid;
import com.threed.jpct.Config;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.IPaintListener;
import com.threed.jpct.IRenderer;

public class SphereHelloWorld implements IPaintListener {
    private static Sphere sphere;
    Location location = new Location();

    private final FrameBuffer buffer;
    private GhostControls controls;

    private static final int MILISEC_PER_TIC = 1;
    private final Ticker ticker = new Ticker(MILISEC_PER_TIC);

    public SphereHelloWorld() {
        buffer = createFrameBuffer();
        controls = new GhostControls(location.getView(), buffer);
    }

    public static void main(String[] args) throws Exception {
        SphereHelloWorld game = new SphereHelloWorld();
        game.configJpct();
        sphere = new Sphere(1.0f, 5);
        game.location.addObject(sphere);
        game.location.addObject(new Cuboid(0.0f, 50.0f));

        game.gameLoop();
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
            if (ticks > 0){
                float milliseconds = ticks * MILISEC_PER_TIC;
                float seconds = milliseconds / 1000.0f;
                location.getModel().stepSimulation(seconds, 10);
                CollisionObject obj = sphere.getModel();
                RigidBody body = RigidBody.upcast(obj);
                if (body != null && body.getMotionState() != null) {
                    Transform trans = new Transform();
                    body.getMotionState().getWorldTransform(trans);
                    System.out.printf("world pos = %f,%f,%f\n", trans.origin.x,
                            trans.origin.y, trans.origin.z);

                    controls.pollControls();
                    controls.move(ticks);
                }

                buffer.clear();
                location.getView().renderScene(buffer);
                location.getView().draw(buffer);
                buffer.update();
                buffer.displayGLOnly();
            }
        }
    }

    public void startPainting() {

    }

    public void finishedPainting() {

    }
}

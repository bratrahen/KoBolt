package com.kobot.framework;

import com.threed.jpct.*;
import com.threed.jpct.util.KeyMapper;
import com.threed.jpct.util.KeyState;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.Display;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class GhostControls implements Serializable {
    public static final double X_AXIS_UPPER_BOUND = Math.PI / 4.2;
    public static final double X_AXIS_LOWER_BOUND = -Math.PI / 4.2;
    KeyMapper keyMapper;
    MouseMapper mouseMapper;
    boolean forward = false;
    boolean backward = false;
    boolean up = false;
    boolean down = false;
    boolean left = false;
    boolean right = false;
    boolean doLoop = true;
    private float xAngle;
    private World world;

    public GhostControls(@NotNull World world, @NotNull FrameBuffer buffer) {
        this.world = world;
        keyMapper = new KeyMapper();
        mouseMapper = new MouseMapper(buffer);
        mouseMapper.hide();

        initCameraPosition(world);
    }

    private void initCameraPosition(@NotNull World world) {
        world.getCamera().moveCamera(Camera.CAMERA_MOVEOUT, 150);
        world.getCamera().moveCamera(Camera.CAMERA_MOVEUP, 100);
        world.getCamera().lookAt(new SimpleVector());
        world.getCamera().setFOV(1.5f);
    }

    public boolean isDoLoop() {
        return doLoop;
    }

    public void pollControls() {

        KeyState ks = null;
        while ((ks = keyMapper.poll()) != KeyState.NONE) {
            if (ks.getKeyCode() == KeyEvent.VK_ESCAPE) {
                doLoop = false;
            }

            if (ks.getKeyCode() == KeyEvent.VK_W) {
                forward = ks.getState();
            }

            if (ks.getKeyCode() == KeyEvent.VK_S) {
                backward = ks.getState();
            }

            if (ks.getKeyCode() == KeyEvent.VK_A) {
                left = ks.getState();
            }

            if (ks.getKeyCode() == KeyEvent.VK_D) {
                right = ks.getState();
            }

            if (ks.getKeyCode() == KeyEvent.VK_PAGE_UP) {
                up = ks.getState();
            }

            if (ks.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
                down = ks.getState();
            }
        }

        if (Display.isCloseRequested()) {
            doLoop = false;
        }
    }

    public void move(long ticks) {

        if (ticks == 0) {
            return;
        }

        translateCamera(ticks);
        rotateCamera(ticks);
    }

    private void translateCamera(long ticks) {
        SimpleVector ellipsoid = new SimpleVector(5, 5, 5);

        if (isForward()) {
            world.checkCameraCollisionEllipsoid(Camera.CAMERA_MOVEIN,
                    ellipsoid, ticks, 5);
        }

        if (isBackward()) {
            world.checkCameraCollisionEllipsoid(Camera.CAMERA_MOVEOUT,
                    ellipsoid, ticks, 5);
        }

        if (isLeft()) {
            world.checkCameraCollisionEllipsoid(Camera.CAMERA_MOVELEFT,
                    ellipsoid, ticks, 5);
        }

        if (isRight()) {
            world.checkCameraCollisionEllipsoid(Camera.CAMERA_MOVERIGHT,
                    ellipsoid, ticks, 5);
        }

        if (isUp()) {
            world.checkCameraCollisionEllipsoid(Camera.CAMERA_MOVEUP,
                    ellipsoid, ticks, 5);
        }

        if (isDown()) {
            world.checkCameraCollisionEllipsoid(Camera.CAMERA_MOVEDOWN,
                    ellipsoid, ticks, 5);
        }
    }

    private void rotateCamera(long ticks) {
        Matrix rot = world.getCamera().getBack();
        int dx = getMouseMapper().getDeltaX();
        int dy = getMouseMapper().getDeltaY();

        float ts = 0.2f * ticks;
        float tsy = ts;

        if (dx != 0) {
            ts = dx / 500f;
        }
        if (dy != 0) {
            tsy = dy / 500f;
        }

        if (dx != 0) {
            rot.rotateAxis(rot.getYAxis(), ts);
        }

        if ((dy > 0 && xAngle < X_AXIS_UPPER_BOUND)
                || (dy < 0 && xAngle > X_AXIS_LOWER_BOUND)) {
            rot.rotateX(tsy);
            xAngle += tsy;
        }
    }

    public boolean isForward() {
        return forward;
    }

    public boolean isBackward() {
        return backward;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public MouseMapper getMouseMapper() {
        return mouseMapper;
    }
}
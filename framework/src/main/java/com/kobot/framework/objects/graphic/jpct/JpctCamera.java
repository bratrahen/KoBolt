package com.kobot.framework.objects.graphic.jpct;

import com.kobot.framework.controls.camera.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Matrix;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

public class JpctCamera implements Camera {
    private final SimpleVector COLLISION_ELLIPSOID = new SimpleVector(5, 5, 5);
    private final World world;
    private final FrameBuffer buffer;

    /**
     * @param world world instance the camera belongs to
     */
    public JpctCamera(World world, FrameBuffer buffer) {
        this.world = world;
        this.buffer = buffer;

        initCameraPosition();
    }

    private void initCameraPosition() {
        world.getCamera().moveCamera(com.threed.jpct.Camera.CAMERA_MOVEOUT, 150);
        world.getCamera().moveCamera(com.threed.jpct.Camera.CAMERA_MOVEUP, 100);
        world.getCamera().lookAt(new SimpleVector());
        world.getCamera().setFOV(1.5f);
    }

    /**
     * @param angle measured in radians [rad]
     */
    public void rotateX(float angle) {
        Matrix cameraBackBuffer = world.getCamera().getBack();
        cameraBackBuffer.rotateX(angle);
    }

    /**
     * @param angle measured in radians [rad]
     */
    public void rotateY(float angle) {
        Matrix cameraBackBuffer = world.getCamera().getBack();
        cameraBackBuffer.rotateAxis(cameraBackBuffer.getYAxis(), angle);
    }

    public void moveForward() {
        move(com.threed.jpct.Camera.CAMERA_MOVEIN);
    }

    /**
     * @param direction com.threed.jpct.Camera.CAMERA_*
     */
    private void move(int direction) {
        world.checkCameraCollisionEllipsoid(direction, COLLISION_ELLIPSOID, 1, 5);
    }

    public void moveBackward() {
        move(com.threed.jpct.Camera.CAMERA_MOVEOUT);
    }

    public void moveLeft() {
        move(com.threed.jpct.Camera.CAMERA_MOVELEFT);
    }

    public void moveRight() {
        move(com.threed.jpct.Camera.CAMERA_MOVERIGHT);
    }

    public void moveUp() {
        move(com.threed.jpct.Camera.CAMERA_MOVEUP);
    }

    public void moveDown() {
        move(com.threed.jpct.Camera.CAMERA_MOVEDOWN);
    }

    public int getOutputHeight() {
        return buffer.getOutputHeight();
    }
}

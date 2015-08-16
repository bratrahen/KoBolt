package com.kobot.framework.controls.camera.commands;

import com.kobot.framework.controls.MouseMapper;
import com.kobot.framework.controls.camera.Camera;

public class RotateCameraCommand extends CameraCommand {
    public static final double X_AXIS_UPPER_BOUND = Math.PI / 4.2;
    public static final double X_AXIS_LOWER_BOUND = -Math.PI / 4.2;

    private final MouseMapper mouseMapper;
    private float xAngle;

    public RotateCameraCommand(Camera camera) {
        super(camera);
        mouseMapper = new MouseMapper(camera.getOutputHeight());
        mouseMapper.hide();
    }

    @Override
    public void execute() {
        int dx = mouseMapper.getDeltaX();
        int dy = mouseMapper.getDeltaY();

        float ticks = 1f;
        float ts = 0.2f * ticks;
        float tsy = ts;

        if (dx != 0) {
            ts = dx / 500f;
        }
        if (dy != 0) {
            tsy = dy / 500f;
        }

        if (dx != 0) {
            camera.rotateY(ts);
        }

        if ((dy > 0 && xAngle < X_AXIS_UPPER_BOUND) || (dy < 0 && xAngle > X_AXIS_LOWER_BOUND)) {
            camera.rotateX(tsy);
            xAngle += tsy;
        }
    }
}

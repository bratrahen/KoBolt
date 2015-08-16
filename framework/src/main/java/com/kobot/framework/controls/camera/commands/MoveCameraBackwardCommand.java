package com.kobot.framework.controls.camera.commands;

import com.kobot.framework.controls.camera.Camera;

public class MoveCameraBackwardCommand extends CameraCommand {
    public MoveCameraBackwardCommand(Camera camera) {
        super(camera);
    }

    @Override
    public void execute() {
        camera.moveBackward();
    }
}

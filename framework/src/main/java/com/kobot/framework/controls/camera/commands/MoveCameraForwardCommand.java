package com.kobot.framework.controls.camera.commands;

import com.kobot.framework.controls.camera.Camera;

public class MoveCameraForwardCommand extends CameraCommand {
    public MoveCameraForwardCommand(Camera camera) {
        super(camera);
    }

    @Override
    public void execute() {
        camera.moveForward();
    }
}

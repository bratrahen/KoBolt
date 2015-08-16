package com.kobot.framework.controls.camera.commands;

import com.kobot.framework.controls.camera.Camera;

public class MoveCameraRightCommand extends CameraCommand {
    public MoveCameraRightCommand(Camera camera) {
        super(camera);
    }

    @Override
    public void execute() {
        camera.moveRight();
    }
}

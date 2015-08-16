package com.kobot.framework.controls.camera.commands;

import com.kobot.framework.controls.camera.Camera;

public class MoveCameraLeftCommand extends CameraCommand {
    public MoveCameraLeftCommand(Camera camera) {
        super(camera);
    }

    @Override
    public void execute() {
        camera.moveLeft();
    }
}

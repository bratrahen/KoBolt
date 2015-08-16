package com.kobot.framework.controls.camera.commands;

import com.kobot.framework.Command;
import com.kobot.framework.controls.camera.Camera;

public abstract class CameraCommand extends Command {
    protected final Camera camera;

    public CameraCommand(Camera camera) {
        this.camera = camera;
    }
}

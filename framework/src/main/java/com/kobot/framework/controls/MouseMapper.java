package com.kobot.framework.controls;

import com.threed.jpct.FrameBuffer;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Mouse;

public class MouseMapper {

    private boolean hidden = false;

    private int height = 0;

    public MouseMapper(int outputHeight) {
        height = outputHeight;
        init();
    }

    public void hide() {
        if (!hidden) {
            Mouse.setGrabbed(true);
            hidden = true;
        }
    }

    public void show() {
        if (hidden) {
            Mouse.setGrabbed(false);
            hidden = false;
        }
    }

    public boolean isVisible() {
        return !hidden;
    }

    public void destroy() {
        show();
        if (Mouse.isCreated()) {
            Mouse.destroy();
        }
    }

    public boolean buttonDown(int button) {
        return Mouse.isButtonDown(button);
    }

    public int getMouseX() {
        return Mouse.getX();
    }

    public int getMouseY() {
        return height - Mouse.getY();
    }

    public int getDeltaX() {
        if (Mouse.isGrabbed()) {
            return Mouse.getDX();
        } else {
            return 0;
        }
    }

    public int getDeltaY() {
        if (Mouse.isGrabbed()) {
            return Mouse.getDY();
        } else {
            return 0;
        }
    }

    private void init() {
        try {
            if (!Mouse.isCreated()) {
                Mouse.create();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

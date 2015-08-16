package com.kobot.framework.controls.camera;

/**
 * Created by rahen on 2015-08-16.
 */
public interface Camera {
    void rotateX(float angle);

    void rotateY(float angle);

    void moveForward();

    void moveBackward();

    void moveLeft();

    void moveRight();

    void moveUp();

    void moveDown();

    int getOutputHeight();
}

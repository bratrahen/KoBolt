package com.kobot.framework.simulation.commands;

import com.kobot.framework.Command;
import com.kobot.framework.entitysystem.Utilities;
import com.kobot.framework.simulation.PhysicalObject;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

public class AccelerateCommand extends Command {
    private final PhysicalObject physicalObject;
    private final Vector3f impulseInWorldSpace;

    /**
     * Applies impulseInWorldSpace to the physical object
     * @param impulseInWorldSpace measured in Newtons [N]
     * @see com.kobot.framework.simulation.PhysicalObject#applyCentralImpulse
     */
    public AccelerateCommand(PhysicalObject physicalObject, Vector3f impulseInWorldSpace) {
        this.physicalObject = physicalObject;
        this.impulseInWorldSpace = impulseInWorldSpace;
    }

    @Override
    public void execute() {
        Matrix3f R = physicalObject.getRotationMatrix();
        Vector3f impulseInLocalSpace = Utilities.mul(R, impulseInWorldSpace);
        physicalObject.applyCentralImpulse(impulseInLocalSpace);
    }
}

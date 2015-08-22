package com.kobot.framework.simulation.commands;

import com.kobot.framework.Command;
import com.kobot.framework.entitysystem.Utilities;
import com.kobot.framework.simulation.PhysicalObject;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

public class AngularAccelerationCommand extends Command {
    private final PhysicalObject physicalObject;
    private final Vector3f torqueImpulse;

    public AngularAccelerationCommand(PhysicalObject physicalObject, Vector3f torqueImpulse) {
        super();
        this.physicalObject = physicalObject;
        this.torqueImpulse = torqueImpulse;
    }

    @Override
    public void execute() {
        physicalObject.applyTorqueImpulse(torqueImpulse);
    }
}

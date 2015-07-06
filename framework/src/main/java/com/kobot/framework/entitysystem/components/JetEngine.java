package com.kobot.framework.entitysystem.components;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.api.basic.UniqueComponent;
import com.kobot.framework.entitysystem.manager.ComponentFinder;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

public class JetEngine implements UniqueComponent {

    private final ComponentFinder finder;
    private final Vector3f maxThrustForce;

    public JetEngine(ComponentFinder finder, Vector3f maxThrustForce) {
        this.finder = finder;
        this.maxThrustForce = maxThrustForce;
    }

    public void setThrustPercentage(int percent) {
        percent = Math.max(0, percent);
        percent = Math.min(percent, 100);

        Entity entity = finder.findEntityForComponent(this);
        PhysicsComponent physics = finder.findPhysicalBody(entity);
//        force.scale(percent / 100f);

        Vector3f force = new Vector3f(maxThrustForce);
        force.normalize();

        Quat4f orientation = new Quat4f();
        physics.body.getOrientation(orientation);







        float x = orientation.getX();
        float y = orientation.getY();
        float z = orientation.getZ();
        force.cross(force, new Vector3f(x, y, z));
        force.scale(maxThrustForce.length() * percent / 100f);
        physics.body.applyCentralForce(force);
    }
}

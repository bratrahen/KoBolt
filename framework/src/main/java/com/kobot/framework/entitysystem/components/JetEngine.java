package com.kobot.framework.entitysystem.components;

import com.bulletphysics.linearmath.QuaternionUtil;
import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.api.Body;
import com.kobot.framework.entitysystem.components.api.basic.UniqueComponent;
import com.kobot.framework.entitysystem.manager.ComponentFinder;

import javax.vecmath.Vector3f;

public class JetEngine implements UniqueComponent {

    private final ComponentFinder finder;
    private final Vector3f maxThrustForce;
    private float scale;

    public JetEngine(ComponentFinder finder, Vector3f maxThrustForce) {
        this.finder = finder;
        this.maxThrustForce = maxThrustForce;
    }

    public void setThrustPercentage(int percent) {
        scale = Math.max(0, percent);
        scale = Math.min(scale, 100);
        scale /= 100f;
    }

    public void applyForce() {
        Entity entity = finder.findEntityForComponent(this);
        Body body = finder.findPhysicalBody(entity);

        Vector3f force = new Vector3f();
        QuaternionUtil.quatRotate(body.getRotation(), maxThrustForce, force);
        force.scale(scale);

        body.applyCentralForce(force);
    }
}

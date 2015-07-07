package com.kobot.framework.entitysystem.components;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;
import com.kobot.framework.entitysystem.systems.JpctRenderingSystem;
import com.kobot.framework.entitysystem.systems.PhysicsSystem;
import com.kobot.framework.objects.graphic.jpct.JpctMotionState;
import com.threed.jpct.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.vecmath.Vector3f;

import static org.junit.Assert.assertEquals;


public class JpctRendererComponentTest {
    final float sizeX = 10;
    final float sizeY = 60;
    final float sizeZ = 10;

    private World world_view;
    private DynamicsWorld world_model;
    private Object3D box_view;

    @Before
    public void setUp() throws Exception {
        JpctRenderingSystem.configJpct();
        world_view = new World();
        world_model = PhysicsSystem.createSimulation(10);


        float baseHalfExtend = sizeX / 2.0f;
        float heightBaseRatio = sizeY / sizeX;
        box_view = Primitives.getBox(baseHalfExtend, heightBaseRatio);
        box_view.rotateY((float) Math.PI / 4f);
        box_view.rotateMesh();
    }

    @Test
    public void testBoundingBox() throws Exception {

        float[] boundingBox = box_view.getMesh().getBoundingBox();
        float boundX = Math.abs(boundingBox[1] - boundingBox[0]);
        float boundY = Math.abs(boundingBox[3] - boundingBox[2]);
        float boundZ = Math.abs(boundingBox[5] - boundingBox[4]);

        assertEquals(sizeX, boundX, 0.0001);
        assertEquals(sizeY, boundY, 0.0001);
        assertEquals(sizeZ, boundZ, 0.0001);
    }
}
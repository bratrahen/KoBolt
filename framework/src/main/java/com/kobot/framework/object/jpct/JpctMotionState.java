package com.kobot.framework.object.jpct;

import com.bulletphysics.linearmath.MatrixUtil;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;
import com.threed.jpct.Matrix;
import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;
import org.jetbrains.annotations.NotNull;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

public class JpctMotionState extends MotionState{

    @NotNull Object3D object3D;

    public JpctMotionState(Object3D object3D) {
        this.object3D = object3D;

        Transform identity = new Transform();
        identity.setIdentity();
        setGraphicFromSimulation(identity);
    }

    public Transform getWorldTransform(Transform worldTrans) {
        setSimulationFromGraphic(worldTrans);
        return worldTrans;
    }

    public void setWorldTransform(Transform worldTrans) {
        setGraphicFromSimulation(worldTrans);
    }

    private void setSimulationFromGraphic(Transform tran) {
        SimpleVector t_view = object3D.getTransformedCenter();
        tran.origin.set(t_view.x, -t_view.y, -t_view.z); // not sure if translation or position

        Matrix R_view = object3D.getRotationMatrix();
        MatrixUtil.setFromOpenGLSubMatrix(tran.basis, R_view.getDump());
    }

    private void setGraphicFromSimulation(Transform tran) {
        Vector3f t_model = tran.origin;
        SimpleVector t_view = object3D.getTransformedCenter();
        float dx_view = t_model.x - t_view.x;
        float dy_view = (-t_model.y) - t_view.y;
        float dz_view = (-t_model.z) - t_view.z;
        object3D.translate(dx_view, dy_view, dz_view);

        Matrix3f R_model = tran.basis;
        Matrix R_view = object3D.getRotationMatrix();
        float[] dump = R_view.getDump();
        MatrixUtil.getOpenGLSubMatrix(R_model, dump);
        R_view.setDump(dump);
        R_view.rotateX((float) Math.PI);
        object3D.setRotationMatrix(R_view);
    }

    @NotNull
    public Object3D getObject3D() {
        return object3D;
    }
}

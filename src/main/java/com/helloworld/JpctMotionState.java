package com.helloworld;

import com.bulletphysics.linearmath.MatrixUtil;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;
import com.threed.jpct.Matrix;
import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

public class JpctMotionState extends MotionState {
    public final Transform centerOfMassOffset = new Transform();
    private Object3D object_view;

    public JpctMotionState(Object3D obj) {
        object_view = obj;
        centerOfMassOffset.setIdentity();
    }

    public JpctMotionState(Object3D obj, Transform startTrans) {
        object_view = obj;
        setViewFromModel(startTrans);
        centerOfMassOffset.setIdentity();
    }

    private void setViewFromModel(Transform tran) {
        Vector3f t_model = tran.origin;
        SimpleVector t_view = object_view.getTransformedCenter();
        float dx_view = t_model.x - t_view.x;
        float dy_view = (-t_model.y) - t_view.y;
        float dz_view = (-t_model.z) - t_view.z;
        object_view.translate(dx_view, dy_view, dz_view);

        Matrix3f R_model = tran.basis;
        Matrix R_view = object_view.getRotationMatrix();
        float[] dump = R_view.getDump();
        MatrixUtil.getOpenGLSubMatrix(R_model, dump);
        R_view.setDump(dump);
        R_view.rotateX((float) Math.PI);
        object_view.setRotationMatrix(R_view);
    }


    public JpctMotionState(Object3D obj, Transform startTrans, Transform centerOfMassOffset) {
        object_view = obj;
        setViewFromModel(startTrans);
        this.centerOfMassOffset.set(centerOfMassOffset);
    }

    public Transform getWorldTransform(Transform worldTrans) {
        setModelFromView(worldTrans);
        return worldTrans;
    }

    private void setModelFromView(Transform tran) {
        SimpleVector t_view = object_view.getTransformedCenter();
        tran.origin.set(t_view.x, -t_view.y, -t_view.z); // not sure if translation or position

        Matrix R_view = object_view.getRotationMatrix();
        MatrixUtil.setFromOpenGLSubMatrix(tran.basis, R_view.getDump());
    }

    public void setWorldTransform(Transform worldTrans) {
        setViewFromModel(worldTrans);
    }
}

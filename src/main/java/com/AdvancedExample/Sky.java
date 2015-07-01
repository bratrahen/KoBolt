package com.AdvancedExample;

import com.threed.jpct.*;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Sky {

    private World sky;
    private Object3D dome;

    public Sky(@NotNull String path, @NotNull SimpleVector center) {
        sky = new World();
        sky.setAmbientLight(255, 255, 255);
        sky.getLights().setRGBScale(Lights.RGB_SCALE_2X);

        dome = Object3D.mergeAll(Loader.load3DS(path + "example/dome.3ds", 2));
        dome.build();
        dome.rotateX((float) -Math.PI / 2f);

        TextureManager.getInstance().addTexture("sky", new Texture(path + "example/sky.jpg"));
        dome.setTexture("sky");
        dome.calcTextureWrap();
        tileTexture(dome, 3);

        dome.translate(center.calcSub(dome.getTransformedCenter()));
        dome.setLighting(Object3D.LIGHTING_NO_LIGHTS);
        dome.setAdditionalColor(Color.WHITE);
        dome.compileAndStrip();

        sky.addObject(dome);
    }

    private void tileTexture(Object3D obj, float tileFactor) {
        PolygonManager pm = obj.getPolygonManager();

        int end = pm.getMaxPolygonID();
        for (int i = 0; i < end; i++) {
            SimpleVector uv0 = pm.getTextureUV(i, 0);
            SimpleVector uv1 = pm.getTextureUV(i, 1);
            SimpleVector uv2 = pm.getTextureUV(i, 2);

            uv0.scalarMul(tileFactor);
            uv1.scalarMul(tileFactor);
            uv2.scalarMul(tileFactor);

            int id = pm.getPolygonTexture(i);

            TextureInfo ti = new TextureInfo(id, uv0.x, uv0.y, uv1.x, uv1.y,
                    uv2.x, uv2.y);
            pm.setPolygonTexture(i, ti);
        }
    }

    public void render(FrameBuffer buffer) {
        buffer.setPaintListenerState(false);
        sky.renderScene(buffer);
        sky.draw(buffer);
        buffer.setPaintListenerState(true);
    }

    public void update(long ticks, Matrix cameraMatrix) {
        sky.getCamera().setBack(cameraMatrix);
        dome.rotateY(0.00005f * ticks);
    }
}

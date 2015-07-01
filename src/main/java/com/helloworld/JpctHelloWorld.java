package com.helloworld;

import com.threed.jpct.*;


public class JpctHelloWorld {

    private static World world;

    public static void main(String[] args) throws InterruptedException {

        world = new World();
        world.setAmbientLight(0, 255, 0);

        TextureManager.getInstance().addTexture("box", new Texture("F:\\1.ProgramowanieProjekty\\HelloJpct\\src\\main\\resources\\box.jpg"));

        Object3D box = Primitives.getBox(13f, 2f);
        box.setTexture("box");
        box.setEnvmapped(Object3D.ENVMAP_ENABLED);

        box.build();
        world.addObject(box);

        world.getCamera().setPosition(50, -50, -5);
        world.getCamera().lookAt(box.getTransformedCenter());

        FrameBuffer buffer = new FrameBuffer(800, 600, FrameBuffer.SAMPLINGMODE_NORMAL);
        buffer.disableRenderer(IRenderer.RENDERER_SOFTWARE);
        buffer.enableRenderer(IRenderer.RENDERER_OPENGL);

        while (!org.lwjgl.opengl.Display.isCloseRequested()) {
            box.rotateY(0.01f);
            buffer.clear(java.awt.Color.BLUE);
            world.renderScene(buffer);
            world.draw(buffer);
            buffer.update();
            buffer.displayGLOnly();
            Thread.sleep(10);
        }

        buffer.disableRenderer(IRenderer.RENDERER_OPENGL);
        buffer.dispose();
        System.exit(0);

   }
}

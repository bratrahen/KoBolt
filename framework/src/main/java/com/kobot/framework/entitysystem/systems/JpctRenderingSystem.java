package com.kobot.framework.entitysystem.systems;

import com.kobot.framework.controls.camera.Camera;
import com.kobot.framework.objects.graphic.jpct.JpctCamera;
import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.JpctRendererComponent;
import com.kobot.framework.entitysystem.eventbus.EventBus;
import com.kobot.framework.entitysystem.eventbus.GameEvent;
import com.kobot.framework.entitysystem.eventbus.GameEventListener;
import com.kobot.framework.entitysystem.eventbus.events.CreateEntityEvent;
import com.kobot.framework.entitysystem.eventbus.events.RemoveEntityEvent;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.threed.jpct.*;
import com.threed.jpct.util.Light;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class JpctRenderingSystem extends System implements GameEventListener {
    static {
        configJpct();
    }

    private final FrameBuffer buffer;
    private final World world;
    private final Camera camera;

    public JpctRenderingSystem(EntityManager entityManager) {
        super(entityManager);
        buffer = createFrameBuffer();
        world = createWorld();
        camera = new JpctCamera(world, buffer);

        EventBus.addListener(this, CreateEntityEvent.class);
        EventBus.addListener(this, RemoveEntityEvent.class);
    }

    private FrameBuffer createFrameBuffer() {
        FrameBuffer result = new FrameBuffer(800, 600, FrameBuffer.SAMPLINGMODE_NORMAL);
        result.disableRenderer(IRenderer.RENDERER_SOFTWARE);
        result.enableRenderer(IRenderer.RENDERER_OPENGL, IRenderer.MODE_OPENGL);

        return result;
    }

    private World createWorld() {
        World result = new World();
        result.setAmbientLight(30, 30, 30);
        result.getLights().setRGBScale(Lights.RGB_SCALE_2X);

        Light sun = new Light(result);
        sun.setIntensity(250, 250, 250);
        sun.setAttenuation(800);
        sun.setPosition(new SimpleVector(0, -100, 0));

        return result;
    }

    public static void configJpct() {
        Config.glVerbose = true;
        Config.glAvoidTextureCopies = true;
        Config.maxPolysVisible = 1000;
        Config.glColorDepth = 24;
        Config.glFullscreen = false;
        Config.farPlane = 4000;
        Config.glShadowZBias = 0.8f;
        Config.lightMul = 1;
        Config.collideOffset = 500;
        Config.glTrilinear = true;
    }

    @Override
    public void update(float timeStepInSeconds) {
        buffer.clear();
        world.renderScene(buffer);
        world.draw(buffer);
        buffer.update();
        buffer.displayGLOnly();
    }

    private void add(Entity entity) {
        JpctRendererComponent component = (JpctRendererComponent) componentFinder.findRenderer(entity);
        world.addObject(component.object3D);
    }

    private void remove(Entity entity) {
        JpctRendererComponent component = (JpctRendererComponent) componentFinder.findRenderer(entity);
        world.removeObject(component.object3D);
    }

    public void handle(GameEvent event) {
        if (event instanceof CreateEntityEvent){
            add(((CreateEntityEvent) event).entity);
        }else if (event instanceof RemoveEntityEvent){
            remove(((RemoveEntityEvent) event).entity);
        } else {
            throw new NotImplementedException();
        }
    }

    public Camera getCamera() {
        return camera;
    }
}

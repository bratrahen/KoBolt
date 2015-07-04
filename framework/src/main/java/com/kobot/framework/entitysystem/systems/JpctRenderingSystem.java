package com.kobot.framework.entitysystem.systems;

import com.kobot.framework.controls.GhostControls;
import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.entitysystem.components.JpctRendererComponent;
import com.threed.jpct.*;
import com.threed.jpct.util.Light;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class JpctRenderingSystem extends System {
    static {
        configJpct();
    }

    private final FrameBuffer buffer;
    private final World world;
    private final GhostControls controls;

    Set<Entity> renderableEntities = new HashSet<Entity>();

    public JpctRenderingSystem(EntityManager entityManager) {
        super(entityManager);
        buffer = createFrameBuffer();
        world = createWorld();
        controls = new GhostControls(world, buffer);
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

    private static void configJpct() {
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
        refreshCollectionOfRenderableEntities();


        controls.pollControls();
        controls.move(1);
        buffer.clear();
        world.renderScene(buffer);
        world.draw(buffer);
        buffer.update();
        buffer.displayGLOnly();
    }

    private void refreshCollectionOfRenderableEntities() {
        Collection<Entity> entities = finder.getAllEntitiesPossessingComponentOfClass(JpctRendererComponent.class);
        for (Entity entity : entities) {
            if (!renderableEntities.contains(entity)) {
                add(entity);
            }
        }

        HashSet<Entity> entitiesToBeRemoved = getEntitiesNoLongerRendered(entities);
        for (Entity entity : entitiesToBeRemoved) {
            remove(entity);
        }
    }

    private void add(Entity entity) {
        Set<JpctRendererComponent> components = (Set)finder.getComponentsForEntity(JpctRendererComponent.class, entity);
        for (JpctRendererComponent component : components) {
            world.addObject(component.object3D);
        }
        renderableEntities.add(entity);
    }

    @NotNull
    private HashSet<Entity> getEntitiesNoLongerRendered(Collection<Entity> entities) {
        HashSet<Entity> result = new HashSet<Entity>(renderableEntities);
        result.removeAll(entities);
        return result;
    }

    private void remove(Entity entity) {
        Set<JpctRendererComponent> components = (Set) finder.getComponentsForEntity(JpctRendererComponent.class, entity);
        for (JpctRendererComponent component : components) {
            world.removeObject(component.object3D);
        }
        renderableEntities.remove(entity);
    }


}

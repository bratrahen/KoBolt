import com.kobot.framework.Game;
import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.entitysystem.components.Team;
import com.kobot.framework.entitysystem.components.factory.EntityFactory;
import com.kobot.framework.entitysystem.components.factory.JpctEntityFactory;
import com.kobot.framework.entitysystem.systems.*;
import com.kobot.framework.entitysystem.systems.System;

import javax.vecmath.Vector3f;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class MonsterWarsGame extends Game {
    private final JpctRenderingSystem renderingSystem;
    private final Set<System> systems = new HashSet<System>();

    public static void main(String[] args) {
        MonsterWarsGame game = new MonsterWarsGame();
        game.run();
    }

    public MonsterWarsGame() {
        EntityManager entityManager = new EntityManager();

        renderingSystem = new JpctRenderingSystem(entityManager);

        systems.add(new MaxLifeSpanSystem(entityManager));
        systems.add(new AiSystem(entityManager));
        systems.add(new PhysicsSystem(entityManager));
        systems.add(new DisposeSystem(entityManager));

        EntityFactory entityFactory = new JpctEntityFactory(entityManager);
        final long RED_TEAM = 1;
        Entity redCube = entityFactory.createStaticCubeWithGun(10, Color.RED, new Vector3f(-50, 0, 0));
        entityManager.addComponentToEntity(Team.getById(RED_TEAM), redCube);

        final long BLUE_TEAM = 2;
        Entity blueCube = entityFactory.createDynamicSphere(1, 1, Color.BLUE, new Vector3f(50, 50, 0));
        entityManager.addComponentToEntity(Team.getById(BLUE_TEAM), blueCube);
        Entity blueSphere = entityFactory.createStaticCube(10, Color.CYAN, new Vector3f(50, 0, 0));
        entityManager.addComponentToEntity(Team.getById(BLUE_TEAM), blueSphere);
    }

    @Override
    protected boolean shouldQuit() {
        return false;
    }

    @Override
    protected void simulate(float timestepInSeconds) {
        for (System system : systems) {
            system.update(timestepInSeconds);
        }
    }

    @Override
    protected void render() {
        renderingSystem.update(0);
    }
}

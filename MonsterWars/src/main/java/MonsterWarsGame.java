import com.kobot.framework.Game;
import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.entitysystem.components.Team;
import com.kobot.framework.entitysystem.components.factory.EntityFactory;
import com.kobot.framework.entitysystem.components.factory.JpctEntityFactory;
import com.kobot.framework.entitysystem.systems.AiSystem;
import com.kobot.framework.entitysystem.systems.JpctRenderingSystem;
import com.kobot.framework.entitysystem.systems.PhysicsSystem;

import javax.vecmath.Vector3f;
import java.awt.*;

public class MonsterWarsGame extends Game {
    private final PhysicsSystem physicsSystem;
    private final JpctRenderingSystem renderingSystem;
    private final AiSystem aiSystem;
    private final EntityFactory entityFactory;

    public static void main(String[] args) {
        MonsterWarsGame game = new MonsterWarsGame();
        game.run();
    }

    public MonsterWarsGame() {
        EntityManager entityManager = new EntityManager();
        physicsSystem = new PhysicsSystem(entityManager);
        renderingSystem = new JpctRenderingSystem(entityManager);
        aiSystem = new AiSystem(entityManager);
        entityFactory = new JpctEntityFactory(entityManager);

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
        aiSystem.update(timestepInSeconds);
        physicsSystem.update(timestepInSeconds);
    }

    @Override
    protected void render() {
        renderingSystem.update(0);
    }
}

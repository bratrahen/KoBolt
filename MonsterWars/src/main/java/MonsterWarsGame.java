import com.kobot.framework.Game;
import com.kobot.framework.entitysystem.EntityManager;
import com.kobot.framework.entitysystem.components.factory.JpctEntityFactory;
import com.kobot.framework.entitysystem.systems.JpctRenderingSystem;
import com.kobot.framework.entitysystem.systems.PhysicsSystem;

import javax.vecmath.Vector3f;
import java.awt.*;

public class MonsterWarsGame extends Game {
    private final PhysicsSystem physicsSystem;
    private final JpctRenderingSystem renderingSystem;
    private final JpctEntityFactory entityFactory;

    public static void main(String[] args) {
        MonsterWarsGame game = new MonsterWarsGame();
        game.run();
    }

    public MonsterWarsGame() {
        EntityManager entityManager = new EntityManager();
        physicsSystem = new PhysicsSystem(entityManager);
        renderingSystem = new JpctRenderingSystem(entityManager);
        entityFactory = new JpctEntityFactory(entityManager);

        entityFactory.createStaticCube(10, Color.RED, new Vector3f(-50, 0 ,0));
        entityFactory.createStaticCube(10, Color.BLUE, new Vector3f(50, 0 ,0));
    }

    @Override
    protected boolean shouldQuit() {
        return false;
    }

    @Override
    protected void simulate(float timestepInSeconds) {
        physicsSystem.update(timestepInSeconds);
    }

    @Override
    protected void render() {
        renderingSystem.update(0);
    }
}

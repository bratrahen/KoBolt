import com.kobot.framework.Game;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.entitysystem.systems.*;
import com.kobot.framework.entitysystem.systems.System;

import javax.vecmath.Vector3f;
import java.util.HashSet;
import java.util.Set;

public class MonsterWarsGame extends Game {
    private static final float GRAVITY = 0.0f;
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
        systems.add(new JetEnginesSystem(entityManager));
        systems.add(new PhysicsSystem(entityManager, GRAVITY));
        systems.add(new DisposeSystem(entityManager));

        MonstersFactory factory = new JpctMonsterFactory(entityManager);

        final long BLUE_TEAM = 1;
        factory.createKushanCarrier(new Vector3f(1600, 0, 0), new Vector3f(0, 0, 0), BLUE_TEAM);

        final long RED_TEAM = 2;
        factory.createTaiidanCarrier(new Vector3f(-1600, 0, 0), new Vector3f(0, (float)Math.PI, 0), RED_TEAM);
//        factory.createCastleWithGun(new Vector3f(-800, 0, 0), RED_TEAM);
//        factory.createMeleeMonster(new Vector3f(50, 50, 0), BLUE_TEAM);
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

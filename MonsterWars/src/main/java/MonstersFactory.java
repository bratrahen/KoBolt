import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.JetEngine;
import com.kobot.framework.entitysystem.components.RangedWeapon;
import com.kobot.framework.entitysystem.components.Team;
import com.kobot.framework.entitysystem.components.ai.MotherShipAi;
import com.kobot.framework.entitysystem.components.factory.JpctPrimitivesFactory;
import com.kobot.framework.entitysystem.manager.ComponentFinder;
import com.kobot.framework.entitysystem.manager.EntityManager;

import javax.vecmath.Vector3f;
import java.awt.*;

public class MonstersFactory extends JpctPrimitivesFactory {

    public MonstersFactory(EntityManager entityManager) {
        super(entityManager);
    }

    Entity createCastle(Vector3f position, long teamId){
        Entity entity = createStaticCube(10, Color.CYAN, position);
        entityManager.addComponentToEntity(Team.getById(teamId), entity);
        return entity;
    }

    Entity createCastleWithGun(Vector3f position, long teamId){
        final float DAMAGE = 10.0f;
        final float RELOAD_IN_SEC= 2.0f;

        Entity entity = createStaticCube(10, Color.ORANGE, position);
        entityManager.addComponentToEntity(Team.getById(teamId), entity);
        entityManager.addComponentToEntity(new RangedWeapon(DAMAGE, RELOAD_IN_SEC, this), entity);
        entityManager.addComponentToEntity(new MotherShipAi(entityManager), entity);

        return entity;
    }

    Entity createMeleeMonster(Vector3f position, long teamId){
        Entity entity = createDynamicSphere(1, 1, Color.BLUE, position);
        entityManager.addComponentToEntity(Team.getById(teamId), entity);

        JetEngine jetEngine = new JetEngine(new ComponentFinder(entityManager), new Vector3f(0, -10, 0));
        entityManager.addComponentToEntity(jetEngine, entity);
        jetEngine.setThrustPercentage(100);

        return entity;
    }
}

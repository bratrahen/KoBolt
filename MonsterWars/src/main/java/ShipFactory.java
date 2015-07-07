import com.bulletphysics.collision.shapes.CollisionShape;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.JetEngine;
import com.kobot.framework.entitysystem.components.RangedWeapon;
import com.kobot.framework.entitysystem.components.Team;
import com.kobot.framework.entitysystem.components.ai.MotherShipAi;
import com.kobot.framework.entitysystem.components.api.Body;
import com.kobot.framework.entitysystem.components.api.RendererComponent;
import com.kobot.framework.entitysystem.components.factory.PrimitivesFactory;
import com.kobot.framework.entitysystem.manager.ComponentFinder;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.simulation.PhysicalObject;
import com.kobot.framework.simulation.PhysicalObjectBuilder;
import org.jetbrains.annotations.NotNull;

import javax.vecmath.Vector3f;
import java.awt.*;

public abstract class ShipFactory extends PrimitivesFactory {
    private static final float PI = (float)Math.PI;
    private static final float BOMBER_MASS_IN_KG = 10;
    private static final float CARRIER_MASS_IN_KG = 10000;
    private static final float FRIGATE_MASS_IN_KG = 1000;

    public ShipFactory(EntityManager entityManager) {
        super(entityManager);
    }

    protected abstract RendererComponent createKushanAttackBomberRenderer();

    protected abstract RendererComponent createTaiidanAttackBomberRenderer();

    protected abstract RendererComponent createKushanAssaultFrigateRenderer();

    protected abstract RendererComponent createTaiidanAssaultFrigateRenderer();

    protected abstract RendererComponent createKushanCarrierRenderer();

    protected abstract RendererComponent createTaiidanCarrierRenderer();

    public Entity createKushanAttackBomber(Vector3f position, Vector3f orientation, long teamId) {
        RendererComponent renderer = createKushanAttackBomberRenderer();
        return createAttackBomber(position, orientation, teamId, renderer);
    }

    public Entity createTaiidanAttackBomber(Vector3f position, Vector3f orientation, long teamId) {
        RendererComponent renderer = createTaiidanAttackBomberRenderer();
        return createAttackBomber(position, orientation, teamId, renderer);
    }

    protected Entity createAttackBomber(Vector3f position, Vector3f orientation, long teamId, RendererComponent renderer) {
        Entity ship = createShip(BOMBER_MASS_IN_KG, position, orientation, renderer);
        entityManager.addComponentToEntity(Team.getById(teamId), ship);
        return ship;
    }

    public Entity createKushanAssaultFrigate(Vector3f position, Vector3f orientation, long teamId) {
        RendererComponent renderer = createKushanAssaultFrigateRenderer();
        return createAssaultFrigate(position, orientation, teamId, renderer);
    }

    public Entity createTaiidanAssaultFrigate(Vector3f position, Vector3f orientation, long teamId) {
        RendererComponent renderer = createTaiidanAssaultFrigateRenderer();
        return createAssaultFrigate(position, orientation, teamId, renderer);
    }

    private Entity createAssaultFrigate(Vector3f position, Vector3f orientation, long teamId, RendererComponent renderer) {
        Entity ship = createShip(FRIGATE_MASS_IN_KG, position, orientation, renderer);
        entityManager.addComponentToEntity(Team.getById(teamId), ship);
        return ship;
    }

    public Entity createKushanCarrier(Vector3f position, Vector3f orientation, long teamId) {
        RendererComponent renderer = createKushanCarrierRenderer();
        return createCarrier(position, orientation, teamId, renderer);
    }

    public Entity createTaiidanCarrier(Vector3f position, Vector3f orientation, long teamId) {
        RendererComponent renderer = createTaiidanCarrierRenderer();
        return createCarrier(position, orientation, teamId, renderer);
    }

    private Entity createCarrier(Vector3f position, Vector3f orientation, long teamId, RendererComponent renderer) {
        Entity ship = createShip(CARRIER_MASS_IN_KG, position, orientation, renderer);
        entityManager.addComponentToEntity(Team.getById(teamId), ship);
        return ship;
    }


    /**
     * @param mass measured in kilograms [kg]
     * @param position world coordinated [x, y, z] measured in meters [m]
     * @param orientation counterclockwise rotation [rotX, rotY, rotZ] around world axises measured in radians [rad]
     * @param renderer ship's renderer
     * @return Entity
     */
    private Entity createShip(float mass, Vector3f position, Vector3f orientation, RendererComponent renderer) {
        Vector3f defaultOrientation = new Vector3f(0, PI, -PI/2f);
        Vector3f sumOrientation = new Vector3f(defaultOrientation);
        sumOrientation.add(orientation);

        PhysicalObjectBuilder builder = new PhysicalObjectBuilder();
        builder.setShape(renderer.getBoundingBox());
        builder.setMass(mass).setPosition(position).setOrientation(sumOrientation);
        PhysicalObject physicalObject = builder.build(renderer.createMotionState());

        Entity entity = entityManager.createEntity();
        entityManager.addComponentToEntity(physicalObject, entity);
        entityManager.addComponentToEntity(renderer, entity);

        return entity;
    }

    @NotNull
    protected final Vector3f calculateInertia(CollisionShape shape, float mass) {
        Vector3f localInertia = new Vector3f(0, 0, 0);
        boolean isDynamic = mass != 0;
        if (isDynamic) {
            shape.calculateLocalInertia(mass, localInertia);
        }
        return localInertia;
    }

    protected Entity createEntity(Body body, RendererComponent renderer) {
        Entity entity = entityManager.createEntity();
        entityManager.addComponentToEntity(body, entity);
        entityManager.addComponentToEntity(renderer, entity);

        return entity;
    }

    Entity createCastleWithGun(Vector3f position, long teamId) {
        final float DAMAGE = 10.0f;
        final float RELOAD_IN_SEC = 2.0f;

        Entity entity = createStaticCube(10, Color.RED, position);
        entityManager.addComponentToEntity(Team.getById(teamId), entity);
        entityManager.addComponentToEntity(new RangedWeapon(DAMAGE, RELOAD_IN_SEC, this), entity);
        entityManager.addComponentToEntity(new MotherShipAi(entityManager), entity);

        return entity;
    }

    Entity createMeleeMonster(Vector3f position, long teamId) {
        Entity entity = createDynamicSphere(1, 1, Color.BLUE, position);
        entityManager.addComponentToEntity(Team.getById(teamId), entity);

        JetEngine jetEngine = new JetEngine(new ComponentFinder(entityManager), new Vector3f(0, -10, 0));
        entityManager.addComponentToEntity(jetEngine, entity);
        jetEngine.setThrustPercentage(100);

        return entity;
    }
}
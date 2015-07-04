package com.kobot.framework.entitysystem.manager;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.StubEntityFactory;
import com.kobot.framework.entitysystem.components.PhysicsComponent;
import com.kobot.framework.entitysystem.components.RangedWeapon;
import com.kobot.framework.entitysystem.components.RendererComponent;
import com.kobot.framework.entitysystem.components.Team;
import com.kobot.framework.entitysystem.components.ai.MotherShipAi;
import com.kobot.framework.entitysystem.manager.ComponentFinder;
import com.kobot.framework.entitysystem.manager.EntityManager;
import org.junit.Before;
import org.junit.Test;

import javax.vecmath.Vector3f;
import java.awt.*;
import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by rahen on 2015-07-04.
 */
public class ComponentFinderTest {

    private EntityManager entityManager;
    private ComponentFinder finder;

    @Before
    public void setUp() throws Exception {
        entityManager = new EntityManager();
        finder = new ComponentFinder(entityManager);
    }

    @Test
    public void testFindTeam() throws Exception {
        Entity entity1 = new Entity(1);
        Entity entity2 = new Entity(2);
        Team team11 = Team.getById(1);
        Team team12 = Team.getById(2);
        entityManager.addComponentToEntity(team11, entity1);
        entityManager.addComponentToEntity(team12, entity1);

        Team team21 = Team.getById(3);
        Team team22 = Team.getById(4);
        entityManager.addComponentToEntity(team21, entity2);
        entityManager.addComponentToEntity(team22, entity2);

        Set<Team> teams1 = finder.findTeam(entity1);
        assertEquals(2, teams1.size());
        assertTrue(teams1.contains(team11));
        assertTrue(teams1.contains(team12));

        Set<Team> teams2 = finder.findTeam(entity2);
        assertEquals(2, teams2.size());
        assertTrue(teams2.contains(team21));
        assertTrue(teams2.contains(team22));
    }

    @Test
    public void testFindPhysicalBody() throws Exception {
        StubEntityFactory factory = new StubEntityFactory(entityManager);
        Entity redSphere = factory.createDynamicSphere(1, 1, Color.RED, new Vector3f(0, 0, 0));
        Entity blueSphere = factory.createDynamicSphere(1, 1, Color.BLUE, new Vector3f(10, 0, 0));

        Set<PhysicsComponent> bodiesOfRed = finder.findPhysicalBodies(redSphere);
        assertEquals(1, bodiesOfRed.size());
        PhysicsComponent redBody = (PhysicsComponent) bodiesOfRed.toArray()[0];
        assertEquals(new Vector3f(0, 0, 0), redBody.getPosition());

        Set<PhysicsComponent> bodiesOfBlue = finder.findPhysicalBodies(blueSphere);
        assertEquals(1, bodiesOfBlue.size());
        PhysicsComponent blueBody = (PhysicsComponent) bodiesOfBlue.toArray()[0];
        assertEquals(new Vector3f(10, 0, 0), blueBody.getPosition());
    }

    @Test
    public void testFindSinglePhysicalBody() throws Exception {
        StubEntityFactory factory = new StubEntityFactory(entityManager);
        Entity redSphere = factory.createDynamicSphere(1, 1, Color.RED, new Vector3f(0, 0, 0));
        Entity blueSphere = factory.createDynamicSphere(1, 1, Color.BLUE, new Vector3f(10, 0, 0));

        PhysicsComponent redBody = finder.findSinglePhysicalBody(redSphere);
        assertTrue(redBody.getPosition().equals(new Vector3f(0, 0, 0)));

        PhysicsComponent blueBody = finder.findSinglePhysicalBody(blueSphere);
        assertTrue(blueBody.getPosition().equals(new Vector3f(10, 0, 0)));
    }

    @Test
    public void testFindGun() throws Exception {
        StubEntityFactory factory = new StubEntityFactory(entityManager);
        Entity redSphere = factory.createDynamicSphere(1, 1, Color.RED, new Vector3f(0, 0, 0));
        RangedWeapon weapon = new RangedWeapon(1, 1, new StubEntityFactory(entityManager));
        entityManager.addComponentToEntity(weapon, redSphere);

        Set<RangedWeapon> weapons = finder.findRangedWeapons(redSphere);
        assertEquals(1, weapons.size());
        assertSame(weapon, weapons.iterator().next());
    }

    @Test
    public void testFindAllMotherShipsAi() throws Exception {
        StubEntityFactory factory = new StubEntityFactory(entityManager);
        Entity redSphere = factory.createDynamicSphere(1, 1, Color.RED, new Vector3f(0, 0, 0));
        MotherShipAi ai_1 = new MotherShipAi(entityManager);
        MotherShipAi ai_2 = new MotherShipAi(entityManager);

        entityManager.addComponentToEntity(ai_1, redSphere);
        entityManager.addComponentToEntity(ai_2, redSphere);

        Collection<MotherShipAi> shipAis = finder.findAllMotherShipsAi();
        assertEquals(2, shipAis.size());
        assertTrue(shipAis.contains(ai_1));
        assertTrue(shipAis.contains(ai_2));
    }

    @Test
    public void testFindRenderers() throws Exception {
        StubEntityFactory factory = new StubEntityFactory(entityManager);
        Entity redSphere = factory.createDynamicSphere(1, 1, Color.RED, new Vector3f(0, 0, 0));

        Set<RendererComponent> renderers = finder.findRenderers(redSphere);
        assertEquals(1, renderers.size());
    }


}
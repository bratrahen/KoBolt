package com.kobot.framework.entitysystem.manager;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.StubEntityFactory;
import com.kobot.framework.entitysystem.components.api.Body;
import com.kobot.framework.entitysystem.components.RangedWeapon;
import com.kobot.framework.entitysystem.components.api.RendererComponent;
import com.kobot.framework.entitysystem.components.Team;
import com.kobot.framework.entitysystem.components.ai.MotherShipAi;
import org.junit.Before;
import org.junit.Test;

import javax.vecmath.Vector3f;
import java.awt.*;
import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.*;

public class ComponentFinderTest {

    private EntityManager entityManager;
    private ComponentFinder finder;

    @Before
    public void setUp() throws Exception {
        entityManager = new EntityManager();
        finder = new ComponentFinder(entityManager);
    }

    @Test
    public void findEnemies(){
        entityManager.addComponentToEntity(Team.getById(1), new Entity(1));
        entityManager.addComponentToEntity(Team.getById(1), new Entity(2));
        entityManager.addComponentToEntity(Team.getById(2), new Entity(3));

        finder.findEnemies(new Entity(1));
    }

    @Test
    public void testFindTeam() throws Exception {
        Entity entity1 = new Entity(1);
        Team team1 = Team.getById(1);
        entityManager.addComponentToEntity(team1, entity1);

        Entity entity2 = new Entity(2);
        Team team2 = Team.getById(2);
        entityManager.addComponentToEntity(team2, entity2);

        Team actualTeam1 = finder.findTeam(entity1);
        assertSame(team1, actualTeam1);

        Team actualTeam2 = finder.findTeam(entity2);
        assertSame(team2, actualTeam2);
    }

    @Test
    public void testFindSinglePhysicalBody() throws Exception {
        StubEntityFactory factory = new StubEntityFactory(entityManager);
        Entity redSphere = factory.createDynamicSphere(1, 1, Color.RED, new Vector3f(0, 0, 0));
        Entity blueSphere = factory.createDynamicSphere(1, 1, Color.BLUE, new Vector3f(10, 0, 0));

        Body redBody = finder.findPhysicalBody(redSphere);
        assertTrue(redBody.getPosition().equals(new Vector3f(0, 0, 0)));

        Body blueBody = finder.findPhysicalBody(blueSphere);
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
        Entity blueSphere = factory.createDynamicSphere(1, 1, Color.BLUE, new Vector3f(10, 0, 0));


        MotherShipAi ai_1 = new MotherShipAi(entityManager);
        MotherShipAi ai_2 = new MotherShipAi(entityManager);

        entityManager.addComponentToEntity(ai_1, redSphere);
        entityManager.addComponentToEntity(ai_2, blueSphere);

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
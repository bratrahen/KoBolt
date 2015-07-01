package com.test.kobot;

import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    public static final double DELTA = 0.0001;

    @Test
    public void run_TestGameBasics(){
        TestGame game = new TestGame();
        game.tickInMillis = game.TIMESTEP_IN_MILLIS;
        game.maxGameLoop = 10;

        game.run();
        assertEquals(10, game.gameLoopCounter);
        assertEquals(game.tickInMillis * (1 + game.maxGameLoop), game.timeInMillis);
    }

    @Test
    public void run_tickEqualsTimestep() throws Exception {
        TestGame game = new TestGame();
        game.tickInMillis = game.TIMESTEP_IN_MILLIS;
        game.maxGameLoop = 10;

        game.run();
        assertEquals(game.maxGameLoop * game.TIMESTEP_IN_SEC, game.simulationTimeInSeconds, DELTA);
    }

    @Test
    public void run_tickTwiceTimestep(){
        TestGame game = new TestGame();
        game.tickInMillis = 2 * game.TIMESTEP_IN_MILLIS;
        game.maxGameLoop = 10;

        game.run();
        assertEquals((2 + 2 + 2 + 2 + 2 + 2 + 2 + 2 + 2 + 2) * game.TIMESTEP_IN_SEC, game.simulationTimeInSeconds, DELTA);
    }

    @Test
    public void run_tickHalfTimestep(){
        TestGame game = new TestGame();
        game.tickInMillis = game.TIMESTEP_IN_MILLIS / 2;
        game.maxGameLoop = 10;

        game.run();
        assertEquals((0 + 1 + 0 + 1 + 0 + 1 + 0 + 1 + 0 + 1) * game.TIMESTEP_IN_SEC, game.simulationTimeInSeconds, DELTA);
    }

    @Test
    public void run_tickOneThirdTimestep(){
        TestGame game = new TestGame();
        game.tickInMillis = game.TIMESTEP_IN_MILLIS / 3;
        game.maxGameLoop = 10;

        game.run();
        assertEquals((0 + 0 + 1 + 0 + 0 + 1 + 0 + 0 + 1 + 0) * game.TIMESTEP_IN_SEC, game.simulationTimeInSeconds, DELTA);
    }
}
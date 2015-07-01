package com.helloworld;

public class AdvancedExampleGame extends Game {
    private WorldView worldView = new AdvancedExampleView();

    public static void main(String[] args) throws Exception {
        AdvancedExampleGame game = new AdvancedExampleGame();
        game.initControls();
        game.gameLoop();
    }

    @Override
    protected WorldView getWorldView() {
        return worldView;
    }

    @Override
    protected WorldModel getWorldModel() {
        return null;
    }
}

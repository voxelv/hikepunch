package com.derelictech.hikepunch;

import com.badlogic.gdx.Game;

/**
 * Created by Tim on 2/14/2016.
 */
public class GameScreen extends AbstractGameScreen {

    WorldController worldController;
    WorldRenderer worldRenderer;

    private boolean paused;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        worldController = new WorldController(game);
        worldRenderer = new WorldRenderer(worldController);
        paused = false;
    }

    @Override
    public void hide() {

    }

    @Override
    public void render(float delta) {
        worldRenderer.render();

        if(!paused) {
            worldController.update(delta);
        }
    }

    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void dispose() {
        worldRenderer.dispose();
    }
}

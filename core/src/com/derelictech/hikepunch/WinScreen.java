package com.derelictech.hikepunch;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.derelictech.hikepunch.objects.MountainsSprite;

/**
 * Created by Tim on 2/14/2016.
 */
public class WinScreen extends AbstractGameScreen {

    private boolean paused;
    SpriteBatch batch;
    MountainsSprite ms;
    Sprite message;

    public WinScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        paused = false;
        batch = new SpriteBatch();
        ms = new MountainsSprite(0, 0, 5);
        message = new Sprite(Assets.instance.win);
        message.setPosition(200, 50);
        message.scale(3);
    }

    @Override
    public void hide() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.8f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        ms.draw(batch);
        message.draw(batch);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {

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

    }
}

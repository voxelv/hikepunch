package com.derelictech.hikepunch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.derelictech.hikepunch.objects.PlayerSprite;

/**
 * Created by Tim on 2/14/2016.
 */
public class WorldController extends InputAdapter{

    private PlayerSprite player;
    private Level level;

    public WorldController() {
        level = new Level(Constants.LEVEL1, (1.0f/(Constants.TILE_PIXEL_WIDTH)));
        player = level.getPlayerSprite();
        init();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Input.Keys.A:
                player.left(true);
                break;
            case Input.Keys.D:
                player.right(true);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch(keycode) {
            case Input.Keys.A:
                player.left(false);
                break;
            case Input.Keys.D:
                player.right(false);
                break;
            default:
                break;
        }
        return true;
    }

    private void init() {
        Gdx.input.setInputProcessor(this);
    }

    public void update(float deltaTime) {
        level.update(deltaTime);
    }

    public Level getLevel() {
        return this.level;
    }
}

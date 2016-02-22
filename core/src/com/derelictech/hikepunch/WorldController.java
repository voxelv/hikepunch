package com.derelictech.hikepunch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.derelictech.hikepunch.objects.PlayerSprite;
import com.derelictech.hikepunch.utils.HPContactListener;

/**
 * Created by Tim on 2/14/2016.
 */
public class WorldController extends InputAdapter{

    private Level level;
    private PlayerSprite player;
    private HPContactListener contactListener;

    public World world;

    public WorldController() {
        world = new World(new Vector2(0, -9.8f), true);
        level = new Level(Constants.LEVEL1, (1.0f/(Constants.TILE_PIXEL_WIDTH)), world);
        player = level.getPlayerSprite();
        contactListener = new HPContactListener(player);
        world.setContactListener(contactListener);
        init();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Input.Keys.A:
                player.moveLeft(true);
                break;
            case Input.Keys.D:
                player.moveRight(true);
                break;
            case Input.Keys.W:
                if(player.canJump()) {
                    player.jump();
                }
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
                player.moveLeft(false);
                break;
            case Input.Keys.D:
                player.moveRight(false);
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
        world.step(deltaTime, 6, 2);
        player.update(deltaTime);
    }

    public Level getLevel() {
        return this.level;
    }
}

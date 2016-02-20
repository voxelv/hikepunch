package com.derelictech.hikepunch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.physics.box2d.World;
import com.derelictech.hikepunch.objects.PlayerSprite;

/**
 * Created by Tim on 2/14/2016.
 */
public class WorldController extends InputAdapter{

    private PlayerSprite player;
    public Level level;
    private String levelname = "level1.png";

    public WorldController() {
        level = new Level(levelname, (1.0f/(Constants.TILE_PIXEL_WIDTH)));
        player = level.getPlayerSprite();
        init();
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

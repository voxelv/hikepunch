package com.derelictech.hikepunch;

import com.badlogic.gdx.InputAdapter;
import com.derelictech.hikepunch.objects.PlayerSprite;

/**
 * Created by Tim on 2/14/2016.
 */
public class WorldController extends InputAdapter{

    public Level level;
    public PlayerSprite player;
    public String levelname = "leveltest.png";

    public WorldController() {
        level = new Level(levelname, (1.0f/(Constants.TILE_PIXEL_WIDTH)));
        player = level.getPlayerSprite();
    }

    private void init() {

    }

    public void update(float deltaTime) {
        player.setPosition(player.getX() + (deltaTime * player.maxSpeed), player.getY());
    }
}

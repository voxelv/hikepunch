package com.derelictech.hikepunch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.derelictech.hikepunch.objects.PlayerSprite;

/**
 * Created by Tim on 2/14/2016.
 */
public class WorldController extends InputAdapter{

    public Level level;
    public PlayerSprite player;
    public String levelname = "level1.png";

    public WorldController() {
        level = new Level(levelname, (1.0f/(Constants.TILE_PIXEL_WIDTH)));
        player = level.getPlayerSprite();
        init();
    }

    private void init() {
        Gdx.input.setInputProcessor(this);
    }

    public void update(float deltaTime) {
        player.setPosition(player.getX() + (deltaTime * player.maxSpeed), player.getY());
        if(player.getX() > level.getLayoutWidth() - player.getWidth()) {
            player.setX(level.getLayoutWidth() - player.getWidth());
        }
    }
}

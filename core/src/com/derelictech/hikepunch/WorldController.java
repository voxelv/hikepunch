package com.derelictech.hikepunch;

import com.badlogic.gdx.InputAdapter;

/**
 * Created by Tim on 2/14/2016.
 */
public class WorldController extends InputAdapter{

    public Level level;

    public WorldController() {
        level = new Level("level1.png", (1.0f/(Constants.TILE_PIXEL_WIDTH)));
    }

    private void init() {

    }

    public void update(float deltaTime) {

    }
}

package com.derelictech.hikepunch;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/**
 * Created by Tim on 2/14/2016.
 */
public abstract class AbstractGameScreen implements Screen {

    protected Game game;

    public AbstractGameScreen(Game game) {
        this.game = game;
    }
}

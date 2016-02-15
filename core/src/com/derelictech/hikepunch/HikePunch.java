package com.derelictech.hikepunch;

import com.badlogic.gdx.Game;

public class HikePunch extends Game {

	@Override
	public void create () {
        Assets.instance.init();
        setScreen(new GameScreen(this));
	}

    @Override
    public void dispose() {
        super.dispose();
        Assets.instance.dispose();
    }
}

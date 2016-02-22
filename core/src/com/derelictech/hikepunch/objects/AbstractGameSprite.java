package com.derelictech.hikepunch.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Tim on 2/14/2016.
 */
public abstract class AbstractGameSprite extends Sprite {

    public Body body;

    public AbstractGameSprite(TextureRegion region, float x, float y, float scale) {
        super(region);
        this.setX(x);
        this.setY(y);
        this.setOrigin(0, 0);
        this.setScale(scale);
    }
}

package com.derelictech.hikepunch.objects;

import com.derelictech.hikepunch.Assets;

/**
 * Created by Tim on 2/14/2016.
 */
public class GrassSprite extends AbstractGameSprite {
    public GrassSprite(float x, float y, float scale) {
        super(Assets.instance.grass_tile, x, y, scale);
    }
}

package com.derelictech.hikepunch.objects;

import com.derelictech.hikepunch.Assets;

/**
 * Created by Tim on 2/14/2016.
 */
public class WaterSprite extends AbstractGameSprite {
    public WaterSprite(float x, float y, float scale) {
        super(Assets.instance.water_tile, x, y, scale);
    }
}
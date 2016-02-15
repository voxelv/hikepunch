package com.derelictech.hikepunch.objects;

import com.derelictech.hikepunch.Assets;

/**
 * Created by Tim on 2/14/2016.
 */
public class RockSprite extends AbstractGameSprite {
    public RockSprite(float x, float y, float scale) {
        super(Assets.instance.rock_tile, x, y, scale);
    }
}
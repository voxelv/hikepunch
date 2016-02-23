package com.derelictech.hikepunch.objects;

import com.derelictech.hikepunch.Assets;

/**
 * Created by Tim on 2/14/2016.
 */
public class DiamondSprite extends AbstractGameSprite {
    public DiamondSprite(float x, float y, float scale) {
        super(Assets.instance.diamond, x, y, scale);
    }
}
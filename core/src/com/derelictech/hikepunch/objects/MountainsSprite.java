package com.derelictech.hikepunch.objects;

import com.derelictech.hikepunch.Assets;

/**
 * Created by Tim on 2/14/2016.
 */
public class MountainsSprite extends AbstractGameSprite {
    public MountainsSprite(float x, float y, float scale) {
        super(Assets.instance.mountains, x, y, scale);
    }
}
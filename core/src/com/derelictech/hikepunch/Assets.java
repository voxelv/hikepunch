package com.derelictech.hikepunch;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Tim on 2/14/2016.
 */
public class Assets implements Disposable {

    public static final Assets instance = new Assets();

    TextureAtlas atlas;
    public TextureRegion bob;
    public TextureRegion bob_arm;
    public TextureRegion bob_leg;
    public TextureRegion diamond;
    public TextureRegion grass_tile;
    public TextureRegion mountains;
    public TextureRegion rock_tile;
    public TextureRegion stick;
    public TextureRegion tree;
    public TextureRegion tree_trunk;
    public TextureRegion tree_leaves;
    public TextureRegion water_tile;
    public TextureRegion win;
    public TextureRegion loose;
    public TextureRegion instructions;

    private Assets(){}

    public void init(){

        atlas = new TextureAtlas("../packs/pack.atlas");

        bob = atlas.findRegion("bob");
        bob_arm = atlas.findRegion("bob_arm");
        bob_leg = atlas.findRegion("bob_leg");
        diamond = atlas.findRegion("diamond");
        grass_tile = atlas.findRegion("grass_tile");
        mountains = atlas.findRegion("mountains");
        rock_tile = atlas.findRegion("rock_tile");
        stick = atlas.findRegion("stick");
        tree = atlas.findRegion("tree");
        tree_trunk = atlas.findRegion("tree_trunk");
        tree_leaves = atlas.findRegion("tree_leaves");
        water_tile = atlas.findRegion("water_tile");
        win = atlas.findRegion("win");
        loose = atlas.findRegion("loose");
        instructions = atlas.findRegion("instructions");

    }

    @Override
    public void dispose() {
        atlas.dispose();
    }
}

package com.derelictech.hikepunch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.derelictech.hikepunch.objects.*;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

/**
 * Created by Tim on 2/14/2016.
 */

@SuppressWarnings("PointlessBitwiseExpression")
public class Level {

    private float scale;
    private PlayerSprite playerSprite;
    private int layoutWidth;
    private int layoutHeight;

    private Array<AbstractGameSprite> gameObjects;

    // Color Constants---------------------------Red-----------------Green---------------Blue----------------Alpha
    private static final int startColor =  ((     255     <<24)|(     0       <<16)|(     0       <<8)|(      255     ));
    private static final int grassColor =  ((     0       <<24)|(     255     <<16)|(     0       <<8)|(      255     ));
    private static final int rockColor =   ((     128     <<24)|(     128     <<16)|(     128     <<8)|(      255     ));
    private static final int waterColor =  ((     0       <<24)|(     0       <<16)|(     255     <<8)|(      255     ));
    private static final int treeColor =   ((     128     <<24)|(     64      <<16)|(     0       <<8)|(      255     ));
    private static final int diamondColor =((     0       <<24)|(     255     <<16)|(     255     <<8)|(      255     ));

    public Level(String filename) {
        this(filename, 1.0f/ Constants.TILE_PIXEL_WIDTH);
    }

    public Level(String filename, float inScale) {
        this.scale = inScale;

        gameObjects = new Array<AbstractGameSprite>();

        gameObjects.add(new MountainsSprite(0, Constants.TILE_PIXEL_WIDTH * scale, scale));

        Pixmap levelLayout = new Pixmap(Gdx.files.internal("../level/" + filename));
        layoutWidth = levelLayout.getWidth();
        layoutHeight = levelLayout.getHeight();
        int currentPixel;
        for(int layoutX = 0; layoutX < layoutWidth;  layoutX++) {
            for(int layoutY = 0; layoutY < layoutHeight; layoutY++) {

                currentPixel = levelLayout.getPixel(layoutX, layoutY);
                float x = (layoutX * (Constants.TILE_PIXEL_WIDTH * scale));
                float y = Gdx.graphics.getHeight() - ((layoutY + 1) * (Constants.TILE_PIXEL_WIDTH * scale));

                if((layoutHeight * Constants.TILE_PIXEL_WIDTH * scale) > Gdx.graphics.getHeight()) {
                    y += (layoutHeight * Constants.TILE_PIXEL_WIDTH * scale) - Gdx.graphics.getHeight();
                }
                else y -= Gdx.graphics.getHeight() - (layoutHeight * Constants.TILE_PIXEL_WIDTH * scale);

                AbstractGameSprite s;

                switch(currentPixel) {
                    case startColor:
                        System.out.println("start");
                        if (playerSprite == null) {
                            playerSprite = new PlayerSprite(x, y, scale);
                        }
                        break;
                    case grassColor:
                        System.out.println("grass");
                        s = new GrassSprite(x, y, scale);
                        gameObjects.add(s);
                        break;
                    case rockColor:
                        System.out.println("rock");
                        s = new RockSprite(x, y, scale);
                        gameObjects.add(s);
                        break;
                    case waterColor:
                        System.out.println("water");
                        s = new WaterSprite(x, y, scale);
                        gameObjects.add(s);
                        break;
                    case treeColor:
                        System.out.println("tree");
                        break;
                    case diamondColor:
                        System.out.println("diamond");
                        break;
                    default:
                        System.out.println("OTHER: " + currentPixel);
                        break;
                }
            } // End Double For Loop
        } // End Double For Loop
        if(playerSprite == null) {
            throw new NullPointerException("No Player Spawn in Level: " + filename);
        }
    }

    public int getLayoutWidth() {
        return layoutWidth;
    }

    public int getLayoutHeight() {
        return layoutHeight;
    }

    public void renderGameObjects(SpriteBatch batch) {
        for(AbstractGameSprite s : gameObjects) {
            s.draw(batch);
        }
    }

    public PlayerSprite getPlayerSprite() {
        return playerSprite;
    }

}

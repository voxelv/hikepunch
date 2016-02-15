package com.derelictech.hikepunch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.derelictech.hikepunch.objects.*;

/**
 * Created by Tim on 2/14/2016.
 */

@SuppressWarnings("PointlessBitwiseExpression")
public class Level {

    private float scale;
    private PlayerSprite playerSprite;

    private Array<AbstractGameSprite> gameObjects;

    // Color Constants---------------------------Red-----------------Green---------------Blue----------------Alpha
    public static final int startColor =  ((     255     <<24)|(     0       <<16)|(     0       <<8)|(      255     ));
    public static final int grassColor =  ((     0       <<24)|(     255     <<16)|(     0       <<8)|(      255     ));
    public static final int rockColor =   ((     128     <<24)|(     128     <<16)|(     128     <<8)|(      255     ));
    public static final int waterColor =  ((     0       <<24)|(     0       <<16)|(     255     <<8)|(      255     ));
    public static final int treeColor =   ((     128     <<24)|(     64      <<16)|(     0       <<8)|(      255     ));
    public static final int diamondColor =((     0       <<24)|(     255     <<16)|(     255     <<8)|(      255     ));

    public Level(String filename) {
        this(filename, 1);
    }

    public Level(String filename, float inScale) {
        this.scale = inScale;

        gameObjects = new Array<AbstractGameSprite>();

        gameObjects.add(new MountainsSprite(0, Constants.TILE_PIXEL_WIDTH * scale, scale));

        Pixmap levelLayout = new Pixmap(Gdx.files.internal("../level/" + filename));
        int currentPixel;
        for(int layoutX = 0; layoutX < levelLayout.getWidth();  layoutX++) {
            for(int layoutY = 0; layoutY < levelLayout.getHeight(); layoutY++) {

                currentPixel = levelLayout.getPixel(layoutX, layoutY);
                float x = (layoutX * (Constants.TILE_PIXEL_WIDTH * scale));
                float y = Gdx.graphics.getHeight() - ((layoutY + 1) * (Constants.TILE_PIXEL_WIDTH * scale));

                if((levelLayout.getHeight() * Constants.TILE_PIXEL_WIDTH * scale) > Gdx.graphics.getHeight()) {
                    y += (levelLayout.getHeight() * Constants.TILE_PIXEL_WIDTH * scale) - Gdx.graphics.getHeight();
                }
                else y -= Gdx.graphics.getHeight() - (levelLayout.getHeight() * Constants.TILE_PIXEL_WIDTH * scale);

                AbstractGameSprite s;

                switch(currentPixel) {
                    case startColor:
                        System.out.println("start");
                        if (playerSprite == null) {
                            playerSprite = new PlayerSprite(x, y, scale);
                            gameObjects.add(playerSprite);
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
    }

    public void renderGameObjects(SpriteBatch batch) {
        for(AbstractGameSprite s : gameObjects) {
            s.draw(batch);
        }
    }

    public void rescale(float newScale) {
        this.scale = newScale;
        for(AbstractGameSprite s : gameObjects) {
            s.setScale(newScale, newScale);
        }
    }

    public PlayerSprite getPlayerSprite() {
        return playerSprite;
    }

}

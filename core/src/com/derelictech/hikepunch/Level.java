package com.derelictech.hikepunch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.derelictech.hikepunch.objects.*;
import com.derelictech.hikepunch.utils.Box2DFactory;

/**
 * Created by Tim on 2/14/2016.
 */

@SuppressWarnings("PointlessBitwiseExpression")
public class Level {

    private float scaleFactor;
    private World world;
    private PlayerSprite player;
    private int layoutWidth;
    private int layoutHeight;

    private class InstructionsSprite extends AbstractGameSprite {
        public InstructionsSprite(float x, float y, float scale) {
            super(Assets.instance.instructions, x, y, scale);
        }
    }
    private InstructionsSprite instruct;

    public boolean win = false;
    public boolean loose = false;

    private Array<AbstractGameSprite> terrain;
    private Array<AbstractGameSprite> water;
    private Array<TreeSprite> trees;
    private Array<AbstractGameSprite> diamonds;

    // Color Constants---------------------------Red-----------------Green---------------Blue----------------Alpha
    private static final int startColor =  ((     255     <<24)|(     0       <<16)|(     0       <<8)|(      255     ));
    private static final int grassColor =  ((     0       <<24)|(     255     <<16)|(     0       <<8)|(      255     ));
    private static final int rockColor =   ((     128     <<24)|(     128     <<16)|(     128     <<8)|(      255     ));
    private static final int waterColor =  ((     0       <<24)|(     0       <<16)|(     255     <<8)|(      255     ));
    private static final int treeColor =   ((     128     <<24)|(     64      <<16)|(     0       <<8)|(      255     ));
    private static final int diamondColor =((     0       <<24)|(     255     <<16)|(     255     <<8)|(      255     ));

    public Level(String filename, float scale, World w) {
        this.scaleFactor = scale;
        this.world = w;

        terrain = new Array<AbstractGameSprite>();
        water = new Array<AbstractGameSprite>();
        trees = new Array<TreeSprite>();
        diamonds = new Array<AbstractGameSprite>();

        terrain.add(new MountainsSprite(0, Constants.TILE_PIXEL_WIDTH * scaleFactor, scaleFactor));

        instruct = new InstructionsSprite(0, 10, scaleFactor/6);

        Pixmap levelLayout = new Pixmap(Gdx.files.internal("../level/" + filename));
        layoutWidth = levelLayout.getWidth();
        layoutHeight = levelLayout.getHeight();

        Shape levelLeftShape = Box2DFactory.createBoxShape(Constants.TILE_PIXEL_WIDTH*scaleFactor/2, Constants.VIEWPORT_HEIGHT, new Vector2(0, 0), 0);
        FixtureDef levelLeftFixtureDef = Box2DFactory.createFixture(levelLeftShape, 100.0f, 0.0f, 0.0f, false);
        Body levelLeftBound = Box2DFactory.createBody(world, BodyType.StaticBody, levelLeftFixtureDef, new Vector2(-scaleFactor*Constants.TILE_PIXEL_WIDTH/2, Constants.VIEWPORT_HEIGHT/2));

        AbstractGameSprite s;
        Shape shape;
        FixtureDef fd;
        Fixture f;
        int treeID = 0;

        Vector2 playerPosition = null;

        // Create Player before all others
        player = new PlayerSprite(world, 5, 5, scaleFactor);
        shape = Box2DFactory.createBoxShape(
                scaleFactor*player.getWidth()/2.0f,
                scaleFactor*player.getHeight()/2.0f,
                new Vector2(scaleFactor*player.getWidth()/2,scaleFactor*player.getHeight()/2),
                0 // Rotation
        );
        fd = Box2DFactory.createFixture(shape, 1.0f, 1.0f, 0f, false);
        player.body = Box2DFactory.createBody(world, BodyType.DynamicBody, fd, new Vector2(5, 5 + 1), Constants.USERDATA.PLAYER);
        player.body.setFixedRotation(true);
        shape = Box2DFactory.createBoxShape(
                scaleFactor*player.getWidth()/2.5f,
                scaleFactor*player.getHeight()/30,
                new Vector2(scaleFactor*player.getWidth()/2, 0),
                0 // Rotation
        );
        fd = Box2DFactory.createFixture(shape, 0.0f, 0.0f, 0.0f, true);
        f = player.body.createFixture(fd);
        f.setUserData(Constants.USERDATA.PLAYER_FOOT_SENSOR);

        int currentPixel;
        for(int layoutX = 0; layoutX < layoutWidth;  layoutX++) {
        for(int layoutY = 0; layoutY < layoutHeight; layoutY++) {

            currentPixel = levelLayout.getPixel(layoutX, layoutY);
            float x = (layoutX * (Constants.TILE_PIXEL_WIDTH * scaleFactor));
            float y = Gdx.graphics.getHeight() - ((layoutY + 1) * (Constants.TILE_PIXEL_WIDTH * scaleFactor));

            if((layoutHeight * Constants.TILE_PIXEL_WIDTH * scaleFactor) > Gdx.graphics.getHeight()) {
                y += (layoutHeight * Constants.TILE_PIXEL_WIDTH * scaleFactor) - Gdx.graphics.getHeight();
            }
            else y -= Gdx.graphics.getHeight() - (layoutHeight * Constants.TILE_PIXEL_WIDTH * scaleFactor);

            switch(currentPixel) {
                case startColor:
                    if (playerPosition == null) {
                        playerPosition = new Vector2(x, y);
                    }
                    break;
                case grassColor:
                    s = new GrassSprite(x, y, scaleFactor);
                    shape = Box2DFactory.createBoxShape(
                            scaleFactor*s.getWidth()/2.1f,
                            scaleFactor*s.getHeight()/2,
                            new Vector2(scaleFactor*s.getWidth()/2,scaleFactor*s.getHeight()/2),
                            0 // Rotation
                    );
                    fd = Box2DFactory.createFixture(shape, 10.0f, 5.0f, 0.0f, false);
                    s.body = Box2DFactory.createBody(world, BodyType.StaticBody, fd, new Vector2(x, y), Constants.USERDATA.GRASS);
                    shape = Box2DFactory.createTileLeftShape();
                    fd = Box2DFactory.createFixture(shape, 0.0f, 0.0f, 0.0f, false);
                    s.body.createFixture(fd);
                    shape = Box2DFactory.createTileRightShape();
                    fd = Box2DFactory.createFixture(shape, 0.0f, 0.0f, 0.0f, false);
                    s.body.createFixture(fd);
                    terrain.add(s);
                    break;
                case rockColor:
                    s = new RockSprite(x, y, scaleFactor);
                    shape = Box2DFactory.createBoxShape(
                            scaleFactor*s.getWidth()/2.1f,
                            scaleFactor*s.getHeight()/2,
                            new Vector2(scaleFactor*s.getWidth()/2,scaleFactor*s.getHeight()/2),
                            0 // Rotation
                    );
                    fd = Box2DFactory.createFixture(shape, 10.0f, 5.0f, 0.0f, false);
                    s.body = Box2DFactory.createBody(world, BodyType.StaticBody, fd, new Vector2(x, y), Constants.USERDATA.GRASS);
                    shape = Box2DFactory.createTileLeftShape();
                    fd = Box2DFactory.createFixture(shape, 0.0f, 0.0f, 0.0f, false);
                    s.body.createFixture(fd);
                    shape = Box2DFactory.createTileRightShape();
                    fd = Box2DFactory.createFixture(shape, 0.0f, 0.0f, 0.0f, false);
                    s.body.createFixture(fd);
                    terrain.add(s);
                    break;
                case waterColor:
                    y -= 0.2f; // Lower water level a tad
                    s = new WaterSprite(x, y, scaleFactor);
                    shape = Box2DFactory.createBoxShape(
                            scaleFactor*s.getWidth()/2,
                            scaleFactor*s.getHeight()/2,
                            new Vector2(scaleFactor*s.getWidth()/2,scaleFactor*s.getHeight()/2),
                            0 // Rotation
                    );
                    fd = Box2DFactory.createFixture(shape, 0.5f, 0.4f, 0f, true);
                    s.body = Box2DFactory.createBody(world, BodyType.StaticBody, fd, new Vector2(x, y), Constants.USERDATA.WATER);
                    water.add(s);
                    break;
                case treeColor:
                    s = new TreeSprite(treeID, x, y, scaleFactor);
                    TreeSprite ts;
                    ts = (TreeSprite) s;
                    shape = Box2DFactory.createBoxShape(
                            scaleFactor*ts.getWidth()/2.05f,
                            scaleFactor*ts.getHeight()/2,
                            new Vector2(scaleFactor*ts.getWidth()/2,scaleFactor*ts.getHeight()/2),
                            0 // Rotation
                    );
                    fd = Box2DFactory.createFixture(shape, 10.0f, 5.0f, 0.0f, false);
                    ts.body = Box2DFactory.createBody(world, BodyType.StaticBody, fd, new Vector2(x, y), Constants.USERDATA.TREE+treeID);
                    shape = Box2DFactory.createTileLeftShape();
                    fd = Box2DFactory.createFixture(shape, 0.0f, 0.0f, 0.0f, false);
                    ts.body.createFixture(fd);
                    shape = Box2DFactory.createTileRightShape();
                    fd = Box2DFactory.createFixture(shape, 0.0f, 0.0f, 0.0f, false);
                    ts.body.createFixture(fd);
                    ts.setBodies(world);
                    trees.add(ts);
                    treeID++;
                    break;
                case diamondColor:
                    s = new DiamondSprite(x, y, scaleFactor);
                    shape = Box2DFactory.createBoxShape(
                            scaleFactor*s.getWidth()/2,
                            scaleFactor*s.getHeight()/2,
                            new Vector2(scaleFactor*s.getWidth()/2,scaleFactor*s.getHeight()/2),
                            0 // Rotation
                    );
                    fd = Box2DFactory.createFixture(shape, 0.5f, 0.4f, 0f, true);
                    s.body = Box2DFactory.createBody(world, BodyType.StaticBody, fd, new Vector2(x, y), Constants.USERDATA.DIAMOND);
                    diamonds.add(s);
                    break;
                case 0x00FF: // Black, with alpha = 255
                    break;
                default:
                    System.out.println("OTHER: " + currentPixel);
                    break;
            }
        } // End Inner For Loop
        } // End Double For Loop
        if(playerPosition == null) {
            throw new NullPointerException("No Player Spawn in Level: " + filename);
        }

        // Set player position after finding where
        player.body.setTransform(playerPosition.x, playerPosition.y, 0);
        player.init();

        terrain.add(instruct);
    }

    public void updateTrees(float deltaTime) {
        for(int i = 0; i < trees.size; i++) {
            trees.get(i).update(world, deltaTime);
            if(trees.get(i).deleted) {
                trees.removeIndex(i);
            }
        }
    }

    public int getLayoutWidth() {
        return layoutWidth;
    }

    public int getLayoutHeight() {
        return layoutHeight;
    }

    public void render(SpriteBatch batch) {
        for(AbstractGameSprite s : terrain) {
            s.draw(batch);
        }
        for(AbstractGameSprite s : water) {
            s.draw(batch);
        }
        for(AbstractGameSprite s : trees) {
            s.draw(batch);
        }
        for(AbstractGameSprite s : diamonds) {
            s.draw(batch);
        }

        player.draw(batch);
    }

    public PlayerSprite getPlayerSprite() {
        return player;
    }

    public void punchTree(int ID) {
        for(int i = 0; i < trees.size; i++) {
            if(trees.get(i).ID == ID) {
                trees.get(i).delete = true;
            }
        }
    }

}

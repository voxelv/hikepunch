package com.derelictech.hikepunch.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.derelictech.hikepunch.Assets;
import com.derelictech.hikepunch.Constants;
import com.derelictech.hikepunch.utils.Box2DFactory;

/**
 * Created by Tim on 2/14/2016.
 */
public class TreeSprite extends AbstractGameSprite {

    private Array<AbstractGameSprite> pieces;
    private Array<Body> bodies;
    private float scaleFactor;
    public int ID;
    public boolean delete = false;
    public boolean deleted = false;


    public TreeSprite(int ID, float x, float y, float scale) {
        super(Assets.instance.tree_trunk, x, y, scale);
        this.ID = ID;
        scaleFactor = scale;
        pieces = new Array<AbstractGameSprite>();
        bodies = new Array<Body>();
        createPieces();
        System.out.println("Tree Created with ID: " + this.ID);
    }

    private class LeavesSprite extends AbstractGameSprite {

        public LeavesSprite(float x, float y, float scale) {
            super(Assets.instance.tree_leaves, x, y, scale);
        }
    }

    private class TrunkSprite extends AbstractGameSprite {

        public TrunkSprite(float x, float y, float scale) {
            super(Assets.instance.tree_trunk, x, y, scale);
        }
    }

    public void createPieces() {
        Array<Vector2> leaves = new Array<Vector2>();
        Array<Vector2> trunk = new Array<Vector2>();
        leaves.add(new Vector2(-1, 8));
        leaves.add(new Vector2(0, 8));
        leaves.add(new Vector2(1, 8));

        leaves.add(new Vector2(-2, 7));
        leaves.add(new Vector2(-1, 7));
        leaves.add(new Vector2(1, 7));
        leaves.add(new Vector2(2, 7));

        leaves.add(new Vector2(-3, 6));
        leaves.add(new Vector2(-2, 6));
        leaves.add(new Vector2(-1, 6));
        leaves.add(new Vector2(1, 6));
        leaves.add(new Vector2(2, 6));
        leaves.add(new Vector2(3, 6));

        leaves.add(new Vector2(-3, 5));
        leaves.add(new Vector2(-2, 5));
        leaves.add(new Vector2(-1, 5));
        leaves.add(new Vector2(1, 5));
        leaves.add(new Vector2(2, 5));
        leaves.add(new Vector2(3, 5));

        leaves.add(new Vector2(-2, 4));
        leaves.add(new Vector2(-1, 4));
        leaves.add(new Vector2(1, 4));
        leaves.add(new Vector2(2, 4));

        for(float i = 1; i < 8f; i+=1f) {
            trunk.add(new Vector2(0, i));
        }

        for(Vector2 v : trunk) {
            pieces.add(new TrunkSprite(v.x + this.getX(), v.y + this.getY(), scaleFactor));
        }

        for(Vector2 v : leaves) {
            pieces.add(new LeavesSprite(v.x + this.getX(), v.y + this.getY(), scaleFactor));
        }
    }

    public void setBodies(World world) {
        Shape shape;
        FixtureDef fd;

        for(AbstractGameSprite s : pieces) {
            shape = Box2DFactory.createBoxShape(
                    scaleFactor*s.getWidth()/2.1f,
                    scaleFactor*s.getHeight()/2,
                    new Vector2(scaleFactor*s.getWidth()/2,scaleFactor*s.getHeight()/2),
                    0 // Rotation
            );
            fd = Box2DFactory.createFixture(shape, 0.5f, 5.0f, 0.1f, false);
            s.body = Box2DFactory.createBody(world, BodyDef.BodyType.StaticBody, fd, new Vector2(s.getX(), s.getY()), Constants.USERDATA.TREE+ID);
            bodies.add(s.body);
            shape = Box2DFactory.createTileLeftShape();
            fd = Box2DFactory.createFixture(shape, 0.0f, 0.0f, 0.0f, false);
            s.body.createFixture(fd);
            shape = Box2DFactory.createTileRightShape();
            fd = Box2DFactory.createFixture(shape, 0.0f, 0.0f, 0.0f, false);
            s.body.createFixture(fd);
        }
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        for(AbstractGameSprite s : pieces) {
            s.draw(batch);
        }
    }

    public void update(World world, float deltaTime) {
        if(delete) {

            System.out.println("Deleting tree: " + ID);

            for(Body b : bodies) {
                world.destroyBody(b);
            }
            world.destroyBody(body);
            deleted = true;
        }
    }
}
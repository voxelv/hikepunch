package com.derelictech.hikepunch.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
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


    public TreeSprite(float x, float y, float scale) {
        super(Assets.instance.tree_trunk, x, y, scale);
        scaleFactor = scale;
        pieces = new Array<AbstractGameSprite>();
        createPieces();
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
            s.body = Box2DFactory.createBody(world, BodyDef.BodyType.StaticBody, fd, new Vector2(s.getX(), s.getY()), Constants.USERDATA.GRASS);
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

    public void update(float deltaTime) {
        for(AbstractGameSprite s : pieces) {
            s.setPosition(s.body.getPosition().x, s.body.getPosition().y);
            s.setRotation(s.body.getAngle()*180/MathUtils.PI);
        }
    }
}
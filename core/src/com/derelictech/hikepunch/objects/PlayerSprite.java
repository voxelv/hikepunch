package com.derelictech.hikepunch.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.derelictech.hikepunch.Assets;

/**
 * Created by Tim on 2/14/2016.
 */
public class PlayerSprite extends AbstractGameSprite {

    private class BobLimb extends Sprite {
        public BobLimb(TextureRegion region, float x, float y, float scale) {
            super(region);
            setPosition(x, y);
            setOrigin(2.5f, 9.5f);
            setScale(scale);
        }

        public float time = 0;

        @Override
        public void setPosition(float x, float y) {
            super.setPosition(x - this.getOriginX(), y - this.getOriginY());
        }
    }

    private static class DIR {
        public static int RIGHT = 1;
        public static int LEFT = -1;
    }

    private boolean movingLeft = false;
    private boolean movingRight = false;

    private float scaleFactor;

    private Vector2 shoulderJoint;
    private Vector2 hipJoint;

    private float maxAccelHorz = 12;
    private float velocity = 0;
    private float horzAcceleration = 0;
    private int dir = DIR.RIGHT;

    private float swingAngle = 0;

    private BobLimb bob_arm_right;
    private BobLimb bob_arm_left;

    private BobLimb bob_leg_right;
    private BobLimb bob_leg_left;

    public PlayerSprite(float x, float y, float scale) {
        super(Assets.instance.bob, x, y, scale);
        this.scaleFactor = scale;

        shoulderJoint = new Vector2(4.5f*scale, 19.5f*scale);
        hipJoint = new Vector2(4.5f*scale, 9.5f*scale);

        bob_arm_right = new BobLimb(Assets.instance.bob_arm, x + shoulderJoint.x, y + shoulderJoint.y, scale);
        bob_arm_left = new BobLimb(Assets.instance.bob_arm, x +  shoulderJoint.x, y + shoulderJoint.y, scale);
        bob_leg_right = new BobLimb(Assets.instance.bob_leg, x + hipJoint.x, y + hipJoint.y, scale);
        bob_leg_left = new BobLimb(Assets.instance.bob_leg, x + hipJoint.x, y + hipJoint.y, scale);
    }

    @Override
    public void draw(Batch batch) {
        bob_arm_left.draw(batch);
        bob_leg_left.draw(batch);
        super.draw(batch);
        bob_leg_right.draw(batch);
        bob_arm_right.draw(batch);
    }
    
    public void update(float deltaTime) {

        // Horizontal Movement
        if(movingRight ^ movingLeft) {
            if(movingRight) {
                dir = DIR.RIGHT;
                flipX(false);
            }
            else {
                dir = DIR.LEFT;
                flipX(true);
            }
            swingAngle = MathUtils.cos(bob_arm_left.time += deltaTime * 9) * 55;
            setSwing(swingAngle);
        }
        else {
            swingAngle = 0;
            setSwing(swingAngle);
        }
        velocity = horzAcceleration * deltaTime;
        setPosition(getX() + dir * (0.5f * horzAcceleration * deltaTime + velocity * deltaTime), getY());
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        bob_arm_left.setPosition(x + shoulderJoint.x, y + shoulderJoint.y);
        bob_arm_right.setPosition(x + shoulderJoint.x, y + shoulderJoint.y);
        bob_leg_left.setPosition(x + hipJoint.x, y + hipJoint.y);
        bob_leg_right.setPosition(x + hipJoint.x, y + hipJoint.y);
    }

    private void setSwing(float angle) {
        bob_arm_left.setRotation(angle);
        bob_arm_right.setRotation(-angle);
        bob_leg_left.setRotation(-angle);
        bob_leg_right.setRotation(angle);
    }

    public void flipX(boolean b) {
            this.setFlip(b, false);
            bob_arm_left.setFlip(b, false);
            bob_arm_right.setFlip(b, false);
            bob_leg_left.setFlip(b, false);
            bob_leg_right.setFlip(b, false);
    }

    public void left(boolean b) {
        movingLeft = b;
        horzAcceleration =  b ? maxAccelHorz : 0;
    }

    public void right(boolean b) {
        movingRight = b;
        horzAcceleration = b ? maxAccelHorz : 0;
    }
}

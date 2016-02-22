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
            setOrigin(2.5f, 9.5f);
            setScale(scale);
            setPosition(x, y);
        }

        @Override
        public void setPosition(float x, float y) {
            super.setPosition(x - this.getOriginX(), y - this.getOriginY());
        }
    }

    private enum State {GROUNDED, AIRBORNE}
    private State jumpState;

    private float scaleFactor;

    private Vector2 shoulderJoint;
    private Vector2 hipJoint;

    private float swingAngle = 0;
    private float timeAccumulator = 0;
    private boolean flipped = false;

    private Vector2 jumpVector = new Vector2(0, 7.0f);
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private float maxXVelocityGround = 7.0f;
    private float maxXVelocityAir = 4.5f;
    private float maxXVelocity = maxXVelocityGround;
    private float xMoveForce = 100.0f;

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
        setPosition(body.getPosition().x, body.getPosition().y);

        if(movingLeft ^ movingRight) {
            moveX();
        }
        int neg = flipped ? -1 : 1;
        if(body.getLinearVelocity().x > 0.25f) {
            flipX(false);
        }
        else if(body.getLinearVelocity().x < -0.25f) {
            flipX(true);
        }

        if(jumpState == State.GROUNDED) {
            swingAngle = MathUtils.cos(timeAccumulator += (deltaTime * 7 * (body.getLinearVelocity().x / maxXVelocity))) * 55 * (body.getLinearVelocity().x / maxXVelocity);
            setSwing(swingAngle);
        }
        else {
            bob_arm_left.setRotation(-20 * neg);
            bob_arm_right.setRotation(130 * neg);
            bob_leg_left.setRotation(20 * neg);
            bob_leg_right.setRotation(-20 * neg);
        }

    }

    public void moveX() {
        if (movingRight && body.getLinearVelocity().x < maxXVelocity)
            body.applyForceToCenter(xMoveForce, 0, true);
        if(movingLeft && body.getLinearVelocity().x > -maxXVelocity)
            body.applyForceToCenter(-xMoveForce, 0, true);
        body.setTransform(body.getPosition().x, body.getPosition().y + 0.001f, body.getAngle());
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
        flipped = b;
    }

    public boolean canJump() {
        return jumpState == State.GROUNDED;
    }

    public void jump() {
        body.applyLinearImpulse(jumpVector, body.getLocalCenter(), true);
    }

    public void enableJump(boolean b) {
        jumpState = b ? State.GROUNDED : State.AIRBORNE;
        maxXVelocity = b ? maxXVelocityGround : maxXVelocityAir;
        if(body.getLinearVelocity().x > maxXVelocity) {
            body.setLinearVelocity(maxXVelocity, body.getLinearVelocity().y);
        }
    }

    public void moveLeft(boolean b) {
        movingLeft = b;
    }

    public void moveRight(boolean b) {
        movingRight = b;
    }
}

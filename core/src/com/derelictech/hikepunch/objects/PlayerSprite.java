package com.derelictech.hikepunch.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.derelictech.hikepunch.Assets;

/**
 * Created by Tim on 2/14/2016.
 */
public class PlayerSprite extends AbstractGameSprite {
    private class BobArm extends Sprite {
        public BobArm(TextureRegion region, float x, float y, float scale) {
            super(region);
            setOrigin(-2.5f*scale, -10.5f*scale);
            setPosition(x, y);
        }
    }

    public Vector2 shoulderJoint;

    BobArm bob_arm_right;
    BobArm bob_arm_left;

    public PlayerSprite(float x, float y, float scale) {
        super(Assets.instance.bob, x, y, scale);

        shoulderJoint = new Vector2(this.getX() + (4.5f * scale), this.getY() + (19.5f * scale));

        bob_arm_right = new BobArm(Assets.instance.bob_arm, x, y, scale);
        //bob_arm_right.setOrigin(shoulderJoint.x, shoulderJoint.y);
        //bob_arm_right.setRotation(45);
        bob_arm_right.setPosition(shoulderJoint.x, shoulderJoint.y);
        bob_arm_right.setScale(scale);
        bob_arm_right.setOrigin(-2.5f * scale, -10.5f * scale);
        bob_arm_right.setRotation(15);

        bob_arm_left = new BobArm(Assets.instance.bob_arm, shoulderJoint.x, shoulderJoint.y, scale);
    }

    @Override
    public void draw(Batch batch) {
        bob_arm_left.draw(batch);
        super.draw(batch);
        bob_arm_right.draw(batch);
    }
}

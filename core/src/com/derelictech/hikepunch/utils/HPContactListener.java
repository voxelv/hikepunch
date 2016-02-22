package com.derelictech.hikepunch.utils;

import com.badlogic.gdx.physics.box2d.*;
import com.derelictech.hikepunch.Constants;
import com.derelictech.hikepunch.objects.PlayerSprite;

/**
 * Created by Tim on 2/21/2016.
 */
public class HPContactListener implements ContactListener {

    private PlayerSprite player;

    public HPContactListener(PlayerSprite player) {
        this.player = player;
    }

    private int footTouchCount = 0;

    @Override
    public void beginContact(Contact contact) {
        String userDataA;
        String userDataB;
        if(contact.getFixtureA().getUserData() instanceof String) userDataA = (String) contact.getFixtureA().getUserData();
        else userDataA = "";
        if(contact.getFixtureB().getUserData() instanceof String) userDataB = (String) contact.getFixtureB().getUserData();
        else userDataB = "";

        // Collision between Player Foot and Grass
        if((userDataA.equals(Constants.USERDATA.PLAYER_FOOT_SENSOR) && userDataB.equals(Constants.USERDATA.GRASS)) ||
           (userDataB.equals(Constants.USERDATA.GRASS) && userDataA.equals(Constants.USERDATA.PLAYER_FOOT_SENSOR))) {
            footTouchCount++;
            if(footTouchCount > 0) player.enableJump(true);
        }
    }

    @Override
    public void endContact(Contact contact) {
        String userDataA;
        String userDataB;
        if(contact.getFixtureA().getUserData() instanceof String) userDataA = (String) contact.getFixtureA().getUserData();
        else userDataA = "";
        if(contact.getFixtureB().getUserData() instanceof String) userDataB = (String) contact.getFixtureB().getUserData();
        else userDataB = "";

        // Collision between Player Foot and Grass
        if((userDataA.equals(Constants.USERDATA.PLAYER_FOOT_SENSOR) && userDataB.equals(Constants.USERDATA.GRASS)) ||
           (userDataB.equals(Constants.USERDATA.GRASS) && userDataA.equals(Constants.USERDATA.PLAYER_FOOT_SENSOR))) {
            footTouchCount--;
            if(footTouchCount > 0) player.enableJump(true);
            else player.enableJump(false);
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public void setPlayer(PlayerSprite p) {
        player = p;
    }
}

package com.derelictech.hikepunch.utils;

import com.badlogic.gdx.physics.box2d.*;
import com.derelictech.hikepunch.Constants;
import com.derelictech.hikepunch.Level;
import com.derelictech.hikepunch.objects.PlayerSprite;

/**
 * Created by Tim on 2/21/2016.
 */
public class HPContactListener implements ContactListener {

    private PlayerSprite player;
    private Level level;

    public HPContactListener(PlayerSprite player, Level level) {
        this.player = player;
        this.level = level;
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
            player.body.setLinearVelocity(player.body.getLinearVelocity().x, 0);
        }


        // Collision between Player Arm and TreeID
        if(userDataA.length() > 0 && userDataA.charAt(0) == Constants.USERDATA.TREE.charAt(0) &&
                userDataB.equals(Constants.USERDATA.PLAYER_ARM_SENSOR)) {
            level.punchTree(Integer.parseInt(userDataA.substring(1)));
        }
        if(userDataB.length() > 0 && userDataB.charAt(0) == Constants.USERDATA.TREE.charAt(0) &&
                userDataA.equals(Constants.USERDATA.PLAYER_ARM_SENSOR)) {
            level.punchTree(Integer.parseInt(userDataB.substring(1)));
        }


        // Collision between Player and Water
        if((userDataA.equals(Constants.USERDATA.PLAYER) && userDataB.equals(Constants.USERDATA.WATER)) ||
                (userDataB.equals(Constants.USERDATA.WATER) && userDataA.equals(Constants.USERDATA.PLAYER))) {
            level.loose = true;
            System.out.println("LOOSE#######################################################################");
        }


        // Collision between Player and Diamond
        if((userDataA.equals(Constants.USERDATA.PLAYER) && userDataB.equals(Constants.USERDATA.DIAMOND)) ||
                (userDataB.equals(Constants.USERDATA.DIAMOND) && userDataA.equals(Constants.USERDATA.PLAYER))) {
            level.win = true;
            System.out.println("WIN#######################################################################");
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

package com.derelictech.hikepunch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.derelictech.hikepunch.objects.PlayerSprite;

/**
 * Created by Tim on 2/14/2016.
 */
public class WorldRenderer implements Disposable{

    OrthographicCamera camera;

    WorldController worldController;
    SpriteBatch batch;

    public WorldRenderer(WorldController worldController) {
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.update();

        this.worldController = worldController;
        batch = new SpriteBatch();
    }

    private void init() {

    }

    public void render() {
        Gdx.gl.glClearColor(0, 0.8f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        float xpos = worldController.level.getPlayerSprite().getX();
        if(xpos < camera.viewportWidth/2) {
            xpos = camera.viewportWidth/2;
        }
        if(xpos > worldController.level.getLayoutWidth() - (camera.viewportWidth/2)) {
            xpos = worldController.level.getLayoutWidth() - (camera.viewportWidth/2);
        }
        camera.position.set(xpos, Constants.VIEWPORT_HEIGHT/2, 0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        worldController.level.renderGameObjects(batch);
        worldController.player.draw(batch);
        batch.end();
    }

    public void resize(int width, int height) {
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
        System.out.println("W: "+ width +" H: "+ height);
        float xpos = worldController.player.getX();
        if(xpos < camera.viewportWidth/2) {
            xpos = camera.viewportWidth/2;
        }
        if(xpos > worldController.level.getLayoutWidth() - (camera.viewportWidth/2)) {
            xpos = worldController.level.getLayoutWidth() - (camera.viewportWidth/2);
        }
        camera.position.set(xpos, Constants.VIEWPORT_HEIGHT/2, 0);
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}

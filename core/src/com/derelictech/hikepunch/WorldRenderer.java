package com.derelictech.hikepunch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Tim on 2/14/2016.
 */
public class WorldRenderer implements Disposable{

    OrthographicCamera camera;

    WorldController worldController;
    SpriteBatch batch;

    public WorldRenderer(WorldController worldController) {
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        camera.update();

        this.worldController = worldController;
        batch = new SpriteBatch();
    }

    private void init() {

    }

    public void render() {
        Gdx.gl.glClearColor(0, 0.8f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        worldController.level.renderGameObjects(batch);
        batch.end();
    }

    public void resize(int width, int height) {
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
        System.out.println("W: "+ width +" H: "+ height);
        camera.position.set(4, 2, 0);
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}

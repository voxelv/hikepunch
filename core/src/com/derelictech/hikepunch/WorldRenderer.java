package com.derelictech.hikepunch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.derelictech.hikepunch.objects.PlayerSprite;

/**
 * Created by Tim on 2/14/2016.
 */
public class WorldRenderer implements Disposable{

    OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    WorldController worldController;
    Level level;
    
    SpriteBatch batch;

    public WorldRenderer(WorldController worldController) {
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.update();

        this.worldController = worldController;
        level = worldController.getLevel();
        batch = new SpriteBatch();
    }

    private void init() {

    }

    public void render() {
        Gdx.gl.glClearColor(0, 0.8f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        float xpos = level.getPlayerSprite().getX();
        if(xpos < camera.viewportWidth/2) {
            xpos = camera.viewportWidth/2;
        }
        if(xpos > level.getLayoutWidth() - (camera.viewportWidth/2)) {
            xpos = level.getLayoutWidth() - (camera.viewportWidth/2);
        }
        camera.position.set(xpos, Constants.VIEWPORT_HEIGHT/2, 0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        level.render(batch);
        batch.end();

        debugRenderer.render(worldController.world, camera.combined);
    }

    public void resize(int width, int height) {
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;

        float xpos = level.getPlayerSprite().getX();
        if(xpos < camera.viewportWidth/2) {
            xpos = camera.viewportWidth/2;
        }
        if(xpos > level.getLayoutWidth() - (camera.viewportWidth/2)) {
            xpos = level.getLayoutWidth() - (camera.viewportWidth/2);
        }
        camera.position.set(xpos, Constants.VIEWPORT_HEIGHT/2, 0);
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}

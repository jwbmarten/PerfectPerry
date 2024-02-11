package com.jwb.perfectWorld;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledGameMap extends GameMap {

    TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;

    public TiledGameMap() {

        tiledMap = new TmxMapLoader().load("DemoShopV1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

    }

    @Override
    public void render(OrthographicCamera camera) {

        //telling renderer what camera to use
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {

        tiledMap.dispose();
    }

    /**
     * Gets a tile at its coordinate within the map at a specified layer.
     *
     * @param layer
     * @param col
     * @param row
     * @return
     */
    @Override
    public TileType getTileTypeByCoordinate(int layer, int col, int row) {


        return null;
    }

    @Override
    public int getWidth() {


        return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getWidth();
    }

    @Override
    public int getHeight() {


        return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getHeight();
    }

    @Override
    public int getLayers() {


        return tiledMap.getLayers().getCount();
    }
}

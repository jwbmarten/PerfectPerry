package com.jwb.perfectWorld;

import static com.jwb.perfectWorld.TileType.TILE_SIZE;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
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
        TileType.loadTileTypes(tiledMap); // Make sure to load tile types after loading the map
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
        TiledMapTileLayer tiledLayer = (TiledMapTileLayer) tiledMap.getLayers().get(layer);
        TiledMapTileLayer.Cell cell = tiledLayer.getCell(col, row);

        if (cell != null && cell.getTile() != null) {
            int id = cell.getTile().getId();
            return TileType.getTileTypeById(id);
        }

        return null;
    }


    public boolean isTileCollidable(float x, float y) {
        int tileX = (int) (x / TILE_SIZE);
        int tileY = (int) (y / TILE_SIZE);

        // FOR DEBUG
        //System.out.println("tileX: " + tileX + "tileY: " + tileY);


        // Assuming layer 0 for simplicity; adjust as needed for your game
        TileType tileType = getTileTypeByCoordinate(0, tileX, tileY);

        // FOR DEBUG
        //System.out.println("Tile ID: " + tileType.toString());

        return tileType != null && tileType.isCollidable(tileType);
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

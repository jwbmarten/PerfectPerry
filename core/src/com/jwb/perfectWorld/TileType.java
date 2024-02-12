package com.jwb.perfectWorld;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;

import java.util.HashMap;

public class TileType {

    public static final int TILE_SIZE = 32;

    private int id;
    private boolean collidable;
    private String name;
    private float damage;

    public TileType(int id, boolean collidable, String name, float damage) {
        this.id = id;
        this.collidable = collidable;
        this.name = name;
        this.damage = damage;
    }

    // Getters...

    private static HashMap<Integer, TileType> tileMap = new HashMap<Integer, TileType>();

    // This method should be called after loading the map
    public static void loadTileTypes(TiledMap tiledMap) {
        tileMap.clear(); // Clear existing tile types
        for (TiledMapTileSet tileset : tiledMap.getTileSets()) {
            for (TiledMapTile tile : tileset) {
                MapProperties props = tile.getProperties();
                int id = tile.getId();
                boolean collidable = props.containsKey("collidable") && props.get("collidable", Boolean.class);
                //String name = props.get("name", String.class, "Unknown");
                String name = "Unknown";
                //float damage = props.get("damage", Float.class, 0f);
                float damage = 0;

                tileMap.put(id, new TileType(id, collidable, name, damage));
            }
        }
    }

    @Override
    public String toString(){

        String tileinfo = "tile id: " + this.id + " tile collidable: " + this.collidable;
        return tileinfo;
    }

    public static TileType getTileTypeById(int id) {
        return tileMap.get(id);
    }


public static boolean isCollidable(TileType tyleType){
    return tyleType.collidable;
}



}
package com.jwb.perfectWorld;

import java.util.HashMap;

public class TileType {

//    //starts at 1, not 0 indexed
//    GRASS(1, true, "Grass"),
//    DIRT(2, true, "Dirt"),
//    SKY(3, false, "Sky"),
//    LAVA(4, true, "Lava"),
//    CLOUD(5, true, "Cloud"),
//    STONE(6, true, "Stone");


    public static final int TILE_SIZE = 32;


    private int id;
    private boolean collidable;
    private String name;
    private float damage;

    private TileType(int id, boolean collidable) {

        this(id, collidable, 0);

    }

    private TileType (int id, boolean collidable, float damage){

        this.id = id;
        this.collidable = collidable;
        this.damage = damage;
    }

    public int getId() {
        return id;
    }

    public boolean isCollidable() {
        return collidable;
    }

    public String getName() {
        return name;
    }

    public float getDamage() {
        return damage;
    }

    private static HashMap<Integer, TileType> tileMap;

    static {

        int i = 1;
        /////////////////////////////////////////////////////////////
        /// T E M P O R A R Y    F O R    D E M O   M O D E
        /////////////////////////////////////////////////////////////

        while (i > 173){

            //initialize all the tile types to be loaded to the tileMap
            if (i == 3){
                tileMap.put(i, new TileType(i, true, 0));
            }

            else {
                tileMap.put(i, new TileType(i, false, 0));
            }


            i ++;


        }
//        for (TileType tileType : TileType.values()) {
//            tileMap.put(tileType.getId(), tileType);
//        }
    }

    public static TileType getTileTypeById (int id) {
        return tileMap.get(id);
    }
}

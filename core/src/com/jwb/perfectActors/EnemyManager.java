package com.jwb.perfectActors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jwb.perfectWorld.TiledGameMap;

import java.util.ArrayList;
import java.util.Iterator;

public class EnemyManager {

    private ArrayList<PerfectEnemy> enemies;
    private TiledGameMap levelMap;
    private Perry perry;

    public EnemyManager(TiledGameMap levelMap, Perry perry){
        this.enemies = new ArrayList<>();
        this.levelMap = levelMap;
        this.perry = perry;
        initializeEnemies();
        System.out.println("enemies initialized");
    }

    public void update(float dt){
        Iterator<PerfectEnemy> iter = enemies.iterator();
        while (iter.hasNext()) {
            PerfectEnemy enemy = iter.next();
            enemy.update(dt, perry.getPosition());
            if (enemy.isDestroyed()) { // You should implement this check based on your game logic
                iter.remove();
            }
        }
    }

    public void initializeEnemies(){

        YellowFellow yellowFellow = new YellowFellow( 1400, 10, levelMap);
        enemies.add(yellowFellow);
    }

    public void render(SpriteBatch batch){
        for (PerfectEnemy enemy: enemies){
            enemy.render(batch);
        }
    }

    public void addEnemy(PerfectEnemy enemy){
        enemies.add(enemy);
    }

    public void removeEnemy(PerfectEnemy enemy){
        enemies.remove(enemy);
    }

    public ArrayList<PerfectEnemy> getEnemies(){
        return enemies;
    }

}

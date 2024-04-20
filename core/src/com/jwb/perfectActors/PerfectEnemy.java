package com.jwb.perfectActors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.jwb.perfectWorld.TiledGameMap;

import java.util.ArrayList;

public abstract class PerfectEnemy {

    static class attackBoxes{

        Rectangle boundingBox;
        Polygon hitPoly;

        public attackBoxes(Rectangle boundingBox, Polygon hitPoly){
            this.boundingBox = boundingBox;
            this.hitPoly = hitPoly;
        }

        public Polygon getHitPoly() {
            return hitPoly;
        }

        public Rectangle getBoundingBox() {
            return boundingBox;
        }
    }

    static class atkBounds{

        int xOffset;
        int yOffset;
        int width;
        int height;

        public atkBounds(int xOffset, int yOffset, int width, int height){
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            this.width = width;
            this.height = height;
        }
    }

    static class animationVertices{

        ArrayList<PerfectEnemy.frameVertices> frameVerticesList = new ArrayList<>();

        public void addFrameVertices(PerfectEnemy.frameVertices frameVertices){
            this.frameVerticesList.add(frameVertices);
        }

        public PerfectEnemy.frameVertices getFrameVerts(int frame){

            return this.frameVerticesList.get(frame);
        }
    }

    static class frameVertices{

        int frameNum;
        PerfectEnemy.atkBounds attackBounds;
        int frameWidth;

        ArrayList<PerfectEnemy.hitboxVertices> hitboxVerticesList = new ArrayList<>();


        frameVertices(int frameNum){
            this.frameNum = frameNum;
        }

        public void addVertices(int xCoord, int yCoord){
            this.hitboxVerticesList.add(new PerfectEnemy.hitboxVertices(xCoord, yCoord));
        }

        public int getFrameVertSize(){ return this.hitboxVerticesList.size();}

        public void setBoundsInfo(PerfectEnemy.atkBounds atkBounds){

            this.attackBounds = atkBounds;
        }

    }


    static class hitboxVertices {
        int xCoordinate;
        int yCoordinate;


        hitboxVertices(int xCoordinate, int yCoordinate) {
            this.xCoordinate = xCoordinate;
            this.yCoordinate = yCoordinate;
        }

        hitboxVertices(int frameNum) {}
    }

    //enemy name
    private String enemyType;

    int healthPoints;
    int stancePoints;

    // is enemy facing/moving left or right
    boolean leftMove;
    boolean rightMove;

    // can enemy currently move or are they in an attack/animation
    boolean canMove;

    // is enemy on the ground
    boolean grounded;

    boolean isAttacking;

    //maximum run speed
    int maxRunSpeed;

    //max walk speed
    int maxWalkSpeed;

    //for applying gravity
    static final int GRAVITY = -15;

    //Vector 3 holds an x y and z axes, but we'll only be using 2 dimensions
    //this will represent Enemy's position
    Vector3 position;

    //this will represent Enemy's velocity
    Vector3 velocity;

    //this will be used to detect movement colllision
    float newVeloctyX;

    public PerfectEnemy.attackBoxes atkBoxes;
    public Rectangle atkHitboxBounds;
    public Polygon atkHitbox;

    //this will be used for Enemy hitbox
    Rectangle enemyHitboxBounds;

    Polygon enemyHitbox;

    //will be used to load and implement enemy animations
    EnemyAnimationMGMT animationManager;

    //tracks the current enemy animation
    Animation activeAnimation;

    //tracks the enemy state
    EnemyStates currentState;


    //current level must be used to track collision with walls
    public TiledGameMap currentLevel;

    public PerfectEnemy(int x, int y, TiledGameMap currentLevel){

        this.position = new Vector3(x, y, 0);
        this.velocity = new Vector3(0, 0, 0);
        this.currentLevel = currentLevel;

    }


    public abstract void update(float dt, Vector3 perryPosition);

    public abstract void takeDamage();

    public void setCanMove(boolean canMove){ this.canMove = canMove;}

    public boolean getCanMove(){ return this.canMove;}

    public boolean isLeftMove(){ return this.leftMove;}

    public boolean isRightMove(){ return this.rightMove;}

    abstract void handleIdle(float dt);

    void changeState(EnemyStates newState){
        if (this.currentState != newState) {
            System.out.println(activeAnimation.name + "  " + activeAnimation.frame);
            this.currentState = newState;
            System.out.println("YELLOW FELLOW State changed to" + newState);
            activeAnimation.reset();
            updateAnimation();
        }
    };

    abstract EnemyStates getCurrentState();

    abstract void chooseRightMoveState();

    abstract void handleMoveRight(float dt);

    abstract void chooseLeftMoveState();

    abstract void handleMoveLeft(float dt);

    abstract void updateAnimation();

    public Rectangle getEnemyBounds(){ return this.enemyHitboxBounds;}

    public Polygon getEnemyHitbox(){ return this.enemyHitbox;}

    public boolean checkIfHit(Rectangle atkBounds, Polygon atkPoly){

        if ((atkBounds == null) || (atkPoly == null)){ return false;}

        if (!this.enemyHitboxBounds.overlaps(atkBounds)){
            System.out.println("bounds do not overlap");
            return false;
        }

        System.out.println("bounds overlap!");
        return Intersector.overlapConvexPolygons(this.enemyHitbox, atkPoly);
    }

    public Vector3 getPosition(){ return this.position;}

    abstract void handleGravity(float dt);

    public TextureRegion getTexture(){return activeAnimation.getFrame();}

    public boolean isDestroyed(){
        return healthPoints <= 0;
    }

    public void render(SpriteBatch sb){
        sb.draw(this.getTexture(), this.getPosition().x, this.getPosition().y);
    }



}

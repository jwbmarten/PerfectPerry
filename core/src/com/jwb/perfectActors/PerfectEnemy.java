package com.jwb.perfectActors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.jwb.perfectWorld.TiledGameMap;

public abstract class PerfectEnemy {

    //enemy name
    private String enemyType;

    // is enemy facing/moving left or right
    boolean leftMove;
    boolean rightMove;

    // can enemy currently move or are they in an attack/animation
    boolean canMove;

    // is enemy on the ground
    boolean grounded;

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

    //this will be used for Enemy hitbox
    Rectangle bounds;

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

    public abstract void update(float dt);

    public void setCanMove(boolean canMove){ this.canMove = canMove;}

    public boolean getCanMove(){ return this.canMove;}

    public boolean isLeftMove(){ return this.leftMove;}

    public boolean isRightMove(){ return this.rightMove;}

    abstract void handleIdle(float dt);

    abstract void changeState(EnemyStates state);

    abstract EnemyStates getCurrentState();

    abstract void chooseRightMoveState();

    abstract void handleMoveRight(float dt);

    abstract void chooseLeftMoveState();

    abstract void handleMoveLeft(float dt);

    abstract void updateAnimation();

    public Rectangle getBounds(){ return this.bounds;}

    public Vector3 getPosition(){ return this.position;}

    abstract void handleGravity(float dt);

    public TextureRegion getTexture(){return activeAnimation.getFrame();}

}

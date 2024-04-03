package com.jwb.perfectActors;

import static com.jwb.perfectWorld.TileType.TILE_SIZE;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.jwb.perfectWorld.TiledGameMap;

public class YellowFellow extends PerfectEnemy {

    float[] yelFelBodyVerts;


    public YellowFellow(int x, int y, TiledGameMap currentLevel) {


        super(x, y, currentLevel);

        this.position = new Vector3(x, y, 0);
        this.velocity = new Vector3(0, 0, 0);
        this.currentState = EnemyStates.IDLE_ONE_LEFT;
        this.leftMove = true;
        this.rightMove = false;
        this.canMove = true;
        this.grounded = false;
        this.isAttacking = false;
        this.maxWalkSpeed = 4;
        this.animationManager = new YellowFellowMGMT();
        this.activeAnimation = animationManager.getAnimation(EnemyStates.IDLE_ONE_LEFT);

        this.enemyHitboxBounds = new Rectangle(position.x + 51, position.y, 232, 232);
        this.yelFelBodyVerts = new float[]{(position.x + 51), (position.y), (position.x + 283), (position.y), (position.x + 283), (position.y + 233), (position.x + 51), (position.y + 233)};
        this.enemyHitbox = new Polygon(this.yelFelBodyVerts)        ;


    }

    @Override
    public void update(float dt, Vector3 perryPosition) {

        //////////////////////////////////////////////////////////////////////
        // STAGE 1: here we need to consider the possible user inputs, but also
        //          the current state (e.g. is the user doing an action which
        //          cannot be paused like attacking/rolling/healing)
        //
        //
        // Objective: adjust Yellow Fellow's current state and velocity, his position will
        //            be updated in the next STAGE



        // first let's check if the enemy is in an unstopbable animation

        if (!canMove) {

            if (isAttacking){
                handleDoubSwing();
            }

        }

        if (!grounded) {
//            System.out.println("Yellow Fellow Not Grounded!");

            handleGravity(dt);
        }

        if (Math.abs( ((int) position.x) - ((int) perryPosition.x) ) < 300) {
//            System.out.println("Perry within Yellow Fellow Range!");
            handleDoubSwing();
        }

        activeAnimation.update(dt);

        position.add(velocity.x, velocity.y, 0);

        if (!isAttacking){ enemyHitboxBounds.setPosition(position.x + 51, position.y);

        this.yelFelBodyVerts = new float[]{(position.x + 51), (position.y), (position.x + 283), (position.y), (position.x + 283), (position.y + 233), (position.x + 51), (position.y + 233)};
        this.enemyHitbox = new Polygon(this.yelFelBodyVerts);}


    }

    public void handleDoubSwing(){

        if ((!this.isAttacking) ){

            if (this.leftMove) {

                changeState(EnemyStates.ATTACK_ONE_LEFT);
                canMove = false;
                isAttacking = true;
                velocity.x = 0;
            }

            if (this.rightMove) {

                changeState(EnemyStates.ATTACK_ONE_RIGHT);
                canMove = false;
                isAttacking = true;
                velocity.x = 0;
            }
        }

        if ((activeAnimation.isComplete())) {

            if (currentState == EnemyStates.ATTACK_ONE_LEFT){
                changeState(EnemyStates.ATTACK_TWO_LEFT);
                position.x -= 200;
                this.enemyHitboxBounds.setPosition(position.x + 254, position.y);
                this.yelFelBodyVerts = new float[]{(position.x + 254), (position.y), (position.x + 486), (position.y), (position.x + 486), (position.y + 233), (position.x + 254), (position.y + 233)};
                this.enemyHitbox = new Polygon(this.yelFelBodyVerts);
                this.isAttacking = true;
                return;
            }

            if (currentState == EnemyStates.ATTACK_ONE_RIGHT){
                changeState(EnemyStates.ATTACK_TWO_RIGHT);
                this.isAttacking = true;
                return;
            }

            if (currentState == EnemyStates.ATTACK_TWO_LEFT){
                changeState(EnemyStates.ATTACK_THREE_LEFT);
                return;
            }

            if (currentState == EnemyStates.ATTACK_TWO_RIGHT){
                changeState(EnemyStates.ATTACK_THREE_RIGHT);
                return;
            }

            if (currentState == EnemyStates.ATTACK_THREE_LEFT){
                changeState(EnemyStates.IDLE_ONE_LEFT);
                position.x += 200;
                this.enemyHitboxBounds.setPosition(position.x + 51, position.y);
                this.yelFelBodyVerts = new float[]{(position.x + 51), (position.y), (position.x + 283), (position.y), (position.x + 283), (position.y + 233), (position.x + 51), (position.y + 233)};
                this.enemyHitbox = new Polygon(this.yelFelBodyVerts);
                canMove = true;
                this.isAttacking = false;
                return;
            }

            if (currentState == EnemyStates.ATTACK_THREE_RIGHT){
                changeState(EnemyStates.IDLE_ONE_RIGHT);
                canMove = true;
                this.isAttacking = false;
                return;
            }

        }

//        System.out.println("Yel Fel frame: " + activeAnimation.frame);

        atkBoxes = animationManager.getAttackHitbox(position, currentState, activeAnimation.frame);
        if (atkBoxes != null){
            atkHitboxBounds = atkBoxes.getBoundingBox();
            atkHitbox = atkBoxes.getHitPoly();}
    }

    public Polygon getYelFelPoly(){
        return this.enemyHitbox;
    }

    public float[] getYelFelBodyVerts() {
        return yelFelBodyVerts;
    }



    @Override
    public void takeDamage(){
        velocity.y = 10;
        this.grounded = false;
    }



    @Override
    void handleIdle(float dt) {

    }


    @Override
    EnemyStates getCurrentState() {
        return null;
    }


    @Override
    void chooseRightMoveState() {

    }

    @Override
    void handleMoveRight(float dt) {

    }

    @Override
    void chooseLeftMoveState() {

    }

    @Override
    void handleMoveLeft(float dt) {

    }


    @Override
    void updateAnimation() {
        this.activeAnimation = animationManager.getAnimation(currentState);
    }

    @Override
    void handleGravity(float dt) {

//        System.out.println("Handlin Gravity");

        float newVelocityY = Math.max(velocity.y + GRAVITY*dt, -8); // Desired velocity increment

        //check that future position plus with width of Perry is not a collidable tile
        float potentialY = position.y  + newVelocityY;

        // Check for collision at Perry's under side
        if (!currentLevel.isTileCollidable(position.x, potentialY )) {
            velocity.y = newVelocityY;
        } else {
            // Adjust Perry's position to avoid penetrating the collidable tile
            velocity.y = 0;

            float tileBoundary = (float) ((Math.floor(potentialY / TILE_SIZE) * TILE_SIZE ) + TILE_SIZE);
            position.y = tileBoundary + 2; // -1 for a small buffer

            grounded = true;

        }
    }
}

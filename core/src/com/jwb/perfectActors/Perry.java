package com.jwb.perfectActors;

import static com.jwb.perfectWorld.TileType.TILE_SIZE;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.jwb.perfectWorld.TiledGameMap;

public class Perry {

    boolean leftMove;
    boolean rightMove;
    boolean rightRoll;

    // determines if Perry can stop his current animation or not
    boolean canMove;

    boolean grounded;

    private float repositionHorizontal;

    private float motionTimer = 0f; // initialize a float to track time for motion delays

    private static final float STATE_TRANSITION_DEBOUNCE_TIME = 0.1f; // 100 ms debounce time to prevent accidental state switching

    private float idleTimer = 0f;

    private static final float RUNNING_RIGHT_DELAY = 0.3f; //500 ms

    private static final float RUN_RIGHT_LOOP_DELAY = 0.4f; // accounts for length of idle to running animation

    private int maxRunSpeed = 10;

    //static int to represent gravity
    private static final int GRAVITY = -15;

    //Vector 3 holds an x y and z axes, but we'll only be using 2 dimensions
    //this will represent Perry's position
    private Vector3 position;

    //this will represent Perry's velocity
    private Vector3 velocity;

    private float newVelocityX;


    //this will be Perry's hitbox
    private Rectangle bounds;

    private PerryAnimationMGMT animationManager;

    private Animation activeAnimation;

    private State currentState ;

    //track if Perry is colliding with anything
    public boolean colliding;

    public TiledGameMap currentLevel;

    public enum State {
        IDLE,
        RUNNING_LEFT,
        START_RUN_RIGHT,
        RUNNING_RIGHT,
        CLING_RIGHT,
        ROLLING_RIGHT,
        JUMP_BACK_RIGHT,
        QUICK_ATTACK_RIGHT
    }
    public Perry(int x, int y, TiledGameMap currentLevel){

        this.animationManager = new PerryAnimationMGMT();
        this.activeAnimation = animationManager.getAnimation(State.IDLE);
        currentState = State.IDLE; // default state

        //updateAnimation();// initial aniamtion setup

        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
//        grounded = false;
        this.currentLevel = currentLevel;


        bounds = new Rectangle(x, y, animationManager.getIdleWidth(), animationManager.getIdleHeight());
        colliding = false;


        rightRoll = false;

        canMove = true;

        this.rightMove = true;

    }

    public void update(float dt, boolean leftPressed, boolean rightPressed){


        //////////////////////////////////////////////////////////////////////
        // STAGE 1: here we need to consider the possible user inputs, but also
        //          the current state (e.g. is the user doing an action which
        //          cannot be paused like attacking/rolling/healing)
        //
        //
        // Objective: adjust Perry's current state and velocity, his position will
        //            be updated in the next STAGE



        // first let's check if the user is in an unstopbable animation

        if (!canMove) {

            //if the user is rolling, call the handle roll method
            if (currentState == State.ROLLING_RIGHT) {
                handleRoll(dt);
            }

            if (currentState == State.QUICK_ATTACK_RIGHT){
                handleQuickAttack();
            }

            if (currentState == State.JUMP_BACK_RIGHT){
                handleJumpBack(dt);
            }
        }

        if ((leftPressed && canMove)){

            leftMove = true;
            rightMove = false;

            //call handle move left function
            handleMoveLeft(dt);


        }

        if (rightPressed && canMove) {

            rightMove = true;
            leftMove = false;
            handleMoveRight(dt);
        }


        //Update vertical velocity based on gravity
        if (!grounded) {
            handleGravity(dt);
        }



        // If not moving left or right, slow down to 0

        if ((!leftPressed && !rightPressed) && (canMove)) {

            if (Math.abs(velocity.x) < 0.1f) {
                velocity.x = 0;
                handleIdle(dt);

            } else {

                System.out.println("Neither Left nor right pressed, slowing down");
                velocity.x *= 0.2f; // Apply a simple friction factor
            }
        }

        activeAnimation.update(dt);

        // Update position based on velocity
        position.add(velocity.x, velocity.y, 0);

    }

    private void updateAnimation() {
        this.activeAnimation = animationManager.getAnimation(currentState);
    }

    public void chooseLeftMoveState()
    {
        if (currentState != State.RUNNING_LEFT) {
//             System.out.println("Setting state to running left and resetting active animation.");
            changeState(State.RUNNING_LEFT);
            canMove = true;
            activeAnimation.reset();
//            motionTimer = 0;
//            idleTimer = 0;
            }


    }

    public void chooseRightMoveState() {




        // if the current state is idle set state to start running right
        if ((leftMove) || (currentState == State.IDLE) ){
            activeAnimation.reset();
            changeState(State.START_RUN_RIGHT);
            canMove = true;
            System.out.print("State changed from idle or moving left to start run right");
            leftMove = false;
            rightMove = true;
        }



        //if the current state is start running right and the animation is complete, set the state to running right
        if ((currentState == State.START_RUN_RIGHT) && activeAnimation.isComplete()){
            changeState(State.RUNNING_RIGHT);
            canMove = true;
        }



    }

    public void changeState(State newState) {
        if (this.currentState != newState) {
            activeAnimation.reset();
            this.currentState = newState;
            updateAnimation();
        }
    }

    public void perryCantStop(float dt){

        if (currentState == State.ROLLING_RIGHT){

            handleRoll(dt);
        }
    }

    public void handleRoll(float dt){

        System.out.println("handlin' rollin'");


        if ((currentState == State.ROLLING_RIGHT) && (activeAnimation.isComplete())){
            changeState(State.RUNNING_RIGHT);
            canMove = true;
        }

        System.out.println("current x velocity: " + velocity.x);

        System.out.println("animation completion: " + activeAnimation.animationPercentComplete());

        newVelocityX = Math.max((2*maxRunSpeed) * (1f - activeAnimation.animationPercentComplete()), 3f);

        float xPosIncrement = newVelocityX * dt;

        float potentialX = position.x + bounds.width;

        if (!currentLevel.isTileCollidable(potentialX, position.y)) {
            velocity.x = newVelocityX;
        } else {

            velocity.x = 0;

            float tileBoundary = (float) (Math.floor(potentialX / TILE_SIZE) * TILE_SIZE);
            position.x = tileBoundary - bounds.getWidth() ; // -1 for a small buffer



        }


    }

    public void handleJumpBack(float dt){

        System.out.println("handlin' jump back'");


        if ((currentState == State.JUMP_BACK_RIGHT) && (activeAnimation.isComplete())){
            changeState(State.IDLE);
            canMove = true;
        }

        System.out.println("current x velocity: " + velocity.x);

        System.out.println("animation completion: " + activeAnimation.animationPercentComplete());

        if ( activeAnimation.animationPercentComplete() < 0.25f || activeAnimation.animationPercentComplete() > 0.8f){
            velocity.x = 0;
            return;
        }

        newVelocityX = Math.min((-2*maxRunSpeed) * (1f - activeAnimation.animationPercentComplete()), -3f);

        float xPosIncrement = newVelocityX * dt;

        float potentialX = position.x + xPosIncrement;

        if (!currentLevel.isTileCollidable(potentialX, position.y)) {
            velocity.x = newVelocityX;
        } else {

            velocity.x = 0;

            float tileBoundary = (float) (Math.floor(potentialX / TILE_SIZE) * TILE_SIZE);
            position.x = tileBoundary + TILE_SIZE ; // -1 for a small buffer

        }

    }

    public void setRoll(){

        System.out.println("setting roll'");

        if (currentState!= State.ROLLING_RIGHT) {

            if (this.rightMove) {

                changeState(State.ROLLING_RIGHT);
                canMove = false;


            }
        }

    }

    public void setJumpBack(){

        System.out.println("jumping back!");

        if (currentState != State.JUMP_BACK_RIGHT){

            if (this.rightMove) {

                changeState(State.JUMP_BACK_RIGHT);
                canMove = false;

            }
        }

    }

    public void handleQuickAttack(){

        System.out.println("Starting Quick Attack!");


        if (currentState != State.QUICK_ATTACK_RIGHT) {

            if (this.rightMove) {

                changeState(State.QUICK_ATTACK_RIGHT);
                canMove = false;
                velocity.x = 0;
            }
        }

        if ((currentState == State.QUICK_ATTACK_RIGHT) && (activeAnimation.isComplete())) {

            changeState(State.IDLE);
            canMove = true;


        }
    }

    public void handleGravity(float dt){

        float newVelocityY = Math.min(velocity.y + GRAVITY*dt, 8); // Desired velocity increment

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



    public void handleMoveLeft(float dt){


        velocity.x = Math.min((velocity.x * 1.05f) - 1.5f * dt, -maxRunSpeed); // Gradual speed build up to -4

    }

    public void handleMoveRight(float dt) {

        chooseRightMoveState();

        newVelocityX = Math.min((velocity.x * 1.5f) + 1.5f * dt , maxRunSpeed); // Desired velocity increment

        float xPosIncrement = newVelocityX * dt;

//        System.out.println("newVelocityX: " + newVelocityX);

        //check that future position plus with width of Perry is not a collidable tile
        float potentialX = position.x + bounds.width;

//        System.out.println("delta time: " + dt);
//
//        System.out.println("xPosition increment: " + xPosIncrement);
//
//        System.out.println("potential X: " + potentialX);

        // Check for collision at Perry's front side
        if (!currentLevel.isTileCollidable(potentialX, position.y)) {
            velocity.x = newVelocityX;
        } else {

//            System.out.println("State changed from moving right to cling right");
//
//            System.out.println("Perry current X pos: " + position.x + "Perry current Y pos: " + position.y);
//
//            System.out.println("current bounds width: " + bounds.getWidth());
//
//            System.out.println("Perry position + width: " + position.x + bounds.getWidth());
//
//            System.out.println("Position plus width collidable?: " + currentLevel.isTileCollidable(position.x + bounds.width, position.y));

            // Adjust Perry's position to avoid penetrating the collidable tile
            velocity.x = 0;


            float tileBoundary = (float) (Math.floor(potentialX / TILE_SIZE) * TILE_SIZE);
            position.x = tileBoundary - bounds.getWidth() ; // -1 for a small buffer

            activeAnimation.reset();
            changeState(State.CLING_RIGHT);
            canMove = false;


        }
    }


    public void handleIdle(float dt){

//        System.out.println("Handlin; idlin'");


        if (currentState == State.CLING_RIGHT){
            activeAnimation.reset();
            changeState(State.IDLE);
            canMove = true;
            //handleIdle(dt);
            System.out.println("State changed from cling right to idle");
            bounds = new Rectangle(position.x, position.y, animationManager.getIdleWidth(), animationManager.getIdleHeight());

        }



        // Check that current (RIGHT ONLY FOR NOW) bounds not in collidable tile

        if (currentLevel.isTileCollidable(position.x + (float) bounds.width, position.y)){

//            System.out.println("Idle state detected collision!");

            float tileBoundary = (float) (Math.floor(position.x / TILE_SIZE) * TILE_SIZE);

            repositionHorizontal = 0f;


        }

            if (currentState != State.IDLE) {
                changeState(State.IDLE);
                canMove = true;
            }

        velocity.x = 0;

    }

    public boolean isLeftMove() {
        return leftMove;
    }

    public boolean isRightMove() {
        return rightMove;
    }

    public boolean isRightRoll() {
        return rightRoll;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public void setRightRoll(boolean rightRoll) {
        this.rightRoll = rightRoll;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return activeAnimation.getFrame();
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean getCanMove(){ return this.canMove; }

    public Rectangle getBounds() {return bounds;}

    public void dispose() { System.out.println("Perry Disposed!"); //texture.dispose();
         }

}

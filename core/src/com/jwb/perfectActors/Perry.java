package com.jwb.perfectActors;

import static com.jwb.perfectWorld.TileType.TILE_SIZE;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
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

    boolean isAttacking;

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

    //Perry's attacking hitbox
    public PerryAnimationMGMT.attackBoxes atkBoxes;
    public Rectangle atkHitboxBounds;
    public Polygon atkHitbox;

    private PerryAnimationMGMT animationManager;

    private Animation activeAnimation;

    private State currentState ;

    //track if Perry is colliding with anything
    public boolean colliding;

    public TiledGameMap currentLevel;

    public enum State {
        IDLE,
        IDLE_LEFT,
        START_RUN_LEFT,
        RUNNING_LEFT,
        CLING_LEFT,
        ROLLING_LEFT,
        JUMP_BACK_LEFT,
        QUICK_ATTACK_LEFT,
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

        if (leftPressed && rightPressed){
        System.out.println("Perry Update.   Left Pressed:  " + leftPressed + " Right Pressed:   " + rightPressed);}

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
            if ((currentState == State.ROLLING_RIGHT) || (currentState == State.ROLLING_LEFT)) {
                handleRoll(dt);
            }

            if ((currentState == State.QUICK_ATTACK_RIGHT) || (currentState == State.QUICK_ATTACK_LEFT)){
                handleQuickAttack();
            }

            if ((currentState == State.JUMP_BACK_RIGHT) || (currentState == State.JUMP_BACK_LEFT)){
                handleJumpBack(dt);
            }
        }

        if ((leftPressed) && (!rightPressed) && (canMove)){

//            leftMove = true;
//            rightMove = false;

            //call handle move left function
//            System.out.println("calling handle move Left");
            handleMoveLeft(dt);


        }

        if ((rightPressed) && (!leftPressed) && (canMove)) {

//            rightMove = true;
//            leftMove = false;
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
        System.out.println(activeAnimation.name + "  " + activeAnimation.frame);
    }

    public void chooseLeftMoveState()
    {

//        System.out.println("Starting method choose left move state");


        // if the current state is idle set state to start running right
        if ((rightMove) || (currentState == State.IDLE_LEFT) ){
            activeAnimation.reset();
            velocity.x = 0;
            changeState(State.START_RUN_LEFT);
            canMove = true;
//            System.out.println("State changed from idle or moving left to start run left");
            leftMove = true;
            rightMove = false;
        }



        //if the current state is start running right and the animation is complete, set the state to running right
        if ((currentState == State.START_RUN_LEFT) && activeAnimation.isComplete()){
            changeState(State.RUNNING_LEFT);
            canMove = true;
        }


    }

    public void chooseRightMoveState() {


        // if the current state is idle set state to start running right
        if ((leftMove) || (currentState == State.IDLE) ){
            activeAnimation.reset();
            velocity.x = 0;
            changeState(State.START_RUN_RIGHT);
            canMove = true;
//            System.out.print("State changed from idle or moving left to start run right");
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
            System.out.println(activeAnimation.name + "  " + activeAnimation.frame);
            this.currentState = newState;
            System.out.println("State changed to" + newState);
            activeAnimation.reset();
            updateAnimation();


        }
    }

    public void perryCantStop(float dt){

        if (currentState == State.ROLLING_RIGHT){

            handleRoll(dt);
        }
    }

    public void handleRoll(float dt){

//        System.out.println("handlin' rollin'");

        ////////////////////////////////////////////////////////////////////////////////////////////
        // IF the rolling left animation is complete
        if ((currentState == State.ROLLING_LEFT) && (activeAnimation.isComplete())){

            //either way, set canMove to true
            canMove = true;

            //and user is still pressing left, transition to running left
            if ((leftMove)) {
                changeState(State.RUNNING_LEFT);
                return;
            }

            //if the user is pressing right, transition to start run right
            if ((rightMove)) {
                changeState(State.START_RUN_RIGHT);
            }



        }///////////////////////////////////////////////////////////////////////////////////////////



        ////////////////////////////////////////////////////////////////////////////////////////////
        // IF the rolling right animation is complete
        if ((currentState == State.ROLLING_RIGHT) && (activeAnimation.isComplete())){

            //either way, set canMove to true
            canMove = true;

            //and user is still pressing left, transition to running right
            if (rightMove){
            changeState(State.RUNNING_RIGHT);
            return;}

            //if the user is pressing left, transition to start run left
            if (leftMove) {
                changeState(State.START_RUN_LEFT);
            }


        }///////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        // ELSE IF THE ANIMATION IS NOT COMPLETE

        //HANDLE ACTIVELY ROLLING LEFT

        if (currentState == State.ROLLING_LEFT) {
            newVelocityX = Math.min((-2 * maxRunSpeed) * (1f - activeAnimation.animationPercentComplete()), -3f);

            float xPosIncrement = newVelocityX * dt;

            float potentialX = position.x + xPosIncrement;

            if (!currentLevel.isTileCollidable(potentialX, position.y)) {
                velocity.x = newVelocityX;
            } else {

                velocity.x = 0;

                float tileBoundary = (float) (Math.floor(potentialX / TILE_SIZE) * TILE_SIZE);
                position.x = tileBoundary + TILE_SIZE; // -1 for a small buffer
            }
        }

        //HANDLE ACTIVELY ROLLING RIGHT

        if (currentState == State.ROLLING_RIGHT){
        newVelocityX = Math.max((2*maxRunSpeed) * (1f - activeAnimation.animationPercentComplete()), 3f);

        float xPosIncrement = newVelocityX * dt;

        float potentialX = position.x + bounds.width;

        if (!currentLevel.isTileCollidable(potentialX, position.y)) {
            velocity.x = newVelocityX;
        } else {

            velocity.x = 0;

            float tileBoundary = (float) (Math.floor(potentialX / TILE_SIZE) * TILE_SIZE);
            position.x = tileBoundary - bounds.getWidth(); // -1 for a small buffer

        }

        }


    }

    public void handleJumpBack(float dt){

//        System.out.println("handlin' jump back'");

        ////////////////////////////////////////////////////////////////////////////////////////////
        //CHECK IF JUMP BACK ANIMATION IS COMPLETE AND IF SO RESET TO IDLE

        //CHECK LEFT
        if ((currentState == State.JUMP_BACK_LEFT) && (activeAnimation.isComplete())){
            changeState(State.IDLE_LEFT);
            canMove = true;
        }

        //CHECK RIGHT
        if ((currentState == State.JUMP_BACK_RIGHT) && (activeAnimation.isComplete())){
            changeState(State.IDLE);
            canMove = true;
        }

        //IF THE ANIMATION IS LESS THAN 25 OR GREATER THAN 80 PERCENT COMPLETE, DON'T MOVE

        if ( activeAnimation.animationPercentComplete() < 0.25f || activeAnimation.animationPercentComplete() > 0.8f){
            velocity.x = 0;
            return;
        }

        //ELSE IF THE ANIMATION IS BETWEEN 25 and 80 PERCENT COMPLETE, HANDLE VELOCITY AND POSITION

        //FOR LEFT

        if (currentState == State.JUMP_BACK_LEFT) {


            newVelocityX = Math.max((2 * maxRunSpeed) * (1f - activeAnimation.animationPercentComplete()), 3f);

            float xPosIncrement = newVelocityX * dt;

            float potentialX = position.x + xPosIncrement + bounds.width;

            if (!currentLevel.isTileCollidable(potentialX, position.y)) {
                velocity.x = newVelocityX;
            } else {

                velocity.x = 0;

                float tileBoundary = (float) (Math.floor(potentialX / TILE_SIZE) * TILE_SIZE);
                position.x = tileBoundary - bounds.width; // -1 for a small buffer

            }
        }

        //FOR RIGHT

        if (currentState == State.JUMP_BACK_RIGHT) {


            newVelocityX = Math.min((-2 * maxRunSpeed) * (1f - activeAnimation.animationPercentComplete()), -3f);

            float xPosIncrement = newVelocityX * dt;

            float potentialX = position.x + xPosIncrement;

            if (!currentLevel.isTileCollidable(potentialX, position.y)) {
                velocity.x = newVelocityX;
            } else {

                velocity.x = 0;

                float tileBoundary = (float) (Math.floor(potentialX / TILE_SIZE) * TILE_SIZE);
                position.x = tileBoundary + TILE_SIZE; // -1 for a small buffer

            }
        }

    }

    public void setRoll(){

//        System.out.println("setting roll'");

        if ((currentState!= State.ROLLING_RIGHT) && (currentState != State.ROLLING_LEFT)) {

            if (this.leftMove) {

                changeState(State.ROLLING_LEFT);
                canMove = false;

            }

            if (this.rightMove) {

                changeState(State.ROLLING_RIGHT);
                canMove = false;

            }
        }

    }

    public void setJumpBack(){

//        System.out.println("jumping back!");

        if ((currentState != State.JUMP_BACK_RIGHT) && (currentState != State.JUMP_BACK_LEFT)){

            if (this.leftMove) {

                changeState(State.JUMP_BACK_LEFT);
                canMove = false;

            }

            if (this.rightMove) {

                changeState(State.JUMP_BACK_RIGHT);
                canMove = false;

            }
        }

    }

    public void handleQuickAttack(){

//        System.out.println("Starting Quick Attack!");


        if ((currentState != State.QUICK_ATTACK_RIGHT) && (currentState != State.QUICK_ATTACK_LEFT)) {

            if (this.leftMove) {

                changeState(State.QUICK_ATTACK_LEFT);
                canMove = false;
                isAttacking = true;
                velocity.x = 0;
            }

            if (this.rightMove) {

                changeState(State.QUICK_ATTACK_RIGHT);
                canMove = false;
                isAttacking = true;
                velocity.x = 0;
            }
        }

        if ((activeAnimation.isComplete())) {

            isAttacking = false;

            if (currentState == State.QUICK_ATTACK_LEFT){
                changeState(State.IDLE_LEFT);
            }

            if (currentState == State.QUICK_ATTACK_RIGHT){
            changeState(State.IDLE);}

            canMove = true;

        }

        atkBoxes = animationManager.getAttackHitbox(position, currentState, activeAnimation.getCurrFrameNum());
        if (atkBoxes != null){
        atkHitboxBounds = atkBoxes.getBoundingBox();
        atkHitbox = atkBoxes.getHitPoly();}

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


        chooseLeftMoveState();

        newVelocityX = Math.max((velocity.x * 1.5f) - 1.5f * dt , -maxRunSpeed); // Desired velocity increment

        float xPosIncrement = newVelocityX * dt;


        //check that future position plus with width of Perry is not a collidable tile
        float potentialX = position.x + xPosIncrement;


        // Check for collision at Perry's front side
        if (!currentLevel.isTileCollidable(potentialX, position.y)) {
            velocity.x = newVelocityX;
        } else {


            // Adjust Perry's position to avoid penetrating the collidable tile
            velocity.x = 0;


            float tileBoundary = (float) (Math.floor(potentialX / TILE_SIZE) * TILE_SIZE);
            position.x = tileBoundary + TILE_SIZE ; // -1 for a small buffer

            activeAnimation.reset();
            System.out.println("State changed to Cling Left!");
            changeState(State.CLING_LEFT);
            canMove = false;


        }

    }

    public void handleMoveRight(float dt) {

        chooseRightMoveState();

        newVelocityX = Math.min((velocity.x * 1.5f) + 1.5f * dt , maxRunSpeed); // Desired velocity increment

        float xPosIncrement = newVelocityX * dt;


        //check that future position plus with width of Perry is not a collidable tile
        float potentialX = position.x + bounds.width;


        // Check for collision at Perry's front side
        if (!currentLevel.isTileCollidable(potentialX, position.y)) {
            velocity.x = newVelocityX;
        } else {


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

        if (currentState == State.CLING_LEFT){
            activeAnimation.reset();
            changeState(State.IDLE_LEFT);
            canMove = true;
            //handleIdle(dt);
            System.out.println("State changed from cling left to idle left");
            bounds = new Rectangle(position.x, position.y, animationManager.getIdleWidth(), animationManager.getIdleHeight());

        }


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

            if ((currentState != State.IDLE) && (currentState != State.IDLE_LEFT)) {

                if (leftMove){
                    changeState(State.IDLE_LEFT);
                } else{
                changeState(State.IDLE);}
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

    public Rectangle getAtkHitboxBounds(){ return atkHitboxBounds;}

    public Polygon getAtkHitbox() {return atkHitbox;}

    public boolean getIsAttacking() {return isAttacking;}

    public void dispose() { System.out.println("Perry Disposed!");} //texture.dispose();


}

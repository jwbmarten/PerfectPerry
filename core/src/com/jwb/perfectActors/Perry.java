package com.jwb.perfectActors;

import static com.jwb.perfectWorld.TileType.TILE_SIZE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.jwb.perfectWorld.TiledGameMap;

public class Perry {

    boolean leftMove;
    boolean rightMove;

    boolean leftPressed;
    boolean rightPressed;

    boolean rightRoll;

    // determines if Perry can stop his current animation or not
    boolean canMove;

    boolean grounded;

    boolean isAttacking;

    boolean isLockedOn;

    private PerfectEnemy lockedEnemy;

    boolean shieldUp;

    private float repositionHorizontal;

    private float motionTimer = 0f; // initialize a float to track time for motion delays

    private float shieldTimer = 0f;

    private int shieldOffsetRight = 70;

    private int shieldOffsetLeft = 0;

    private static final float ROLL_DEBOUNCE_TIME = 0.18f; // 100 ms debounce time to prevent accidental state switching

    private float idleTimer = 0f;

    private static final float RUNNING_RIGHT_DELAY = 0.3f; //500 ms

    private static final float RUN_RIGHT_LOOP_DELAY = 0.4f; // accounts for length of idle to running animation

    private int maxRunSpeed = 10;

    private int maxWalkSpeed = 5;

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

    private Animation activeAnimationBehind;

    private Animation activeAnimationForeground;

    private State currentState ;

    private ShieldState shieldState ;

    //track if Perry is colliding with anything
    public boolean colliding;

    public TiledGameMap currentLevel;

    public enum State {
        IDLE,
        IDLE_SHIELD_RIGHT,
        IDLE_LEFT,
        IDLE_SHIELD_LEFT,
        START_SHIELD_LEFT,
        START_WALK_LEFT,
        START_RUN_LEFT,
        WALKING_LEFT,
        WALKING_LEFT_SHIELD,
        REVERSE_WALKING_LEFT,
        REVERSE_WALKING_LEFT_SHIELD,
        RUNNING_LEFT,
        CLING_LEFT,
        ROLLING_LEFT,
        JUMP_BACK_LEFT,
        QUICK_ATTACK_LEFT,
        START_WALK_RIGHT,
        START_RUN_RIGHT,
        START_SHIELD_RIGHT,
        WALKING_RIGHT,
        WALKING_RIGHT_SHIELD,
        REVERSE_WALKING_RIGHT,
        REVERSE_WALKING_RIGHT_SHIELD,
        RUNNING_RIGHT,
        CLING_RIGHT,
        ROLLING_RIGHT,
        JUMP_BACK_RIGHT,
        QUICK_ATTACK_RIGHT,
    }

    public enum ShieldState {
        SHIELD_UP_RIGHT,
        SHIELD_CYCLE_RIGHT,
        SHIELD_DOWN_RIGHT,
        SHIELD_UP_LEFT,
        SHIELD_CYCLE_LEFT,
        SHIELD_DOWN_LEFT,
        NO_SHIELD
    }

    public Perry(int x, int y, TiledGameMap currentLevel){

        this.animationManager = new PerryAnimationMGMT();
        this.activeAnimation = animationManager.getAnimation(State.IDLE);
        this.shieldState = ShieldState.NO_SHIELD;
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

        isLockedOn = false;

        this.rightMove = true;

    }

    public void update(float dt, boolean leftPressed, boolean rightPressed, boolean spacePressed, boolean shieldPressed){

        this.leftPressed = leftPressed;
        this.rightPressed = rightPressed;

        if (leftPressed && rightPressed){
        System.out.println("Perry Update.   Left Pressed:  " + leftPressed + " Right Pressed:   " + rightPressed);}

        if (spacePressed && (motionTimer < ROLL_DEBOUNCE_TIME)){
            motionTimer += Gdx.graphics.getDeltaTime();
        }

//        if ((velocity.x < -maxRunSpeed) || (velocity.x > maxRunSpeed)){
//            System.out.println("SOMETHINGS FUCKY!");
//        }

        if (shieldPressed){
            shieldUp = true;
        } else { shieldUp = false;}

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

            newVelocityX = 0;

            if (Math.abs(velocity.x) < 0.1f) {
                velocity.x = 0;
                handleIdle(dt);

            } else {

//                System.out.println("Neither Left nor right pressed, slowing down");
                velocity.x *= 0.2f; // Apply a simple friction factor
            }
        }

        if (shieldPressed || (shieldState != ShieldState.NO_SHIELD)){
            handleShield(dt);
        }

        if (activeAnimationForeground != null){
            activeAnimationForeground.update(dt);
            activeAnimationBehind.update(dt);
        }

         activeAnimation.update(dt);

        // Update position based on velocity
        position.add(velocity.x, velocity.y, 0);

    }

    private void updateAnimation() {
        this.activeAnimation = animationManager.getAnimation(currentState);
//        System.out.println(activeAnimation.name + "  " + activeAnimation.frame);
    }

    public void chooseLeftMoveState() {

        // IF facing right

        if (rightMove && canMove){

            if ((isLockedOn) && (lockedEnemy.position.x > position.x)){

                    if (shieldUp){
                        changeState(State.REVERSE_WALKING_RIGHT_SHIELD);
                    } else {
                        changeState(State.REVERSE_WALKING_RIGHT);
                    }

                return;
            }

            rightMove = false;
            leftMove = true;

                switch (currentState) {
                    case IDLE_SHIELD_RIGHT:
                        changeState(State.WALKING_LEFT_SHIELD);
                        changeShieldState(ShieldState.SHIELD_CYCLE_LEFT);
                        break;
                    case START_SHIELD_RIGHT:
                        changeState(State.START_WALK_LEFT);

                        break;
                    case WALKING_RIGHT_SHIELD:
                        changeState(State.WALKING_LEFT_SHIELD);
                        changeShieldState(ShieldState.SHIELD_CYCLE_LEFT);
                        break;
                    default:
                        changeState(State.START_WALK_LEFT);

                }
            velocity.x = 0;
            newVelocityX = 0;
            return;
        }

        /// IF ALREADY MOVING LEFT

        switch(currentState) {
            case IDLE_LEFT:
                changeState(State.START_WALK_LEFT);
                break;
            case IDLE_SHIELD_LEFT:
                changeState(State.WALKING_LEFT_SHIELD);
                break;
            case START_WALK_LEFT:
                if (activeAnimation.isComplete()){
                    changeState(State.WALKING_LEFT);
                    canMove = true;}
                break;
            case WALKING_LEFT:
                if (shieldUp){
                    changeState(State.WALKING_LEFT_SHIELD);}
                if ((motionTimer > ROLL_DEBOUNCE_TIME) && this.canMove){
                    changeState(State.RUNNING_LEFT);}
                break;
            case WALKING_LEFT_SHIELD:
                if (!shieldUp){
                    changeState(State.WALKING_LEFT);}
                break;
        }

////        System.out.println("Starting method choose left move state");
//
//        // if the current state is idle set state to start running right
//        if ((rightMove) || (currentState == State.IDLE_LEFT) ){
//            activeAnimation.reset();
//            velocity.x = 0;
//            newVelocityX = 0;
//            if (shieldUp){
//                changeState(State.WALKING_LEFT_SHIELD);
//            } else{
//                changeState(State.START_WALK_LEFT);}
//            canMove = true;
////            System.out.println("State changed from idle or moving left to start run left");
//            leftMove = true;
//            rightMove = false;
//        }
//
//        if ((currentState == State.WALKING_LEFT) && (shieldUp)){
//            changeState(State.WALKING_LEFT_SHIELD);
//        }
//
//        if ((currentState == State.WALKING_LEFT_SHIELD) && (!shieldUp)){
//            changeState(State.WALKING_LEFT);
//        }
//
//
//        //if the current state is start running right and the animation is complete, set the state to running right
//        if ((currentState == State.START_WALK_LEFT) && activeAnimation.isComplete()){
//            changeState(State.WALKING_LEFT);
//            canMove = true;
//        }
//
//        if ((motionTimer > ROLL_DEBOUNCE_TIME) && this.canMove){
//            changeState(State.RUNNING_LEFT);
//        }

    }

    public void chooseRightMoveState(State currentState) {

        if (leftMove && canMove){

            if ((isLockedOn) && (lockedEnemy.position.x < position.x)){

                if (shieldUp){
                    changeState(State.REVERSE_WALKING_LEFT_SHIELD);
                } else {
                    changeState(State.REVERSE_WALKING_LEFT);
                }

                return;
            }

            leftMove = false;
            rightMove = true;

            switch (currentState){
                case IDLE_SHIELD_LEFT:
                    changeState(State.WALKING_RIGHT_SHIELD);
                    changeShieldState(ShieldState.SHIELD_CYCLE_RIGHT);
                    break;
                case START_SHIELD_LEFT:
                    changeState(State.START_WALK_RIGHT);

                    break;
                case WALKING_LEFT_SHIELD:
                    changeState(State.WALKING_RIGHT_SHIELD);
                    changeShieldState(ShieldState.SHIELD_CYCLE_RIGHT);
                    break;
                default:
                    changeState(State.START_WALK_RIGHT);

            }

            velocity.x = 0;
            newVelocityX = 0;
            return;
        }

        switch(currentState) {
            case IDLE:
                changeState(State.START_WALK_RIGHT);
                break;
            case IDLE_SHIELD_RIGHT:
                changeState(State.WALKING_RIGHT_SHIELD);
                break;
            case START_WALK_RIGHT:
                if (activeAnimation.isComplete()){
                    changeState(State.WALKING_RIGHT);
                    canMove = true;}
                break;
            case WALKING_RIGHT:
                if (shieldUp){
                    changeState(State.WALKING_RIGHT_SHIELD);}
                if ((motionTimer > ROLL_DEBOUNCE_TIME) && this.canMove){
                    changeState(State.RUNNING_RIGHT);}
                break;
            case WALKING_RIGHT_SHIELD:
                if (!shieldUp){
                    changeState(State.WALKING_RIGHT);}
                break;
        }

//        // if the current state is idle set state to start running right
//        if ((leftMove) || (currentState == State.IDLE) || (currentState == State.IDLE_SHIELD_RIGHT )){
//            activeAnimation.reset();
//            velocity.x = 0;
//            newVelocityX = 0;
//
//            if (shieldUp){
//                changeState(State.WALKING_RIGHT_SHIELD);
//            } else{
//            changeState(State.START_WALK_RIGHT);}
//            canMove = true;
////            System.out.print("State changed from idle or moving left to start run right");
//            leftMove = false;
//            rightMove = true;
//        }
//
//        if ((currentState == State.WALKING_RIGHT) && (shieldUp)){
//            changeState(State.WALKING_RIGHT_SHIELD);
//        }
//
//        if ((currentState == State.WALKING_RIGHT_SHIELD) && (!shieldUp)){
//            changeState(State.WALKING_RIGHT);
//        }
//
//
//        //if the current state is start walk right and the animation is complete, set the state to walking right
//        if ((currentState == State.START_WALK_RIGHT) && activeAnimation.isComplete()){
//
//            changeState(State.WALKING_RIGHT);
//            canMove = true;
//        }
//
//        if ((motionTimer > ROLL_DEBOUNCE_TIME) && this.canMove){
//            changeState(State.RUNNING_RIGHT);
//        }



    }

    public void changeState(State newState) {

        if (this.currentState != newState) {
//            System.out.println(activeAnimation.name + "  " + activeAnimation.frame);
            this.currentState = newState;
            System.out.println("State changed to" + newState);
            activeAnimation.reset();
            updateAnimation();
        }

        switch (newState) {
            case START_WALK_RIGHT:
                canMove = true;
                leftMove = false;
                rightMove = true;
            break;
            case START_WALK_LEFT:
                canMove = true;
                leftMove = true;
                rightMove = false;
            break;
        }
    }

    public void changeShieldState(ShieldState newState) {
        if (this.shieldState != newState) {

            if (activeAnimationForeground != null){
            activeAnimationForeground.reset();
            activeAnimationBehind.reset();}

            this.shieldState = newState;

            if (animationManager.getShieldAnimations(shieldState) != null) {
                activeAnimationForeground = animationManager.getShieldAnimations(shieldState)[0];
                activeAnimationBehind = animationManager.getShieldAnimations(shieldState)[1];
            } else {
                activeAnimationForeground = null;
                activeAnimationBehind = null;
            }
        }
    }

    public void perryCantStop(float dt){

        if (currentState == State.ROLLING_RIGHT){
            handleRoll(dt);
        }
    }

    public void handleShield(float dt){

        if (shieldTimer < 1f){
            shieldTimer += dt;
        }

        switch (shieldState) {
            case NO_SHIELD:
                if (rightMove) {
                    changeShieldState(ShieldState.SHIELD_UP_RIGHT);
                }
                if (leftMove) {
                    changeShieldState(ShieldState.SHIELD_UP_LEFT);
                }
                break;
            case SHIELD_UP_RIGHT:
                if (activeAnimationForeground.isComplete()) {
                    changeShieldState(ShieldState.SHIELD_CYCLE_RIGHT);
                }
                break;
            case SHIELD_UP_LEFT:
                if (activeAnimationForeground.isComplete()) {
                    changeShieldState(ShieldState.SHIELD_CYCLE_LEFT);
                    break;
                }
        }

//        // IF THERE IS NO SHIELD ANIMATION YET, START IT
//        if (shieldState == ShieldState.NO_SHIELD){
//            if (rightMove){
//                changeShieldState(ShieldState.SHIELD_UP_RIGHT);
//            }
//
//            if (leftMove){
//                changeShieldState(ShieldState.SHIELD_UP_LEFT);
//            }
//        }
//
//        // IF SHIELD START ANIMATIONS ARE COMPLETE, GO TO SHIELD LOOPS
//        if ((shieldState == ShieldState.SHIELD_UP_RIGHT) && (activeAnimationForeground.isComplete())){
//            changeShieldState(ShieldState.SHIELD_CYCLE_RIGHT);
//        }
//
//        if ((shieldState == ShieldState.SHIELD_UP_LEFT) && (activeAnimationForeground.isComplete())){
//            changeShieldState(ShieldState.SHIELD_CYCLE_LEFT);
//        }

        ///////////////////////////////////////////////////////////////////////////////
        /////// PERRY'S SHIELDING ANIMATIONS

        switch (currentState){
            case IDLE:
                changeState(State.START_SHIELD_RIGHT);
                break;
            case IDLE_LEFT:
                changeState(State.START_SHIELD_LEFT);
                break;
            case START_SHIELD_RIGHT:
                if (activeAnimation.isComplete()){
                changeState(State.IDLE_SHIELD_RIGHT);}
                break;
            case START_SHIELD_LEFT:
                if (activeAnimation.isComplete()){
                changeState(State.IDLE_SHIELD_LEFT);}
                break;
        }


//        if (currentState == State.IDLE){
//            changeState(State.START_SHIELD_RIGHT);
//        }
//
//        if (currentState == State.IDLE_LEFT){
//            changeState(State.START_SHIELD_LEFT);
//        }
//
//        if ((currentState == State.START_SHIELD_RIGHT) && (activeAnimation.isComplete())){
//            changeState(State.IDLE_SHIELD_RIGHT);
//        }
//
//        if ((currentState == State.START_SHIELD_LEFT) && (activeAnimation.isComplete())){
//            changeState(State.IDLE_SHIELD_LEFT);
//        }

        if (!shieldUp){

            if (rightMove){
                changeShieldState(ShieldState.SHIELD_DOWN_RIGHT);
            }

            if (leftMove){
                changeShieldState(ShieldState.SHIELD_DOWN_LEFT);
            }

            if (( (shieldState == ShieldState.SHIELD_DOWN_RIGHT) && (activeAnimationForeground.isComplete()) ) || ( (shieldState == ShieldState.SHIELD_DOWN_LEFT) && (activeAnimationForeground.isComplete()) )){
                changeShieldState(ShieldState.NO_SHIELD);
            }
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
                changeState(State.WALKING_LEFT);
                return;
            }

            //if the user is pressing right, transition to start run right
            if ((rightMove)) {
                changeState(State.START_WALK_RIGHT);
            }
        }
        ///////////////////////////////////////////////////////////////////////////////////////////



        ////////////////////////////////////////////////////////////////////////////////////////////
        // IF the rolling right animation is complete
        if ((currentState == State.ROLLING_RIGHT) && (activeAnimation.isComplete())){

            //either way, set canMove to true
            canMove = true;

            //and user is still pressing left, transition to running right
            if (rightMove){
                changeState(State.WALKING_RIGHT);
            return;}

            //if the user is pressing left, transition to start run left
            if (leftMove) {
                changeState(State.START_WALK_LEFT);
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

            if (this.leftPressed) {

                changeState(State.ROLLING_LEFT);
                canMove = false;

            }

            if (this.rightPressed) {

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
                position.x -= 192;
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
                position.x += 192;
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

        else {
            atkHitboxBounds = null;
            atkHitbox = null;
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

        chooseLeftMoveState();

        if (currentState == State.IDLE_LEFT){
            newVelocityX = 0;
        }

        if ((currentState == State.WALKING_LEFT) || (currentState == State.WALKING_LEFT_SHIELD) || (currentState == State.REVERSE_WALKING_RIGHT) || (currentState == State.REVERSE_WALKING_RIGHT_SHIELD)){
            newVelocityX = Math.max((velocity.x * 1.5f) - 1.5f * dt , -maxWalkSpeed);}

        else if (currentState == State.RUNNING_LEFT){
        newVelocityX = Math.max((velocity.x * 1.5f) - 1.5f * dt , -maxRunSpeed);} // Desired velocity increment


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

        chooseRightMoveState(currentState);

        if (currentState == State.IDLE){
            newVelocityX = 0;
        }

        if ((currentState == State.WALKING_RIGHT) || (currentState == State.WALKING_RIGHT_SHIELD) || (currentState == State.REVERSE_WALKING_LEFT) || (currentState == State.REVERSE_WALKING_LEFT_SHIELD)){
            newVelocityX = Math.min((velocity.x * 1.5f) + 1.5f * dt , maxWalkSpeed);}

        else if (currentState == State.RUNNING_RIGHT){
            newVelocityX = Math.min((velocity.x * 1.5f) + 1.5f * dt , maxRunSpeed);} // Desired velocity increment


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

        if (currentState == State.WALKING_RIGHT_SHIELD){
            activeAnimation.reset();
            changeState(State.IDLE_SHIELD_RIGHT);
            canMove = true;
            //handleIdle(dt);
            System.out.println("State changed from walk right shield to idle shield right");
            bounds = new Rectangle(position.x, position.y, animationManager.getIdleWidth(), animationManager.getIdleHeight());
        }

        if (currentState == State.WALKING_LEFT_SHIELD){
            activeAnimation.reset();
            changeState(State.IDLE_SHIELD_LEFT);
            canMove = true;
            //handleIdle(dt);
            System.out.println("State changed from walk left shield to idle shield left");
            bounds = new Rectangle(position.x, position.y, animationManager.getIdleWidth(), animationManager.getIdleHeight());
        }


        // Check that current (RIGHT ONLY FOR NOW) bounds not in collidable tile

        if (currentLevel.isTileCollidable(position.x + (float) bounds.width, position.y)){

//            System.out.println("Idle state detected collision!");
            float tileBoundary = (float) (Math.floor(position.x / TILE_SIZE) * TILE_SIZE);
            repositionHorizontal = 0f;
        }

            if ((currentState != State.IDLE) && (currentState != State.IDLE_LEFT) && (!shieldUp)) {

                if (leftMove){
                    changeState(State.IDLE_LEFT);
                    velocity.x = 0;
                } else{
                changeState(State.IDLE);}
                velocity.x = 0;
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

    public TextureRegion getTextureForeground(){

        if (activeAnimationForeground == null){
            return null;
        }
        return activeAnimationForeground.getFrame();
    }

    public TextureRegion getTextureBehind(){

        if (activeAnimationBehind == null){
            return null;
        }

        return activeAnimationBehind.getFrame();
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean getCanMove(){ return this.canMove; }

    public Rectangle getBounds() {return bounds;}

    public Rectangle getAtkHitboxBounds(){ return atkHitboxBounds;}

    public Polygon getAtkHitbox() {return atkHitbox;}

    public boolean getIsAttacking() {return isAttacking;}

    public float getMotionTimer() {
        return motionTimer;
    }
    public void resetMotionTimer(){
        this.motionTimer = 0;
    }

    public float getRollDebounceTime(){
        return ROLL_DEBOUNCE_TIME;
    }

    public int getShieldOffsetRight(Perry.State state){

//        switch (state) {
//
//            case IDLE_SHIELD_RIGHT:
//                return 70;
//            case WALKING_RIGHT_SHIELD:
//                return 70;
//            default:
//                return 0;
//        }

        return shieldOffsetRight;

    }

    public int getShieldOffsetLeft(Perry.State state){

//        switch (state) {
//
//            case IDLE_SHIELD_LEFT:
//                return 30;
//            case WALKING_LEFT_SHIELD:
//                return 0;
//            default:
//                return 0;
//        }

        return shieldOffsetLeft;
    }

    public void lockOn(PerfectEnemy enemy) {
        this.lockedEnemy = enemy;
        this.isLockedOn = enemy != null;
    }

    public void unlock() {
        this.isLockedOn = false;
        this.lockedEnemy = null;
    }

    public boolean isLockedOn() {
        return isLockedOn;
    }

    public PerfectEnemy getLockedEnemy() {
        return lockedEnemy;
    }

    public void dispose() { System.out.println("Perry Disposed!");} //texture.dispose();


}

package com.jwb.perfectActors;

import static com.jwb.perfectWorld.TileType.TILE_SIZE;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.jwb.perfectWorld.TileType;
import com.jwb.perfectWorld.TiledGameMap;

public class Perry {

    boolean leftMove;
    boolean rightMove;
    boolean rightRoll;

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
        ROLLING_RIGHT
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

    }

    public void update(float dt, boolean leftPressed, boolean rightPressed){


        if ((leftPressed)){
            leftMove = true;
            rightMove = false;


//        System.out.println("left pressed: " + leftPressed);

        }

        if (rightPressed){
            chooseRightMoveState(dt);
            setBounds(new Rectangle(position.x, position.y, activeAnimation.getFrameWidth(), activeAnimation.getFrameHeight()));
            handleMoveRight(dt);


//        System.out.println("right pressed: " + rightPressed);

        }


        if (activeAnimation == null){
            System.out.println("active animation is null :(");
        }






        //Update vertical velocity based on gravity
        if (!grounded) {
            handleGravity(dt);
        }


        // Update horizontal velocity based on movement direction
        if (leftPressed) {

            //call handle move left function
            handleMoveLeft(dt);

        }



            // If not moving left or right, slow down to 0



            if (!leftPressed && !rightPressed) {

                if (Math.abs(velocity.x) < 0.1f) {
                    velocity.x = 0;
                    handleIdle(dt);

                } else {

                    //                System.out.println("Neither Left nor right pressed");
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
            activeAnimation.reset();
//            motionTimer = 0;
//            idleTimer = 0;
            }


    }

    public void chooseRightMoveState(float dt) {

        // if current state is not one of the other right actions and that action is complete, set the current state
        // to start running right
        //if ((currentState != State.ROLLING_RIGHT) || activeAnimation.isComplete()) {

        boolean leftFlag = false;

        //if currently moving left

        if (leftMove){

            leftFlag = true;
            leftMove = false;
            rightMove = true;
//            motionTimer = 0; //reset timer
//            idleTimer = 0;
        }

//        System.out.println("Choosing Right Move State!");

            // if the current state is rolling right and the animation is
            // complete, set state to running right
            if ( (currentState == State.ROLLING_RIGHT) && (activeAnimation.isComplete()) )  {
                activeAnimation.reset();
                changeState(State.RUNNING_RIGHT);
                System.out.print("State changed from rolling right to start run right");
                leftMove = false;
                rightMove = true;
//                motionTimer = 0; //reset timer
//                idleTimer = 0;

            }

            // if the current state is idle or left flag is true, set state to start running right
            if ((currentState == State.IDLE) || (leftFlag)){
                activeAnimation.reset();
                changeState(State.START_RUN_RIGHT);
                System.out.print("State changed from idle or moving left to start run right");
                leftMove = false;
                rightMove = true;
//                motionTimer = 0; //reset timer
//                idleTimer = 0;
            }


            if (currentState != State.RUNNING_RIGHT) {

//                motionTimer += dt;

            }

            //if the current state is start running right and the animation is complete, set the state to running right
            if ((currentState == State.START_RUN_RIGHT) && activeAnimation.isComplete()){
                changeState(State.RUNNING_RIGHT);
            }



        ////////////////////////////////////////////////////////////////////////////////////////////
        //if current state is attack right, climb, etc and the animation is complete ADD STUFF HERE


    }

    public void changeState(State newState) {
        if (this.currentState != newState) {
            activeAnimation.reset();
            this.currentState = newState;
            updateAnimation();
        }
    }

    public void handleRoll(){

        System.out.println("handlin' rollin'");


        if (currentState!= State.ROLLING_RIGHT) {

            if (this.rightMove) {

                changeState(State.ROLLING_RIGHT);


            }
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
//        motionTimer += dt;

        if (currentState == State.CLING_RIGHT) {

            return;
        }

        if (currentState == State.ROLLING_RIGHT) {

            System.out.println("current x velocity: " + velocity.x);

            System.out.println("animation completion: " + activeAnimation.animationPercentComplete());

            newVelocityX = Math.max(maxRunSpeed * (2f - activeAnimation.animationPercentComplete()), 5);

        } else {

            newVelocityX = Math.min((velocity.x * 1.5f) + 1.5f * dt, maxRunSpeed); // Desired velocity increment
        }

        //check that future position plus with width of Perry is not a collidable tile
        float potentialX = position.x + bounds.width + newVelocityX;

        // Check for collision at Perry's front side
        if (!currentLevel.isTileCollidable(potentialX + bounds.width, position.y)) {
            velocity.x = newVelocityX;
        } else {
            // Adjust Perry's position to avoid penetrating the collidable tile
            velocity.x = 0;


            float tileBoundary = (float) (Math.floor(potentialX / TILE_SIZE) * TILE_SIZE);
            position.x = tileBoundary + 96; // -1 for a small buffer

            activeAnimation.reset();
            changeState(State.CLING_RIGHT);
//            System.out.println("State changed from moving right to cling right");
//
//            System.out.println("Perry current X pos: " + position.x + "Perry current Y pos: " + position.y);
//
//            System.out.println("current bounds width: " + bounds.getWidth());
//
//            System.out.println("Perry position + width: " + position.x + bounds.getWidth());
//
//            System.out.println("Position plus width collidable?: " + currentLevel.isTileCollidable(position.x + bounds.width, position.y));



        }
    }


    public void handleIdle(float dt){

//        System.out.println("Handlin; idlin'");


        if (currentState == State.CLING_RIGHT){
            activeAnimation.reset();
            changeState(State.IDLE);
            //handleIdle(dt);
            System.out.println("State changed from cling right to idle");
            bounds = new Rectangle(position.x, position.y, animationManager.getIdleWidth(), animationManager.getIdleHeight());

        }


//        System.out.println("Perry current X pos: " + position.x + "Perry current Y pos: " + position.y);
//
//
//        System.out.println("current bounds width: " + bounds.getWidth());
//
//        System.out.println("Perry position + width: " + position.x + bounds.getWidth());
//
//        System.out.println("Position plus width collidable?: " + currentLevel.isTileCollidable(position.x + ( bounds.width), position.y));


        // Check that current (RIGHT ONLY FOR NOW) bounds not in collidable tile

        if (currentLevel.isTileCollidable(position.x + (float) bounds.width, position.y)){

            System.out.println("Idle state detected collision!");

            float tileBoundary = (float) (Math.floor(position.x / TILE_SIZE) * TILE_SIZE);

            repositionHorizontal = 0f;

            //check that no additional collidable tiles to the left of the one detected, adjust repositionHorizontal if so

            while (currentLevel.isTileCollidable((position.x + (float) bounds.width) - repositionHorizontal, position.y)){

                System.out.println("adjusting tile boundary");

                repositionHorizontal += (float) TILE_SIZE;

            }

            position.x = tileBoundary - bounds.width - repositionHorizontal; // -1 for a small buffer

        }

        idleTimer += dt;

        if (idleTimer > STATE_TRANSITION_DEBOUNCE_TIME) {
//            System.out.println("SETTING TO IDLE C");
            if (currentState != State.IDLE) {
                changeState(State.IDLE);
            }
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

    public Rectangle getBounds() {return bounds;}

    public void dispose() { System.out.println("Perry Disposed!"); //texture.dispose();
         }

}

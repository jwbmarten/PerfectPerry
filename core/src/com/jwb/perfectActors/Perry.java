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

    private float motionTimer = 0f; // initialize a float to track time for motion delays

    private static final float STATE_TRANSITION_DEBOUNCE_TIME = 0.1f; // 100 ms debounce time to prevent accidental state switching

    private float idleTimer = 0f;

    private static final float RUNNING_RIGHT_DELAY = 0.3f; //500 ms

    private static final float RUN_RIGHT_LOOP_DELAY = 0.4f; // accounts for length of idle to running animation

    //static int to represent gravity
    private static final int GRAVITY = -15;

    //Vector 3 holds an x y and z axes, but we'll only be using 2 dimensions
    //this will represent Perry's position
    private Vector3 position;

    //this will represent Perry's velocity
    private Vector3 velocity;

    //this will be Perry's hitbox
    private Rectangle bounds;

    private Texture texture;

    private Texture textureRunRight;

    private Texture textureRunningRight;

    private Texture textureRollRight;

    private Animation activeAnimation;

    private Animation perryAnimation;

    private Animation perryAnimationRightRun;

    private Animation perryAnimationRightCycle;

    private Animation perryRollRight;

    private State currentState;

    //track if Perry is colliding with anything
    public boolean colliding;

    public TiledGameMap currentLevel;

    public enum State {
        IDLE,
        RUNNING_LEFT,
        START_RUN_RIGHT,
        RUNNING_RIGHT,
        ROLLING_RIGHT
    }
    public Perry(int x, int y, TiledGameMap currentLevel){

        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
//        grounded = false;
        this.currentLevel = currentLevel;
        texture = new Texture("PerryInactive80ms.png");
        perryAnimation = new Animation(new TextureRegion(texture), 35, 2.8f, true);
        activeAnimation = perryAnimation;

        textureRunRight = new Texture("PerryIdletoRunShort.png");
        perryAnimationRightRun = new Animation(new TextureRegion(textureRunRight), 5, 0.4f, false);

        textureRunningRight = new Texture("RunningLoopCLEANEDUP.png");
        perryAnimationRightCycle = new Animation(new TextureRegion(textureRunningRight), 6, 1f, true);

        textureRollRight = new Texture("RollRightWorking.png");
        perryRollRight = new Animation( new TextureRegion(textureRollRight), 6, .6f, false);

        bounds = new Rectangle(x, y, texture.getWidth()/35, texture.getHeight());
        colliding = false;

        currentState = State.IDLE; // default state

        rightRoll = false;

    }

    public void update(float dt, boolean leftPressed, boolean rightPressed){

//        if (leftPressed){
//        System.out.println("left pressed: " + leftPressed);}
//
//        if (rightPressed){
//        System.out.println("right pressed: " + rightPressed);}


        activeAnimation.update(dt);

        // Update the active animation based on the current state
        switch (currentState) {

            case IDLE:

                activeAnimation = perryAnimation; //idle animation
                bounds = new Rectangle(position.x, position.y, (float) texture.getWidth() /35, texture.getHeight());
                break;

            case RUNNING_LEFT:
                activeAnimation = perryAnimation; //idle animation
                break;

            case START_RUN_RIGHT:
                activeAnimation = perryAnimationRightRun;
                break;

            case RUNNING_RIGHT:
                activeAnimation = perryAnimationRightCycle;
                bounds = new Rectangle(position.x, position.y, (float) textureRunningRight.getWidth() /12, texture.getHeight());
                break;

            case ROLLING_RIGHT:
                activeAnimation = perryRollRight;
                break;

        }

        //Update vertical velocity based on gravity
        if (!grounded) {
            handleGravity(dt);
        }


        // Update horizontal velocity based on movement direction
        if (leftPressed) {

            //call handle move left function
            handleMoveLeft(dt);

        } else if (rightPressed) {

//            System.out.println("Right Move");

            if (motionTimer > RUN_RIGHT_LOOP_DELAY ){
                if ((currentState == State.START_RUN_RIGHT && perryAnimationRightRun.isComplete()) || (currentState == State.ROLLING_RIGHT && perryRollRight.isComplete())) {
                    currentState = State.RUNNING_RIGHT;
                    activeAnimation = perryAnimationRightCycle; // Switch to the running cycle animation
                    activeAnimation.reset(); // Reset the running cycle animation to start from the first frame
                }
            }
            // call handle move right function
            if (motionTimer > RUNNING_RIGHT_DELAY) {
            handleMoveRight(dt);}
            else {
                motionTimer += dt;
            }
        } else {
            // If not moving left or right, slow down to 0

//            System.out.println("Neither Left nor right pressed");

            if (!leftPressed && !rightPressed) {
                velocity.x *= 0.2f; // Apply a simple friction factor
                if (Math.abs(velocity.x) < 0.1f) {
                    velocity.x = 0;
                    handleIdle(dt);

                }
            }

        }

        // Update position based on velocity
        position.add(velocity.x, velocity.y, 0);

    }

    public void setLeftMove(boolean t)
    {
        if (t) {
            if (currentState != State.RUNNING_LEFT) {
//                System.out.println("Setting state to running left and resetting active animation.");
                currentState = State.RUNNING_LEFT;
                activeAnimation.reset();
                motionTimer = 0;
                idleTimer = 0;
            }
        }

        else if (currentState == State.RUNNING_LEFT){
            currentState = State.IDLE;
//            System.out.println("SETTING TO IDLE A");
            activeAnimation.reset();
            motionTimer = 0;

        }

        if(rightMove && t) rightMove = false;
        leftMove = t;

    }

    public void setRightMove(boolean t) {

        if ((currentState != State.ROLLING_RIGHT ) || perryRollRight.isComplete()) {

            if (t) {
                if (currentState != State.START_RUN_RIGHT) {
                    currentState = State.START_RUN_RIGHT;
                    activeAnimation = perryAnimationRightRun;
                    activeAnimation.reset();
                    motionTimer = 0; //reset timer
                    idleTimer = 0;
                }
            } else if (currentState == State.START_RUN_RIGHT) {
//                System.out.println("SETTING TO IDLE B");
                currentState = State.IDLE;
                activeAnimation.reset();
                motionTimer = 0;
            }

            if (leftMove && t) leftMove = false;

            rightMove = t;
        }
    }

    public void handleRoll(){

        System.out.println("handlin' rollin'");

        if (currentState!= State.ROLLING_RIGHT) {

            if (this.rightMove) {

                currentState = State.ROLLING_RIGHT;
                activeAnimation = perryRollRight;
                activeAnimation.reset();

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


        velocity.x = Math.min((velocity.x * 1.05f) - 1.5f * dt, -14); // Gradual speed build up to -4

    }

    public void handleMoveRight(float dt) {
        motionTimer += dt;

        float newVelocityX = Math.min((velocity.x * 1.5f) + 1.5f * dt, 8); // Desired velocity increment

        //check that future position plus with width of Perry is not a collidable tile
        float potentialX = position.x + bounds.width + newVelocityX;

        // Check for collision at Perry's front side
        if (!currentLevel.isTileCollidable(potentialX + bounds.width, position.y)) {
            velocity.x = newVelocityX;
        } else {
            // Adjust Perry's position to avoid penetrating the collidable tile
            velocity.x = 0;


            float tileBoundary = (float) (Math.floor(potentialX / TILE_SIZE) * TILE_SIZE);
            position.x = tileBoundary - bounds.width - 1; // -1 for a small buffer


        }
    }


    public void handleIdle(float dt){

        idleTimer += dt;

        if (idleTimer > STATE_TRANSITION_DEBOUNCE_TIME) {
//            System.out.println("SETTING TO IDLE C");
            if (currentState != State.IDLE) {
                currentState = State.IDLE;
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

    public void dispose() { texture.dispose(); }

}

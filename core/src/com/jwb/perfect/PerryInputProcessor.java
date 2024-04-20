package com.jwb.perfect;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.jwb.gameStates.PlayState;
import com.jwb.perfectActors.Perry;

import java.util.List;

public class PerryInputProcessor implements InputProcessor {

    private boolean rightPressed;

    private boolean leftPressed;

    private boolean spacePressed;

    private boolean shieldPressed;

    //initialize Perry field that will be assigned via constructor
    private Perry perry;

    PlayState playState;


    // For Debug - when space is press, set to true and then PlayState update will check if true
    // , if true return camera position and then set to false

    private boolean getCameraPosition;

    ////////////////////////////////////////////////////
    //  C O N S T R U C T O R  /////////////////////////
    ////////////////////////////////////////////////////

    /**
     * CONSTRUCTOR
     * takes the specific perry instance to be controlled.
     *
     * Called in the PlayState constructor
     * @param perry
     */
    public PerryInputProcessor(Perry perry, PlayState playState){

        this.perry = perry;
        this.playState = playState;
    }


    ////////////////////////////////////////////////////
    //  M E T H O D S //////////////////////////////////
    ////////////////////////////////////////////////////

    /**
     * Called when a key was pressed
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyDown(int keycode)
    {
        switch (keycode)
        {
            //case if A is pressed
            case Keys.A:
                //perry.setLeftMove(true);
                this.leftPressed = true;
                System.out.println("Left key pressed!");
                break;
            //case if D is pressed
            case Keys.D:
                //perry.setRightMove(true);
                this.rightPressed = true;
                System.out.println("Right key pressed!");
                break;
            //case if space is pressed
            case Keys.SPACE:

                if (perry.getCurrentState() == Perry.State.IDLE){
                    perry.setJumpBack();
                }
                if (perry.getCurrentState() == Perry.State.IDLE_LEFT){
                    perry.setJumpBack();
                }

                spacePressed = true;
                System.out.println("Space key pressed!");

                break;
            //case if L is pressed
            case Keys.K:
                perry.handleQuickAttack();
                break;
            case Keys.O:
                this.shieldPressed = true;
                break;
            case Keys.L:
                playState.handleLockOn();

        }
        return true;
    }

    /**
     * Called when a key was released
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyUp(int keycode) {
        switch (keycode)
        {
            //case Keys.LEFT:
            case Keys.A:
                //perry.setLeftMove(false);
                this.leftPressed = false;
                System.out.println("Left button released!");
                if (perry.getCurrentState() == Perry.State.CLING_LEFT){
                    perry.setCanMove(true);}
                break;
            //case Keys.RIGHT:
            case Keys.D:
                this.rightPressed = false;
                System.out.println("Right button released!");
                if (perry.getCurrentState() == Perry.State.CLING_RIGHT){
                perry.setCanMove(true);}
                //perry.setRightMove(false);
                break;

            case Keys.SPACE:
                this.spacePressed = false;
                if ((perry.getMotionTimer() < perry.getRollDebounceTime()) && (perry.getCurrentState() != Perry.State.JUMP_BACK_RIGHT) && (perry.getCurrentState() != Perry.State.JUMP_BACK_LEFT)){
                    perry.setRoll();
                }

                if (perry.getCurrentState() == Perry.State.RUNNING_RIGHT){
                    perry.changeState(Perry.State.WALKING_RIGHT);
                }

                if (perry.getCurrentState() == Perry.State.RUNNING_LEFT){
                    perry.changeState(Perry.State.WALKING_LEFT);
                }

                perry.resetMotionTimer();

            case Keys.O:
                this.shieldPressed = false;
        }
        return true;
    }

    /**
     * Returns handled input boolean array for Perry Update Method
     *
     * Index 0 : left pressed
     * Index 1 : right pressed
     *
     * @return
     */
    public boolean[] returnHandledInputs(){

        boolean[] inputArray = new boolean[4];

        if (this.leftPressed && this.rightPressed){

            inputArray[0] = false;
            inputArray[1] = true;

        } else{

            inputArray[0] = this.leftPressed;
            inputArray[1] = this.rightPressed;
        }

        inputArray[2] = this.spacePressed;
        inputArray[3] = this.shieldPressed;

        return inputArray;

    }

    public boolean isRightPressed(){
        return rightPressed;
    }

    public boolean isLeftPressed(){
        return leftPressed;
    }

    public boolean isGetCameraPosition() {
        return getCameraPosition;
    }

    public void setGetCameraPosition(boolean getCameraPosition) {
        this.getCameraPosition = getCameraPosition;
    }

    /**
     * Called when a key was typed
     *
     * @param character The character
     * @return whether the input was processed
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Called when the screen was touched or a mouse button was pressed. The button parameter will be {@link Buttons#LEFT} on iOS.
     *
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Called when a finger was lifted or a mouse button was released. The button parameter will be {@link Buttons#LEFT} on iOS.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Called when the touch gesture is cancelled. Reason may be from OS interruption to touch becoming a large surface such as
     * the user cheek). Relevant on Android and iOS only. The button parameter will be {@link Buttons#LEFT} on iOS.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchDragged (int x, int y, int pointer) {
        return false;
    }

    public boolean mouseMoved (int x, int y) {
        return false;
    }

    public boolean scrolled (float amountX, float amountY) {
        return false;
    }
}


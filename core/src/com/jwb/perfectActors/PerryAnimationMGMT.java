package com.jwb.perfectActors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class PerryAnimationMGMT {

    Perry perry;


    public float animationWidth;
    public float animationHeight;

    private Animation perryIdle;
    private Animation perryStartRunRight;
    private Animation perryRunRight;
    private Animation perryRollRight;
    private Animation perryClingRight;



    private float idleWidth;
    private float idleHeight;


    private int idleFrameCount;
    private float idleCycleTime;


    private float runStartDelay;
    public float startToRunWidth;
    public float startToRunHeight;
    private int startToRunFrameCount;
    private float startToRunCycleTime;


    private float timeToRunCycle;
    private float runWidth;
    private float runHeight;
    private int runFrameCount;
    private float runCycleTime;

    private float rollWidth;
    private float rollHeight;
    private int rollFrameCount;
    private float rollCycleTime;

    private float clingWidth;
    private float clingHeight;
    private int clingFrameCount;
    private float clingCycleTime;


    private Rectangle bounds;


    private int gravity = -15;

    private float motionTimer = 0f; // initialize a float to track time for motion delays

    private float idleTimer = 0f;

    public PerryAnimationMGMT() {
        loadAnimations();
    }

    private void loadAnimations() {


        ////////////////////////////////////////////////////////////////////////////////////////////
        /////////////   P E R R Y 'S   A N I M A T I O N S     /////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////




        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        ///                                      I D L E                                         ///
        ////////////////////////////////////////////////////////////////////////////////////////////

        idleFrameCount = 35;
        idleCycleTime = 2.8f;
        boolean idleIsCycle = true;

        Texture textureIdle = new Texture("PerryInactive80ms.png");
        perryIdle = new Animation(new TextureRegion(textureIdle), idleFrameCount, idleCycleTime, idleIsCycle);


        idleWidth = textureIdle.getWidth() /((float) idleFrameCount);
        idleHeight = textureIdle.getHeight();

        //bounds = new Rectangle()

        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////



        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        ///                             S T A R T   R U N N I N G                                ///
        ////////////////////////////////////////////////////////////////////////////////////////////

        startToRunFrameCount = 5;
        startToRunCycleTime = 0.4f;
        boolean startToRunIsCycle = false;


        ///                        S T A R T     R U N    R I G H T                              ///


        Texture textureRunRight = new Texture("PerryIdletoRunShort.png");
        perryStartRunRight = new Animation(new TextureRegion(textureRunRight), startToRunFrameCount, startToRunCycleTime, startToRunIsCycle);

        startToRunWidth = textureRunRight.getWidth() /((float) startToRunFrameCount);
        startToRunHeight = textureRunRight.getHeight();

        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        ///                                   R U N N I N G                                      ///
        ////////////////////////////////////////////////////////////////////////////////////////////

        runFrameCount = 8;
        runCycleTime = 1f;
        runStartDelay = 0.3f;
        timeToRunCycle = 0.4f;

        boolean runIsCycle = true;






        ///                                 R U N    R I G H T                                   ///

        Texture textureStartRunRight = new Texture("RunningLoopCLEANEDUP.png");
        perryRunRight = new Animation(new TextureRegion(textureStartRunRight), runFrameCount, runCycleTime, runIsCycle);
        runWidth = textureRunRight.getWidth() /((float) runFrameCount);
        runHeight = textureRunRight.getHeight();


        ///                                  R U N   L E F T                                     ///

            ///TODO
            ///
            ///

        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////



        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        ///                                W A L L   C L I N G                                   ///
        ////////////////////////////////////////////////////////////////////////////////////////////

        clingFrameCount = 1;
        clingCycleTime = 6f;
        boolean clingIsCycle = true;

        ///                               C L I N G    R I G H T                                 ///

        Texture textureClingRight = new Texture("RightWallCling.png");
        perryClingRight = new Animation(new TextureRegion(textureClingRight), clingFrameCount, clingCycleTime, clingIsCycle);

        clingWidth = textureClingRight.getWidth() /((float) runFrameCount);
        clingHeight = textureClingRight.getHeight();


        ///                                C L I N G   L E F T                                    ///

        ///TODO
        ///
        ///

        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////



        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        ///                                   R O L L I N G                                      ///
        ////////////////////////////////////////////////////////////////////////////////////////////

        rollFrameCount = 6;
        rollCycleTime = .6f;
        boolean rollIsCycle = false;

        ///                                R O L L    R I G H T                                  ///

        Texture textureRollRight = new Texture("RollRightWorking.png");
        perryRollRight = new Animation(new TextureRegion(textureRollRight), rollFrameCount, rollCycleTime, rollIsCycle);
        rollWidth = textureRollRight.getWidth() /((float) rollFrameCount);
        rollHeight = textureRollRight.getHeight();

        ///                                 R O L L   L E F T                                    ///

        ///TODO
        ///
        ///

        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////

        // MORE TO COME



    }

    public Animation getAnimation(Perry.State state) {
        switch (state) {
            case IDLE:
                System.out.println("Returning IDLE animation");
                this.animationWidth = idleWidth;
                this.animationHeight = idleHeight;
                return perryIdle;
            case START_RUN_RIGHT:
                System.out.println("Returning START_RUN_RIGHT animation");
                this.animationWidth = startToRunHeight;
                this.animationHeight = startToRunHeight;
                this.animationWidth = startToRunWidth;
                this.animationHeight = startToRunHeight;
                return perryStartRunRight;
            case RUNNING_RIGHT:
                System.out.println("Returning RUNNING_RIGHT animation");
                this.animationWidth = runWidth;
                this.animationHeight = runHeight;
                return perryRunRight;
            case CLING_RIGHT:
                System.out.println("Returning CLING_RIGHT animation");
                this.animationWidth = clingWidth;
                this.animationHeight = clingHeight;
                return perryClingRight;
            case ROLLING_RIGHT:
                System.out.println("Returning ROLLING_RIGHT animation");
                this.animationWidth = rollWidth;
                this.animationHeight = rollHeight;
                return perryRollRight;
            default:
                System.out.println("Returning default (IDLE) animation");
                this.animationWidth = idleWidth;
                this.animationHeight = idleHeight;
                return perryIdle;        }
    }


    public float getIdleWidth() {
        return idleWidth;
    }

    public float getIdleHeight() {
        return idleHeight;
    }

    public float getRunWidth() {
        return runWidth;
    }

    public float getRunHeight() {
        return runHeight;
    }

    public float getRollWidth() {
        return rollWidth;
    }

    public float getRollHeight() {
        return rollHeight;
    }




}

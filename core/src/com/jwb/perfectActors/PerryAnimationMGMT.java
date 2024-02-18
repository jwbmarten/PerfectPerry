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
    private Animation perryJumpBackRight;
    private Animation perryClingRight;
    private Animation perryQuickAttackRight;



    private float idleWidth;
    private float idleHeight;
    private float jumpBackWidth;
    private float jumpBackHeight;


    private int idleFrameCount;
    private float idleCycleTime;
    private int jumpBackFrameCount;
    private float jumpBackCycleTime;


    public float startToRunWidth;
    public float startToRunHeight;
    private int startToRunFrameCount;
    private float startToRunCycleTime;


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

    private float quickAttackWidth;
    private float quickAttackHeight;
    private int quickAttackFrameCount;
    private float quickAttackCycleTime;



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

        idleFrameCount = 33;
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
        ///                                J U M P   B A C K                                     ///
        ////////////////////////////////////////////////////////////////////////////////////////////

        jumpBackFrameCount = 13;
        jumpBackCycleTime = 0.8f;
        boolean jumpBackIsCycle = false;


        Texture textureJumpBack = new Texture("PerryJumpBackRight.png");
        perryJumpBackRight = new Animation(new TextureRegion(textureJumpBack), jumpBackFrameCount, jumpBackCycleTime, jumpBackIsCycle);

        jumpBackWidth = textureJumpBack.getWidth() /((float) jumpBackFrameCount);
        jumpBackHeight = textureJumpBack.getHeight();


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

        runFrameCount = 12;
        runCycleTime = 1f;

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





        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        ///                               Q U I C K   A T T A C K                                ///
        ////////////////////////////////////////////////////////////////////////////////////////////


        quickAttackFrameCount = 10;
        quickAttackCycleTime = 1f;
        boolean quickAttackIsCycle = false;

        ///                          Q U I C K   A T T A C K   R I G H T                         ///

        Texture textureQuickAttackRight = new Texture("PerryQuickAttackRight.png");
        perryQuickAttackRight = new Animation(new TextureRegion(textureQuickAttackRight), quickAttackFrameCount, quickAttackCycleTime, quickAttackIsCycle);
        quickAttackWidth = textureQuickAttackRight.getWidth();
        quickAttackHeight = textureQuickAttackRight.getHeight();

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
            case JUMP_BACK_RIGHT:
                System.out.println("Returning JUMP_BACK_RIGHT animation");
                this.animationWidth = jumpBackWidth;
                this.animationHeight = jumpBackHeight;
                return perryJumpBackRight;
            case QUICK_ATTACK_RIGHT:
                System.out.println("Returning QUICK_ATTACK_RIGHT animation");
                this.animationWidth = quickAttackWidth;
                this.animationHeight = quickAttackHeight;
                return perryQuickAttackRight;
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

package com.jwb.perfectActors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class PerryAnimationMGMT {

    static class attackBoxes{

        Rectangle boundingBox;
        Polygon hitPoly;

        public attackBoxes(Rectangle boundingBox, Polygon hitPoly){
            this.boundingBox = boundingBox;
            this.hitPoly = hitPoly;
        }

        public Polygon getHitPoly() {
            return hitPoly;
        }

        public Rectangle getBoundingBox() {
            return boundingBox;
        }
    }

    static class atkBounds{

        int xOffset;
        int yOffset;
        int width;
        int height;

        public atkBounds(int xOffset, int yOffset, int width, int height){
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            this.width = width;
            this.height = height;
        }
    }

    static class animationVertices{

        ArrayList<frameVertices> frameVerticesList = new ArrayList<>();

        public void addFrameVertices(frameVertices frameVertices){
            this.frameVerticesList.add(frameVertices);
        }

        public frameVertices getFrameVerts(int frame){

            return this.frameVerticesList.get(frame);
        }
    }

    static class frameVertices{

        int frameNum;
        atkBounds attackBounds;
        int frameWidth;

        ArrayList<hitboxVertices> hitboxVerticesList = new ArrayList<>();


        frameVertices(int frameNum){
            this.frameNum = frameNum;
        }

        public void addVertices(int xCoord, int yCoord){
            this.hitboxVerticesList.add(new hitboxVertices(xCoord, yCoord));
        }

        public int getFrameVertSize(){ return this.hitboxVerticesList.size();}

        public void setBoundsInfo(atkBounds atkBounds){

            this.attackBounds = atkBounds;
        }

    }


    static class hitboxVertices {
        int xCoordinate;
        int yCoordinate;


        hitboxVertices(int xCoordinate, int yCoordinate) {
            this.xCoordinate = xCoordinate;
            this.yCoordinate = yCoordinate;
        }

        hitboxVertices(int frameNum) {}
    }

    Perry perry;


    public float animationWidth;
    public float animationHeight;

    private Animation perryIdle;
    private Animation perryIdleLEFT;
    private Animation perryStartRunRight;
    private Animation perryStartRunLEFT;
    private Animation perryRunRight;
    private Animation perryRunLEFT;
    private Animation perryRollRight;
    private Animation perryRollLEFT;
    private Animation perryJumpBackRight;
    private Animation perryJumpBackLEFT;
    private Animation perryClingRight;
    private Animation perryClingLEFT;

    private Animation perryQuickAttackRight;
    private Animation perryQuickAttackLEFT;
    animationVertices quickAttackVertices = new animationVertices();



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

        ///                                 I D L E   L E F T                                    ///

        Texture textureIdleLEFT = new Texture("PerryIdleLEFT.png");
        perryIdleLEFT = new Animation(new TextureRegion(textureIdleLEFT), idleFrameCount, idleCycleTime, idleIsCycle, "Idle Left");



        ///                               I D L E   R I G H T                                    ///

        Texture textureIdle = new Texture("PerryInactive80ms.png");
        perryIdle = new Animation(new TextureRegion(textureIdle), idleFrameCount, idleCycleTime, idleIsCycle, "Idle Right");

        idleWidth = textureIdle.getWidth() /((float) idleFrameCount);
        idleHeight = textureIdle.getHeight();

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


        ///                            J U M P   B A C K   L E F T                               ///

        Texture textureJumpBackLEFT = new Texture("PerryJumpBackLEFT.png");
        perryJumpBackLEFT = new Animation(new TextureRegion(textureJumpBackLEFT), jumpBackFrameCount, jumpBackCycleTime, jumpBackIsCycle, "Jump Back Left");

        ///                           J U M P   B A C K   R I G H T                              ///


        Texture textureJumpBack = new Texture("PerryJumpBackRight.png");
        perryJumpBackRight = new Animation(new TextureRegion(textureJumpBack), jumpBackFrameCount, jumpBackCycleTime, jumpBackIsCycle, "Jump Back Right");

        jumpBackWidth = textureJumpBack.getWidth() /((float) jumpBackFrameCount);
        jumpBackHeight = textureJumpBack.getHeight();


        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        ///                             S T A R T   R U N N I N G                                ///
        ////////////////////////////////////////////////////////////////////////////////////////////

        startToRunFrameCount = 5;
        startToRunCycleTime = 0.4f;
        boolean startToRunIsCycle = false;


        ///                         S T A R T     R U N    L E F T                               ///

        Texture textureStartRunLEFT = new Texture("PerryIdletoRunLEFT.png");
        perryStartRunLEFT = new Animation(new TextureRegion(textureStartRunLEFT), startToRunFrameCount, startToRunCycleTime, startToRunIsCycle, "Start Run Cycle Left");


        ///                        S T A R T     R U N    R I G H T                              ///


        Texture textureStartRunRight = new Texture("PerryIdletoRunShort.png");
        perryStartRunRight = new Animation(new TextureRegion(textureStartRunRight), startToRunFrameCount, startToRunCycleTime, startToRunIsCycle, "Start Run Cycle Right");



        startToRunWidth = textureStartRunRight.getWidth() /((float) startToRunFrameCount);
        startToRunHeight = textureStartRunRight.getHeight();

        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        ///                                   R U N N I N G                                      ///
        ////////////////////////////////////////////////////////////////////////////////////////////

        runFrameCount = 12;
        runCycleTime = 1f;

        boolean runIsCycle = true;


        ///                                  R U N   L E F T                                     ///

        Texture textureRunLEFT = new Texture("RunningLoopLEFT.png");
        perryRunLEFT = new Animation(new TextureRegion(textureRunLEFT), runFrameCount, runCycleTime, runIsCycle, "Running Left");


        ///                                 R U N    R I G H T                                   ///

        Texture textureRunRight = new Texture("RunningLoopCLEANEDUP.png");
        perryRunRight = new Animation(new TextureRegion(textureRunRight), runFrameCount, runCycleTime, runIsCycle, "Running Right");



        runWidth = textureRunRight.getWidth() /((float) runFrameCount);
        runHeight = textureRunRight.getHeight();



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

        ///                                C L I N G   L E F T                                    ///

        Texture textureClingLEFT = new Texture("LEFTWallCling.png");
        perryClingLEFT = new Animation(new TextureRegion(textureClingLEFT), clingFrameCount, clingCycleTime, clingIsCycle, "Cling Left");


        ///                               C L I N G    R I G H T                                 ///

        Texture textureClingRight = new Texture("RightWallCling.png");
        perryClingRight = new Animation(new TextureRegion(textureClingRight), clingFrameCount, clingCycleTime, clingIsCycle, "Cling Right");



        clingWidth = textureClingRight.getWidth() /((float) runFrameCount);
        clingHeight = textureClingRight.getHeight();



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

        ///                                 R O L L   L E F T                                    ///

        Texture textureRollLEFT = new Texture("RollLEFTWorking.png");
        perryRollLEFT = new Animation(new TextureRegion(textureRollLEFT), rollFrameCount, rollCycleTime, rollIsCycle, "Roll Left");

        ///                                R O L L    R I G H T                                  ///

        Texture textureRollRight = new Texture("RollRightWorking.png");
        perryRollRight = new Animation(new TextureRegion(textureRollRight), rollFrameCount, rollCycleTime, rollIsCycle, "Roll Right");



        rollWidth = textureRollRight.getWidth() /((float) rollFrameCount);
        rollHeight = textureRollRight.getHeight();



        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////




        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        ///                               Q U I C K   A T T A C K                                ///
        ////////////////////////////////////////////////////////////////////////////////////////////


        quickAttackFrameCount = 10;
        quickAttackCycleTime = 1f;
        boolean quickAttackIsCycle = false;



        quickAttackVertices.addFrameVertices(new frameVertices(0));
        quickAttackVertices.addFrameVertices(new frameVertices(1));
        quickAttackVertices.addFrameVertices(new frameVertices(2));
        quickAttackVertices.addFrameVertices(new frameVertices(3));
        quickAttackVertices.addFrameVertices(new frameVertices(4));

        frameVertices quickAttFr5 = new frameVertices(5);
        quickAttFr5.addVertices(208, 192);
        quickAttFr5.addVertices(215, 195);
        quickAttFr5.addVertices(143, 275);
        quickAttFr5.addVertices(100, 270);
        quickAttFr5.setBoundsInfo(new atkBounds(91, 190, 126, 89));
        quickAttackVertices.addFrameVertices(quickAttFr5);



        frameVertices quickAttFr6 = new frameVertices(6);
        quickAttFr6.addVertices(245, 98);
        quickAttFr6.addVertices(245, 165);
        quickAttFr6.addVertices(228, 268);
        quickAttFr6.addVertices(258, 264);
        quickAttFr6.addVertices(303, 237);
        quickAttFr6.addVertices(350, 172);
        quickAttFr6.addVertices(359, 117);
        quickAttFr6.addVertices(256, 42);
        quickAttFr6.setBoundsInfo(new atkBounds(227, 43, 132, 231));
        quickAttackVertices.addFrameVertices(quickAttFr6);


        quickAttackVertices.addFrameVertices(new frameVertices(7));
        quickAttackVertices.addFrameVertices(new frameVertices(8));
        quickAttackVertices.addFrameVertices(new frameVertices(9));







        ///                           Q U I C K   A T T A C K   L E F T                          ///

        Texture textureQuickAttackLEFT = new Texture("PerryQuickAttackLEFT.png");
        perryQuickAttackLEFT = new Animation(new TextureRegion(textureQuickAttackLEFT), quickAttackFrameCount, quickAttackCycleTime, quickAttackIsCycle, "Quick Attack Left");


        ///                          Q U I C K   A T T A C K   R I G H T                         ///

        Texture textureQuickAttackRight = new Texture("PerryQuickAttackRight.png");
        perryQuickAttackRight = new Animation(new TextureRegion(textureQuickAttackRight), quickAttackFrameCount, quickAttackCycleTime, quickAttackIsCycle, "Quick Attack Right");


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
            case IDLE_LEFT:
                System.out.println("Returning IDLE_LEFT animation");
                this.animationWidth = idleWidth;
                this.animationHeight = idleHeight;
                return perryIdleLEFT;
            case START_RUN_LEFT:
                System.out.println("Returning START_RUN_LEFT animation");
                this.animationWidth = startToRunWidth;
                this.animationHeight = startToRunHeight;
                return perryStartRunLEFT;
            case START_RUN_RIGHT:
                System.out.println("Returning START_RUN_RIGHT animation");
                this.animationWidth = startToRunWidth;
                this.animationHeight = startToRunHeight;
                return perryStartRunRight;
            case RUNNING_LEFT:
                System.out.println("Returning RUNNING_LEFT animation");
                this.animationWidth = runWidth;
                this.animationHeight = runHeight;
                return perryRunLEFT;
            case RUNNING_RIGHT:
                System.out.println("Returning RUNNING_RIGHT animation");
                this.animationWidth = runWidth;
                this.animationHeight = runHeight;
                return perryRunRight;
            case CLING_LEFT:
                System.out.println("Returning CLING_LEFT animation");
                this.animationWidth = clingWidth;
                this.animationHeight = clingHeight;
                return perryClingLEFT;
            case CLING_RIGHT:
                System.out.println("Returning CLING_RIGHT animation");
                this.animationWidth = clingWidth;
                this.animationHeight = clingHeight;
                return perryClingRight;
            case ROLLING_LEFT:
                System.out.println("Returning ROLLING_RIGHT animation");
                this.animationWidth = rollWidth;
                this.animationHeight = rollHeight;
                return perryRollLEFT;
            case ROLLING_RIGHT:
                System.out.println("Returning ROLLING_RIGHT animation");
                this.animationWidth = rollWidth;
                this.animationHeight = rollHeight;
                return perryRollRight;
            case JUMP_BACK_LEFT:
                System.out.println("Returning JUMP_BACK_LEFT animation");
                this.animationWidth = jumpBackWidth;
                this.animationHeight = jumpBackHeight;
                return perryJumpBackLEFT;
            case JUMP_BACK_RIGHT:
                System.out.println("Returning JUMP_BACK_RIGHT animation");
                this.animationWidth = jumpBackWidth;
                this.animationHeight = jumpBackHeight;
                return perryJumpBackRight;
            case QUICK_ATTACK_LEFT:
                System.out.println("Returning QUICK_ATTACK_LEFT animation");
                this.animationWidth = quickAttackWidth;
                this.animationHeight = quickAttackHeight;
                return perryQuickAttackLEFT;
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

    public float[] mapHitboxVerts(Vector3 position, frameVertices frameVerts, float frameWidth, boolean reverse) {

        if (frameVerts.getFrameVertSize() == 0) {
            return null;
        }

        float[] mappedHitbox = new float[frameVerts.getFrameVertSize()*2];
        int i = 0;

        if (reverse) {
            for (hitboxVertices hbVerts : frameVerts.hitboxVerticesList) {
                mappedHitbox[i] = (position.x + frameWidth - hbVerts.xCoordinate);
                i ++;
                mappedHitbox[i] = (position.y - hbVerts.yCoordinate);
                i ++;
            }
        } else {
            for (hitboxVertices hbVerts : frameVerts.hitboxVerticesList) {
                mappedHitbox[i] = (position.x + hbVerts.xCoordinate);
                i ++;
                mappedHitbox[i] = (position.y + hbVerts.yCoordinate);
                i ++;
            }
        }
        return mappedHitbox;
    }

    public Rectangle mapBoundingBox(Vector3 position, frameVertices frameVerts, float frameWidth, boolean reverse) {

        if (reverse){
            return new Rectangle(position.x + frameWidth - frameVerts.attackBounds.xOffset, position.y + frameVerts.attackBounds.yOffset, frameVerts.attackBounds.width, frameVerts.attackBounds.height);
        }

        else{
            return new Rectangle(position.x + frameVerts.attackBounds.xOffset, position.y + frameVerts.attackBounds.yOffset, frameVerts.attackBounds.width, frameVerts.attackBounds.height);

        }
    }


    public attackBoxes getAttackHitbox(Vector3 position,Perry.State state, int currentFrame) {

        switch (state) {
            case QUICK_ATTACK_RIGHT:

                frameVertices currentFrameVerts = quickAttackVertices.getFrameVerts(currentFrame);
                float[] mappedHitboxVerts = mapHitboxVerts(position, currentFrameVerts, quickAttackWidth, false);

                if (mappedHitboxVerts != null){
                    System.out.println("hitbox not null!");
                return new attackBoxes(mapBoundingBox(position, currentFrameVerts, quickAttackWidth, false), new Polygon(mapHitboxVerts(position, currentFrameVerts, quickAttackWidth, false)));}

                else { return null;}

            default:
                return null;


        }
    }






}

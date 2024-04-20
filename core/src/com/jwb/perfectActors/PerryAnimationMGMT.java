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


    frameVertices currentFrameVerts;
    float[] mappedHitboxVerts;

    public float animationWidth;
    public float animationHeight;

    private Animation perryIdle;
    private Animation perryIdleLEFT;
    private Animation perryStartWalkRight;
    private Animation perryStartWalkLEFT;
    private Animation perryWalkRight;
    private Animation perryREVWalkRight;
    private Animation perryWalkLEFT;
    private Animation perryREVWalkLEFT;
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

    private Animation perryShieldUpLEFT;
    private Animation perryShieldUpRight;
    private Animation shieldUpForegroundRight;
    private Animation shieldUpForegroundLEFT;
    private Animation shieldUpBackgroundRight;
    private Animation shieldUpBackgroundLEFT;
    private Animation shieldLoopForegroundRight;
    private Animation shieldLoopForegroundLEFT;
    private Animation shieldLoopBackgroundRight;
    private Animation shieldLoopBackgroundLEFT;
    private Animation shieldDownForegroundRight;
    private Animation shieldDownForegroundLEFT;
    private Animation shieldDownBackgroundRight;
    private Animation shieldDownBackgroundLEFT;
    private Animation perryShieldIdleRight;
    private Animation perryShieldIdleLEFT;
    private Animation perryWalkRightShield;
    private Animation perryWalkLEFTShield;
    private Animation perryREVWalkRightShield;
    private Animation perryREVWalkLEFTShield;
    private Animation perryShieldLoopRight;
    animationVertices shieldLoopVertices = new animationVertices();

    private float idleWidth;
    private float idleHeight;
    private float jumpBackWidth;
    private float jumpBackHeight;

    private int idleFrameCount;
    private float idleCycleTime;
    private int jumpBackFrameCount;
    private float jumpBackCycleTime;

    public float startToWalkWidth;
    public float startToWalkHeight;
    private int startToWalkFrameCount;
    private float startToWalkCycleTime;

    private float walkWidth;
    private float walkHeight;
    private int walkFrameCount;
    private float walkCycleTime;

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

    private float shieldUpWidth;
    private float shieldUpHeight;
    private int shieldUpFrameCount;
    private float shieldUpCycleTime;

    private float shieldONLYUpWidth;
    private float shieldONLYUpHeight;
    private int shieldONLYUpFrameCount;
    private float shieldONLYUpCycleTime;

    private float shieldIdleWidth;
    private float shieldIdleHeight;
    private int shieldIdleFrameCount;
    private float shieldIdleCycleTime;

    private float shieldLoopWidth;
    private float shieldLoopHeight;
    private int shieldLoopFrameCount;
    private float shieldLoopCycleTime;

    private float shieldDownWidth;
    private float shieldDownHeight;
    private int shieldDownFrameCount;
    private float shieldDownCycleTime;

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
        ///                             S T A R T   W A L K I N G                                ///
        ////////////////////////////////////////////////////////////////////////////////////////////

        startToWalkFrameCount = 3;
        startToWalkCycleTime = 0.3f;
        boolean startToWalkIsCycle = false;

        ///                          S T A R T   W A L K   L E F T                               ///

        Texture textureStartWalkLEFT = new Texture("PerryIdletoWalkLEFT.png");
        perryStartWalkLEFT = new Animation(new TextureRegion(textureStartWalkLEFT), startToWalkFrameCount, startToWalkCycleTime, startToWalkIsCycle, "Start Walk LEFT");

        ///                         S T A R T   W A L K   R I G H T                              ///

        Texture textureStartWalkRIGHT = new Texture("PerryIdletoWalkRight.png");
        perryStartWalkRight = new Animation(new TextureRegion(textureStartWalkRIGHT), startToWalkFrameCount, startToWalkCycleTime, startToWalkIsCycle, "Start Walk RIGHT");

        startToWalkWidth = textureStartWalkRIGHT.getWidth() / ((float) startToWalkFrameCount);
        startToWalkHeight = textureStartWalkRIGHT.getHeight();

        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        ///                                   W A L K I N G                                      ///
        ////////////////////////////////////////////////////////////////////////////////////////////

        walkFrameCount = 8;
        walkCycleTime = 1f;
        boolean walkIsCycle = true;

        ///                                W A L K   L E F T                                     ///

        Texture textureWalkingLEFT = new Texture("PerryWalkLEFTCycle.png");
        perryWalkLEFT = new Animation(new TextureRegion(textureWalkingLEFT), walkFrameCount, walkCycleTime, walkIsCycle, "Walking LEFT");

        ///                              W A L K   R I G H T                                     ///

        Texture textureWalkingRight = new Texture("PerryWalkRightCycle.png");
        perryWalkRight = new Animation(new TextureRegion(textureWalkingRight), walkFrameCount, walkCycleTime, walkIsCycle, "Walking RIGHT");


        ///                        R E V E R S E   W A L K   L E F T                             ///

        Texture textureREVWalkingLEFT = new Texture("PerryREVWalkLEFTCycle.png");
        perryREVWalkLEFT = new Animation(new TextureRegion(textureREVWalkingLEFT), walkFrameCount, walkCycleTime, walkIsCycle, "Walking LEFT");

        ///                       R E V E R S E   W A L K   R I G H T                            ///

        Texture textureREVWalkingRight = new Texture("PerryREVWalkRightCycle.png");
        perryREVWalkRight = new Animation(new TextureRegion(textureREVWalkingRight), walkFrameCount, walkCycleTime, walkIsCycle, "Walking LEFT");



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


        quickAttackWidth = (textureQuickAttackRight.getWidth()/ quickAttackFrameCount);
        quickAttackHeight = textureQuickAttackRight.getHeight();


        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////




        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        ///                              S H I E L D / P A R R Y                                 ///
        ////////////////////////////////////////////////////////////////////////////////////////////

        shieldUpFrameCount = 4;
        shieldUpCycleTime = 0.25f;
        boolean shieldUpIsCycle = false;


        ////////////////////////////////////////////////////////////////////////////////////////////

        ///                      I D L E   T O   S H I E L D   U P   R I G H T                   ///

        Texture textureShieldUpRight = new Texture("PerryShieldUpRight.png");
        perryShieldUpRight = new Animation(new TextureRegion(textureShieldUpRight), shieldUpFrameCount, shieldUpCycleTime, shieldUpIsCycle, "Shield Up Right");

        ///                      I D L E   T O   S H I E L D   U P   L E F T                   ///

        Texture textureShieldUpLEFT = new Texture("PerryShieldUpLEFT.png");
        perryShieldUpLEFT = new Animation(new TextureRegion(textureShieldUpLEFT), shieldUpFrameCount, shieldUpCycleTime, shieldUpIsCycle, "Shield Up Left");


        shieldUpWidth = (textureShieldUpRight.getWidth()/ (float)shieldUpFrameCount);
        shieldUpWidth = textureShieldUpRight.getHeight();

        ////////////////////////////////////////////////////////////////////////////////////////////

        shieldIdleFrameCount = 2;
        shieldIdleCycleTime = 0.5f;
        boolean shieldIdleIsCyce = true;

        ///                       I D L E   S H I E L D   U P   R I G H T                        ///

        Texture textureIdleShieldUpRight = new Texture("PerryIdleShieldUpRight.png");
        perryShieldIdleRight = new Animation(new TextureRegion(textureIdleShieldUpRight), shieldIdleFrameCount,shieldIdleCycleTime, shieldIdleIsCyce, "Shield Idle Right");

        ///                       I D L E   S H I E L D   U P   R I G H T                        ///

        Texture textureIdleShieldUpLEFT = new Texture("PerryIdleShieldUpLEFT.png");
        perryShieldIdleLEFT = new Animation(new TextureRegion(textureIdleShieldUpLEFT), shieldIdleFrameCount,shieldIdleCycleTime, shieldIdleIsCyce, "Shield Idle Right");

        ///                      W A L K I N G   S H I E L D   U P   R I G H T                   ///

        Texture textureShieldWalkRight = new Texture("PerryWalkRightShield.png");
        perryWalkRightShield = new Animation(new TextureRegion(textureShieldWalkRight), walkFrameCount, walkCycleTime, walkIsCycle, "Walk Right Shield");

        ///                      W A L K I N G   S H I E L D   U P   L E F T                     ///

        Texture textureShieldWalkLeft = new Texture("PerryWalkLEFTShield.png");
        perryWalkLEFTShield = new Animation(new TextureRegion(textureShieldWalkLeft), walkFrameCount, walkCycleTime, walkIsCycle, "Walk Right Shield");

        ///               R E V E R S E   W A L K I N G   S H I E L D   U P   R I G H T          ///

        Texture textureREVShieldWalkRight = new Texture("PerryWalkRightShieldREV.png");
        perryREVWalkRightShield = new Animation(new TextureRegion(textureREVShieldWalkRight), walkFrameCount, walkCycleTime, walkIsCycle, "Walk Right Shield");

        ///               R E V E R S E   W A L K I N G   S H I E L D   U P   L E F T            ///

        Texture textureREVShieldWalkLeft = new Texture("PerryWalkLEFTShieldREV.png");
        perryREVWalkLEFTShield = new Animation(new TextureRegion(textureREVShieldWalkRight), walkFrameCount, walkCycleTime, walkIsCycle, "Walk Right Shield");

        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        ///                        S H I E L D    A N I M A T I O N S                            ///

        shieldONLYUpFrameCount = 8;
        shieldONLYUpCycleTime = 0.2f;
        boolean shieldONLYUpIsCycle = false;

        ///                 S H I E L D   U P   R I G H T   F O R E G R O U N D                  ///

        Texture textureShieldUpFR = new Texture("ShieldONLYUpRight.png");
        shieldUpForegroundRight = new Animation(new TextureRegion(textureShieldUpFR), shieldONLYUpFrameCount, shieldONLYUpCycleTime, shieldONLYUpIsCycle, "Shield Up Fore Right");

        ///                  S H I E L D   U P   L E F T   F O R E G R O U N D                   ///

        Texture textureShieldUpFL = new Texture("ShieldONLYUpLEFT.png");
        shieldUpForegroundLEFT = new Animation(new TextureRegion(textureShieldUpFL), shieldONLYUpFrameCount, shieldONLYUpCycleTime, shieldONLYUpIsCycle, "Shield Up Fore Left");

        ///                 S H I E L D   U P   R I G H T   B A C K G R O U N D                  ///

        Texture texShieldLR = new Texture("ShieldUpRightBEHIND.png");
        shieldUpBackgroundRight = new Animation(new TextureRegion(texShieldLR), shieldONLYUpFrameCount, shieldONLYUpCycleTime, shieldONLYUpIsCycle, "Shield Up Back Right");

        ///                  S H I E L D   U P   L E F T   B A C K G R O U N D                   ///

        Texture texShieldLL = new Texture("ShieldUpLEFTBEHIND.png");
        shieldUpBackgroundLEFT = new Animation(new TextureRegion(texShieldLL), shieldONLYUpFrameCount, shieldONLYUpCycleTime, shieldONLYUpIsCycle, "Shield Up Back Left");

        ///              S H I E L D   L O O P   R I G H T   F O R E G R O U N D                 ///

        shieldLoopFrameCount = 9;
        shieldLoopCycleTime = 0.9f;
        boolean shieldLoopIsCycle = true;

        Texture texShieldLoopRF = new Texture("PerryShieldLoopRight.png");
        shieldLoopForegroundRight = new Animation(new TextureRegion(texShieldLoopRF), shieldLoopFrameCount, shieldLoopCycleTime, shieldLoopIsCycle, "Shield Loop Fore Right");

        ///               S H I E L D   L O O P   L E F T   F O R E G R O U N D                  ///

        Texture texShieldLoopLF = new Texture("PerryShieldLoopLEFT.png");
        shieldLoopForegroundLEFT = new Animation(new TextureRegion(texShieldLoopLF), shieldLoopFrameCount, shieldLoopCycleTime, shieldLoopIsCycle, "Shield Loop Fore Left");

        ///              S H I E L D   L O O P   R I G H T   B A C K G R O U N D                 ///

        Texture texShieldLoopRB = new Texture("ShieldLoopRightBEHIND.png");
        shieldLoopBackgroundRight = new Animation(new TextureRegion(texShieldLoopRB), shieldLoopFrameCount, shieldLoopCycleTime, shieldLoopIsCycle, "Shield Loop Back Right");

        ///               S H I E L D   L O O P   L E F T   B A C K G R O U N D                  ///

        Texture texShieldLoopLB = new Texture("ShieldLoopLEFTBEHIND.png");
        shieldLoopBackgroundLEFT = new Animation(new TextureRegion(texShieldLoopLB), shieldLoopFrameCount, shieldLoopCycleTime, shieldLoopIsCycle, "Shield Loop Left Back");

        ///               S H I E L D   D O W N   R I G H T   F O R E G R O U N D                  ///

        shieldDownFrameCount = 6;
        shieldDownCycleTime = 0.15f;
        boolean shieldDownIsCycle = false;

        Texture texShieldDownRF = new Texture("ShieldONLYDownRight.png");
        shieldDownForegroundRight = new Animation(new TextureRegion(texShieldDownRF), shieldDownFrameCount, shieldDownCycleTime, shieldDownIsCycle, "Shield Down Fore Right");

        ///               S H I E L D   D O W N   L E F T   F O R E G R O U N D                  ///

        Texture texShieldDownLF = new Texture("ShieldONLYDownLEFT.png");
        shieldDownForegroundLEFT = new Animation(new TextureRegion(texShieldDownLF), shieldDownFrameCount, shieldDownCycleTime, shieldDownIsCycle, "Shield Down Fore Left");

        ///              S H I E L D   D O W N   R I G H T   B A C K G R O U N D                 ///

        Texture texShieldDownRB = new Texture("ShieldDownRightBEHIND.png");
        shieldDownBackgroundRight = new Animation(new TextureRegion(texShieldDownRB), shieldDownFrameCount, shieldDownCycleTime, shieldDownIsCycle, "Shield Down Back Right");

        ///               S H I E L D   D O W N   L E F T   B A C K G R O U N D                  ///

        Texture texShieldDownLB = new Texture("ShieldDownLEFTBEHIND.png");
        shieldDownBackgroundLEFT = new Animation(new TextureRegion(texShieldDownLB), shieldDownFrameCount, shieldDownCycleTime, shieldDownIsCycle, "Shield Down Back Left");


        frameVertices shieldFrameVerts0 = new frameVertices(0);
        shieldFrameVerts0.addVertices(173, 0);
        shieldFrameVerts0.addVertices(194, 23);
        shieldFrameVerts0.addVertices(203, 110);
        shieldFrameVerts0.addVertices(192, 178);
        shieldFrameVerts0.addVertices(134, 264);
        shieldFrameVerts0.addVertices(51, 276);
        shieldFrameVerts0.addVertices(46, 263);
        shieldFrameVerts0.addVertices(125, 243);
        shieldFrameVerts0.addVertices(171, 167);
        shieldFrameVerts0.addVertices(181, 100);
        shieldFrameVerts0.addVertices(174, 22);
        shieldFrameVerts0.addVertices(156, 0);
        shieldFrameVerts0.setBoundsInfo(new atkBounds(46, 0, 158, 279));
        shieldLoopVertices.addFrameVertices(shieldFrameVerts0);

        frameVertices shieldFrameVerts1 = new frameVertices(1);
        shieldFrameVerts1.addVertices(173, 0);
        shieldFrameVerts1.addVertices(194, 23);
        shieldFrameVerts1.addVertices(203, 110);
        shieldFrameVerts1.addVertices(192, 178);
        shieldFrameVerts1.addVertices(134, 264);
        shieldFrameVerts1.addVertices(51, 276);
        shieldFrameVerts1.addVertices(46, 263);
        shieldFrameVerts1.addVertices(125, 243);
        shieldFrameVerts1.addVertices(171, 167);
        shieldFrameVerts1.addVertices(181, 100);
        shieldFrameVerts1.addVertices(174, 22);
        shieldFrameVerts1.addVertices(156, 0);
        shieldFrameVerts1.setBoundsInfo(new atkBounds(46, 0, 158, 279));
        shieldLoopVertices.addFrameVertices(shieldFrameVerts1);

        frameVertices shieldFrameVerts2 = new frameVertices(2);
        shieldFrameVerts2.addVertices(173, 0);
        shieldFrameVerts2.addVertices(194, 23);
        shieldFrameVerts2.addVertices(203, 110);
        shieldFrameVerts2.addVertices(192, 178);
        shieldFrameVerts2.addVertices(134, 264);
        shieldFrameVerts2.addVertices(51, 276);
        shieldFrameVerts2.addVertices(46, 263);
        shieldFrameVerts2.addVertices(125, 243);
        shieldFrameVerts2.addVertices(171, 167);
        shieldFrameVerts2.addVertices(181, 100);
        shieldFrameVerts2.addVertices(174, 22);
        shieldFrameVerts2.addVertices(156, 0);
        shieldFrameVerts2.setBoundsInfo(new atkBounds(46, 0, 158, 279));
        shieldLoopVertices.addFrameVertices(shieldFrameVerts2);

        frameVertices shieldFrameVerts3 = new frameVertices(3);
        shieldFrameVerts3.addVertices(173, 0);
        shieldFrameVerts3.addVertices(194, 23);
        shieldFrameVerts3.addVertices(203, 110);
        shieldFrameVerts3.addVertices(192, 178);
        shieldFrameVerts3.addVertices(134, 264);
        shieldFrameVerts3.addVertices(51, 276);
        shieldFrameVerts3.addVertices(46, 263);
        shieldFrameVerts3.addVertices(125, 243);
        shieldFrameVerts3.addVertices(171, 167);
        shieldFrameVerts3.addVertices(181, 100);
        shieldFrameVerts3.addVertices(174, 22);
        shieldFrameVerts3.addVertices(156, 0);
        shieldFrameVerts3.setBoundsInfo(new atkBounds(46, 0, 158, 279));
        shieldLoopVertices.addFrameVertices(shieldFrameVerts3);

        frameVertices shieldFrameVerts4 = new frameVertices(4);
        shieldFrameVerts4.addVertices(173, 0);
        shieldFrameVerts4.addVertices(194, 23);
        shieldFrameVerts4.addVertices(203, 110);
        shieldFrameVerts4.addVertices(192, 178);
        shieldFrameVerts4.addVertices(134, 264);
        shieldFrameVerts4.addVertices(51, 276);
        shieldFrameVerts4.addVertices(46, 263);
        shieldFrameVerts4.addVertices(125, 243);
        shieldFrameVerts4.addVertices(171, 167);
        shieldFrameVerts4.addVertices(181, 100);
        shieldFrameVerts4.addVertices(174, 22);
        shieldFrameVerts4.addVertices(156, 0);
        shieldFrameVerts4.setBoundsInfo(new atkBounds(46, 0, 158, 279));
        shieldLoopVertices.addFrameVertices(shieldFrameVerts4);

        frameVertices shieldFrameVerts5 = new frameVertices(5);
        shieldFrameVerts5.addVertices(173, 0);
        shieldFrameVerts5.addVertices(194, 23);
        shieldFrameVerts5.addVertices(203, 110);
        shieldFrameVerts5.addVertices(192, 178);
        shieldFrameVerts5.addVertices(134, 264);
        shieldFrameVerts5.addVertices(51, 276);
        shieldFrameVerts5.addVertices(46, 263);
        shieldFrameVerts5.addVertices(125, 243);
        shieldFrameVerts5.addVertices(171, 167);
        shieldFrameVerts5.addVertices(181, 100);
        shieldFrameVerts5.addVertices(174, 22);
        shieldFrameVerts5.addVertices(156, 0);
        shieldFrameVerts5.setBoundsInfo(new atkBounds(46, 0, 158, 279));
        shieldLoopVertices.addFrameVertices(shieldFrameVerts5);

        frameVertices shieldFrameVerts6 = new frameVertices(6);
        shieldFrameVerts6.addVertices(173, 0);
        shieldFrameVerts6.addVertices(194, 23);
        shieldFrameVerts6.addVertices(203, 110);
        shieldFrameVerts6.addVertices(192, 178);
        shieldFrameVerts6.addVertices(134, 264);
        shieldFrameVerts6.addVertices(51, 276);
        shieldFrameVerts6.addVertices(46, 263);
        shieldFrameVerts6.addVertices(125, 243);
        shieldFrameVerts6.addVertices(171, 167);
        shieldFrameVerts6.addVertices(181, 100);
        shieldFrameVerts6.addVertices(174, 22);
        shieldFrameVerts6.addVertices(156, 0);
        shieldFrameVerts6.setBoundsInfo(new atkBounds(46, 0, 158, 279));
        shieldLoopVertices.addFrameVertices(shieldFrameVerts6);

        frameVertices shieldFrameVerts7 = new frameVertices(7);
        shieldFrameVerts7.addVertices(173, 0);
        shieldFrameVerts7.addVertices(194, 23);
        shieldFrameVerts7.addVertices(203, 110);
        shieldFrameVerts7.addVertices(192, 178);
        shieldFrameVerts7.addVertices(134, 264);
        shieldFrameVerts7.addVertices(51, 276);
        shieldFrameVerts7.addVertices(46, 263);
        shieldFrameVerts7.addVertices(125, 243);
        shieldFrameVerts7.addVertices(171, 167);
        shieldFrameVerts7.addVertices(181, 100);
        shieldFrameVerts7.addVertices(174, 22);
        shieldFrameVerts7.addVertices(156, 0);
        shieldFrameVerts7.setBoundsInfo(new atkBounds(46, 0, 158, 279));
        shieldLoopVertices.addFrameVertices(shieldFrameVerts7);

        frameVertices shieldFrameVerts8 = new frameVertices(8);
        shieldFrameVerts8.addVertices(173, 0);
        shieldFrameVerts8.addVertices(194, 23);
        shieldFrameVerts8.addVertices(203, 110);
        shieldFrameVerts8.addVertices(192, 178);
        shieldFrameVerts8.addVertices(134, 264);
        shieldFrameVerts8.addVertices(51, 276);
        shieldFrameVerts8.addVertices(46, 263);
        shieldFrameVerts8.addVertices(125, 243);
        shieldFrameVerts8.addVertices(171, 167);
        shieldFrameVerts8.addVertices(181, 100);
        shieldFrameVerts8.addVertices(174, 22);
        shieldFrameVerts8.addVertices(156, 0);
        shieldFrameVerts8.setBoundsInfo(new atkBounds(46, 0, 158, 279));
        shieldLoopVertices.addFrameVertices(shieldFrameVerts8);

        Texture textureShieldLoopR = new Texture("PerryShieldLoopRight.png");

        shieldLoopWidth = (textureShieldLoopR.getWidth()/ shieldLoopFrameCount);
        shieldLoopWidth = textureShieldLoopR.getHeight();
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
            case START_WALK_LEFT:
                this.animationWidth = startToWalkWidth;
                this.animationHeight = startToWalkHeight;
                return perryStartWalkLEFT;
            case START_WALK_RIGHT:
                this.animationWidth = startToWalkWidth;
                this.animationHeight = startToWalkHeight;
                return perryStartWalkRight;
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
            case WALKING_LEFT:
                this.animationWidth = walkWidth;
                this.animationHeight = walkHeight;
                return perryWalkLEFT;
            case REVERSE_WALKING_LEFT:
                this.animationWidth = walkWidth;
                this.animationHeight = walkHeight;
                return perryREVWalkLEFT;
            case WALKING_LEFT_SHIELD:
                this.animationWidth = walkWidth;
                this.animationHeight = walkHeight;
                return perryWalkLEFTShield;
            case REVERSE_WALKING_LEFT_SHIELD:
                this.animationWidth = walkWidth;
                this.animationHeight = walkHeight;
                return perryREVWalkLEFTShield;
            case WALKING_RIGHT:
                this.animationWidth = walkWidth;
                this.animationHeight = walkHeight;
                return perryWalkRight;
            case REVERSE_WALKING_RIGHT:
                this.animationWidth = walkWidth;
                this.animationHeight = walkHeight;
                return perryREVWalkRight;
            case WALKING_RIGHT_SHIELD:
                this.animationWidth = walkWidth;
                this.animationHeight = walkHeight;
                return perryWalkRightShield;
            case REVERSE_WALKING_RIGHT_SHIELD:
                this.animationWidth = walkWidth;
                this.animationHeight = walkHeight;
                return perryREVWalkRightShield;
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
            case START_SHIELD_RIGHT:
                System.out.println("Returning SHIELD_UP_RIGHT perry animation");
                this.animationWidth = shieldUpWidth;
                this.animationHeight = shieldUpHeight;
                return perryShieldUpRight;
            case START_SHIELD_LEFT:
                System.out.println("Returning SHIELD_UP_LEFT perry animation");
                this.animationWidth = shieldUpWidth;
                this.animationHeight = shieldUpHeight;
                return perryShieldUpLEFT;
            case IDLE_SHIELD_RIGHT:
                System.out.println("Returning IDLE_SHIELD_RIGHT perry animation");
                this.animationWidth = shieldUpWidth;
                this.animationHeight = shieldUpHeight;
                return perryShieldIdleRight;
            case IDLE_SHIELD_LEFT:
                System.out.println("Returning IDLE_SHIELD_LEFT perry animation");
                this.animationWidth = shieldUpWidth;
                this.animationHeight = shieldUpHeight;
                return perryShieldIdleLEFT;
            default:
                System.out.println("Returning default (IDLE) animation");
                this.animationWidth = idleWidth;
                this.animationHeight = idleHeight;
                return perryIdle;        }
    }

    public Animation[] getShieldAnimations(Perry.ShieldState shieldState){

        Animation[] shieldAnimations = new Animation[2];

        switch (shieldState) {
            case SHIELD_UP_RIGHT:
//                System.out.println("SHIELD: Returning shield up right animation");
                shieldAnimations[0] = shieldUpForegroundRight;
                shieldAnimations[1] = shieldUpBackgroundRight;
                return shieldAnimations;
            case SHIELD_UP_LEFT:
//                System.out.println("SHIELD: Returning shield up left animation");
                shieldAnimations[0] = shieldUpForegroundLEFT;
                shieldAnimations[1] = shieldUpBackgroundLEFT;
                return shieldAnimations;
            case SHIELD_CYCLE_RIGHT:
//                System.out.println("SHIELD: Returning shield cycle right animation");
                shieldAnimations[0] = shieldLoopForegroundRight;
                shieldAnimations[1] = shieldLoopBackgroundRight;
                return shieldAnimations;
            case SHIELD_CYCLE_LEFT:
//                System.out.println("SHIELD: Returning shield cycle left animation");
                shieldAnimations[0] = shieldLoopForegroundLEFT;
                shieldAnimations[1] = shieldLoopBackgroundLEFT;
                return shieldAnimations;
            case SHIELD_DOWN_RIGHT:
//                System.out.println("SHIELD: Returning shield down right animation");
                shieldAnimations[0] = shieldDownForegroundRight;
                shieldAnimations[1] = shieldDownBackgroundRight;
                return shieldAnimations;
            case SHIELD_DOWN_LEFT:
//                System.out.println("SHIELD: Returning shield down left animation");
                shieldAnimations[0] = shieldDownForegroundLEFT;
                shieldAnimations[1] = shieldDownBackgroundLEFT;
                return shieldAnimations;
            case NO_SHIELD:
                return null;
            default:
                return null;
        }
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
                mappedHitbox[i] = ((position.x + quickAttackWidth) - hbVerts.xCoordinate) ;
                i ++;
                mappedHitbox[i] = (position.y + hbVerts.yCoordinate);
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
            return new Rectangle(position.x + frameWidth - frameVerts.attackBounds.xOffset - frameVerts.attackBounds.width, position.y + frameVerts.attackBounds.yOffset, frameVerts.attackBounds.width, frameVerts.attackBounds.height);
        }

        else{
            return new Rectangle(position.x + frameVerts.attackBounds.xOffset, position.y + frameVerts.attackBounds.yOffset, frameVerts.attackBounds.width, frameVerts.attackBounds.height);

        }
    }


    public attackBoxes getAttackHitbox(Vector3 position,Perry.State state, int currentFrame) {

        switch (state) {
            case QUICK_ATTACK_RIGHT:

                currentFrameVerts = quickAttackVertices.getFrameVerts(currentFrame);
                mappedHitboxVerts = mapHitboxVerts(position, currentFrameVerts, quickAttackWidth, false);

                if (mappedHitboxVerts != null){
                    System.out.println("hitbox not null!");
                return new attackBoxes(mapBoundingBox(position, currentFrameVerts, quickAttackWidth, false), new Polygon(mapHitboxVerts(position, currentFrameVerts, quickAttackWidth, false)));}

                else { return null;}

            case QUICK_ATTACK_LEFT:

                currentFrameVerts = quickAttackVertices.getFrameVerts(currentFrame);
                mappedHitboxVerts = mapHitboxVerts(position, currentFrameVerts, quickAttackWidth, true);

                if (mappedHitboxVerts != null){
                    System.out.println("hitbox not null!");
                    return new attackBoxes(mapBoundingBox(position, currentFrameVerts, quickAttackWidth, true), new Polygon(mapHitboxVerts(position, currentFrameVerts, quickAttackWidth, true)));}

                else { return null;}

            default:
                return null;


        }
    }
}

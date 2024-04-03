package com.jwb.perfectActors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;

public class YellowFellowMGMT extends EnemyAnimationMGMT {

    public float animationWidth;
    public float animationHeight;

    private Animation idleOneLeft;
    private Animation idleOneRight;
    private float idleWidth;
    private float idleHeight;
    private int idleFrameCount;
    private float idleCycleTime;

    private Animation dSwingLeftStart;
    private Animation dSwingRightStart;
    private float dSwingStartWidth;
    private float dSwingStartHeight;
    private int dSwingStartFrameCount;
    private float dSwingStartCycleTime;

    private Animation dSwingLeftOne;
    private Animation dSwingRightOne;
    private float dSwingOneWidth;
    private float dSwingOneHeight;
    private int dSwingOneFrameCount;
    private float dSwingOneCycleTime;

    PerfectEnemy.animationVertices dSwingOneVerts = new PerfectEnemy.animationVertices();

    private Animation dSwingLeftTwo;
    private Animation dSwingRightTwo;
    private float dSwingTwoWidth;
    private float dSwingTwoHeight;
    private int dSwingTwoFrameCount;
    private float dSwingTwoCycleTime;

    PerfectEnemy.animationVertices dSwingTwoVerts = new PerfectEnemy.animationVertices();

    public YellowFellowMGMT(){
        loadAnimations();
    }



    @Override
    void loadAnimations() {

        ////////////////////////////////////////////////////////////////////////////////////////////
        /////////////   Y E L L O W 'S   A N I M A T I O N S     ///////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        ///                                      I D L E                                         ///
        ////////////////////////////////////////////////////////////////////////////////////////////

        idleFrameCount = 6;
        idleCycleTime = 2f;
        boolean idleIsCycle = true;

        Texture textureIdle = new Texture("YellowFellowIDLE.png");
        idleOneLeft = new Animation(new TextureRegion(textureIdle), idleFrameCount, idleCycleTime, idleIsCycle, "YelFel Idle Left");

        idleWidth = textureIdle.getWidth() /((float) idleFrameCount);
        idleHeight = textureIdle.getHeight();



        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        ///                             D O U B L E    S W I N G                                 ///
        ////////////////////////////////////////////////////////////////////////////////////////////

        dSwingStartFrameCount = 12;
        dSwingStartCycleTime = 1f;
        boolean doubSwingIsCycle = false;

        Texture textureDSwingStartLeft = new Texture("YellowFellowRaiseArm.png");
        dSwingLeftStart = new Animation (new TextureRegion(textureDSwingStartLeft), dSwingStartFrameCount, dSwingStartCycleTime, doubSwingIsCycle,"Double Swing Left");

        dSwingStartWidth = textureDSwingStartLeft.getWidth()/((float) dSwingStartFrameCount);
        dSwingStartHeight = textureDSwingStartLeft.getHeight();

        /////SECOND PHASE OF ANIMATION

        dSwingOneFrameCount = 15;
        dSwingOneCycleTime = 0.5f;
        boolean dSwingOneIsCycle = false;

        Texture textureDSwingOneLeft = new Texture("YellowFellowDSONE.png");
        dSwingLeftOne = new Animation (new TextureRegion(textureDSwingOneLeft), dSwingOneFrameCount, dSwingOneCycleTime, dSwingOneIsCycle,"Double Swing Left");


        dSwingOneVerts.addFrameVertices(new PerfectEnemy.frameVertices(0));
        dSwingOneVerts.addFrameVertices(new PerfectEnemy.frameVertices(1));


        PerfectEnemy.frameVertices dSwingOne2 = new PerfectEnemy.frameVertices(2);
        dSwingOne2.addVertices(225, 205);
        dSwingOne2.addVertices(242, 132);
        dSwingOne2.addVertices(378, 172);
        dSwingOne2.addVertices(348, 260);
        dSwingOne2.addVertices(304, 280);
        dSwingOne2.addVertices(231, 234);
        dSwingOne2.setBoundsInfo(new PerfectEnemy.atkBounds(224, 130, 157, 150));
        dSwingOneVerts.addFrameVertices(dSwingOne2);

        PerfectEnemy.frameVertices dSwingOne3 = new PerfectEnemy.frameVertices(3);
        dSwingOne3.addVertices(183 ,172);
        dSwingOne3.addVertices(217 , 105);
        dSwingOne3.addVertices(277 , 127);
        dSwingOne3.addVertices(354 , 172);
        dSwingOne3.addVertices(309 , 240);
        dSwingOne3.addVertices(281 , 256);
        dSwingOne3.addVertices(248 , 256);
        dSwingOne3.addVertices(184 , 194);
        dSwingOne3.setBoundsInfo(new PerfectEnemy.atkBounds(180, 104, 173, 157));
        dSwingOneVerts.addFrameVertices(dSwingOne3);

        PerfectEnemy.frameVertices dSwingOne4 = new PerfectEnemy.frameVertices(4);
        dSwingOne4.addVertices(131 , 157);
        dSwingOne4.addVertices(137 , 130);
        dSwingOne4.addVertices(163 , 107);
        dSwingOne4.addVertices(192 , 69);
        dSwingOne4.addVertices(324 , 167);
        dSwingOne4.addVertices(303 , 194);
        dSwingOne4.addVertices(255 , 227);
        dSwingOne4.addVertices(223 , 239);
        dSwingOne4.addVertices(184 , 227);
        dSwingOne4.setBoundsInfo(new PerfectEnemy.atkBounds( 134, 70, 185, 164));
        dSwingOneVerts.addFrameVertices(dSwingOne4);

        PerfectEnemy.frameVertices dSwingOne5 = new PerfectEnemy.frameVertices(5);
        dSwingOne5.addVertices(98 , 111);
        dSwingOne5.addVertices(110 , 86);
        dSwingOne5.addVertices(144 , 75);
        dSwingOne5.addVertices(171 , 44);
        dSwingOne5.addVertices(219 , 91);
        dSwingOne5.addVertices(278 , 168);
        dSwingOne5.addVertices(253 , 188);
        dSwingOne5.addVertices(222 , 173);
        dSwingOne5.addVertices(192 , 211);
        dSwingOne5.addVertices(133 , 197);
        dSwingOne5.addVertices(129 , 156);
        dSwingOne5.addVertices(109 , 147);
        dSwingOne5.setBoundsInfo(new PerfectEnemy.atkBounds(100, 43, 181, 171));
        dSwingOneVerts.addFrameVertices(dSwingOne5);

        PerfectEnemy.frameVertices dSwingOne6 = new PerfectEnemy.frameVertices(6);
        dSwingOne6.addVertices(56 , 105);
        dSwingOne6.addVertices(69 , 76);
        dSwingOne6.addVertices(103 , 62);
        dSwingOne6.addVertices(134 , 35);
        dSwingOne6.addVertices(180 , 78);
        dSwingOne6.addVertices(237 , 156);
        dSwingOne6.addVertices(213 , 179);
        dSwingOne6.addVertices(183 , 163);
        dSwingOne6.addVertices(150 , 200);
        dSwingOne6.addVertices(94 , 188);
        dSwingOne6.setBoundsInfo(new PerfectEnemy.atkBounds(56, 34, 179, 167));
        dSwingOneVerts.addFrameVertices(dSwingOne6);

        PerfectEnemy.frameVertices dSwingOne7 = new PerfectEnemy.frameVertices(7);
        dSwingOne7.addVertices(36 , 65);
        dSwingOne7.addVertices(53 , 40);
        dSwingOne7.addVertices(93 , 34);
        dSwingOne7.addVertices(126 , 14);
        dSwingOne7.addVertices(190 , 166);
        dSwingOne7.addVertices(171 , 174);
        dSwingOne7.addVertices(145 , 149);
        dSwingOne7.addVertices(106 , 181);
        dSwingOne7.addVertices(51 , 154);
        dSwingOne7.setBoundsInfo(new PerfectEnemy.atkBounds(36, 12, 155, 183));
        dSwingOneVerts.addFrameVertices(dSwingOne7);

        PerfectEnemy.frameVertices dSwingOne8 = new PerfectEnemy.frameVertices(8);
        dSwingOne8.addVertices(43 , 123);
        dSwingOne8.addVertices(46 , 30);
        dSwingOne8.addVertices(69 , 10);
        dSwingOne8.addVertices(149 , 1);
        dSwingOne8.addVertices(183 , 134);
        dSwingOne8.addVertices(175 , 167);
        dSwingOne8.addVertices(85 , 160);
        dSwingOne8.setBoundsInfo(new PerfectEnemy.atkBounds(43, 1, 137, 171));
        dSwingOneVerts.addFrameVertices(dSwingOne8);

        PerfectEnemy.frameVertices dSwingOne9 = new PerfectEnemy.frameVertices(9);
        dSwingOne9.addVertices(18 , 83);
        dSwingOne9.addVertices(7 , 36);
        dSwingOne9.addVertices(28 , 0);
        dSwingOne9.addVertices(155 , 0);
        dSwingOne9.addVertices(177 , 118);
        dSwingOne9.addVertices(130 , 120);
        dSwingOne9.setBoundsInfo(new PerfectEnemy.atkBounds(7, 0, 167, 123));
        dSwingOneVerts.addFrameVertices(dSwingOne9);

        PerfectEnemy.frameVertices dSwingOne10 = new PerfectEnemy.frameVertices(10);
        dSwingOne10.addVertices(56 , 101);
        dSwingOne10.addVertices(37 , 33);
        dSwingOne10.addVertices(64 , 0);
        dSwingOne10.addVertices(84 , 0);
        dSwingOne10.addVertices(218 , 131);
        dSwingOne10.setBoundsInfo(new PerfectEnemy.atkBounds(34, 0, 167, 123));
        dSwingOneVerts.addFrameVertices(dSwingOne10);

        PerfectEnemy.frameVertices dSwingOne11 = new PerfectEnemy.frameVertices(11);
        dSwingOne11.addVertices(82 , 101);
        dSwingOne11.addVertices(58 , 35);
        dSwingOne11.addVertices(90 , 0);
        dSwingOne11.addVertices(199 , 0);
        dSwingOne11.addVertices(225 , 126);
        dSwingOne11.setBoundsInfo(new PerfectEnemy.atkBounds(58, 0, 167, 123));
        dSwingOneVerts.addFrameVertices(dSwingOne11);

        PerfectEnemy.frameVertices dSwingOne12 = new PerfectEnemy.frameVertices(12);
        dSwingOne12.addVertices(106 , 105);
        dSwingOne12.addVertices(82 , 34);
        dSwingOne12.addVertices(108 , 0);
        dSwingOne12.addVertices(229 , 0);
        dSwingOne12.addVertices(248 , 117);
        dSwingOne12.addVertices(217 , 125);
        dSwingOne12.setBoundsInfo(new PerfectEnemy.atkBounds(82, 0, 167, 123));
        dSwingOneVerts.addFrameVertices(dSwingOne12);

        PerfectEnemy.frameVertices dSwingOne13 = new PerfectEnemy.frameVertices(13);
        dSwingOne13.addVertices(146 , 93);
        dSwingOne13.addVertices(125 , 31);
        dSwingOne13.addVertices(146 , 0);
        dSwingOne13.addVertices(253 , 0);
        dSwingOne13.addVertices(268 , 121);
        dSwingOne13.setBoundsInfo(new PerfectEnemy.atkBounds(125, 0, 143, 123));
        dSwingOneVerts.addFrameVertices(dSwingOne13);

        PerfectEnemy.frameVertices doubSwing14 = new PerfectEnemy.frameVertices(14);
        doubSwing14.addVertices(198 , 85);
        doubSwing14.addVertices(180 , 29);
        doubSwing14.addVertices(194 , 0);
        doubSwing14.addVertices(281 , 0);
        doubSwing14.addVertices(295 , 43);
        doubSwing14.addVertices(291 , 110);
        doubSwing14.setBoundsInfo(new PerfectEnemy.atkBounds(180, 0, 107, 100));
        dSwingOneVerts.addFrameVertices(doubSwing14);

        ///THIRD PHASE OF ANIMATION

        dSwingTwoFrameCount = 12;
        dSwingTwoCycleTime = 0.5f;
        boolean dSwingTwoIsCycle = false;

        Texture textureDSwingTwoLeft = new Texture("YellowFellowDSTWO.png");
        dSwingLeftTwo = new Animation (new TextureRegion(textureDSwingTwoLeft), dSwingTwoFrameCount, dSwingTwoCycleTime, dSwingTwoIsCycle,"Double Swing Left");

        PerfectEnemy.frameVertices dSwingTwo0 = new PerfectEnemy.frameVertices(0);
        dSwingTwo0.addVertices(194 , 296);
        dSwingTwo0.addVertices(161 , 270);
        dSwingTwo0.addVertices(130 , 177);
        dSwingTwo0.addVertices(141 , 153);
        dSwingTwo0.addVertices(161 , 139);
        dSwingTwo0.addVertices(176 , 103);
        dSwingTwo0.addVertices(226 , 158);
        dSwingTwo0.addVertices(281 , 259);
        dSwingTwo0.setBoundsInfo(new PerfectEnemy.atkBounds(199, 97, 83, 201));
        dSwingTwoVerts.addFrameVertices(dSwingTwo0);

        PerfectEnemy.frameVertices dSwingTwo1 = new PerfectEnemy.frameVertices(1);
        dSwingTwo1.addVertices(122 , 277);
        dSwingTwo1.addVertices( 86, 250);
        dSwingTwo1.addVertices(80 , 133);
        dSwingTwo1.addVertices(95 , 109);
        dSwingTwo1.addVertices(123 , 106);
        dSwingTwo1.addVertices(148 , 76);
        dSwingTwo1.addVertices(162 , 88);
        dSwingTwo1.addVertices(210 , 255);
        dSwingTwo1.setBoundsInfo(new PerfectEnemy.atkBounds(75, 75, 294, 208));
        dSwingTwoVerts.addFrameVertices(dSwingTwo1);

        PerfectEnemy.frameVertices dSwingTwo2 = new PerfectEnemy.frameVertices(2);
        dSwingTwo2.addVertices(59 , 245);
        dSwingTwo2.addVertices(27 , 208);
        dSwingTwo2.addVertices(33 , 134);
        dSwingTwo2.addVertices(47 , 91);
        dSwingTwo2.addVertices(66 , 72);
        dSwingTwo2.addVertices(98 , 75);
        dSwingTwo2.addVertices(130 , 55);
        dSwingTwo2.addVertices(144 , 72);
        dSwingTwo2.addVertices(156 , 260);
        dSwingTwo2.setBoundsInfo(new PerfectEnemy.atkBounds(23, 55, 137, 208));
        dSwingTwoVerts.addFrameVertices(dSwingTwo2);

        PerfectEnemy.frameVertices dSwingTwo3 = new PerfectEnemy.frameVertices(3);
        dSwingTwo3.addVertices(53 , 213);
        dSwingTwo3.addVertices(17 , 177);
        dSwingTwo3.addVertices(35 , 60);
        dSwingTwo3.addVertices(60 , 42);
        dSwingTwo3.addVertices(90 , 45);
        dSwingTwo3.addVertices(121 , 21);
        dSwingTwo3.addVertices(135 , 39);
        dSwingTwo3.addVertices(146 , 209);
        dSwingTwo3.setBoundsInfo(new PerfectEnemy.atkBounds(14, 20, 137, 197));
        dSwingTwoVerts.addFrameVertices(dSwingTwo3);

        PerfectEnemy.frameVertices dSwingTwo4 = new PerfectEnemy.frameVertices(4);
        dSwingTwo4.addVertices(52 , 193);
        dSwingTwo4.addVertices(16 , 156);
        dSwingTwo4.addVertices(34 , 38);
        dSwingTwo4.addVertices(60 , 16);
        dSwingTwo4.addVertices(124 , 4);
        dSwingTwo4.addVertices(137 , 18);
        dSwingTwo4.addVertices(139 , 154);
        dSwingTwo4.setBoundsInfo(new PerfectEnemy.atkBounds(14, 0, 129, 195));
        dSwingTwoVerts.addFrameVertices(dSwingTwo4);

        PerfectEnemy.frameVertices dSwingTwo5 = new PerfectEnemy.frameVertices(5);
        dSwingTwo5.addVertices(18 , 86);
        dSwingTwo5.addVertices(9 , 45);
        dSwingTwo5.addVertices(38 , 0);
        dSwingTwo5.addVertices(149 , 0);
        dSwingTwo5.addVertices(149 , 107);
        dSwingTwo5.setBoundsInfo(new PerfectEnemy.atkBounds(7, 0, 143, 111));
        dSwingTwoVerts.addFrameVertices(dSwingTwo5);

        PerfectEnemy.frameVertices dSwingTwo6 = new PerfectEnemy.frameVertices(6);
        dSwingTwo6.addVertices(37 , 85);
        dSwingTwo6.addVertices(23 , 48);
        dSwingTwo6.addVertices(39 , 18);
        dSwingTwo6.addVertices(64 , 0);
        dSwingTwo6.addVertices(166 , 0);
        dSwingTwo6.addVertices(166 , 104);
        dSwingTwo6.setBoundsInfo(new PerfectEnemy.atkBounds(22, 0, 147, 110));
        dSwingTwoVerts.addFrameVertices(dSwingTwo6);

        PerfectEnemy.frameVertices dSwingTwo7 = new PerfectEnemy.frameVertices(7);
        dSwingTwo7.addVertices(76 , 85);
        dSwingTwo7.addVertices(61 , 58);
        dSwingTwo7.addVertices(67 , 30);
        dSwingTwo7.addVertices(99 , 0);
        dSwingTwo7.addVertices(204 , 0);
        dSwingTwo7.addVertices(195 , 105);
        dSwingTwo7.setBoundsInfo(new PerfectEnemy.atkBounds(60, 0, 184, 111));
        dSwingTwoVerts.addFrameVertices(dSwingTwo7);

        PerfectEnemy.frameVertices dSwingTwo8 = new PerfectEnemy.frameVertices(36);
        dSwingTwo8.addVertices(134 , 74);
        dSwingTwo8.addVertices(123 , 60);
        dSwingTwo8.addVertices(124 , 28);
        dSwingTwo8.addVertices(162 , 0);
        dSwingTwo8.addVertices(243 , 0);
        dSwingTwo8.addVertices(240 , 92);
        dSwingTwo8.setBoundsInfo(new PerfectEnemy.atkBounds(122, 0, 140, 94));
        dSwingTwoVerts.addFrameVertices(dSwingTwo8);

        PerfectEnemy.frameVertices dSwingTwo9 = new PerfectEnemy.frameVertices(9);
        dSwingTwo9.addVertices(189 , 75);
        dSwingTwo9.addVertices(181 , 44);
        dSwingTwo9.addVertices(199 , 0);
        dSwingTwo9.addVertices(273 , 0);
        dSwingTwo9.addVertices(270 , 94);
        dSwingTwo9.setBoundsInfo(new PerfectEnemy.atkBounds(179, 0, 140, 94));
        dSwingTwoVerts.addFrameVertices(dSwingTwo9);

        dSwingTwoVerts.addFrameVertices(new PerfectEnemy.frameVertices(10));

        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////

    }

    @Override
    Animation getAnimation(EnemyStates state) {

        switch (state) {
            case IDLE_ONE_LEFT:
                System.out.println("Returning Yellow Fellow IDLE ONE LEFT animation");
                this.animationWidth = idleWidth;
                this.animationHeight = idleHeight;
                return idleOneLeft;

            case ATTACK_ONE_LEFT:
                System.out.println("Returning Yellow Fellow DOUBLE SWING LEFT animation");
                this.animationWidth = dSwingStartWidth;
                this.animationHeight = dSwingStartHeight;
                return dSwingLeftStart;

            case ATTACK_TWO_LEFT:
                this.animationWidth = dSwingOneWidth;
                this.animationHeight = dSwingOneHeight;
                return dSwingLeftOne;

            case ATTACK_THREE_LEFT:
                this.animationWidth = dSwingTwoWidth;
                this.animationHeight = dSwingTwoHeight;
                return dSwingLeftTwo;

            default:
                System.out.println("returning default (IDLE ONE LEFT) animation");
                this.animationWidth = idleWidth;
                this.animationHeight = idleHeight;
                return idleOneLeft;
        }
    }


    @Override
    float getAnimationWidth() {
        return this.idleWidth;
    }

    @Override
    float getAnimationHeight() {
        return this.idleHeight;
    }



    @Override
    PerfectEnemy.attackBoxes getAttackHitbox(Vector3 position, EnemyStates state, int currentFrame) {

        switch (state) {

            case ATTACK_TWO_LEFT:

                currentFrameVerts = dSwingOneVerts.getFrameVerts(currentFrame);
                mappedHitboxVerts = mapHitboxVerts(position, currentFrameVerts, dSwingStartWidth, false);

                if (mappedHitboxVerts != null){
                    return new PerfectEnemy.attackBoxes(mapBoundingBox(position, currentFrameVerts, dSwingStartWidth, false), new Polygon(mapHitboxVerts(position, currentFrameVerts, dSwingStartWidth, false)));
                }

                else {return null;}

            case ATTACK_THREE_LEFT:

                currentFrameVerts = dSwingTwoVerts.getFrameVerts(currentFrame);
                mappedHitboxVerts = mapHitboxVerts(position, currentFrameVerts, dSwingStartWidth, false);

                if (mappedHitboxVerts != null){
                    return new PerfectEnemy.attackBoxes(mapBoundingBox(position, currentFrameVerts, dSwingStartWidth, false), new Polygon(mapHitboxVerts(position, currentFrameVerts, dSwingStartWidth, false)));
                }

                else {return null;}

            default:
                return null;

        }

    }

}

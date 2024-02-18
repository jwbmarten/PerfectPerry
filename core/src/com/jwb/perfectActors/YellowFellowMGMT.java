package com.jwb.perfectActors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class YellowFellowMGMT extends EnemyAnimationMGMT {

    public float animationWidth;
    public float animationHeight;

    private Animation idleOneLeft;


    private float idleWidth;
    private float idleHeight;


    private int idleFrameCount;
    private float idleCycleTime;




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
        idleOneLeft = new Animation(new TextureRegion(textureIdle), idleFrameCount, idleCycleTime, idleIsCycle);


        idleWidth = textureIdle.getWidth() /((float) idleFrameCount);
        idleHeight = textureIdle.getHeight();


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
}

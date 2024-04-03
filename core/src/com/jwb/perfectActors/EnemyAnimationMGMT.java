package com.jwb.perfectActors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public abstract class EnemyAnimationMGMT {


    float animationWidth;
    float animationHeight;

    PerfectEnemy.frameVertices currentFrameVerts;
    float[] mappedHitboxVerts;


    abstract void loadAnimations();

    abstract Animation getAnimation(EnemyStates state);

    abstract float getAnimationWidth();

    abstract float getAnimationHeight();

    abstract PerfectEnemy.attackBoxes getAttackHitbox(Vector3 positon, EnemyStates state, int currentFrame);

    float[] mapHitboxVerts(Vector3 position, PerfectEnemy.frameVertices frameVerts, float frameWidth, boolean reverse) {

        if (frameVerts.getFrameVertSize() == 0) {
            return null;
        }

        float[] mappedHitbox = new float[frameVerts.getFrameVertSize() * 2];
        int i = 0;

        if (reverse) {

            for (PerfectEnemy.hitboxVertices hbVerts : frameVerts.hitboxVerticesList) {
                mappedHitbox[i] = ((position.x + frameWidth) - hbVerts.xCoordinate);
                i++;
                mappedHitbox[i] = (position.y + hbVerts.yCoordinate);
                i++;
            }
        } else {
            for (PerfectEnemy.hitboxVertices hbVerts : frameVerts.hitboxVerticesList) {
                mappedHitbox[i] = ((position.x + frameWidth) - hbVerts.xCoordinate);
                i++;
                mappedHitbox[i] = (position.y + hbVerts.yCoordinate);
                i++;
            }
        }
        return mappedHitbox;
    }

    public Rectangle mapBoundingBox(Vector3 position, PerfectEnemy.frameVertices frameVerts, float frameWidth, boolean reverse) {

        if (reverse){
            return new Rectangle(position.x + frameWidth - frameVerts.attackBounds.xOffset, position.y + frameVerts.attackBounds.yOffset, frameVerts.attackBounds.width, frameVerts.attackBounds.height);
        }

        else{
            return new Rectangle(position.x + frameVerts.attackBounds.xOffset, position.y + frameVerts.attackBounds.yOffset, frameVerts.attackBounds.width, frameVerts.attackBounds.height);

        }
    }
}
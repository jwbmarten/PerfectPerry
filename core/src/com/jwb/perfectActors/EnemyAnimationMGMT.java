package com.jwb.perfectActors;

public abstract class EnemyAnimationMGMT {



    float animationWidth;
    float animationHeight;

    public EnemyAnimationMGMT(){
        loadAnimations();
    }

    abstract void loadAnimations();

    abstract Animation getAnimation(EnemyStates state);

    abstract float getAnimationWidth();

    abstract float getAnimationHeight();





}

package com.jwb.gameStates;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {

    //set up camera
    protected OrthographicCamera cam;
    protected Vector3 mouse;
    /**
     * Manages the states of the games so we can have a play state,
     * and a pause/menu state that can be placed "over" the play state
     */
    protected GameStateManager gsm;

    /**
     * Constructor for the abstract class State
     */
    protected State(GameStateManager gsm){

        //
        this.gsm = gsm;

        //
        cam = new OrthographicCamera();
        mouse = new Vector3();

    }

    protected abstract void handleInput();

    /**
     * the update method takes a float delta time (dt) and handles updating
     */
    public abstract void update(float dt);

    /**
     * the render method takes a batch of sprites to be rendered by the game
     */
    public abstract void render(SpriteBatch sb);
    /**
     * used to dispose of the textures/media to prevent memory issues
     */
    public abstract void dispose();
}

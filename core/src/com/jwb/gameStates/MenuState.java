package com.jwb.gameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuState extends State{

    //initialize texture objects for background and play button
    private Texture background;
    private Texture playBtn;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        //set background using path (in assets folder)
        background = new Texture("bgPSU.png");
        //set play button using path (in assets folder)
        playBtn = new Texture("playBtn.png");

    }

    @Override
    public void handleInput() {

        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    /**
     * the dispose method disposes of the background and play button objects,
     * gets called when the menu state is popped from the stack so that it can be
     * pushed again without memory issues
     */
    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();

        System.out.println("Menu State Disposed");
    }

    /**
     * you should only have one sprite batch for your game, they are heavy files
     * you can think of sprite batches like a container that opens and closes.
     * first you have to open the box up, put everything you want into it
     * and then it uses everything in the box to render stuff
     */
    @Override
    public void render(SpriteBatch sb) {

//        //this will allow us to start drawing
//        sb.begin();
//        //here we are drawing the background
//        sb.draw(background, 0,0, FlappyDemo.WIDTH, FlappyDemo.HEIGHT);
//        //here we are drawing the play button, first input is the image, second input is the horizontal position, third input is the height
//        sb.draw(playBtn, (FlappyDemo.WIDTH / 2) - (playBtn.getWidth() / 2), FlappyDemo.HEIGHT / 2);
//
//        //stop drawing
//        sb.end();

    }
}

package com.jwb.gameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jwb.perfect.PerryInputProcessor;
import com.jwb.perfectActors.Perry;
import com.jwb.perfectWorld.GameMap;
import com.jwb.perfectWorld.TiledGameMap;

import java.util.ArrayList;

public class PlayState extends State {

    private OrthographicCamera cam;

    private PerryInputProcessor inputProcessor;
    //define Perry object
    private Perry perry;

    private Texture demolvl;

    private Vector2 groundPos1, groundPos2;

    TiledGameMap gameMap;



    public PlayState(GameStateManager gsm) {


        //the super constructor creates a new stack object?
        super(gsm);

        demolvl = new Texture("DemoShopV0.png");

        gameMap = new TiledGameMap();

        //initialize Perry
        perry = new Perry(600, 300, gameMap);

        cam = new OrthographicCamera();
        cam.setToOrtho(false, 970,546 );


        inputProcessor = new PerryInputProcessor(perry);
        Gdx.input.setInputProcessor(inputProcessor);



    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isTouched()) {
            // Convert screen coordinates to world coordinates
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos); // Adjusts touchPos to world coordinates

            // Now, you can use touchPos.x and touchPos.y to check the tile
            boolean collidable = gameMap.isTileCollidable(touchPos.x, touchPos.y);
            System.out.println("Tile collidable: " + collidable);
        }
    }

    @Override
    public void update(float dt) {


        handleInput();

        perry.update(dt);

        //make camera follow our bird, the +80 just offsets the camera a bit in front of the bird
        cam.position.x = Math.round(perry.getPosition().x + 86);


        //anytime we change where our camera is, we have to update it
        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {

        gameMap.render(cam);



        //setting the projection matrix I think because we resized the camera view
        sb.setProjectionMatrix(cam.combined);

        sb.begin();

        //drap map


        //draw Perry
        sb.draw(perry.getTexture(), Math.round(perry.getPosition().x), Math.round(perry.getPosition().y));

        sb.end();

        float deltaTime = Gdx.graphics.getDeltaTime();


    }

    @Override
    public void dispose() {

        perry.dispose();
        System.out.println("Play State Disposed");

    }

}

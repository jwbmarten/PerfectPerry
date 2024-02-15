package com.jwb.gameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jwb.perfect.PerryInputProcessor;
import com.jwb.perfectActors.Perry;
import com.jwb.perfectWorld.GameMap;
import com.jwb.perfectWorld.TiledGameMap;

import java.util.ArrayList;

public class PlayState extends State {

    private OrthographicCamera cam;
    private ExtendViewport viewport;

    private PerryInputProcessor inputProcessor;
    //define Perry object
    private Perry perry;


    private Texture demolvl;

    private Vector2 groundPos1, groundPos2;

    TiledGameMap gameMap;

    private Vector3 targetPosition = new Vector3();

    private float lerpFactor = 0.1f; // Adjust this value to control the speed of the camera interpolation




    public PlayState(GameStateManager gsm) {


        //the super constructor creates a new stack object?
        super(gsm);

        demolvl = new Texture("DemoShopV0.png");

        gameMap = new TiledGameMap();

        //initialize Perry
        perry = new Perry(1000, 800, gameMap);

        cam = new OrthographicCamera();
        //cam.setToOrtho(false, 970,546 );

        ///////////////////////////////////////////////////////////
        //// SET ZOOM LEVEL WITHIN WORLD


        // 16:9 aspect ratio
        viewport = new ExtendViewport(1200, 675, cam); // Initialize ExtendViewport with desired world width and height

        // 16:9 aspect ratio
//        viewport = new ExtendViewport(2400, 1350, cam); // Initialize ExtendViewport with desired world width and height


        // 21:9 aspect ratio
//        viewport = new ExtendViewport(2800, 1200, cam); // Initialize ExtendViewport with desired world width and height

        inputProcessor = new PerryInputProcessor(perry);
        Gdx.input.setInputProcessor(inputProcessor);



    }

    public boolean checkPerryDirection(String direction){

        if (direction.toLowerCase() == "left"){
            return inputProcessor.isLeftPressed();
        } else if (direction.toLowerCase() == "right") {
            return inputProcessor.isRightPressed();
        }

        return false;
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

        // Get Perry's position as a Vector3
        Vector3 perryPosition = new Vector3(perry.getPosition().x, perry.getPosition().y, 0);

        // Set the target position for the camera, offset if desired
        targetPosition.set(perryPosition.x + 96, perryPosition.y + 320, 0);

        // Interpolate the camera's position towards the target position
        cam.position.lerp(targetPosition, lerpFactor);

        cam.position.set( (int) cam.position.x, (int) cam.position.y, 0 );

        // Ensure the camera updates its matrixes
        cam.update();

        perry.update(dt, inputProcessor.isLeftPressed(), inputProcessor.isRightPressed());
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (inputProcessor.isGetCameraPosition()){

            System.out.println("Camera position x: " + cam.position.x + "Camera position y: " + cam.position.y);

            System.out.println("Graphics width: " + Gdx.graphics.getWidth() + "Graphics height: " + Gdx.graphics.getHeight());

            inputProcessor.setGetCameraPosition(false);
        }
    }


    @Override
    public void render(SpriteBatch sb) {

        gameMap.render(cam);

        //setting the projection matrix I think because we resized the camera view
        sb.setProjectionMatrix(cam.combined);

        sb.begin();

        //draw Perry
        sb.draw(perry.getTexture(), perry.getPosition().x, perry.getPosition().y);

        sb.end();

    }

    @Override
    public void dispose() {

        perry.dispose();
        System.out.println("Play State Disposed");

    }

    public void resize(int width, int height) {
        viewport.update(width, height); // Ensure viewport updates on resize
    }

}

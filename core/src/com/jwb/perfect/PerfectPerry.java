package com.jwb.perfect;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.jwb.gameStates.GameStateManager;
import com.jwb.gameStates.PlayState;


public class PerfectPerry extends ApplicationAdapter {

	private SpriteBatch batch;

	private GameStateManager gsm;





	@Override
	public void create () {

		batch = new SpriteBatch();
		gsm = new GameStateManager();
		gsm.push(new PlayState(gsm));


	}

	@Override
	public void render () {

		gsm.update(Gdx.graphics.getDeltaTime());
		ScreenUtils.clear(0, 0, 0.2f, 1);
		gsm.render(batch);


	}

	@Override
	public void dispose() {

		batch.dispose();
	}

}

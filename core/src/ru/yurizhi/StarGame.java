package ru.yurizhi;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StarGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture background;
	private TextureRegion region;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bat.jpg");
		img = new Texture("justice_league.png");
		region = new TextureRegion(img, 500, 0, 150, 140);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(region, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}

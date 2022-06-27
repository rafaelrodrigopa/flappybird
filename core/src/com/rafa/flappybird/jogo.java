package com.rafa.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class jogo extends ApplicationAdapter {

//	private int contado = 0;

	private SpriteBatch batch;
	private Texture passaro;

	@Override
	public void create () {
//		Gdx.app.log("create","jogo iniciado");
		batch = new SpriteBatch();
		passaro = new Texture("passaro1.png");
	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(passaro,300,0);
		batch.end();

//		contado++;
//		Gdx.app.log("render","jogo renderizado: " + contado);
	}
	
	@Override
	public void dispose () {

	}
}

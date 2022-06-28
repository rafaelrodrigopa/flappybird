package com.rafa.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class jogo extends ApplicationAdapter {

	private int movimentoX = 0;
	private int movimentoY = 0;

	private SpriteBatch batch;
	private Texture[] passaros;
	private Texture fundo;

	//Atributos de configurações
	private float larguraDispositivo;
	private float alturaDispositivo;
	private float variacao = 0;
	private float gravidade = 0;
	private float posicaoInicialVerticalPassaro = 0;

	@Override
	public void create () {
//		Gdx.app.log("create","jogo iniciado");
		batch = new SpriteBatch();
		passaros = new Texture[3];
		passaros[0] = new Texture("passaro1.png");
		passaros[1] = new Texture("passaro2.png");
		passaros[2] = new Texture("passaro3.png");
		fundo = new Texture("fundo.png");

		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();
		posicaoInicialVerticalPassaro = (alturaDispositivo/2);
	}

	@Override
	public void render () {

		batch.begin();

		// São trẽs variações de imagens para animar o passaro
		if (variacao > 2)
			variacao = 0;

		/* Aplica evento de toque na tela*/
		boolean toqueTela = Gdx.input.justTouched();
		if (toqueTela){
			gravidade = -20;
		}

		/* Aplica gravidade no pássaro */
		if (posicaoInicialVerticalPassaro > 0 || toqueTela)
			posicaoInicialVerticalPassaro = posicaoInicialVerticalPassaro - gravidade;

		batch.draw(fundo,0,0,larguraDispositivo,alturaDispositivo);
		batch.draw(passaros[ (int) variacao], 30,posicaoInicialVerticalPassaro);

		// Depois de alguns renders a imagens é trocada para fazer a animação
		variacao += Gdx.graphics.getDeltaTime() * 10;

		gravidade++;
		movimentoX++;
		movimentoY++;
		batch.end();

//		contado++;
//		Gdx.app.log("render","jogo renderizado: " + contado);
	}
	
	@Override
	public void dispose () {

	}
}

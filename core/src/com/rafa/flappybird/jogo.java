package com.rafa.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class jogo extends ApplicationAdapter {

	private SpriteBatch batch;
	private Texture[] passaros;
	private Texture fundo;
	private Texture canoAbaixo;
	private Texture canoAcima;

	//Atributos de configurações
	private float larguraDispositivo;
	private float alturaDispositivo;
	private float variacao = 0;
	private float gravidade = 0;
	private float posicaoInicialVerticalPassaro = 0;
	private float posicaoCanoHorizontal = 0;
	private float posicaoCanoVertical = 0;
	private float espacoEntreCanos;
	private Random random;

	@Override
	public void create () {
		inicializarTexturas();
		inicializarObjetos();
	}

	@Override
	public void render () {
		verificarEstadoJogo();
		desenharTexturas();
	}

	private void verificarEstadoJogo(){

		//Movimentar o cano
		posicaoCanoHorizontal -= Gdx.graphics.getDeltaTime() * 200;

		if (posicaoCanoHorizontal < -canoAcima.getWidth()){
			posicaoCanoHorizontal = larguraDispositivo;
			posicaoCanoVertical = random.nextInt(800) -400;
		}

		/* Aplica evento de toque na tela*/
		boolean toqueTela = Gdx.input.justTouched();
		if (toqueTela){
			gravidade = -25;
		}

		/* Aplica gravidade no pássaro */
		if (posicaoInicialVerticalPassaro > 0 || toqueTela)
			posicaoInicialVerticalPassaro = posicaoInicialVerticalPassaro - gravidade;

		// Depois de alguns renders a imagens é trocada para fazer a animação
		variacao += Gdx.graphics.getDeltaTime() * 10;

		// São trẽs variações de imagens para animar o passaro
		if (variacao > 3)
			variacao = 0;

		gravidade++;
	}

	private void desenharTexturas(){
		batch.begin();

		batch.draw(fundo,0,0,larguraDispositivo,alturaDispositivo);
		batch.draw(passaros[ (int) variacao], 30,posicaoInicialVerticalPassaro);
		batch.draw(canoAbaixo,posicaoCanoHorizontal,alturaDispositivo/2-canoAbaixo.getHeight() - espacoEntreCanos/2 - posicaoCanoVertical);
		batch.draw(canoAcima,posicaoCanoHorizontal,alturaDispositivo/2 + espacoEntreCanos/2 + posicaoCanoVertical);

		batch.end();
	}

	private void inicializarTexturas(){
		passaros = new Texture[3];

		passaros[0] = new Texture("passaro1.png");
		passaros[1] = new Texture("passaro2.png");
		passaros[2] = new Texture("passaro3.png");

		fundo = new Texture("fundo.png");
		canoAbaixo = new Texture("cano_baixo_maior.png");
		canoAcima = new Texture("cano_topo_maior.png");
	}

	private void inicializarObjetos(){
		batch = new SpriteBatch();
		random = new Random();

		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();
		posicaoInicialVerticalPassaro = (alturaDispositivo/2);
		posicaoCanoHorizontal = larguraDispositivo;
		espacoEntreCanos = 400;
	}

	@Override
	public void dispose () {

	}
}

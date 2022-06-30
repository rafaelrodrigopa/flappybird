package com.rafa.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class jogo extends ApplicationAdapter {

	private SpriteBatch batch;
	private Texture[] passaros;
	private Texture fundo;
	private Texture canoAbaixo;
	private Texture canoAcima;
	private Texture gameOver;

	//Formas para colisão
	private ShapeRenderer shapeRenderer;
	private Circle circuloPassaro;
	private Rectangle retanguloCanoAcima;
	private Rectangle retanguloCanoAbaixo;

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
	private int pontos;
	private boolean passouCano;
	private int estadoJogo = 0;

	//Exibição de textos
	BitmapFont textoPontuacao;
	BitmapFont textoReiniciar;
	BitmapFont melhorPontuacao;

	@Override
	public void create () {
		inicializarTexturas();
		inicializarObjetos();
	}

	@Override
	public void render () {
		verificarEstadoJogo();
		validarPontos();
		desenharTexturas();
		detectarColisoes();
	}

	private void detectarColisoes() {

		//CirculoPassaro
		circuloPassaro.set(50 + passaros[0].getWidth()/2,posicaoInicialVerticalPassaro + passaros[0].getHeight()/2,passaros[0].getWidth()/2);

		//retanguloCanoAbaixo
		retanguloCanoAbaixo.set(posicaoCanoHorizontal,alturaDispositivo/2-canoAbaixo.getHeight() - espacoEntreCanos/2 - posicaoCanoVertical, canoAbaixo.getWidth(),canoAbaixo.getHeight());

		//retanguloCanoAcima
		retanguloCanoAcima.set(posicaoCanoHorizontal,alturaDispositivo/2 + espacoEntreCanos/2 + posicaoCanoVertical,canoAcima.getWidth(),canoAcima.getHeight());

		boolean colidiuCanoAcima = Intersector.overlaps(circuloPassaro, retanguloCanoAcima);
		boolean colidiuCanoAbaixo = Intersector.overlaps(circuloPassaro, retanguloCanoAbaixo);

		if ( colidiuCanoAcima || colidiuCanoAbaixo){
			estadoJogo = 2;
		}

//		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//		shapeRenderer.circle(50 + passaros[0].getWidth()/2,posicaoInicialVerticalPassaro + passaros[0].getHeight()/2,passaros[0].getWidth()/2);
//		shapeRenderer.setColor(Color.RED);
//
//		shapeRenderer.rect(posicaoCanoHorizontal,alturaDispositivo/2 + espacoEntreCanos/2 + posicaoCanoVertical,canoAcima.getWidth(),canoAcima.getHeight());
//		shapeRenderer.rect(posicaoCanoHorizontal,alturaDispositivo/2-canoAbaixo.getHeight() - espacoEntreCanos/2 - posicaoCanoVertical, canoAbaixo.getWidth(),canoAbaixo.getHeight());
//		shapeRenderer.end();
	}

	private void validarPontos() {
		if (posicaoCanoHorizontal < 50-passaros[0].getWidth()){//Passou da posição do passaro
			if (!passouCano){
				pontos++;
				passouCano = true;
			}
		}

		// Depois de alguns renders a imagens é trocada para fazer a animação
		variacao += Gdx.graphics.getDeltaTime() * 10;

		// São trẽs variações de imagens para animar o passaro
		if (variacao > 3)
			variacao = 0;
	}

	private void verificarEstadoJogo(){

		boolean toqueTela = Gdx.input.justTouched();

		if ( estadoJogo == 0 ){
			/* Aplica evento de toque na tela*/
			if (toqueTela){
				gravidade = -15;
				estadoJogo = 1;
			}
		}else if (estadoJogo == 1){

			/* Aplica evento de toque na tela*/
			if (toqueTela){
				gravidade = -15;
				estadoJogo = 1;
			}

			//Movimentar o cano
			posicaoCanoHorizontal -= Gdx.graphics.getDeltaTime() * 200;

			if (posicaoCanoHorizontal < -canoAcima.getWidth()){
				posicaoCanoHorizontal = larguraDispositivo;
				posicaoCanoVertical = random.nextInt(400) -200;
				passouCano = false;
			}

			/* Aplica gravidade no pássaro */
			if (posicaoInicialVerticalPassaro > 0 || toqueTela)
				posicaoInicialVerticalPassaro = posicaoInicialVerticalPassaro - gravidade;



			gravidade++;

		}else if (estadoJogo == 2){
			/* Aplica evento de toque na tela*/
			if (toqueTela){
				estadoJogo = 0;
				pontos = 0;
				gravidade = 0;
				posicaoInicialVerticalPassaro = alturaDispositivo/2;
				posicaoCanoHorizontal = larguraDispositivo;
			}
		}
	}

	private void desenharTexturas(){
		batch.begin();

		batch.draw(fundo,0,0,larguraDispositivo,alturaDispositivo);
		batch.draw(passaros[ (int) variacao], 50,posicaoInicialVerticalPassaro);
		batch.draw(canoAbaixo,posicaoCanoHorizontal,alturaDispositivo/2-canoAbaixo.getHeight() - espacoEntreCanos/2 - posicaoCanoVertical);
		batch.draw(canoAcima,posicaoCanoHorizontal,alturaDispositivo/2 + espacoEntreCanos/2 + posicaoCanoVertical);
		textoPontuacao.draw(batch, String.valueOf(pontos),larguraDispositivo/2, alturaDispositivo-110);

		if(estadoJogo == 2){
			batch.draw(gameOver,larguraDispositivo/2 - gameOver.getWidth()/2,alturaDispositivo/2);
			textoReiniciar.draw(batch,"Toque para reiniciar",larguraDispositivo/2 -140, alturaDispositivo/2 - gameOver.getHeight()/2);
			melhorPontuacao.draw(batch,"Seu record é: 0 pontos",larguraDispositivo/2-140,alturaDispositivo/2 - gameOver.getHeight());
		}

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
		gameOver = new Texture("game_over.png");
	}

	private void inicializarObjetos(){
		batch = new SpriteBatch();
		random = new Random();

		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();
		posicaoInicialVerticalPassaro = (alturaDispositivo/2);
		posicaoCanoHorizontal = larguraDispositivo;
		espacoEntreCanos = 400;

		//Configurações dos textos
		textoPontuacao = new BitmapFont();
		textoPontuacao.setColor(Color.WHITE);
		textoPontuacao.getData().setScale(10);

		textoReiniciar = new BitmapFont();
		textoReiniciar.setColor(Color.GREEN);
		textoReiniciar.getData().setScale(2);

		melhorPontuacao = new BitmapFont();
		melhorPontuacao.setColor(Color.RED);
		melhorPontuacao.getData().setScale(2);

		//Formas Geométricas para colisoes;
		shapeRenderer = new ShapeRenderer();
		circuloPassaro = new Circle();
		retanguloCanoAbaixo = new Rectangle();
		retanguloCanoAcima = new Rectangle();
	}

	@Override
	public void dispose () {

	}
}

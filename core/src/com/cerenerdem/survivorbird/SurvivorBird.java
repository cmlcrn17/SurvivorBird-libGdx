package com.cerenerdem.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.awt.Color;
import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {

	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture bee;
	Texture bee1;
	Texture bee2;
	Texture bee3;

	float birdX = 0;
	float birdY = 0;

	int gameState = 0; // Oyun duruyorken 0, başlayınca 1, bitince 2
	float velocity = 0; //Hız
	float enemyVelocity = 2; //Arıların ilerleme hızı
	float gravity = 0.1f; // Yer Çekimi

	int numberOfEnemies = 4; // Arıların sayısı
	float [] enemyX = new float[numberOfEnemies];
	float [] enemyOffset = new float[numberOfEnemies];
	float [] enemyOffset2 = new float[numberOfEnemies];
	float [] enemyOffset3 = new float[numberOfEnemies];
	float distance = 0;
	Random random;

	Circle birdCircle;
	Circle[] enemyCircles;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;

	ShapeRenderer shapeRenderer;

	int score = 0;
	int scoreEnemy = 0;

	BitmapFont font;
	BitmapFont font2;

	@Override
	public void create () { //Oyun başladığında yapılacak işlemler

		batch = new SpriteBatch();

		background = new Texture("background.png");
		bird = new Texture("bird.png");
		bee = new Texture("bee.png");
		bee1 = new Texture("bee.png");
		bee2 = new Texture("bee.png");
		bee3 = new Texture("bee.png");

		distance = Gdx.graphics.getWidth() / 2; //arıların arasındaki uzaklıklar
		random = new Random();

		birdX = Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2;
		birdY = Gdx.graphics.getHeight() / 3;

		shapeRenderer = new ShapeRenderer();

		birdCircle = new Circle();
		enemyCircles = new Circle[numberOfEnemies];
		enemyCircles2 = new Circle[numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];

		font = new BitmapFont();
		font.getData().setScale(4);
		font2 = new BitmapFont();
		font2 = new BitmapFont();
		font2.getData().setScale(6);


		for (int i = 0; i < numberOfEnemies; i++){

			enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200); //Y ekseni için rastgele bir numara oluşturduk.
			enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200); //Y ekseni için rastgele bir numara oluşturduk.
			enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200); //Y ekseni için rastgele bir numara oluşturduk.


			enemyX[i] = Gdx.graphics.getWidth() - bee1.getWidth() / 2 + i * distance;

			enemyCircles[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();


		}

	}

	@Override
	public void render () { //Sürekli yapılacak işlemler render altında yazılır. Mesela kuşun uçuyor olması

		batch.begin();

		//Objelerimizi tanımlıcaz.
		batch.draw(background, 0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight()); // background resmi var olan ekran boyutlarına göre getir.
		batch.draw(bird, birdX, birdY, Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);

		if (gameState == 1){ //Oyun başladıysa

			if (enemyX[scoreEnemy] < Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2){

				score ++;

				if (scoreEnemy < numberOfEnemies - 1) {
					scoreEnemy++;

				} else if (scoreEnemy > 3){

					scoreEnemy = 0;

				}

			}




			if (Gdx.input.justTouched()){ //Kullanıcı ekrana dokunursa, tıklarsa...
				velocity = -7;
			}

			for (int i = 0; i < numberOfEnemies; i++) {

				if (enemyX[i] < Gdx.graphics.getWidth() / 15){

					enemyX[i] = enemyX[i] + numberOfEnemies * distance;

					enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200); //Y ekseni için rastgele bir numara oluşturduk.
					enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200); //Y ekseni için rastgele bir numara oluşturduk.
					enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200); //Y ekseni için rastgele bir numara oluşturduk.




				} else  {

					enemyX[i] = enemyX[i] - enemyVelocity;
				}

				enemyX[i] = enemyX[i] - enemyVelocity;

				batch.draw(bee1, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffset[i],Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
				batch.draw(bee2, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffset2[i],Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
				batch.draw(bee3, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffset3[i],Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);


				enemyCircles[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOffset[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
				enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOffset2[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
				enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOffset3[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);

			}



			if (birdY > 0 ) {
				velocity = velocity + gravity; // Düşme hızı
				birdY = birdY - velocity;
			} else if (velocity < 0){

				gameState = 2;

			}

		} else if (gameState == 0) { // Oyun duruyorsa
			if (Gdx.input.justTouched()){ //Kullanıcı ekrana dokunursa, tıklarsa...
				gameState = 1;
			}
		} else if (gameState == 2){

			font2.draw(batch,"Game Over! Tap To Play Again!",100,Gdx.graphics.getHeight() / 2);

			if (Gdx.input.justTouched()){ //Kullanıcı ekrana dokunursa, tıklarsa...
				gameState = 1;

				birdY= Gdx.graphics.getHeight() / 3;

				for (int i = 0; i < numberOfEnemies; i++){

					enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200); //Y ekseni için rastgele bir numara oluşturduk.
					enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200); //Y ekseni için rastgele bir numara oluşturduk.
					enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200); //Y ekseni için rastgele bir numara oluşturduk.


					enemyX[i] = Gdx.graphics.getWidth() - bee1.getWidth() / 2 + i * distance;

					enemyCircles[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();

				}

				velocity = 0;
				scoreEnemy = 0;
				score = 0;

			}

		}


		font.draw(batch,String.valueOf(score), 100,200);
		batch.end();


		birdCircle.set(birdX + Gdx.graphics.getWidth() / 30, birdY + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);



		for (int i = 0; i < numberOfEnemies; i++){

			if (Intersector.overlaps(birdCircle, enemyCircles[i]) || Intersector.overlaps(birdCircle, enemyCircles2[i]) ||  Intersector.overlaps(birdCircle, enemyCircles3[i])){

				gameState=2;

			}
		}

		//shapeRenderer.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}

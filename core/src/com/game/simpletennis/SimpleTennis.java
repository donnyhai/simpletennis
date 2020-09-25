package com.game.simpletennis;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Gdx.input.getX/getY and Gdx.graphics have origin top right (x down, y right)
//batch.draw has origin top left (x down, y left)

//DOCS
//https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/Input.html
//https://github.com/libgdx/libgdx/wiki/Polling

public class SimpleTennis extends ApplicationAdapter {
	SpriteBatch batch;

	Game game;
	Texture background;

	int height;
	int width;

	double globalTime;

	int counter = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		width = Gdx.graphics.getWidth(); //here width is the longer side, height the shorter
		height = Gdx.graphics.getHeight();

		game = new Game(width, height);

		background = new Texture("icehockey2.png");

		this.globalTime = 0;

	}

	@Override
	public void render () {
		batch.begin();

		this.executeHittings();
		this.actualizeSpeedVectors();

		/*
		this.calculateRacketcurrentSpeeds();

		game.ball.calculateCurrentMotionValues(this.globalTime);

		if (Gdx.input.justTouched()) {
			System.out.println("ballpos: " + game.ball.currentPosition[0] + " " + game.ball.currentPosition[1]);
			System.out.println("ballspeed: " + game.ball.currentSpeed[0] + " " + game.ball.currentSpeed[1]);
			counter = 0;
		}


		if(game.ball.ballHitsRacket(game.p0.racket)) {
			double r = game.p0.racket.speedRate;
			double speed = game.p0.racket.getMotionSpeed();
			//System.out.println("player0");
			//game.ball.actualizeMotionEquations(game.ball.ballToRacketVector(game.p0.racket), this.globalTime);
			double[] vec = new double[] {r * speed * game.p0.racket.currentSpeed[0], r * speed * game.p0.racket.currentSpeed[1]};

			if(counter == 0) {
				System.out.println(vec[0] + " "  + vec[1]);
				counter += 1;
			}

 			game.ball.actualizeMotionEquations(vec, this.globalTime);

			if(counter == 1) {
				System.out.println("currentspeed: " + game.ball.currentSpeed[0] + " and " + game.ball.currentSpeed[1]);
				counter += 1;
			}
		}

		if(game.ball.ballHitsRacket(game.p1.racket)) {
			double r = game.p1.racket.speedRate;
			double speed = game.p1.racket.getMotionSpeed();
			System.out.println("player1");
			//game.ball.actualizeMotionEquations(game.ball.ballToRacketVector(game.p1.racket), this.globalTime);
			double[] vec = new double[] {r * speed * game.p1.racket.currentSpeed[0], r * speed * game.p1.racket.currentSpeed[1]};
			game.ball.actualizeMotionEquations(vec, this.globalTime);

		}

		 */

		if(game.ball.ballHitsWall(0, this.height)) {
			game.ball.setCurrentPosition(new double[] {game.ball.currentPosition[0], game.ball.currentPosition[1]});
			double vec[] = new double[] {0, -2 * game.ball.currentSpeed[1]};
			game.ball.actualizeMotionEquations(vec, this.globalTime);
		}

		if(game.ball.ballHitsGoal(0, this.width)) {
			double vec[] = new double[] {-2 * game.ball.currentSpeed[0], 0};
			game.ball.actualizeMotionEquations(vec, this.globalTime);
		}


		this.globalTime += 0.02;

		draw(batch);
		batch.end();
	}

	public void draw(SpriteBatch batch) {
		int r0 = game.p0.racket.racketSize;
		int r1 = game.p1.racket.racketSize;
		int bs = game.ball.ballSize;
		batch.draw(background, 0, 0, width, height);
		batch.draw(game.ball.texture, (int) game.ball.currentPosition[0] - bs, (int) game.ball.currentPosition[1] - bs, 2 * bs, 2 * bs);
		batch.draw(game.p0.racket.texture, (int) game.p0.racket.currentPosition[0] - r0, (int) game.p0.racket.currentPosition[1] - r0, 2 * r0, 2 * r0);
		batch.draw(game.p1.racket.texture, (int) game.p1.racket.currentPosition[0] - r1, (int) game.p1.racket.currentPosition[1] - r1, 2 * r1, 2 * r1);
	}

	public void actualizeRacketSpeedVectors() {
		for(int i = 0; i < 2; i++) {
			if(Gdx.input.isTouched(i)) {
				int x = Gdx.input.getX(i);
				int y = height - Gdx.input.getY(i);
				if(x < width / 2) {
					if(this.globalTime - this.game.p0.racket.saveTime > this.game.p0.racket.timeDelta) {
						this.game.p0.racket.actualizeSpeedVector(new double[] {x,y});
						this.game.p0.racket.saveTime = this.globalTime;
					}
				}
				if(x > width / 2) {
					if(this.globalTime - this.game.p1.racket.saveTime > this.game.p1.racket.timeDelta) {
						this.game.p1.racket.actualizeSpeedVector(new double[] {x,y});
						this.game.p1.racket.saveTime = this.globalTime;
					}
				}
			}
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	public void actualizeSpeedVectors() {
		this.actualizeRacketSpeedVectors();
		this.game.ball.actualizeBallSpeedVector();
	}

	public void executeHittings() {

	}

}

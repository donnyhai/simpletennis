package com.game.simpletennis;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Gdx.input.getX/getY and Gdx.graphics have origin top right (x down, y left)
//batch.draw has origin top left (x down, y right)

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
	double timeStep = 0.02;

	int counter = 0;

	int upY, downY, upX, downX;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		width = Gdx.graphics.getWidth(); //here width is the longer side, height the shorter (width = 1794 height 1080 (Pixel 2 API 26))
		height = Gdx.graphics.getHeight();

		this.upY = height;
		this.downY = 0;
		this.upX = width;
		this.downX = 0;

		game = new Game(width, height);

		background = new Texture("icehockey2.png");

		this.globalTime = 0;

	}

	@Override
	public void render () {
		batch.begin();

		//this.executeHittings();
		this.actualizeSpeedVectors();

		if(game.ball.ballHitsWall(this.downY, this.upY)) {
			if(this.game.ball.ballHitsUpWall(this.upY)) {
				this.game.ball.currentPosition[1] = this.upY - this.game.ball.ballSize - 1;
			}
			else if (this.game.ball.ballHitsDownWall(this.downY)) {
				this.game.ball.currentPosition[1] = downY + this.game.ball.ballSize + 1;
			}
			this.game.ball.currentSpeed = new double[] {this.game.ball.currentSpeed[0], this.game.ball.currentSpeed[1] - 2 * game.ball.currentSpeed[1]};
			this.game.ball.actualizeNextPosition();
		}
		if(game.ball.ballHitsGoal(this.downX, this.upX)) {
			if(this.game.ball.ballHitsUpGoal(this.upX)) {
				this.game.ball.currentPosition[0] = this.upX - this.game.ball.ballSize - 1;
			}
			else if (this.game.ball.ballHitsDownGoal(this.downX)) {
				this.game.ball.currentPosition[0] = this.downX + this.game.ball.ballSize + 1;
			}
			this.game.ball.currentSpeed = new double[] {this.game.ball.currentSpeed[0] - 2 * game.ball.currentSpeed[0], this.game.ball.currentSpeed[1]};
			this.game.ball.actualizeNextPosition();

		}

		this.globalTime += this.timeStep;

		draw(batch);
		batch.end();
	}


	public void executeHittings() {

	}


	public void actualizeSpeedVectors() {
		this.actualizeRacketSpeedVectors();
		this.game.ball.decelerateSpeed(this.timeStep);
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


	public void draw(SpriteBatch batch) {
		int r0 = game.p0.racket.racketSize;
		int r1 = game.p1.racket.racketSize;
		int bs = game.ball.ballSize;
		batch.draw(background, 0, 0, width, height);
		batch.draw(game.ball.texture, (int) game.ball.currentPosition[0] - bs, (int) game.ball.currentPosition[1] - bs, 2 * bs, 2 * bs);
		batch.draw(game.p0.racket.texture, (int) game.p0.racket.currentPosition[0] - r0, (int) game.p0.racket.currentPosition[1] - r0, 2 * r0, 2 * r0);
		batch.draw(game.p1.racket.texture, (int) game.p1.racket.currentPosition[0] - r1, (int) game.p1.racket.currentPosition[1] - r1, 2 * r1, 2 * r1);
	}


	@Override
	public void dispose () {
		batch.dispose();
	}

}

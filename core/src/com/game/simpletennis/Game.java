package com.game.simpletennis;

import com.badlogic.gdx.graphics.Texture;

public class Game {

    Player p0;
    Player p1;
    Ball ball;

    public Game(int width, int height) {
        this.p0 = new Player(new Racket(new Texture("button.png"), 100), new Goal(500), 0);
        this.p1 = new Player(new Racket(new Texture("button.png"), 100), new Goal(500), 1);
        this.ball = new Ball(new Texture("circle.png"), 100);

        this.p0.racket.setCurrentPosition(new double[] {100, height * 0.5});
        this.p0.racket.setNextPosition(this.p0.racket.currentPosition);
        this.p1.racket.setCurrentPosition(new double[] {width - 100, height * 0.5});
        this.p1.racket.setNextPosition(this.p1.racket.currentPosition);

        this.ball.setCurrentPosition(new double[] {width * 0.5, height * 0.5});
        this.ball.setNextPosition(this.ball.currentPosition);
        this.ball.setCurrentSpeed(new double[] {80, 45});
        this.ball.setAcceleration(new double[] {10, 10});
    }

}

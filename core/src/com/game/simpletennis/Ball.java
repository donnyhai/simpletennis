package com.game.simpletennis;

import com.badlogic.gdx.graphics.Texture;

public class Ball {

    Texture texture;
    int ballSize; //radius

    double[] initialPosition = new double[2]; //initial position of the ball, every hit induces a new initial position for the next movement
    double[] currentPosition = new double[2]; //current position, center of the ball
    double initialSpeed[] = {0,0};
    double currentSpeed[] = {0,0};
    double[] a = new double[2];

    double lastHitTime;
    double timePassedSinceLastHit;

    public Ball(Texture texture, int ballSize) {
        this.texture = texture;
        this.ballSize = ballSize;
        this.timePassedSinceLastHit = 0;
        this.lastHitTime = 0;
    }

    //ball hits object and object induces addedSpeed on the ball
    public void actualizeMotionEquations(double[] addedSpeed, double globalTime) {
        this.initialPosition = this.currentPosition;
        this.initialSpeed[0] = this.currentSpeed[0] + addedSpeed[0];
        this.initialSpeed[1] = this.currentSpeed[1] + addedSpeed[1];
        this.currentSpeed = this.initialSpeed;
        this.lastHitTime = globalTime;
        this.timePassedSinceLastHit = 0;
    }

    //actualize physical values of ball movement
    public void calculateCurrentMotionValues(double globalTime) {
        this.timePassedSinceLastHit  = globalTime - this.lastHitTime;

        this.currentSpeed[0] = this.a[0] * this.timePassedSinceLastHit + this.initialSpeed[0];
        this.currentSpeed[1] = this.a[1] * this.timePassedSinceLastHit + this.initialSpeed[1];

        this.currentPosition[0] = 0.5 * this.a[0] * Math.pow(this.timePassedSinceLastHit, 2) + this.initialSpeed[0] * this.timePassedSinceLastHit + this.initialPosition[0];
        this.currentPosition[1] = 0.5 * this.a[1] * Math.pow(this.timePassedSinceLastHit, 2) + this.initialSpeed[1] * this.timePassedSinceLastHit + this.initialPosition[1];
    }

    public double[] ballToRacketVector(Racket racket) {
        return new double[] {this.currentPosition[0] - racket.currentPosition[0], this.currentPosition[1] - racket.currentPosition[1]};
    }

    public boolean ballHitsRacket(Racket racket) {
        return this.abs(this.ballToRacketVector(racket)) < racket.racketSize + this.ballSize;
    }

    public boolean ballHitsGoal(int upX, int downX) {
        if(this.currentPosition[0] - upX < this.ballSize) {
            return true;
        }
        else if (downX - this.currentPosition[0] < this.ballSize) {
            return true;
        }
        return false;
    }

    public boolean ballHitsWall(int leftY, int rightY) {
        if(this.currentPosition[1] - leftY < this.ballSize) {
            return true;
        }
        else if (rightY - this.currentPosition[1] < this.ballSize) {
            return true;
        }
        return false;
    }

    public double abs(double[] vec) {
        return Math.sqrt(Math.pow(vec[0], 2) + Math.pow(vec[1], 2));
    }

    public void setInitialPosition(double[] position) {
        this.initialPosition = position;
    }

    public void setCurrentPosition(double[] position) {
        this.currentPosition = position;
    }

    public void setAcceleration(double[] a) {
        this.a = a;
    }
}

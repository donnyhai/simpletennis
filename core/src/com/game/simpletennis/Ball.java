package com.game.simpletennis;

import com.badlogic.gdx.graphics.Texture;

public class Ball {

    Texture texture;
    int ballSize; //radius

    double[] currentPosition = new double[2]; //current position, center of the ball
    double[] nextPosition = new double[2];
    double currentSpeed[] = {0,0};
    double[] a = new double[2];

    double lastHitTime;
    double timePassedSinceLastHit;

    double mass;
    double impulse;
    double kinEnergy;

    int counter = 0;

    public Ball(Texture texture, int ballSize) {
        this.texture = texture;
        this.ballSize = ballSize;
        this.timePassedSinceLastHit = 0;
        this.lastHitTime = 0;
    }

    //BASE METHODS

    public void decelerateSpeed(double timeStep) {
        this.setCurrentPosition(this.nextPosition);
        for(int i = 0; i < 2; i++) {
            double speed_i = this.currentSpeed[i];
            if(Math.abs(speed_i) > this.a[i] * timeStep) {
                if (speed_i > 0) {
                    this.currentSpeed[i] = speed_i - this.a[i] * timeStep;
                }
                else if (speed_i < 0) {
                    this.currentSpeed[i] = speed_i + this.a[i] * timeStep;
                }
            }
            else {
                this.currentSpeed[i] = 0;
            }
        }
        this.setNextPosition(new double[] {this.currentPosition[0] + this.currentSpeed[0], this.currentPosition[1] + this.currentSpeed[1]});
    }






    //RACKET HITTING

    public double[] ballToRacketVector(Racket racket) {
        return new double[] {racket.currentPosition[0] - this.currentPosition[0], racket.currentPosition[1] - this.currentPosition[1]};
    }

    public boolean ballHitsRacket(Racket racket) {
        return this.abs(this.ballToRacketVector(racket)) < racket.racketSize + this.ballSize;
    }

    public boolean posNearBall(double[] pos, int add_distance) {
        return this.abs(new double[] {this.currentPosition[0] - pos[0], this.currentPosition[1] - pos[1]}) < this.ballSize + add_distance;
    }
















    //ACTUALIZE VALUES

    public void actualizeCurrentSpeed() {
        this.currentSpeed = new double[] {this.nextPosition[0] - this.currentPosition[0], this.nextPosition[1] - this.currentPosition[1]};
    }

    public void actualizeNextPosition() {
        this.nextPosition = new double[] {this.currentPosition[0] + this.currentSpeed[0], this.currentPosition[1] + this.currentSpeed[1]};
    }








    //WALL AND GOAL HITTING

    public boolean ballHitsGoal(int downX, int upX) {
        return this.ballHitsDownGoal(downX) || this.ballHitsUpGoal(upX);
    }

    public boolean ballHitsUpGoal(int upX) {
        if(upX - this.currentPosition[0] < this.ballSize) { return true; }
        return false;
    }

    public boolean ballHitsDownGoal(int downX) {
        if (this.currentPosition[0] - downX < this.ballSize) { return true; }
        return false;
    }

    public boolean ballHitsWall(int downY, int upY) {
        return this.ballHitsUpWall(upY) || this.ballHitsDownWall(downY);
    }

    public boolean ballHitsUpWall(int upY) {
        if(upY - this.currentPosition[1] < this.ballSize) { return true; }
        return false;
    }

    public boolean ballHitsDownWall(int downY) {
        if (this.currentPosition[1] - downY < this.ballSize) { return true; }
        return false;
    }








    //SETTERS

    public void setCurrentPosition(double[] position) {
        this.currentPosition = position;
    }
    public void setCurrentSpeed(double[] speed) {
        this.currentSpeed = speed;
    }

    public void setNextPosition(double[] position) {
        this.nextPosition = position;
    }

    public void setImpulse(double impulse) {
        this.impulse = impulse;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void setKinEnergy(double kinEnergy) {
        this.kinEnergy = kinEnergy;
    }

    public void setAcceleration(double[] a) {
        this.a = a;
    }






    public double abs(double[] vec) {
        return Math.sqrt(Math.pow(vec[0], 2) + Math.pow(vec[1], 2));
    }
}

package com.game.simpletennis;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class Racket {

    Texture texture;

    double currentPosition[] = {0,0};
    double nextPosition[] = {0,0}; //save next position
    double currentSpeed[] = {0,0};

    double timeDelta = 0.03; //time between currentPosition and nextPosition

    int racketSize; //radius
    double saveTime = 0;
    double speedRate = 0.001;

    double mass;
    double impuls;
    double kinEnergy;


    public Racket(Texture texture, int racketSize) {
        this.texture = texture;
        this.racketSize = racketSize;
    }

    public void setCurrentPosition(double[] position) {
        this.currentPosition = position;
    }

    public void setNextPosition(double[] position) {
        this.nextPosition = position;
    }

    public void setTimeDelta(double time) {
        this.timeDelta = time;
    }

    public void setCurrentSpeed(double[] vec) {
        this.currentSpeed = vec;
    }

    public void actualizeSpeedVector(double[] nextPosition) {
        this.setCurrentPosition(this.nextPosition);
        this.setNextPosition(nextPosition);
        this.currentSpeed = new double[] {this.nextPosition[0] - this.currentPosition[0], this.nextPosition[1] - this.currentPosition[1]};
    }

    public double getMotionSpeed() {
        return this.abs(this.currentSpeed)/this.timeDelta;
    }

    public double abs(double[] vec) {
        return Math.sqrt(Math.pow(vec[0], 2) + Math.pow(vec[1], 2));
    }


}

package com.game.simpletennis;

import com.badlogic.gdx.graphics.Texture;

public class Ball2 {

    Texture texture;
    int ballSize; //radius

    double[] initialPosition = new double[2]; //initial position of the ball, every hit induces a new initial position for the next movement
    double[] currentPosition = new double[2]; //current position, center of the ball
    double[] nextPosition = new double[2];
    double initialSpeed[] = {0,0};
    double currentSpeed[] = {0,0};
    double[] a = new double[2];

    double lastHitTime;
    double timePassedSinceLastHit;

    double mass;
    double impulse;
    double kinEnergy;

    int counter = 0;

    public Ball2(Texture texture, int ballSize) {
        this.texture = texture;
        this.ballSize = ballSize;
        this.timePassedSinceLastHit = 0;
        this.lastHitTime = 0;
    }

    public void actualizeSpeedVector(double rate) {
        if(counter == 0) {
            System.out.println("nextpos: " + this.nextPosition[0] + " " + this.nextPosition[1]);
            counter = 1;
        }


        this.setCurrentPosition(this.nextPosition);


        for(int i = 0; i < 2; i++) {
            double speedi = this.currentSpeed[i];
            if(Math.abs(speedi) > rate * this.a[i]) {
                double signum = speedi / Math.abs(speedi);
                this.currentSpeed[i] = speedi - signum * this.a[i] * rate;
            }
            else {
                this.currentSpeed[i] = 0;
            }
        }

        this.setNextPosition(new double[] {this.currentPosition[0] + this.currentSpeed[0], this.currentPosition[1] + this.currentSpeed[1]});


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

        this.currentPosition[0] = 0.5 * this.a[0] * this.timePassedSinceLastHit * this.timePassedSinceLastHit + this.initialSpeed[0] * this.timePassedSinceLastHit + this.initialPosition[0];
        this.currentPosition[1] = 0.5 * this.a[1] * this.timePassedSinceLastHit * this.timePassedSinceLastHit + this.initialSpeed[1] * this.timePassedSinceLastHit + this.initialPosition[1];
    }

    public double[] ballToRacketVector(Racket racket) {
        return new double[] {this.currentPosition[0] - racket.currentPosition[0], this.currentPosition[1] - racket.currentPosition[1]};
    }

    public boolean ballHitsRacket(Racket racket) {
        return this.abs(this.ballToRacketVector(racket)) < racket.racketSize + this.ballSize;
    }

    public boolean ballHitsGoal(int downX, int upX) {

        if(this.counter < 2) {
            System.out.println("curr: "  + this.currentPosition[0] + " " + this.currentPosition[1]);
            counter = 2;
        }

        if(upX - this.currentPosition[0] < this.ballSize) {
            this.currentPosition[0] = upX - this.ballSize - 1;
            return true;
        }
        else if (this.currentPosition[0] - downX < this.ballSize) {
            this.currentPosition[0] = downX + this.ballSize + 1;
            return true;
        }
        return false;
    }

    public boolean ballHitsWall(int downY, int upY) {


        if(upY - this.currentPosition[1] < this.ballSize) {
            this.currentPosition[1] = upY - this.ballSize - 1;
            return true;
        }
        else if (this.currentPosition[1] - downY < this.ballSize) {
            this.currentPosition[1] = downY + this.ballSize + 1;
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

    public void actualizeCurrentSpeed() {
        this.currentSpeed = new double[] {this.nextPosition[0] - this.currentPosition[0], this.nextPosition[1] - this.currentPosition[1]};
    }
}

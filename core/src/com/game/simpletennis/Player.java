package com.game.simpletennis;

public class Player {

    int points = 0;
    Racket racket;
    Goal goal;
    int number;

    int counter = 0;

    public Player(Racket racket, Goal goal, int number) {
        this.racket = racket;
        this.goal = goal;
        this.number = number;
    }

}

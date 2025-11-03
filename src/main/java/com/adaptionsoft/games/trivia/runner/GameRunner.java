package com.adaptionsoft.games.trivia.runner;

import java.io.PrintStream;
import java.util.Random;
import com.adaptionsoft.games.uglytrivia.Game;

public class GameRunner {

    private static final int CHANCE_OF_WRONG_ANSWER_1_IN = 9;
    private static final int WRONG_ANSWER_ROLL = 7;

    public static void main(String[] args) {
        Random rand = new Random();
        playGame(rand, System.out);
    }

    public static void playGame(Random rand, PrintStream output) {

        Game aGame = new Game(output);

        aGame.add("Chet");
        aGame.add("Pat");
        aGame.add("Sue");

        if (!aGame.isPlayable()) {
            output.println("Game cannot be played, not enough players.");
            return;
        }

        boolean playerHasWon;
        do {
            aGame.roll(rand.nextInt(5) + 1);

            if (rand.nextInt(CHANCE_OF_WRONG_ANSWER_1_IN) == WRONG_ANSWER_ROLL) {
                playerHasWon = aGame.wrongAnswer();
            } else {
                playerHasWon = aGame.wasCorrectlyAnswered();
            }

        } while (!playerHasWon);
    }
}
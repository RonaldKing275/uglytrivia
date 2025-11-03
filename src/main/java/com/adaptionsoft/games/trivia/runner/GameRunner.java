package com.adaptionsoft.games.trivia.runner;

import java.io.PrintStream;
import java.util.Random;
import com.adaptionsoft.games.uglytrivia.Game;

public class GameRunner {

    // Sugestia 3: Usunięcie "Magicznych Liczb"
    private static final int CHANCE_OF_WRONG_ANSWER_1_IN = 9;
    private static final int WRONG_ANSWER_ROLL = 7;

    public static void main(String[] args) {
        Random rand = new Random();
        // Sugestia 2: Przekazanie domyślnego wyjścia do gry
        playGame(rand, System.out);
    }

    /**
     * Uruchamia jedną pełną grę, wypisując stan na podany strumień wyjścia.
     */
    public static void playGame(Random rand, PrintStream output) {
        // Sugestia 2: Wstrzyknięcie strumienia wyjścia
        Game aGame = new Game(output);

        aGame.add("Chet");
        aGame.add("Pat");
        aGame.add("Sue");

        if (!aGame.isPlayable()) {
            output.println("Game cannot be played, not enough players.");
            return;
        }

        // BŁĄD 3 (Naprawiony): Poprawiona logika pętli gry
        boolean playerHasWon;
        do {
            aGame.roll(rand.nextInt(5) + 1);

            // Symulacja złej odpowiedzi (logika zachowana z oryginału)
            if (rand.nextInt(CHANCE_OF_WRONG_ANSWER_1_IN) == WRONG_ANSWER_ROLL) {
                playerHasWon = aGame.wrongAnswer(); // Zwróci false
            } else {
                playerHasWon = aGame.wasCorrectlyAnswered(); // Zwróci true, jeśli gracz wygrał
            }

        } while (!playerHasWon); // Kontynuuj, dopóki ktoś nie wygra
    }
}
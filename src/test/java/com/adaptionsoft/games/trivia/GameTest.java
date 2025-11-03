package com.adaptionsoft.games.trivia;

import static org.junit.Assert.*;

import com.adaptionsoft.games.trivia.runner.GameRunner;
import org.approvaltests.Approvals;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.stream.IntStream;

public class GameTest {

    @Test
    public void itsLockedDown() throws Exception {

        Random randomizer = new Random(123455);

        // Sugestia 2 (Testowanie): Przechwyć strumień wyjścia zamiast System.setOut
        ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
        try (PrintStream testOutput = new PrintStream(resultStream)) {

            IntStream.range(1, 15).forEach(i -> {
                // Przekaż strumień do przechwytywania do GameRunnera
                GameRunner.playGame(randomizer, testOutput);
            });
        } // try-with-resources automatycznie zamknie strumień

        // Zweryfikuj przechwycony output
        Approvals.verify(resultStream.toString());
    }
}
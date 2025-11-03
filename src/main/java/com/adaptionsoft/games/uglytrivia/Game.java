package com.adaptionsoft.games.uglytrivia;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {

    // Sugestia 3: Usunięcie "Magicznych Liczb"
    private static final int MAX_PLAYERS = 6;
    private static final int BOARD_SIZE = 12;
    private static final int COINS_TO_WIN = 6;
    private static final int QUESTIONS_PER_CATEGORY = 50;

    // Sugestia 5: Uproszczenie logiki kategorii
    private static final String[] CATEGORIES = {"Pop", "Science", "Sports", "Rock"};

    // Sugestia 1: Użycie klasy Player zamiast 4 tablic
    private final List<Player> players = new ArrayList<>();
    private int currentPlayer = 0;

    // Pule pytań
    private final LinkedList<String> popQuestions = new LinkedList<>();
    private final LinkedList<String> scienceQuestions = new LinkedList<>();
    private final LinkedList<String> sportsQuestions = new LinkedList<>();
    private final LinkedList<String> rockQuestions = new LinkedList<>();

    // Sugestia 2: Oddzielenie logiki od I/O (wstrzykiwanie strumienia wyjścia)
    private final PrintStream output;

    public Game(PrintStream output) {
        this.output = output;
        for (int i = 0; i < QUESTIONS_PER_CATEGORY; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast(createRockQuestion(i));
        }
    }

    public String createRockQuestion(int index) {
        return "Rock Question " + index;
    }

    public boolean isPlayable() {
        return (howManyPlayers() >= 2);
    }

    public boolean add(String playerName) {
        if (howManyPlayers() >= MAX_PLAYERS) {
            output.println("Game is full. Cannot add player: " + playerName);
            return false;
        }

        // BŁĄD 2 (Naprawiony): Poprawne dodawanie gracza
        players.add(new Player(playerName));

        output.println(playerName + " was added");
        output.println("They are player number " + players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    private Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    public void roll(int roll) {
        Player player = getCurrentPlayer();
        output.println(player.getName() + " is the current player");
        output.println("They have rolled a " + roll);

        if (player.isInPenaltyBox()) {
            if (roll % 2 != 0) {
                // Sugestia 6: Użycie flagi na obiekcie gracza
                player.setGettingOutOfPenaltyBox(true);
                output.println(player.getName() + " is getting out of the penalty box");
                movePlayerAndAskQuestion(roll);
            } else {
                player.setGettingOutOfPenaltyBox(false);
                output.println(player.getName() + " is not getting out of the penalty box");
            }
        } else {
            player.setGettingOutOfPenaltyBox(false); // Na wszelki wypadek
            movePlayerAndAskQuestion(roll);
        }
    }

    private void movePlayerAndAskQuestion(int roll) {
        Player player = getCurrentPlayer();
        player.move(roll, BOARD_SIZE);

        output.println(player.getName() + "'s new location is " + player.getPlace());
        output.println("The category is " + currentCategory());
        askQuestion();
    }

    private void askQuestion() {
        String category = currentCategory();

        // Sugestia 4: Użycie .equals() do porównywania stringów
        if ("Pop".equals(category))
            output.println(popQuestions.removeFirst());
        if ("Science".equals(category))
            output.println(scienceQuestions.removeFirst());
        if ("Sports".equals(category))
            output.println(sportsQuestions.removeFirst());
        if ("Rock".equals(category))
            output.println(rockQuestions.removeFirst());
    }

    private String currentCategory() {
        // Sugestia 5: Uproszczona logika kategorii
        return CATEGORIES[getCurrentPlayer().getPlace() % CATEGORIES.length];
    }

    /**
     * @return true jeśli gracz wygrał grę, false w przeciwnym razie
     */
    public boolean wasCorrectlyAnswered() {
        Player player = getCurrentPlayer();

        if (player.isInPenaltyBox()) {
            if (player.isGettingOutOfPenaltyBox()) {
                return handleCorrectAnswer(player);
            } else {
                // Był w polu karnym, ale nie wychodził (parzysty rzut)
                // LUB właśnie odpowiedział (bez rzutu), ale to nie jest obsłużone
                // Wg oryginalnej logiki, jeśli nie wychodzi, to po prostu przechodzi dalej
                advancePlayer();
                return false; // Nie wygrał
            }
        } else {
            // Normalna odpowiedź
            return handleCorrectAnswer(player);
        }
    }

    private boolean handleCorrectAnswer(Player player) {
        output.println("Answer was correct!!!!"); // Poprawiona literówka

        // BŁĄD 1 (Naprawiony): Gracz dostaje monetę PRZED zmianą tury
        player.incrementPurse();
        output.println(player.getName()
                + " now has "
                + player.getPurses()
                + " Gold Coins.");

        // BŁĄD 3 (Naprawiony): Sprawdzamy wygraną aktualnego gracza
        boolean winner = didPlayerWin();
        advancePlayer();
        return winner;
    }

    /**
     * @return Zawsze false, bo zła odpowiedź nie kończy gry
     */
    public boolean wrongAnswer() {
        Player player = getCurrentPlayer();
        output.println("Question was incorrectly answered");
        output.println(player.getName() + " was sent to the penalty box");
        player.setInPenaltyBox(true);

        advancePlayer();

        // BŁĄD 3 (Naprawiony): Zwraca status wygranej (zawsze false)
        return false;
    }

    private void advancePlayer() {
        currentPlayer++;
        if (currentPlayer == players.size()) {
            currentPlayer = 0;
        }
    }

    private boolean didPlayerWin() {
        // BŁĄD 3 (Naprawiony): Logika odwrócona na poprawną
        return getCurrentPlayer().getPurses() == COINS_TO_WIN;
    }
}
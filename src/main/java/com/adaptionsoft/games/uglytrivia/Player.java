package com.adaptionsoft.games.uglytrivia;

public class Player {
    private final String name;
    private int place = 0;
    private int purses = 0;
    private boolean inPenaltyBox = false;
    private boolean isGettingOutOfPenaltyBox = false;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPlace() {
        return place;
    }

    public int getPurses() {
        return purses;
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    public void setInPenaltyBox(boolean inPenaltyBox) {
        this.inPenaltyBox = inPenaltyBox;
    }

    public boolean isGettingOutOfPenaltyBox() {
        return isGettingOutOfPenaltyBox;
    }

    public void setGettingOutOfPenaltyBox(boolean gettingOutOfPenaltyBox) {
        isGettingOutOfPenaltyBox = gettingOutOfPenaltyBox;
    }

    public void incrementPurse() {
        this.purses++;
    }

    public void move(int roll, int boardSize) {
        this.place += roll;
        if (this.place >= boardSize) {
            this.place -= boardSize;
        }
    }
}
package sample.players;

import sample.Game.CheckersColor;
import sample.Game.CheckersDeck;


public class Player {


    private CheckersColor color;
    private String name;

    public Player() {
    }

    public Player(String name, CheckersColor color) {
        this.name = name;
        this.color = color;

    }

    public String getName() {
        return name;
    }

    public CheckersColor getColor() {
        return color;
    }

    public void getCurrentCommand(CheckersDeck cd) {

    }
}


package sample.serialization;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import sample.Game.Cell;
import sample.players.Player;

import java.util.*;

public class CheckerDeckDTO {
    private List<CellDTO> cells;
    private List<CheckerDTO> checkers;
    //private Map<CellDTO, CheckerDTO> checkerDTOMap;
    private CellDTO[] lastRowArr;
    private Player moveTurn;
    private Player pl1, pl2;

    public CheckerDeckDTO() {
        //checkerDTOMap = new HashMap<>();
       cells = new ArrayList<>();
       checkers = new ArrayList<>();
        lastRowArr = new CellDTO[4];
    }

    public List<CellDTO> getCells() {
        return cells;
    }

    public List<CheckerDTO> getCheckers() {
        return checkers;
    }

    public CellDTO[] getLastRowArr() {
        return lastRowArr;
    }



    public Player getMoveTurn() {
        return moveTurn;
    }

    public Player getPl1() {
        return pl1;
    }

    public Player getPl2() {
        return pl2;
    }

    public void setMoveTurn(Player moveTurn) {
        this.moveTurn = moveTurn;
    }

    public void setPl1(Player pl1) {
        this.pl1 = pl1;
    }

    public void setPl2(Player pl2) {
        this.pl2 = pl2;
    }

    public CellDTO foundCellDTOById(UUID id) {
        for(CellDTO cell : cells) {
            if(cell.getId().equals(id)) return cell;
        }
        return null;
    }

    @Override
    public String toString() {
        return "CheckerDeckDTO{" +
                "cells=" + cells +
                ", checkers=" + checkers +
                ", lastRowArr=" + Arrays.toString(lastRowArr) +
                ", moveTurn=" + moveTurn +
                ", pl1=" + pl1 +
                ", pl2=" + pl2 +
                '}';
    }
}

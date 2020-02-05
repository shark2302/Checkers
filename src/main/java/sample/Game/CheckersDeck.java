package sample.Game;

import sample.command.*;
import sample.players.*;
import java.util.*;
import sample.exceptions.*;

public class CheckersDeck {

    public static final int ROWS = 8;



    private Stack<Command> commands;
    private Cell[] lastRowArr;
    private Map<CheckersColor, List<Checker>> checkerMap;
    private Checker picked;
    private Player moveTurn;
    private Player pl1, pl2;



    public CheckersDeck(Player pl1, Player pl2) {
        lastRowArr = new Cell[4];
        checkerMap = new HashMap<>();
        commands = new Stack<>();
        this.pl1 = pl1;
        this.pl2 = pl2;
        checkerMap.put(CheckersColor.WHITE, new ArrayList<>());
        checkerMap.put(CheckersColor.RED, new ArrayList<>());
        createCells();
        moveTurn = pl1;
    }
    public CheckersDeck() {
        lastRowArr = new Cell[4];
        checkerMap = new HashMap<>();
        commands = new Stack<>();
        checkerMap.put(CheckersColor.WHITE, new ArrayList<>());
        checkerMap.put(CheckersColor.RED, new ArrayList<>());
    }

    private void createCells() {
        Cell[] previousRow = new Cell[4];
        for (int i = 0; i < previousRow.length; i++)
            previousRow[i] = new Cell(0);
        for (int i = 0; i < ROWS - 1; i++) {
            Cell[] curRow = new Cell[4];
            for (int j = 0; j < previousRow.length; j++) {
                Cell c = new Cell(i + 1);
                if (i % 2 == 0) {
                    curRow[j] = c;
                    previousRow[j].getLinkByColor(CheckersColor.WHITE).setRigth(curRow[j]);
                    curRow[j].getLinkByColor(CheckersColor.RED).setLeft(previousRow[j]);
                    if (j != previousRow.length - 1) {
                        curRow[j].getLinkByColor(CheckersColor.RED).setRigth(previousRow[j + 1]);
                        previousRow[j + 1].getLinkByColor(CheckersColor.WHITE).setLeft(curRow[j]);
                    }
                } else {
                    if (j == 0) {
                        curRow[j] = c;
                    }
                    previousRow[j].getLinkByColor(CheckersColor.WHITE).setLeft(curRow[j]);
                    curRow[j].getLinkByColor(CheckersColor.RED).setRigth(previousRow[j]);
                    if (j != previousRow.length - 1) {
                        Cell cell = new Cell(i + 1);
                        curRow[j + 1] = cell;
                        previousRow[j].getLinkByColor(CheckersColor.WHITE).setRigth(curRow[j + 1]);
                        curRow[j + 1].getLinkByColor(CheckersColor.RED).setLeft(previousRow[j]);
                    }
                }
            }
            if (i < previousRow.length - 1) {
                for (int j = 0; j < previousRow.length; j++) {
                    Checker checker = new Checker(previousRow[j], CheckersColor.WHITE);
                    checkerMap.get(CheckersColor.WHITE).add(checker);
                }
            }
            if (i > ROWS / 2) {
                for (int j = 0; j < previousRow.length; j++) {
                    Checker checker = new Checker(previousRow[j], CheckersColor.RED);
                    checkerMap.get(CheckersColor.RED).add(checker);
                }
                if (i == ROWS - 2) {
                    for (int k = 0; k < curRow.length; k++) {
                        Checker ch = new Checker(curRow[k], CheckersColor.RED);
                        checkerMap.get(CheckersColor.RED).add(ch);
                    }
                }

            }
            previousRow = curRow;

        }
        lastRowArr = previousRow;

    }

    public void pickChecker(Cell cell) throws NotAvailiblePickException, ClickedException {
        if (picked == null) {

            if (cell == null || cell.getChecker() == null) throw new ClickedException();
            List<Checker> availibleToHit = new ArrayList<>();
            for (Checker checker : checkerMap.get(moveTurn.getColor())) {

                if (checker.isPossibleHit())
                        availibleToHit.add(checker);
                }

            if (!availibleToHit.isEmpty()) {
                for (int i = 0; i < availibleToHit.size(); i++) {
                    if (cell == availibleToHit.get(i).getCell() && cell.getChecker() != null) {
                        picked = cell.getChecker();

                        availibleToHit.clear();
                    }
                }
                if(picked == null) throw new NotAvailiblePickException();
            } else if (cell.getChecker() != null && cell.getChecker().getColor() == moveTurn.getColor()) {
                picked = cell.getChecker();

                availibleToHit.clear();
            }
            else throw new NotAvailiblePickException();
        }

    }

    public void getCurrentCommand(Cell c) throws WrongMoveException, MustHitException {
        if (picked != null) {
            if (picked.isPossibleHit()) {
                if (picked.getLongMove().containsKey(c)) {
                    commands.push(new HitCommand(this, picked,c, picked.getLongMove().get(c)));
                } else {
                    picked = null;
                    throw new MustHitException();
                }
            } else {
                if (picked.getAvailibleMoves().contains(c)) {
                    commands.push(new MoveCommand(this, c, picked));
                } else {
                    picked = null;
                    throw new WrongMoveException("Wrong move");
                }
            }
            picked = null;

        }

    }

    public boolean isWinner() {
        if(commands.size() > 15) {
            if(checkerMap.get(CheckersColor.WHITE).isEmpty() || noAvailibleMoves(CheckersColor.WHITE))
                return true;
            if(checkerMap.get(CheckersColor.RED).isEmpty() || noAvailibleMoves(CheckersColor.RED))
                return true;
        }
        return false;
    }

    private boolean noAvailibleMoves(CheckersColor color) {
        for(Checker checker : checkerMap.get(color)) {
            if(checker.isPossibleHit()|| !checker.getAvailibleMoves().isEmpty())
                return false;
        }
        return true;
    }

    public void changeMoveTurn() {
        if (moveTurn.getColor() == pl1.getColor()) {
            moveTurn = pl2;
        } else if (moveTurn.getColor() == pl2.getColor()) {
            moveTurn = pl1;
        }
    }

    public void setMoveTurn(Player moveTurn) {
        this.moveTurn = moveTurn;
    }

    public Player getPl1() {
        return pl1;
    }

    public Player getPl2() {
        return pl2;
    }

    public Player getMoveTurn() {
        return moveTurn;
    }

    public Map<CheckersColor, List<Checker>> getCheckerMap() {
        return checkerMap;
    }

    public Stack<Command> getCommands() {
        return commands;
    }

    public Checker getPicked() {
        return picked;
    }

    public Cell[] getLastRowArr() {
        return lastRowArr;
    }

    public void setPl1(Player pl1) {
        this.pl1 = pl1;
    }

    public void setPl2(Player pl2) {
        this.pl2 = pl2;
    }
}










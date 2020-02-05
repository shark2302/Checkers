package sample.threads;

import sample.Game.Cell;
import sample.Game.Checker;
import sample.Game.CheckersColor;
import sample.Game.CheckersDeck;
import sample.command.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CheckerThread extends Thread {

    private Checker checker;
    private CheckersDeck cd;
    private Map<HitCommand, Boolean> hitCommands;
    private Map<MoveCommand, Boolean> moveCommands;

    public CheckerThread(CheckersDeck cd, Checker checker, Map<HitCommand, Boolean> hitCommands, Map<MoveCommand, Boolean> moveCommands) {
        this.checker = checker;
        this.hitCommands = hitCommands;
        this.moveCommands = moveCommands;
        this.cd = cd;
    }

    @Override
    public void run() {

        if(checker.isPossibleHit()) {
            Map<Cell, List<Checker>> longHit = checker.getLongMove();
            for(Cell c : longHit.keySet()) {
                hitCommands.put(new HitCommand(cd, checker, c, longHit.get(c)), isSafeMove(c));
            }
        }
        else {
            for(Cell c : checker.getAvailibleMoves())
                moveCommands.put(new MoveCommand(cd, c, checker), isSafeMove(c));
        }

    }

    private boolean isSafeMove(Cell c) {
        if(c.getLeftChecker(CheckersColor.WHITE) != null &&
                c.getLeftChecker(CheckersColor.WHITE).getColor() != checker.getColor()
        && !c.isCheckerOnRightCell(CheckersColor.RED))
            return false;
        else if(c.getRightChecker(CheckersColor.WHITE) != null &&
                c.getRightChecker(CheckersColor.WHITE).getColor() != checker.getColor()
                && !c.isCheckerOnLeftCell(CheckersColor.RED))
            return false;
        else if(c.getLeftChecker(CheckersColor.RED) != null &&
                c.getLeftChecker(CheckersColor.RED).getColor() != checker.getColor()
                && !c.isCheckerOnRightCell(CheckersColor.WHITE))
            return false;
        else if(c.getRightChecker(CheckersColor.RED) != null &&
                c.getRightChecker(CheckersColor.RED).getColor() != checker.getColor()
                && !c.isCheckerOnLeftCell(CheckersColor.WHITE))
            return false;
        else return true;
    }
}

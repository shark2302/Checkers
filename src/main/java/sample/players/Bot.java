package sample.players;
import sample.Game.*;
import sample.command.*;
import sample.Game.CheckersDeck;
import sample.command.HitCommand;
import sample.command.MoveCommand;
import sample.threads.CheckerThread;

import java.util.*;

public class Bot extends Player {
   private Random r = new Random();
    private Map<MoveCommand, Boolean> moveCommands;
    private Map<HitCommand, Boolean> hitCommands;

    public Bot(String name, CheckersColor color) {
        super(name, color);
        moveCommands = new HashMap<>();
        hitCommands = new HashMap<>();

    }

    public void getCurrentCommand(CheckersDeck cd) {

        if(cd.getMoveTurn().getColor() == getColor()) {
            for(Checker checker : cd.getCheckerMap().get(getColor())) {
                if (checker.isPossibleMove()) {
                    Thread thread = new CheckerThread(cd, checker, hitCommands, moveCommands);
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        System.out.println("Hit Commands : " + hitCommands.size());
        System.out.println("Move Commands : " + moveCommands.size());
        cd.getCommands().push(selectCommand());
        moveCommands.clear();
        hitCommands.clear();


       /* if (cd.getMoveTurn().getColor() == getColor()) {
            for (Checker checker : cd.getCheckerMap().get(getColor())) {
                if (checker.isPossibleHit()) {
                    Map<Cell, List<Checker>> hitMap = checker.getLongMove();
                    Cell key = null;
                    for (Cell c : hitMap.keySet())
                        key = c;
                    if (key != null && hitMap.get(key) != null) {
                        cd.getCommands().push(new HitCommand(cd, checker, key, hitMap.get(key)));
                        return;
                    }
                }
            }
            for (Checker checker : cd.getCheckerMap().get(getColor())) {
                if (!checker.getAvailibleMoves().isEmpty()) {
                    cd.getCommands().push(new MoveCommand(cd, checker.getAvailibleMoves().get(r.nextInt(1)), checker));
                    return;
                }

            }
        }

        */

    }

    private Command selectCommand() {
        if(hitCommands.size() == 1)
            return singleHitCommand();
        else if(!hitCommands.isEmpty())
            return selectHitCommand();
        else {
            return selectMoveCommand();
        }
    }

    private HitCommand singleHitCommand() {
        for(HitCommand c : hitCommands.keySet())
            return c;
        return null;
    }

    private HitCommand selectHitCommand() {
        List<HitCommand> com = new ArrayList<>();
        for(HitCommand command : hitCommands.keySet()) {
            if(hitCommands.get(command))
                com.add(command);
        }
        if(com.isEmpty())
            return singleHitCommand();
        else if(com.size() == 1)
            return com.get(0);
        else {
            int hitted = 0;
            HitCommand command = null;
            for (HitCommand c : com) {
                if(c.getHittedChecker().size() > hitted) {
                    hitted = c.getHittedChecker().size();
                    command = c;
                }
            }
            if(command != null) return command;
        }
        return null;
    }

    private MoveCommand selectMoveCommand() {
        List<MoveCommand> safeMove = new ArrayList<>();
        for(MoveCommand command : moveCommands.keySet()) {
            if(moveCommands.get(command))
                safeMove.add(command);
        }
        if(safeMove.isEmpty()) return singleMoveCommand();
        else if(safeMove.size() == 1) return safeMove.get(0);
        else {
            return safeMove.get(r.nextInt(safeMove.size() - 1));
        }
    }

    private MoveCommand singleMoveCommand() {
        for(MoveCommand c : moveCommands.keySet())
            return c;
        return null;
    }

}


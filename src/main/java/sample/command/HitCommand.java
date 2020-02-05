package sample.command;


import sample.Game.*;

import java.util.List;

public class HitCommand implements Command {

    private CheckersDeck cd;
    private Checker checker;
    private Cell previousCell;
    private List<Checker> hittedChecker;
    private boolean becomeKing;
    private Cell c;


    public HitCommand(CheckersDeck cd, Checker checker, Cell c, List<Checker> hittedChecker) {
        this.cd = cd;
        this.c = c;
        this.checker = checker;
   this.hittedChecker = hittedChecker;
        becomeKing = false;
        previousCell = checker.getCell();

    }

    @Override
    public void doCommand() {

            for(Cell c : checker.getMiddleCells().get(this.c)) {
                System.out.println(c);
                if (checker.getColor() == CheckersColor.RED && c.getRow() == 0
                        || checker.getColor() == CheckersColor.WHITE && c.getRow() == 7 && !(checker instanceof King)) {
                    int index = cd.getCheckerMap().get(checker.getColor()).indexOf(checker);
                    checker = new King(checker.getCell(), checker.getColor());
                    cd.getCheckerMap().get(checker.getColor()).set(index, checker);
                    becomeKing = true;
                }
            }
        checker.longHit(c, hittedChecker);
        for(Checker hitted : hittedChecker)
            cd.getCheckerMap().get(hitted.getColor()).remove(hitted);
        cd.changeMoveTurn();

    }





    @Override
    public void undoCommand() {
        if(becomeKing) {
            int index = cd.getCheckerMap().get(checker.getColor()).indexOf(checker);
            checker = new Checker(checker.getCell(), checker.getColor());
            cd.getCheckerMap().get(checker.getColor()).set(index, checker);
            becomeKing = false;
        }
        for(Checker hittedChecker: hittedChecker)
            cd.getCheckerMap().get(hittedChecker.getColor()).add(new Checker(hittedChecker.getCell(), hittedChecker.getColor()));
        checker.move(previousCell);

        cd.changeMoveTurn();

    }

    public String getMoveTurnPlayerName() {
        return cd.getMoveTurn().getName();
    }

    public List<Checker> getHittedChecker() {
        return hittedChecker;
    }

    public boolean isBecomeKing() {
        return becomeKing;
    }
}

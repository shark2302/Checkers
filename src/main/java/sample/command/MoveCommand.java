package sample.command;
import sample.Game.Checker;
import sample.Game.Cell;
import sample.Game.CheckersDeck;
import sample.Game.King;
import sample.Game.CheckersColor;



public class MoveCommand implements Command {
    private Cell currentCell;
    private Checker checker;
    private Cell c;
    private CheckersDeck cd;
    private boolean becomeKing;

    public MoveCommand(CheckersDeck cd, Cell c, Checker checker) {
        this.cd = cd;
        this.c = c;
        this.checker = checker;
        currentCell = checker.getCell();
        becomeKing = false;
    }

    @Override
    public void doCommand() {
        checker.move(c);
        if(checker.getColor() == CheckersColor.RED && checker.getCell().getRow() == 0
        || checker.getColor() == CheckersColor.WHITE && checker.getCell().getRow() == 7 && !(checker instanceof King)) {
            int index = cd.getCheckerMap().get(checker.getColor()).indexOf(checker);
            checker = new King(checker.getCell(), checker.getColor());
            cd.getCheckerMap().get(checker.getColor()).set(index, checker);
            becomeKing = true;
        }

        cd.changeMoveTurn();

    }

    @Override
    public void undoCommand() {
        if(becomeKing && (checker.getColor() == CheckersColor.RED && checker.getCell().getRow() == 0
                || checker.getColor() == CheckersColor.WHITE && checker.getCell().getRow() == 7 && checker instanceof King)) {
            int index = cd.getCheckerMap().get(checker.getColor()).indexOf(checker);
            checker = new Checker(checker.getCell(), checker.getColor());
            cd.getCheckerMap().get(checker.getColor()).set(index, checker);
            becomeKing = false;
        }
        checker.move(currentCell);

        cd.changeMoveTurn();

    }

    @Override
    public String getMoveTurnPlayerName() {
        return cd.getMoveTurn().getName();
    }

    @Override
    public boolean isBecomeKing() {
        return becomeKing;
    }
}

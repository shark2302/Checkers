package sample.Game;
import java.util.*;

public class King extends Checker {


    public King(Cell cell, CheckersColor color) {
        super(cell, color);

        cell.setChecker(this);
    }

    @Override
    public List<Cell> getAvailibleMoves() {
        List<Cell> availibleMoves = new ArrayList<>();
        for (Cell cell = getCell().getRight(CheckersColor.RED);
             cell != null && cell.getChecker() == null;
             cell = cell.getRight(CheckersColor.RED)) {
            availibleMoves.add(cell);
        }
        for (Cell cell = getCell().getLeft(CheckersColor.RED);
             cell != null && cell.getChecker() == null;
             cell = cell.getLeft(CheckersColor.RED)) {
            availibleMoves.add(cell);
        }
        for (Cell cell = getCell().getRight(CheckersColor.WHITE);
             cell != null && cell.getChecker() == null;
             cell = cell.getRight(CheckersColor.WHITE)) {
            availibleMoves.add(cell);
        }
        for (Cell cell = getCell().getLeft(CheckersColor.WHITE);
             cell != null && cell.getChecker() == null;
             cell = cell.getLeft(CheckersColor.WHITE)) {
            availibleMoves.add(cell);
        }
        return availibleMoves;
    }

    public Map<Cell, List<Checker>> getPossibleHit(Map.Entry<Cell, List<Checker>> start, Set<Cell> visited) {
        Map<Cell, List<Checker>> hitOpportunity = new HashMap<>();
        for (Cell rUp = start.getKey().getRight(CheckersColor.RED);
             rUp != null;
             rUp = rUp.getRight(CheckersColor.RED)) {
            visited.add(rUp);
            if(rUp.getChecker() != null && rUp.getChecker().getColor() != getColor() &&
                    rUp.getRight(CheckersColor.RED) != null && !rUp.isCheckerOnRightCell(CheckersColor.RED)) {
                List<Checker> a = new ArrayList<>(start.getValue());
                a.add(rUp.getChecker());
                hitOpportunity.put(rUp.getRight(CheckersColor.RED), a);

            }
            if(rUp.getChecker() != null) break;
        }

        for (Cell rDown = start.getKey().getRight(CheckersColor.WHITE);
             rDown != null;
             rDown = rDown.getRight(CheckersColor.WHITE)) {
            visited.add(rDown);
            if(rDown.getChecker() != null && rDown.getChecker().getColor() != getColor() &&
                    rDown.getRight(CheckersColor.WHITE) != null && !rDown.isCheckerOnRightCell(CheckersColor.WHITE)) {
                List<Checker> a = new ArrayList<>(start.getValue());
                a.add(rDown.getChecker());
                hitOpportunity.put(rDown.getRight(CheckersColor.WHITE), a);

            }
            if(rDown.getChecker() != null) break;
        }
        for (Cell lUp = start.getKey().getLeft(CheckersColor.RED);
             lUp != null ;
             lUp = lUp.getLeft(CheckersColor.RED)) {
            visited.add(lUp);
            if(lUp.getChecker() != null && lUp.getChecker().getColor() != getColor() &&
                    lUp.getLeft(CheckersColor.RED) != null && !lUp.isCheckerOnLeftCell(CheckersColor.RED)) {
                List<Checker> a = new ArrayList<>(start.getValue());
                a.add(lUp.getChecker());
                hitOpportunity.put(lUp.getLeft(CheckersColor.RED), a);

            }
            if(lUp.getChecker() != null) break;
        }
        for (Cell lDown = start.getKey().getLeft(CheckersColor.WHITE);
             lDown != null ;
             lDown = lDown.getLeft(CheckersColor.WHITE)) {
            visited.add(lDown);
            if(lDown.getChecker() != null && lDown.getChecker().getColor() != getColor() &&
                    lDown.getLeft(CheckersColor.WHITE) != null && !lDown.isCheckerOnLeftCell(CheckersColor.WHITE)) {
                List<Checker> a = new ArrayList<>(start.getValue());
                a.add(lDown.getChecker());
                hitOpportunity.put(lDown.getLeft(CheckersColor.WHITE), a);

            }
            if(lDown.getChecker() != null) break;
        }
        return hitOpportunity;
    }

    public Map<Cell, List<Cell>> countMiddleCells(Map.Entry<Cell, List<Cell>> start, Set<Cell> visited) {
        Map<Cell, List<Cell>> hitOpportunity = new HashMap<>();
        for (Cell rUp = start.getKey().getRight(CheckersColor.RED);
             rUp != null;
             rUp = rUp.getRight(CheckersColor.RED)) {
            visited.add(rUp);
            if(rUp.getChecker() != null && rUp.getChecker().getColor() != getColor() &&
                    rUp.getRight(CheckersColor.RED) != null && !rUp.isCheckerOnRightCell(CheckersColor.RED)) {
                List<Cell> a = new ArrayList<>(start.getValue());
                a.add(rUp.getRight(CheckersColor.RED));
                hitOpportunity.put(rUp.getRight(CheckersColor.RED), a);

            }
            if(rUp.getChecker() != null) break;
        }

        for (Cell rDown = start.getKey().getRight(CheckersColor.WHITE);
             rDown != null;
             rDown = rDown.getRight(CheckersColor.WHITE)) {
            visited.add(rDown);
            if(rDown.getChecker() != null && rDown.getChecker().getColor() != getColor() &&
                    rDown.getRight(CheckersColor.WHITE) != null && !rDown.isCheckerOnRightCell(CheckersColor.WHITE)) {
                List<Cell> a = new ArrayList<>(start.getValue());
                a.add(rDown.getRight(CheckersColor.WHITE));
                hitOpportunity.put(rDown.getRight(CheckersColor.WHITE), a);

            }
            if(rDown.getChecker() != null) break;
        }
        for (Cell lUp = start.getKey().getLeft(CheckersColor.RED);
             lUp != null ;
             lUp = lUp.getLeft(CheckersColor.RED)) {
            visited.add(lUp);
            if(lUp.getChecker() != null && lUp.getChecker().getColor() != getColor() &&
                    lUp.getLeft(CheckersColor.RED) != null && !lUp.isCheckerOnLeftCell(CheckersColor.RED)) {
                List<Cell> a = new ArrayList<>(start.getValue());
                a.add(lUp.getLeft(CheckersColor.RED));
                hitOpportunity.put(lUp.getLeft(CheckersColor.RED), a);

            }
            if(lUp.getChecker() != null) break;
        }
        for (Cell lDown = start.getKey().getLeft(CheckersColor.WHITE);
             lDown != null ;
             lDown = lDown.getLeft(CheckersColor.WHITE)) {
            visited.add(lDown);
            if(lDown.getChecker() != null && lDown.getChecker().getColor() != getColor() &&
                    lDown.getLeft(CheckersColor.WHITE) != null && !lDown.isCheckerOnLeftCell(CheckersColor.WHITE)) {
                List<Cell> a = new ArrayList<>(start.getValue());
                a.add(lDown.getLeft(CheckersColor.WHITE));
                hitOpportunity.put(lDown.getLeft(CheckersColor.WHITE), a);

            }
            if(lDown.getChecker() != null) break;
        }
        return hitOpportunity;
    }

}

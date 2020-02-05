package sample.Game;


import java.util.*;

public class Checker {


    private Cell cell;
    private CheckersColor color;
    private   Map<Cell, Set<Cell>> wayToLastPoint;
    private List<Cell> middleCell;

    public Checker(Cell cell, CheckersColor color) {
        this.cell = cell;
        this.color = color;
        cell.setChecker(this);
      wayToLastPoint = new HashMap<>();
      middleCell = new ArrayList<>();

    }
    public boolean isPossibleMove() {
        return !getAvailibleMoves().isEmpty() || isPossibleHit();
    }
    public List<Cell> getAvailibleMoves() {
        List<Cell> availibleMoves = new ArrayList<>();
       if(cell.getLeft(color) != null && !cell.isCheckerOnLeftCell(color))
           availibleMoves.add(cell.getLinks().get(color).getLeft());
       if(cell.getRight(color) != null && !cell.isCheckerOnRightCell(color))
           availibleMoves.add(cell.getLinks().get(color).getRigth());
       return availibleMoves;
    }

    public boolean isPossibleHit() {
        return !getPossibleHit(new Map.Entry<Cell, List<Checker>>() {
            List<Checker> a = Collections.emptyList();
            @Override
            public Cell getKey() {
                return getCell();
            }

            @Override
            public List<Checker> getValue() {
                return a;
            }

            @Override
            public List<Checker> setValue(List<Checker> value) {
                List<Checker> oldVal = a;
                a = value;
                return oldVal;
            }
        }, new HashSet<Cell>()).isEmpty();
    }


    public Map<Cell, List<Checker>> getLongMove() {
        Set<Cell> visited = new HashSet<>();
        Set<Cell> visitedAfterSearched = new HashSet<>(visited);
        Map.Entry<Cell, List<Checker>> s = new Map.Entry<Cell, List<Checker>>() {
          List<Checker> a = Collections.emptyList();
            @Override
            public Cell getKey() {
                return getCell();
            }

            @Override
            public List<Checker> getValue() {
                return a;
            }

            @Override
            public List<Checker> setValue(List<Checker> value) {
                List<Checker> oldVal = a;
                a = value;
                return oldVal;
            }
        };
        return searchLongHitMove(s, visited);
    }

    public Map<Cell, List<Checker>> getPossibleHit(Map.Entry<Cell, List<Checker>> start, Set<Cell> visited) {
        Cell cell = start.getKey();
        Cell rUp = cell.getRight(CheckersColor.RED);
        Cell rDown = cell.getRight(CheckersColor.WHITE);
        Cell lUp = cell.getLeft(CheckersColor.RED);
        Cell lDown = cell.getLeft(CheckersColor.WHITE);
        Map<Cell, List<Checker>> hitOpportunity = new HashMap<>();

        if(rUp != null && rUp.getChecker() != null && rUp.getChecker().getColor() != color &&
                rUp.getRight(CheckersColor.RED) != null && !rUp.isCheckerOnRightCell(CheckersColor.RED)) {

            List<Checker> a = new ArrayList<>(start.getValue());
            a.add(rUp.getChecker());
            hitOpportunity.put(rUp.getRight(CheckersColor.RED), a);
        }
        if(rDown != null && rDown.getChecker() != null &&  rDown.getChecker().getColor() != color &&
                rDown.getRight(CheckersColor.WHITE) != null && !rDown.isCheckerOnRightCell(CheckersColor.WHITE)) {

            List<Checker> a = new ArrayList<>(start.getValue());
            a.add(rDown.getChecker());
            hitOpportunity.put(rDown.getRight(CheckersColor.WHITE), a);
        }
        if(lUp != null && lUp.getChecker() != null &&  lUp.getChecker().getColor() != color
                && lUp.getLeft(CheckersColor.RED) != null && !lUp.isCheckerOnLeftCell(CheckersColor.RED)) {

            List<Checker> a = new ArrayList<>(start.getValue());
            a.add(lUp.getChecker());
            hitOpportunity.put(lUp.getLeft(CheckersColor.RED), a);
        }
        if(lDown != null && lDown.getChecker() != null  && lDown.getChecker().getColor() != color
                && lDown.getLeft(CheckersColor.WHITE) != null && !lDown.isCheckerOnLeftCell(CheckersColor.WHITE)) {
            List<Checker> a = new ArrayList<>(start.getValue());
            a.add(lDown.getChecker());
            hitOpportunity.put(lDown.getLeft(CheckersColor.WHITE), a);
        }
        return hitOpportunity;
    }

    private Map<Cell, List<Checker>> searchLongHitMove(Map.Entry<Cell, List<Checker>> start,  Set<Cell> visited) {
        Map<Cell, List<Checker>> possible = getPossibleHit(start, visited);
        if (possible.isEmpty() || visited.containsAll(possible.keySet())) {
            if (!visited.isEmpty()) {
                visited.add(start.getKey());

                if(!start.getKey().equals(getCell()))
                middleCell.add(start.getKey());


                return Collections.singletonMap(start.getKey(), start.getValue());
            }
        }
            Map<Cell, List<Checker>> lastCells = new HashMap<>();
            visited.add(start.getKey());

             if(!start.getKey().equals(getCell()))
                middleCell.add(start.getKey());
            for (Map.Entry<Cell, List<Checker>> c : possible.entrySet()) {
                if (!visited.contains(c.getKey())) {
                    Map<Cell, List<Checker>> m = searchLongHitMove(c, visited);
                    lastCells.putAll(m);
                }
            }
            return lastCells;
    }

    public Map<Cell, List<Cell>> getMiddleCells() {
        Set<Cell> visited = new HashSet<>();

        Map.Entry<Cell, List<Cell>> s = new Map.Entry<Cell, List<Cell>>() {
            List<Cell> a = Collections.emptyList();
            @Override
            public Cell getKey() {
                return getCell();
            }

            @Override
            public List<Cell> getValue() {
                return a;
            }

            @Override
            public List<Cell> setValue(List<Cell> value) {
                List<Cell> oldVal = a;
                a = value;
                return oldVal;
            }
        };
        return searchMiddleCells(s, visited);
    }

    public Map<Cell, List<Cell>> countMiddleCells(Map.Entry<Cell, List<Cell>> start, Set<Cell> visited) {
        Cell cell = start.getKey();
        Cell rUp = cell.getRight(CheckersColor.RED);
        Cell rDown = cell.getRight(CheckersColor.WHITE);
        Cell lUp = cell.getLeft(CheckersColor.RED);
        Cell lDown = cell.getLeft(CheckersColor.WHITE);
        Map<Cell, List<Cell>> hitOpportunity = new HashMap<>();

        if(rUp != null && rUp.getChecker() != null && rUp.getChecker().getColor() != color &&
                rUp.getRight(CheckersColor.RED) != null && !rUp.isCheckerOnRightCell(CheckersColor.RED)) {

            List<Cell> a = new ArrayList<>(start.getValue());
            a.add(rUp.getRight(CheckersColor.RED));
            hitOpportunity.put(rUp.getRight(CheckersColor.RED), a);
        }
        if(rDown != null && rDown.getChecker() != null &&  rDown.getChecker().getColor() != color &&
                rDown.getRight(CheckersColor.WHITE) != null && !rDown.isCheckerOnRightCell(CheckersColor.WHITE)) {

            List<Cell> a = new ArrayList<>(start.getValue());
            a.add(rDown.getRight(CheckersColor.WHITE));
            hitOpportunity.put(rDown.getRight(CheckersColor.WHITE), a);
        }
        if(lUp != null && lUp.getChecker() != null &&  lUp.getChecker().getColor() != color
                && lUp.getLeft(CheckersColor.RED) != null && !lUp.isCheckerOnLeftCell(CheckersColor.RED)) {

            List<Cell> a = new ArrayList<>(start.getValue());
            a.add(lUp.getLeft(CheckersColor.RED));
            hitOpportunity.put(lUp.getLeft(CheckersColor.RED), a);
        }
        if(lDown != null && lDown.getChecker() != null  && lDown.getChecker().getColor() != color
                && lDown.getLeft(CheckersColor.WHITE) != null && !lDown.isCheckerOnLeftCell(CheckersColor.WHITE)) {
            List<Cell> a = new ArrayList<>(start.getValue());
            a.add(lDown.getLeft(CheckersColor.WHITE));
            hitOpportunity.put(lDown.getLeft(CheckersColor.WHITE), a);
        }
        return hitOpportunity;
    }

    private Map<Cell, List<Cell>> searchMiddleCells(Map.Entry<Cell, List<Cell>> start,  Set<Cell> visited) {
        Map<Cell, List<Cell>> possible = countMiddleCells(start, visited);
        if (possible.isEmpty() || visited.containsAll(possible.keySet())) {
            if (!visited.isEmpty()) {
                visited.add(start.getKey());

                if(!start.getKey().equals(getCell()))
                    middleCell.add(start.getKey());


                return Collections.singletonMap(start.getKey(), start.getValue());
            }
        }
        Map<Cell, List<Cell>> lastCells = new HashMap<>();
        visited.add(start.getKey());

        if(!start.getKey().equals(getCell()))
            middleCell.add(start.getKey());
        for (Map.Entry<Cell, List<Cell>> c : possible.entrySet()) {
            if (!visited.contains(c.getKey())) {
                Map<Cell, List<Cell>> m = searchMiddleCells(c, visited);
                lastCells.putAll(m);
            }
        }
        return lastCells;
    }

    public void move(Cell c) {
        getCell().setChecker(null);
        setCell(c);
        c.setChecker(this);
        middleCell.clear();
        wayToLastPoint.clear();
    }

    public void longHit(Cell c, List<Checker> hitted) {
        for(Checker checker : hitted) {

            checker.getCell().setChecker(null);
        }
        getCell().setChecker(null);
        setCell(c);
        c.setChecker(this);
        wayToLastPoint.clear();
        middleCell.clear();
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Cell getCell() {
        return cell;
    }


    public CheckersColor getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Checker checker = (Checker) o;
        return Objects.equals(cell, checker.cell) &&
                color == checker.color;
    }



    public Map<Cell, Set<Cell>> getWayToLastCell() {
        for(Cell c : wayToLastPoint.keySet()) {
            wayToLastPoint.get(c).remove(getCell());
        }
        return wayToLastPoint;
    }

    public List<Cell> getMiddleCell() {
        return middleCell;
    }


}



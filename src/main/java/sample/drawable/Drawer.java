package sample.drawable;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.Game.CheckersDeck;
import sample.Game.Cell;
import sample.Game.Checker;
import sample.Game.CheckersColor;
import sample.Game.King;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Drawer {

    private GraphicsContext gc;
    private CheckersDeck cd;
    private double size;
    private double cellSize;
    private List<DrawableCell> cells;
    private boolean drawed;

    public Drawer(GraphicsContext gc, CheckersDeck cd, double size) {
        this.gc = gc;
        this.cd = cd;
        this.size = size;
        cellSize = size / 8;
        cells = new ArrayList<>();
        drawed = false;
        fillCellList();
    }

    public void draw(GraphicsContext gc) {
        for (DrawableCell cell : cells) {
            cell.draw(gc);
            if (cell.getCell().getChecker() != null) {
                drawChecker(cell.getCell().getChecker(), cell.getRow(), cell.getColomn());
                if (cell.getCell().getChecker() == cd.getPicked()) {
                    gc.setStroke(Color.BLUE);
                    gc.strokeOval(cell.getColomn() * cellSize + 1, cell.getRow() * cellSize + 1, cellSize - 2, cellSize - 2);
                }
            }
        }
        if(cd.getPicked() != null) {
            List<Cell> availibleMoves = cd.getPicked().getAvailibleMoves();

            for (DrawableCell cell : cells) {

                if(cd.getPicked().isPossibleHit()) {
                    Map<Cell, List<Checker>> ends = cd.getPicked().getLongMove();
                    List<Cell> middle = cd.getPicked().getMiddleCell();
                    if(middle.contains(cell.getCell())) {
                        gc.setStroke(Color.RED);
                        gc.strokeRect(cell.getColomn() * cellSize + 3, cell.getRow() * cellSize + 3, cellSize - 5, cellSize - 5);
                    }
                    if (ends.containsKey(cell.getCell())) {
                        gc.setStroke(Color.BLUE);
                        gc.strokeRect(cell.getColomn() * cellSize + 3, cell.getRow() * cellSize + 3, cellSize - 5, cellSize - 5);
                    }


                }
                else if(availibleMoves.contains(cell.getCell()) && !cd.getPicked().isPossibleHit()) {
                    gc.setStroke(Color.RED);
                    gc.strokeRect(cell.getColomn() * cellSize + 3, cell.getRow() * cellSize + 3, cellSize - 5, cellSize - 5);
                }

            }
        }
        drawed = true;
    }

    private void drawChecker(Checker checker, int row, int colomn) {
        if (checker.getColor() == CheckersColor.RED)
            gc.setFill(Color.RED);
        if (checker.getColor() == CheckersColor.WHITE)
            gc.setFill(Color.WHITE);

        if (checker.getColor() == CheckersColor.WHITE && checker instanceof King)
            gc.setFill(Color.BLUE);
        if (checker.getColor() == CheckersColor.RED && checker instanceof King)
            gc.setFill(Color.GREEN);
        gc.fillOval(colomn * cellSize, row * cellSize, cellSize, cellSize);
    }

    private void fillCellList() {
        Cell[] lastRowArr = cd.getLastRowArr().clone();

        for (int i = CheckersDeck.ROWS - 1; i >= 0; i--) {
            if (i % 2 == 0) {
                for (int j = 0; j < lastRowArr.length; j++) {
                    DrawableCell cell = new DrawableCell(lastRowArr[j], i, j * 2, cellSize);
                    if(lastRowArr[j].getRight(CheckersColor.RED) != null)
                    lastRowArr[j] = lastRowArr[j].getRight(CheckersColor.RED);
                    cells.add(cell);
                }
            } else {
                for (int j = 0; j < lastRowArr.length; j++) {
                    DrawableCell cell = new DrawableCell(lastRowArr[j], i, j * 2 + 1, cellSize);
                    if(lastRowArr[j].getLeft(CheckersColor.RED) != null)
                    lastRowArr[j] = lastRowArr[j].getLeft(CheckersColor.RED);
                    cells.add(cell);
                }
            }

        }


    }
    public Cell foundPickedCell(double posX, double posY) {
        for (int i = 0; i < cells.size(); i++) {
            if (cells.get(i).isClicked(posX, posY)) return cells.get(i).getCell();

        }
        return null;
    }

    public boolean isDrawed() {
        return drawed;
    }
}

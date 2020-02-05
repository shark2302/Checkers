package sample.drawable;

import javafx.scene.canvas.GraphicsContext;
import sample.Game.Cell;
import javafx.scene.paint.Color;



public class DrawableCell {


    private int row;
    private int colomn;
    private double size;

    private Cell cell;




    public DrawableCell(Cell cell, int row, int colomn, double size) {
        this.cell = cell;
        this.row = row;
        this.colomn = colomn;
        this.size = size;

    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(colomn *  size, row * size, size, size);
    }



    public boolean isClicked(double posX, double posY) {
        double bottomX = colomn * size + size;
        double bottomY = row * size + size;
        if(posX >= colomn * size && posY >= row * size && posX <= bottomX && posY <= bottomY) return true;
        return false;

    }





    public int getRow() {
        return row;
    }

    public int getColomn() {
        return colomn;
    }



    public Cell getCell() {
        return cell;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "row=" + row +
                ", colomn=" + colomn +
                '}';
    }
}

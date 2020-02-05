package sample.Game;




public class Link {

    private Cell left;
    private Cell rigth;

    public Link(Cell left, Cell rigth) {
        this.left = left;
        this.rigth = rigth;
    }

    public Cell getLeft() {
        return left;
    }

    public Cell getRigth() {
        return rigth;
    }

    public void setLeft(Cell left) {
        this.left = left;
    }

    public void setRigth(Cell rigth) {
        this.rigth = rigth;
    }
}

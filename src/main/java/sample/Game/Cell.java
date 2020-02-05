package sample.Game;

import sample.serialization.CellDTO;
import sample.serialization.LinksEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Cell {

private int row;
    private UUID id;
    private Map<CheckersColor, Link> links;
    private Checker checker;
    private Map<LinksEnum, UUID> linksId;


    public Cell(int row) {
        this.row = row;
        links = new HashMap<>();
        links.put(CheckersColor.RED, new Link(null, null));
        links.put(CheckersColor.WHITE, new Link(null, null));
        checker = null;
        id = UUID.randomUUID();
    }

    public Cell(CellDTO c) {
        this.row = c.getRow();
        links = new HashMap<>();
        links.put(CheckersColor.RED, new Link(null, null));
        links.put(CheckersColor.WHITE, new Link(null, null));
        checker = null;
        id = c.getId();
        linksId = new HashMap<>(c.getLinks());
    }

    public Checker getChecker() {
        return checker;
    }

    public void setChecker(Checker checker) {
        this.checker = checker;
    }

    public Link getLinkByColor(CheckersColor color) {
        return links.get(color);
    }

    public Map<CheckersColor, Link> getLinks() {
        return links;
    }

    public int getRow() {
        return row;
    }

    public Cell getLeft(CheckersColor color) {
        return links.get(color).getLeft();
    }

    public Cell getRight(CheckersColor color) {
        return links.get(color).getRigth();
    }

    public boolean isCheckerOnLeftCell(CheckersColor color) {
        if(links.get(color).getLeft() != null && links.get(color).getLeft().getChecker() != null)
        return links.get(color).getLeft().getChecker() != null;
        else return false;
    }

    public boolean isCheckerOnRightCell(CheckersColor color) {
        if(links.get(color).getRigth() != null && links.get(color).getRigth().getChecker() != null)
        return links.get(color).getRigth().getChecker() != null;
        else return false;
    }
    public Checker getLeftChecker(CheckersColor color) {
        if(links.get(color).getLeft() != null && links.get(color).getLeft().getChecker() != null)
        return links.get(color).getLeft().getChecker();
        else return null;
    }
    public Checker getRightChecker(CheckersColor color) {
        if(links.get(color).getRigth() != null && links.get(color).getRigth().getChecker() != null)
        return links.get(color).getRigth().getChecker();
        else return null;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return row == cell.row &&
                Objects.equals(links, cell.links) &&
                Objects.equals(checker, cell.checker);
    }


    @Override
    public int hashCode() {
        return Objects.hash(row, links, checker);
    }

    public Map<LinksEnum, UUID> getLinksId() {
        return linksId;
    }
}

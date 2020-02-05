package sample.serialization;

import sample.Game.Cell;
import sample.Game.CheckersColor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CellDTO {

    private Map<LinksEnum, UUID> links;
    private int row;
    private UUID id;

    public CellDTO() {
    }

    public CellDTO(Cell cell) {
        this.row = cell.getRow();
        this.id = cell.getId();
        links = new HashMap<>();
        if(cell.getLeft(CheckersColor.RED)!= null)
        links.put(LinksEnum.LU, cell.getLeft(CheckersColor.RED).getId());
        if(cell.getRight(CheckersColor.RED) != null)
        links.put(LinksEnum.RU, cell.getRight(CheckersColor.RED).getId());
        if(cell.getLeft(CheckersColor.WHITE) != null)
        links.put(LinksEnum.LD, cell.getLeft(CheckersColor.WHITE).getId());
        if(cell.getRight(CheckersColor.WHITE) != null)
        links.put(LinksEnum.RD, cell.getRight(CheckersColor.WHITE).getId());
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Map<LinksEnum, UUID> getLinks() {
        return links;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CellDTO{" +
                "links=" + links +
                ", row=" + row +
                ", id=" + id +
                '}';
    }
}

package sample.serialization;
import sample.Game.King;
import sample.Game.Cell;
import sample.Game.Checker;
import sample.Game.CheckersColor;
import sample.Game.CheckersDeck;
import sample.drawable.DrawableCell;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Converter {


    public CheckerDeckDTO dtoFromReal(CheckersDeck cd) {
        CheckerDeckDTO res = new CheckerDeckDTO();

        Cell[] lastRowArr = cd.getLastRowArr().clone();

        for (int i = CheckersDeck.ROWS - 1; i >= 0; i--) {
            if (i % 2 == 0) {
                for (int j = 0; j < lastRowArr.length; j++) {
                    CellDTO cell = new CellDTO(lastRowArr[j]);
                    res.getCells().add(cell);
                    if(lastRowArr[j].getChecker() != null) {
                        if(lastRowArr[j].getChecker() instanceof King)
                        res.getCheckers().add(new CheckerDTO(lastRowArr[j].getChecker(), true));
                        else    res.getCheckers().add(new CheckerDTO(lastRowArr[j].getChecker(), false));
                    }
                    lastRowArr[j] = lastRowArr[j].getRight(CheckersColor.RED);
                }

            } else {
                for (int j = 0; j < lastRowArr.length; j++) {
                    CellDTO cell = new CellDTO(lastRowArr[j]);
                    res.getCells().add(cell);
                    if(lastRowArr[j].getChecker() != null) {
                        if(lastRowArr[j].getChecker() instanceof King)
                            res.getCheckers().add(new CheckerDTO(lastRowArr[j].getChecker(), true));
                        else    res.getCheckers().add(new CheckerDTO(lastRowArr[j].getChecker(), false));
                    }
                    lastRowArr[j] = lastRowArr[j].getLeft(CheckersColor.RED);
                }
            }

        }
        res.setMoveTurn(cd.getMoveTurn());
        res.setPl1(cd.getPl1());
        res.setPl2(cd.getPl2());
        for (int i = 0; i < cd.getLastRowArr().length; i++) {
            res.getLastRowArr()[i] = res.foundCellDTOById(cd.getLastRowArr()[i].getId());
        }
        return res;
    }

    public CheckersDeck realFromDto(CheckerDeckDTO deckDTO) {
        List<Cell> cells = new ArrayList<>();
        CheckersDeck cd = new CheckersDeck();
        for(CellDTO c : deckDTO.getCells()) {
            Cell ce = new Cell(c);
            cells.add(ce);
        }
        for(Cell cell : cells) {
            cell.getLinkByColor(CheckersColor.WHITE).setLeft(foundCellByID(cells, cell.getLinksId().get(LinksEnum.LD)));
            cell.getLinkByColor(CheckersColor.WHITE).setRigth(foundCellByID(cells, cell.getLinksId().get(LinksEnum.RD)));
            cell.getLinkByColor(CheckersColor.RED).setLeft(foundCellByID(cells, cell.getLinksId().get(LinksEnum.LU)));
            cell.getLinkByColor(CheckersColor.RED).setRigth(foundCellByID(cells, cell.getLinksId().get(LinksEnum.RU)));
        }
        for(CheckerDTO checkerDTO : deckDTO.getCheckers()) {
            if(checkerDTO.isKing())
            cd.getCheckerMap().get(checkerDTO.getColor()).add(new King(foundCellByID(cells, checkerDTO.getCellId()), checkerDTO.getColor()));
            else   cd.getCheckerMap().get(checkerDTO.getColor()).add(new Checker(foundCellByID(cells, checkerDTO.getCellId()), checkerDTO.getColor()));
        }
        for (int i = 0; i < deckDTO.getLastRowArr().length; i++) {
            cd.getLastRowArr()[i] = foundCellByID(cells, deckDTO.getLastRowArr()[i].getId());
        }
        cd.setMoveTurn(deckDTO.getMoveTurn());
        cd.setPl1(deckDTO.getPl1());
        cd.setPl2(deckDTO.getPl2());
        return cd;
    }

    private Cell foundCellByID(List<Cell> cells, UUID id) {
        for(Cell cell : cells) {
            if (cell.getId().equals(id))
                return cell;
        }
        return null;
    }


}

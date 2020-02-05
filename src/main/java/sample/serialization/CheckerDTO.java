package sample.serialization;
import sample.Game.CheckersColor;

import java.util.UUID;
import sample.Game.Checker;

public class CheckerDTO {

    private CheckersColor color;
    private UUID cellId;
    private boolean king;
    public CheckerDTO(CheckersColor color) {
        this.color = color;
    }
    public CheckerDTO(Checker checker, boolean king) {
        this.color = checker.getColor();
        this.cellId = checker.getCell().getId();
        this.king = king;
    }
    public CheckersColor getColor() {
        return color;
    }

    public UUID getCellId() {
        return cellId;
    }

    public boolean isKing() {
        return king;
    }

    @Override
    public String toString() {
        return "CheckerDTO{" +
                "color=" + color +
                '}';
    }

    public CheckerDTO() {
    }
}

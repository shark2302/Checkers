package sample.command;

public interface Command {

    void doCommand();
    void undoCommand();
    String getMoveTurnPlayerName();
    boolean isBecomeKing();
}

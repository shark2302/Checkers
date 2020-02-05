package sample.command;



import javafx.scene.control.TextArea;
import sample.command.Command;


import java.util.Stack;

public class Invoker {
    private TextArea text;
    private Stack<Command> commands;
    private String lastMovePlayerName;

    public Invoker(Stack<Command> commands, TextArea text) {
        this.commands = commands;
        this.text = text;
    }

    public void execute() {
        lastMovePlayerName = commands.peek().getMoveTurnPlayerName();
        commands.peek().doCommand();
        if(commands.peek() instanceof HitCommand) {
            if(((HitCommand) commands.peek()).getHittedChecker().size() == 1)
            text.appendText("\n" + lastMovePlayerName +
                    " убивает " + ((HitCommand) commands.peek()).getHittedChecker().size() + " шашку");
            else
                text.appendText("\n" + lastMovePlayerName +
                        " убивает " + ((HitCommand) commands.peek()).getHittedChecker().size() + " шашки");

        }
        if(commands.peek().isBecomeKing())
            text.appendText("\nШашка игрока " + lastMovePlayerName + " стала дамкой");
        text.appendText("\nХодит " + commands.peek().getMoveTurnPlayerName());
     }


    public void getBack() {
        text.appendText("\nВерунлись на ход назад. Ходит " + lastMovePlayerName);

        if (commands.peek() instanceof HitCommand) {
            if(((HitCommand) commands.peek()).getHittedChecker().size() == 1)
                text.appendText("\nВосстановлена " +
                    ((HitCommand) commands.peek()).getHittedChecker().size() + " шашка игрока " + commands.peek().getMoveTurnPlayerName());
            else
                text.appendText("\nВосстановлены " +
                    ((HitCommand) commands.peek()).getHittedChecker().size() + " шашки игрока " + commands.peek().getMoveTurnPlayerName());
        }
        if(commands.peek().isBecomeKing())
            text.appendText("\nШашка игрока " + lastMovePlayerName + " больше не дамка");
        lastMovePlayerName = commands.peek().getMoveTurnPlayerName();
        commands.pop().undoCommand();

    }

    public Stack<Command> getCommands() {
        return commands;
    }
}

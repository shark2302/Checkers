package sample.threads;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import sample.Game.CheckersDeck;
import sample.command.Invoker;
import sample.drawable.Drawer;
import sample.players.Bot;
import sample.players.Player;

public class MoveThread extends Thread {

    private Player player;
    private CheckersDeck cd;
    private Invoker invoker;
    private Drawer drawer;
    private GraphicsContext gc;

    public MoveThread(Player player, CheckersDeck cd, Invoker invoker, Drawer drawer, GraphicsContext gc) {
        this.player = player;
        this.cd = cd;
        this.invoker = invoker;
        this.drawer = drawer;
        this.gc = gc;
    }

    @Override
    public void run() {
        player.getCurrentCommand(cd);
        if (!invoker.getCommands().isEmpty() && invoker.getCommands().peek() != null)
            invoker.execute();

        drawer.draw(gc);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




    }
}

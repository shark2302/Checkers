package sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import org.json.simple.JSONObject;
import sample.Game.CheckersDeck;
import sample.command.Invoker;
import sample.drawable.Drawer;
import sample.players.Bot;
import sample.players.Player;
import sample.exceptions.*;
import sample.Game.CheckersColor;
import sample.serialization.CheckerDeckDTO;
import sample.serialization.Converter;
import sample.threads.MoveThread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;


public class Controller implements Initializable {

    public TextField secondPlayer;
    public TextField firstPlayer;

    public RadioButton rb2;
    public AnchorPane textAnchor;
    public TextArea textArea;
    public AnchorPane gameAnchor;
    public Button saveBtn;
    private FileChooser fileChooser;
    private Gson json;

    @FXML
    Canvas canvas;
    private Player pl1, pl2;
    private CheckersDeck cd;
    private Invoker invoker;

    private Drawer drawer;
    private static final String WRONG_MOVE = "Вы не можете сюда пойти!";
    private static final String MUST_HIT = "Эта шашка должна бить";
    private static final String NOT_AVAILIBLE_PICK = "Вы не можете выбрать эту шашку! Либо вы должны бить другой шашкой, либо пытаетесь выбрать шашку соперника";
    private static final String WRONG_CLICK = "На этой клетке нет шашки";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        json = new Gson();
        fileChooser = createFileChooserWithFilter("Text files", "*.txt");
        canvas.requestFocus();
        canvas.setFocusTraversable(true);
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            if (event.getButton() == MouseButton.PRIMARY)
                try {
                    cd.pickChecker(drawer.foundPickedCell(event.getX(), event.getY()));
                } catch (NotAvailiblePickException e) {
                    textArea.appendText("\n" + NOT_AVAILIBLE_PICK);
                } catch (ClickedException e) {
                    textArea.appendText("\n" + WRONG_CLICK);
                }
            drawer.draw(canvas.getGraphicsContext2D());
            if (event.getButton() == MouseButton.SECONDARY) {
                try {

                    cd.getCurrentCommand(drawer.foundPickedCell(event.getX(), event.getY()));
                    if (!invoker.getCommands().isEmpty() && invoker.getCommands().peek() != null)
                        invoker.execute();
                } catch (WrongMoveException e) {
                    textArea.appendText("\n" + WRONG_MOVE);

                } catch (MustHitException e) {
                    textArea.appendText("\n" + MUST_HIT);
                }
                drawer.draw(canvas.getGraphicsContext2D());

                if (pl2 instanceof Bot) {
                    pl2.getCurrentCommand(cd);
                    invoker.execute();
                    drawer.draw(canvas.getGraphicsContext2D());
                    //textArea.setText(textArea.getText() + "\nХодит " + cd.getMoveTurn().getName());
                }
                if(cd.isWinner()) {
                    Alert alert = createInformationAlert("ИГРА ОКОНЧЕНА");
                    alert.showAndWait();
                    if(!alert.isShowing())
                        System.exit(1);
                }
            }
        });
        canvas.setOnKeyPressed(event -> {
            System.out.println(1);
            if (event.getCode() == KeyCode.ENTER && !invoker.getCommands().isEmpty()) {
                if (!invoker.getCommands().isEmpty() && invoker.getCommands().peek() != null ) {
                    invoker.getBack();
                    drawer.draw(canvas.getGraphicsContext2D());
                }
            }
        });
    }

    @FXML
    public void game() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.ORANGE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        pl1 = new Player(firstPlayer.getText(), CheckersColor.WHITE);
        if (rb2.isSelected())
            pl2 = new Bot(secondPlayer.getText(), CheckersColor.RED);
        else pl2 = new Player(secondPlayer.getText(), CheckersColor.RED);
        cd = new CheckersDeck(pl1, pl2);
        invoker = new Invoker(cd.getCommands(), textArea);
        drawer = new Drawer(gc, cd, canvas.getHeight());
        gameAnchor.setVisible(false);
        rb2.setVisible(false);
        firstPlayer.setVisible(false);
        secondPlayer.setVisible(false);
        textAnchor.setVisible(true);
        textArea.setVisible(true);
        saveBtn.setVisible(true);
        drawer.draw(gc);
        textArea.setText("Игра началась");
        textArea.appendText("\nХодит " + cd.getMoveTurn().getName());
    }




    private Alert createInformationAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setContentText(text);
        return alert;
    }


    public void botBattle(ActionEvent actionEvent) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.ORANGE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        pl1 = new Bot(firstPlayer.getText(), CheckersColor.WHITE);
        pl2 = new Bot(secondPlayer.getText(), CheckersColor.RED);
        cd = new CheckersDeck(pl1, pl2);
        invoker = new Invoker(cd.getCommands(), textArea);
        gameAnchor.setVisible(false);
        rb2.setVisible(false);
        firstPlayer.setVisible(false);
        secondPlayer.setVisible(false);
        textAnchor.setVisible(true);
        textArea.setVisible(true);
        saveBtn.setVisible(true);
        textArea.setText("Игра началась");
        textArea.appendText("\nХодит " + cd.getMoveTurn().getName());
        drawer = new Drawer(gc, cd, canvas.getHeight());
        drawer.draw(gc);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!cd.isWinner()) {

                    Thread th1 = new MoveThread(pl1, cd, invoker, drawer, gc);
                    th1.start();
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Thread th2 = new MoveThread(pl2, cd, invoker, drawer, gc);
                    th2.start();
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

        });
        thread.start();
    }



    public void load(ActionEvent actionEvent) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.ORANGE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Converter con = new Converter();
        ObjectMapper mapper = new ObjectMapper();

       // CheckerDeckDTO c = json.fromJson(chooseFile(), CheckerDeckDTO.class);
        try {
           CheckerDeckDTO c =  mapper.readValue(readLineFromFile(), CheckerDeckDTO.class);
            cd = con.realFromDto(c);
            System.out.println(cd == null);
        } catch (IOException e) {
            e.printStackTrace();
        }

       //
        //chooseFile();
        invoker = new Invoker(cd.getCommands(), textArea);
        drawer = new Drawer(gc, cd, canvas.getHeight());
        gameAnchor.setVisible(false);
        rb2.setVisible(false);
        firstPlayer.setVisible(false);
        secondPlayer.setVisible(false);
        textAnchor.setVisible(true);
        textArea.setVisible(true);
        saveBtn.setVisible(true);
        drawer.draw(gc);
        textArea.setText("Игра была загружена");
        textArea.appendText("\nХодит " + cd.getMoveTurn().getName());
        if(cd.getPl2() instanceof Bot && cd.getMoveTurn().getColor() == cd.getPl2().getColor()){
            cd.getPl2().getCurrentCommand(cd);
            invoker.execute();
        }

    }

    public void save(ActionEvent actionEvent) {
        File s = new File(fileChooser.showSaveDialog(null).getAbsolutePath());
        ObjectMapper mapper = new ObjectMapper();
        Converter conv = new Converter();
        try {
            FileWriter fileWriter = new FileWriter(s);
            mapper.writeValue(s, conv.dtoFromReal(cd));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String chooseFile() {
        try {
            return fileChooser.showOpenDialog(null).getAbsolutePath();
            //return readLineFromFile(path);
             //cd = conv.realFromDto(json.fromJson(fileString, CheckerDeckDTO.class));
        } catch (Exception e) {

            return null;
        }
    }
    public FileChooser createFileChooserWithFilter(String fileTypesGroup,
                                                   String fileTypes){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(fileTypesGroup,fileTypes));

        return fileChooser;
    }

    public File readLineFromFile ()
             {

        return new File(chooseFile());


    }
}

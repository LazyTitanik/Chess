package cz.cvut.fel.pjv.GUI;

import cz.cvut.fel.pjv.game.Board;
import cz.cvut.fel.pjv.game.Game;
import cz.cvut.fel.pjv.players.*;
import cz.cvut.fel.pjv.tools.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.scene.Group;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class GraphicsOfGame {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static boolean whitePlayerTurn;
    private static boolean blackPlayerTurn;
    private static Board board;
    private static List<ArrayList<Field>> fields;
    private static Field chosenField;
    private static Player player1;
    private static Player player2;
    private static Language language;
    private static Game game;

    private GraphicsOfGame(){}

    /**
     * @return Scene of the game
     */
    public static Scene generateGameScene(Game newGame, Language lang, Stage stage){
        game = newGame;
        language = lang;
        board = newGame.getBoard();
        player1 = newGame.getPlayer1();
        player2 = newGame.getPlayer2();
        BorderPane result = new BorderPane();
        javafx.scene.image.Image image = new Image(GraphicsOfGame.class.getResourceAsStream("/Main_picture.jpeg"));
        ImageView img = new ImageView(image);
        img.setFitHeight(600);
        img.setFitWidth(900);
        result.getChildren().add(img);
        fields = new ArrayList<>();
        VBox col = BoardView.generateBoard(fields, board);
        for (ArrayList<Field> column : fields){
            for (Field tempField : column){
                tempField.setOnMouseClicked(event-> mouseClick(board, tempField.getPosition(), stage));
            }
        }

        col.setAlignment(Pos.BASELINE_RIGHT);
        Button giveUp = new Button(ButtonNames.getName(language, Name.GIVE_UP));
        giveUp.setOnMouseClicked(event-> {
            String tmp = GraphicsOfGame.class.getResource("/Never_Give_Up.mp4").toString();
            Media media = new Media(tmp);
            Stage stage1 = new Stage();

            MediaPlayer mediaPlayer = new MediaPlayer(media);

            MediaView mediaView = new MediaView(mediaPlayer);

            mediaPlayer.setAutoPlay(true);
            Group root = new Group();
            root.getChildren().add(mediaView);
            Scene scene = new Scene(root,500,400);
            stage1.setScene(scene);
            stage1.setTitle("Never Give Up!!!");
            stage1.show();
        });
        Button draw = new Button(ButtonNames.getName(language, Name.DRAW));
        VBox tools = new VBox(50);
        tools.setAlignment(Pos.CENTER_LEFT);
        tools.getChildren().addAll(giveUp, draw);
        result.setRight(tools);
        result.setLeft(col);
        col.setTranslateX(100);
        return new Scene(result, 900, 600);
    }

    static void mouseClick(Board board, Position pos, Stage stage){
        LOGGER.fine(((char)('a' + pos.getVertical()) )+ " " + (pos.getHorizontal()+1) + " has been pressed");
        if (board.getPiece(pos) != null && !fields.get(pos.getHorizontal()).get(pos.getVertical()).isPossibleMove()) {
            for (int i=0; i<8; i++){
                for (int j=0; j<8; j++){
                    fields.get(i).get(j).hideMove();
                }
            }
            chosenField = fields.get(pos.getHorizontal()).get(pos.getVertical());
            if ((board.getPiece(pos).getColour() == Colour.WHITE && whitePlayerTurn)||
            (board.getPiece(pos).getColour() == Colour.BLACK && blackPlayerTurn)){
                List<Move> moves = new ArrayList<>(board.getPiece(pos).getMoves(board, pos));
                for (Move move : moves){
                    fields.get(move.getEnd().getHorizontal()).get(move.getEnd().getVertical()).showMove(move.getMoveType());
                }
            }
        }else{
            if (fields.get(pos.getHorizontal()).get(pos.getVertical()).isPossibleMove()){
                Move madeMove = new Move(chosenField.getPosition(), pos,
                        fields.get(pos.getHorizontal()).get(pos.getVertical()).getType());
                if (board.getPiece(chosenField.getPosition()).getColour() == Colour.WHITE){
                    if (player1.getColour() == Colour.WHITE){
                        player1.setMove(madeMove);
                    } else{
                        player2.setMove(madeMove);
                    }
                }
                else{
                    if (player1.getColour() == Colour.BLACK){
                        player1.setMove(madeMove);
                    } else{
                        player2.setMove(madeMove);
                    }
                }
                whitePlayerTurn = !whitePlayerTurn;
                blackPlayerTurn = !blackPlayerTurn;
                updateBoard();
            }
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    fields.get(i).get(j).hideMove();
                }
            }
        }
        try{
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (Exception e){
            LOGGER.severe("GraphicsOfGame's thread has been interrupted while sleeping");
            Thread.currentThread().interrupt();
        }
        if (board.isCheck(Colour.WHITE) && board.isMate(Colour.WHITE)){
            showEnd(Colour.BLACK, stage);
        }
        else if (board.isCheck(Colour.BLACK) && board.isMate(Colour.BLACK)){
            showEnd(Colour.WHITE, stage);
        }
    }

    /**
     * Used by ThisPlayer class, to enable move for user
     */
    public static void setGettingMove(Player currentPlayer){
        if (currentPlayer.getColour() == Colour.WHITE){
            whitePlayerTurn = true;
            blackPlayerTurn = false;
        } else if(currentPlayer.getColour() == Colour.BLACK){
            whitePlayerTurn = false;
            blackPlayerTurn = true;
        }
        updateBoard();
    }

    private static void updateBoard(){
        for(int i=0; i<8; i++){
            for (int j=0; j<8; j++){
                fields.get(i).get(j).update(board);
            }
        }
    }

    private static void showEnd(Colour colour, Stage stage){
        GameOver.setGameOverMenu(language, stage, colour, game);
    }

}

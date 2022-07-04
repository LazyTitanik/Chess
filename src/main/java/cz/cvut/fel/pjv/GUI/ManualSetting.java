package cz.cvut.fel.pjv.GUI;

import cz.cvut.fel.pjv.game.Board;
import cz.cvut.fel.pjv.game.Game;
import cz.cvut.fel.pjv.pieces.*;
import cz.cvut.fel.pjv.players.*;
import cz.cvut.fel.pjv.tools.*;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ManualSetting {
    private static Board board;
    private static Player player1; // these are going to be used in foreseeable future
    private static Player player2;
    private static Piece chosenPiece;

    private ManualSetting(){}

    /**
     * @return Scene of manual setting
     */
    static Scene generateManualSettingScene(Language lang, Stage stage, Player p1, Player p2){
        BorderPane result = new BorderPane();
        player1 = p1;
        player2 = p2;
        board = new Board();
        board.setManualInit();
        chosenPiece = null;
        javafx.scene.image.Image image = new Image(GraphicsOfGame.class.getResourceAsStream("/Main_picture.jpeg"));
        ImageView img = new ImageView(image);
        img.setFitHeight(600);
        img.setFitWidth(900);
        result.getChildren().add(img);

        List<ArrayList<Field>> fields = new ArrayList<>();
        VBox checker = BoardView.generateBoard(fields, board);
        checker.setAlignment(Pos.BASELINE_LEFT);
        result.setLeft(checker);

        for(ArrayList<Field> col : fields){
            for(Field field : col){
                field.setOnMouseClicked(e->{
                    board.setPiece(chosenPiece, field.getPosition());
                    field.update(board);
                });
            }
        }

        VBox availableOptions = generateOptions();
        Button confirm = new Button(ButtonNames.getName(lang, Name.CONFIRM));
        confirm.setOnMouseClicked(e->{
            if (!board.isInitted()){
                // TODO: Show message
            }
            else{
                Game game = new Game();
                if (p1.getClass().equals(Bot.class)){
                    game.setYourGame(board, p1);
                }
                else {
                    game.setYourGame(board, p2);
                }
                stage.setScene(GraphicsOfGame.generateGameScene(game, lang, stage));
                game.start();
            }
        });
        availableOptions.getChildren().add(confirm);
        result.setRight(availableOptions);

        return new Scene(result, 900, 600);
    }

    private static VBox generateOptions(){
        int spacingH = 15;
        int spacingY = 20;
        HBox kings = new HBox();
        kings.setSpacing(spacingH);
        Field whiteKing = new Field(75, 75, King.class, Colour.WHITE);
        Field blackKing = new Field (75, 75, King.class, Colour.BLACK);
        Field delete = new Field(75, 75, null, Colour.WHITE);
        whiteKing.setOnMouseClicked(e -> choosePiece(King.class, Colour.WHITE));
        blackKing.setOnMouseClicked(e->choosePiece(King.class, Colour.BLACK));
        delete.setOnMouseClicked(e->choosePiece(null, Colour.WHITE));
        kings.getChildren().addAll(whiteKing, blackKing, delete);

        HBox queens = new HBox();
        queens.setSpacing(spacingH);
        Field whiteQueen = new Field(75, 75, Queen.class, Colour.WHITE);
        Field blackQueen = new Field (75, 75, Queen.class, Colour.BLACK);
        whiteQueen.setOnMouseClicked(e -> choosePiece(Queen.class, Colour.WHITE));
        blackQueen.setOnMouseClicked(e->choosePiece(Queen.class, Colour.BLACK));
        queens.getChildren().addAll(whiteQueen, blackQueen);

        HBox knights = new HBox();
        knights.setSpacing(spacingH); ///???????????
        Field whiteKnight = new Field(75, 75, Knight.class, Colour.WHITE);
        Field blackKnight = new Field (75, 75, Knight.class, Colour.BLACK);
        whiteKnight.setOnMouseClicked(e -> choosePiece(Knight.class, Colour.WHITE));
        blackKnight.setOnMouseClicked(e->choosePiece(Knight.class, Colour.BLACK));
        knights.getChildren().addAll(whiteKnight, blackKnight);

        HBox bishops = new HBox();
        bishops.setSpacing(spacingH);
        Field whiteBishop = new Field(75, 75, Bishop.class, Colour.WHITE);
        Field blackBishop = new Field (75, 75, Bishop.class, Colour.BLACK);
        whiteBishop.setOnMouseClicked(e -> choosePiece(Bishop.class, Colour.WHITE));
        blackBishop.setOnMouseClicked(e->choosePiece(Bishop.class, Colour.BLACK));
        bishops.getChildren().addAll(whiteBishop, blackBishop);

        HBox rooks = new HBox();
        rooks.setSpacing(spacingH);
        Field whiteRook = new Field(75, 75, Rook.class, Colour.WHITE);
        Field blackRook = new Field (75, 75, Rook.class, Colour.BLACK);
        whiteRook.setOnMouseClicked(e -> choosePiece(Rook.class, Colour.WHITE));
        blackRook.setOnMouseClicked(e->choosePiece(Rook.class, Colour.BLACK));
        rooks.getChildren().addAll(whiteRook, blackRook);

        HBox pawns = new HBox();
        pawns.setSpacing(spacingH);
        Field whitePawn = new Field(75, 75, Pawn.class, Colour.WHITE);
        Field blackPawn = new Field (75, 75, Pawn.class, Colour.BLACK);
        whitePawn.setOnMouseClicked(e -> choosePiece(Pawn.class, Colour.WHITE));
        blackPawn.setOnMouseClicked(e->choosePiece(Pawn.class, Colour.BLACK));
        pawns.getChildren().addAll(whitePawn, blackPawn);

        VBox options = new VBox();
        options.setSpacing(spacingY);
        options.getChildren().addAll(kings, queens, knights, bishops, rooks, pawns);
        return options;
    }

    private static void choosePiece(Class c, Colour colour){
        if( c == null){
            chosenPiece = null;
            return;
        }
        if (colour == Colour.WHITE) {
            if (c.equals(Pawn.class)) {
                chosenPiece = new Pawn(Colour.WHITE);
            } else if (c.equals(King.class)) {
                chosenPiece = new King(Colour.WHITE);
            } else if (c.equals(Rook.class)) {
                chosenPiece = new Rook(Colour.WHITE);
            } else if (c.equals(Knight.class)) {
                chosenPiece = new Knight(Colour.WHITE);
            } else if (c.equals(Bishop.class)) {
                chosenPiece = new Bishop(Colour.WHITE);
            } else if (c.equals(Queen.class)) {
                chosenPiece = new Queen(Colour.WHITE);
            }
        } else {
            if (c.equals(Pawn.class)) {
                chosenPiece = new Pawn(Colour.BLACK);
            } else if (c.equals(King.class)) {
                chosenPiece = new King(Colour.BLACK);
            } else if (c.equals(Rook.class)) {
                chosenPiece = new Rook(Colour.BLACK);
            } else if (c.equals(Knight.class)) {
                chosenPiece = new Knight(Colour.BLACK);
            } else if (c.equals(Bishop.class)) {
                chosenPiece = new Bishop(Colour.BLACK);
            } else if (c.equals(Queen.class)) {
                chosenPiece = new Queen(Colour.BLACK);
            }
        }
    }
}

package cz.cvut.fel.pjv.GUI;

import cz.cvut.fel.pjv.game.Board;
import cz.cvut.fel.pjv.game.Game;
import cz.cvut.fel.pjv.pieces.Piece;
import cz.cvut.fel.pjv.players.Bot;
import cz.cvut.fel.pjv.players.Player;
import cz.cvut.fel.pjv.tools.ButtonNames;
import cz.cvut.fel.pjv.tools.Language;
import cz.cvut.fel.pjv.tools.Name;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Watching {
    private static Board board;
    private static Piece chosenPiece;
    static Scene generateWatchingScene(Language lang, Stage stage, Game game) {
        BorderPane result = new BorderPane();
        board = game.getBoard();
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

        Button nextMove = new Button(ButtonNames.getName(lang, Name.NEXT_MOVE));
        nextMove.setOnMouseClicked(event->{
            game.makeMove();
            for(ArrayList<Field> col : fields){
                for(Field field : col){
                    field.update(board);
                }
            }
        });
        Button mainMenu = new Button(ButtonNames.getName(lang, Name.MAIN_MENU));
        mainMenu.setOnMouseClicked(event -> stage.setScene(Menu.generateMenu(stage)));

        VBox options = new VBox(nextMove, mainMenu);
        result.setRight(options);

        return new Scene(result, 900, 600);
    }
}

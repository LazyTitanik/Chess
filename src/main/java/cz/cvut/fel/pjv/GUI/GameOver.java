package cz.cvut.fel.pjv.GUI;

import cz.cvut.fel.pjv.game.Game;
import cz.cvut.fel.pjv.tools.ButtonNames;
import cz.cvut.fel.pjv.tools.Colour;
import cz.cvut.fel.pjv.tools.Language;
import cz.cvut.fel.pjv.tools.Name;
import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.TextField;

import java.io.IOException;


public class GameOver {
    private static boolean saveShown =false;
    private GameOver(){}

    /**
     * sets menu of ended game to the stage
     */
    public static void setGameOverMenu (Language lang, Stage stage, Colour colour, Game game){
        saveShown = false;
        BorderPane root = new BorderPane();

        Image image = new Image(Menu.class.getResourceAsStream("/Main_picture.jpeg"));
        ImageView img = new ImageView(image);
        img.setFitHeight(600);
        img.setFitWidth(900);
        root.getChildren().add(img);

        Rectangle background = new Rectangle(900,600, Color.LIGHTBLUE);
        background.setOpacity(0.4);
        root.getChildren().add(background);

        VBox menu = new VBox();
        menu.setSpacing(15);

        Label message;
        if (colour == Colour.WHITE) {
            message = new Label(ButtonNames.getName(lang, Name.GAME_OVER_WHITE));
        }
        else {
            message = new Label(ButtonNames.getName(lang, Name.GAME_OVER_BLACK));
        }
        message.setFont(new Font(40));
        message.setTextFill(Color.BLACK);
        message.setAlignment(Pos.CENTER);
        message.setTranslateX(250);
        menu.getChildren().add(message);

        MenuItem saveGame = new MenuItem(Name.SAVE_GAME, lang);
        menu.getChildren().add(saveGame);

        MenuItem mainMenu = new MenuItem(Name.MAIN_MENU, lang);
        menu.getChildren().add(mainMenu);

        saveGame.setOnMouseClicked(event->saveClick(lang, menu, game));
        root.setCenter(menu);

        mainMenu.setOnMouseClicked(event->stage.setScene(Menu.generateMenu(stage)));

        stage.setScene(new Scene(root, 900, 600));
    }

    private static class MenuItem extends StackPane {
        public  MenuItem(Name n, Language language){
            javafx.scene.shape.Rectangle bg = new javafx.scene.shape.Rectangle(200,20,Color.WHITE);
            bg.setOpacity(0.5);

            final Text text = new Text();
            text.setText(ButtonNames.getName(language, n));
            text.setFill(Color.WHITE);
            text.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD,14));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg,text);
            FillTransition st = new FillTransition(Duration.seconds(0.5),bg);
            setOnMouseEntered(event -> {
                st.setFromValue(Color.DARKGRAY);
                st.setToValue(Color.DARKGOLDENROD);
                st.setCycleCount(Animation.INDEFINITE);
                st.setAutoReverse(true);
                st.play();
            });
            setOnMouseExited(event -> {
                st.stop();
                bg.setFill(Color.WHITE);
            });
        }
    }

    private static void saveClick (Language lang, VBox menu, Game game){
        if (!saveShown){
            saveShown = true;
            HBox saveMenuItem = new HBox();

            Label example = new Label(ButtonNames.getName(lang, Name.DIRECTORY_NAME));
            example.setTextFill(Color.BLACK);
            example.setAlignment(Pos.CENTER);
            example.setTranslateX(350);

            TextField directory = new TextField();
            directory.setPromptText("D:\\\\My_games\\Chess\\My_favourite_game.txt");
            directory.setAlignment(Pos.CENTER);

            Label textInfo = new Label();
            textInfo.setTextFill(Color.BLACK);
            textInfo.setAlignment(Pos.CENTER);
            textInfo.setTranslateX(350);

            Button submit = new Button(ButtonNames.getName(lang, Name.CONFIRM));
            submit.setAlignment(Pos.BASELINE_RIGHT);
            submit.setOnMouseClicked(e->{
                if (directory.getText().isEmpty()){
                    textInfo.setText(ButtonNames.getName(lang, Name.ERROR_EMPTY_DIRECTORY));
                    return;
                }

                game.saveGame(directory.getText());

                textInfo.setText(ButtonNames.getName(lang, Name.GAME_SAVED));
            });
            saveMenuItem.getChildren().addAll(directory, submit);
            saveMenuItem.setAlignment(Pos.CENTER);
            menu.getChildren().addAll(example ,saveMenuItem, textInfo);
        }
    }

}

package cz.cvut.fel.pjv.GUI;

import cz.cvut.fel.pjv.game.Game;
import cz.cvut.fel.pjv.tools.ButtonNames;
import cz.cvut.fel.pjv.tools.Language;
import cz.cvut.fel.pjv.tools.Name;
import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class EnterFilename {
    static Scene generateNameEnteringScene(Language lang, Stage stage){
        Image image = new Image(Menu.class.getResourceAsStream("/Main_picture.jpeg"));
        ImageView img = new ImageView(image);
        img.setFitHeight(600);
        img.setFitWidth(900);
        BorderPane root = new BorderPane();
        root.getChildren().add(img);

        Rectangle background = new Rectangle(900,600, Color.LIGHTBLUE);
        background.setOpacity(0.4);
        root.getChildren().add(background);

        VBox menu = new VBox();
        menu.setSpacing(15);

        HBox loadMenuItem = new HBox();

        Label example = new Label(ButtonNames.getName(lang, Name.DIRECTORY_NAME));
        example.setTextFill(Color.BLACK);
        example.setAlignment(Pos.CENTER);

        TextField directory = new TextField();
        directory.setPromptText("D:\\\\My_games\\Chess\\My_favourite_game.txt");
        directory.setAlignment(Pos.CENTER);

        Label textInfo = new Label();
        textInfo.setTextFill(Color.BLACK);
        textInfo.setAlignment(Pos.CENTER);

        Button submit = new Button(ButtonNames.getName(lang, Name.CONFIRM));
        submit.setAlignment(Pos.BASELINE_RIGHT);
        submit.setOnMouseClicked(e->{
            if (directory.getText().isEmpty()){
                textInfo.setText(ButtonNames.getName(lang, Name.ERROR_EMPTY_DIRECTORY));
            }
            else{
                String fileName = directory.getText();
                Game game = new Game();
                if(game.setWatching(fileName)){
                    stage.setScene(Watching.generateWatchingScene(lang, stage, game));
                }
            }
        });
        loadMenuItem.getChildren().addAll(directory, submit);
        loadMenuItem.setAlignment(Pos.CENTER);

        MenuItem mainMenu = new MenuItem(Name.MAIN_MENU, lang);
        mainMenu.setOnMouseClicked(event->stage.setScene(Menu.generateMenu(stage)));
        menu.getChildren().addAll(mainMenu,example ,loadMenuItem, textInfo);
        menu.setTranslateX(50);
        menu.setTranslateY(100);
        root.setLeft(menu);

        return new Scene(root, 900, 600);
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
}

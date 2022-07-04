package cz.cvut.fel.pjv.GUI;

// Source: https://github.com/Kawun/tutorials/tree/master/tutorial20

import cz.cvut.fel.pjv.game.Game;
import cz.cvut.fel.pjv.players.Bot;
import cz.cvut.fel.pjv.players.Player;
import cz.cvut.fel.pjv.players.ThisPlayer;
import cz.cvut.fel.pjv.tools.ButtonNames;
import cz.cvut.fel.pjv.tools.Colour;
import cz.cvut.fel.pjv.tools.Language;
import cz.cvut.fel.pjv.tools.Name;
import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static cz.cvut.fel.pjv.GUI.EnterFilename.generateNameEnteringScene;
import static cz.cvut.fel.pjv.tools.Name.*;

public class Menu {
    private static List<SubMenu> subMenus;
    private static Language language;

    private Menu(){}

    /**
     * Generates the main menu scene
     */
    public static Scene generateMenu(Stage stage) {
        subMenus = new ArrayList<>();
        language = Language.ENGLISH;
        Pane root = new Pane();
        Image image = new Image(Menu.class.getResourceAsStream("/Main_picture.jpeg"));
        ImageView img = new ImageView(image);
        img.setFitHeight(600);
        img.setFitWidth(900);
        root.getChildren().add(img);

        MenuItem newGame = new MenuItem(NEW_GAME);
        MenuItem options = new MenuItem(SETTINGS);
        MenuItem exitGame = new MenuItem(EXIT);
        SubMenu mainMenu = new SubMenu(
                newGame,options,exitGame
        );
        subMenus.add(mainMenu);

        MenuItem languages = new MenuItem(LANGUAGE);
        MenuItem languageBack = new MenuItem(BACK);
        SubMenu optionsMenu = new SubMenu(
                languages, languageBack
        );
        subMenus.add(optionsMenu);

        MenuItem newGame1 = new MenuItem(ONE_PLAYER);
        MenuItem newGame2 = new MenuItem(TWO_PLAYERS);
        MenuItem newGame3 = new MenuItem(LOAD_GAME);
        MenuItem newGame4 = new MenuItem(BACK);
        SubMenu newGameMenu = new SubMenu(
                newGame1,newGame2,newGame3, newGame4
        );
        subMenus.add(newGameMenu);

        MenuItem onePlayer1 = new MenuItem(WHITE_DEFAULT);
        MenuItem onePlayer2 = new MenuItem(BLACK_DEFAULT);
        MenuItem onePlayer3 = new MenuItem(SET_UP);
        MenuItem onePlayerBack = new MenuItem( BACK);
        SubMenu onePlayerMenu = new SubMenu(
                onePlayer1, onePlayer2, onePlayer3, onePlayerBack
        );
        subMenus.add(onePlayerMenu);

        MenuItem twoPlayers1 = new MenuItem(DEFAULT);
        MenuItem twoPlayers2 = new MenuItem(SET_UP);
        MenuItem twoPlayersBack = new MenuItem(BACK);
        SubMenu twoPlayersMenu = new SubMenu(
                twoPlayers1, twoPlayers2, twoPlayersBack
        );
        subMenus.add(twoPlayersMenu);

        MenuItem language1 = new MenuItem(ENGLISH_LANGUAGE);
        MenuItem language2 = new MenuItem(CZECH_LANGUAGE);
        MenuItem language3 = new MenuItem(RUSSIAN_LANGUAGE);
        SubMenu languageMenu = new SubMenu(
                language1, language2, language3
        );
        subMenus.add(languageMenu);

        MenuBox menuBox = new MenuBox(mainMenu);

        newGame.setOnMouseClicked(event->menuBox.setSubMenu(newGameMenu));
        options.setOnMouseClicked(event->menuBox.setSubMenu(optionsMenu));
        exitGame.setOnMouseClicked(event-> System.exit(0));

        languages.setOnMouseClicked(event->menuBox.setSubMenu(languageMenu));
        languageBack.setOnMouseClicked(event->menuBox.setSubMenu(mainMenu));

        language1.setOnMouseClicked(event -> {
            changeLanguage(Language.ENGLISH);
            menuBox.setSubMenu(optionsMenu);
        });
        language2.setOnMouseClicked(event -> {
            changeLanguage(Language.CZECH);
            menuBox.setSubMenu(optionsMenu);
        });
        language3.setOnMouseClicked(event -> {
            changeLanguage(Language.RUSSIAN);
            menuBox.setSubMenu(optionsMenu);
        });

        newGame1.setOnMouseClicked(event->menuBox.setSubMenu(onePlayerMenu));
        newGame2.setOnMouseClicked(event->menuBox.setSubMenu(twoPlayersMenu));
        newGame3.setOnMouseClicked(event->stage.setScene(generateNameEnteringScene(language ,stage)));
        newGame4.setOnMouseClicked(event->menuBox.setSubMenu(mainMenu));

        onePlayer1.setOnMouseClicked(event->startNewGame(stage, Bot.class, Colour.BLACK));
        onePlayer2.setOnMouseClicked(event->startNewGame(stage, Bot.class, Colour.WHITE));
        onePlayer3.setOnMouseClicked(event->{
            Player p1 = new ThisPlayer(Colour.WHITE);
            Player p2 = new Bot(Colour.BLACK);
            stage.setScene(ManualSetting.generateManualSettingScene(language, stage, p1, p2));
        });
        onePlayerBack.setOnMouseClicked(event->menuBox.setSubMenu(newGameMenu));

        twoPlayers1.setOnMouseClicked(event -> startNewGame(stage, ThisPlayer.class, Colour.BLACK));
        twoPlayers2.setOnMouseClicked(event -> {
            Player p1 = new ThisPlayer(Colour.WHITE);
            Player p2 = new ThisPlayer(Colour.BLACK);
            stage.setScene(ManualSetting.generateManualSettingScene(language, stage, p1, p2));
        });
        twoPlayersBack.setOnMouseClicked(event->menuBox.setSubMenu(newGameMenu));

        root.getChildren().addAll(menuBox);

        Scene scene = new Scene(root,900,600);
        scene.setOnKeyPressed(event -> {
            FadeTransition ft = new FadeTransition(Duration.seconds(1), menuBox);
            if (!menuBox.isVisible()) {
                ft.setFromValue(0);
                ft.setToValue(1);
                ft.play();
                menuBox.setVisible(true);
            } else {
                ft.setFromValue(1);
                ft.setToValue(0);
                ft.setOnFinished(evt -> menuBox.setVisible(false));
                ft.play();

            }
        });
        return scene;
    }

    private static class MenuItem extends StackPane{
        private final Text text;
        private final Name name;
        public  MenuItem(Name n){
            name = n;
            Rectangle bg = new Rectangle(200,20,Color.WHITE);
            bg.setOpacity(0.5);

            text = new Text();
            text.setText(ButtonNames.getName(language, name));
            text.setFill(Color.WHITE);
            text.setFont(Font.font("Arial",FontWeight.BOLD,14));

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

        public void changeText(Language lang) {
            this.text.setText(ButtonNames.getName(lang, name));
            text.setFill(Color.WHITE);
            text.setFont(Font.font("Arial",FontWeight.BOLD,14));
        }
    }

    private static class MenuBox extends Pane{
        static SubMenu subMenu;
        public MenuBox(SubMenu subMenu){
            MenuBox.subMenu = subMenu;

            setVisible(false);
            Rectangle bg = new Rectangle(900,600,Color.LIGHTBLUE);
            bg.setOpacity(0.4);
            getChildren().addAll(bg, subMenu);
        }
        public void setSubMenu(SubMenu subMenu){
            getChildren().remove(MenuBox.subMenu);
            MenuBox.subMenu = subMenu;
            getChildren().add(MenuBox.subMenu);
        }
    }

    private static class SubMenu extends VBox{
        List<MenuItem> menuItems;
        public SubMenu(MenuItem...items){
            setSpacing(15);
            setTranslateY(100);
            setTranslateX(50);
            menuItems = new ArrayList<>();
            for(MenuItem item : items){
                getChildren().addAll(item);
                menuItems.add(item);
            }
        }
    }

    private static void startNewGame(Stage stage, Class playerClass, Colour c){
        Player secondPlayer;
        if (playerClass.equals(ThisPlayer.class)){
            secondPlayer = new ThisPlayer(c);
        } else if(playerClass.equals(Bot.class)){
            secondPlayer = new Bot(c);
        }
        else {
            return;
        }
        Game game = new Game();
        game.setDefault(secondPlayer);
        stage.setScene(GraphicsOfGame.generateGameScene(game, language, stage));
        game.start();
    }

    private static void changeLanguage(Language lang){
        if (lang == language){
            return;
        }
        for (SubMenu menu : subMenus){
            for (MenuItem item : menu.menuItems){
                item.changeText(lang);
            }
        }
        language = lang;
    }
}

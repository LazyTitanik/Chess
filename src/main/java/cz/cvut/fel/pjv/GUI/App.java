package cz.cvut.fel.pjv.GUI;

import cz.cvut.fel.pjv.tools.ButtonNames;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage){
        ButtonNames.init();
        primaryStage.setScene(Menu.generateMenu(primaryStage));
        primaryStage.setOnCloseRequest( e -> System.exit(0));
        primaryStage.show();
    }

    public static void main(String[] args){
        launch();
    }
}

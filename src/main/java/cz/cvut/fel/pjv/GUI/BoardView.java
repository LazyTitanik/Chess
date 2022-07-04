package cz.cvut.fel.pjv.GUI;

import cz.cvut.fel.pjv.game.Board;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class BoardView {

    private BoardView(){}

    /**
     * @param fields all fields are being written here
     * @param board required to set fields
     * @return VBox, which contains Fields according to real places (a1 is below left, h8 is up right)
     */
    static VBox generateBoard(List<ArrayList<Field>> fields, Board board){
        // 1st initing the array
        for (int i=0; i<8; i++){
            ArrayList<Field> temp = new ArrayList<>();
            for (int j=0; j<8; j++){
                temp.add(null);
            }
            fields.add(temp);
        }

        // 2nd filling the array
        VBox col = new VBox(0);
        for (int i=7; i>=0; i--){
            HBox tempBox = new HBox(0);
            for (int j=0; j<8;j++){
                Field tempField = new Field(75, 75, j, i, board);
                tempBox.getChildren().add(tempField);
                fields.get(i).set(j, tempField);
            }
            col.getChildren().addAll(tempBox);
        }
        return col;
    }
}

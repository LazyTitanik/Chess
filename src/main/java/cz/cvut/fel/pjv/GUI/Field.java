package cz.cvut.fel.pjv.GUI;

import cz.cvut.fel.pjv.game.Board;
import cz.cvut.fel.pjv.pieces.*;
import cz.cvut.fel.pjv.tools.Colour;
import cz.cvut.fel.pjv.tools.MoveType;
import cz.cvut.fel.pjv.tools.Position;
import javafx.animation.FillTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Objects;
import java.util.logging.*;

public class Field extends Pane {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final Position position;
    private final Circle circle;
    private final Rectangle background;
    private final int width;
    private final int height;
    private boolean possibleMove;
    private ImageView piece;
    private MoveType type;
    private static final String WHITE_KING = "/Pieces/WhiteKing.png";
    private static final String WHITE_KNIGHT = "/Pieces/WhiteKnight.png";
    private static final String WHITE_PAWN = "/Pieces/WhitePawn.png";
    private static final String WHITE_QUEEN = "/Pieces/WhiteQueen.png";
    private static final String WHITE_ROOK = "/Pieces/WhiteRook.png";
    private static final String WHITE_BISHOP = "/Pieces/WhiteBishop.png";

    private static final String BLACK_KING = "/Pieces/BlackKing.png";
    private static final String BLACK_KNIGHT = "/Pieces/BlackKnight.png";
    private static final String BLACK_PAWN = "/Pieces/BlackPawn.png";
    private static final String BLACK_QUEEN = "/Pieces/BlackQueen.png";
    private static final String BLACK_ROOK = "/Pieces/BlackRook.png";
    private static final String BLACK_BISHOP = "/Pieces/BlackBishop.png";

    /**
     * This constructor is used to make new field without board
     * (E.g. if you are using Field class as button for choosing a piece
     * in manual setting)
     */
    public Field(int width, int height, Class pieceClass, Colour colour){
        circle = new Circle(0); // just to please IDE
        position = new Position(8, 8);
        this.width = width;
        this.height = height;
        background = new Rectangle(height, width);
        background.setFill(Color.BEIGE);
        piece= null;
        if(pieceClass != null){
            piece = new ImageView();
            loadPiece(pieceClass, colour);
        }
        FillTransition st = new FillTransition(Duration.seconds(0.05),background);
        setOnMouseEntered(event -> {
            st.setFromValue(Color.BEIGE);
            st.setToValue(Color.SANDYBROWN);
            st.setCycleCount(1);
            st.setAutoReverse(false);
            st.play();
        });
        setOnMouseExited(event -> {
            st.stop();
            background.setFill(Color.BEIGE);
        });
        if(piece != null){
            getChildren().addAll(background, piece);
        }
        else{
            getChildren().add(background);
        }
    }

    /**
     * Creates a field, that is binded to a certain position on board
     */
    public Field(int width, int height, int vertical, int horizontal, Board board) {
        possibleMove = false;
        circle = new Circle((double) height/4);
        circle.setCenterX((double) width/2);
        circle.setCenterY((double) height/2);
        circle.setFill(Color.GREY);
        circle.setOpacity(0);

        position = new Position(vertical, horizontal);
        this.width = width;
        this.height = height;
        Color c;
        background = new Rectangle(height, width);
        if ((vertical + horizontal) % 2 == 0) {
            c = Color.SADDLEBROWN;
            background.setFill(c);
        }
        else{
            c = Color.BEIGE;
            background.setFill(c);
        }
        FillTransition st = new FillTransition(Duration.seconds(0.05),background);
        setOnMouseEntered(event -> {
            st.setFromValue(c);
            st.setToValue(Color.SANDYBROWN);
            st.setCycleCount(1);
            st.setAutoReverse(false);
            st.play();
        });
        setOnMouseExited(event -> {
            st.stop();
            background.setFill(c);
        });
        piece = new ImageView();
        if (board.getPiece(position) != null) {
            loadPiece(board.getPiece(position).getClass(), board.getPiece(position).getColour());
        } else {
            piece.setOpacity(0);
        }

        getChildren().addAll(background, piece, circle);
    }

    /**
     * Updates the Field according to current state of board
     */
    public void update(Board board){
        if (board.getPiece(position) != null) {
            if (board.getPiece(position).getColour() == Colour.WHITE) {
                if (board.getPiece(position).getClass() == Pawn.class) {
                    piece.setImage(new Image(getClass().getResourceAsStream(WHITE_PAWN)));
                } else if (board.getPiece(position).getClass() == King.class) {
                    piece.setImage(new Image(getClass().getResourceAsStream(WHITE_KING)));
                } else if (board.getPiece(position).getClass() == Rook.class) {
                    piece.setImage(new Image(getClass().getResourceAsStream(WHITE_ROOK)));
                }else if (board.getPiece(position).getClass() == Knight.class) {
                    piece.setImage(new Image(getClass().getResourceAsStream(WHITE_KNIGHT)));
                }else if (board.getPiece(position).getClass() == Bishop.class) {
                    piece.setImage(new Image(getClass().getResourceAsStream(WHITE_BISHOP)));
                }else if (board.getPiece(position).getClass() == Queen.class) {
                    piece.setImage(new Image(getClass().getResourceAsStream(WHITE_QUEEN)));
                }
            }
            else if(board.getPiece(position).getColour() == Colour.BLACK){
                if (board.getPiece(position).getClass() == Pawn.class) {
                    piece.setImage(new Image(getClass().getResourceAsStream(BLACK_PAWN)));
                } else if (board.getPiece(position).getClass() == King.class) {
                    piece.setImage(new Image(getClass().getResourceAsStream(BLACK_KING)));
                } else if (board.getPiece(position).getClass() == Rook.class) {
                    piece.setImage(new Image(getClass().getResourceAsStream(BLACK_ROOK)));
                }else if (board.getPiece(position).getClass() == Knight.class) {
                    piece.setImage(new Image(getClass().getResourceAsStream(BLACK_KNIGHT)));
                }else if (board.getPiece(position).getClass() == Bishop.class) {
                    piece.setImage(new Image(getClass().getResourceAsStream(BLACK_BISHOP)));
                }else if (board.getPiece(position).getClass() == Queen.class) {
                    piece.setImage(new Image(getClass().getResourceAsStream(BLACK_QUEEN)));
                }
            }
            piece.setFitHeight(height);
            piece.setFitWidth(width);
            piece.setOpacity(1);
        } else {
            piece.setOpacity(0);
        }

    }

    private void loadPiece(Class pieceClass, Colour colour){
        if (colour == Colour.WHITE) {
            if (pieceClass.equals(Pawn.class) ){
                piece = (giveImagePattern(WHITE_PAWN));
            } else if (pieceClass.equals(King.class) ){
                piece = (giveImagePattern(WHITE_KING));
            } else if (pieceClass.equals(Rook.class) ){
                piece = (giveImagePattern(WHITE_ROOK));
            }else if (pieceClass.equals(Knight.class)) {
                piece = (giveImagePattern(WHITE_KNIGHT));
            }else if (pieceClass.equals(Bishop.class)) {
                piece = (giveImagePattern(WHITE_BISHOP));
            }else if (pieceClass.equals(Queen.class)) {
                piece = (giveImagePattern(WHITE_QUEEN));
            }
        }
        if (colour == Colour.BLACK) {
            if (pieceClass.equals(Pawn.class)) {
                piece = (giveImagePattern(BLACK_PAWN));
            } else if (pieceClass.equals(King.class) ){
                piece = (giveImagePattern(BLACK_KING));
            } else if (pieceClass.equals(Rook.class) ){
                piece = (giveImagePattern(BLACK_ROOK));
            }else if (pieceClass.equals(Knight.class)) {
                piece = (giveImagePattern(BLACK_KNIGHT));
            }else if (pieceClass.equals(Bishop.class)) {
                piece = (giveImagePattern(BLACK_BISHOP));
            }else if (pieceClass.equals(Queen.class)) {
                piece = (giveImagePattern(BLACK_QUEEN));
            }
        }
    }

    private ImageView giveImagePattern(String name){
        try {
            javafx.scene.image.Image image =
                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(name)));
            ImageView img = new ImageView(image);
            img.setFitHeight(height);
            img.setFitWidth(width);
            return (img);
        }
        catch (NullPointerException e){
            LOGGER.severe("File " + name + " could not be opened");
            System.exit(404);
            return null;
        }
    }

    /**
     * @return the position, which is binded to
     */
    public Position getPosition(){
        return position;
    }

    /**
     * Sets grey circle visible for user.
     */
    public void showMove(MoveType t){
        type = t;
        circle.setOpacity(0.3);
        possibleMove = true;
    }

    /**
     * Sets grey circle invisible for user.
     */
    public void hideMove(){
        type = MoveType.UNDEFINED;
        circle.setOpacity(0);
        possibleMove = false;
    }

    /**
     * @return true, if user sees grey circle on this field
     */
    public boolean isPossibleMove() {
        return possibleMove;
    }

    /**
     * @return type of move, that is available on this field
     */
    public MoveType getType(){
        return type;
    }
}

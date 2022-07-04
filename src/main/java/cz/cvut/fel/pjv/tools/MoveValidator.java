package cz.cvut.fel.pjv.tools;

import cz.cvut.fel.pjv.game.Board;
import cz.cvut.fel.pjv.pieces.*;
import org.junit.*;
import org.junit.jupiter.api.BeforeEach;

import static cz.cvut.fel.pjv.tools.Colour.BLACK;
import static cz.cvut.fel.pjv.tools.Colour.WHITE;
import static org.junit.jupiter.api.Assertions.*;

public class MoveValidator {
    private MoveValidator(){}

    /**
     * Verifies given move according to chess rules
     * @return true if move can be made
     */
    public static boolean verify(Board board, Move move){
        if (!move.getStart().isNormal() || !move.getEnd().isNormal()){
            return false;
        }
        boolean result = true;
        if (move.getMoveType() == MoveType.UNDEFINED){
            move.setMove(move.getStart(), move.getEnd(), MoveValidator.defineType(board, move));
        }
        /*
⠄⠄⠄⠄⠄⠄⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠄⠄
⠄⠄⠄⠄⠄⢠⣿⣿⣿⣿⣿⡿⠟⠛⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠇⠄⠄
⠄⠄⠄⠄⠄⠈⠙⠛⠉⠉⠁⠄⠄⢠⣾⣿⣿⡈⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠏⠄⠄⠄
⠄⠄⠄⠄⠄⠄⠄⠄⠄⣀⣀⣤⣾⣿⣿⣿⣿⣿⣄⠈⠙⠛⠿⠿⠿⠿⠿⠿⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠏⠄⠄⠄⠄
⠄⠄⠄⠄⠄⠄⠄⠄⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣦⣄⡀⠄⠄⠄⠄⠄⠄⠄⠉⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠋⠄⠄⠄⠄⠄
⠄⠄⠄⠄⠄⠄⠄⠄⠄⠙⢿⣿⡿⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣶⣶⣤⣄⡀⠄⠄⠄⠹⣿⣿⣿⣿⣿⣿⣿⠟⠁⠄⠄⠄⠄⠄⠄
⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠉⣁⡀⠄⠙⢻⣿⣿⣿⣿⣿⣿⣿⠿⣿⣿⣿⣿⣷⣤⡀⠄⣿⣿⣿⣿⡿⠋⠁⠄⠄⠄⠄⠄⠄⠄⠄
⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⢰⣿⠂⠄⢠⠄⣿⣿⣿⡿⠉⠄⠄⠄⠄⠄⠙⠿⣿⣿⣿⣧⣾⣿⣿⠿⢠⡶⠆⠄⠄⠄⠄⠄⠄⠄⠄
⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⢀⡜⢯⡀⠄⠄⢀⣿⣿⣿⠄⢠⣿⡏⠄⠄⠄⠄⠄⠙⣿⣿⣿⣿⣿⠋⡴⠁⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄
⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⢸⣿⣀⣀⣀⣠⣾⣿⣿⡇⠄⢸⣿⡀⠄⠋⠄⢀⣆⢀⣿⣿⣿⣿⣋⡄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄
⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠈⣿⣿⣿⣿⣿⣿⣿⣿⡅⠄⠄⠙⠳⠄⠄⠄⣋⣥⣿⣿⣿⣿⠟⠁⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄
⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⢻⣿⣿⣿⣿⣿⣿⣿⣿⣶⣤⣄⣀⣀⣀⣤⣿⣿⣿⣿⡿⠁⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄
⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠁⠄⠄⠄⠄⠄⠄⠄⡀⠄⠄⠄⠄⠄⠄⠄⠄
⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⣸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠹⢦⠈⠄⠄⠄⠄⠄⠄⡞⠄⠄⠄⠄⠄⠄⠄⠄
⠄⠄⠄⠄⠄⠄⠄⠄⠄⣠⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡟⠨⢹⣿⣿⣿⣿⣿⣿⠇⠄⠁⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄
⠄⠄⠄⠄⠄⠄⠄⠄⢴⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣶⣬⣿⣿⣿⣿⣿⠋⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄
⠄⠄⠄⠄⠄⠄⠄⠄⠈⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣿⣿⣿⣿⠃⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄
⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⢸⡏⠄⠄⠉⣿⣿⣿⣝⡻⣿⣿⣿⣿⡿⠃⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄
               NO SWITCHES?                                 */
        if (board.getPiece(move.getStart()).getClass().equals(Queen.class)) {
            result = MoveValidator.verifyQueen(board, move);
        }
        else if(board.getPiece(move.getStart()).getClass().equals(Bishop.class)){
            result = MoveValidator.verifyBishop(board, move);
        }
        else if(board.getPiece(move.getStart()).getClass().equals(Rook.class)){
            result = MoveValidator.verifyRook(board, move);
        }
        else if(board.getPiece(move.getStart()).getClass().equals(Knight.class)){
            result = MoveValidator.verifyKnight(board, move);
        }
        else if(board.getPiece(move.getStart()).getClass().equals(Pawn.class)){
            result = MoveValidator.verifyPawn(board, move);
        }
        else if(board.getPiece(move.getStart()).getClass().equals(King.class)){
            result = MoveValidator.verifyKing(board, move);
        }
        if (move.getMoveType() == MoveType.CAPTURE && board.getPiece(move.getEnd()).getClass().equals(King.class)){
            return false;
        }
        if (result){
            return isNotCheckDanger(board, move);
        }
        else{
            return false;
        }
    }

    private static MoveType defineType(Board board, Move move){
        if(Pawn.class.equals(board.getPiece(move.getStart()).getClass())){
            if (board.getEnPassant().equalsTo(move.getEnd())){
                return MoveType.EN_PASSANT;
            }
            if (move.getEnd().getHorizontal() == 7 || move.getEnd().getHorizontal() == 0){
                return MoveType.CHANGING;
            }
            if (board.getPiece(move.getEnd()) == null){
                return MoveType.REGULAR;
            }
            return MoveType.CAPTURE;
        }

        int v = move.getEnd().getVertical() - move.getStart().getVertical();
        if (King.class.equals(board.getPiece(move.getStart()).getClass()) && v != 1 && v != -1 && v != 0){
            return MoveType.CASTLING;
        }

        if(board.getPiece(move.getEnd()) == null){
            return MoveType.REGULAR;
        }
        else{
            return MoveType.CAPTURE;
        }
    }

    private static boolean isNotCheckDanger(Board board, Move move){
        Board newBoard = new Board();
        newBoard.init(board);
        newBoard.makeMove(move);
        return !newBoard.isCheck(board.getPiece(move.getStart()).getColour());
    }

    private static boolean verifyBishop(Board board, Move move){
        int v = move.getEnd().getVertical() - move.getStart().getVertical();
        int h = move.getEnd().getHorizontal() - move.getStart().getHorizontal();
        if (v != h && v != - h){
            return false;
        }
        v = setToBasic(v);
        h = setToBasic(h);
        return verifyWithVector(board, move, v, h);
    }

    private static boolean verifyRook(Board board, Move move){
        if (move.getStart().getVertical() != move.getEnd().getVertical() &&
            move.getStart().getHorizontal() != move.getEnd().getHorizontal()){
            return false;
        }
        int v = move.getEnd().getVertical() - move.getStart().getVertical();
        int h = move.getEnd().getHorizontal() - move.getStart().getHorizontal();
        v = setToBasic(v);
        h = setToBasic(h);
        return verifyWithVector(board, move, v, h);
    }

    private  static boolean verifyQueen(Board board, Move move){
        int v = move.getEnd().getVertical() - move.getStart().getVertical();
        int h = move.getEnd().getHorizontal() - move.getStart().getHorizontal();
        if (move.getStart().getVertical() != move.getEnd().getVertical() &&
                move.getStart().getHorizontal() != move.getEnd().getHorizontal() &&
                v != h && v != - h){
            return false;
        }
        v = setToBasic(v);
        h = setToBasic(h);
        return verifyWithVector(board, move, v, h);
    }

    private static boolean verifyPawn(Board board, Move move){
        int shift = -1;
        if (board.getPiece(move.getStart()).getColour() == WHITE){
            shift = 1;
        }
        Position temp = new Position(move.getStart().getVertical(),move.getStart().getHorizontal()+shift);

        if (move.getMoveType() == MoveType.REGULAR &&
                board.getPiece(temp) == null &&
                move.getEnd().equalsTo(temp)) {
            return true;
        }

        temp.setPosition(move.getStart().getVertical(),move.getStart().getHorizontal()+(shift*2));
        if (move.getMoveType() == MoveType.REGULAR &&
                board.getPiece(temp) == null &&
                move.getEnd().equalsTo(temp)) {
            return true;
        }

        if (move.getMoveType() == MoveType.CAPTURE &&
                board.getPiece(move.getEnd()) != null) {
            if (move.getEnd().getHorizontal() == move.getStart().getHorizontal() + shift &&
                    board.getPiece(move.getStart()).getColour() != board.getPiece(move.getEnd()).getColour() &&
                    (move.getEnd().getVertical() == move.getStart().getVertical() + 1 ||
                            move.getEnd().getVertical() == move.getStart().getVertical() - 1)) {
                return true;
            }
        }
        if (move.getMoveType() == MoveType.EN_PASSANT &&
            board.getEnPassant().equalsTo(move.getEnd()) &&
            move.getEnd().getHorizontal() == move.getStart().getHorizontal() + shift &&
            (move.getEnd().getVertical() == move.getStart().getVertical() + 1 ||
                 move.getEnd().getVertical() == move.getStart().getVertical() - 1)   ){
            return true;
        }
        boolean result = false;
        if (move.getMoveType() == MoveType.CHANGING &&
            move.getNewPiece() != null &&
                (move.getEnd().getHorizontal() == 0 ||
                 move.getEnd().getHorizontal() == 7)) {
            Move tempMove = new Move(move.getStart(), move.getEnd(), MoveType.REGULAR);
            result = verifyPawn(board, tempMove);
            tempMove.setMove(move.getStart(), move.getEnd(), MoveType.CAPTURE);
            result = result || verifyPawn(board, tempMove);
        }
        return result;
    }

    private static boolean verifyKnight(Board board, Move move) {
        int vectorV = move.getEnd().getVertical() - move.getStart().getVertical();
        int vectorH = move.getEnd().getHorizontal() - move.getStart().getHorizontal();
        Colour myColour = board.getPiece(move.getStart()).getColour();
        boolean result = false;
        for (int i = -2; i < 3; i += 4) {
            for (int j = -1; j < 2; j += 2) {
                result = result || (vectorV == i && vectorH == j) || (vectorV == j && vectorH == i);
            }
        }
        return result &&
                ((board.getPiece(move.getEnd()) != null &&
                        move.getMoveType() == MoveType.CAPTURE &&
                        board.getPiece(move.getEnd()).getColour() != myColour) ||
                (board.getPiece(move.getEnd()) == null &&
                        move.getMoveType() == MoveType.REGULAR));
    }

    private static boolean verifyKing(Board board, Move move){
        int vectorV = move.getEnd().getVertical() - move.getStart().getVertical();
        int vectorH = move.getEnd().getHorizontal() - move.getStart().getHorizontal();
        if (move.getMoveType() == MoveType.CASTLING){
            return verifyCastling(board, move, vectorV, vectorH);
        }
        if (move.getMoveType() == MoveType.REGULAR){
            return board.getPiece(move.getEnd()) == null &&
                    ( -1 <= vectorV && vectorV <= 1 &&
                      -1 <= vectorH && vectorH <= 1);
        }
        if (move.getMoveType() == MoveType.CAPTURE){
            return board.getPiece(move.getEnd()) != null &&
                    board.getPiece(move.getEnd()).getColour() != board.getPiece(move.getStart()).getColour() &&
                    (-1 <= vectorV && vectorV <= 1 &&
                     -1 <= vectorH && vectorH <= 1);
        }
        return false;
    }

    private static boolean verifyCastling(Board board, Move move, int v, int h){
        if ((move.getStart().getHorizontal() != 0 && board.getPiece(move.getStart()).getColour() == WHITE) ||
        (move.getStart().getHorizontal() != 7 && board.getPiece(move.getStart()).getColour() == BLACK)) {
            return false;
        }
        if (h != 0 || (v != 2 && v != -2)){
            return false;
        }
        v = setToBasic(v);
        if (board.getPiece(move.getStart()).getColour() == WHITE){
            if (v > 0 && !board.isWhiteRightCastling()) {
                return false;
            }
            if (v < 0 && !board.isWhiteLeftCastling()){
                return false;
            }
        }
        else{
            if (v > 0 && !board.isBlackRightCastling()){
                return false;
            }
            if (v < 0 && !board.isBlackLeftCastling()){
                return false;
            }
        }
        if (fieldsNotEmpty(board, move, v)){
            return false;
        }
        Position tempPos = new Position(move.getEnd().getVertical() + v, move.getEnd().getHorizontal());
        Move tempMove = new Move(move.getStart(), tempPos, MoveType.REGULAR);
        return (isNotCheckDanger(board, tempMove));
    }

    private static boolean fieldsNotEmpty(Board board, Move move, int v){
        boolean notEmpty = false;
        Position tempPos = new Position(move.getStart().getVertical() + v, move.getStart().getHorizontal());
        while (tempPos.isNormal() ){
            if (tempPos.getVertical() != 7 && tempPos.getVertical() != 0){
                notEmpty = notEmpty || board.getPiece(tempPos) != null;
            }
            tempPos.setPosition(tempPos.getVertical() + v, tempPos.getHorizontal());
        }
        return notEmpty;
    }

    private static int setToBasic(int x){
        if (x > 0){
            return 1;
        }
        else if (x < 0){
            return -1;
        }
        else{
            return 0;
        }
    }

    private static boolean verifyWithVector(Board board, Move move, int v, int h){
        Position endPos = new Position(move.getStart().getVertical() + v, move.getStart().getHorizontal() + h);
        while (endPos.isNormal()){
            if (board.getPiece(endPos) == null && endPos.equalsTo(move.getEnd())){
                return true;
            }
            if (board.getPiece(endPos) == null){
                endPos.setPosition(endPos.getVertical() + v, endPos.getHorizontal() + h);
                continue;
            }
            if (board.getPiece(endPos).getColour() == board.getPiece(move.getStart()).getColour()){
                return false;
            }
            if (board.getPiece(endPos).getColour() != board.getPiece(move.getStart()).getColour() &&
                    endPos.equalsTo(move.getEnd())){
                return true;
            }
            if (board.getPiece(endPos).getColour() != board.getPiece(move.getStart()).getColour() &&
                    !endPos.equalsTo(move.getEnd())){
                return false;
            }
            endPos.setPosition(endPos.getVertical() + v, endPos.getHorizontal() + h);
        }
        return true;  // perfectly, the method never reaches this row
    }
}

package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.game.Board;
import cz.cvut.fel.pjv.tools.Move;
import cz.cvut.fel.pjv.tools.MoveType;
import cz.cvut.fel.pjv.tools.MoveValidator;
import cz.cvut.fel.pjv.tools.Position;

import java.util.ArrayList;
import java.util.List;

class RegularMoves {
    private RegularMoves(){}

    public static List<Move> getVerticals(Board board, Position position){
        List<Move> result = new ArrayList<>();
        result.addAll(getAny(board, position, 0, 1));
        result.addAll(getAny(board, position, 0, -1));
        return result;
    }

    public static List<Move> getHorizontals(Board board, Position position){
        List<Move> result = new ArrayList<>();
        result.addAll(getAny(board, position, 1, 0));
        result.addAll(getAny(board, position, -1, 0));
        return result;
    }

    public static List<Move> getDiagonals(Board board, Position position){
        List<Move> result = new ArrayList<>();
        result.addAll(getAny(board, position, 1, 1));
        result.addAll(getAny(board, position, 1, -1));
        result.addAll(getAny(board, position, -1, 1));
        result.addAll(getAny(board, position, -1, -1));
        return result;
    }

    private static List<Move> getAny(Board board, Position position, int x, int y){
        // (x;y) is a vector
        Position position1 = new Position(position.getVertical() + y, position.getHorizontal() + x);
        List<Move> result = new ArrayList<>();
        Move temp;

        while ( position1.isNormal() && board.getPiece(position1) == null){
            temp = new Move(position, position1, MoveType.REGULAR);
            if (MoveValidator.verify(board, temp)){
                Position posToResult = new Position(position1);
                Move toTheResult = new Move(position, posToResult, MoveType.REGULAR);
                result.add(toTheResult);
            }
            position1.setPosition(y+position1.getVertical(), x+position1.getHorizontal());
        }
        temp = new Move(position, position1, MoveType.CAPTURE);
        if (position1.isNormal() && MoveValidator.verify(board, temp)){
            Position posToResult = new Position(position1);
            Move toTheResult = new Move(position, posToResult, MoveType.REGULAR);
            result.add(toTheResult);
        }
        return result;
    }
}

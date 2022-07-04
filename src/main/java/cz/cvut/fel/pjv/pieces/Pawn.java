package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.game.Board;
import cz.cvut.fel.pjv.tools.*;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{
    public Pawn(Colour c) {
        super(c);
    }

    @Override
    public List<Move> getMoves(Board board, Position position){
        List<Move> result = new ArrayList<>();
        Position endPos = new Position('i', 8);
        Move hypotheticalMove = new Move(position, position, MoveType.UNDEFINED);

        int shift = -1;
        if (this.colour == Colour.WHITE){
            shift = 1;
        }

        if ((shift == 1 && position.getHorizontal() == 1) ||
        (shift == -1 && position.getHorizontal() == 6)){
            if (endPos.setPosition(position.getVertical(), position.getHorizontal() + (shift*2)) &&
                    board.getPiece(endPos) == null) {
                hypotheticalMove.setMove(position, endPos, MoveType.REGULAR);
                if (MoveValidator.verify(board, hypotheticalMove)){
                    Move toTheResult = new Move(hypotheticalMove);
                    result.add(toTheResult);
                }
            }
        }

        if (endPos.setPosition(position.getVertical(), position.getHorizontal() + shift) &&
            board.getPiece(endPos) == null) {
            hypotheticalMove.setMove(position, endPos, MoveType.REGULAR);
            if (MoveValidator.verify(board, hypotheticalMove)){
                Move toTheResult = new Move(hypotheticalMove);
                result.add(toTheResult);
            }
        }

        for (int i = -1; i < 2; i += 2) {
            if (endPos.setPosition(position.getVertical() + i, position.getHorizontal() + shift)) {
                if (endPos.equalsTo(board.getEnPassant())) {
                    hypotheticalMove.setMove(position, endPos, MoveType.EN_PASSANT);
                    if (MoveValidator.verify(board, hypotheticalMove)) {
                        Move toTheResult = new Move(hypotheticalMove);
                        result.add(toTheResult);
                    }
                } else if (board.getPiece(endPos) != null) {
                    hypotheticalMove.setMove(position, endPos, MoveType.CAPTURE);
                    if (MoveValidator.verify(board, hypotheticalMove)) {
                        Move toTheResult = new Move(hypotheticalMove);
                        result.add(toTheResult);
                    }
                }
            }
        }
        return result;
    }
}

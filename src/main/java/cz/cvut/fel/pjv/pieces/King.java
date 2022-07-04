package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.game.Board;
import cz.cvut.fel.pjv.tools.*;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece{
    public King(Colour c) {
        super(c);
    }

    @Override
    public List<Move> getMoves(Board board, Position position){
        int v = position.getVertical();
        int h = position.getHorizontal();
        Position hypotheticalEnd = new Position((char)(v+'a'), h);
        Move hypotheticalMove = new Move(position, position, MoveType.UNDEFINED);
        List<Move> result = new ArrayList<>();

        // Regular moves and capturing
        for (int i=-1; i<2; i++){
            for (int j=-1; j<2; j++){
                if (hypotheticalEnd.setPosition(v+i, h+j)){
                    hypotheticalMove.setMove(position, hypotheticalEnd, MoveType.UNDEFINED);
                    if (MoveValidator.verify(board, hypotheticalMove)) {
                        Move toTheResult = new Move(hypotheticalMove);
                        result.add(toTheResult);
                    }
                }
            }
        }

        // Castling
        if ((colour == Colour.WHITE && position.getHorizontal() == 0) ||
                (colour == Colour.BLACK && position.getHorizontal() == 7)) {
            hypotheticalEnd.setPosition(6, position.getHorizontal());
            hypotheticalMove.setMove(position, hypotheticalEnd, MoveType.CASTLING);
            if (MoveValidator.verify(board, hypotheticalMove)) {
                Move toTheResult = new Move(hypotheticalMove);
                result.add(toTheResult);
            }
            hypotheticalEnd.setPosition(2, position.getHorizontal());
            hypotheticalMove.setMove(position, hypotheticalEnd, MoveType.CASTLING);
            if (MoveValidator.verify(board, hypotheticalMove)) {
                Move toTheResult = new Move(hypotheticalMove);
                result.add(toTheResult);
            }
        }
        return result;
    }
}

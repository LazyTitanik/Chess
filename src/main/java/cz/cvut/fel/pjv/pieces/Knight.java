package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.game.Board;
import cz.cvut.fel.pjv.tools.*;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{
    public Knight(Colour c) {
        super(c);
    }

    @Override
    public List<Move> getMoves(Board board, Position position) {
        List<Move> result = new ArrayList<>();
        Position endPos = new Position('i', 8);
        Move hypotheticalMove = new Move(position, position, MoveType.UNDEFINED);
        for (int i=-2; i<3; i+=4){
            for (int j=-1; j<2; j+=2){
                endPos.setPosition(position.getVertical()+i, position.getHorizontal()+j);
                hypotheticalMove.setMove(position, endPos, MoveType.UNDEFINED);
                if (MoveValidator.verify(board, hypotheticalMove)){
                    Move toTheResult = new Move(hypotheticalMove);
                    result.add(toTheResult);
                }
                endPos.setPosition(position.getVertical()+j, position.getHorizontal()+i);
                hypotheticalMove.setMove(position, endPos, MoveType.UNDEFINED);
                if (MoveValidator.verify(board, hypotheticalMove)){
                    Move toTheResult = new Move(hypotheticalMove);
                    result.add(toTheResult);
                }
            }
        }
        return result;
    }
}

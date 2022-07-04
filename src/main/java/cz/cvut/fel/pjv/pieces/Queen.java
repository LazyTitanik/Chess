package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.game.Board;
import cz.cvut.fel.pjv.tools.Colour;
import cz.cvut.fel.pjv.tools.Position;
import cz.cvut.fel.pjv.tools.Move;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece{
    public Queen(Colour c) {
        super(c);
    }

    @Override
    public List<Move> getMoves(Board board, Position position){
        List<Move> result = new ArrayList<>();
        result.addAll(RegularMoves.getHorizontals(board, position));
        result.addAll(RegularMoves.getVerticals(board, position));
        result.addAll(RegularMoves.getDiagonals(board, position));
        return result;
    }
}

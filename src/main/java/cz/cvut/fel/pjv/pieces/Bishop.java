package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.game.Board;
import cz.cvut.fel.pjv.tools.Colour;
import cz.cvut.fel.pjv.tools.Position;
import cz.cvut.fel.pjv.tools.Move;

import java.util.List;

public class Bishop extends Piece{
    public Bishop(Colour c) {
        super(c);
    }

    @Override
    public List<Move> getMoves(Board board, Position position){
        return RegularMoves.getDiagonals(board, position);
    }
}

package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.game.Board;
import cz.cvut.fel.pjv.tools.Colour;
import cz.cvut.fel.pjv.tools.Move;
import cz.cvut.fel.pjv.tools.Position;

import java.util.List;

public abstract class Piece {
    protected final Colour colour;

    protected Piece (Colour c){
        this.colour = c;
    }

    /**
     * returns an array of verified moves, that can be
     * made on given board from given position
    * */
    public abstract List<Move> getMoves(Board board, Position position);

    public Colour getColour(){
        return this.colour;
    }
}

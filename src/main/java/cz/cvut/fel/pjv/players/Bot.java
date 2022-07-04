package cz.cvut.fel.pjv.players;

import cz.cvut.fel.pjv.game.Board;
import cz.cvut.fel.pjv.tools.*;

import java.util.List;
import java.util.Random;

public class Bot extends Player {

    private final Random rand;

    /**
     * Creates a player, that can return random moves
     */
    public Bot (Colour c){
        super(c);
        rand = new Random();
    }

    /**
     * @return random move, which is available. In case no moves are available,
     * does not return any and never exits the method
     */
    @Override
    public Move getMove(Board board) {
        Position start = new  Position(rand.nextInt(8), rand.nextInt(8));
        List<Move> moves;
        do {
            do {
                start.setPosition(rand.nextInt(8), rand.nextInt(8));
            } while (board.getPiece(start) == null);
            moves = board.getPiece(start).getMoves(board, start);
        }while(board.getPiece(start).getColour() != colour || moves.isEmpty());
        return moves.get(rand.nextInt(moves.size()));
    }
}

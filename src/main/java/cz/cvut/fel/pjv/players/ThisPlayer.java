package cz.cvut.fel.pjv.players;

import cz.cvut.fel.pjv.GUI.GraphicsOfGame;
import cz.cvut.fel.pjv.game.Board;
import cz.cvut.fel.pjv.tools.Colour;
import cz.cvut.fel.pjv.tools.Move;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ThisPlayer extends Player {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private Move currentMove;
    public ThisPlayer(Colour c){
        super(c);
        currentMove = null;
    }

    /**
     * Waits a move from another thread, which can sent one by setMove() method
     * @return Move, that can be not permitted by rules
     */
    public Move getMove(Board b){
        GraphicsOfGame.setGettingMove(this);
        while(currentMove == null){
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (Exception e){
                LOGGER.severe("ThisPlayer's thread has been interrupted while sleeping");
                Thread.currentThread().interrupt();
            }
        }
        Move result = new Move(currentMove);
        currentMove = null;
        LOGGER.fine("Move has been sent by ThisPlayer");
        return result;
    }

    /**
     * Sets a move, so the method getMove can return it to another thread
     */
    public void setMove(Move move){
        currentMove = new Move(move);
    }
}

package cz.cvut.fel.pjv.players;

import cz.cvut.fel.pjv.game.Board;
import cz.cvut.fel.pjv.tools.Colour;
import cz.cvut.fel.pjv.tools.Move;

public abstract class Player {
    Colour colour;

    protected Player(Colour c){
        this.colour = c;
    }

    public abstract Move getMove(Board board);

    public Colour getColour(){
        return colour;
    }

    public void setMove(Move m){}
}


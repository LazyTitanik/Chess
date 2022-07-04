package cz.cvut.fel.pjv.tools;

import cz.cvut.fel.pjv.pieces.Piece;

public class Move {
    // in case of castling a king must be on "start" position moving to "end"
    private Position start = null;
    private Position end = null;
    private MoveType type = null;
    private Piece newPiece;

    /**
     * Makes new Move
     * @param t must not be CHANGING. Use constructor with Piece parameter instead
     */
    public Move (Position s, Position e, MoveType t){
        if (s != null && e != null && t != null && t != MoveType.CHANGING){
            this.start = s;
            this.end = e;
            this.type = t;
        }
    }

    /**
     * Makes new Move
     * @param t must be CHANGING. Use constructor without Piece parameter, if you
     *          want to make "regular" move
     */
    public Move (Position s, Position e, MoveType t, Piece p){
        if (s != null && e != null && t == MoveType.CHANGING){
            this.start = new Position(s);
            this.end = new Position(e);
            this.type = t;
            newPiece = p;
        }
    }

    /**
     * Created new move, which is independent of given move
     */
    public Move (Move newMove){
        this.start = new Position(newMove.start);
        this.end = new Position(newMove.end);
        this.type = newMove.type;
        if (type == MoveType.CHANGING){
            this.newPiece = newMove.newPiece;
        }
    }

    /**
     * Sets s and e parameters as start and end positions
     * Does not create new positions!
     */
    public void setMove(Position s, Position e, MoveType t){
        if (s != null && e != null && t != null){
            this.start = s;
            this.end = e;
            this.type = t;
        }
    }

    public Position getStart(){
        return start;
    }

    public Position getEnd(){
        return end;
    }

    public MoveType getMoveType(){
        return type;
    }

    public Piece getNewPiece(){
        return newPiece;
    }
}

package cz.cvut.fel.pjv.game;

import cz.cvut.fel.pjv.pieces.*;
import cz.cvut.fel.pjv.tools.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static cz.cvut.fel.pjv.tools.Colour.*;

public class Board {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final List<ArrayList<Piece>> checker;
    private final Position enPassant;
    private final Position whiteKingPos;
    private final Position blackKingPos;
    private boolean whiteRightCastling;
    private boolean whiteLeftCastling;
    private boolean blackRightCastling;
    private boolean blackLeftCastling;
    private boolean manualInit;
    private int whiteKings;
    private int blackKings;

    /**
     * Creates new Board instance. However, it cannot be used
     * until it is initialized by one of init(), initDefault,
     * setManualInit() (last one must be controlled by isInitted() )
     */
    public Board(){
        this.enPassant = new Position('i', 8);
        this.whiteKingPos = new Position('i', 8);
        this.blackKingPos = new Position('i', 8);
        this.checker = new ArrayList<>();
        this.manualInit = false;

        for (int i=0; i<8; i++){
            ArrayList<Piece> temp = new ArrayList<>();
            for (int j=0; j<8; j++){
                temp.add(null);
            }
            this.checker.add(temp);
        }
    }

    /**
     * Makes this board a copy of given board.
     * In case it is set to be initialized manually, does nothing
     */
    public void init(Board board){
        if (manualInit){
            LOGGER.warning("Trying to make a copy of board, while it is supposed to be initialized manually");
            return;
        }
        this.whiteRightCastling = board.whiteRightCastling;
        this.whiteLeftCastling = board.whiteLeftCastling;
        this.blackRightCastling = board.blackRightCastling;
        this.blackLeftCastling = board.blackLeftCastling;
        this.enPassant.setPosition(board.getEnPassant());
        this.whiteKingPos.setPosition(board.getWhiteKingPos());
        this.blackKingPos.setPosition(board.getBlackKingPos());

        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++){
                this.checker.get(i).set(j, board.checker.get(i).get(j));
            }
        }
    }

    /**
     * Initializes a game according to chess rules
     * In case it is set to be initialized manually, does nothing
     */
    public void initDefault(){
        if (manualInit){
            LOGGER.warning("Trying to initialize default board, while it is supposed to be initialized manually");
            return;
        }
        whiteRightCastling = true;
        whiteLeftCastling = true;
        blackRightCastling = true;
        blackLeftCastling = true;

        Piece whiteKing = new King(WHITE);
        Piece whiteQueen = new Queen(WHITE);
        Piece whiteBishop = new Bishop(WHITE);
        Piece whiteKnight = new Knight(WHITE);
        Piece whiteRook = new Rook(WHITE);
        Piece whitePawn = new Pawn(WHITE);

        Piece blackKing = new King(BLACK);
        Piece blackQueen = new Queen(BLACK);
        Piece blackBishop = new Bishop(BLACK);
        Piece blackKnight = new Knight(BLACK);
        Piece blackRook = new Rook(BLACK);
        Piece blackPawn = new Pawn(BLACK);

        for (List<Piece> col : this.checker){
            col.set(1, whitePawn);
        }
        this.checker.get(0).set(0, whiteRook);
        this.checker.get(7).set(0, whiteRook);
        this.checker.get(1).set(0, whiteKnight);
        this.checker.get(6).set(0, whiteKnight);
        this.checker.get(2).set(0, whiteBishop);
        this.checker.get(5).set(0, whiteBishop);
        this.checker.get(3).set(0, whiteQueen);
        this.checker.get(4).set(0, whiteKing);
        this.whiteKingPos.setPosition('e'-'a', 0);

        for (List<Piece> col : this.checker){
            col.set(6, blackPawn);
        }
        this.checker.get(0).set(7, blackRook);
        this.checker.get(7).set(7, blackRook);
        this.checker.get(1).set(7, blackKnight);
        this.checker.get(6).set(7, blackKnight);
        this.checker.get(2).set(7, blackBishop);
        this.checker.get(5).set(7, blackBishop);
        this.checker.get(3).set(7, blackQueen);
        this.checker.get(4).set(7, blackKing);
        this.blackKingPos.setPosition('e'-'a', 7);
    }

    /**
     * Enables setting pieces manually
     */
    public void setManualInit(){
        manualInit = true;
    }

    /**
     * Places a piece on given position
     * @return false, if manual initializing is not enabled or
     * a piece can not be placed on this position
     */
    public boolean setPiece(Piece piece, Position pos){
        // is initted
        if (!manualInit){
            return false;
        }
        // in case deleting
        if (piece == null){ // deleting a piece
            if (checker.get(pos.getVertical()).get(pos.getHorizontal()) != null){
                if(checker.get(pos.getVertical()).get(pos.getHorizontal()).getClass().equals(King.class)
                        && checker.get(pos.getVertical()).get(pos.getHorizontal()).getColour() == WHITE){
                    whiteKings--;
                    whiteKingPos.setPosition(10,10);
                }
                if(checker.get(pos.getVertical()).get(pos.getHorizontal()).getClass().equals(King.class)
                        && checker.get(pos.getVertical()).get(pos.getHorizontal()).getColour() == BLACK){
                    blackKings--;
                    blackKingPos.setPosition(10,10);
                }
                checker.get(pos.getVertical()).set(pos.getHorizontal(), null);
            }
            return true;
        }
        // in case King
        if (piece.getClass().equals(King.class)){
            return controlKings(piece, pos);
        }

        // all other cases
        Piece oldPiece = checker.get(pos.getVertical()).get(pos.getHorizontal());
        checker.get(pos.getVertical()).set(pos.getHorizontal(), piece);
        if (this.isCheck(WHITE) || this.isCheck(BLACK)){
            checker.get(pos.getVertical()).set(pos.getHorizontal(), oldPiece);
            return false;
        }
        if (oldPiece != null) {
            if (oldPiece.getClass().equals(King.class) && oldPiece.getColour() == WHITE) {
                whiteKingPos.setPosition(10,10);
                whiteKings --;
            }
            if (oldPiece.getClass().equals(King.class) && oldPiece.getColour() == WHITE) {
                blackKingPos.setPosition(10,10);
                blackKings --;
            }
        }
        return true;
    }

    private boolean controlKings(Piece piece, Position pos){
        // 1st setting vectors
        int whiteVector = 0;
        int blackVector = 0;
        if (piece.getColour() == WHITE){
            whiteVector = 1;
        }else{
            blackVector = 1;
        }

        // 2nd controlling quantity
        whiteKings+= whiteVector;
        blackKings+= blackVector;
        if (whiteKings > 1 || blackKings > 1){
            whiteKings -= whiteVector;
            blackKings -= blackVector;
            return false;
        }

        // 3rd emulating putting the piece
        Piece oldPiece = checker.get(pos.getVertical()).get(pos.getHorizontal());
        checker.get(pos.getVertical()).set(pos.getHorizontal(), piece);
        if(piece.getColour() == WHITE){
            whiteKingPos.setPosition(pos);
        } else{
            blackKingPos.setPosition(pos);
        }

        // 4th controlling check possibility
        if (this.isCheck(WHITE) || this.isCheck(BLACK)){
            whiteKings -= whiteVector;
            blackKings -= blackVector;
            checker.get(pos.getVertical()).set(pos.getHorizontal(), oldPiece);
            if(piece.getColour() == WHITE){
                whiteKingPos.setPosition(10, 10);
            } else{
                blackKingPos.setPosition(10,10);
            }
            return false;
        }

        // 5th true if all conditions passed
        return true;
    }

    /**
     * @return true, if the board can be used in a game
     */
    public boolean isInitted()
    {
        return whiteKings == 1 && blackKings == 1;
    }

    /**
     * Changes positions of pieces on board according to given move.
     * Does not verify moves before changing the board!
     */
    public void makeMove(Move move){                     // TODO: implement CHANGING!!!!!!!!!!!

        if (this.getPiece(move.getStart()).getClass().equals(King.class) &&
                this.getPiece(move.getStart()).getColour() == WHITE) {
            this.whiteKingPos.setPosition(move.getEnd());
            whiteLeftCastling = false;
            whiteRightCastling = false;
        }
        else if(this.getPiece(move.getStart()).getClass().equals(King.class) &&
                this.getPiece(move.getStart()).getColour() == BLACK){
            this.blackKingPos.setPosition(move.getEnd());
            blackLeftCastling = false;
            blackRightCastling = false;
        }
        enPassant.setPosition(8, 8);
        if (move.getMoveType() == MoveType.REGULAR ||
            move.getMoveType() == MoveType.CAPTURE){
            controlCastlingPossibility(move);
            Piece temp = this.checker.get(move.getStart().getVertical()).get(move.getStart().getHorizontal());
            this.checker.get(move.getStart().getVertical()).set(move.getStart().getHorizontal(), null);
            this.checker.get(move.getEnd().getVertical()).set(move.getEnd().getHorizontal(), temp);
            if (temp.getClass().equals(Pawn.class) &&
                move.getEnd().getHorizontal() - move.getStart().getHorizontal() == 2 ){
                this.enPassant.setPosition(move.getStart().getVertical(), move.getStart().getHorizontal()+1 );
            }
            if (temp.getClass().equals(Pawn.class) &&
                move.getEnd().getHorizontal() - move.getStart().getHorizontal() == -2 ){
                this.enPassant.setPosition(move.getStart().getVertical(), move.getStart().getHorizontal()+1 );
            }
        }
        if (move.getMoveType() == MoveType.CASTLING){
            controlCastlingPossibility(move);
            // 1st moving a king
            Piece temp = this.checker.get(move.getStart().getVertical()).get(move.getStart().getHorizontal());
            this.checker.get(move.getStart().getVertical()).set(move.getStart().getHorizontal(), null);
            this.checker.get(move.getEnd().getVertical()).set(move.getEnd().getHorizontal(), temp);

            //2nd moving a rook
            if (move.getEnd().getVertical() == 2){
                temp = this.checker.get(0).get(move.getStart().getHorizontal());
                this.checker.get(0).set(move.getStart().getHorizontal(), null);
                this.checker.get(3).set(move.getStart().getHorizontal(), temp);
            }
            if (move.getEnd().getVertical() == 6){
                temp = this.checker.get(7).get(move.getStart().getHorizontal());
                this.checker.get(7).set(move.getStart().getHorizontal(), null);
                this.checker.get(5).set(move.getStart().getHorizontal(), temp);
            }
        }
        if (move.getMoveType() == MoveType.EN_PASSANT){
            Piece temp = this.checker.get(move.getStart().getVertical()).get(move.getStart().getHorizontal());
            this.checker.get(move.getStart().getVertical()).set(move.getStart().getHorizontal(), null);
            this.checker.get(move.getEnd().getVertical()).set(move.getEnd().getHorizontal(), temp);
            if (move.getEnd().getHorizontal() == 2){
                this.checker.get(move.getEnd().getVertical()).set(3, null);
            }
            if (move.getEnd().getHorizontal() == 5){
                this.checker.get(move.getEnd().getVertical()).set(4, null);
            }
        }
    }

    /**
     * @return requested piece
     */
    public Piece getPiece(Position pos){
        return this.checker.get(pos.getVertical()).get(pos.getHorizontal());
    }

    private void controlCastlingPossibility(Move move){
        if (this.getPiece(move.getStart()).getClass().equals((King.class))){
            if (this.getPiece(move.getStart()).getColour() == WHITE){
                whiteRightCastling = false;
                whiteLeftCastling = false;
            }
            else{
                blackRightCastling = false;
                blackLeftCastling = false;
            }
        }
        if (this.getPiece(move.getStart()).getClass().equals((Rook.class))){
            int v = move.getStart().getVertical();
            int h = move.getStart().getHorizontal();
            if (v == 0 && h == 0){
                whiteLeftCastling = false;
            }
            else if (v == 7 && h == 0){
                whiteRightCastling = false;
            }
            else if (v == 0 && h == 7){
                blackLeftCastling = false;
            }
            else if (v == 7 && h == 7){
                blackRightCastling = false;
            }
        }
    }

    /**
     * @return true in case on of players has a checkmate
     */
    public boolean isEnd(){
        return ((isCheck(WHITE) && isMate(WHITE)) ||
        (isCheck(BLACK) && isMate(BLACK)));
    }

    /**
     * @return true, if player with given colour can not make any move by their king
     */
    public boolean isMate(Colour colour){
        Position temp = new Position(0,0);
        boolean isAnyMove = false;
        for (int i=0; i<8; i++){
            for (int j = 0; j < 8; j++) {
                if (this.checker.get(i).get(j) != null) {
                    if (this.checker.get(i).get(j).getColour() == colour) {
                        temp.setPosition(i, j);
                        isAnyMove = isAnyMove || (!this.checker.get(i).get(j).getMoves(this, temp).isEmpty());
                    }
                }
            }
        }
        return (!isAnyMove);
    }

    /**
     * @return true, if the king of player with given colour is under attack
     */
    public boolean isCheck (Colour colour){
        Position kingPos = new Position(whiteKingPos);
        if (colour == BLACK){
            kingPos.setPosition(blackKingPos);
        }

        // checking queens, rooks, bishops
        for (int i=-1; i<2; i++){
            for (int j=-1; j<2; j++){
                Position curPos = new Position(kingPos);
                curPos.setPosition(curPos.getVertical()+i, curPos.getHorizontal()+j);
                while (curPos.isNormal()){
                    if (this.getPiece(curPos) != null && this.getPiece(curPos).getColour() == colour){
                        break;
                    }
                    else if (this.getPiece(curPos) != null && this.getPiece(curPos).getColour() != colour){
                        if (this.getPiece(curPos).getClass().equals(Queen.class)){
                            return true;
                        }
                        if ((i==0 || j==0) && this.getPiece(curPos).getClass().equals(Rook.class)){
                            return true;
                        }
                        if (this.getPiece(curPos).getClass().equals(Bishop.class)){
                            return true;
                        }
                    }
                    curPos.setPosition(curPos.getVertical()+i, curPos.getHorizontal()+j);
                }
            }
        }

        // checking knights
        Position curPos = new Position(kingPos);
        for (int i=-2; i<3; i+=4) {
            for (int j = -1; j < 2; j += 2) {
                curPos.setPosition(kingPos.getVertical() + i, kingPos.getHorizontal() + j);
                if (curPos.isNormal() && this.getPiece(curPos) != null) {
                    if (this.getPiece(curPos).getClass().equals(Knight.class) &&
                            this.getPiece(curPos).getColour() != colour) {
                        return true;
                    }
                }
                curPos.setPosition(kingPos.getVertical() + j, kingPos.getHorizontal() + i);
                if (curPos.isNormal() && this.getPiece(curPos) != null) {
                    if (this.getPiece(curPos).getClass().equals(Knight.class) &&
                            this.getPiece(curPos).getColour() != colour) {
                        return true;
                    }
                }
            }
        }
        // checking pawns
        for (int i=-1; i<2; i++) {
            for (int j = -1; j < 2; j++) {
                curPos.setPosition(kingPos.getVertical() + i, kingPos.getHorizontal() + j);
                if(!curPos.isNormal()){
                    continue;
                }
                if (getPiece(curPos) != null){
                    if(getPiece(curPos).getClass().equals(Pawn.class) &&                // a Pawn is nearby
                            getPiece(curPos).getColour() != colour &&                   // it's opponent's pawn
                            i != 0 && j != 0 &&                                         // it's on the same diagonal
                            ((getPiece(curPos).getColour() == WHITE && j == -1) ||       //
                                (getPiece(curPos).getColour() == BLACK && j == 1)))    // it can beat the king
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @return position, where en passant move is available
     */
    public Position getEnPassant(){
        return enPassant;
    }

    public Position getWhiteKingPos(){
        return whiteKingPos;
    }

    public Position getBlackKingPos(){
        return blackKingPos;
    }

    /**
     * @return true, if black king is able to make castling
     * to the left
     */
    public boolean isBlackLeftCastling() {
        return blackLeftCastling;
    }

    /**
     * @return true, if black king is able to make castling
     * to the right
     */
    public boolean isBlackRightCastling() {
        return blackRightCastling;
    }

    /**
     * @return true, if white king is able to make castling
     * to the left
     */
    public boolean isWhiteLeftCastling() {
        return whiteLeftCastling;
    }


    /**
     * @return true, if white king is able to make castling
     * to the right
     */
    public boolean isWhiteRightCastling() {
        return whiteRightCastling;
    }
}

package cz.cvut.fel.pjv.game;

import cz.cvut.fel.pjv.pieces.*;
import cz.cvut.fel.pjv.players.*;
import cz.cvut.fel.pjv.tools.Colour;
import cz.cvut.fel.pjv.tools.Move;
import cz.cvut.fel.pjv.tools.MoveType;
import cz.cvut.fel.pjv.tools.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game implements Runnable{

    boolean isInit = false;
    private Board board;
    private Player player1;
    private Player player2;
    private List<String> gameRecord = new ArrayList<>();
    private int moveNumber;


    /**
    * runs the game in a new thread. Has to be initialized by one of setDefault(),
    * setYourGame(), loadGame() first
    */
    public boolean start(){
        moveNumber = 1;

        if (!isInit){
            return false;
        }
        Thread thread = new Thread(this);
        thread.start();
        return true;
    }

    /**
     * Runs a game. Asks players for move one by one and applies them to a board
     */
    public void run(){
        Move lastMove;
        while (true)
        {
            if (!board.isEnd()){
                LOGGER.fine("waiting for player1 move");
                lastMove = player1.getMove(board);
                saveMove(lastMove);
                board.makeMove(lastMove);
            }
            else{
                break;
            }
            moveNumber++;

            if (!board.isEnd()){
                LOGGER.fine("waiting for player2 move");
                lastMove = player2.getMove(board);
                saveMove(lastMove);
                board.makeMove(lastMove);
            }
            else{
                break;
            }
            moveNumber++;
        }
        if (board.isCheck(Colour.WHITE)){
            gameRecord.add("0-1");
        }else{
            gameRecord.add("1-0");
        }
        LOGGER.fine("Game Ended");
    }

    /**
     * Initializes a game with default settings (classic chess)
     * One player is always ThisPlayer, second player can be assigned
     * with otherPlayer parameter
    */
    public void setDefault(Player otherPlayer){
        if(otherPlayer.getColour() == Colour.WHITE){
            player1 = otherPlayer;
            player2 = new ThisPlayer(Colour.BLACK);
        }
        else{
            player2 = otherPlayer;
            player1 = new ThisPlayer(Colour.WHITE);
        }
        board = new Board();
        board.initDefault();

        isInit = true;
    }

    public boolean setWatching(String filename){
        try{
            board = new Board();
            board.initDefault();
            moveNumber=0;
            File file = new File(filename);
            Scanner myReader = new Scanner(file) ;
            while (myReader.hasNext()) {
                String move = myReader.next();
                String data = move + ' ';
                if (data.charAt(1) != '.' && data.charAt(2) != '.' &&
                        (('A' <=data.charAt(0) && data.charAt(0) <= 'Z') ||
                                'a'<=data.charAt(0) && data.charAt(0) <= 'h')){
                    gameRecord.add(move);
                }
                System.out.println(data);
            }
        }catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "File not found", e);
            return false;
        }
        return true;
    }

    public void makeMove(){
        if(moveNumber >= gameRecord.size()){
            return;
        }
        String curMove = gameRecord.get(moveNumber);
        if(curMove.equals("O-O") || curMove.equals("O-O-O")){
            makeCastling(curMove);
            return;
        }
        Class pieceClass = getPieceClass(curMove);

        for(int v=0; v<8; v++){
            for (int h=0; h<8; h++){
                Position tmp = new Position(v, h);
                if(board.getPiece(tmp) != null){
                    if(board.getPiece(tmp).getClass().equals(pieceClass) &&
                        findAndMakeMove(curMove, tmp)){
                        // exiting the loops
                        v = 10;
                        h = 10;
                    }
                }
            }
        }
        moveNumber++;
    }

    private boolean findAndMakeMove(String given, Position start){
        List<Move> moves = board.getPiece(start).getMoves(board, start);
        for(Move move : moves){
            // TODO: make more nicely
            String tmp = writeMove(move);
            if(tmp.equals(given)){
                board.makeMove(move);
                return true;
            }
        }
        return false;
    }

    private void makeCastling(String data){
        if(data.equals("O-O") && moveNumber%2 == 0){
            Move move = new Move(new Position('e', 0), new Position('g', 0), MoveType.CASTLING);
            board.makeMove(move);
        }
        if(data.equals("O-O-O") && moveNumber%2 == 0){
            Move move = new Move(new Position('e', 0), new Position('c', 0), MoveType.CASTLING);
            board.makeMove(move);
        }
        if(data.equals("O-O") && moveNumber%2 == 1){
            Move move = new Move(new Position('e', 7), new Position('g', 7), MoveType.CASTLING);
            board.makeMove(move);
        }
        if(data.equals("O-O-O") && moveNumber%2 == 1){
            Move move = new Move(new Position('e', 7), new Position('c', 7), MoveType.CASTLING);
            board.makeMove(move);
        }
    }

    private Class getPieceClass(String data){
        if(data.charAt(0) == 'Q'){
            return Queen.class;
        }
        if(data.charAt(0) == 'N'){
            return Knight.class;
        }
        if(data.charAt(0) == 'K'){
            return King.class;
        }
        if(data.charAt(0) == 'R'){
            return Rook.class;
        }
        if(data.charAt(0) == 'B'){
            return Bishop.class;
        }
        return Pawn.class;
    }

    public Board getBoard(){
        return board;
    }

    /**
     * sets given board to the game (in case it has been initialized manually)
     * One player is always ThisPlayer, second player can be assigned
     * with otherPlayer parameter
     */
    public void setYourGame(Board board, Player otherPlayer){
        if(otherPlayer.getColour() == Colour.WHITE){
            player1 = otherPlayer;
            player2 = new ThisPlayer(Colour.BLACK);
        }
        else{
            player2 = otherPlayer;
            player1 = new ThisPlayer(Colour.WHITE);
        }
        this.board = board;

        isInit = true;
    }

    /**
    * Saves a finished game or paused one
    */
    public void saveGame(String fileDirectory) {
        try(FileWriter file = new FileWriter(fileDirectory)){
        int i;
        for (i = 0; i<gameRecord.size()-1; i+=2){
            int moveIndex = i/2+1;
            file.write(moveIndex + ". " + gameRecord.get(i) + " " + gameRecord.get(i+1) + " ");
        }
        file.write((i/2+1) + ". " + gameRecord.get(i) + " ");
        i++;
        if (i+1<gameRecord.size()){
            file.write(gameRecord.get(i) + " ");
        }
        file.write(gameRecord.get(i+1));
        }catch (IOException e){
            LOGGER.log(Level.SEVERE, "Writing went wrong", e);
        }
    }

    private void saveMove(Move move){
        gameRecord.add(writeMove(move));
    }

    private String writeMove ( Move move){
        String result = "";
        if (move.getMoveType() == MoveType.CASTLING && move.getEnd().getVertical() == 6){
            return "O-O";
        }
        if (move.getMoveType() == MoveType.CASTLING && move.getEnd().getVertical() == 2){
            return "O-O-O";
        }
        result = result +
                getName(board.getPiece(move.getStart()).getClass())+
                solveInaccuracy(move);

        if (board.getPiece(move.getStart()).getClass().equals(Pawn.class) &&
                (board.getPiece(move.getEnd()) != null || move.getMoveType() == MoveType.EN_PASSANT)){
            result = result + (char)(move.getStart().getVertical() + 'a') + 'x';
        }
        else if(move.getMoveType() == MoveType.CAPTURE){
            result = result + 'x';
        }
        result = result + (char)(move.getEnd().getVertical() + 'a') + (move.getEnd().getHorizontal() + 1);
        if (move.getMoveType() == MoveType.CHANGING){
            result = result + '=' + getName(move.getNewPiece().getClass());
        }
        Board newBoard = new Board();
        newBoard.init(board);
        newBoard.makeMove(move);
        if (newBoard.isEnd()){
            return result;
        }
        if (newBoard.isCheck(Colour.BLACK) || newBoard.isCheck(Colour.WHITE)){
            result = result + '+';
        }
        if (newBoard.isMate(Colour.BLACK) || newBoard.isMate(Colour.WHITE)){
            result = result + '#';
        }
        return result;
    }

    public Player getPlayer1(){
        return player1;
    }

    public Player getPlayer2(){
        return player2;
    }

    private String solveInaccuracy (Move move){
        List<Position> samePositions = new ArrayList<>();
        int sameEnds = 0;
        for (int i=0; i<8; i++){
            for (int j = 0; j < 8; j++) {
                if(hasSameMove(move, i, j)){
                    sameEnds++;
                    samePositions.add(new Position(i , j));
                }
            }
        }
        if(sameEnds == 1){
            return "";
        }
        return getPrecision(move, samePositions);
    }

    private boolean hasSameMove (Move move, int v, int h){
        Position startPos = new Position(v, h);
        if(board.getPiece(startPos) == null){
            return false;
        }
        for (Move tmp : board.getPiece(startPos).getMoves(board, startPos)){
            if(tmp.getEnd().equalsTo(move.getEnd()) &&
                    board.getPiece(startPos).getClass().equals(board.getPiece(move.getStart()).getClass()) &&
                    board.getPiece(startPos).getColour() == board.getPiece(move.getStart()).getColour()){
                return true;
            }
        }
        return false;
    }

    private String getPrecision ( Move move, List<Position> samePositions){
        int sameV=0;
        int sameH=0;
        for(Position pos:samePositions){
            if(pos.getVertical() == move.getStart().getVertical()){
                sameV++;
            }
            if(pos.getHorizontal() == move.getStart().getHorizontal()){
                sameH++;
            }
        }

        if(sameH == 1){
            return "" + (char)(move.getStart().getVertical() + 'a');
        }
        else if(sameV == 1){
            return "" + (move.getStart().getHorizontal() + 1);
        }
        else{
            return "" + (char)(move.getStart().getVertical() + 'a') + (move.getStart().getHorizontal() + 1);
        }
    }

    private String getName(Class c){
        if (c.equals(Pawn.class)){
            return "";
        }
        if (c.equals(Bishop.class)){
            return "B";
        }
        if (c.equals(Knight.class)){
            return "N";
        }
        if (c.equals(Queen.class)){
            return "Q";
        }
        if (c.equals(Rook.class)){
            return "R";
        }
        if (c.equals(King.class)){
            return "K";
        }
        return "";
    }
}

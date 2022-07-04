package cz.cvut.fel.pjv.tests;

import cz.cvut.fel.pjv.game.Board;
import cz.cvut.fel.pjv.tools.Move;
import cz.cvut.fel.pjv.tools.MoveType;
import cz.cvut.fel.pjv.tools.MoveValidator;
import cz.cvut.fel.pjv.tools.Position;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class MoveValidatorTests {
    // Unit tests

    private Board board;

//    @BeforeEach
//    public void prepare() {
//        board = new Board();
//        board.initDefault();
//    }
    // Já se hrozně omlouvám, že jsem to zahodil a napsal ten kus kódu v každém testu,
    // ale vůbec to nechtělo fungovat. Furt jsem měl NullPointerException, protože board je null,
    // zatímco se to muselo inicializovat.

    @Test
    public void firstPawnMove(){
        board = new Board();
        board.initDefault();
        Move move = new Move(new Position('e', 1), new Position('e', 3), MoveType.REGULAR);
        assertTrue(MoveValidator.verify(board, move), "First pawn move cannot be made");
    }

    @Test
    public void knightMove(){
        board = new Board();
        board.initDefault();
        Move move = new Move(new Position('b', 0), new Position('c', 2), MoveType.REGULAR);
        assertTrue(MoveValidator.verify(board, move), "Knight move cannot be made");
    }

    @Test
    public void queenMove(){
        board = new Board();
        board.initDefault();
        Move pawnMove = new Move(new Position('e', 1), new Position('e', 3), MoveType.REGULAR);
        board.makeMove(pawnMove);
        Move move = new Move(new Position('d', 0), new Position('g', 3), MoveType.REGULAR);
        assertTrue(MoveValidator.verify(board, move), "Queen move cannot be made");
    }

    @Test
    public void bishopMove(){
        board = new Board();
        board.initDefault();
        Move pawnMove = new Move(new Position('e', 1), new Position('e', 3), MoveType.REGULAR);
        board.makeMove(pawnMove);
        Move move = new Move(new Position('f', 0), new Position('c', 3), MoveType.REGULAR);
        assertTrue(MoveValidator.verify(board, move), "Bishop move cannot be made");
    }

    @Test
    public void rookMove(){
        board = new Board();
        board.initDefault();
        Move pawnMove = new Move(new Position('a', 1), new Position('a', 3), MoveType.REGULAR);
        board.makeMove(pawnMove);
        Move move = new Move(new Position('a', 0), new Position('a', 2), MoveType.REGULAR);
        assertTrue(MoveValidator.verify(board, move), "Rook move cannot be made");
    }

    @Test
    public void kingMove(){
        board = new Board();
        board.initDefault();
        Move pawnMove = new Move(new Position('e', 1), new Position('e', 3), MoveType.REGULAR);
        board.makeMove(pawnMove);
        Move move = new Move(new Position('e', 0), new Position('e', 1), MoveType.REGULAR);
        assertTrue(MoveValidator.verify(board, move), "King move cannot be made");
    }

    @Test
    public void rightCastling(){
        board = new Board();
        board.initDefault();
        Move pawnMove = new Move(new Position('e', 1), new Position('e', 3), MoveType.REGULAR);
        board.makeMove(pawnMove);
        Move bishopMove = new Move(new Position('f', 0), new Position('c', 3), MoveType.REGULAR);
        board.makeMove(bishopMove);
        Move knightMove = new Move(new Position('g', 0), new Position('f', 2), MoveType.REGULAR);
        board.makeMove(knightMove);
        Move castling = new Move(new Position('e', 0), new Position('g', 0), MoveType.CASTLING);
        assertTrue(MoveValidator.verify(board, castling), "Right castling cannot be made");
    }

    @Test
    public void leftCastling(){
        board = new Board();
        board.initDefault();
        Move pawnMove = new Move(new Position('e', 1), new Position('e', 3), MoveType.REGULAR);
        board.makeMove(pawnMove);
        Move pawnMove2 = new Move(new Position('d', 1), new Position('d', 3), MoveType.REGULAR);
        board.makeMove(pawnMove2);
        Move bishopMove = new Move(new Position('c', 0), new Position('f', 3), MoveType.REGULAR);
        board.makeMove(bishopMove);
        Move queenMove = new Move(new Position('d', 0), new Position('g', 3), MoveType.REGULAR);
        board.makeMove(queenMove);
        Move knightMove = new Move(new Position('b', 0), new Position('c', 2), MoveType.REGULAR);
        board.makeMove(knightMove);
        Move castling = new Move(new Position('e', 0),new Position('c', 0), MoveType.CASTLING);
        assertTrue(MoveValidator.verify(board, castling), "Left castling move cannot be made");
    }

    @Test
    public void notCastling(){
        board = new Board();
        board.initDefault();
        Move castling = new Move(new Position('e', 0), new Position('g', 0), MoveType.CASTLING);
        assertFalse(MoveValidator.verify(board, castling), "Method allows castling while its impossible");
    }

    @Test
    public void queenCapturing(){
        board = new Board();
        board.initDefault();
        Move pawnMove = new Move(new Position('e', 1), new Position('e', 3), MoveType.REGULAR);
        board.makeMove(pawnMove);
        Move pawnMove2 = new Move(new Position('h', 6), new Position('h', 4), MoveType.REGULAR);
        board.makeMove(pawnMove2);
        Move queenMove = new Move(new Position('d', 0), new Position('h', 4), MoveType.CAPTURE);
        assertTrue(MoveValidator.verify(board, queenMove), "Queen capturing is impossible");
    }

    @Test
    public void pawnCapturing(){
        board = new Board();
        board.initDefault();
        Move pawnMove = new Move(new Position('e', 1), new Position('e', 3), MoveType.REGULAR);
        board.makeMove(pawnMove);
        Move pawnMove2 = new Move(new Position('d', 6), new Position('d', 4), MoveType.REGULAR);
        board.makeMove(pawnMove2);
        Move pawnMove3 = new Move(new Position('e', 3), new Position('d', 4), MoveType.CAPTURE);
        assertTrue(MoveValidator.verify(board, pawnMove3), "Pawn capturing is impossible");
    }
}

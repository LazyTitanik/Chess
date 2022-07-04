package cz.cvut.fel.pjv.tools;

public enum MoveType {
    // in case of castling a king must be on "start" position
    REGULAR, CAPTURE, CASTLING, EN_PASSANT, CHANGING, UNDEFINED;
}

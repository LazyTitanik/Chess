package cz.cvut.fel.pjv.tools;

public enum Name {
    // Main menu
    NEW_GAME, SETTINGS, EXIT, LANGUAGE, BACK, ONE_PLAYER, TWO_PLAYERS,
    LOAD_GAME, WHITE_DEFAULT, BLACK_DEFAULT, DEFAULT, SET_UP,
    // game buttons
    GIVE_UP, DRAW, CONFIRM,
    // GameOver menu
    SAVE_GAME, MAIN_MENU, /*CONFIRM,*/ GAME_OVER_WHITE, GAME_OVER_BLACK,
    DIRECTORY_NAME,
    // Info in GameOver menu
    ERROR_EMPTY_DIRECTORY, ERROR_FILE_CANNOT_BE_CREATED, GAME_SAVED,
    // Watching a game
    NEXT_MOVE,
    // Available languages
    ENGLISH_LANGUAGE, CZECH_LANGUAGE, RUSSIAN_LANGUAGE;
}

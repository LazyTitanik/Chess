package cz.cvut.fel.pjv.tools;

import java.util.HashMap;
import java.util.Map;

import static cz.cvut.fel.pjv.tools.Name.*;


public class ButtonNames {
    private static final Map<Language, HashMap<Name, String>> names = new HashMap<>();
    private static boolean inited = false;

    /**
     * Initializes class, so names could be received in the future.
     * In case it already has been initialized, does nothing.
     * Uses HashMap, not EnumMap, so if name is not in there, the
     * behavior is undefined
     */
    public static void init(){
        if(inited){
            return;
        }
        HashMap<Name, String> englishMap = new HashMap<>();
        englishMap.put(NEW_GAME, "NEW GAME");
        englishMap.put(SETTINGS, "SETTINGS");
        englishMap.put(EXIT, "EXIT");
        englishMap.put(LANGUAGE, "LANGUAGE");
        englishMap.put(BACK, "BACK");
        englishMap.put(ONE_PLAYER, "ONE PLAYER");
        englishMap.put(TWO_PLAYERS, "TWO PLAYERS");
        englishMap.put(LOAD_GAME, "LOAD GAME");
        englishMap.put(WHITE_DEFAULT, "WHITE DEFAULT");
        englishMap.put(BLACK_DEFAULT, "BLACK DEFAULT");
        englishMap.put(DEFAULT, "DEFAULT");
        englishMap.put(SET_UP, "SET UP");
        englishMap.put(GIVE_UP, "Give up");
        englishMap.put(DRAW, "Draw");
        englishMap.put(ENGLISH_LANGUAGE, "ENGLISH");
        englishMap.put(CZECH_LANGUAGE, "ČEŠTINA");
        englishMap.put(RUSSIAN_LANGUAGE, "РУССКИЙ");
        englishMap.put(SAVE_GAME, "SAVE GAME");
        englishMap.put(MAIN_MENU, "MAIN MENU");
        englishMap.put(CONFIRM, "Confirm");
        englishMap.put(GAME_OVER_WHITE, "Game is over. White won");
        englishMap.put(GAME_OVER_BLACK, "Game is over. Black won");
        englishMap.put(DIRECTORY_NAME, "E.g. D:\\\\My_games\\Chess\\Game1.txt");
        englishMap.put(ERROR_EMPTY_DIRECTORY, "DIRECTORY MUST NOT BE EMPTY");
        englishMap.put(ERROR_FILE_CANNOT_BE_CREATED, "THIS DIRECTORY CANNOT BE ACCESSED");
        englishMap.put(GAME_SAVED, "Game saved successfully");
        englishMap.put(NEXT_MOVE, "Next move");
        names.put(Language.ENGLISH, englishMap);

        HashMap<Name, String> czechMap = new HashMap<>();
        czechMap.put(NEW_GAME, "NOVÁ HRA");
        czechMap.put(SETTINGS, "NASTAVENÍ");
        czechMap.put(EXIT, "VÝCHOD");
        czechMap.put(LANGUAGE, "JAZYK");
        czechMap.put(BACK, "ZPATKY");
        czechMap.put(ONE_PLAYER, "JEDEN HRAČ");
        czechMap.put(TWO_PLAYERS, "DVA HRAČE");
        czechMap.put(LOAD_GAME, "NAČÍST HRU");
        czechMap.put(WHITE_DEFAULT, "BÍLÝ VÝCHOZÍ");
        czechMap.put(BLACK_DEFAULT, "ČERNÝ VÝCHOZÍ");
        czechMap.put(DEFAULT, "VÝCHOZÍ");
        czechMap.put(SET_UP, "NASTAVIT");
        czechMap.put(GIVE_UP, "Vzdat se");
        czechMap.put(DRAW, "Remíza");
        czechMap.put(ENGLISH_LANGUAGE, "ENGLISH");
        czechMap.put(CZECH_LANGUAGE, "ČEŠTINA");
        czechMap.put(RUSSIAN_LANGUAGE, "РУССКИЙ");
        czechMap.put(SAVE_GAME, "ULOŽIT HRU");
        czechMap.put(MAIN_MENU, "HLAVNÍ MENU");
        czechMap.put(CONFIRM, "Potvrdit");
        czechMap.put(GAME_OVER_WHITE, "Hra se ukončila. Bílé vyhrali");
        czechMap.put(GAME_OVER_BLACK, "Hra se ukončila. Černé vyhrali");
        czechMap.put(DIRECTORY_NAME, "Příklad D:\\\\My_games\\Chess\\Game1.txt");
        czechMap.put(ERROR_EMPTY_DIRECTORY, "REPOZITÁŘ NESMÍ BÝT PRÁZDNÝ");
        czechMap.put(ERROR_FILE_CANNOT_BE_CREATED, "REPOZITÁŘ NENÍ DOSAŽITELNÝ");
        czechMap.put(GAME_SAVED, "Hra uložena úspěšně");
        czechMap.put(NEXT_MOVE, "Následujicí tah");
        names.put(Language.CZECH, czechMap);


        HashMap<Name, String> russianMap = new HashMap<>();
        russianMap.put(NEW_GAME, "НОВАЯ ИГРА");
        russianMap.put(SETTINGS, "НАСТРОЙКИ");
        russianMap.put(EXIT, "ВЫХОД");
        russianMap.put(LANGUAGE, "ЯЗЫК");
        russianMap.put(BACK, "НАЗАД");
        russianMap.put(ONE_PLAYER, "ОДИН ИГРОК");
        russianMap.put(TWO_PLAYERS, "ДВА ИГРОКА");
        russianMap.put(LOAD_GAME, "ЗАГРУЗИТЬ ИГРУ");
        russianMap.put(WHITE_DEFAULT, "БЕЛЫЕ ПО УМОЛЧАНИЮ");
        russianMap.put(BLACK_DEFAULT, "ЧЕРНЫЕ ПО УМОЛЧАНИЮ");
        russianMap.put(DEFAULT, "ПО УМОЛЧАНИЮ");
        russianMap.put(SET_UP, "НАСТРОИТЬ");
        russianMap.put(GIVE_UP, "Сдаться");
        russianMap.put(DRAW, "Ничья");
        russianMap.put(ENGLISH_LANGUAGE, "ENGLISH");
        russianMap.put(CZECH_LANGUAGE, "ČEŠTINA");
        russianMap.put(RUSSIAN_LANGUAGE, "РУССКИЙ");
        russianMap.put(SAVE_GAME, "СОХРАНИТЬ ИГРУ");
        russianMap.put(MAIN_MENU, "ГЛАВНОЕ МЕНЮ");
        russianMap.put(CONFIRM, "Подтвердить");
        russianMap.put(GAME_OVER_WHITE, "Игра закончена. Белые выиграли");
        russianMap.put(GAME_OVER_BLACK, "Игра закончена. Черные выиграли");
        russianMap.put(DIRECTORY_NAME, "Например D:\\\\My_games\\Chess\\Game1.txt");
        czechMap.put(ERROR_EMPTY_DIRECTORY, "ПУТЬ НЕ МОЖЕТ БЫТЬ ПУСТОЙ");
        czechMap.put(ERROR_FILE_CANNOT_BE_CREATED, "НЕЛЬЗЯ СОХРАНИТЬ ФАЙЛ");
        czechMap.put(GAME_SAVED, "Игра успешно сохранена");
        czechMap.put(NEXT_MOVE, "Следующий ход");
        names.put(Language.RUSSIAN, russianMap);

        inited = true;
    }

    /**
     * Returns a name of button in String
     */
    public static String getName(Language language, Name name){
        return names.get(language).get(name);
    }
}

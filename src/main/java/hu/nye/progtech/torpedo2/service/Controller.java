package hu.nye.progtech.torpedo2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hu.nye.progtech.torpedo2.model.Field;
import hu.nye.progtech.torpedo2.model.Ocean;
import hu.nye.progtech.torpedo2.ui.UI;

public class Controller {
    /**
     * Mezok
     */
    private Player player1;
    private Player player2;
    private Scanner reader = new Scanner(System.in);
    private boolean turnOne = true;

    /**
     * Jatekosok konf
     */
    public void setupPlayers() {
        while (player1 == null) player1 = setupPlayer("1");
        while (player2 == null) player2 = setupPlayer("2");
    }

    /**
     * Nev bekerese
     *
     * @param id separate player to the user
     * @return created player
     */
    private Player setupPlayer(String id) {
        UI.printf("%s. Jatekos: ", id);
        String name = reader.next();
        if (name.isEmpty()) return null;
        return new Player(name);
    }

    /**
     * A ket jatekosnak a palya felallitasa
     */
    public void setupBoards() {
        setupBoard(player1);
        setupBoard(player2);
    }

    /**
     * Jatekosnak a palya beallitasa
     *
     * @param player to setup battlefield
     */
    private void setupBoard(Player player) {
        UI.println("========================================");
        UI.printf(UI.ANSI_YELLOW + "[%s] Tabla keszitese: %n" + UI.ANSI_RESET, player.getName());
        player.initBoard();
        printBoard(player, false);
        setupShips(player);
        UI.clear();
        sleep(2000);
    }

    /**
     * Tabla kiiratasa
     *
     * @param player board to be printed
     * @param enemy  view
     */
    private void printBoard(Player player, boolean enemy) {
        Field[][] board = player.getBoard();
        int sizeX = board.length;
        for (int i = 0; i <= sizeX; i++) {
            UI.print(i == 0 ? "[ ]" : "[" + i + "]");
        }
        UI.println("");
        for (int i = 0; i < sizeX; i++) {
            char c = (char) ((int) 'a' + i);
            UI.print("[" + c + "]");
            int sizeY = board[i].length;
            for (int j = 0; j < sizeY; j++) {
                //UI.print("["+ i + "|" + j +"]");
                Field field = board[i][j];
                if (enemy) {
                    printFieldEnemy(field);
                } else {
                    printField(field);
                }
            }
            UI.println("");
        }
    }

    /**
     * Mezok kiiratasa
     *
     * @param field to be printed
     */
    private void printField(Field field) {
        if (field instanceof Ocean) {
            UI.print(UI.ANSI_BLUE + "[~]" + UI.ANSI_RESET);
        } else if (field instanceof ShipComponent) {
            UI.print(UI.ANSI_GREEN + "[*]" + UI.ANSI_RESET);
        }
    }

    /**
     * Ellenseg mezeinek mutatasa
     *
     * @param field to be printed
     */
    private void printFieldEnemy(Field field) {
        if (field instanceof Ocean) {
            if (field.getFieldState().isBombed()) {
                UI.print(UI.ANSI_CYAN + "[X]" + UI.ANSI_RESET);
            } else {
                UI.print(UI.ANSI_BLUE + "[~]" + UI.ANSI_RESET);
            }
        } else if (field instanceof ShipComponent) {
            if (field.getFieldState().isBombed()) {
                UI.print(UI.ANSI_RED + "[+]" + UI.ANSI_RESET);
            } else {
                UI.print(UI.ANSI_BLUE + "[~]" + UI.ANSI_RESET);
            }
        }
    }

    /**
     * Hajok felallitasa
     *
     * @param player ships to be placed
     */
    private void setupShips(Player player) {
        Ship[] ships = player.getShips();
        printShips(ships);
        for (Ship ship : ships) {
            setupShip(player, ship);
            printBoard(player, false);
        }
    }

    /**
     * Elhelyezese
     *
     * @param player
     * @param ship
     */
    private void setupShip(Player player, Ship ship) {
        UI.print("Helyezd el " + ship.getName() + " (" + ship.getLength() + ") :");
        String inputPosition = reader.next();
        int[] pos = inputToPosition(inputPosition);
        if (!isPositionInRange(pos, player.getBoard())) {
            //Hibas pozicio bevitel, ujra kell kerdezni
            UI.println(UI.ANSI_YELLOW + "Nem megfelelo pozicio, probald ujra" + UI.ANSI_RESET);
            setupShip(player, ship);
            return;
        }
        List<String> directions = new ArrayList<>();
        if (player.canPlaceShip(pos[0], pos[1], Board.UP, ship.getLength())) {
            directions.add("Fel");
        }
        if (player.canPlaceShip(pos[0], pos[1], Board.RIGHT, ship.getLength())) {
            directions.add("Jobb");
        }
        if (player.canPlaceShip(pos[0], pos[1], Board.DOWN, ship.getLength())) {
            directions.add("Le");
        }
        if (player.canPlaceShip(pos[0], pos[1], Board.LEFT, ship.getLength())) {
            directions.add("Bal");
        }

        boolean isPlaced = false;
        if (directions.size() > 0) {
            while (!isPlaced) {
                for (String dir : directions) {
                    UI.printf("- %s %n", dir);
                }
                UI.print("Irany: ");
                String inputDirection = reader.next();
                if (player.canPlaceShip(pos[0], pos[1], inputToDirection(inputDirection), ship.getLength())) {
                    //Hajo elhelyezese
                    player.placeShip(pos[0], pos[1], inputToDirection(inputDirection), ship);
                    UI.println(UI.ANSI_GREEN + "Ugyes vagy" + UI.ANSI_RESET);
                    isPlaced = true;
                } else {
                    UI.println(UI.ANSI_RED + "Ervenytelen bevitel" + UI.ANSI_RESET);
                }
            }
        } else {
            UI.println(UI.ANSI_YELLOW + "Nincs itt hely" + UI.ANSI_RESET);
            setupShip(player, ship);
        }
    }

    /**
     * Elerheto hajok kiirasa
     *
     * @param ships
     */
    private void printShips(Ship[] ships) {
        for (Ship ship : ships) {
            UI.println(" (" + ship.getLength() + ") " + ship.getName());
        }
    }

    /**
     * Beviteli adat atalakitasa poziciora
     *
     * @param input
     * @return position
     */
    private int[] inputToPosition(String input) {
        int[] pos = {-1, -1};
        char posY = input.charAt(0);
        String posX = input.substring(1);
        pos[0] = (int) posY - (int) 'a';
        try {
            pos[1] = Integer.parseInt(posX) - 1;
        } catch (NumberFormatException ex) {
            UI.println(UI.ANSI_RED + "A kovetkezo a megfelelo beviteli mod: " + UI.ANSI_YELLOW + "[a-j][1-10]" + UI.ANSI_RESET);
        }
        return pos;
    }

    /**
     * Itt ellenorizzuk, h a felhasznalo esetleg a palyan kivuli koordinatat adott e meg
     *
     * @param pos   position
     * @param board
     * @return if is within range
     */
    private boolean isPositionInRange(int[] pos, Field[][] board) {
        int y = pos[0];
        int x = pos[1];
        if (y >= 0 && y < board.length
                && x >= 0 && x < board[y].length) return true;
        return false;
    }

    /**
     * Hajok elhelyezesenek iranyai
     *
     * @param input
     * @return direction
     */
    private int inputToDirection(String input) {
        switch (input.toLowerCase()) {
            case "f":
            case "fel":
                return Board.UP;
            case "j":
            case "jobb":
                return Board.RIGHT;
            case "l":
            case "le":
                return Board.DOWN;
            case "b":
            case "bal":
                return Board.LEFT;
            default:
                return -1;
        }
    }

    /**
     * A jatekosok meg elnek-e vagy mar nincs hajojuk azt ellenorizzuk itt
     *
     * @return if is still battling
     */
    public boolean isGameOnGoing() {
        return isPlayerStillLiving(player1) && isPlayerStillLiving(player2);
    }

    /**
     * Egyenekent nezzuk meg, h meg van a jatekosnak lehetosege
     *
     * @param player to be checked
     * @return if player is alive
     */
    private boolean isPlayerStillLiving(Player player) {
        for (Ship ship : player.getShips()) {
            if (ship.getHealth() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Jatek loop
     */
    public void play() {
        UI.println("========================================");
        if (turnOne) {
            printBoard(player2, true);
            play(player1, player2);
            printBoard(player2, true);
        } else {
            printBoard(player1, true);
            play(player2, player1);
            printBoard(player1, true);
        }
        turnOne = !turnOne;
        UI.clear();
        sleep(2000);
    }

    /**
     * Jatekos sorra kerul
     *
     * @param player
     * @param enemy
     */
    private void play(Player player, Player enemy) {
        UI.printf(UI.ANSI_YELLOW + "[%s] Add meg a loves koordinatait: " + UI.ANSI_RESET, player.getName());
        String inputPosition = reader.next();
        int[] pos = inputToPosition(inputPosition);
        if (isPositionInRange(pos, player.getBoard())) {
            UI.println(enemy.hit(pos));
        } else {
            UI.println(UI.ANSI_YELLOW + "Nem megfelelo koordinata" + UI.ANSI_RESET);
            play(player, enemy);
        }
        sleep(1000);
    }

    /**
     * Nyertes
     */
    public void kudosToWinner() {
        String message = UI.ANSI_GREEN + "Remek %s :) !" + UI.ANSI_RED + " Legyozted %s :(" + UI.ANSI_RESET;
        if (isPlayerStillLiving(player1)) {
            UI.printf(message, player1.getName(), player2.getName());
        } else if (isPlayerStillLiving(player2)) {
            UI.printf(message, player2.getName(), player1.getName());
        }
        sleep(2500);
    }

    /**
     * Varakozas, h folytatodjon
     *
     * @param millis time
     */
    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

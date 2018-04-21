package game;

import game.fielDecorator.AvailableMapsLoader;
import game.fielDecorator.MapLoader;
import game.map.Map;
import game.movement.*;
import game.movement.helper.Bridge;
import game.movement.helper.Dynamite;
import game.movement.helper.MovementHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static game.helper.ArrayHelper.contains;

/**
 * The class where the magic happen.
 * <p>
 * This class is invoked when the User selects playing.
 * </p>
 */
class Play {
    private static ArrayList<String> availableMaps;
    private final String[] moves = new String[]{"w", "a", "s", "d"};
    private final String[] helpers = new String[]{"t", "b"};
    private HashMap<String, Integer> allowedMovements;
    private HashMap<String, Integer> allowedMovementHelpers;
    private int allowedRepeats;
    private int selectedMap = 0;

    static void init() {
        AvailableMapsLoader aml = new AvailableMapsLoader();
        availableMaps = aml.loadObject("aml.ser");
        if (availableMaps == null) {
            availableMaps = new ArrayList<>();
        }
    }

    static ArrayList<String> getAvailableMaps() {
        return availableMaps;
    }

    public static void setAvailableMaps(ArrayList<String> availableMaps1) {
        availableMaps = availableMaps1;
        saveMaps();
    }

    static void addMapToAvailableMaps(String mapName) {
        availableMaps.add(mapName);
        saveMaps();
    }

    public static void removeMap(String mapName) {
        availableMaps.remove(mapName);
        saveMaps();
    }

    /**
     * Saves the available maps in a file.
     */
    private static void saveMaps() {
        AvailableMapsLoader aml = new AvailableMapsLoader();
        aml.saveObject("aml.ser", availableMaps);
    }

    /**
     * Starts the game.
     */
    void play() {
        selectMap();
        startGame();
    }

    /**
     * Prints the available Maps, and requesting the user to choose one.
     */
    private void selectMap() {
        if (printAvailableMaps()) {
            readMap();
        }
    }

    private boolean printAvailableMaps() {
        if (availableMaps == null || availableMaps.isEmpty()) {
            System.out.println("Sorry there is no maps available!");
            return false;
        }
        availableMaps.forEach((s -> System.out.println(availableMaps.indexOf(s) + 1 + ". " + s)));
        return true;
    }

    /**
     * Reads a mup index from the default input stream.
     */
    private void readMap() {
        Scanner scanner = new Scanner(System.in);
        boolean failed = true;
        while (failed) {
            String readData = scanner.nextLine();
            try {
                int tmpSelectedMap = Integer.parseInt(readData);
                if (tmpSelectedMap < 1 || tmpSelectedMap > availableMaps.size()) {
                    throw new Exception();
                }
                this.selectedMap = tmpSelectedMap - 1;
                failed = false;
            } catch (Exception e) {
                System.out.println("Numbers allowed only from the list!");
            }
        }
    }

    /**
     * Starts the game. Now the user can play it.
     */
    private void startGame() {
        Map map = loadMap();
        if (map != null) {
            this.printUserInterface(map);
            String signals = readSignals(map);
            map.touchStartStation();
            makeMovement(signals, map);
            this.printUserInterface(map);
            if (map.isRandalDead()) {
                System.out.println("Nooooooooooooooooooo!\n You killed RANDAL! ;( \n Suffer!!");
            }
            if (map.isMapCompleted()) {
                System.out.println("WoW! You are amazing!");
            } else {
                System.out.println("Better luck next time!");
            }
        } else {
            System.out.println("The selected map doesn't available anymore!");
        }
    }

    /**
     * Loads the selected Map, to play
     *
     * @return the selected map instance
     */
    private Map loadMap() {
        Map map = null;
        try {
            map = (new MapLoader()).loadObject(availableMaps.get(this.selectedMap) + ".ser");
            initSignals(map);
        } catch (Exception e) {
            if (availableMaps.size() > this.selectedMap) {
                availableMaps.remove(this.selectedMap);
            }
            saveMaps();
        }
        return map;
    }

    /**
     * Initialises the allowed elements
     *
     * @param map the loaded Map.
     */
    private void initSignals(Map map) {
        this.allowedMovements = map.getAllowedMovements();
        this.allowedMovementHelpers = map.getAllowedMovementHelpers();
        this.allowedRepeats = map.getNumberOfAllowedRepeats();
    }

    /**
     * Prints user interface to the default output stream.
     *
     * @param map Map
     */
    private void printUserInterface(Map map) {
        this.printAvailableSignals();
        map.draw();
    }

    /**
     * We must do some user input validation.
     * Because we CAN'T TRUST ANYONE.
     * Yeah it's ugly and all, but at least it work's, but not sure.
     *
     * @param map Map
     * @return String
     */
    private String readSignals(Map map) {
        Scanner input = new Scanner(System.in);
        boolean failedToRead = true;
        String signals = "";
        while (failedToRead) {
            HashMap<String, Integer> tmpAllowedMovements = (HashMap<String, Integer>) allowedMovements.clone();
            HashMap<String, Integer> tmpAllowedMovementHelpers = (HashMap<String, Integer>) allowedMovementHelpers.clone();
            signals = input.nextLine();
            int k = 0;
            int repeats = 0;

            for (int i = 0; i < signals.length(); i++) {
                String key = signals.charAt(i) + "";
                if (contains(moves, key)) {
                    k = tmpAllowedMovements.get(getMovement(key).getName()) - 1;
                    tmpAllowedMovements.put(getMovement(key).getName(), k);
                } else if (contains(helpers, key)) {
                    k = tmpAllowedMovementHelpers.get(getMovementHelper(key).getName()) - 1;
                    tmpAllowedMovementHelpers.put(getMovementHelper(key).getName(), k);
                    try {
                        key = signals.charAt(++i) + "";
                        k = tmpAllowedMovements.get(getMovement(key).getName()) - 1;
                        tmpAllowedMovements.put(getMovement(key).getName(), k);
                    } catch (Exception e) {
                        System.out.println("A movement helper must follow a movement.");
                        failedToRead = false;
                        break;
                    }
                } else if (key.equals("r")) {
                    k = --allowedRepeats;
                    boolean isNumber = true;
                    int tmpI = i;
                    while (isNumber) {
                        try {
                            Integer.parseInt(signals.charAt(++i) + "");
                        } catch (Exception e) {
                            isNumber = false;
                        }
                    }
                    i--;
                    if (tmpI == i) {
                        System.out.println("Repeat must follow a number.");
                        failedToRead = false;
                        break;
                    }

                    if (allowedRepeats < 0) {
                        System.out.println("You used all your repeats!");
                        failedToRead = false;
                        break;
                    }
                    repeats++;
                } else if (key.equals("p")) {
                    repeats--;
                } else {
                    System.out.println("Bad signal is given \"" + signals.charAt(i) + "\" at: " + (i + 1));
                    failedToRead = false;
                    break;
                }
                if (k < 0) {
                    System.out.println("You used more elements then allowed.");
                    failedToRead = false;
                    break;
                }
            }
            if (repeats != 0) {
                System.out.println("There is a black hole in the signals!");
                failedToRead = false;
            }
            failedToRead = !failedToRead;
            if (failedToRead) {
                this.initSignals(map);
                printAvailableSignals();
            }
        }
        return signals;
    }

    /**
     * Makes a movements
     *
     * @param signals Movement signals
     * @param map     Map
     */
    private void makeMovement(String signals, Map map) {
        Movement movement;
        MovementHelper movementHelper;
        if (map.isRandalDead()) {
            return;
        }
        for (int j = 0; j < signals.length(); j++) {
            String key = signals.charAt(j) + "";
            movement = null;
            movementHelper = null;
            if (contains(moves, key)) {
                movement = getMovement(key);
            } else if (contains(helpers, key)) {
                movementHelper = getMovementHelper(key);
                key = signals.charAt(++j) + "";
                movement = getMovement(key);
            } else if (key.equals("r")) {
                j += doSomeMagic(getRepeat(signals.substring(j + 1)), map);
            }
            if (movement != null) {
                map.useMovementHelper(movement, movementHelper);
                map.nextMove(movement);
            }
        }
    }

    /**
     * Just believe it. It's magic.
     * Even I don't know what is going on.
     *
     * @param signals String
     * @param map     Map
     * @return int
     */
    private int doSomeMagic(String signals, Map map) {
        int numberOfRepeats = 0;
        int i = 0;
        boolean notChar = true;
        int result = signals.length() + 1;
        while (notChar) {
            try {
                numberOfRepeats = numberOfRepeats * 10 + Integer.parseInt(signals.charAt(i) + "");
                i++;
            } catch (Exception e) {
                notChar = false;
            }
        }
        signals = signals.substring(i);

        for (i = 0; i < numberOfRepeats; i++) {
            makeMovement(signals, map);
        }
        return result;
    }

    /**
     * Gets a Movement.
     *
     * @param key String key of the Movement.
     * @return Movement
     */
    private Movement getMovement(String key) {
        Movement mv = null;
        if (key.equals("w")) {
            mv = new Up();
        }
        if (key.equals("s")) {
            mv = new Down();
        }
        if (key.equals("a")) {
            mv = new Left();
        }
        if (key.equals("d")) {
            mv = new Right();
        }
        return mv;
    }

    /**
     * Gets a Movement Helper.
     *
     * @param key String key of the Movement Helper.
     * @return Movement Helper
     */
    private MovementHelper getMovementHelper(String key) {
        MovementHelper movementHelper = null;
        if (key.equals("t")) {
            movementHelper = new Dynamite();
        }
        if (key.equals("b")) {
            movementHelper = new Bridge();
        }
        return movementHelper;
    }

    /**
     * It's a magic method too.
     *
     * @param signals String signals
     * @return String signals
     */
    private String getRepeat(String signals) {
        int findStopSignals = 1;
        StringBuilder stringBuilder = new StringBuilder();
        boolean doBreak = false;
        for (int i = 0; i < signals.length(); i++) {
            char key = signals.charAt(i);
            if (key == 'p') {
                if (findStopSignals == 1) {

                    doBreak = true;
                } else {
                    findStopSignals--;
                }
            }
            if (key == 'r') {
                findStopSignals++;
            }
            if (doBreak) {
                break;
            }
            stringBuilder.append(key);
        }
        return stringBuilder.toString();
    }

    private void printAvailableSignals() {
        this.printAvailableElements(this.allowedMovements);
        this.printAvailableElements(this.allowedMovementHelpers);
        System.out.println("Number of allowed repeats: " + this.allowedRepeats);
    }

    /**
     * Prints allowed Movements to the default output stream.
     */
    private void printAvailableElements(HashMap<String, Integer> element) {
        element.forEach((s, integer) -> System.out.print(s + ": " + integer + " "));
        System.out.println();
    }
}

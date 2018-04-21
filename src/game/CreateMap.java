package game;

import game.fielDecorator.MapLoader;
import game.helper.ArrayHelper;
import game.helper.MapHelper;
import game.map.Map;
import game.map.create.InputValidator;
import game.map.create.UserInteraction;
import game.map.field.*;
import game.movement.Down;
import game.movement.Left;
import game.movement.Right;
import game.movement.Up;
import game.movement.helper.Bridge;
import game.movement.helper.Dynamite;

import java.util.HashMap;
import java.util.Scanner;

class CreateMap {

    private final String[] availableFields;
    private Map map;
    private int[] mapSize;
    /** */
    private String[] mapInString;
    private HashMap<String, Integer> movementHelpers;
    private HashMap<String, Integer> movements;
    private int numberOfAllowedRepeats = 0;
    private int[] startPosition = new int[2];
    private Field[][] mapFields = null;
    private String mapName;

    CreateMap() {
        availableFields = new String[]{
                (new Water()).drawingElement(),
                (new Prairie()).drawingElement(),
                (new Rock()).drawingElement(),
                (new Station()).drawingElement(),
        };
    }

    /**
     * Starts a process to create a new map.
     */
    void createMap() {
        this.readMapSize();
        this.readMapElements();
        this.readStartPosition();
        this.readMovements();
        this.readMovementHelpers();
        this.readNumberOfAllowedRepeats();
        this.readMapName();
        this.save();
    }

    /**
     * Reads the map size from the default input stream.
     */
    private void readMapSize() {
        Scanner scanner = new Scanner(System.in);
        String input;
        boolean isSizeNotValid;

        System.out.println("Map Dimension (ex.: 12x4 (ROWxCOLUMN)):");

        do {
            input = scanner.nextLine();
            this.mapSize = InputValidator.parseMapSize(input.split("x"));
            if (isSizeNotValid = !InputValidator.isMapSizeValid(this.mapSize)) {
                System.out.println("Bad input. Please try again.");
            }
        } while (isSizeNotValid);
        mapInString = new String[mapSize[0]];
    }

    /**
     * Reads the map fields from the default input stream.
     */
    private void readMapElements() {
        Scanner scanner = new Scanner(System.in);
        String row;
        boolean mapIsBad = true;
        while (mapIsBad) {
            UserInteraction.printMapHelper();
            for (int i = 0; i < mapSize[0]; i++) {
                row = scanner.next();
                if (validateInputAndPrintErrorMessageIfHasAny(row, mapSize[1])) {
                    mapInString[i] = row;
                } else {
                    --i;
                }
            }
            UserInteraction.drawMap(this.mapInString);
            mapIsBad = !isValidMap();
            if (mapIsBad) {
                UserInteraction.printBadMapMessage();
            }
        }
        this.mapFields = MapHelper.initMapFieldsFromFile(this.mapInString, this.mapSize);
    }

    /**
     * Checks is the given row is a valid Map row, and print an error message if not.
     *
     * @param row    Map row.
     * @param length Map row length.
     * @return true if the Map row is valid.
     */
    private boolean validateInputAndPrintErrorMessageIfHasAny(String row, int length) {
        if (isValidInput(row, length)) {
            return true;
        }
        System.out.println("There is an error in your input.");

        return false;
    }

    /**
     * Checks is the given row is a valid Map row.
     *
     * @param row    Map row.
     * @param length Map row length.
     * @return true if the Map row is valid.
     */
    private boolean isValidInput(String row, int length) {
        if (row.length() != length) {
            return false;
        }

        for (int i = 0; i < row.length(); i++) {
            /*
             * I am lazy. Believe it.
             */
            if (!ArrayHelper.contains(this.availableFields, row.charAt(i) + "")) {
                return false;
            }

        }

        return true;

    }

    /**
     * Check is the map has at least 2 Station.
     *
     * @return true if the map is valid.
     */
    private boolean isValidMap() {
        int stationCounter = 0;

        for (String row : mapInString) {
            for (int i = 0; i < row.length(); i++) {
                if (MapHelper.getField(row.charAt(i) + "") instanceof Station) {
                    stationCounter++;
                }
                if (stationCounter >= 2) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Reads the start position of Randal from the default input stream.
     */
    private void readStartPosition() {
        Scanner scanner = new Scanner(System.in);
        String pos;
        boolean isStartPositionNotValid = true;
        while (isStartPositionNotValid) {
            System.out.println("Start position (hint: it must be a Station): (ex.:6x2)");
            pos = scanner.nextLine();
            this.startPosition = InputValidator.parseStartPosition(pos.split("x"));

            if (isStartPositionNotValid = !MapHelper.isStartPositionValid(this.mapFields, this.startPosition, this.mapSize)) {
                System.out.println("Wrong start position!");
            }
            if (!isStartPositionNotValid) {
                System.out.println("Start position is: " + this.startPosition[0] + "x" + this.startPosition[1]);
                isStartPositionNotValid = shouldNotTryAgain();
            }

        }
    }

    /**
     * Reads the number of allowed Movements from the default input stream.
     */
    private void readMovements() {
        HashMap<String, Integer> movements = new HashMap<>();
        movements.put((new Up()).getName(), 0);
        movements.put((new Down()).getName(), 0);
        movements.put((new Left()).getName(), 0);
        movements.put((new Right()).getName(), 0);
        movements = readElements(movements);
        this.movements = movements;
    }

    /**
     * Reads the number of allowed Movement Helpers from the default input stream.
     */
    private void readMovementHelpers() {
        HashMap<String, Integer> movementHelpers = new HashMap<>();
        movementHelpers.put((new Bridge()).getName(), 0);
        movementHelpers.put((new Dynamite()).getName(), 0);
        movementHelpers = readElements(movementHelpers);
        this.movementHelpers = movementHelpers;
    }

    /**
     * Magic method for Movement/Movement Helper reading from the default input stream.
     *
     * @param data Elements that should be read
     * @return Readed elements.
     */
    private HashMap<String, Integer> readElements(HashMap<String, Integer> data) {
        Scanner scanner = new Scanner(System.in);
        boolean notDone = true;
        while (notDone) {
            data.forEach((key, value) -> {
                System.out.println("Number of " + key + " available:");
                try {
                    int i = Integer.parseInt(scanner.nextLine());
                    if (i < 0) {
                        throw new Exception("Must be greater than or equal to 0.");
                    }
                    data.put(key, i);
                } catch (Exception e) {
                    data.put(key, 0);
                }
            });
            data.forEach((key, value) ->
                    System.out.println("Number of " + key + " available: " + data.get(key))
            );
            notDone = shouldNotTryAgain();
        }
        return data;
    }


    private void readMapName() {
        Scanner scanner = new Scanner(System.in);
        boolean notDone = true;
        String mapName;
        System.out.println("Map name:");
        do {
            mapName = scanner.nextLine();
            if (Play.getAvailableMaps().contains(mapName)) {
                System.out.println("This map is already exists!");
                System.out.println("Do you want to rewrite it? (yes, no)");
                String key = scanner.nextLine();
                if (key.equals("yes") || key.length() == 0) {
                    Play.removeMap(mapName);
                    notDone = false;
                }
            } else {
                notDone = false;
            }
        } while (notDone);
        this.mapName = mapName;
    }

    /**
     * Reads the number of allowed Repeats from the default input stream.
     */
    private void readNumberOfAllowedRepeats() {
        Scanner scanner = new Scanner(System.in);
        String input;
        boolean notDone = true;
        System.out.println("How many repeats do you want to make available?");
        while (notDone) {
            input = scanner.nextLine();
            try {
                this.numberOfAllowedRepeats = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("Bad input. Number pls.");
            }
            this.numberOfAllowedRepeats = this.numberOfAllowedRepeats <= 0 ? 0 : this.numberOfAllowedRepeats;
            System.out.println("Number of allowed repeats: " + this.numberOfAllowedRepeats);
            notDone = shouldNotTryAgain();
        }
    }

    /**
     * Ask's the user to choose if the given input is valid to him/her or not.
     */
    private boolean shouldNotTryAgain() {
        System.out.println("Is it Okey? (yes, no)");
        String key = (new Scanner(System.in)).nextLine();
        return !(key.equals("yes") || key.length() == 0);
    }

    /**
     * Saves the map.
     */
    private void save() {
        this.initMap();
        Play.addMapToAvailableMaps(this.map.getName());
        MapLoader ml = new MapLoader();
        ml.saveObject(this.map.getName() + ".ser", this.map);
    }

    /**
     * Initialize Map before it could be saved.
     */
    private void initMap() {
        this.map = new Map();
        this.map.init(
                this.mapFields,
                this.startPosition,
                this.movements,
                this.movementHelpers,
                this.numberOfAllowedRepeats
        );
        this.map.setName(this.mapName);
    }
}

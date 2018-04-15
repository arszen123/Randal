package game;

import game.fielDecorator.AvailableMapsLoader;
import game.fielDecorator.MapLoader;
import game.map.Map;
import game.movement.*;
import game.movement.helper.Bridge;
import game.movement.helper.Dinamit;
import game.movement.helper.MovementHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Game {
    private ArrayList<String> availableMaps = new ArrayList<String>();
    private HashMap<String, Integer> allowedMovements;
    private HashMap<String, Integer> allowedMovementHelpers;
    private int selectedMap = 0;

    public void init() {
        AvailableMapsLoader aml = new AvailableMapsLoader();
        availableMaps = aml.loadObject("aml.ser");
    }

    public void testData() {
        availableMaps.add("test.ser");
        availableMaps.add("test1.ser");
        availableMaps.add("imposible.ser");
    }

    public void start() {
        selectMap();
        startGame();

    }

    private void selectMap() {
        if (availableMaps == null || availableMaps.isEmpty()) {
            System.out.println("Sorry there is no maps available!");
            return;
        }
        availableMaps.forEach((s -> {
            System.out.println(availableMaps.indexOf(s)+1+". "+s);
        }));
        Scanner scanner = new Scanner(System.in);
        boolean failed = true;
        while (failed) {
            String readData = scanner.nextLine();
            if(readData.equals("q")) {
                endGame();
            }
            try {
                int tmpSelectedMap = Integer.parseInt(readData);
                if (tmpSelectedMap<1 || tmpSelectedMap>availableMaps.size()) {
                    throw new Exception();
                }
                this.selectedMap = tmpSelectedMap -1;
                failed = false;
            } catch (Exception e) {
                System.out.println("Numbers allowed only from the list!");
            }
        }

    }

    private void startGame() {
        Map map;
        map = (new MapLoader()).loadObject(this.availableMaps.get(this.selectedMap));
        this.allowedMovements = map.getAllowedMovements();
        this.allowedMovementHelpers = map.getAllowedMovementHelpers();
        this.drawAllowedMovements();
        map.draw();
        Scanner input = new Scanner(System.in);
        String[] moves = new String[]{"w","a","s","d"};
        String[] helpers = new String[]{"t","b"};
        Movement movement = null;
        MovementHelper movementHelper = null;
        while (true) {
            String key = input.nextLine();
            if (contains(moves, key)) {
                movement = readMovement(key);
            } else if(contains(helpers, key)) {
                if (key.equals("t")) {
                    movementHelper = new Dinamit();
                }
                if (key.equals("b")) {
                    movementHelper = new Bridge();
                }
                key = input.nextLine();
                movement = readMovement(key);
            } else if(key.equals("r")) {

            }
            map.useMovemenetHelper(movement, movementHelper);
            map.nextMove(movement);
            this.drawAllowedMovements();
            map.draw();
        }
    }

    private boolean contains(Object[] objs, Object obj){
        for (Object object: objs) {
            if (object.equals(obj)) {
                return true;
            }
        }
        return false;
    }

    private Movement readMovement(String key) {
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

    public void drawAllowedMovements() {
        System.out.println(
                "Up: " + this.allowedMovements.get((new Up()).getMovementName()) +
                "Down: " + this.allowedMovements.get((new Down()).getMovementName()) +
                "Left: " + this.allowedMovements.get((new Left()).getMovementName()) +
                "Right: " + this.allowedMovements.get((new Right()).getMovementName())
        );
    }

    private void endGame() {
        System.exit(0);
    }


}

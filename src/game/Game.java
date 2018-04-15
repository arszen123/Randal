package game;

import game.fielDecorator.AvailableMapsLoader;
import game.fielDecorator.MapLoader;
import game.map.Map;
import game.movement.*;
import game.movement.helper.Bridge;
import game.movement.helper.Dinamit;
import game.movement.helper.MovementHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Game {
    private ArrayList<String> availableMaps = new ArrayList<String>();
    private HashMap<String, Integer> allowedMovements;
    private HashMap<String, Integer> allowedMovementHelpers;
    private int selectedMap = 0;
    private String[] moves = new String[]{"w", "a", "s", "d"};
    private String[] helpers = new String[]{"t", "b"};

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
            System.out.println(availableMaps.indexOf(s) + 1 + ". " + s);
        }));
        Scanner scanner = new Scanner(System.in);
        boolean failed = true;
        while (failed) {
            String readData = scanner.nextLine();
            if (readData.equals("q")) {
                endGame();
            }
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

    private void startGame() {
        Map map = loadMap();
        this.drawUserInterface(map);
        Scanner input = new Scanner(System.in);
        String signals = input.nextLine();
        makeMovement(signals, map);
    }

    private int doSomeMagic(String signals, Map map) {
        int numberOfRepeats = 0;
        int i = 0;
        boolean notChar = true;
        int result = signals.length()+1;
        while(notChar) {
            try{
                numberOfRepeats = numberOfRepeats * 10 + Integer.parseInt(signals.charAt(i)+"");
                i++;
            }catch (Exception e) {
                notChar = false;
            }
        }
        signals = signals.substring(i);
        System.out.println("Magic:" + numberOfRepeats);
        System.out.println("Magic:" + signals);

        for(i=0;i<numberOfRepeats;i++){
            makeMovement(signals, map);
        }
        return result;
    }

    private void makeMovement(String signals, Map map) {
        Movement movement;
        MovementHelper movementHelper;
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
                j += doSomeMagic(getRepeate(signals.substring(j+1)), map);
                System.out.println("----");
                System.out.println(signals);
                System.out.println(signals.substring(j));
                System.out.println("----");
            }
            if (movement != null) {
                map.useMovementHelper(movement, movementHelper);
                map.nextMove(movement);
            }
            this.drawUserInterface(map);
        }
        System.out.println();
        System.out.println();
        System.out.println(signals);
        System.out.println();
        System.out.println();
    }

    private Map loadMap() {
        Map map = (new MapLoader()).loadObject(this.availableMaps.get(this.selectedMap));
        this.allowedMovements = map.getAllowedMovements();
        this.allowedMovementHelpers = map.getAllowedMovementHelpers();
        return map;
    }

    private void drawUserInterface(Map map) {
        this.drawAllowedMovements();
        map.draw();
    }

    private boolean contains(Object[] objs, Object obj) {
        for (Object object : objs) {
            if (object.equals(obj)) {
                return true;
            }
        }
        return false;
    }

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

    private MovementHelper getMovementHelper(String key) {
        MovementHelper movementHelper = null;
        if (key.equals("t")) {
            movementHelper = new Dinamit();
        }
        if (key.equals("b")) {
            movementHelper = new Bridge();
        }
        return movementHelper;
    }

    private String getRepeate(String signals) {
        int findStopSignals = 1;
        StringBuilder stringBuilder = new StringBuilder();
        boolean doBreak = false;
        for (int i = 0;i<signals.length();i++){
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
            if(doBreak) {
                break;
            }
            stringBuilder.append(key);
        }
        return stringBuilder.toString();
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

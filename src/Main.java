import game.Game;
import game.map.Map;
import game.fielDecorator.MapLoader;
import game.map.field.*;
import game.movement.*;

import java.util.HashMap;

public class Main {

    public static void main(String[] args){
        createMap();
        test1();
        Game game = new Game();
        //game.init();
        game.testData();
        game.start();

    }

    static void createMap() {

        Field[][] map = new Field[][]{
                {new Station(), new Praire(), new Praire(), new Praire(), new Praire(), new Praire()},
                {new Praire(), new Water(), new Water(), new Water(), new Water(), new Praire()},
                {new Praire(), new Water(), new Rock(), new Rock(), new Water(), new Praire()},
                {new Praire(), new Water(), new Rock(), new Rock(), new Water(), new Rock()},
                {new Praire(), new Water(), new Water(), new Water(), new Water(), new Water()},
                {new Praire(), new Praire(), new Praire(), new Rock(), new Water(), new Station()},
        };

        HashMap<String, Integer> allowedM = new HashMap<String, Integer>();
        allowedM.put((new Up()).getMovementName(), 20);
        allowedM.put((new Down()).getMovementName(), 20);
        allowedM.put((new Left()).getMovementName(), 20);
        allowedM.put((new Right()).getMovementName(), 20);

        HashMap<String, Integer> allowedMH = new HashMap<>();
        allowedMH.put("Dinamit", 5);
        allowedMH.put("Bridge", 5);

        Map test = new Map();
        test.init(map,new int[]{0,0},allowedM, null, 10);
        MapLoader ml = new MapLoader();
        ml.saveObject("imposible.ser", test);
    }
    static void test1() {

        Field[][] map = new Field[][]{
                {new Station(), new Praire(), new Praire(), new Praire(), new Praire(), new Praire()},
                {new Praire(), new Praire(), new Praire(), new Praire(), new Praire(), new Praire()},
                {new Praire(), new Praire(), new Praire(), new Praire(), new Praire(), new Praire()},
                {new Praire(), new Praire(), new Praire(), new Praire(), new Praire(), new Praire()},
                {new Praire(), new Praire(), new Praire(), new Praire(), new Praire(), new Praire()},
                {new Praire(), new Praire(), new Praire(), new Praire(), new Praire(), new Station()},
        };

        HashMap<String, Integer> allowedM = new HashMap<String, Integer>();
        allowedM.put((new Up()).getMovementName(), 20);
        allowedM.put((new Down()).getMovementName(), 20);
        allowedM.put((new Left()).getMovementName(), 20);
        allowedM.put((new Right()).getMovementName(), 20);

        HashMap<String, Integer> allowedMH = new HashMap<>();
        allowedMH.put("Dinamit", 5);
        allowedMH.put("Bridge", 5);

        Map test = new Map();
        test.init(map,new int[]{0,0},allowedM, null, 10);
        MapLoader ml = new MapLoader();
        ml.saveObject("imposible.ser", test);
    }

}

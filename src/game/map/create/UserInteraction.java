package game.map.create;

public class UserInteraction {

    public static void printMapHelper() {
        System.out.println("S: Station P: Prairie W: Water *: Rock");
        System.out.println("Map:");
    }

    public static void drawMap(String[] map) {
        System.out.print("  ");
        for (int i = 0; i < map[0].length(); i++) {
            System.out.print(" " + i);
        }
        System.out.println();
        for (int i = 0; i < map.length; i++) {
            System.out.print(i + ".");
            for (int j = 0; j < map[i].length(); j++) {
                System.out.print(" " + map[i].charAt(j));
            }
            System.out.println();
        }
    }

    public static void printBadMapMessage() {
        System.out.println("You give a bad map. Try again. (hint: 2 station is needed.)");
    }
}

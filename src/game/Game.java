package game;

import java.util.Scanner;

public class Game {
    private static boolean play = true;
    private int selectedOption;

    /**
     * Ends the game.
     * <p>
     * It's the best part of this.
     * Terminates the game
     * </p>
     */
    private static void endGame() {
        play = false;
        System.exit(0);
    }

    /**
     * Starts the game.
     */
    public void start() {
        Play.init();
        while (play) {
            this.printOptions();
            this.readOption();
            if (selectedOption == 1) {
                Play playGame = new Play();
                playGame.play();
            }
            if (selectedOption == 2) {
                CreateMap cm = new CreateMap();
                cm.createMap();
            }
            if (selectedOption == 3) {
                this.printHelp();
            }
            if (selectedOption == 4) {
                this.printAbout();
            }
        }
    }

    /**
     * Prints what can do.
     */
    private void printOptions() {
        System.out.println("Randal v1.1!\n Welcome!");
        System.out.println("1 - Play");
        System.out.println("2 - Create Map");
        System.out.println("h - Help");
        System.out.println("a - About");
        System.out.println("q -  Quit");
    }

    /**
     * Reads what to do.
     */
    private void readOption() {
        boolean isNotValidSignal;
        Scanner sc = new Scanner(System.in);
        String input;
        do {
            input = sc.nextLine();
            isNotValidSignal = false;
            if (input.equals("1")) {
                this.selectedOption = 1;
            } else if (input.equals("2")) {
                this.selectedOption = 2;
            } else if (input.equals("h")) {
                this.selectedOption = 3;
            } else if (input.equals("a")) {
                this.selectedOption = 4;
            } else if (input.equals("q") || input.equals("quit") || input.equals("exit")) {
                endGame();
            } else {
                isNotValidSignal = true;
            }
        } while (isNotValidSignal);
    }

    private void printHelp() {
        System.out.println("Playing:");
        System.out.println("Movements:");
        System.out.println("\t\tw");
        System.out.print("\ta");
        System.out.print("\ts");
        System.out.print("\td");
        System.out.println();
        System.out.println("Movement Helpers:");
        System.out.println("Dynamite (Rock -> Prairie): t");
        System.out.println("Bridge (Water -> Prairie): b");
        System.out.println("Every Movement Helpers must follow a movement.");
        System.out.println("Repeats: r[number][movement]p");
        System.out.println();

        System.out.println("Create Map:");
        System.out.println("Maps can lose if the file where it's saved is corrupted.\nSo be sore no one touching it.\nIt's a big leak, but better then nothing!");
        System.out.println("Available Fields:");
        System.out.println("Prairie:\tP");
        System.out.println("Rock: \t\t*");
        System.out.println("Water:\t\tW");
        System.out.println("Station:\tS");
        System.out.println();
    }

    private void printAbout() {
        System.out.println("\tRandal v1.1");
        System.out.println("You have to go through all the station on the Map with Randal.\n" +
                "You can walk on Prairie only. If Randal steps on a Water field he will die. ;(\n" +
                "With Movement Helpers randal can make Prairie from Water and Rock.\n");
        System.out.println();
        System.out.println("Enjoy the game! :D");
    }

}

package game.map;

import game.map.field.*;
import game.movement.Movement;
import game.movement.helper.Bridge;
import game.movement.helper.Dynamite;
import game.movement.helper.MovementHelper;

import java.io.Serializable;
import java.util.HashMap;

/**
 * It contains the map model, and some interaction with the player.
 */
public class Map implements Cloneable, Serializable {
    private final int X = 0;
    private final int Y = 1;
    private final int[] size = new int[2];
    private String name;
    private Field[][] map;
    private int[] startPosition;
    private HashMap<String, Integer> allowedMovements;
    private HashMap<String, Integer> allowedMovementHelpers;
    private int numberOfAllowedRepeats;
    private boolean isRandalDead = false;

    /**
     * Initialize Map model
     *
     * @param map                    Map fields
     * @param startPosition          Position where randal is spawned
     * @param allowedMovements       Number of allowed Movements
     * @param allowedMovementHelpers Number of allowed Movement Helpers
     * @param numberOfAllowedRepeats Number of allowed Repeats
     */
    public void init(
            Field[][] map,
            int[] startPosition,
            HashMap<String, Integer> allowedMovements,
            HashMap<String, Integer> allowedMovementHelpers,
            int numberOfAllowedRepeats
    ) {
        this.map = map;
        this.size[Y] = map.length;
        this.size[X] = map[0].length;
        this.startPosition = startPosition;
        this.allowedMovements = allowedMovements;
        this.allowedMovementHelpers = allowedMovementHelpers;
        this.numberOfAllowedRepeats = numberOfAllowedRepeats;
    }

    /**
     * Moves Randal to the desired destination
     *
     * @param movement Movement where should Randal move
     */
    public void nextMove(Movement movement) {
        int[] newPosition = this.getNewPosition(movement.getWhereToMove());
        if (isNextMoveAllowed(newPosition)) {
            if (!(this.map[this.startPosition[X]][this.startPosition[Y]] instanceof Station)) {
                this.map[this.startPosition[X]][this.startPosition[Y]] = new Road();
            }
            if ((this.map[newPosition[X]][newPosition[Y]]) instanceof Station){
                ((Station)this.map[newPosition[X]][newPosition[Y]]).setTouched(true);
                System.out.println(newPosition[X] +" " +newPosition[Y]);
            }
            this.startPosition = newPosition;
        }

        if (isValidArrayIndex(newPosition) && (this.map[newPosition[X]][newPosition[Y]] instanceof Water)) {
            isRandalDead = true;
        }
    }

    /**
     * Returns Randal new position
     *
     * @param stepTo Randal next position from now
     * @return Randal new position
     */
    private int[] getNewPosition(int[] stepTo) {
        return new int[]{this.startPosition[X] + stepTo[X], this.startPosition[Y] + stepTo[Y]};
    }

    /**
     * Returns is the next move allowed or not.
     *
     * @param newPosition The position where randal want to move
     * @return true if the new position field is allowed to Randal, to step.
     */
    private boolean isNextMoveAllowed(int[] newPosition) {
        return isValidArrayIndex(newPosition) &&
                this.map[newPosition[X]][newPosition[Y]].allowedToMoveHere();
    }

    /**
     * Check that the new position is not out of map range
     *
     * @param newPosition The position where randal want to move
     * @return true if new position indexes is between the map dimension
     */
    private boolean isValidArrayIndex(int[] newPosition) {
        return newPosition[X] < size[Y] &&
                newPosition[X] >= 0 &&
                newPosition[Y] < size[X] &&
                newPosition[Y] >= 0;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Draws the actual map state to the default output.
     */
    public void draw() {
        for (int i = 0; i < size[Y]; i++) {
            for (int j = 0; j < size[X]; j++) {
                if (i == startPosition[X] && j == startPosition[Y]) {
                    System.out.print(" O");
                } else {
                    System.out.print(" " + map[i][j].drawingElement());
                }
            }
            System.out.println();
            System.out.println();
        }
    }

    /**
     * Default getter for allowedMovements
     *
     * @return allowedMovements in HashMap<String, Integer>
     */
    public HashMap<String, Integer> getAllowedMovements() {
        return this.allowedMovements;
    }

    /**
     * Default getter for allowedMovementHelpers
     *
     * @return allowedMovementHelpers in HashMap<String, Integer>
     */
    public HashMap<String, Integer> getAllowedMovementHelpers() {
        return this.allowedMovementHelpers;
    }

    /**
     * Default getter for numberOfAllowedRepeats
     *
     * @return numberOfAllowedRepeats in Integer
     */
    public int getNumberOfAllowedRepeats() {
        return numberOfAllowedRepeats;
    }

    /**
     * Uses a Movement Helper on a field
     *
     * @param movement       which destination should the Movement Helper used.
     * @param movementHelper Movement Helper that should used.
     */
    public void useMovementHelper(Movement movement, MovementHelper movementHelper) {
        int[] newPosition = getNewPosition(movement.getWhereToMove());
        if (isValidArrayIndex(newPosition)) {
            Field field = map[newPosition[X]][newPosition[Y]];
            if (field instanceof Rock && movementHelper instanceof Dynamite) {
                map[newPosition[X]][newPosition[Y]] = new Prairie();
            }
            if (field instanceof Water && movementHelper instanceof Bridge) {
                map[newPosition[X]][newPosition[Y]] = new Prairie();
            }
        }
    }

    /**
     * Checks if randal gone touched all station
     *
     * @return true if all station is touched
     */
    public boolean isMapCompleted() {
        for (int i = 0; i < size[Y]; i++) {
            for (int j = 0; j < size[X]; j++) {
                if (map[i][j] instanceof Station) {
                    if (!isStationOkey(i, j)) {
                        return false;
                    }
                }
            }
            System.out.println();
        }
        return true;
    }

    /**
     * Checks is Randal touched the given Station at least once
     *
     * @param x Index
     * @param y Index
     * @return true if there is at least one road near the stations
     */
    private boolean isStationOkey(int x, int y) {
        return ((Station)map[x][y]).isTouched();
    }

    /**
     * Is randal dead?
     *
     * @return true if poor Randal is dead
     */
    public boolean isRandalDead() {
        return this.isRandalDead;
    }

    /**
     * Default getter for name
     *
     * @return name Name of the map
     */
    public String getName() {
        return this.name;
    }

    /**
     * Default setter for name
     *
     * @param name Name of the map
     */
    public void setName(String name) {
        this.name = name;
    }

    public void touchStartStation() {
        ((Station)this.map[this.startPosition[X]][this.startPosition[Y]]).setTouched(true);
    }
}

package game.map;

import game.map.field.*;
import game.movement.*;
import game.movement.helper.Bridge;
import game.movement.helper.Dinamit;
import game.movement.helper.MovementHelper;

import java.io.Serializable;
import java.util.HashMap;

public class Map implements Cloneable, Serializable {
    private final int X = 0;
    private final int Y = 1;
    private Field[][] map;
    private int[] startPosition;
    private int[] size = new int[2];
    private HashMap<String, Integer> allowedMovements;
    private HashMap<String, Integer> allowedMovementHelpers;
    private int numberOfAllowedRepeats;

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

    public void nextMove(Movement movement) {
        int[] newPosition = this.getNewPosition(movement.getWhereToMove());
        if (isNextMoveAllowed(newPosition)) {
            if (!(this.map[this.startPosition[X]][this.startPosition[Y]] instanceof Station)) {
                this.map[this.startPosition[X]][this.startPosition[Y]] = new Road();
            }
            this.startPosition = newPosition;
            this.decreaseAllowedMovement(movement);
            System.out.println("Moved");
        } else {
            System.out.println("Not Moved");
            //there should check some field types
        }
    }

    private int[] getNewPosition(int[] stepTo) {
        return new int[]{this.startPosition[X] + stepTo[X], this.startPosition[Y] + stepTo[Y]};
    }


    private boolean isNextMoveAllowed(int[] newPosition) {
        return newPosition[X] < size[X] &&
                newPosition[X] >= 0 &&
                newPosition[Y] < size[Y] &&
                newPosition[Y] >= 0 &&
                this.map[newPosition[X]][newPosition[Y]].allowedToMoveHere();
    }

    private void decreaseAllowedMovement(Movement movement) {
        int tmp = this.allowedMovements.get(movement.getMovementName()) - 1;
        this.allowedMovements.put(movement.getMovementName(), tmp);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String toString() {
        return "NUM: " + numberOfAllowedRepeats;
    }

    public void draw() {
        for (int i=0;i<size[X];i++) {
            for (int j=0;j<size[Y];j++) {
                if(i == startPosition[X] && j == startPosition[Y]) {
                    System.out.print("O");
                } else {
                    System.out.print(map[i][j].drawingElement());
                }
            }
            System.out.println();
        }
    }

    public HashMap<String, Integer> getAllowedMovements() {
        return this.allowedMovements;
    }

    public HashMap<String, Integer> getAllowedMovementHelpers() {
        return this.allowedMovementHelpers;
    }

    public void useMovemenetHelper(Movement movement, MovementHelper movementHelper){
        int[] newPosition = getNewPosition(movement.getWhereToMove());
        Field field = map[newPosition[X]][newPosition[Y]];
        if (field instanceof Rock  && movementHelper instanceof Dinamit) {
            map[newPosition[X]][newPosition[Y]] = new Praire();
        }
        if (field instanceof Water && movementHelper instanceof Bridge) {
            map[newPosition[X]][newPosition[Y]] = new Praire();
        }
    }
}

package game.movement;

import java.io.Serializable;

/**
 *
 */
public interface Movement extends Serializable{
    final static int X = 0;
    final static int Y = 1;
    int[] whereToMove = new int[2];

    /**
     *
     * @return int[]
     */
    int[] getWhereToMove();

    String getMovementName();
}

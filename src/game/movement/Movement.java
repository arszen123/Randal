package game.movement;

import java.io.Serializable;

/**
 *
 */
public interface Movement extends Serializable {
    int X = 0;
    int Y = 1;
    int[] whereToMove = new int[2];

    /**
     * @return int[]
     */
    int[] getWhereToMove();

    String getName();
}

package game.map.field;

import java.io.Serializable;

/**
 *
 */
public interface Field extends Serializable{
    /**
     * @return boolean
     */
    boolean allowedToMoveHere();

    String drawingElement();
}

package game.map.field;

import game.movement.helper.MovementHelper;

import java.io.Serializable;

/**
 *
 */
public interface Field extends Serializable{
    /**
     *
     * @return boolean
     */
    boolean allowedToMoveHere();

    /**
     * @param movementHelper MovementHelper
     * @return boolean
     */
    boolean allowedToMoveHere(MovementHelper movementHelper);

    String drawingElement();
}

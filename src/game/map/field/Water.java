package game.map.field;

import game.movement.helper.Bridge;
import game.movement.helper.MovementHelper;

public class Water implements Field {
    @Override
    public boolean allowedToMoveHere() {
        return allowedToMoveHere(null);
    }

    @Override
    public boolean allowedToMoveHere(MovementHelper movementHelper) {
        return false;
    }


    @Override
    public String drawingElement() {
        return "W";
    }
}

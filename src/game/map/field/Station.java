package game.map.field;

import game.movement.helper.MovementHelper;

public class Station implements Field {
    @Override
    public boolean allowedToMoveHere() {
        return allowedToMoveHere(null);
    }

    @Override
    public boolean allowedToMoveHere(MovementHelper movementHelper) {
        return true;
    }


    @Override
    public String drawingElement() {
        return "S";
    }
}

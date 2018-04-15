package game.map.field;

import game.movement.helper.MovementHelper;

public class Praire implements Field{
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
        return "P";
    }
}

package game.map.field;

public class Prairie implements Field {

    @Override
    public boolean allowedToMoveHere() {
        return true;
    }

    @Override
    public String drawingElement() {
        return "P";
    }
}

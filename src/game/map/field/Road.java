package game.map.field;

public class Road implements Field {
    @Override
    public boolean allowedToMoveHere() {
        return false;
    }

    @Override
    public String drawingElement() {
        return "R";
    }
}

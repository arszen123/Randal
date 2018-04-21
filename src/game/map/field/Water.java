package game.map.field;

public class Water implements Field {
    @Override
    public boolean allowedToMoveHere() {
        return false;
    }


    @Override
    public String drawingElement() {
        return "W";
    }
}

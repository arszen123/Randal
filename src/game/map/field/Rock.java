package game.map.field;

public class Rock implements Field {

    @Override
    public boolean allowedToMoveHere() {
        return false;
    }


    @Override
    public String drawingElement() {
        return "*";
    }
}

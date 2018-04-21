package game.map.field;

public class Station implements Field {

    private boolean touched = false;

    @Override
    public boolean allowedToMoveHere() {
        return true;
    }


    @Override
    public String drawingElement() {
        return "S";
    }

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }
}

package game.movement;

class Move implements Movement {

    public int[] getWhereToMove() {
        return this.whereToMove;
    }

    public String getMovementName() {
        return this.getClass().getSimpleName();
    }
}

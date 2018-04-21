package game.movement;

class Move implements Movement {

    /**
     * @return destination where should Randal move
     */
    public int[] getWhereToMove() {
        return this.whereToMove;
    }

    /**
     * @return the class short name
     */
    public String getName() {
        return this.getClass().getSimpleName();
    }
}

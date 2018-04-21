package game.movement.helper;

/**
 * You cant explode dinamits on Rocks.
 */
public class Dynamite implements MovementHelper {
    public String getName() {
        return this.getClass().getSimpleName();
    }
}

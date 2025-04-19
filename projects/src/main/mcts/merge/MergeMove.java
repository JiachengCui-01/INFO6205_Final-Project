package mcts.merge;

import mcts.core.Move;

/**
 * Move class for 2048, representing the direction of a slide.
 */
public class MergeMove implements Move<MergeGame> {

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private final Direction direction;
    private final int player;

    public MergeMove(int player, Direction direction) {
        this.player = player;
        this.direction = direction;
    }

    @Override
    public int player() {
        return player;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "Move " + direction.name();
    }
}

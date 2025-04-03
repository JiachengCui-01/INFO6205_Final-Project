/*
 * Copyright (c) 2024. Robin Hillyard
 */

package mcts.core;

/**
 * This interface defines the behavior of a Move in a Game.
 *
 * @param <G>
 */
public interface Move<G extends Game<G>> {
    /**
     * The player of this Move.
     *
     * @return the player.
     */
    int player();
}

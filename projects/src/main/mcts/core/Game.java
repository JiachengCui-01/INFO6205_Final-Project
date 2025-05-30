/*
 * Copyright (c) 2024. Robin Hillyard
 */

package mcts.core;

/**
 * This interface describes the behavior of a Game for the purpose of testing the Monte Carlo Tree Search utilities.
 */
public interface Game<G extends Game<G>> {

    /**
     * Get the starting state for this Game.
     *
     * @return a State of G.
     */
    State<G> start();

    /**
     * The opening player for this type of game.
     *
     * @return a non-negative integer.
     */
    int opener();
}

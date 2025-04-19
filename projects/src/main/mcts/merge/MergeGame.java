package mcts.merge;

import mcts.core.Game;
import mcts.core.State;
import java.util.Random;

/**
 * Game class for marge game - 2048.
 */
public class MergeGame implements Game<MergeGame> {

    private final Random random;

    public MergeGame() {
        this.random = new Random();
    }

    public MergeGame(long seed) {
        this.random = new Random(seed);
    }

    public Random getRandom() {
        return random;
    }

    @Override
    public State<MergeGame> start() {
        return new MergeState(this);
    }

    @Override
    public int opener() {
        return 0; // 2048 is single-player, player is always 0
    }

    @Override
    public String toString() {
        return "2048 Merge Game";
    }
}

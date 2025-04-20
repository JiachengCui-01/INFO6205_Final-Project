package mcts.merge;

import mcts.core.Node;
import org.junit.Test;

import static org.junit.Assert.*;

public class MergeMCTSTest {

    @Test
    public void testRunSearchReturnsValidMove() {
        MergeGame game = new MergeGame();
        MergeState initialState = (MergeState) game.start();
        MergeNode root = new MergeNode(initialState, null);  

        MCTS mcts = new MCTS(root);
        mcts.runSearch(100);

        MergeMove bestMove = root.bestMove();  

        assertNotNull("MCTS should return a non-null move", bestMove);
        assertTrue("Returned move should be legal in the initial state",
                initialState.getLegalMoves().contains(bestMove));
    }

    @Test
    public void testRunSearchMultipleTimes() {
        MergeGame game = new MergeGame();
        MergeState initialState = (MergeState) game.start();

        for (int i = 0; i < 3; i++) {
            MergeNode root = new MergeNode(initialState, null);
            MCTS mcts = new MCTS(root);
            mcts.runSearch(50);
            MergeMove move = root.bestMove();

            assertNotNull("Search run #" + (i + 1) + " returned null", move);
        }
    }

    @Test
    public void testRunSearchWithZeroIterations() {
        MergeGame game = new MergeGame();
        MergeState initialState = (MergeState) game.start();
        MergeNode root = new MergeNode(initialState, null);

        MCTS mcts = new MCTS(root);
        mcts.runSearch(0);

        MergeMove move = root.bestMove();

        assertTrue("Move should either be null or legal",
                move == null || initialState.getLegalMoves().contains(move));
    }
}

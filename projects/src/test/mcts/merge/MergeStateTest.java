package mcts.merge;

import mcts.core.Move;
import org.junit.Test;
import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.*;

public class MergeStateTest {

    @Test
    public void testInitialStateHasTwoTiles() {
        MergeGame game = new MergeGame();
        MergeState state = new MergeState(game);
        int count = countNonZeroTiles(state);
        assertEquals(2, count);
    }

    @Test
    public void testMovesAreAvailableInitially() {
        MergeGame game = new MergeGame();
        MergeState state = new MergeState(game);
        Collection<Move<MergeGame>> moves = state.moves(0);
        assertFalse(moves.isEmpty());
    }

    @Test
    public void testNextStateAfterMove() {
        MergeGame game = new MergeGame();
        MergeState state = new MergeState(game);
        Move<MergeGame> move = state.moves(0).iterator().next();
        MergeState nextState = (MergeState) state.next(move);
        assertNotSame(state, nextState);
    }

    @Test
    public void testIsTerminalFalseInitially() {
        MergeGame game = new MergeGame();
        MergeState state = new MergeState(game);
        assertFalse(state.isTerminal());
    }

    @Test
    public void testWinnerIsAlwaysEmpty() {
        MergeGame game = new MergeGame();
        MergeState state = new MergeState(game);
        Optional<Integer> winner = state.winner();
        assertFalse(winner.isPresent());
    }

    private int countNonZeroTiles(MergeState state) {
        int count = 0;
        try {
            java.lang.reflect.Field gridField = MergeState.class.getDeclaredField("grid");
            gridField.setAccessible(true);
            int[][] grid = (int[][]) gridField.get(state);
            for (int[] row : grid) {
                for (int val : row) {
                    if (val != 0) count++;
                }
            }
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }
        return count;
    }
}

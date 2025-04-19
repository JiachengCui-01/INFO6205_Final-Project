package mcts.merge;

import mcts.core.State;
import org.junit.Test;

import static org.junit.Assert.*;

public class MergeGameTest {

    @Test
    public void testGetRandomReturnsInstance() {
        MergeGame game = new MergeGame();
        assertNotNull(game.getRandom());
    }

    @Test
    public void testOpenerAlwaysZero() {
        MergeGame game = new MergeGame();
        assertEquals(0, game.opener());
    }
    
    @Test
    public void testInitialStateIsValid() {
        MergeGame game = new MergeGame();
        MergeState state = new MergeState(game);
        assertNotNull(state);
        assertFalse(state.isTerminal());
        assertEquals(game, state.game());
    }
    
}

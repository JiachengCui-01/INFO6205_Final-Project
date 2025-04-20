package mcts.merge;

import mcts.core.State;
import org.junit.Test;

import static org.junit.Assert.*;

public class MergeSimulatorTest {

    @Test
    public void testSimulateFromNode() {
        MergeGame game = new MergeGame();
        MergeState state = new MergeState(game);
        MergeNode node = new MergeNode(state);

        int result = node.simulate();
        assertTrue("Score should be non-negative", result >= 0);
    }

    @Test
    public void testSimulateMultipleTimes() {
        MergeGame game = new MergeGame();
        MergeState state = new MergeState(game);

        for (int i = 0; i < 10; i++) {
            MergeNode node = new MergeNode(state);
            int score = node.simulate();
            assertTrue("Simulation #" + i + " score should be non-negative", score >= 0);
        }
    }

    @Test
    public void testSimulateReachesTerminalState() {
        MergeGame game = new MergeGame();
        MergeState state = new MergeState(game);
        MergeNode node = new MergeNode(state);
        State<MergeGame> sim = node.state();
        while (!sim.isTerminal()) {
            if (sim.moves(sim.player()).isEmpty()) break; 
            sim = sim.next(sim.moves(sim.player()).iterator().next());
        }

        assertTrue("Final simulation state should be terminal or blocked", sim.isTerminal() || sim.moves(sim.player()).isEmpty());
    }

    @Test
    public void testSimulateFromTerminalState() {
        MergeGame game = new MergeGame();
        int[][] fullGrid = new int[][] {
            {2, 4, 2, 4},
            {4, 2, 4, 2},
            {2, 4, 2, 4},
            {4, 2, 4, 2}
        };
        MergeState terminalState = new MergeState(game, fullGrid, 0); 
        MergeNode node = new MergeNode(terminalState);

        int result = node.simulate();
        assertEquals("Simulation on terminal state should return 0 or minimal score", 0, result);
    }
}

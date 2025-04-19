package mcts.merge;

import org.junit.Test;
import mcts.core.State;
import static org.junit.Assert.*;

public class MergeSimulatorTest {

    @Test
    public void testSimulateFromNode() {
        MergeGame game = new MergeGame();
        MergeState state = new MergeState(game);
        MergeNode node = new MergeNode(state);
    
        int result = node.simulate(); // ✅ 正确调用 simulate()
        assertTrue(result >= 0);
    }
    
}

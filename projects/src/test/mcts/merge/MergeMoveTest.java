package mcts.merge;

import org.junit.Test;

import static org.junit.Assert.*;

public class MergeMoveTest {

    @Test
    public void testConstructorAndDirection() {
        MergeMove move = new MergeMove(0, MergeMove.Direction.UP);
        assertEquals(MergeMove.Direction.UP, move.getDirection());
    }
    
    
    @Test
    public void testDifferentDirectionsAreNotEqual() {
        MergeMove up = new MergeMove(0, MergeMove.Direction.UP);
        MergeMove down = new MergeMove(0, MergeMove.Direction.DOWN);
        assertNotEquals(up, down);
    }
}

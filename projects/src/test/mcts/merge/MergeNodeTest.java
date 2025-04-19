package mcts.merge;

import mcts.core.Node;
import mcts.core.State;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class MergeNodeTest {

    @Test
    public void testConstructorAndIsLeaf() {
        MergeGame game = new MergeGame();
        MergeState state = new MergeState(game);
        MergeNode node = new MergeNode(state);
        assertEquals(state, node.state());
        assertTrue(node.isLeaf());

    }

    @Test
    public void testAddChildIncreasesSize() {
        MergeGame game = new MergeGame();
        MergeState state = new MergeState(game);
        MergeNode node = new MergeNode(state);

        int initialSize = node.children().size();
        node.addChild(state);
        assertEquals(initialSize + 1, node.children().size());
    }

    @Test
    public void testExpandAddsChildren() {
        MergeGame game = new MergeGame();
        MergeState state = new MergeState(game);
        MergeNode node = new MergeNode(state);

        node.expand();
        Collection<Node<MergeGame>> children = node.children();
        assertFalse(children.isEmpty());

        for (Node<MergeGame> childNode : children) {
            MergeNode child = (MergeNode) childNode;
            assertEquals(node, child.getParent());
        }        
    }

    @Test
    public void testGetParent() {
        MergeGame game = new MergeGame();
        MergeState state = new MergeState(game);
        MergeNode parent = new MergeNode(state);
        parent.addChild(state);
    
        MergeNode child = (MergeNode) parent.children().iterator().next();
        assertEquals(parent, child.getParent());
    }    
}

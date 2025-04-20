package mcts.merge;

import mcts.core.Move;
import mcts.core.Node;
import mcts.core.State;

import java.util.*;

/**
 * MCTS implementation for MergeGame (2048).
 * Mirrors the structure of mcts.tictactoe.MCTS.
 */
public class MCTS {

    private final MergeNode root;
    private final Random random;

    public MCTS(Node<MergeGame> node) {
        this.root = (MergeNode) node; 
        this.random = new Random();
    }

    public void runSearch(int iterations) {
        for (int i = 0; i < iterations; i++) {
            MergeNode selected = root;
    
            // Selection
            while (!selected.isLeaf() && !selected.state().isTerminal()) {
                selected = bestUCTChild(selected);
            }
    
            // Expansion
            if (!selected.state().isTerminal() && !selected.isExpanded()) {
                selected.expand();
            }
    
            // Simulation
            int result = selected.simulate();
    
            // Backpropagation
            selected.backPropagate(result);
        }
    }
    

    private MergeNode bestUCTChild(MergeNode node) {
        double bestValue = -Double.MAX_VALUE;
        MergeNode bestChild = null;

        for (Node<MergeGame> child : node.children()) {
            MergeNode c = (MergeNode) child;
            double uct = uctValue(c.wins(), c.playouts(), node.playouts());

            if (uct > bestValue) {
                bestValue = uct;
                bestChild = c;
            }
        }

        return bestChild;
    }

    private double uctValue(int wins, int playouts, int totalPlayouts) {
        if (playouts == 0) return Double.MAX_VALUE;
        return (double) wins / playouts + Math.sqrt(2 * Math.log(totalPlayouts) / playouts);
    }

    public Move<MergeGame> bestMove() {
        Move<MergeGame> bestMove = null;
        double bestScore = -1;
    
        for (Map.Entry<Move<MergeGame>, Node<MergeGame>> entry : root.getChildrenMap().entrySet()) {
            MergeNode node = (MergeNode) entry.getValue();
            if (node.playouts() > bestScore) {
                bestScore = node.playouts();
                bestMove = entry.getKey();
            }
        }
    
        return bestMove;
    }
    
}

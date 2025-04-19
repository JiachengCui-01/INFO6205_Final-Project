package mcts.merge;

import mcts.core.Move;
import mcts.core.Node;
import mcts.core.State;

import java.util.Scanner;

/**
 * Command-line simulator to test MCTS playing 2048.
 */
public class MergeSimulator {

    public static void main(String[] args) {
        MergeGame game = new MergeGame();
        State<MergeGame> state = game.start();

        System.out.println("Initial State:");
        System.out.println(state);

        Scanner scanner = new Scanner(System.in);

        int step = 0;
        while (!state.isTerminal()) {
            System.out.println("Valid moves at step " + step + ": " + state.moves(state.player()));
        
            Node<MergeGame> root = new MergeNode(state);
            MCTS mcts = new MCTS(root);
            mcts.runSearch(1000);
        
            Move<MergeGame> best = mcts.bestMove();
            if (best == null) break;
        
            System.out.println("AI chooses: " + best);
        
            state = state.next(best);
        
            System.out.println("After step " + (step + 1) + ":");
            System.out.println(state);
            step++;
        }
        

        System.out.println("Final State:");
        System.out.println(state);
    }
}

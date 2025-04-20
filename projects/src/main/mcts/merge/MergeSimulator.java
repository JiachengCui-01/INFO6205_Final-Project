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
        int[] iterationSet = {50, 100, 200, 500};
        mcts.util.RunLogger logger = new mcts.util.RunLogger("timing_merge.csv");
    
        for (int iterations : iterationSet) {
            for (int trial = 0; trial < 10; trial++) {
                System.out.println("=== Trial " + trial + " with " + iterations + " iterations ===");
    
                MergeGame game = new MergeGame();
                State<MergeGame> state = game.start();
                System.out.println("Initial State:");
                System.out.println(state);
    
                int step = 0;
                while (!state.isTerminal()) {
                    System.out.println("Valid moves at step " + step + ": " + state.moves(state.player()));
    
                    Node<MergeGame> root = new MergeNode(state);
                    MCTS mcts = new MCTS(root);
    
                    long start = System.nanoTime();
                    mcts.runSearch(iterations);
                    long end = System.nanoTime();
                    long duration = (end - start) / 1_000_000;
    
                    Move<MergeGame> move = mcts.bestMove();
                    if (move == null) break;
    
                    System.out.println("AI chooses: " + move);
                    state = state.next(move);
                    System.out.println("New state:\n" + state);
    
                    int score = ((MergeState) state).getScore();
                    logger.log(iterations, duration, String.valueOf(score));
                    step++;
                }
    
                System.out.println("Final Score: " + ((MergeState) state).getScore());
            }
        }
    
        System.out.println("All Merge simulations complete.");
    }
    
}

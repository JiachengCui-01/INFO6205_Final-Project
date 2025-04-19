package mcts.tictactoe;

import mcts.core.State;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class MCTSTest {

    @Test
    public void testMCTSBasicRun() {
        TicTacToe game = new TicTacToe();
        State<TicTacToe> state = game.start();
        MCTS mcts = new MCTS(new TicTacToeNode(state));
        mcts.runSearch();
        assertNotNull(mcts.bestChild());
    }


}
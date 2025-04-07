package mcts.tictactoe;

import mcts.core.Node;
import mcts.core.State;

import java.util.*;

public class TicTacToeNode implements Node<TicTacToe> {

    private final State<TicTacToe> state;
    private final ArrayList<Node<TicTacToe>> children;
    private final Node<TicTacToe> parent;

    private int wins;
    private int playouts;

    public TicTacToeNode(State<TicTacToe> state) {
        this(state, null);
    }

    public TicTacToeNode(State<TicTacToe> state, Node<TicTacToe> parent) {
        this.state = state;
        this.parent = parent;
        this.children = new ArrayList<>();
        initializeNodeData();
    }

    private void initializeNodeData() {
        if (isLeaf()) {
            playouts = 1;
            Optional<Integer> winner = state.winner();
            wins = winner.isPresent() ? 2 : 1; // win: 2, draw: 1
        }
    }

    public boolean isLeaf() {
        return state.isTerminal();
    }

    public State<TicTacToe> state() {
        return state;
    }

    public boolean white() {
        return state.player() == state.game().opener();
    }

    public Collection<Node<TicTacToe>> children() {
        return children;
    }

    public void addChild(State<TicTacToe> state) {
        children.add(new TicTacToeNode(state, this));
    }

    public Node<TicTacToe> getParent() {
        return parent;
    }

    public void backPropagate() {
        playouts = 0;
        wins = 0;
        for (Node<TicTacToe> child : children) {
            wins += child.wins();
            playouts += child.playouts();
        }
    }

    public int wins() {
        return wins;
    }

    public int playouts() {
        return playouts;
    }

    public Node<TicTacToe> bestChildByUCT(double c) {
        return children.stream()
                .max(Comparator.comparing(child -> {
                    int childPlayouts = child.playouts();
                    if (childPlayouts == 0) return Double.POSITIVE_INFINITY;
                    double winRate = (double) child.wins() / childPlayouts;
                    return winRate + c * Math.sqrt(Math.log(this.playouts()) / childPlayouts);
                }))
                .orElseThrow();
    }
}

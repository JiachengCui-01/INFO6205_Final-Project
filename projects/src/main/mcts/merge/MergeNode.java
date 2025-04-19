package mcts.merge;

import mcts.core.Move;
import mcts.core.Node;
import mcts.core.State;

import java.util.*;

/**
 * Node implementation for 2048 game used in MCTS.
 */
public class MergeNode implements Node<MergeGame> {

    private final State<MergeGame> state;
    private final Map<Move<MergeGame>, Node<MergeGame>> children = new HashMap<>();
    private final Node<MergeGame> parent;

    private int playouts = 0;
    private double wins = 0;

    public MergeNode(State<MergeGame> state) {
        this(state, null);
    }

    public MergeNode(State<MergeGame> state, Node<MergeGame> parent) {
        this.state = state;
        this.parent = parent;
    }

    public void backPropagate(int reward) {
        this.playouts++;
        this.wins += reward;
    }
        
    public Map<Move<MergeGame>, Node<MergeGame>> getChildrenMap() {
        return children;
    }    

    @Override
    public State<MergeGame> state() {
        return state;
    }

    @Override
    public Collection<Node<MergeGame>> children() {
        return children.values();
    }

    @Override
    public boolean isLeaf() {
        return children.isEmpty();
    }

    @Override
    public boolean white() {
        return true; // 所有节点都可以认为是自己行动（单人游戏）
    }

    @Override
    public void addChild(State<MergeGame> childState) {
        Node<MergeGame> child = new MergeNode(childState, this);
        children.put(null, child); // TODO: replace `null` with actual move if needed
    }

    @Override
    public void backPropagate() {
        this.playouts++;
        this.wins += 1; // 默认假设每次模拟获得 1 分
    }

    @Override
    public int playouts() {
        return playouts;
    }

    @Override
    public int wins() {
        return (int) this.wins;
    }

    @Override
    public void expand() {
        for (Move<MergeGame> move : state.moves(state.player())) {
            State<MergeGame> nextState = state.next(move);
            children.put(move, new MergeNode(nextState, this));
        }
    }

    @Override
    public boolean isExpanded() {
        return !children.isEmpty();
    }

    public Node<MergeGame> getParent() {
        return parent;
    }

    // Optional: Simulation method
    public int simulate() {
        State<MergeGame> sim = state;
        while (!sim.isTerminal()) {
            Collection<Move<MergeGame>> moves = sim.moves(sim.player());
            Move<MergeGame> move = moves.iterator().next();
            sim = sim.next(move);
        }
        return sim.winner().orElse(0);
    }
}

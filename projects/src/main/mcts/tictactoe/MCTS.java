/*
 * Copyright (c) 2024. Robin Hillyard
 */

package mcts.tictactoe;

import mcts.core.Node;
import mcts.core.State;

import java.util.*;

/**
 * Class to represent a Monte Carlo Tree Search for TicTacToe.
 */
public class MCTS {

    private static final int SIMULATIONS = 1000;
    private static final double EXPLORATION = Math.sqrt(2);

    private final Node<TicTacToe> root;

    public MCTS(Node<TicTacToe> root) {
        this.root = root;
    }

    public static void main(String[] args) {
        // initialize game
        TicTacToe game = new TicTacToe();
        State<TicTacToe> initialState = game.start();
        Node<TicTacToe> root = new TicTacToeNode(initialState);

        MCTS mcts = new MCTS(root);

        mcts.runSearch();

        // print out the best move
        Node<TicTacToe> best = mcts.bestChild();
        System.out.println("MCTS advising the best next move is：");
        System.out.println(best.state());
    }

    public void runSearch() {
        for (int i = 0; i < SIMULATIONS; i++) {
            Node<TicTacToe> selected = select(root);
            if (!selected.isLeaf()) {
                expand(selected);
                selected = selected.children().iterator().next();
            }
            int result = simulate(selected);
            backpropagate(selected, result);
        }
    }

    private Node<TicTacToe> select(Node<TicTacToe> node) {
        while (!node.children().isEmpty()) {
            node = ((TicTacToeNode) node).bestChildByUCT(EXPLORATION);
        }
        return node;
    }

    private void expand(Node<TicTacToe> node) {
        Collection<? extends mcts.core.Move<TicTacToe>> moves = node.state().moves(node.state().player());
        for (mcts.core.Move<TicTacToe> move : moves) {
            State<TicTacToe> nextState = node.state().next(move);
            node.addChild(nextState);
        }
    }


    private int simulate(Node<TicTacToe> node) {
        State<TicTacToe> sim = node.state();
        while (!sim.isTerminal()) {
            Collection<? extends mcts.core.Move<TicTacToe>> moves = sim.moves(sim.player());
            mcts.core.Move<TicTacToe> move = moves.iterator().next(); // 随机模拟
            sim = sim.next(move);
        }
        return sim.winner().orElse(-1);
    }

    private void backpropagate(Node<TicTacToe> node, int result) {
        while (node != null) {
            ((TicTacToeNode) node).backPropagate();
            node = ((TicTacToeNode) node).getParent();
        }
    }


    public Node<TicTacToe> bestChild() {
        return root.children().stream()
                .max(Comparator.comparing(Node::playouts))
                .orElseThrow();
    }
}


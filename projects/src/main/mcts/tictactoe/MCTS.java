/*
 * Copyright (c) 2024. Robin Hillyard
 */

package mcts.tictactoe;

import mcts.core.Node;
import mcts.core.State;
import mcts.core.Move;

import java.util.*;

/**
 * Class to represent a Monte Carlo Tree Search for TicTacToe.
 */
public class MCTS {

    private static final int SIMULATIONS = 10000;
    private static final double EXPLORATION = Math.sqrt(2);

    private final Node<TicTacToe> root;

    public MCTS(Node<TicTacToe> root) {
        this.root = root;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TicTacToe game = new TicTacToe();
        State<TicTacToe> state = game.start();
        int currentPlayer = game.opener(); // AI goes first (X)

        while (!state.isTerminal()) {
            System.out.println("\nCurrent board:");
            System.out.println(state);

            if (currentPlayer == TicTacToe.O) {
                // input (matching the habit of normal user)
                System.out.print("Your move (format: row col (from 1 to 3)): ");
                int row = scanner.nextInt() - 1;
                int col = scanner.nextInt() - 1;

                boolean valid = false;
                for (Move<TicTacToe> move : state.moves(currentPlayer)) {
                    TicTacToe.TicTacToeMove m = (TicTacToe.TicTacToeMove) move;
                    int[] pos = m.move();
                    if (pos[0] == row && pos[1] == col) {
                        state = state.next(m);
                        valid = true;
                        break;
                    }
                }
                if (!valid) {
                    System.out.println("Invalid move. Try again.");
                    continue;
                }
            } else {
                // AI using MCTS
                System.out.println("AI is thinking...");
                Node<TicTacToe> root = new TicTacToeNode(state);
                MCTS mcts = new MCTS(root);
                mcts.runSearch();
                state = mcts.bestChild().state();
            }

            currentPlayer = 1 - currentPlayer; // Player switch
        }

        // print out all matchups
        System.out.println("\nFinal board:");
        System.out.println(state);
        Optional<Integer> winner = state.winner();
        if (winner.isPresent()) {
            System.out.println("Winner is: " + (winner.get() == TicTacToe.X ? "AI (X)" : "You (O)"));
        } else {
            System.out.println("It's a draw!");
        }

        scanner.close();
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

    // Using roll out strategy
    private int simulate(Node<TicTacToe> node) {
        State<TicTacToe> sim = node.state();

        while (!sim.isTerminal()) {
            int currentPlayer = sim.player();
            Collection<? extends Move<TicTacToe>> moves = sim.moves(currentPlayer);
            List<Move<TicTacToe>> moveList = new ArrayList<>(moves);

            boolean moved = false;

            for (Move<TicTacToe> move : moveList) {
                State<TicTacToe> next = sim.next(move);
                if (next.winner().orElse(-1) == currentPlayer) {
                    sim = next;
                    moved = true;
                    break;
                }
            }

            if (moved) continue;

            int opponent = 1 - currentPlayer;
            for (Move<TicTacToe> move : moveList) {
                State<TicTacToe> next = sim.next(move);
                if (next.winner().orElse(-1) == opponent) {
                    sim = next;
                    moved = true;
                    break;
                }
            }

            if (moved) continue;

            Move<TicTacToe> chosen = moveList.get(new Random().nextInt(moveList.size()));
            sim = sim.next(chosen);
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


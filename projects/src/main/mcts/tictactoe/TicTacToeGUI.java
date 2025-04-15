package mcts.tictactoe;

import javax.swing.*;
import java.awt.*;

public class TicTacToeGUI extends JFrame {

    public TicTacToeGUI() {
        setTitle("Tic Tac Toe - MCTS AI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setLocationRelativeTo(null);

        TicTacToeBoardPanel boardPanel = new TicTacToeBoardPanel();
        add(boardPanel, BorderLayout.CENTER);

        JLabel statusLabel = new JLabel("Your move (X)", SwingConstants.CENTER);
        boardPanel.setStatusLabel(statusLabel);
        add(statusLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToeGUI::new);
    }
}

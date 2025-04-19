package mcts.merge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Optional;

public class MergeGUI extends JFrame {

    private MergeState state;
    private final MergeBoardPanel boardPanel;
    private final JLabel statusLabel;

    public MergeGUI() {
        setTitle("2048 - MCTS AI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);

        MergeGame game = new MergeGame();
        state = (MergeState) game.start();

        boardPanel = new MergeBoardPanel(state);
        statusLabel = new JLabel("Use arrow keys to move.", SwingConstants.CENTER);

        add(boardPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> move(MergeMove.Direction.UP);
                    case KeyEvent.VK_DOWN -> move(MergeMove.Direction.DOWN);
                    case KeyEvent.VK_LEFT -> move(MergeMove.Direction.LEFT);
                    case KeyEvent.VK_RIGHT -> move(MergeMove.Direction.RIGHT);
                }
            }
        });

        setFocusable(true);
        setVisible(true);
    }

    private void move(MergeMove.Direction dir) {
        if (state.isTerminal()) {
            statusLabel.setText("Game Over! Final score: " + state.getScore());
            return;
        }

        MergeMove move = new MergeMove(0, dir);
        MergeState nextState = (MergeState) state.next(move);
        if (nextState == state) {
            statusLabel.setText("Invalid move.");
        } else {
            state = nextState;
            boardPanel.setState(state);
            statusLabel.setText("Score: " + state.getScore());
            boardPanel.repaint();

            if (state.isTerminal()) {
                statusLabel.setText("Game Over! Final score: " + state.getScore());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MergeGUI::new);
    }
}

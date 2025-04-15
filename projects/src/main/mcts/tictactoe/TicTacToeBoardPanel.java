package mcts.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import mcts.core.Node;
import mcts.core.State;



public class TicTacToeBoardPanel extends JPanel {

    private final int CELL_SIZE = 120;
    private final int GRID_SIZE = 3;
    private char[][] board;
    private boolean playerTurn = true; // 玩家是 X，AI 是 O
    private JLabel statusLabel;

    public TicTacToeBoardPanel() {
        this.board = new char[3][3];
        setPreferredSize(new Dimension(CELL_SIZE * GRID_SIZE, CELL_SIZE * GRID_SIZE));
        setBackground(new Color(250, 250, 240)); 

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!playerTurn) return;

                int row = e.getY() / CELL_SIZE;
                int col = e.getX() / CELL_SIZE;

                if (board[row][col] == '\0') {
                    board[row][col] = 'X';
                    repaint();
                    playerTurn = false;

                    if (checkGameOver()) return;

                    if (statusLabel != null) statusLabel.setText("AI is thinking...");
                    SwingUtilities.invokeLater(() -> {
                        makeAIMove();
                        repaint();
                        playerTurn = true;
                        checkGameOver();
                        if (statusLabel != null) statusLabel.setText("Your move (X)");
                    });
                }
            }
        });
    }

    public void setStatusLabel(JLabel label) {
        this.statusLabel = label;
    }

    private void makeAIMove() {
        try {
            StringBuilder builder = new StringBuilder();
            int lastPlayer = 1;
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    char c = board[i][j];
                    builder.append((c == 'X') ? "X" : (c == 'O') ? "O" : ".");
                    if (j < GRID_SIZE - 1) builder.append(" ");
                }
                if (i < GRID_SIZE - 1) builder.append("\n");
            }

            Position position = Position.parsePosition(builder.toString(), lastPlayer);
            TicTacToe game = new TicTacToe();
            State<TicTacToe> currentState = game.new TicTacToeState(position);
            Node<TicTacToe> node = new TicTacToeNode(currentState);

            MCTS mcts = new MCTS(node);
            mcts.runSearch();
            State<TicTacToe> bestState = mcts.bestChild().state();

            Position newPosition = ((TicTacToe.TicTacToeState) bestState).position();
            int[][] oldGrid = position.getGrid();
            int[][] newGrid = newPosition.getGrid();

            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    if (oldGrid[i][j] != newGrid[i][j] && newGrid[i][j] == 0) {
                        board[i][j] = 'O';
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkGameOver() {
        String result = getWinner();
        if (result != null) {
            JOptionPane.showMessageDialog(this, result, "Game Over", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
            return true;
        }
        return false;
    }

    private String getWinner() {
        for (int i = 0; i < 3; i++) {
            if (equal(i, 0, i, 1, i, 2)) return who(board[i][0]);
            if (equal(0, i, 1, i, 2, i)) return who(board[0][i]);
        }
        if (equal(0, 0, 1, 1, 2, 2)) return who(board[0][0]);
        if (equal(0, 2, 1, 1, 2, 0)) return who(board[0][2]);

        for (char[] row : board)
            for (char c : row)
                if (c == '\0') return null;

        return "Draw!";
    }

    private boolean equal(int r1, int c1, int r2, int c2, int r3, int c3) {
        return board[r1][c1] != '\0' && board[r1][c1] == board[r2][c2] && board[r2][c2] == board[r3][c3];
    }

    private String who(char c) {
        return c + " wins!";
    }

    private void resetGame() {
        board = new char[3][3];
        playerTurn = true;
        if (statusLabel != null) statusLabel.setText("Your move (X)");
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(3)); 

        for (int i = 1; i < GRID_SIZE; i++) {
            g2.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, CELL_SIZE * GRID_SIZE);
            g2.drawLine(0, i * CELL_SIZE, CELL_SIZE * GRID_SIZE, i * CELL_SIZE);
        }

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                char symbol = board[i][j];
                if (symbol != '\0') {
                    g2.setFont(new Font("Arial", Font.BOLD, 60));
                    g2.setColor(symbol == 'X' ? new Color(60, 130, 220) : new Color(220, 50, 70));
                    g2.drawString(String.valueOf(symbol), j * CELL_SIZE + 35, i * CELL_SIZE + 80);
                }
            }
        }
    }
}

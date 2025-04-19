package mcts.merge;

import javax.swing.*;
import java.awt.*;

public class MergeBoardPanel extends JPanel {

    private MergeState state;

    public MergeBoardPanel(MergeState state) {
        this.state = state;
        setPreferredSize(new Dimension(500, 500));
        setBackground(new Color(0xFAF8EF));
    }

    public void setState(MergeState state) {
        this.state = state;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid((Graphics2D) g);
    }

    private void drawGrid(Graphics2D g2) {
        int[][] grid = state.getGrid();
        int size = 4;
        int cellSize = getWidth() / size;
        int padding = 10;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int value = grid[row][col];
                int x = col * cellSize + padding;
                int y = row * cellSize + padding;
                int w = cellSize - 2 * padding;

                g2.setColor(getTileColor(value));
                g2.fillRoundRect(x, y, w, w, 20, 20);

                if (value != 0) {
                    g2.setColor(Color.BLACK);
                    g2.setFont(new Font("Arial", Font.BOLD, 24));
                    String text = String.valueOf(value);
                    FontMetrics fm = g2.getFontMetrics();
                    int textWidth = fm.stringWidth(text);
                    int textHeight = fm.getAscent();
                    g2.drawString(text, x + (w - textWidth) / 2, y + (w + textHeight) / 2 - 4);
                }
            }
        }
    }

    private Color getTileColor(int value) {
        return switch (value) {
            case 0 -> new Color(0xCDC1B4);
            case 2 -> new Color(0xEEE4DA);
            case 4 -> new Color(0xEDE0C8);
            case 8 -> new Color(0xF2B179);
            case 16 -> new Color(0xF59563);
            case 32 -> new Color(0xF67C5F);
            case 64 -> new Color(0xF65E3B);
            case 128 -> new Color(0xEDCF72);
            case 256 -> new Color(0xEDCC61);
            case 512 -> new Color(0xEDC850);
            case 1024 -> new Color(0xEDC53F);
            case 2048 -> new Color(0xEDC22E);
            default -> new Color(0x3C3A32);
        };
    }
}

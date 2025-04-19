package mcts.merge;

import mcts.core.State;
import mcts.core.Move;

import java.util.*;

/**
 * Represents the state of a 2048 game board.
 */
public class MergeState implements State<MergeGame> {

    private static final int SIZE = 4;
    private final MergeGame game;
    private final int[][] grid;
    private final int player;
    private final int score;
    private final Random random;

    public MergeState(MergeGame game) {
        this.game = game;
        this.grid = new int[SIZE][SIZE];
        this.random = game.getRandom();
        this.player = 0;
        this.score = 0;
        addRandomTile();
        addRandomTile();
    }

    public MergeState(MergeGame game, int[][] grid, int score) {
        this.game = game;
        this.grid = grid;
        this.random = game.getRandom();
        this.player = 0;
        this.score = score;
    }

    @Override
    public MergeGame game() {
        return game;
    }

    @Override
    public int player() {
        return player;
    }

    @Override
    public Collection<Move<MergeGame>> moves(int player) {
        List<Move<MergeGame>> validMoves = new ArrayList<>();
        for (MergeMove.Direction dir : MergeMove.Direction.values()) {
            if (canMove(dir)) {
                validMoves.add(new MergeMove(player, dir));
            }
        }
        return validMoves;
    }

    @Override
    public State<MergeGame> next(Move<MergeGame> move) {
        MergeMove.Direction dir = ((MergeMove) move).getDirection();
        int[][] newGrid = copyGrid(grid);
        int gained = applyMove(newGrid, dir);
    
        if (gained == -1) return this; 
    
        int newScore = this.score + gained;
    
        placeRandomTile(newGrid); 
        return new MergeState(game, newGrid, newScore);
    }
    
    

    @Override
    public boolean isTerminal() {
        return moves(player).isEmpty();
    }

    @Override
    public Optional<Integer> winner() {
        return Optional.empty(); // 2048 has no clear "winner"
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Score: " + score + "\n");
        for (int[] row : grid) {
            for (int val : row) {
                sb.append(String.format("%5d", val));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public Random random() {
        return this.random;
    }


    // ------------------- Helper methods ------------------------

    private void addRandomTile() {
        addRandomTile(this.grid);
    }

    private void addRandomTile(int[][] board) {
        List<int[]> empty = new ArrayList<>();
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (board[i][j] == 0)
                    empty.add(new int[]{i, j});

        if (!empty.isEmpty()) {
            int[] spot = empty.get(random.nextInt(empty.size()));
            board[spot[0]][spot[1]] = random.nextDouble() < 0.9 ? 2 : 4;
        }
    }

    private boolean canMove(MergeMove.Direction dir) {
        int[][] temp = deepCopy(grid);
        return applyMove(temp, dir) != -1;
    }
    
    private int applyMove(int[][] board, MergeMove.Direction dir) {
        boolean moved = false;
        int[] scoreGain = new int[]{0}; // 记录本轮得分
    
        for (int i = 0; i < SIZE; i++) {
            int[] line = extractLine(board, dir, i);
            int[] merged = mergeLine(line, scoreGain);
            if (!Arrays.equals(line, merged)) {
                moved = true;
            }
            writeLine(board, dir, i, merged);
        }
    
        return moved ? scoreGain[0] : -1;  // ✅ 如果没移动，返回 -1 表示非法移动
    }

    private int[] extractLine(int[][] board, MergeMove.Direction dir, int index) {
        int[] line = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            switch (dir) {
                case LEFT -> line[i] = board[index][i];
                case RIGHT -> line[i] = board[index][SIZE - 1 - i];
                case UP -> line[i] = board[i][index];
                case DOWN -> line[i] = board[SIZE - 1 - i][index];
            }
        }
        return line;
    }
    
    private void writeLine(int[][] board, MergeMove.Direction dir, int index, int[] line) {
        for (int i = 0; i < SIZE; i++) {
            switch (dir) {
                case LEFT -> board[index][i] = line[i];
                case RIGHT -> board[index][SIZE - 1 - i] = line[i];
                case UP -> board[i][index] = line[i];
                case DOWN -> board[SIZE - 1 - i][index] = line[i];
            }
        }
    }

    // 用于合并一行并累计得分
    private int[] mergeLine(int[] line, int[] scoreGain) {
        LinkedList<Integer> merged = new LinkedList<>();
        int i = 0;

        while (i < SIZE) {
            if (line[i] != 0) {
                int val = line[i];
                if (i + 1 < SIZE && line[i] == line[i + 1]) {
                    val *= 2;
                    scoreGain[0] += val; // ✅ 累加分数
                    i++; // 跳过下一个格子
                }
                merged.add(val);
            }
            i++;
        }

        while (merged.size() < SIZE) {
            merged.add(0);
        }

        return merged.stream().mapToInt(Integer::intValue).toArray();
    }

    private int[][] copyGrid(int[][] original) {
        int[][] copy = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, SIZE);
        }
        return copy;
    }

    private void placeRandomTile(int[][] grid) {
        List<int[]> emptyCells = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j] == 0) {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }
    
        if (emptyCells.isEmpty()) return;
    
        int[] cell = emptyCells.get(random.nextInt(emptyCells.size()));
        grid[cell[0]][cell[1]] = random.nextDouble() < 0.9 ? 2 : 4;
    }
    
    private int[][] deepCopy(int[][] input) {
        int[][] copy = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
            copy[i] = Arrays.copyOf(input[i], SIZE);
        return copy;
    }
    public int getScore() {
        return this.score;
    }

    public int[][] getGrid() {
        return this.grid;
    }

}

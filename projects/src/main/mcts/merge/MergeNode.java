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

    public MergeMove bestMove() {
        MergeMove best = null;
        int maxPlayouts = -1;
    
        for (Map.Entry<Move<MergeGame>, Node<MergeGame>> entry : children.entrySet()) {
            Node<MergeGame> child = entry.getValue();
            if (child instanceof MergeNode) {
                int childPlayouts = ((MergeNode) child).getPlayouts();
                if (childPlayouts > maxPlayouts) {
                    maxPlayouts = childPlayouts;
                    best = (MergeMove) entry.getKey();
                }
            }
        }
    
        return best;
    }

    public int getPlayouts() {
        return playouts;
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
        return true; 
    }

    @Override
    public void addChild(State<MergeGame> childState) {
        MergeMove move = ((MergeState) this.state).getLastMoveTo(childState); 
        if (move != null) {
            Node<MergeGame> child = new MergeNode(childState, this);
            children.put(move, child); 
        }
    }    

    @Override
    public void backPropagate() {
        this.playouts++;
        this.wins += 1; 
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

    public int simulate() {
        State<MergeGame> current = this.state;
        Random rand = new Random();
        int maxDepth = 20;
        int steps = 0;
    
        while (!current.isTerminal() && steps < maxDepth) {
            List<Move<MergeGame>> moves = new ArrayList<>(current.moves(current.player()));
            List<Move<MergeGame>> bestMoves = new ArrayList<>();
            int simulationsPerMove = 2;
            int bestUtility = Integer.MIN_VALUE;
    
            for (Move<MergeGame> move : moves) {
                int totalUtility = 0;
                for (int k = 0; k < simulationsPerMove; k++) {
                    State<MergeGame> next = current.next(move);
                    int scoreDelta = ((MergeState) next).getScore() - ((MergeState) current).getScore();
                    int emptyCells = ((MergeState) next).countEmpty();
                    int maxTile = ((MergeState) next).getMaxTile();
                    int logTile = maxTile > 0 ? (int)(Math.log(maxTile) / Math.log(2)) : 0;
                    int utility = scoreDelta + 15 * emptyCells + 5 * logTile;

                    totalUtility += utility;
                }
                int averageUtility = totalUtility / simulationsPerMove;
            
                if (averageUtility > bestUtility) {
                    bestUtility = averageUtility;
                    bestMoves.clear();
                    bestMoves.add(move);
                } else if (averageUtility == bestUtility) {
                    bestMoves.add(move);
                }
            }
    
            Move<MergeGame> selected = bestMoves.get(rand.nextInt(bestMoves.size()));
            current = current.next(selected);
            steps++;
        }
    
        return ((MergeState) current).getScore();
    }
    
    
}

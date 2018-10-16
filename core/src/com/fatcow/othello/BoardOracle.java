package com.fatcow.othello;

import com.badlogic.gdx.math.Vector2;
import com.fatcow.othello.Components.RepresentationComponent;
//import com.sun.org.apache.xml.internal.security.algorithms.implementations.IntegrityHmac;
//import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.InternetHeaders;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class BoardOracle {

    private Board board;
    private Hashtable<Vector2, LinkedList<Vector2>> possibleTurns;

    public BoardOracle(Board board, Hashtable<Vector2, LinkedList<Vector2>> possibleTurns) {
        this.board = board;
        this.possibleTurns = possibleTurns;
    }

    public Turn predict() {
//        int maxReverse = -1;
//        Vector2 bestPosition = new Vector2();
//        for (Vector2 turn: possibleTurns.keySet()) {
//            if (possibleTurns.get(turn).size() > maxReverse) {
//                maxReverse = possibleTurns.get(turn).size();
//                bestPosition = turn;
//            }
//        }
//        return new Turn((int)bestPosition.x, (int)bestPosition.y, DiskType.getOpposite(GameConfig.PLAYER_DISK_TYPE));
        return miniMax(DiskType.getOpposite(GameConfig.PLAYER_DISK_TYPE), PlayerType.MIN, GameConfig.MAX_DEPTH);
    }

    private Turn miniMax(DiskType disk, PlayerType player, int maxDepth) {
        Vector2 bestPosition = new Vector2();
        int bestHeurValue = Integer.MAX_VALUE;

        Hashtable<Vector2, LinkedList<Vector2>> turns = RepresentationComponent.getPossibleTurns(disk, board);
        for (Vector2 turn: turns.keySet()) {
            Board newBoard = new Board(board, new Turn((int)turn.x, (int)turn.y, disk), turns.get(turn));
            int childValue = miniMax(newBoard, DiskType.getOpposite(disk), PlayerType.getOpposite(player), maxDepth-1,
                    Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (childValue < bestHeurValue) {
                bestHeurValue = childValue;
                bestPosition = turn;
            }
        }

        return new Turn((int)bestPosition.x, (int)bestPosition.y, disk);
    }

    private int miniMax(Board board, DiskType disk, PlayerType player, int depth, int alpha, int beta){
        if (depth == 0) {
            int heurValue = simpleHeuristic(board);
            return heurValue;
        }
        int bestValue = (player == PlayerType.MAX)? Integer.MIN_VALUE: Integer.MAX_VALUE;

        Hashtable<Vector2, LinkedList<Vector2>> turns = RepresentationComponent.getPossibleTurns(disk, board);
        if (turns.size() < 1) {
            return bestValue;
        }
        for (Vector2 turn: turns.keySet()) {
            Board newBoard = new Board(board, new Turn((int)turn.x, (int)turn.y, disk), turns.get(turn));
            int childValue = miniMax(newBoard,
                    DiskType.getOpposite(disk), PlayerType.getOpposite(player), depth - 1, alpha, beta);
            if (player == PlayerType.MAX) {
                if (childValue > bestValue) {
                    bestValue = childValue;

                }
                alpha = Math.max(alpha, bestValue);
            } else {
                if (childValue < bestValue) {
                    bestValue = childValue;

                }
                beta = Math.min(beta, bestValue);
            }
            if (beta < alpha)
               break;
        }
        //System.out.printf("we choose bestBalue = %d\n", bestValue);

        return bestValue;
    }

    private Turn alphaBeta(DiskType disk, PlayerType player){
        int alpha = Integer.MAX_VALUE;
        int beta = Integer.MIN_VALUE;

        Vector2 bestPosition = new Vector2();

        return new Turn((int)bestPosition.x, (int)bestPosition.y, DiskType.getOpposite(disk));
    }

    private int alphaBeta(Board board, DiskType disk, PlayerType player, int depth) {
        return 10;
    }

    private int simpleHeuristic(Board board){
        DiskType[][] boardData = board.getData();
        DiskType playerDisk = GameConfig.PLAYER_DISK_TYPE;
        int heurValue = 1000;

        for (DiskType[] row: boardData){
            for (DiskType disk: row){
                if (disk != null) {
                    if (disk == playerDisk) {
                        heurValue++;
                    } else {
                        heurValue--;
                    }
                }
            }
        }

        List<Vector2> cornerCells = new ArrayList<Vector2>();
        cornerCells.add(new Vector2(0, 0));
        cornerCells.add(new Vector2(0, GameConfig.BOARD_SIZE-1));
        cornerCells.add(new Vector2(GameConfig.BOARD_SIZE-1, 0));
        cornerCells.add(new Vector2(GameConfig.BOARD_SIZE-1, GameConfig.BOARD_SIZE-1));
        for (Vector2 corner: cornerCells){
            DiskType cornerDisk = boardData[(int)corner.x][(int)corner.y];
            if (cornerDisk == null)
                continue;
            if (cornerDisk == playerDisk){
                heurValue += 15;
            }else
            {
                heurValue-= 15;
            }
        }
        // System.out.printf("for player %s value = %d\n", playerDisk.toString(), heurValue);
        return heurValue;
    }

}

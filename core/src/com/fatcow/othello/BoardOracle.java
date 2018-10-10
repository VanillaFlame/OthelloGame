package com.fatcow.othello;

import com.badlogic.gdx.math.Vector2;
import com.fatcow.othello.Components.RepresentationComponent;

import java.util.Hashtable;
import java.util.LinkedList;

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
        return miniMax(GameConfig.PLAYER_DISK_TYPE, PlayerType.MAX);
    }

    private Turn miniMax(DiskType disk, PlayerType player){
        Vector2 bestPosition = new Vector2();
        int bestHeurValue = 0;
        int MAX_DEPTH = 5;
        for (Vector2 turn: possibleTurns.keySet()) {
            if (miniMax(board, DiskType.getOpposite(disk), PlayerType.getOpposite(player), MAX_DEPTH) > bestHeurValue) {
                bestHeurValue = possibleTurns.get(turn).size();
                bestPosition = turn;
            }
        }

        return new Turn((int)bestPosition.x, (int)bestPosition.y, DiskType.getOpposite(disk));
    }

    private int miniMax(Board board, DiskType disk, PlayerType player, int depth){
        if (depth == 0){
            return simpleHeuristic(board, disk);
        }
        int bestValue = (player == PlayerType.MAX)? Integer.MIN_VALUE: Integer.MAX_VALUE;

        Hashtable<Vector2, LinkedList<Vector2>> turns = RepresentationComponent.getPossibleTurns(disk, board);
        for (Vector2 turn: turns.keySet()){
            int childValue = miniMax(new Board(board, new Turn((int)turn.x, (int)turn.y, disk)),
                    DiskType.getOpposite(disk), PlayerType.getOpposite(player), depth - 1);
            if (player == PlayerType.MAX) {
                if (childValue > bestValue) {
                    bestValue = childValue;
                }
            }else{
                if (childValue < bestValue) {
                    bestValue = childValue;
                }
            }
        }

        return bestValue;
    }

//    private Turn alphaBeta(DiskType disk, PlayerType player){
//
//    }

    private int simpleHeuristic(Board board, DiskType playerDisk){
        DiskType[][] boardData = board.getData();
        int heurValue = 1000;

        for (DiskType[] row: boardData){
            for (DiskType disk: row){
                if (disk == playerDisk){
                    heurValue++;
                }
                else{
                    heurValue--;
                }
            }
        }

        return heurValue;
    }

}

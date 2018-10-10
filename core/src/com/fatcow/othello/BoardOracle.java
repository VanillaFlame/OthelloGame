package com.fatcow.othello;

import com.badlogic.gdx.math.Vector2;

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
        int maxReverse = -1;
        Vector2 bestPosition = new Vector2();
        for (Vector2 turn: possibleTurns.keySet()) {
            if (possibleTurns.get(turn).size() > maxReverse) {
                maxReverse = possibleTurns.get(turn).size();
                bestPosition = turn;
            }
        }
        return new Turn((int)bestPosition.x, (int)bestPosition.y, DiskType.getOpposite(GameConfig.PLAYER_DISK_TYPE));
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
        // TODO: find possible turns for board, then
        // possibleTurns =
        int bestValue = 0;
        for (Vector2 turn: possibleTurns.keySet()){
            int childValue = miniMax(board, DiskType.getOpposite(disk), PlayerType.getOpposite(player), depth - 1);
            if (player == PlayerType.MAX) {
                // TODO: create new board
                //Board newBoard = board
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

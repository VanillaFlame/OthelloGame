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
}

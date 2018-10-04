package com.fatcow.othello;

import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;
import java.util.List;

public class Board {

    private Board prevState;
    private DiskType[][] data;

    public Board(DiskType[][] startPosition) {
        this.data = startPosition;
        prevState = null;
    }

    public Board(Board prevState, Turn turn, List<Vector2> toReverse) {
        this(prevState, turn);
        for (Vector2 reverse: toReverse) {
            data[(int)reverse.x][(int)reverse.y] = turn.getDiskType();
        }
    }

    public Board(Board prevState, Turn turn) {
        this.prevState = prevState;
        data = new DiskType[GameConfig.BOARD_SIZE][GameConfig.BOARD_SIZE];
        if (turn != null) {
            copyData();
            data[turn.getX()][turn.getY()] = turn.getDiskType();
        }
    }

    public DiskType[][] getData() {
        return data;
    }

    private void copyData() {
        for (int i = 0; i < prevState.getData().length; ++i) {
            data[i] = Arrays.copyOf(prevState.getData()[i], prevState.getData().length);
        }
    }
}

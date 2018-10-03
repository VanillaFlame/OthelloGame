package com.fatcow.othello;

import com.fatcow.othello.Components.OraculComponent;

public class Turn {

    private int x, y;
    private DiskType diskType;

    public Turn() {

    }

    public Turn(int x, int y, DiskType diskType) {
        this.x = x;
        this.y = y;
        this.diskType = diskType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public DiskType getDiskType() {
        return diskType;
    }
}

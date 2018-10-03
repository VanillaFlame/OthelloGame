package com.fatcow.othello;

public enum DiskType {
    BLACK, WHITE;

    public static DiskType getOpposite(DiskType type) {
        return type == BLACK ? WHITE : BLACK;
    }
}

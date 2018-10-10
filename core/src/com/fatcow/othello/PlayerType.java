package com.fatcow.othello;

public enum PlayerType {
    MIN, MAX;

    public static PlayerType getOpposite(PlayerType type) {
        return type == MAX ? MIN : MAX;
    }
}
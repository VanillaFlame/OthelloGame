package com.fatcow.othello;

import com.badlogic.gdx.Gdx;

public final class GameConfig {
    public static final float WORLD_WIDTH = Gdx.graphics.getWidth();
    public static final float WORLD_HEIGHT = Gdx.graphics.getHeight();
    public static final int BOARD_SIZE = 8;
    public static final DiskType PLAYER_DISK_TYPE = DiskType.BLACK;
}

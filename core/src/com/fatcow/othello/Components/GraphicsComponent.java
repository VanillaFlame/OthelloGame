package com.fatcow.othello.Components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.fatcow.othello.*;

import java.util.*;

public class GraphicsComponent implements Component {

    public static final float BOARD_CELL_SIZE = 65;
    public static final float LEFT_BORDER_WIDTH = 12;
    public static final float BOTTOM_BORDER_WIDTH = 9;

    private Turn lastPlayerTurn = new Turn(-1, -1, GameConfig.PLAYER_DISK_TYPE);
    private Vector2 mouseOnCell = new Vector2(-1, -1);
    private DiskType[][] currentBoardPositions;
    private Hashtable<Vector2, LinkedList<Vector2>> possibleTurns = new Hashtable<Vector2, LinkedList<Vector2>>();
    private Texture boardTexture;
    private Texture whiteDiskTexture;
    private Texture blackDiskTexture;
    private Texture selectedCellTexture;
    private Texture redDotTexture;
    private Json json;

    public GraphicsComponent() {
        json = new Json();
        boardTexture = new Texture("Board.jpg");
        whiteDiskTexture = new Texture("WhiteDisk.png");
        blackDiskTexture = new Texture("BlackDisk.png");
        selectedCellTexture = new Texture("SelectedCell.png");
        redDotTexture = new Texture("RedDot.png");
    }

    public void update(GameObject gameObject, Batch batch, float delta) {
        batch.begin();
        batch.draw(boardTexture, 0, 0);
        for (int i = 0; i < currentBoardPositions.length; ++i) {
            for (int j = 0; j < currentBoardPositions.length; ++j) {
                if (currentBoardPositions[i][j] != null) {
                    Texture toDraw = currentBoardPositions[i][j] == DiskType.WHITE ? whiteDiskTexture : blackDiskTexture;
                    batch.draw(
                            toDraw,
                            LEFT_BORDER_WIDTH + i * BOARD_CELL_SIZE,
                            BOTTOM_BORDER_WIDTH + (GameConfig.BOARD_SIZE - 1 - j) * BOARD_CELL_SIZE);
                }
            }
        }

        for (Vector2 turn : possibleTurns.keySet()) {
            batch.draw(
                    selectedCellTexture,
                    LEFT_BORDER_WIDTH + turn.x * BOARD_CELL_SIZE - 3,
                    BOTTOM_BORDER_WIDTH + (GameConfig.BOARD_SIZE - 1 - turn.y) * BOARD_CELL_SIZE - 3);
        }

        if (possibleTurns.containsKey(mouseOnCell)) {
            for (Vector2 reverse : possibleTurns.get(mouseOnCell)) {
                batch.draw(
                        redDotTexture,
                        LEFT_BORDER_WIDTH + reverse.x * BOARD_CELL_SIZE - 3,
                        BOTTOM_BORDER_WIDTH + (GameConfig.BOARD_SIZE - 1 - reverse.y) * BOARD_CELL_SIZE - 3);
            }
        }
        batch.end();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void receiveMessage(String message) {
        String[] string = message.split(MESSAGE_TOKEN);
        if (string.length == 1) {
            if (string[0].equalsIgnoreCase(Message.DRAG_OVER_POSSIBLE_TURN_END.toString())) {
                mouseOnCell = new Vector2(-1 , -1);
            }
        }
        if (string.length == 2) {
            if (string[0].equalsIgnoreCase(Message.PLAYER_INPUT.toString())) {
                lastPlayerTurn = json.fromJson(Turn.class, string[1]);
                currentBoardPositions[lastPlayerTurn.getX()][lastPlayerTurn.getY()] = lastPlayerTurn.getDiskType();
            }
            if (string[0].equalsIgnoreCase(Message.BOARD_DATA_CHANGED.toString())) {
                currentBoardPositions = json.fromJson(DiskType[][].class, string[1]);
            }
            if (string[0].equalsIgnoreCase(Message.POSSIBLE_TURNS.toString())) {
                Hashtable<String, LinkedList<Vector2>> strTurns = json.fromJson(Hashtable.class, string[1]);
                possibleTurns.clear();
                for (String key: strTurns.keySet()) {
                    possibleTurns.put(BoardUtils.stringPosToVector(key), strTurns.get(key));
                }
            }
            if (string[0].equalsIgnoreCase(Message.DRAG_OVER_POSSIBLE_TURN_BEGIN.toString())) {
                mouseOnCell = json.fromJson(Vector2.class, string[1]);
            }
        }
    }
}

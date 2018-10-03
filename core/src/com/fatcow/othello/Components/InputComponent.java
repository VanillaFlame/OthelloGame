package com.fatcow.othello.Components;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.fatcow.othello.*;

import java.util.HashSet;

public class InputComponent implements Component, InputProcessor {

    private GameObject gameObject;
    private Json json = new Json();
    private HashSet<Vector2> possibleTurns = new HashSet<Vector2>();

    public InputComponent() {
    }

    public InputComponent(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX -= GraphicsComponent.LEFT_BORDER_WIDTH;
        screenY -= GraphicsComponent.BOTTOM_BORDER_WIDTH;
        int i = (int)(screenX / GraphicsComponent.BOARD_CELL_SIZE);
        int j = (int)(screenY / GraphicsComponent.BOARD_CELL_SIZE);
        i = Math.min(GameConfig.BOARD_SIZE - 1, i);
        j = Math.min(GameConfig.BOARD_SIZE - 1, j);
        System.out.println("Player clicked at: " +
                '(' + i + ", " + j + ')' );
        if (gameObject != null && possibleTurns.contains(new Vector2(i, j))) {
            Turn turn = new Turn(i, j, GameConfig.PLAYER_DISK_TYPE);
            gameObject.sendMessage(Message.PLAYER_INPUT, json.toJson(turn, Turn.class));
        } else {
            System.out.println("This turn is unfair!");
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void receiveMessage(String message) {
        String[] string = message.split(MESSAGE_TOKEN);
        if (string.length == 2) {
            if (string[0].equalsIgnoreCase(Message.POSSIBLE_TURNS.toString())) {
                possibleTurns = json.fromJson(HashSet.class, string[1]);
            }
        }
    }
}

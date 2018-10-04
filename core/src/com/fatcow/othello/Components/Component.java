package com.fatcow.othello.Components;

public interface Component {

    String MESSAGE_TOKEN = ":::::";

    enum Message {
        BOARD_DATA_CHANGED,
        PLAYER_INPUT,
        ORACLE_PREDICT,
        DRAG_OVER_POSSIBLE_TURN_BEGIN,
        DRAG_OVER_POSSIBLE_TURN_END,
        POSSIBLE_TURNS,
        PLAYER_TURN,
        COMPUTER_TURN,
    }

    void dispose();

    void receiveMessage(String message);
}
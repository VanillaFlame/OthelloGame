package com.fatcow.othello.Components;

public interface Component {

    String MESSAGE_TOKEN = ":::::";

    enum Message {
        TURN_PREDICTED,
        PLAYER_INPUT,
        POSSIBLE_TURNS,
    }

    void dispose();

    void receiveMessage(String message);
}
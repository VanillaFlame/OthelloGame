package com.fatcow.othello.Components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.fatcow.othello.*;

import java.util.HashSet;

public class OracleComponent implements Component {

    private Board currentBoard;
    private Turn lastPlayerTurn = new Turn(-1, -1, GameConfig.PLAYER_DISK_TYPE);
    private HashSet<Vector2> possibleTurns;
    protected Json json = new Json();
    protected GameObject gameObject;

    public OracleComponent(Board startBoard) {
        currentBoard = startBoard;
        possibleTurns = getPossibleTurns(GameConfig.PLAYER_DISK_TYPE);
        System.out.println("Possible player turns: " + possibleTurns);
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public HashSet<Vector2> getPossibleTurns() {
        return possibleTurns;
    }

    public void forceSendPossibleTurnsMessage() {
        if (gameObject != null) {
            gameObject.sendMessage(Message.POSSIBLE_TURNS, json.toJson(possibleTurns, HashSet.class));
        }
    }

    public void forceSendPredictedMessage() {
        if (gameObject != null) {
            gameObject.sendMessage(Message.TURN_PREDICTED, json.toJson(currentBoard.getData(), DiskType[][].class));
        }
    }

    private HashSet<Vector2> getPossibleTurns(DiskType diskType) {
        HashSet<Vector2> possibleTurns = new HashSet<Vector2>();
        DiskType[][] data = currentBoard.getData();
        for (int i = 0; i < data.length; ++i) {
            for (int j = 0; j < data.length; ++j) {
                if (data[i][j] == diskType) {
                    possibleTurns.addAll(BoardUtils.getPossibleRowTurns(currentBoard, diskType, i, j));
                    possibleTurns.addAll(BoardUtils.getPossibleColumnTurns(currentBoard, diskType, i, j));
                    possibleTurns.addAll(BoardUtils.getPossibleDiagTurns(currentBoard, diskType, i, j));
                    possibleTurns.addAll(BoardUtils.getPossibleAntidiagTurns(currentBoard, diskType, i, j));
                }
            }
        }
        return possibleTurns;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void receiveMessage(String message) {
        String[] string = message.split(MESSAGE_TOKEN);
        if (string.length == 2) {
            if (string[0].equalsIgnoreCase(Message.PLAYER_INPUT.toString())) {
                lastPlayerTurn = json.fromJson(Turn.class, string[1]);
                currentBoard = new Board(currentBoard, lastPlayerTurn);
                possibleTurns = getPossibleTurns(GameConfig.PLAYER_DISK_TYPE);
                System.out.println("Possible player turns: " + possibleTurns);
                forceSendPossibleTurnsMessage();
            }
        }
    }
}

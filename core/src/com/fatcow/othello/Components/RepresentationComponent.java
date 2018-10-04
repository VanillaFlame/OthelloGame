package com.fatcow.othello.Components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.fatcow.othello.*;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;

public class RepresentationComponent implements Component {

    private Board currentBoard;
    private Turn lastPlayerTurn = new Turn(-1, -1, GameConfig.PLAYER_DISK_TYPE);
    private Hashtable<Vector2, LinkedList<Vector2>> possibleTurns;
    protected Json json = new Json();
    protected GameObject gameObject;

    public RepresentationComponent(Board startBoard) {
        currentBoard = startBoard;
        possibleTurns = getPossibleTurns(GameConfig.PLAYER_DISK_TYPE);
        System.out.println("Possible player turns: " + possibleTurns);
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public Hashtable<Vector2, LinkedList<Vector2>> getPossibleTurns() {
        return possibleTurns;
    }

    public void forceSendPossibleTurnsMessage() {
        if (gameObject != null) {
            gameObject.sendMessage(Message.POSSIBLE_TURNS, json.toJson(possibleTurns, HashSet.class));
        }
    }

    public void forceSendDataChangedMessage() {
        if (gameObject != null) {
            gameObject.sendMessage(Message.BOARD_DATA_CHANGED, json.toJson(currentBoard.getData(), DiskType[][].class));
        }
    }

    private void sendComputerTurnMessage() {
        if (gameObject != null) {
            gameObject.sendMessage(Message.COMPUTER_TURN);
            System.out.println("Computer turn...");
        }
    }

    private void sendPlayerTurnMessage() {
        if (gameObject != null) {
            gameObject.sendMessage(Message.PLAYER_TURN);
            System.out.println("Player turn...");
        }
    }

    private void sendOraclePredictMessage(Turn oracleTurn) {
        if (gameObject != null) {
            gameObject.sendMessage(Message.ORACLE_PREDICT, json.toJson(oracleTurn, Turn.class));
            System.out.println("Oracle clicked at: " +
                    '(' + oracleTurn.getY() + ", " + oracleTurn.getY() + ')');
        }
    }

    private Hashtable<Vector2, LinkedList<Vector2>> getPossibleTurns(DiskType diskType) {
        Hashtable<Vector2, LinkedList<Vector2>> possibleTurns = new Hashtable<Vector2, LinkedList<Vector2>>();
        DiskType[][] data = currentBoard.getData();
        for (int i = 0; i < data.length; ++i) {
            for (int j = 0; j < data.length; ++j) {
                if (data[i][j] == diskType) {
                    possibleTurns.putAll(BoardUtils.getPossibleRowTurns(currentBoard, diskType, i, j));
                    possibleTurns.putAll(BoardUtils.getPossibleColumnTurns(currentBoard, diskType, i, j));
                    possibleTurns.putAll(BoardUtils.getPossibleDiagTurns(currentBoard, diskType, i, j));
                    possibleTurns.putAll(BoardUtils.getPossibleAntidiagTurns(currentBoard, diskType, i, j));
                }
            }
        }
        return possibleTurns;
    }

    private void turnAsOracle() {
        sendComputerTurnMessage();
        Hashtable<Vector2, LinkedList<Vector2>> oraclePossibleTurns =
                getPossibleTurns(DiskType.getOpposite(GameConfig.PLAYER_DISK_TYPE));
        BoardOracle oracle = new BoardOracle(currentBoard, oraclePossibleTurns);
        Turn oracleTurn = oracle.predict();

        currentBoard = new Board(currentBoard, oracleTurn, oraclePossibleTurns.get(
                new Vector2(oracleTurn.getX(), oracleTurn.getY())));
        possibleTurns = getPossibleTurns(GameConfig.PLAYER_DISK_TYPE);
        System.out.println("Possible player turns: " + possibleTurns);
        forceSendPossibleTurnsMessage();
        forceSendDataChangedMessage();
        sendPlayerTurnMessage();
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
                currentBoard = new Board(currentBoard, lastPlayerTurn, possibleTurns.get(
                        new Vector2(lastPlayerTurn.getX(), lastPlayerTurn.getY())));
                possibleTurns = getPossibleTurns(GameConfig.PLAYER_DISK_TYPE);
                System.out.println("Possible player turns: " + possibleTurns);
                forceSendPossibleTurnsMessage();
                forceSendDataChangedMessage();
                turnAsOracle();
            }
        }
    }
}

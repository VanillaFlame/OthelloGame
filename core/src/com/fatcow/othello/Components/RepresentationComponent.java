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
        if (GameConfig.PLAYER_DISK_TYPE == DiskType.BLACK) {
            possibleTurns = getPossibleTurns(GameConfig.PLAYER_DISK_TYPE, currentBoard);
            System.out.println("Possible player turns: " + possibleTurns);
        } else {
            turnAsOracle();
        }
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

    public static Hashtable<Vector2, LinkedList<Vector2>> getPossibleTurns(DiskType diskType, Board board) {
        Hashtable<Vector2, LinkedList<Vector2>> possibleTurns = new Hashtable<Vector2, LinkedList<Vector2>>();
        DiskType[][] data = board.getData();
        for (int i = 0; i < data.length; ++i) {
            for (int j = 0; j < data.length; ++j) {
                if (data[i][j] == diskType) {
                    Hashtable<Vector2, LinkedList<Vector2>> rowTurns = BoardUtils.getPossibleRowTurns(board, diskType, i, j);
                    Hashtable<Vector2, LinkedList<Vector2>> colTurns = BoardUtils.getPossibleColumnTurns(board, diskType, i, j);
                    Hashtable<Vector2, LinkedList<Vector2>> diagTurns = BoardUtils.getPossibleDiagTurns(board, diskType, i, j);
                    Hashtable<Vector2, LinkedList<Vector2>> antiDiagTurns = BoardUtils.getPossibleAntidiagTurns(board, diskType, i, j);
                    for (Vector2 rowTurn: rowTurns.keySet()) {
                        if (possibleTurns.containsKey(rowTurn)) {
                            possibleTurns.get(rowTurn).addAll(rowTurns.get(rowTurn));
                        } else {
                            possibleTurns.put(rowTurn, rowTurns.get(rowTurn));
                        }
                    }
                    for (Vector2 colTurn: colTurns.keySet()) {
                        if (possibleTurns.containsKey(colTurn)) {
                            possibleTurns.get(colTurn).addAll(colTurns.get(colTurn));
                        } else {
                            possibleTurns.put(colTurn, colTurns.get(colTurn));
                        }
                    }
                    for (Vector2 diagTurn: diagTurns.keySet()) {
                        if (possibleTurns.containsKey(diagTurn)) {
                            possibleTurns.get(diagTurn).addAll(diagTurns.get(diagTurn));
                        } else {
                            possibleTurns.put(diagTurn, diagTurns.get(diagTurn));
                        }
                    }
                    for (Vector2 antiDiagTurn: antiDiagTurns.keySet()) {
                        if (possibleTurns.containsKey(antiDiagTurn)) {
                            possibleTurns.get(antiDiagTurn).addAll(antiDiagTurns.get(antiDiagTurn));
                        } else {
                            possibleTurns.put(antiDiagTurn, antiDiagTurns.get(antiDiagTurn));
                        }
                    }
                }
            }
        }
        return possibleTurns;
    }

    private void turnAsOracle() {
        sendComputerTurnMessage();
        Hashtable<Vector2, LinkedList<Vector2>> oraclePossibleTurns =
                RepresentationComponent.getPossibleTurns(DiskType.getOpposite(GameConfig.PLAYER_DISK_TYPE), currentBoard);
        if (oraclePossibleTurns.size() == 0) {
            System.out.println("Oracle has no turns!");
            possibleTurns = RepresentationComponent.getPossibleTurns(GameConfig.PLAYER_DISK_TYPE, currentBoard);
            if (possibleTurns.size() == 0) {
                forceSendPossibleTurnsMessage();
                System.out.println("Player has no turns!");
                System.out.println("Game is over!");
                return;
            }
        } else {
            BoardOracle oracle = new BoardOracle(currentBoard, oraclePossibleTurns);
            Turn oracleTurn = oracle.predict();
            currentBoard = new Board(currentBoard, oracleTurn, oraclePossibleTurns.get(
                    new Vector2(oracleTurn.getX(), oracleTurn.getY())));
            possibleTurns = RepresentationComponent.getPossibleTurns(GameConfig.PLAYER_DISK_TYPE, currentBoard);
        }
        if (possibleTurns.size() == 0) {
            System.out.println("Player has no turns!");
            forceSendPossibleTurnsMessage();
            turnAsOracle();
            return;
        }
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
                possibleTurns = getPossibleTurns(GameConfig.PLAYER_DISK_TYPE, currentBoard);
                System.out.println("Possible player turns: " + possibleTurns);
                forceSendPossibleTurnsMessage();
                forceSendDataChangedMessage();
                turnAsOracle();
            }
        }
    }
}

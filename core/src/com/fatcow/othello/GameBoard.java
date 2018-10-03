package com.fatcow.othello;

import com.fatcow.othello.Components.GraphicsComponent;
import com.fatcow.othello.Components.InputComponent;
import com.fatcow.othello.Components.OracleComponent;

public class GameBoard extends GameObject {

    public GameBoard(OracleComponent oracleComponent,
                     GraphicsComponent graphicsComponent,
                     InputComponent inputComponent) {
        super(oracleComponent, graphicsComponent, inputComponent);
        inputComponent.setGameObject(this);
        oracleComponent.setGameObject(this);
        oracleComponent.forceSendPredictedMessage();
        oracleComponent.forceSendPossibleTurnsMessage();
    }
}

package com.fatcow.othello;

import com.fatcow.othello.Components.Component;
import com.fatcow.othello.Components.GraphicsComponent;
import com.fatcow.othello.Components.InputComponent;
import com.fatcow.othello.Components.OraculComponent;

public class GameBoard extends GameObject {

    public GameBoard(OraculComponent oraculComponent,
                     GraphicsComponent graphicsComponent,
                     InputComponent inputComponent) {
        super(oraculComponent, graphicsComponent, inputComponent);
        inputComponent.setGameObject(this);
        oraculComponent.setGameObject(this);
        oraculComponent.forceSendPredictedMessage();
        oraculComponent.forceSendPossibleTurnsMessage();
    }
}

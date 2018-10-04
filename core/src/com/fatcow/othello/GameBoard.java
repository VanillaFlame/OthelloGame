package com.fatcow.othello;

import com.fatcow.othello.Components.GraphicsComponent;
import com.fatcow.othello.Components.InputComponent;
import com.fatcow.othello.Components.RepresentationComponent;

public class GameBoard extends GameObject {

    public GameBoard(RepresentationComponent representationComponent,
                     GraphicsComponent graphicsComponent,
                     InputComponent inputComponent) {
        super(representationComponent, graphicsComponent, inputComponent);
        inputComponent.setGameObject(this);
        representationComponent.setGameObject(this);
        representationComponent.forceSendDataChangedMessage();
        representationComponent.forceSendPossibleTurnsMessage();
    }
}

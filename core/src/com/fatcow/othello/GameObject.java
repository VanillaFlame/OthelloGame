package com.fatcow.othello;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.fatcow.othello.Components.Component;
import com.fatcow.othello.Components.GraphicsComponent;
import com.fatcow.othello.Components.InputComponent;
import com.fatcow.othello.Components.OracleComponent;

public class GameObject {

    protected OracleComponent oracleComponent;
    protected GraphicsComponent graphicsComponent;
    protected InputComponent inputComponent;

    protected Array<Component> components;

    public GameObject(OracleComponent oracleComponent,
                      GraphicsComponent graphicsComponent,
                      InputComponent inputComponent) {
        components = new Array<Component>();
        this.inputComponent = inputComponent;
        this.oracleComponent = oracleComponent;
        this.graphicsComponent = graphicsComponent;

        if (oracleComponent != null) {
            components.add(oracleComponent);
        }
        if (graphicsComponent != null) {
            components.add(graphicsComponent);
        }
        if (inputComponent != null) {
            components.add(inputComponent);
        }
    }

    public void update(Batch batch, float delta) {
        if (graphicsComponent != null) {
            graphicsComponent.update(this, batch, delta);
        }
    }

    public void sendMessage(Component.Message messageType, String... args) {
        StringBuilder builder = new StringBuilder();
        builder.append(messageType.toString());
        for (String string : args) {
            builder.append(Component.MESSAGE_TOKEN);
            builder.append(string);
        }
        oracleComponent.receiveMessage(builder.toString());
        graphicsComponent.receiveMessage(builder.toString());
        inputComponent.receiveMessage(builder.toString());
//        for(Component component: components) {
//            component.receiveMessage(builder.toString());
//        }
    }

    public OracleComponent getOracleComponent() {
        return oracleComponent;
    }

    public GraphicsComponent getGraphicsComponent() {
        return graphicsComponent;
    }

    public InputComponent getInputComponent() {
        return inputComponent;
    }
}

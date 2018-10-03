package com.fatcow.othello;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.fatcow.othello.Components.Component;
import com.fatcow.othello.Components.GraphicsComponent;
import com.fatcow.othello.Components.InputComponent;
import com.fatcow.othello.Components.OraculComponent;

public class GameObject {

    protected OraculComponent oraculComponent;
    protected GraphicsComponent graphicsComponent;
    protected InputComponent inputComponent;

    protected Array<Component> components;

    public GameObject(OraculComponent oraculComponent,
                      GraphicsComponent graphicsComponent,
                      InputComponent inputComponent) {
        components = new Array<Component>();
        this.inputComponent = inputComponent;
        this.oraculComponent = oraculComponent;
        this.graphicsComponent = graphicsComponent;

        if (oraculComponent != null) {
            components.add(oraculComponent);
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
        oraculComponent.receiveMessage(builder.toString());
        graphicsComponent.receiveMessage(builder.toString());
        inputComponent.receiveMessage(builder.toString());
//        for(Component component: components) {
//            component.receiveMessage(builder.toString());
//        }
    }

    public OraculComponent getOraculComponent() {
        return oraculComponent;
    }

    public GraphicsComponent getGraphicsComponent() {
        return graphicsComponent;
    }

    public InputComponent getInputComponent() {
        return inputComponent;
    }
}

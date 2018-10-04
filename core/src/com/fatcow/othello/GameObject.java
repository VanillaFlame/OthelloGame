package com.fatcow.othello;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.fatcow.othello.Components.Component;
import com.fatcow.othello.Components.GraphicsComponent;
import com.fatcow.othello.Components.InputComponent;
import com.fatcow.othello.Components.RepresentationComponent;

public class GameObject {

    protected RepresentationComponent representationComponent;
    protected GraphicsComponent graphicsComponent;
    protected InputComponent inputComponent;

    protected Array<Component> components;

    public GameObject(RepresentationComponent representationComponent,
                      GraphicsComponent graphicsComponent,
                      InputComponent inputComponent) {
        components = new Array<Component>();
        this.inputComponent = inputComponent;
        this.representationComponent = representationComponent;
        this.graphicsComponent = graphicsComponent;

        if (representationComponent != null) {
            components.add(representationComponent);
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
        inputComponent.receiveMessage(builder.toString());
        graphicsComponent.receiveMessage(builder.toString());
        representationComponent.receiveMessage(builder.toString());
//        for(Component component: components) {
//            component.receiveMessage(builder.toString());
//        }
    }

    public RepresentationComponent getRepresentationComponent() {
        return representationComponent;
    }

    public GraphicsComponent getGraphicsComponent() {
        return graphicsComponent;
    }

    public InputComponent getInputComponent() {
        return inputComponent;
    }
}

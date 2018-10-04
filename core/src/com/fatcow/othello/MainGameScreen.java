package com.fatcow.othello;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fatcow.othello.Components.GraphicsComponent;
import com.fatcow.othello.Components.InputComponent;
import com.fatcow.othello.Components.RepresentationComponent;
public class MainGameScreen extends ScreenAdapter {

    private OthelloGame game;
    private Viewport viewport;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private GameBoard gameBoard;

    public MainGameScreen(OthelloGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        viewport.apply(true);
        batch = new SpriteBatch();

        DiskType[][] startPosition = {
                {null, null, null, null,             null, null, null, null},
                {null, null, null, null,             null, null, null, null},
                {null, null, null, null,             null, null, null, null},
                {null, null, null, DiskType.WHITE, DiskType.BLACK, null, null, null},
                {null, null, null, DiskType.BLACK, DiskType.WHITE, null, null, null},
                {null, null, null, null,             null, null, null, null},
                {null, null, null, null,             null, null, null, null},
                {null, null, null, null,             null, null, null, null}
        };
        Board startBoard = new Board(startPosition);
        gameBoard = new GameBoard(
                new RepresentationComponent(startBoard),
                new GraphicsComponent(),
                new InputComponent());
        gameBoard.getInputComponent().setGameObject(gameBoard);
        Gdx.input.setInputProcessor(gameBoard.getInputComponent());

    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render(float delta) {
        clearScreen();
        update(delta);
    }

    private void update(float delta) {
        gameBoard.update(batch, delta);
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, Color.WHITE.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}

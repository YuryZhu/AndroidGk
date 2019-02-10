package ru.yurizhi.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.yurizhi.base.Base2DScreen;
import ru.yurizhi.math.Rect;
import ru.yurizhi.sprite.Background;
import ru.yurizhi.sprite.Star;
import ru.yurizhi.sprite.menu.ExitButton;
import ru.yurizhi.sprite.menu.StartButton;


public class MenuScreen extends Base2DScreen {



    private Game game;
    private TextureAtlas atlas;
    private TextureAtlas atlasBut;
    private Texture bg;
    private Background background;
    private Star star[];
    private StartButton startButton;
    private ExitButton exitButton;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bat.jpg");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        atlasBut = new TextureAtlas("textures/batButton.atlas");

        startButton = new StartButton(atlasBut, game);
        exitButton = new ExitButton(atlasBut);
        star = new Star[32];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(atlasBut);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    public void update(float delta) {
        for (int i = 0; i < star.length; i++) {
            star[i].update(delta);
        }
    }

    public void draw() {
        Gdx.gl.glClearColor(0.5f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < star.length; i++) {
            star[i].draw(batch);
        }
        exitButton.draw(batch);
        startButton.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (int i = 0; i < star.length; i++) {
            star[i].resize(worldBounds);
        }
        exitButton.resize(worldBounds);
        startButton.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        exitButton.touchDown(touch, pointer);
        startButton.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        exitButton.touchUp(touch, pointer);
        startButton.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }
}

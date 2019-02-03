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

    boolean leftMove;
    boolean rightMove;
    boolean upMove;
    boolean downMove;


    Texture img;
    TextureRegion region;

    private Game game;
    private float speed;

    Vector2 pos;
    Vector2 v;


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
        atlasBut = new TextureAtlas("textures/batButton.pack");
        img = new Texture("textures/justice_league.png");
        region = new TextureRegion(img, 500, 0, 160, 170);

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
        startButton.draw(batch);
        exitButton.draw(batch);
        updateMotion();
        for (int i = 0; i < star.length; i++) {
            star[i].draw(batch);
        }
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        startButton.resize(worldBounds);
        exitButton.resize(worldBounds);
        for (int i = 0; i < star.length; i++) {
            star[i].resize(worldBounds);
        }
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


    public void updateMotion() {
        speed = (float) (0.3 * Gdx.graphics.getDeltaTime());
        if (leftMove) {
            pos.x -= speed;
        }
        if (rightMove) {
            pos.x += speed;
        }
        if (upMove) {
            pos.y -= speed;
        }
        if (downMove) {
            pos.y += speed;
        }
    }

    public void setLeftMove(boolean t) {
        if (rightMove && t) rightMove = false;
        leftMove = t;
    }

    public void setRightMove(boolean t) {
        if (leftMove && t) leftMove = false;
        rightMove = t;
    }

    public void setUpMove(boolean t) {
        if (upMove && t) upMove = false;
        downMove = t;
    }

    public void setDownMove(boolean t) {
        if (downMove && t) downMove = false;
        upMove = t;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                this.setLeftMove(true);
                break;
            case Input.Keys.RIGHT:
                this.setRightMove(true);
                break;
            case Input.Keys.UP:
                this.setUpMove(true);
                break;
            case Input.Keys.DOWN:
                this.setDownMove(true);
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                this.setLeftMove(false);
                break;
            case Input.Keys.RIGHT:
                this.setRightMove(false);
                break;
            case Input.Keys.UP:
                this.setUpMove(false);
                break;
            case Input.Keys.DOWN:
                this.setDownMove(false);
                break;
        }
        return true;
    }
}

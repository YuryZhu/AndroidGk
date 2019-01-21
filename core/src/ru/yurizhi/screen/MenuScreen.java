package ru.yurizhi.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.yurizhi.base.Base2DScreen;


public class MenuScreen extends Base2DScreen {

    private static final float V_LEN = 2.5f;

    Texture img;
    Texture background;
    TextureRegion region;

    boolean leftMove;
    boolean rightMove;
    boolean upMove;
    boolean downMove;

    private float speed;

    Vector2 pos;
    Vector2 v;

    @Override
    public void show() {
        super.show();
        background = new Texture("bat.jpg");
        img = new Texture("justice_league.png");
        region = new TextureRegion(img, 500, 0, 160, 170);
        pos = new Vector2(-0.5f, -0.5f);
        v = new Vector2(0.002f, 0.002f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.5f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, -0.5f, -0.5f, 1f, 1f);
        batch.draw(region, pos.x, pos.y, 0.2f, 0.2f);
        batch.end();
        updateMotion();
//        pos.add(v);

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return super.touchDown(touch, pointer);
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

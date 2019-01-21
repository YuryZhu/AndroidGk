package ru.yurizhi.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.yurizhi.base.Base2DScreen;

import javax.swing.text.Position;


public class MenuScreen extends Base2DScreen {

    SpriteBatch batch;
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
    boolean move;
    Vector2 speed2, moveTo;

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        background = new Texture("bat.jpg");
        img = new Texture("justice_league.png");
        region = new TextureRegion(img, 500, 0, 160, 170);
        pos = new Vector2(0, 0);
        v = new Vector2(0, 0);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(region, pos.x, pos.y);
        updateMotion();
        pos.add(v);
        if (pos.x < 0) pos.x = 0;
        if (pos.x > 1280 - 155) pos.x = 1280 - 155;
        if (pos.y < 0) pos.y = 0;
        if (pos.y > 720 - 160) pos.y = 720 - 160;
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown " + screenX + " " + (Gdx.graphics.getHeight() - screenY));
        int y = (Gdx.graphics.getHeight() - screenY - 75);
        int x = screenX - 75;
//        pos.set(x, y);
        int tempX = x;
        int tempY = y;
        double distance;
        float time = 500 * Gdx.graphics.getDeltaTime();
        distance = Math.sqrt((tempX - pos.x) * (tempX - pos.x) + (tempY - pos.y) * (tempY - pos.y));
        v.x += 0.1 * time * (tempX - pos.x) / distance;
        v.y += 0.1 * time * (tempY - pos.y) / distance;
        return false;
    }

    public void updateMotion() {
        speed = 500 * Gdx.graphics.getDeltaTime();
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

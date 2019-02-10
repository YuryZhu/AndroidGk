package ru.yurizhi.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.yurizhi.base.Base2DScreen;
import ru.yurizhi.math.Rect;
import ru.yurizhi.pool.BulletPool;
import ru.yurizhi.pool.EnemyPool;
import ru.yurizhi.pool.ExplosionPool;
import ru.yurizhi.sprite.Background;
import ru.yurizhi.sprite.RoadLines;
import ru.yurizhi.sprite.Star;
import ru.yurizhi.sprite.RoadHatch;
import ru.yurizhi.sprite.game.*;
import ru.yurizhi.utils.EnemyEmitter;
import ru.yurizhi.utils.Font;

public class GameScreen extends Base2DScreen {

    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private enum State {PLAYING, GAME_OVER}

    private TextureAtlas atlas;
    private TextureAtlas atlasBut;
    private Texture bg;
    private Background background;
    private Star star[];
    private RoadHatch road[];
    private RoadLines line[];
    private MainShip mainShip;
    private MessageGameOver messageGameOver;
    private StartNewGame startNewGame;

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;

    private EnemyEmitter enemyEmitter;

    private Music music;

    private State state;

    private Font font;
    private StringBuilder sbFrags = new StringBuilder();
    private StringBuilder sbHP = new StringBuilder();
    private StringBuilder sbLevel = new StringBuilder();

    int frags = 0;

    @Override
    public void show() {
        super.show();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.setVolume(0.8f);
        music.play();
        bg = new Texture("textures/street1.jpg");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        atlasBut = new TextureAtlas("textures/batButton.atlas");

        star = new Star[1];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(atlasBut);
        }
        road = new RoadHatch[5];
        for (int i = 0; i < road.length; i++) {
            road[i] = new RoadHatch(atlasBut);
        }
        line = new RoadLines[2];
        for (int i = 0; i < line.length; i++) {
            line[i] = new RoadLines(atlasBut);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);
        mainShip = new MainShip(atlasBut, bulletPool, explosionPool, worldBounds);
        enemyPool = new EnemyPool(bulletPool, worldBounds, explosionPool, mainShip);

        enemyEmitter = new EnemyEmitter(atlasBut,atlas, enemyPool, worldBounds);
        messageGameOver = new MessageGameOver(atlas);
        startNewGame = new StartNewGame(atlas, this);
        this.font = new Font("font/font.fnt", "font/font.png");
        this.font.setSize(0.02f);
        startNewGame();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    private void update(float delta) {
        for (Star aStar : star) {
            aStar.update(delta);
        }
        for (RoadHatch aRoad : road) {
            aRoad.update(delta);
        }
        for (RoadLines aLine : line) {
            aLine.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        switch (state) {
            case PLAYING:
                mainShip.update(delta);
                bulletPool.updateActiveSprites(delta);
                enemyPool.updateActiveSprites(delta);
                enemyEmitter.generate(delta, frags);
                break;
            case GAME_OVER:
                break;
        }
    }

    private void checkCollisions() {
        if (state == State.PLAYING) {
            List<Enemy> enemyList = enemyPool.getActiveObjects();
            for (Enemy enemy : enemyList) {
                if (enemy.isDestroyed()) {
                    continue;
                }
                float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
                if (enemy.pos.dst2(mainShip.pos) < minDist * minDist) {
                    enemy.destroy();
                    mainShip.damage(enemy.getDamage());
                    return;
                }
            }
            List<Bullet> bulletList = bulletPool.getActiveObjects();

            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() == mainShip || bullet.isDestroyed()) {
                    continue;
                }
                if (mainShip.isBulletCollision(bullet)) {
                    mainShip.damage(bullet.getDamage());
                    bullet.destroy();
                }
            }

            for (Enemy enemy : enemyList) {
                if (enemy.isDestroyed()) {
                    continue;
                }
                for (Bullet bullet : bulletList) {
                    if (bullet.getOwner() != mainShip || bullet.isDestroyed()) {
                        continue;
                    }
                    if (enemy.isBulletCollision(bullet)) {
                        enemy.damage(mainShip.getDamage());
                        if (enemy.isDestroyed()) {
                            frags++;
                        }
                        bullet.destroy();
                    }
                }
            }
        }
    }

    private void deleteAllDestroyed() {
        if (mainShip.isDestroyed()) {
            state = State.GAME_OVER;
        }
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        Gdx.gl.glClearColor(0.5f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star aStar : star) {
            aStar.draw(batch);
        }
        for (RoadHatch aRoad : road) {
            aRoad.draw(batch);
        }
        for (RoadLines aLine : line) {
            aLine.draw(batch);
        }
        switch (state) {
            case PLAYING:
                mainShip.draw(batch);
                bulletPool.drawActiveSprites(batch);
                enemyPool.drawActiveSprites(batch);
                break;
            case GAME_OVER:
                messageGameOver.draw(batch);
                startNewGame.draw(batch);
                break;
        }
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }

    public void printInfo() {
        sbFrags.setLength(0);
        sbHP.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft(), worldBounds.getTop());
        font.draw(batch, sbHP.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop(), Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight(), worldBounds.getTop(), Align.right);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star aStar : star) {
            aStar.resize(worldBounds);
        }
        for (RoadHatch aRoad : road) {
            aRoad.resize(worldBounds);
        }
        for (RoadLines aLine : line) {
            aLine.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        atlasBut.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        mainShip.dispose();
        music.dispose();
        font.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (!mainShip.isDestroyed()) {
            mainShip.keyDown(keycode);
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (!mainShip.isDestroyed()) {
            mainShip.keyUp(keycode);
        }
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer);
        } else {
            startNewGame.touchDown(touch, pointer);
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer);
        } else {
            startNewGame.touchUp(touch, pointer);
        }
        return super.touchUp(touch, pointer);
    }

    public void startNewGame() {
        state = State.PLAYING;

        mainShip.startNewGame();
        frags = 0;
        enemyEmitter.setLevel(1);

        bulletPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
    }
}

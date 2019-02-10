package ru.yurizhi.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import ru.yurizhi.base.SpritesPool;
import ru.yurizhi.math.Rect;
import ru.yurizhi.sprite.game.Enemy;
import ru.yurizhi.sprite.game.MainShip;

public class EnemyPool extends SpritesPool<Enemy> {

    private Sound shootSound;
    private Rect worldBounds;
    private BulletPool bulletPool;
    private MainShip mainShip;

    public EnemyPool(BulletPool bulletPool, Rect worldBounds, MainShip mainShip) {
        this.shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.mainShip = mainShip;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(shootSound, bulletPool, worldBounds, mainShip);
    }

    @Override
    public void dispose() {
        super.dispose();
        shootSound.dispose();
    }
}

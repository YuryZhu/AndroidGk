package ru.yurizhi.pool;

import ru.yurizhi.base.SpritesPool;
import ru.yurizhi.sprite.game.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}


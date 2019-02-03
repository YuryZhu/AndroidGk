package ru.yurizhi.pool;

import ru.yurizhi.base.SpritesPool;
import ru.yurizhi.sprite.game.Road;

public class RoadPool extends SpritesPool<Road> {
    @Override
    protected Road newObject() {
        return new Road();
    }
}

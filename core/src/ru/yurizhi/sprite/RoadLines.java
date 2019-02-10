package ru.yurizhi.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.yurizhi.base.Sprite;
import ru.yurizhi.math.Rect;

public class RoadLines extends Sprite {

    private Vector2 v = new Vector2();
    private Rect worldBounds;
    private static float count = -0.5f;

    public RoadLines(TextureAtlas atlas) {
        super(atlas.findRegion("line"));
        setHeightProportion(1f);
        v.set(0, -0.24f);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        checkAndHandleBounds();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float posY = this.count;
        this.count += 1.03f;
        pos.set(0, posY);
    }

    private void checkAndHandleBounds() {
        if (getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if (getBottom() > worldBounds.getTop()) setTop(worldBounds.getBottom());
    }
}

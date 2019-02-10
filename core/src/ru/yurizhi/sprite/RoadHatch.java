package ru.yurizhi.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.yurizhi.base.Sprite;
import ru.yurizhi.math.Rect;
import ru.yurizhi.math.Rnd;

public class RoadHatch extends Sprite {

    private Vector2 v = new Vector2();
    private Rect worldBounds;

    public RoadHatch(TextureAtlas atlas) {
        super(atlas.findRegion("luk"));
        setHeightProportion(0.065f);
        v.set(0,-0.24f);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        checkAndHandleBounds();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(posX, posY);
    }

    private void checkAndHandleBounds() {
        if (getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
        if (getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if (getBottom() > worldBounds.getTop()) setTop(worldBounds.getBottom());
    }
}

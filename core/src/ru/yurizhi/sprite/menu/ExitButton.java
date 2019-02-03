package ru.yurizhi.sprite.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.yurizhi.math.Rnd;


public class ExitButton extends ScaledTouchUpButton {

    private Vector2 v = new Vector2();

    public ExitButton(TextureAtlas atlas) {
        super(atlas.findRegion("batman_exit1"));
        setHeightProportion(0.15f);
        pos.set(0.3f,0.42f);
        v.set(Rnd.nextFloat(-0.005f, 0.005f), Rnd.nextFloat(-0.5f, -0.1f));
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
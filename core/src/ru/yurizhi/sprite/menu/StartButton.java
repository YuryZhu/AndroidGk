package ru.yurizhi.sprite.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;


public class StartButton extends ScaledTouchUpButton {


    public StartButton(TextureAtlas atlas) {
        super(atlas.findRegion("batman_play"));
        setHeightProportion(0.17f);
        pos.set(0f,0f);
    }

    @Override
    public void action() {

    }
}

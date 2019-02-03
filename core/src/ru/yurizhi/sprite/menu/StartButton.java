package ru.yurizhi.sprite.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.yurizhi.math.Rect;
import ru.yurizhi.screen.GameScreen;


public class StartButton extends ScaledTouchUpButton {

    private Game game;

    public StartButton(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("batman_play"));
        setHeightProportion(0.17f);
        this.game = game;
        setHeightProportion(0.15f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + 0.45f);
        setRight(worldBounds.getRight() - 0.22f);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }
}

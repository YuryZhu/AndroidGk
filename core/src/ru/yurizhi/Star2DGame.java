package ru.yurizhi;

import com.badlogic.gdx.Game;
import ru.yurizhi.screen.MenuScreen;

public class Star2DGame extends Game {
    @Override
    public void create() {
        setScreen(new MenuScreen());
    }
}
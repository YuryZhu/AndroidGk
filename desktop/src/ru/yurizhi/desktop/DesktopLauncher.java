package ru.yurizhi.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.yurizhi.Star2DGame;
import ru.yurizhi.StarGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		// вертикальная синхронизация
		config.vSyncEnabled = true;
		new LwjglApplication(new Star2DGame(), config);
	}
}

package com.maincrab.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.Game;
import com.maincrab.SpaceGame;

import static com.maincrab.LevelScreen.globH;
import static com.maincrab.LevelScreen.globW;


public class DesktopLauncher {

	public static void main (String[] arg) {
	    Game myGame = new SpaceGame();
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(globW,globH);
		new Lwjgl3Application(myGame, config);
	}
}

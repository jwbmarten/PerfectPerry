package com.jwb.perfect;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.jwb.perfect.PerfectPerry;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Perfect_Perry");

		//////////////////////////////////
		//// SET WINDOW SIZE

		config.setWindowedMode(1920, 1080);
		new Lwjgl3Application(new PerfectPerry(), config);
	}
}

package com.derelictech.hikepunch.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.derelictech.hikepunch.HikePunch;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class DesktopLauncher {

    private static boolean repackTextures = true;

	public static void main (String[] arg) {

        if(repackTextures) {
            TexturePacker.process(".", "../packs", "pack");
        }

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 480;
		new LwjglApplication(new HikePunch(), config);
	}
}

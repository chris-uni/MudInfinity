package main;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import engine.DisplayManager;
import engine.Loader;
import engine.Renderer;
import entities.Camera;
import entities.LayerManager;
import shaders.StaticShader;
import stream.LevelLoader;
import textures.SheetTextureManager;
import textures.TextureManager;

public class Main{
	
	public static void main(String[] args){
		
		// Creates the game window and the OpenGL context.
		DisplayManager.createDisplay();
		
		// This is the model -> VAO loader class.
		Loader loader = new Loader();
		
		// The static shader in charge of linking between program and shader code.
		StaticShader shader = new StaticShader();
		
		// The games rendering system.
		Renderer renderer = new Renderer(shader);
		
		// Holds all game entities.
		LayerManager layeryManager = new LayerManager();
		
		// Holds all game textures.
		TextureManager textureManager = new TextureManager();
		
		SheetTextureManager sheetTextureManager = new SheetTextureManager();
		
		// In charge of loading the game entities/ textures.
		LevelLoader levelLoader = new LevelLoader(layeryManager, textureManager, sheetTextureManager, loader);
		
		// This is where the level and textures are loaded up.
		// levelLoader.loadTextures("res/levels/texture_map.txt");
		// levelLoader.loadLevel("res/levels/tile_map.txt");
		
		levelLoader.loadTileSheet("res/levels/tiled_map_test_2.tmx");
		
		// The games camera.
		Camera camera = new Camera();
		camera.setPosition(new Vector3f(0.5f, -0.25f, 0));
		
		// Main game loop.
		while(!Display.isCloseRequested()){
			
			camera.move();
			renderer.prepare();
			shader.start();
			shader.loadViewMatrix(camera);
	
			renderer.renderLayers(layeryManager, shader);
			
			shader.stop();
			DisplayManager.updateDisplay();
		}
		// Calls all relevant cleanUp methods.
		shader.cleanUp();
		loader.cleanUp();
		textureManager.cleanUp();
		layeryManager.cleanUp();
		
		// Closes the display.
		DisplayManager.closeDisplay();
	}
}

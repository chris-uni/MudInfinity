package models;

import org.lwjgl.util.vector.Vector2f;

import engine.Loader;
import textures.SpriteSheet;
import textures.Texture;

public class Tile{
	
	private SpriteSheet spriteSheet;
	
	private RawModel rawModel;
	private Texture texture;
	
	private float[] vertices;
	private int[] indices;
	private float[] textureCoords;

	public Tile(Loader loader, String textureName){
		
		this.vertices = new float[]{

				-1.0f, 1.0f, 0,
				-1.0f, 0, 0,
				0, 0, 0,
				0, 1.0f, 0
				
			};
			
			this.indices = new int[]{
					
				0, 1, 3,
				3, 1, 2
			};
			
			this.textureCoords = new float[]{
					
				0,0,
				0,1,
				1,1,
				1,0
			};
		
		this.rawModel = loader.loadToVAO(vertices, indices, textureCoords);
		this.texture = new Texture(loader.loadTexture(textureName));
	}
	
	public Tile(Loader loader, SpriteSheet sheet, int index, Vector2f textCoords){
		
		this.vertices = new float[]{

				-1.0f, 1.0f, 0,
				-1.0f, 0, 0,
				0, 0, 0,
				0, 1.0f, 0
				
			};
			
			this.indices = new int[]{
					
				0, 1, 3,
				3, 1, 2
			};
			
			this.textureCoords = new float[]{
								
				0, 0,
				0, 1,
				1, 1,
				1, 0
			};
		
		this.rawModel = loader.loadToVAO(vertices, indices, textureCoords);
		this.spriteSheet = sheet;
	}

	public RawModel getRawModel(){ return this.rawModel; }
	
	public Texture getTexture(){ return this.texture; }
	
	public SpriteSheet getSpriteSheet(){ return this.spriteSheet; }
}

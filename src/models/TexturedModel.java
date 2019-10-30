package models;

import textures.Texture;

public class TexturedModel {

	private RawModel rawModel;
	private Texture texture;
	
	public TexturedModel(RawModel raw, Texture texture){
		
		this.rawModel = raw;
		this.texture = texture;
	}

	public RawModel getRawModel(){
		return rawModel;
	}

	public Texture getTexture(){
		return texture;
	}	
}

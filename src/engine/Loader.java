package engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import models.RawModel;

public class Loader {

	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textureIDs = new ArrayList<Integer>();
	
	public RawModel loadToVAO(float[] vertices, int[] indices, float[] textCoords){
		
		int vaoID = createVAO();
		
		bindIndicesBuffer(indices);
		
		storeDataInAttributeList(0, 3, vertices);
		storeDataInAttributeList(1, 2, textCoords);
		
		unbindVAO();
		
		return new RawModel(vaoID, indices.length);
	}
	
	public int loadSpriteSheet(String file){
		
		String path = file.substring(2);
		String newPath = "res" + path;
		
		Texture texture = null;
		
		try{
			
			texture = TextureLoader.getTexture("PNG", new FileInputStream(newPath));
		}
		catch (FileNotFoundException e){
			
			e.printStackTrace();
		}
		catch (IOException e){
			
			e.printStackTrace();
		}
		
		int textureID = texture.getTextureID();
		textureIDs.add(textureID);
		
		return textureID;
	}
	
	public int loadTexture(String file){
		
		Texture texture = null;
		
		try{
			
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/textures/" + file+ ".png"));
		}
		catch (FileNotFoundException e){
			
			e.printStackTrace();
		}
		catch (IOException e){
			
			e.printStackTrace();
		}
		
		int textureID = texture.getTextureID();
		textureIDs.add(textureID);
		
		return textureID;
	}
	
	private int createVAO(){
		
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		
		GL30.glBindVertexArray(vaoID);
		
		return vaoID;
	}
	
	private void storeDataInAttributeList(int attribNum, int size, float[] vertices){
		
		int vboID = GL15.glGenBuffers();
		
		vbos.add(vboID);
		
		// Bind buffer to current vbo.
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		
		FloatBuffer buffer = toFloatBuffer(vertices);
		
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		
		GL20.glVertexAttribPointer(attribNum, size, GL11.GL_FLOAT, false,  0, 0);
		
		// Un-bind buffer from current vbo.
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private void unbindVAO(){
		
		GL30.glBindVertexArray(0);
	}
	
	private void bindIndicesBuffer(int[] indices){
		
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = toIntBuffer(indices);
		
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	public void cleanUp(){
		
		for(int vao : vaos){
			
			GL30.glDeleteVertexArrays(vao);
		}
		
		for(int vbo : vbos){
			
			GL15.glDeleteBuffers(vbo);
		}
		
		for(int textureID : textureIDs){
			
			GL11.glDeleteTextures(textureID);
		}
	}
	
	private IntBuffer toIntBuffer(int[] indices){
		
		IntBuffer buffer = BufferUtils.createIntBuffer(indices.length);
		buffer.put(indices);
		buffer.flip();
		
		return buffer;
	}
	
	private FloatBuffer toFloatBuffer(float[] vertices){
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
		
		buffer.put(vertices);
		buffer.flip();
		
		return buffer;
	}
}

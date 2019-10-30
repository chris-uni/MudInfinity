package engine;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import entities.Entity;
import entities.LayerManager;
import entities.Layer;
import models.RawModel;
import models.TexturedModel;
import models.Tile;
import shaders.StaticShader;
import toolbox.Maths;

public class Renderer {

	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000; 
	
	private final float RED = 0.5f;
	private final float GREEN = 0.5f;
	private final float BLUE = 0.5f;
	private final int ALPHA = 1;

	private Matrix4f projectionMatrix;
	
	public Renderer(StaticShader shader){
		
		createProjectionMatrix();
		
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	// Called once per frame.
	public void prepare(){
		
		// Determines the games background colour.
		GL11.glClearColor(RED, GREEN, BLUE, ALPHA);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}
	
	public void render(TexturedModel texModel){
		
		RawModel model = texModel.getRawModel();
		
		GL30.glBindVertexArray(model.getVaoID());
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texModel.getTexture().getID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	public void renderLayers(LayerManager entityManager, StaticShader shader){
		
		for(Layer layer : entityManager.getLayers()){
			
			for(Entity entity : layer.getEntities()){
				
				Tile tile = entity.getTile();
				
				RawModel model = tile.getRawModel();
				
				GL30.glBindVertexArray(model.getVaoID());
				
				GL20.glEnableVertexAttribArray(0);
				GL20.glEnableVertexAttribArray(1);
				
				// 
				shader.loadNumRows(tile.getSpriteSheet().getNumRows());
				shader.loadOffset(entity.getTextureXOffset(), entity.getTextureYOffset());
				//
				
				Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRx(), entity.getRy(), entity.getRz(), entity.getScale());
				shader.loadTransformationMatrix(transformationMatrix);
				
				GL13.glActiveTexture(GL13.GL_TEXTURE0);
				// GL11.glBindTexture(GL11.GL_TEXTURE_2D, tile.getTexture().getID());
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, tile.getSpriteSheet().getID());
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
	
				GL20.glDisableVertexAttribArray(0);
				GL20.glDisableVertexAttribArray(1);
				GL30.glBindVertexArray(0);
			}

		}
	}
	
	private void createProjectionMatrix(){
		
		float aspectRatio = (float)Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float)((1f / Math.tan(Math.toRadians(FOV / 2f)))) * aspectRatio;
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;
		
		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
	}
}

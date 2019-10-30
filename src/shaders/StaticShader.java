package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import entities.Camera;
import toolbox.Maths;

public class StaticShader extends ShaderProgram{

	private static final String VS_FILE = "src/shaders/vertexShader.txt";
	private static final String FS_FILE = "src/shaders/fragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	
	private int location_numRows;
	private int location_offset;
	
	public StaticShader() {
		super(VS_FILE, FS_FILE);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void bindAttributes(){
		
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

	@Override
	protected void getAllUniformLocations() {
		
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		
		location_numRows = super.getUniformLocation("numRows");
		location_offset = super.getUniformLocation("offset");
	}
	
	public void loadNumRows(int value){
		
		super.loadFloat(location_numRows, value);
	}
	
	public void loadOffset(float x, float y){
		
		super.load2fVector(location_offset, new Vector2f(x, y));
	}
	public void loadTransformationMatrix(Matrix4f matrix){
		
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix){
		
		super.loadMatrix(location_projectionMatrix, matrix);
	}

	public void loadViewMatrix(Camera camera){
		
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
}

package entities;

import org.lwjgl.util.vector.Vector3f;

import models.Tile;

public class Entity {

	private Tile tile;
	private Vector3f position;
	
	private float rx, ry, rz;
	
	private float scale;

	private int textureIndex = 0;
	
	public Entity(Tile tile, Vector3f position, float rx, float ry, float rz, float scale) {

		this.tile = tile;
		this.position = position;
		this.rx = rx;
		this.ry = ry;
		this.rz = rz;
		this.scale = scale;
	}
	
	public Entity(Tile tile, int textureIndex, Vector3f position, float rx, float ry, float rz, float scale) {

		this.tile = tile;
		this.textureIndex = textureIndex;
		this.position = position;
		this.rx = rx;
		this.ry = ry;
		this.rz = rz;
		this.scale = scale;
	}

	public float getTextureXOffset(){
		
		int column = this.textureIndex % tile.getSpriteSheet().getNumRows();
		return (float) column / tile.getSpriteSheet().getNumRows();
	}
	
	public float getTextureYOffset(){
		
		int row = this.textureIndex / tile.getSpriteSheet().getNumRows();
		return (float) row / tile.getSpriteSheet().getNumRows();
	}
	
	public void increasePosition(float x, float y, float z){
		
		this.position.x += x;
		this.position.y += y;
		this.position.z += z;
	}
	
	public void increaseRotation(float x, float y, float z){
		
		this.rx += x;
		this.ry += y;
		this.rz += z;
	}
	
	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getRx() {
		return rx;
	}

	public void setRx(float rx) {
		this.rx = rx;
	}

	public float getRy() {
		return ry;
	}

	public void setRy(float ry) {
		this.ry = ry;
	}

	public float getRz() {
		return rz;
	}

	public void setRz(float rz) {
		this.rz = rz;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}	
}

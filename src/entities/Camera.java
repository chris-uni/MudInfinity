package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	private Vector3f position = new Vector3f(0, 0, 0);
	
	public Camera(){}

	public void move(){
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			
			this.position.y += 0.003f;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			
			this.position.y -= 0.003f;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			
			this.position.x -= 0.003f;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			
			this.position.x += 0.003f;
		}
	}
	
	public void setPosition(Vector3f position){
		
		this.position = position;
	}
	
	public Vector3f getPosition() {
		return position;
	}	
}

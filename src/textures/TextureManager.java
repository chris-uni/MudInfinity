package textures;

import java.util.HashMap;
import java.util.Map;

public class TextureManager {

	private Map<Integer, String> textures = new HashMap<Integer, String>();
	
	public String getTexture(int key){ 
		
		return textures.get(key); 
	}
	
	public void addTexture(int key, String name){
		
		this.textures.put(key, name); 	
	}
	
	public void cleanUp(){
			
		textures.clear();
	}
}

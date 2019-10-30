package textures;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

public class SheetTextureManager {

	private Map<Integer, Vector2f> sheetTextures = new HashMap<Integer, Vector2f>();
	
	public Vector2f getTextureCoords(int key){
		
		return this.sheetTextures.get(key);
	}
	
	public void addSheetTexture(int key, Vector2f textCoords){
		
		this.sheetTextures.put(key, textCoords);
	}
	
	public void cleanUp(){
		
		this.sheetTextures.clear();
	}
}

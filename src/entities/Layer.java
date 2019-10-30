package entities;

import java.util.ArrayList;
import java.util.List;

public class Layer {

	private String name;
	
	private List<Entity> entities = new ArrayList<Entity>();
	
	public Layer(String name){
		
		this.name = name;
	}
	
	public String getName(){
		
		return this.name;	
	}
	
	public List<Entity> getEntities(){
		
		return entities;
	}
	
	public void addEntity(Entity entity){
		
		this.entities.add(entity);
	}
	
	public void cleanUp(){
		
		entities.clear();
	}
}

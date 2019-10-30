package entities;

import java.util.ArrayList;
import java.util.List;

public class LayerManager {

	private List<Layer> layers = new ArrayList<Layer>();
	
	public List<Layer> getLayers(){ return layers; }
	
	public void addLayer(Layer layer){ this.layers.add(layer); }
	
	public void cleanUp(){
		
		layers.clear();
	}
}

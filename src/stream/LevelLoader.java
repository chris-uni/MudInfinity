package stream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import engine.Loader;
import entities.Entity;
import entities.LayerManager;
import entities.Layer;
import models.Tile;
import textures.SheetTextureManager;
import textures.SpriteSheet;
import textures.TextureManager;

public class LevelLoader {

	private final float SCALE = 0.06f;
	
	private LayerManager entityManager;
	private TextureManager textureManager;
	private SheetTextureManager sheetTextureManager;
	
	private Loader loader;
	
	public LevelLoader(LayerManager entityManager, TextureManager textureManager, SheetTextureManager sheetTextureManager, Loader loader){
		
		this.entityManager = entityManager;
		this.textureManager = textureManager;
		this.sheetTextureManager = sheetTextureManager;
		this.loader = loader;
	}
	
	public void loadTileSheet(String filename){
		
		int mapWidth = 0, mapHeight = 0;
		int tileWidth = 0, tileHeight = 0;
		
		SpriteSheet spriteSheet = null;
		int sheetWidth = 0, sheetHeight = 0;
		String sheetSource;
		
		try{
			
			File tilemap = new File(filename);
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(tilemap);
			
			doc.getDocumentElement().normalize();
			
			// Gets the tile info.
			
			NodeList nMap = doc.getElementsByTagName("map");
			Node mapNode = nMap.item(0);
			
			if(mapNode.getNodeType() == Node.ELEMENT_NODE){
				
				Element attribs = (Element) mapNode;
				
				// Number of tiles in the xy direction.
				mapWidth = Integer.parseInt(attribs.getAttribute("width"));
				mapHeight = Integer.parseInt(attribs.getAttribute("height"));
				
				// Dimensions of each tile.
				tileWidth = Integer.parseInt(attribs.getAttribute("tilewidth"));
				tileHeight = Integer.parseInt(attribs.getAttribute("tileheight"));
				
				System.out.println("The tile map is " + mapWidth + " tiles wide and " + mapHeight + " tiles tall.");
				System.out.println("Each tile is " + tileWidth + " pixels wide and " + tileHeight + " pixels tall.");
			}
			
			// Gets the texture info.
			NodeList nImage = doc.getElementsByTagName("image");
			Node imageNode = nImage.item(0);

			if(imageNode.getNodeType() == Node.ELEMENT_NODE){
				
				Element attribs = (Element) imageNode;
				
				// Gets the width and height of the texture sheet, and gets the source of the texture sheet.
				sheetWidth = Integer.parseInt(attribs.getAttribute("width"));
				sheetHeight = Integer.parseInt(attribs.getAttribute("height"));
				sheetSource = attribs.getAttribute("source");
				
				System.out.println("The tile-sheet is " + sheetWidth + "x" + sheetHeight + " pixels, and is located at " + sheetSource);
				
				// Creates the spriteSheet.
				spriteSheet = new SpriteSheet(loader.loadSpriteSheet(sheetSource), sheetWidth, sheetHeight, mapWidth, mapHeight, tileWidth, tileHeight, sheetHeight / tileHeight, sheetWidth / tileWidth);
				
				// This is where we split up the sheet into individual tiles.
				processSpriteSheet(spriteSheet);
			}
			
			// First load the layer names.
			NodeList nLayers = doc.getElementsByTagName("layer");
			
			for(int i = 0; i < nLayers.getLength(); i++){
				
				// For every layer in the map.
				
				Node nLayer = nLayers.item(i);
				
				if(nLayer.getNodeType() == Node.ELEMENT_NODE){
					
					Element data = (Element) nLayer;
					
					// Gets the name of the layer.
					String layerName = data.getAttribute("name");
					
					// Gets the raw data of the map. (The values that correspond to a texture in the texture sheet).
					String mapData = data.getElementsByTagName("data").item(0).getTextContent();
					
					System.out.println(layerName + " contains the data: " + mapData);
					
					// Makes the new layer.
					Layer newLayer = new Layer(layerName);
					
					String[] rawData = mapData.split(",");
					int[][] processedData = processRawData(rawData, mapWidth, mapHeight);
					
					for(int y = 0; y < mapHeight; y++){
						
						for(int x = 0; x < mapWidth; x++){
							
							int index = processedData[x][y];
							
							newLayer.addEntity(new Entity(
											   new Tile(loader, spriteSheet, index - 1, sheetTextureManager.getTextureCoords(index)),
											   index - 1, // 
											   new Vector3f(x * SCALE, y * SCALE, -1), 
											   0,0,0, SCALE));
							
							System.out.println("new Entity -> texture key -> [" + x + "," + y + "] -> " + sheetTextureManager.getTextureCoords(index));
						}
					}
					
					entityManager.addLayer(newLayer);
				}
			}
		}
		catch(Exception e){
			
			e.printStackTrace();
		}
		
	}
	
	private int[][] processRawData(String[] rawData, int width, int height){
		
		int[][] data = new int[width][height];
		
		int counter = 0;
		
		for(int y = 0; y < height; y++){
			
			for(int x = 0; x < width; x++){
				
				String rd = rawData[counter];
				rd = rd.trim();
				
				data[x][y] = Integer.parseInt(rd);
				System.out.println("Index: [" + x + "," + y + "] -> " + rd);
				counter++;
			}
		}
		
		return data;
	}
	
	private void processSpriteSheet(SpriteSheet spriteSheet) {
		
		int sheetWidth = spriteSheet.getSheetWidth();
		int sheetHeight =  spriteSheet.getSheetHeight();
		
		int tileWidth = spriteSheet.getTileWidth();
		int tileHeight = spriteSheet.getTileHeight();
		
		int numX = sheetWidth / tileWidth;
		int numY = sheetHeight / tileHeight;
		
		int counter = 1;
				
		for(int y = 0; y < numY; y++){
			
			for(int x = 0; x < numX; x++){
				
				System.out.println("At index " + counter + " textCoords = " + x * tileWidth + ", " + y * tileHeight);
				sheetTextureManager.addSheetTexture(counter, new Vector2f(x * tileWidth, y * tileHeight));
				
				counter++;
			}
		}
	}

	/**
	 * This method loads the text file defining the layout of the game.
	 */
	public void loadLevel(String file){
		
		FileReader fr = null;
		
		Layer layer = new Layer("test layer");
		entityManager.addLayer(layer);
		
		try{
			
			fr = new FileReader(new File(file));
			
			BufferedReader reader = new BufferedReader(fr);
			String line;
			
			// This acts as the 'y' component.
			int lineCounter = 0;
			
			while(true){
				
				try{
					
					line = reader.readLine();
					
					if(line.startsWith("~")){
						
						reader.close();
						break;
					}
					else{
						
						String[] currentLine = line.split(",");
						int elementsInLine = currentLine.length;
						
						for(int x = 0; x < elementsInLine; x++){
							
							layer.addEntity(new Entity(
													new Tile(loader, textureManager.getTexture(Integer.parseInt(currentLine[x]))), 
													new Vector3f(x * SCALE, lineCounter * SCALE, -1), 0, 0, 0, SCALE));
						}
						
					}
					
					// Moving onto the next line in the file.
					lineCounter--;
				}
				catch(IOException e){
					
					e.printStackTrace();
				}
			}
		}
		catch(FileNotFoundException e){
			
			e.printStackTrace();
		}
	}
	
	/**
	 * This method loads the list of textures used in the game.
	 */
	public void loadTextures(String file){
		
		int textureIndex = 0;
		String textureName = "";
		
		FileReader fr = null;
		
		try{
			
			fr = new FileReader(new File(file));
			
			BufferedReader reader = new BufferedReader(fr);
			String line;
			
			while(true){
					
				try {
					
					line = reader.readLine();
					
					if(line.startsWith("~")){
						
						reader.close();
						
						break;
					}
					else{
						
						String[] currentLine = line.split(",");
						
						textureIndex = Integer.parseInt(currentLine[0]);
						textureName = currentLine[1];
						
						textureManager.addTexture(textureIndex, textureName);
					}
					
				}
				catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
		catch(FileNotFoundException e){
			
			e.printStackTrace();
		}
	}
}

package textures;

public class SpriteSheet {
	
	private int sheetWidth;
	private int sheetHeight;
	
	private int numTilesX;
	private int numTilesY;
	
	private int tileWidth;
	private int tileHeight;
	
	private int numColumns;
	private int numRows;

	private int textureID;
	
	public SpriteSheet(int textureID, int sheetWidth, int sheetHeight, int numTilesX, int numTilesY, int tileWidth, int tileHeight, int numColumns, int numRows) {
		
		this.sheetWidth = sheetWidth;
		this.sheetHeight = sheetHeight;
		
		this.numTilesX = numTilesX;
		this.numTilesY = numTilesY;
		
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		
		this.numColumns = numColumns;
		this.numRows = numRows;
		
		this.textureID = textureID;
	}

	public SpriteSheet(int textureID){
		
		this.textureID = textureID;
	}
	
	public int getID(){ return this.textureID; }

	public int getSheetWidth() {
		return sheetWidth;
	}

	public void setSheetWidth(int sheetWidth) {
		this.sheetWidth = sheetWidth;
	}

	public int getSheetHeight() {
		return sheetHeight;
	}

	public void setSheetHeight(int sheetHeight) {
		this.sheetHeight = sheetHeight;
	}
	
	public int getNumX(){
		
		return this.numTilesX;
	}
	
	public int getNumY(){
		
		return this.numTilesY;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}
	
	public int getNumColumns(){
		
		return this.numColumns;
	}
	
	public int getNumRows(){
		
		return this.numRows;
	}
}

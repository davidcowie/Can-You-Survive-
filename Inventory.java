import java.awt.Graphics2D;

public class Inventory {

	private InventorySquare[] inventoryTiles;
	private int startX,startY;
	private int spaceBetweenTiles = 10;
	
	private int wheelMovement;
	private int currentSelection;
	
	public Inventory(){
		inventoryTiles = new InventorySquare[StoredInfo.invBarNumber];
		startX = GamePanel.WIDTH/2 - inventoryTiles.length*StoredInfo.inventorySquareSize;
		startY = GamePanel.HEIGHT - 10 - StoredInfo.inventorySquareSize;
		initTiles();
		updateInventory();
		setSelection(new Pistol());
	}
	
	public void update(){
		currentSelection ++;
	}
	private void initTiles(){
		/*inventoryTiles[0] = new InventorySquare(startX ,startY,new Pistol());
		inventoryTiles[1] = new InventorySquare(startX+1*(spaceBetweenTiles + StoredInfo.inventorySquareSize*2) ,startY,null);
		inventoryTiles[2] = new InventorySquare(startX +2*(spaceBetweenTiles + StoredInfo.inventorySquareSize*2),startY,new Pistol());
		inventoryTiles[3] = new InventorySquare(startX +3*(spaceBetweenTiles + StoredInfo.inventorySquareSize*2),startY,new Pistol());
		inventoryTiles[4] = new InventorySquare(startX+4*(spaceBetweenTiles +StoredInfo.inventorySquareSize *2),startY,null);
		*/
		for(int i=0;i<inventoryTiles.length;i++){
			if( i < StoredInfo.storedWeapons.size()){
			inventoryTiles[i] = new InventorySquare(startX+i*(spaceBetweenTiles + StoredInfo.inventorySquareSize*2),startY,StoredInfo.storedWeapons.get(i));
			}else{
				inventoryTiles[i] = new InventorySquare(startX+i*(spaceBetweenTiles + StoredInfo.inventorySquareSize*2),startY,null);
				
			}
		}
	}
	public void updateInventory(){
		for(int i=0;i<inventoryTiles.length;i++){
			if(i<StoredInfo.storedWeapons.size()){
			inventoryTiles[i].setWeapon(StoredInfo.storedWeapons.get(i));
			}else {
				inventoryTiles[i].setWeapon(null);
			}
		}
	}
	public void setSelection(Weapon w){
		for(int i=0;i<inventoryTiles.length;i++){
			try{
			if(inventoryTiles[i].getWeapon().getName().equals(w.getName())){
				inventoryTiles[i].setisSelected(true);
			}else{
				inventoryTiles[i].setisSelected(false);
			}
			}catch(NullPointerException e){
				//System.out.println(e.getMessage());
			}
		}
	}
	public void draw(Graphics2D g){
		
		for(int i=0;i<inventoryTiles.length;i++){
			inventoryTiles[i].draw(g);
		}
	}
	public void setWheelMovement(int m){
		wheelMovement = m;
	}
}

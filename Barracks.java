import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Barracks {

	public static InventorySquare[] inventoryTiles;
	private InventorySquare[] equipedTiles;
	private int startX,startY;
	private int spaceBetweenTiles = 10;
	
	private int wheelMovement;
	private int currentSelection;
	
	
	private BarracksCell[] barracks;
	public BarracksCell[] equipedItems;
	private int holdingID;
	private boolean isHolding;
	
	public Barracks(){
		inventoryTiles = new InventorySquare[5];
		equipedTiles = new InventorySquare[3];
		startX = GamePanel.WIDTH/2 - StoredInfo.invBarNumber*StoredInfo.barracksSquareSize;
		startY = GamePanel.HEIGHT/2;
		//initTiles();
		//updateInventory();
		//setSelection(new Pistol());
		
		barracks = new BarracksCell[StoredInfo.barracksTileHeight*StoredInfo.invBarNumber];
		equipedItems = new BarracksCell[StoredInfo.invBarNumber];
		int x=0,y=0;
		for(int i=0;i<barracks.length;i++){
			startY = 100;
			barracks[i] = new BarracksCell(startX + x*(spaceBetweenTiles + StoredInfo.barracksSquareSize*2),startY + y*(spaceBetweenTiles + StoredInfo.barracksSquareSize*2),StoredInfo.barracksSquareSize*2,0);
			x++;
			if(x == StoredInfo.invBarNumber){
				x =0;
				y++;
			}
			
		}
		for(int i=0;i<equipedItems.length;i++){
			int id =0;
			if(i<StoredInfo.storedWeapons.size()){
				String name = StoredInfo.storedWeapons.get(i).getName();
				switch(name){
				case "pistol":
					id = StoredInfo.PISTOLID;
					break;
				case "smg":
					id = StoredInfo.SMGID;
					break;
				case "sniperRifle":
					id = StoredInfo.SNIPERID;
					break;
				case "shotgun":
					id = StoredInfo.SHOTGUNID;
					break;
				case "minigun":
					id = StoredInfo.MINIGUNID;
					break;
				case "revolver":
					id = StoredInfo.REVOLVERID;
					break;
				}
			}
			equipedItems[i] = new BarracksCell(startX + i*(spaceBetweenTiles + StoredInfo.barracksSquareSize*2),startY*StoredInfo.barracksTileHeight + spaceBetweenTiles,StoredInfo.barracksSquareSize*2,id);
		}
	}
	
	public void close(){
		StoredInfo.storedWeapons.clear();
		for(int i=0;i<equipedItems.length;i++){
			if(equipedItems[i].id != StoredInfo.emptyID){
				
				if(i < StoredInfo.storedWeapons.size()){
				StoredInfo.storedWeapons.set(i, StoredInfo.allWeapons[equipedItems[i].id -1]);
				}else{
					StoredInfo.storedWeapons.add( StoredInfo.allWeapons[equipedItems[i].id -1]);
				}
			}
		}
	}
	
	public boolean isEquipedItemsEmpty() throws InvalidEquipedWeaponsException{
		boolean empty;
		int numEquiped =0;
		for(int i =0;i<equipedItems.length;i++){
			if(equipedItems[i].id != StoredInfo.emptyID){
				//return false;
				empty = false;
				numEquiped++;
			}
		}
		if(numEquiped > 0){
			return false;
		}else {
			throw new InvalidEquipedWeaponsException("Equiped Items are empty");
		}
		//return true;
	}


	public void updateInventory(){
		
		/*for(int i=0;i<barracks.length;i++){
			if(i<StoredInfo.allWeaponsOwned.size()){
			//inventoryTiles[i].setWeapon(StoredInfo.storedWeapons.get(i));
			int id = 0;
			String name = StoredInfo.allWeaponsOwned.get(i).getName();
			switch(name){
			case "pistol":
				id = StoredInfo.PISTOLID;
				break;
			case "smg":
				id = StoredInfo.SMGID;
				break;
			case "sniperRifle":
				id = StoredInfo.SNIPERID;
				break;
			case "shotgun":
				id = StoredInfo.SHOTGUNID;
				break;
			case "minigun":
				id = StoredInfo.MINIGUNID;
				break;
			case "revolver":
				id = StoredInfo.REVOLVERID;
				break;
			}
			boolean canAdd=true;
			for(int j=0;j<equipedItems.length;j++){
				if(equipedItems[j].id == id){
					canAdd = false;
					break;
				}
				canAdd = true;
			}
			if(canAdd){
				for(int m =0;m<barracks.length;m++){
					if(barracks[m].id == StoredInfo.emptyID){
						barracks[m].id = id;
						break;
					}
				}
				addToBarracks();
				//barracks[i].id = id;
			}
			}else {
				barracks[i].id = StoredInfo.emptyID;
				//inventoryTiles[i].setWeapon(null);
			}	
		}*/
		
		for(int i=0;i<StoredInfo.allWeaponsOwned.size();i++){
			int id = 0;
			String name = StoredInfo.allWeaponsOwned.get(i).getName();
			switch(name){
			case "pistol":
				id = StoredInfo.PISTOLID;
				break;
			case "smg":
				id = StoredInfo.SMGID;
				break;
			case "sniperRifle":
				id = StoredInfo.SNIPERID;
				break;
			case "shotgun":
				id = StoredInfo.SHOTGUNID;
				break;
			case "minigun":
				id = StoredInfo.MINIGUNID;
				break;
			case "revolver":
				id = StoredInfo.REVOLVERID;
				break;
			}
			boolean canAdd = false;
			for(int j =0;j<equipedItems.length;j++){
				if(id == equipedItems[j].id){
					canAdd = false;
					break;
				}
				canAdd = true;
			}
			if(canAdd){
				addToBarracks(id);
			}
		}
	}
	public void addToBarracks(int id,int index){
		barracks[index].id = id;
	}
	public void addToBarracks(int id){
		for(int i=0;i<barracks.length;i++){
			if(barracks[i].id == StoredInfo.emptyID){
				barracks[i].id = id;
				break;
			}
		}
	}
	
	public void addToBarracks(){
		int id =0;
		String name = StoredInfo.allWeaponsOwned.get(StoredInfo.allWeaponsOwned.size()-1).getName();
		switch(name){
		case "pistol":
			id = StoredInfo.PISTOLID;
			break;
		case "smg":
			id = StoredInfo.SMGID;
			break;
		case "sniperRifle":
			id = StoredInfo.SNIPERID;
			break;
		case "shotgun":
			id = StoredInfo.SHOTGUNID;
			break;
		case "minigun":
			id = StoredInfo.MINIGUNID;
			break;
		case "revolver":
			id = StoredInfo.REVOLVERID;
			break;
		}
		boolean canAdd=true;
		for(int j=0;j<equipedItems.length;j++){
			if(equipedItems[j].id == id){
				canAdd = false;
				break;
			}
			canAdd = true;
		}
		for(int i=0;i<barracks.length;i++){
			if(barracks[i].id == StoredInfo.emptyID){
				
				barracks[i].id = id;
				break;
			}
		}
	}
	
	public void draw(Graphics2D g){
		
	
		
		for(int i=0;i<barracks.length;i++){
			barracks[i].draw(g);
		}
		for(int i=0;i<equipedItems.length;i++){
			equipedItems[i].draw(g);
		}
		
		if(isHolding){
			BufferedImage image = null;
			if(holdingID != StoredInfo.emptyID){
				switch(holdingID){
					case StoredInfo.PISTOLID:
						image = Images.pistolIcon;
						break;
					case StoredInfo.SMGID:
						image = Images.smgIcon;
						break;
					case StoredInfo.SNIPERID:
						image = Images.sniperIcon;
						break;
					case StoredInfo.SHOTGUNID:
						image = Images.shotgunIcon;
						break;
					case StoredInfo.MINIGUNID:
						image = Images.minigunIcon;
						break;
					case StoredInfo.REVOLVERID:
						image = Images.revolverIcon;
						break;
					}
				g.drawImage(image, ShopState.mouseX- StoredInfo.barracksSquareSize,ShopState.mouseY- StoredInfo.barracksSquareSize,StoredInfo.barracksSquareSize*2,StoredInfo.barracksSquareSize*2,null);
			}
		}
	}
	
/*	public BarracksCell getEquipedBarracksCell(int i){
		return new BarracksCell(equipedItems[i]);
	}*/
	public int getEquipedBarracksCellID(int i){
		return equipedItems[i].id;
	}
	boolean clickedInSquare = false;
	int originalCell;
	public void click(MouseEvent e){
		//if the right mouse button was clicked;
		
		clickedInSquare = false;
		
		if(e.getButton() == e.BUTTON1){
			for(int i=0;i<equipedItems.length;i++){
				if(equipedItems[i].contains(new Point(e.getX(),e.getY()))){
					if(!isHolding && equipedItems[i].id != StoredInfo.emptyID){
						holdingID = equipedItems[i].id;
						equipedItems[i].id = StoredInfo.emptyID;
						isHolding = true;
						
						originalCell = i;
					}else if(isHolding && equipedItems[i].id == StoredInfo.emptyID){
						equipedItems[i].id = holdingID;
						isHolding = false;
					}else if(isHolding && equipedItems[i].id != StoredInfo.emptyID){
						int temp = holdingID;
						holdingID = equipedItems[i].id;
						equipedItems[i].id = temp;
						isHolding = true;
					}
					clickedInSquare = true;
				}
			}
			for(int i=0;i<barracks.length;i++){
				if(barracks[i].contains(new Point(e.getX(),e.getY()))){
					if(!isHolding && barracks[i].id != StoredInfo.emptyID){
						holdingID = barracks[i].id;
						barracks[i].id = StoredInfo.emptyID;
						isHolding = true;
						
						originalCell = i;
					}else if(isHolding && barracks[i].id == StoredInfo.emptyID){
						barracks[i].id = holdingID;
						isHolding = false;
					}else if(isHolding && barracks[i].id != StoredInfo.emptyID){
						int temp = holdingID;
						holdingID = barracks[i].id;
						barracks[i].id = temp;
						isHolding = true;
					}
					clickedInSquare = true;
				}
			}
			
			if(!clickedInSquare && isHolding){
				System.out.println("HOLDING AND CLICKED NOT in CELL!");
				barracks[originalCell].id = holdingID;
				isHolding = false;
			}
		}
	}
	
	//handle exception
	public int getBarracksID(int index){
		if(index < 0 || index > barracks.length -1){
			return 0;
		}
		return barracks[index].id;
	}
}

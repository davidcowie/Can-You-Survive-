import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

public class ShopState extends GameState{
	
	private final boolean DEBUGSTATEMENTS = false;
	
	private Barracks barracks;
	private boolean showBarracks;
	private BarracksCell[] currentEquiped  = new BarracksCell[StoredInfo.invBarNumber];
	private int startX = 5, startY = GamePanel.HEIGHT - StoredInfo.inventorySquareSize*2 - 10;

	private Player player;
	
	private String[] names = {"APPLE","JOE","CILL","BOB","BABAANA","HULAHOOP","Lives","Ammo" };
	private Weapon[] weapons = {new Pistol(),new SMG(),new SniperRifle(),new Shotgun(),new Revolver(),new Minigun()};
	private Bomb[] bombs = {new Grenade(),new Bomb()};
	
	private ShopTile[] shopTiles;
	private ShopTile touchingTile;
	private boolean touchingATile;
	private int prevState;
	private boolean emptyEquiped;
	private ShopTile door;
	// i need something to store all of the weapon options -- and check if the player already owns it then have it like faded or something
	// maybe when player is ontop of the item press enter to buy it-- reduce cash and add to StoredInfo weapons
	//maybe one of them could be to replensih your health
	public ShopState(GameStateManager gsm,int prevState){
		this.gsm = gsm;
		this.prevState = prevState;
		/*shopTiles = new ShopTile[names.length];
		   for(int i=1;i<=names.length;i++){
			   if(i == names.length -3){
		    		 shopTiles[i-1] = new ShopTile(GamePanel.WIDTH-100, 25 + (i)*75,weapons[weapons.length-1]); // or lives
		    	 }else if(i == names.length-2 ){
		    		 shopTiles[i-1] = new ShopTile(75, 25 + (i-1)*75,weapons[weapons.length-2]);
		    	 }else if(i == names.length){
			    		 shopTiles[i-1] = new ShopTile(75, 25 + (i-1)*75,"grenade");
		    	 }else if(i == names.length - 1){
			    		shopTiles[i-1] = new ShopTile(GamePanel.WIDTH-100, 25 + (i)*75,"Lives"); // or lives
		    	 }
		    	 else if(i%2 == 0){
		    		shopTiles[i-1] = new ShopTile(75, 25 + (i-1)*75,weapons[i-1]);
		    	 }else{
		    		 shopTiles[i-1] = new ShopTile(GamePanel.WIDTH-100, 25 + i*75,weapons[i-1]);
		    	 }
		    	
		     }*/
		   
		   door = new ShopTile(GamePanel.WIDTH/2,GamePanel.HEIGHT +20,"door");
		//init();
	}
	
	@Override
	public void update() {

		if(!showBarracks){
		player.update();
		}
		
		if(player.getX() < 200 || player.getX() > 500){
			checkPlayerShopTileCollision();
		}
		
		
		
		//check if enter the door
		if(player.getY() > 400){
			double dx = door.getX()- player.getX();
			double dy = door.getY() - player.getY();
			double dist = Math.sqrt(dx*dx +dy*dy);
			if(dist < door.getRadius() + player.getRadius()){
				if(StoredInfo.prevState == GameStateManager.MENUSTATE){
					gsm.setState(GameStateManager.MENUSTATE);
				}else{
					gsm.setNextWaveState(GameStateManager.LEVEL1STATE, ++StoredInfo.waveNumber);
				}
			}
		}
		
	}
	
	public void checkPlayerShopTileCollision(){
		for(int i=0;i<shopTiles.length;i++){
			ShopTile s = shopTiles[i];
			int sx = s.getX();
			int sy = s.getY();
			int sr = s.getRadius();
			
			double dx = sx - player.getX();
			double dy = sy - player.getY();
			double dist = Math.sqrt(dx*dx + dy*dy);
			if(dist < sr + player.getRadius()){
				touchingTile = s;
				if(touchingTile.getName().equals("Lives") || touchingTile.getName().equals("Ammo") || touchingTile.getName().equals("grenade")){
					if(touchingTile.getName().equals("Lives")){
						if(StoredInfo.lives >= 3 ){
							touchingATile = false;
							break;
						}
						touchingATile = true;
					}else if(touchingTile.getName().equals("grenade")){
						if(StoredInfo.numBombs < StoredInfo.maxBombs){
							touchingATile = true;
							break;
						}
						touchingATile = false;
					}
				}else{
					for(int j=0;j<StoredInfo.allWeaponsOwned.size();j++){
						
						 if(StoredInfo.allWeaponsOwned.get(j).getName().equals(touchingTile.getName())){
							 touchingATile = false;
							break;
						 }
						
						touchingATile = true;
					}
					break;
				}
			}else {
				touchingATile = false;
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {
		// g.setColor(new Color(195, 195, 195));
	    // g.fillRect(0,0, GamePanel.WIDTH,GamePanel.HEIGHT);
	     g.drawImage(Images.shopBackground,0,0, GamePanel.WIDTH,GamePanel.HEIGHT, null);
	     for(int i =0;i<shopTiles.length;i++){
				shopTiles[i].draw(g);
		}
	     player.draw(g);
	     
	     if(touchingATile){
	    	 g.setColor(Color.BLACK);
	    	 g.setFont(new Font("Century Gothic",Font.BOLD,24));
	    	 String s = "Do you want to buy the " + touchingTile.getName() + " for ";
	    	 String s2 = "$" + touchingTile.getCost() ;
	    	 int length = (int) (g.getFontMetrics().getStringBounds(s, g).getWidth());
	    	 int length2 = (int) (g.getFontMetrics().getStringBounds(s2, g).getWidth());
	    	 g.drawString(s, GamePanel.WIDTH/2 - length/2, GamePanel.HEIGHT/2 - 50);
	    	 g.drawString(s2, GamePanel.WIDTH/2 - length2/2, GamePanel.HEIGHT/2 -20);
	     }
	     
	     door.draw(g);
	     //draws total cash avaliable
	     g.setColor(Color.white);
	     g.setFont(new Font("Dialog",Font.PLAIN,12));
	     g.drawString("cash: " + StoredInfo.totalCash, GamePanel.WIDTH-75, 20);
	     
	    
	     //draws lives
	     int liveIconSize = 6;
	        for(int i=0;i<StoredInfo.lives;i++){
	        
	        	//BufferedImage image = Images.heartIcon;
	    		g.drawImage(Images.heartIcon,GamePanel.WIDTH - 100 +i*(liveIconSize*2 + 5) , GamePanel.HEIGHT- 45, liveIconSize*2, liveIconSize*2,null);
	        	
	    		//g.setStroke(new BasicStroke(1));
	        }
	        //draw the number of bombs
	        int bombIconSize = 4;
	        for(int i=0;i<StoredInfo.maxBombs;i++){
	        	
	        	if(i<StoredInfo.numBombs){
	    		g.drawImage(Images.grenade, GamePanel.WIDTH - 120 + i*(bombIconSize*3 + 5), GamePanel.HEIGHT - 75, Images.grenade.getWidth()/2,Images.grenade.getHeight()/2,null);
	        	}
	        }
	        
	       //draw the weapons currently equiped
	        if(!showBarracks){
	        	
	        	
	        	  //draw telling houw to open the barracks
		        g.setColor(Color.BLACK);
		        g.setFont(new Font("Gothic",Font.PLAIN,18));
		        String s = "To edit equiped weapons" ;
		        String s2 = "open the barracks('b')";
		       // int length = g.getFontMetrics().stringWidth(s);
		        g.drawString(s, 5, startY - 22);
		        g.drawString(s2, startX, startY-5);
	        	
	        	
	        	for(int i=0;i<StoredInfo.invBarNumber;i++){
	        		currentEquiped[i].draw(g);
	        	}
	        }
	        
	        if(emptyEquiped){
	        	g.setColor(Color.black);
	        	g.setFont(new Font("Century Gothic",Font.BOLD,24));
		    	 String s = "You must have at least one weapon equiped ";
		    	 int length = (int) (g.getFontMetrics().getStringBounds(s, g).getWidth());
		    	 ///int length2 = (int) (g.getFontMetrics().getStringBounds(s2, g).getWidth());
		    	 g.drawString(s, GamePanel.WIDTH/2 - length/2, GamePanel.HEIGHT/2 - 55);
	        }
	      
	        
	      //drawing the barracks
	        if(showBarracks){
	        	barracks.draw(g);
	        }
	}
	
	//this should be called evertime the state is set to the shop
	//dont want init because that makes it slow
	public void start(){
		barracks.updateInventory();   
		   for(int i =0;i<currentEquiped.length;i++){
       		currentEquiped[i] = new BarracksCell(startX +i*(StoredInfo.inventorySquareSize*2 + 10),startY,StoredInfo.inventorySquareSize*2, barracks.getEquipedBarracksCellID(i));
       	}
		player.setX(GamePanel.WIDTH/2);
		player.setY(10);
	}

	@Override
	public void init() {
		player = new Player(GamePanel.WIDTH/2 , 10);
		player.setRadius(10);
		player.setSpeed(3);
		
		shopTiles = new ShopTile[names.length];
		   for(int i=1;i<=names.length;i++){
			   if(i == names.length -3){
		    		 shopTiles[i-1] = new ShopTile(GamePanel.WIDTH-100, 25 + (i)*75,weapons[weapons.length-1]); // or lives
		    	 }else if(i == names.length-2 ){
		    		 shopTiles[i-1] = new ShopTile(75, 25 + (i-1)*75,weapons[weapons.length-2]);
		    	 }else if(i == names.length){
			    		 shopTiles[i-1] = new ShopTile(75, 25 + (i-1)*75,"grenade");
		    	 }else if(i == names.length - 1){
			    		shopTiles[i-1] = new ShopTile(GamePanel.WIDTH-100, 25 + (i)*75,"Lives"); // or lives
		    	 }
		    	 else if(i%2 == 0){
		    		shopTiles[i-1] = new ShopTile(75, 25 + (i-1)*75,weapons[i-1]);
		    	 }else{
		    		 shopTiles[i-1] = new ShopTile(GamePanel.WIDTH-100, 25 + i*75,weapons[i-1]);
		    	 }
		    	
		     }
		   
		   door = new ShopTile(GamePanel.WIDTH/2,GamePanel.HEIGHT +20,"door");
		   
		   barracks = new Barracks();
		   barracks.updateInventory();
		   
		   for(int i =0;i<currentEquiped.length;i++){
       		currentEquiped[i] = new BarracksCell(startX +i*(StoredInfo.inventorySquareSize*2 + 10),startY,StoredInfo.inventorySquareSize*2, barracks.getEquipedBarracksCellID(i));
       	}
	}
	
	private void purchaseLives(){
		if(3 - StoredInfo.lives == 1){
			StoredInfo.totalCash -= 10;
		}else if(3 - StoredInfo.lives == 2){
			StoredInfo.totalCash -= 20;
		}else if(3 - StoredInfo.lives == 3){
			StoredInfo.totalCash -= 30;
		}
		StoredInfo.lives = 3;
	}
	
	private void purchaseGrenades(){
		StoredInfo.totalCash -= 10*(StoredInfo.maxBombs - StoredInfo.numBombs);
		StoredInfo.numBombs = StoredInfo.maxBombs;
	}
	
	private void purchaseTile(){
		
			
			if(touchingATile && StoredInfo.totalCash >= touchingTile.getCost()){
				if(touchingTile.getName().equals("Ammo")){
					for(int j = 0;j<StoredInfo.storedWeapons.size();j++){
					StoredInfo.storedWeapons.get(j).currentAmmo = StoredInfo.storedWeapons.get(j).getMaxAmmo();
					}
					StoredInfo.totalCash -= touchingTile.getCost();
				}else if(touchingTile.getName().equals("grenade")){
					purchaseGrenades();
				}else if(touchingTile.getName().equals("Lives")){
					purchaseLives();
				}
				else{
					StoredInfo.allWeaponsOwned.add(touchingTile.getWeapon());
				//StoredInfo.storedWeapons.add(touchingTile.getWeapon());
				StoredInfo.totalCash -= touchingTile.getCost();
				
				}
				touchingTile.buyTile();
				//barracks.updateInventory();
				barracks.addToBarracks();
			}
			//barracks.updateInventory();
			
			/*if(canAdd){
				if(touchingTile.getName().equals("Ammo")){
					for(int j = 0;j<StoredInfo.storedWeapons.size();j++){
					StoredInfo.storedWeapons.get(j).currentAmmo = StoredInfo.storedWeapons.get(j).getMaxAmmo();
					}
					StoredInfo.totalCash -= touchingTile.getCost();
				}else{
				StoredInfo.storedWeapons.add(touchingTile.getWeapon());
				StoredInfo.totalCash -= touchingTile.getCost();
				touchingTile.buyTile();
				}
			}*/
		//}
		//StoredInfo.storedWeapons.add(new SMG());
		//gsm.setState(gsm.LEVEL1STATE,2);
	}

	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W){
			player.setUp(true);
		}else if(key == KeyEvent.VK_D){
			player.setRight(true);
		}else if(key == KeyEvent.VK_A){
			player.setLeft(true);
		}else if(key == KeyEvent.VK_S){
			player.setDown(true);
		}
		
		if(key == KeyEvent.VK_ENTER){
			purchaseTile();
			
		}
		
		if(key == KeyEvent.VK_B){
			if(!showBarracks){
			showBarracks = true;
			}
			else{
				try{
					emptyEquiped = barracks.isEquipedItemsEmpty();
					showBarracks = false;
					
				}catch(InvalidEquipedWeaponsException arg){
					log(arg.getMessage());
					//barracks.equipedItems[0].id = barracks.getBarracksID(0);
					//barracks.updateInventory();
					showBarracks = true;
					emptyEquiped = true;
					
				}
				/*if(barracks.isEquipedItemsEmpty()){
					//maybe throw my exception here
					barracks.updateInventory();
					barracks.equipedItems[0].id = barracks.getBarracksID(0);
				}*/
					//showBarracks = false;
				if(!showBarracks){
					barracks.close();
					for(int i =0;i<currentEquiped.length;i++){
		        		currentEquiped[i] = new BarracksCell(startX +i*(StoredInfo.inventorySquareSize*2 + 10),startY,StoredInfo.inventorySquareSize*2, barracks.getEquipedBarracksCellID(i));
		        	}
				}
				}
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W){
			player.setUp(false);
		}else if(key == KeyEvent.VK_D){
			player.setRight(false);
		}else if(key == KeyEvent.VK_A){
			player.setLeft(false);
		}else if(key == KeyEvent.VK_S){
			player.setDown(false);
		}
		
		if(key == KeyEvent.VK_B){
			//showBarracks = false;
		}
		
	}
	public static int mouseX,mouseY;
	@Override
	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		mouseX = e.getX();
		mouseY = e.getY();
		player.setMousePos(mx,my);
		
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(showBarracks){
		barracks.click(e);
		}
	}

	
}

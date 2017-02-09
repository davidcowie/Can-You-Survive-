import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

public class ShopTile {

	private int x,y,radius;
	private Weapon weapon;
	private String name;
	private Color color;
	private int boughtId;
	private boolean isWeapon;
	private Image image;
	private int cost;
	public ShopTile(int x,int y,Weapon w){
		this.x = x;
		this.y = y;
		radius = 25;
		weapon = w;
		setImage();
		//image = w.getImage();
		isWeapon = true;
		name = weapon.getName();
		

		//buyTile();
		initBoughtID();
	}
	public ShopTile(int x,int y,Bomb b){
		this.x = x;
		this.y = y;
		setName();
		radius = 25;
		
		buyTile();
	}
	int r,gc,b;
	public ShopTile(int x,int y,String name){
		this.x = x;
		this.y = y;
		this.name = name;
		if(name.equals("door")){
			radius = 50;
		}else{
			radius = 25;
		}
		if(name.equals("Lives")){
			image = Images.heartIcon;
		}else if(name.equals("Ammo")){
			image = Images.coinIcon;
		}else if(name.equals("grenade")){
			image = Images.grenade;
		}
		 r = (int)(Math.random()*255)+1;
		 gc = (int)(Math.random()*255)+1;
		 b = (int)(Math.random()*255)+1;
		 isWeapon = false;
		 initBoughtID();
	}
	
	public void buyTile(){
	/*	if(weapon instanceof Pistol){
			for(int i=0;i<StoredInfo.storedWeapons.size();i++){
				if(StoredInfo.storedWeapons.get(i) instanceof Pistol){
					color = Color.cyan;
					break;
				}else{
				color = Color.BLUE;
				}
			}
		}else if(weapon instanceof SMG){
			for(int i=0;i<StoredInfo.storedWeapons.size();i++){
				if(StoredInfo.storedWeapons.get(i) instanceof SMG){
					color = Color.PINK;
					break;
				}else{
				color = Color.ORANGE;
				}
			}
		}else if(weapon instanceof SniperRifle){
			for(int i=0;i<StoredInfo.storedWeapons.size();i++){
				if(StoredInfo.storedWeapons.get(i) instanceof SniperRifle){
					color = Color.GREEN;
					break;
				}else{
					color = Color.RED;
				}
			}
		}else if(weapon instanceof Shotgun){
			for(int i=0;i<StoredInfo.storedWeapons.size();i++){
				if(StoredInfo.storedWeapons.get(i).getName().equals("shotgun")){
					color = Color.DARK_GRAY;
					break;
				}
				color = Color.gray;
			}
		}*/
		boughtId = 1;
		

	}
	
	private void initBoughtID(){
				if(getName().equals("Lives")){
		if(StoredInfo.lives >= 3 ){
			boughtId = 1;
			
		}else{
		boughtId = 0;
		}
	}else if(getName().equals("grenade")){
		if(StoredInfo.numBombs >= StoredInfo.maxBombs){
			boughtId = 1;
			
		}else{
			boughtId = 0;
			}
	}
	else{
	for(int i=0;i<StoredInfo.allWeaponsOwned.size();i++){
		if(StoredInfo.allWeaponsOwned.get(i).getName().equals(name)){
			boughtId = 1;
			break;
		}
		boughtId = 0;
	}
	}
	}
	
	
	public void draw(Graphics2D g){
		
		/*if(r!=0){
			g.setColor(new Color(r,gc,b));
		}else{
			g.setColor(color);
		}
		g.fillRect(x-radius, y-radius, radius*2, radius*2);*/
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial",Font.PLAIN,12));
		g.drawString(getName(), x-radius, y-radius);
		
		if(isWeapon){ //maybe use a weapon id
			
		g.drawImage(Images.shopIconTileSet[boughtId],x-radius,y-radius,radius*2,radius*2,null);
		g.drawImage(image,x-radius,y-radius,radius*2,radius*2,null);
			
		}else if(image != null){
			g.drawImage(Images.shopIconTileSet[boughtId],x-radius,y-radius,radius*2,radius*2,null);
			g.drawImage(image,x-radius,y-radius,radius*2,radius*2,null);
		}else{
			g.setColor(new Color(r,gc,b));
			g.fillRect(x-radius, y-radius, radius*2, radius*2);
		}
	}
	private void setImage(){
		String name = weapon.getName();
		switch(name){
		case "smg":
			image = Images.smgIcon;
			break;
		case "pistol":
			image = Images.pistolIcon;
			break;
		case "sniperRifle":
			image = Images.sniperIcon;
			break;
		case "shotgun":
			image = Images.shotgunIcon;
			break;
		case "minigun":
			image = Images.minigunIcon;
			break;
		case "flamethrower":
			image = Images.flamethrowerIcon;
			break;
		case "revolver":
			image = Images.revolverIcon;
			break;
		}
	}
	
	public void setName(){
		
	}
	
	public int getCost(){
		if(name.equals("Lives")){
			return 10;
		}else if(name.equals("Ammo")){
			return 1;
		}else if(name.equals("grenade")){
			return 10;
		}else if(name.equals("bomb")){
			return 1;
		}
		return weapon.getCost();
	}
	
	public Weapon getWeapon(){
		return weapon;
	}
	public String getName(){
		return name;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getRadius(){
		return radius;
	}
}

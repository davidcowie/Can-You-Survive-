import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class InventorySquare {

	private int x,y;
	public int radius = 15;
	
	private Weapon weapon;
	private boolean selected;
	
	private BufferedImage image;
	//have 2 images for the border as to which gun is selected
	// then also use the shoptile gun images
	
	public InventorySquare(int x,int y,Weapon w){
		this.x = x;
		this.y = y;
		weapon = w;
		radius = StoredInfo.inventorySquareSize;
		if(weapon != null){
		setImage();
			//image = Images.pistolIcon; 
		}
	}
	public InventorySquare(int x,int y,int r,Weapon w){
		this.x = x;
		this.y = y;
		radius = r;
		weapon = w;
		if(weapon != null){
		setImage();
			//image = Images.pistolIcon; 
		}
	}
	public void update(){
		
	}
	
	public void draw(Graphics2D g){
		g.setColor(new Color(255,0,0,10));
		g.fillRect(x-getRadius(), y-getRadius(), getRadius()*2, getRadius()*2);
		if(image != null){
			//g.drawImage(weapon.getShopImages()[0], x-radius, y-radius, radius*2, radius*2,null);
			g.drawImage(image, x-radius, y-radius, radius*2, radius*2,null);
		}
		if(selected){
		g.setColor(Color.DARK_GRAY);
		}else {
			g.setColor(Color.GRAY);
		}
		g.setStroke(new BasicStroke(5));
		g.drawRect(x-getRadius(), y- getRadius(), getRadius()*2, getRadius()*2);
		g.setStroke(new BasicStroke(1));
		
	}
	public void setWeapon(Weapon w){
		this.weapon = w;
		if(weapon != null){
		setImage();
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
		}
	}
	public Weapon getWeapon(){
		return weapon;
	}
	public void setisSelected(boolean b){
		selected = b;
	}
	public int getRadius() {
		return radius;
	}
	
	

	
}

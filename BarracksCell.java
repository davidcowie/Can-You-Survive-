import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class BarracksCell extends Rectangle {

	public int id;
	private Image image;
	
	//why when i set the image in here it doesnt work but if i set it in draw then it does work????????
	public BarracksCell(int x,int y,int r,int id){
		setBounds(x,y,r,r);
		this.id = id;
		//image = Images.pistolIcon;
	}
	
	
	public void draw(Graphics g){
		g.drawImage(Images.tile_cell, x, y, width,height,null);
		
		if(id != StoredInfo.emptyID){
			switch(id){
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
			case StoredInfo.REVOLVERID:
				image = Images.revolverIcon;
				break;
			case StoredInfo.MINIGUNID:
				image = Images.minigunIcon;
				break;
			}
			//image = Images.pistolIcon;
			//g.drawImage(StoredInfo.allWeaponImages[id-1], x, y, width,height,null);
			g.drawImage(image, x, y, width,height,null);
	
		}else {
			g.setColor(Color.red);
			//g.fillRect(x, y, width, height);
		}
	}
	
	public Rectangle getBarracksCellRectangle(){
		return new Rectangle(x,y,width,height);
	}
	
	public void setX(int x){
		this.x = x;
	}
}

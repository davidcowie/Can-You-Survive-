import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class PowerUp {

	private double x,y;
	private int type,radius;
	public static final int CASH = 0;
	private Color color;
	private BufferedImage image;
	
	public PowerUp(double x,double y, int type){
		this.x = x;
		this.y = y;
		this.type = type;
		radius = 2;
		switch(type){
		case CASH:
			color = Color.green;
			image = Images.cashIcon;
			break;
		}
	}
	
	public void draw(Graphics2D g){
		if(image != null){
			g.drawImage(image,(int)(x-radius*4), (int)(y-radius*4), radius*8,radius*8,null);
		}else{
		g.setColor(color);
		g.fillRect((int)(x-radius), (int)(y-radius), (int)radius*radius, (int)(radius*radius));
		}
	}
	public int getType(){
		return type;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getRadius() {
		return radius;
	}
	
}

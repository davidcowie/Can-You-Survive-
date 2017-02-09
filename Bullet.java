import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Bullet extends Entity {

	private double radians;
	private BufferedImage image;
	
	public Bullet(double ang,double x,double y,double bulletSpeed){
		super(x,y);
		speed = bulletSpeed;
		radius = 3;
		height = 3;
		width = 2;
		radians = Math.toRadians(ang);
		dx = Math.cos(radians)*speed;
		dy = Math.sin(radians)*speed;
		image = rotateImage(Images.bullet,ang+90);
		//System.out.println(dy);
	}
	public Bullet(Bullet that){
		super(that);
		speed = 2;
		radius = 2;
		radians =that.getRadians();
		dx = Math.cos(radians)*speed;
		dy = Math.sin(radians)*speed;
	}
	
	// boolean so can remove it from an array list
	public boolean updateB(){
		x+=dx;
		y+=dy;
		
		if(x < radius || y < radius || x + radius > GamePanel.WIDTH || y +radius > GamePanel.HEIGHT){
			return true;
		}
		
		return false;
	}
	
	
	
	public String toString(){
		return "--BULLET-- x: " + x + " y: " + y ;
	}
	
	public double getRadians(){
		return radians;
	}
	
	public void draw(Graphics2D g) {
		//super.draw(g, Color.BLACK);
		g.drawImage(image,(int)(x-radius), (int)(y-radius), (int)radius*2,(int) radius*2,null);
		
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}

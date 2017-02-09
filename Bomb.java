import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

public class Bomb {

	protected int x;
	protected int y;
	private int radius;
	protected boolean detonate;
	private double explosionRadius;
	private double maxExplosionRadius;
	private int damage;
	
	private boolean removeBomb;
	
	protected Image image;
	
	public Bomb(double x,double y){
		this.x = (int) x;
		this.y = (int) y;
		radius = 5;
		maxExplosionRadius = 20;
		explosionRadius = 1;
		damage = 5;
	}
	public Bomb(){
		
	}
	
	public boolean update(){
		
		if(detonate){
			explosionRadius += 0.5;
			if(explosionRadius>= maxExplosionRadius){
				removeBomb = true;
			}
		}
		
		
		if(removeBomb){
			return true;
		}
		return false;
	}
	
	public void draw(Graphics2D g){
		if(!detonate){
			if(image != null){
				g.drawImage(image, (x-radius), y-radius, radius*5,radius*5,null);
			}else{
				g.setColor(Color.black);
				g.fillRect(x-radius, y-radius, radius*2, radius*2);
			}
		}else{
			//g.setColor(Color.red);
			//g.drawOval((int)(x-explosionRadius), (int)(y-explosionRadius), (int)(explosionRadius*2), (int)(explosionRadius*2));
			g.drawImage(Images.fireRing, (int)(x-explosionRadius), (int)(y-explosionRadius), (int)(explosionRadius*2), (int)(explosionRadius*2), null);
		}
	}
	
	public double getExplosionRadius(){
		return explosionRadius;
	}
	public int getX(){
		return x;
	}
	public boolean isDetonated(){
		return detonate;
	}
	public int getY(){
		return y;
	}
	public int getDamage(){
		return damage;
	}
	
	public void setDetonate(boolean b){
		detonate = b;
	}
}

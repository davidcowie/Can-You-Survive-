import java.awt.Image;
import java.awt.image.BufferedImage;

public abstract class Weapon {

	protected long fireRate;
	protected  int clipSize;//num of bullets when gun is fully loaded
	protected int currentClipSize; 
	protected  int power;
	protected  long reloadTime;
	protected  String name;
	protected int cost;
	protected double bulletSpeed;
	protected int maxAmmo;
	protected int currentAmmo;
	protected BufferedImage[] shopImages;
	protected BufferedImage image;
	
	
	public BufferedImage getImage(){
		return image;
	}
	public BufferedImage[] getShopImages(){
		return shopImages;
	}
	public int getMaxAmmo(){
		return maxAmmo;
	}
	public int getCurrentAmmo(){
		return currentAmmo;
	}
	
	public long getFireRate() {
		return fireRate;
	}
	public int getClipSize() {
		return clipSize;
	}
	public int getPower() {
		return power;
	}
	public long getReloadTime() {
		return reloadTime;
	}
	public  String getName(){
		return name;
	}
	public int getCost(){
		return cost;
	}
	public double getBulletSpeed(){
		return bulletSpeed;
	}
	
	
}

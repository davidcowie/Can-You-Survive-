
public class Grenade extends Bomb {

	private long startTime,explodeDelay = 2000;
	private double angle;
	private final int grenadeSpeed = 3;
	private double dx,dy;
	private double startX,startY;
	private double throwingRange = 150;
	
	public Grenade(double x,double y,double angle){
		super(x,y);
		startX = x;
		startY = y;
		startTime = System.nanoTime();
		double rad = Math.toRadians(angle);
		dx = Math.cos(rad)*grenadeSpeed;
		dy = Math.sin(rad)*grenadeSpeed;
		image = Images.grenade;
		
	}
	public Grenade(){
		 
	}
	
	public boolean update(){
		double distx = x - startX;
		double disty = y - startY;
		double dist = Math.sqrt(distx*distx+disty*disty);
		if(dist <throwingRange){
		x+=dx;
		y+= dy;
		}
		long elapsed = (System.nanoTime() - startTime)/1000000;
		if(elapsed > explodeDelay){
			detonate = true;
		}
		
		return super.update();
	}
}

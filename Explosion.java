import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Explosion {

	
	private int x,y,amount,range;
	private long explosionStart,explosionDelay = 2500;
	
	private Point[] points;
	private int[] radii;
	
	private boolean fade;
	
	public Explosion(double x,double y){
		this.x = (int) x;
		this.y = (int) y;
		explosionStart = System.nanoTime();
		amount = (int)(Math.random()*5 +1);
		range = 10;
		points = new Point[amount];
		radii = new int[amount];
		
		int tempX = (int) x,tempY = (int) y;
		for(int i=0;i<amount;i++){
			int xdist = (int)(Math.random()*range + 1);
			if((int)(Math.random()*2) == 1) {
				xdist = -xdist;
			}
			int ydist = (int)(Math.random()*range + 1);
			if((int)(Math.random()*2)==1){
				ydist = -ydist;
			}
			tempX += xdist;
			tempY += ydist;
			points[i] = new Point(tempX ,tempY);
			radii[i] = (int)(Math.random()*3 + 1);
			tempX = this.x;
			tempY = this.y;
			
		}
	}
	
	
	public boolean update(){
		long elapsed = (System.nanoTime() - explosionStart)/1000000;
		if(elapsed > 1500){
			fade = true;
		}
		if(elapsed > explosionDelay){
			return true;
		}
		
		
		return false;
	}
	private int alpha = 255;
	public void draw(Graphics2D g){
	//	g.setColor(Color.red);
		if(fade){
			alpha -= 5;
		}
		if(alpha <= 0){
			alpha = 0;
		}
		g.setColor(new Color(255,0,0,alpha));
		for(int i=0;i<amount;i++){
			g.fillOval(points[i].x-radii[i], points[i].y-radii[i], 2*radii[i], 2*radii[i]);
		}
	}
}

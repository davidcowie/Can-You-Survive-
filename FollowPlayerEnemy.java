import java.awt.Graphics2D;

public class FollowPlayerEnemy extends BasicEnemy {

	private Player player;
	private double speed = 1;
	private int health = 1,damage = 1;
	public FollowPlayerEnemy(double x, double y, double radius, int id,Player player) {
		super(x, y, radius, .5,1,1);
		this.player = player;
		
		//dx = player.getX() - x;
		//dy = player.getY() - y;
		
	}
	
	public boolean updateB(){
	/*	boolean remove = super.updateB();
		if(remove){
			return true;
		}*/
		
		if(player.getX() > x){
			x+= Math.abs(dx);
		}else if(player.getX() < x) {
			x -= Math.abs(dx);
		}
		
		if(player.getY() > y){
			y+= Math.abs(dy);
		}else {
			y-= Math.abs(dy);
		}
		
		checkWallCollision();
		
		if(dead){
			return true;
		}
		
		return false;
	}
	public void draw(Graphics2D g){
		super.draw(g);
	}
	/*public FollowPlayerEnemy(Enemy that){
		
	}*/
}

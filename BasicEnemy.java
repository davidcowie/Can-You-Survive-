import java.awt.Color;
import java.awt.Graphics2D;

public class BasicEnemy extends Enemy{

	//the basic enemy is separate because of the movement of the enemy. if the enemy moves in straight lines
	//then the enemy is a basic enemy
	
	private double angle;
	
	public BasicEnemy(double x, double y,double radius,int id) {
		super(x, y,radius);
		speed = 3;
		damage = 1;
		health = 1;
		dx = Math.cos(Math.toRadians(Math.random()*140 +20))*speed;
		dy = Math.sin(Math.toRadians(Math.random()*140 + 20))*speed;
		this.id = id;
		
		angle = -90 - Math.toDegrees(Math.atan2(dy, dx));
		//log(angle +"");
		//image = rotateImage(Images.flamethrowerIcon,angle);
	}
	
	public BasicEnemy(BasicEnemy that){
		super(that);
	}
	
	public BasicEnemy(double x, double y,double radius,double speed,double health,int damage){
		super(x,y,radius);
		this.speed = speed;
		this.health = health;
		this.damage = damage;
		double randXAngle = Math.random()*140 +20;
		dx = Math.cos(Math.toRadians(randXAngle))*speed;
		dy = Math.sin(Math.toRadians(Math.random()*140 + 20))*speed;
		
		angle = -90 - Math.toDegrees(Math.atan2(dy, dx));
	}
	public BasicEnemy(double x,double y,int[] stuff){
		super(x,y,stuff[0]);
		this.speed = stuff[1];
		this.health = stuff[2];
		this.damage = stuff[3];
		double randXAngle = Math.random()*140 +20;
		dx = Math.cos(Math.toRadians(randXAngle))*speed;
		dy = Math.sin(Math.toRadians(Math.random()*140 + 20))*speed;
		angle = -90 - Math.toDegrees(Math.atan2(dy, dx));
	}
	
	@Override
	public boolean updateB(){
		x+=dx;
		y+=dy;
		//angle = 0;
		image = rotateImage(Images.zombie,-1*angle);
		//angle++;
		checkWallCollision();
		
		if(dead){
			return true;
		}
		
		return false;
	}
	
	protected void checkWallCollision(){
		if(x <radius && dx <0 || x +radius > GamePanel.WIDTH && dx > 0){
			dx = -dx;
			angle = -90 - Math.toDegrees(Math.atan2(dy, dx));
			//image = rotateImage(Images.zombie,angle);
		}
		if(y<radius && dy <0 || y + radius > GamePanel.HEIGHT && dy >0){
			dy=-dy;
		
			angle = -90 - Math.toDegrees(Math.atan2(dy, dx));
			//image = rotateImage(Images.zombie,angle);
		}
	}
	
	/*public void hit(int damage){
		health -= damage;
		if(health <=0 ){
			dead = true;
		}
		super.hit(damage);
	}*/
	
	/*public void draw(Graphics2D g) {
		super.draw(g,Color.GREEN);
	}*/

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}

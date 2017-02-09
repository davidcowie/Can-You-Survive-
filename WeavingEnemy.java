import java.awt.Color;
import java.awt.Graphics2D;

public class WeavingEnemy extends Enemy {

	
	public WeavingEnemy(double x, double y,double radius,int id) {
		super(10,10 ,radius);
		speed = 3;
		damage = 1;
		health = 1;
		//dx = Math.cos(Math.toRadians(Math.random()*140 +20))*speed;
		//dy = Math.sin(Math.toRadians(Math.random()*140 + 20))*speed;
		dx = 1;
		dy =0;
	}
	
	public WeavingEnemy(BasicEnemy that){
		super(that);
		this.radius = that.getRadius();
		speed = 1.5;
		health = 1;
		dx = Math.cos(Math.toRadians(Math.random()*140 +20))*speed;
		dy = Math.sin(Math.toRadians(Math.random()*140 + 20))*speed;
		this.id = that.getID();
	}
	
	public boolean updateB(){
		
		x+=dx;
		y = 20*Math.sin(.1*x) + 100;
		//y+=dy;
		
		
		double angle = Math.toRadians(45);
		//x = Math.abs(x*Math.cos(angle) - y*Math.sin(angle));
		//y = y*Math.cos(angle) + y*Math.sin(angle);
		System.out.println("x: " + x + " y: " + y);
		//x+=dx;
		if(x <radius && dx <0 || x +radius > GamePanel.WIDTH && dx > 0){
			dx = -dx;
		}
		if(y<radius && dy <0 || y + radius > GamePanel.HEIGHT && dy >0){
			dy=-dy;
		}
		
		if(dead){
			return true;
		}
		
		
		return false;
	}
	
	public void hit(int damage){
		super.hit(damage);
	}
	
	public int getDamage(){
		return damage;
	}
	public boolean isDead(){
		return dead;
	}
	public double getHealth(){
		return health;
	}
	public int getID(){
		return id;
	}
	
	public boolean equals(BasicEnemy that){
		if(this.x == that.getX() && this.y == that.getY() && this.health == that.getHealth() && this.isDead() == that.isDead() && this.id == that.getID()){
			return true;
		}else {
			return false;
		}
	}
	public String toString(){
		return "--ENEMY-- x: " + x + " y: " + y + " health: " + health + " id: " + id;
	}


	public void draw(Graphics2D g) {
		super.draw(g,Color.GREEN);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	

	
}

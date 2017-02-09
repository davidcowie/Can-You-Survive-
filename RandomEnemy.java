import java.awt.Color;
import java.awt.Graphics2D;

public class RandomEnemy extends BasicEnemy {

		
		
		
		//possible enemy types:
		//basic enemy just goes straight at enemy-- can have different speeds for them
		//random enemy that just goes random ways---maybe randomly turns instead of just bouncing off the walls
		//enemy goes in circles on the way to the enemy or like weaves
		//if add obsticals then have enemies that can climb over them
		public RandomEnemy(double x, double y,double radius,int id) {
			super(x, y,radius,id);
			this.radius = radius;
			speed = 3;
			damage = 1;
			health = 1;
			dx = Math.cos(Math.toRadians(Math.random()*140 +20))*speed;
			dy = Math.sin(Math.toRadians(Math.random()*140 + 20))*speed;
		
		}
		
		public RandomEnemy(BasicEnemy that){
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
			y+=dy;
			
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
			return "--RandomENEMY-- x: " + x + " y: " + y + " health: " + health + " id: " + id;
		}


		public void draw(Graphics2D g) {
			super.draw(g,Color.GREEN);
		}

		@Override
		public void update() {
			// TODO Auto-generated method stub
			
		}
		
	

}

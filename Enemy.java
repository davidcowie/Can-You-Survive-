import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

public class Enemy extends Entity{

	protected double health;
	protected boolean dead;
	protected int damage;
	protected int id;
	
	protected BufferedImage image;
	//possible enemy types:
		//basic enemy just goes straight at enemy-- can have different speeds for them
		//random enemy that just goes random ways---maybe randomly turns instead of just bouncing off the walls
		//enemy goes in circles on the way to the enemy or like weaves
		//if add obsticals then have enemies that can climb over them
	public Enemy(double x,double y,double radius){
		super(x,y);
		this.radius = radius;
		width = height = radius*2;
		image = Images.zombie;
	}
	
	public Enemy(Enemy that){
		super(that);
		this.radius = that.getRadius();
		speed = 1.5;
		health = 1;
		dx = Math.cos(Math.toRadians(Math.random()*140 +20))*speed;
		dy = Math.sin(Math.toRadians(Math.random()*140 + 20))*speed;
		
	}


	
	public void hit(int damage){
		health -= damage;
		if(health <=0 ){
			dead = true;
		}
		log("THE ENEMY HAS LOST DAMGAE");
	}


	@Override
	public boolean updateB() {
		// TODO Auto-generated method stub
		return false;
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
		return "--ENEMY" + this.getClass() + "-- x: " + x + " y: " + y + " health: " + health + " id: " + id;
	}
	
	public void draw(Graphics2D g) {
		//super.draw(g,Color.GREEN);
		g.drawImage(image, (int)(x-radius),(int)(y-radius),(int)radius*2,(int)radius*2,null);
		AffineTransform tx = g.getTransform();
		AffineTransform tx1 = g.getTransform();
		Graphics2D g2 = image.createGraphics();
	/*	g.setColor(Color.red);
		g.fillRect(0,0, 50, 50);
		//g.translate(100, 100); // moves the origin to 10,10
		g.setColor(Color.PINK);
		g.fillRect(0,0, 50, 50);
		//g.scale(50/GamePanel.WIDTH, 50/GamePanel.HEIGHT);
		g.rotate(45);
		g.setColor(Color.BLUE);
		g.fillRect(0,0, 50, 50);
		g.translate(100, 100);
		g.setColor(Color.ORANGE);
		g.fillRect(0,0, 50, 50);*/
		
	/* this is close
	 * 	tx1.translate(350, 100);
		g.setTransform(tx1);
		g.setColor(Color.PINK);
		g.fillRect(0,0, 50, 50);
		tx1.rotate(45);
		g.setTransform(tx1);
		g.setColor(Color.BLUE);
		g.fillRect(0,0, 50, 50);
		g.setColor(Color.GREEN);
		g.fillRect(0,0, 10, 10);*/
		//tx1.getRotateInstance(45);
		//g.rotate(45,-GamePanel.WIDTH/2, -GamePanel.HEIGHT/2);
		//g.ro
		
		g.setTransform(tx);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	public void printAllInfo(){
		super.printAllInfo();
		String indent = "------>";
		log(indent + "health: " + getHealth());
		log(indent + "damage: " + getDamage());
		log(indent + "isDead: "+ isDead());
	}
	
}

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {

	
	private boolean left,right,up,down;
	private double health,maxHealth;
	private int lives;
	private boolean dead;
	
	protected boolean firing;
	protected long firingTimer,firingDelay = 1000/5,firingStart;
	private long reloadTimeElapsed;
	private int mouseX,mouseY;
	
	private int cash;
	
	private ArrayList<Weapon> weapons;
	protected Weapon weapon;
	protected long reloadStart;
	
	private boolean recovering;
	private long recoveringStart,recoveringDelay = 1500;
	
	private BufferedImage image;
	
	//shop when player walks around and when he collided with a weapon he can buy it and door at end to advance to next level
	// stored information state for cash and weapons --- static class
	//add obsticals
	
	public Player(Player that){
		super(that.getX(),that.getY());
		radius = that.getRadius();
		speed = that.getSpeed();
		dx = that.getDx();
		dy = that.getDy();
		health = that.getHealth();
		dead = that.isDead();
	}

	
	public Player(double x,double y){ // in the future maybe a gun or something
		super(x,y);
		radius= 7;
		speed = 2;
		health = maxHealth = 10;
		dead = false;
		firing = false;
		lives = StoredInfo.lives;
		
		weapons = StoredInfo.storedWeapons; // static class weapon
		//addWeaponToArsenal(new Pistol());
		//addWeaponToArsenal(new SMG());
		weapon = weapons.get(0);
		cash = StoredInfo.totalCash;
	}
	
	public void update(){
		if(left){
			dx = -speed;
		}
		if(right){
			dx = speed;
		}
		if(up){
			dy = -speed;
		}
		if(down){
			dy = speed;
		}
		
		x += dx;
		y += dy;
		
		dx =0;
		dy =0;
		
		if(x < radius){
			x = radius;
		}else if(x +radius > GamePanel.WIDTH){
			x = GamePanel.WIDTH - radius;
		}
		if(y < radius){
			y = radius;
		}else if(y + radius > GamePanel.HEIGHT){
			y = GamePanel.HEIGHT - radius;
		}
		
		
		shoot();
		
		if(recovering){
		recoveryUpdate();
		}
		
		if(health <=0){
			
			log("you lost a life!");
			
		}
		//System.out.println(weapon.getName() + " ad   " + weapon.currentClipSize);
		
	}
	
	private void loseLife(){
		StoredInfo.lives -= 1;
		log(StoredInfo.lives +"");
		if(StoredInfo.lives <0){
			dead = true;
		}
		health = maxHealth;
		x = GamePanel.WIDTH/2;
		y = GamePanel.HEIGHT/2;
	}
	
	public void shoot(){
		if(firing && weapon.currentClipSize >0){
			long elapsed = (System.nanoTime() - firingStart)/1000000;
			if(elapsed > weapon.getFireRate()){
				if(weapon.getName().equals("shotgun")){
					Level1State.bullets.add(new Bullet(getAngle(),x,y,weapon.getBulletSpeed()));
					Level1State.bullets.add(new Bullet(getAngle()+45,x,y,weapon.getBulletSpeed()));
					Level1State.bullets.add(new Bullet(getAngle()-45,x,y,weapon.getBulletSpeed()));
					Level1State.bullets.add(new Bullet(getAngle()+22,x,y,weapon.getBulletSpeed()));
					Level1State.bullets.add(new Bullet(getAngle()-22,x,y,weapon.getBulletSpeed()));
					weapon.currentClipSize -= 3;
				}else {
					Level1State.bullets.add(new Bullet(getAngle(),x,y,weapon.getBulletSpeed()));
					weapon.currentClipSize--;
				}
				
				if(weapon.currentClipSize ==0){
					reloadStart = System.nanoTime();
				}
				firing = false;
				firingStart = System.nanoTime();
			}
		}
		if(weapon.currentClipSize <=0){
			reload();
		}
	}
	
	public void reload(){
		reloadTimeElapsed = (System.nanoTime() - reloadStart)/1000000;
		if(reloadTimeElapsed >= weapon.getReloadTime()){
			weapon.currentClipSize = weapon.clipSize;
			log("RELOADED");
		}else{
			log("RELOADING");
		}
	}
	
	public void hit(int damage){
		health -= damage;
		if(health<=0.0){
			loseLife();
		}
		recovering = true;
		recoveringStart = System.nanoTime();
		counter = 0;
	}
	
	private void recoveryUpdate(){
		long elapsed = (System.nanoTime() - recoveringStart)/1000000;
		if(elapsed >= recoveringDelay){
			recovering = false;
		}
	}
	private int counter;
	int degrees =0;
	public void draw(Graphics2D g) {
		//super.draw(g, Color.blue);
		image = rotateImage(Images.player,getAngle()-5);
		
		//image = super.rotateImage(Images.player,degrees++);
		//g.drawImage(image, (int)(x-radius*4/2), (int)(y-radius*4/2),(int)(radius*4),(int)(radius*4),null);
		//g.setColor(Color.red);
		//g.drawRect((int)(x-radius*4/2), (int)(y-radius*4/2),(int)(radius*4),(int)(radius*4));
		if(recovering){
		//	g.setColor(new Color(255,255,255,150));
			//g.fillRect((int)(x-radius*4/2), (int)(y-radius*4/2),(int)(radius*4),(int)(radius*4));
			if(counter % 10 !=0){
			g.drawImage(image, (int)(x-radius*4/2), (int)(y-radius*4/2),(int)(radius*4),(int)(radius*4),null);
			}
			counter++;
		}else {
			g.drawImage(image, (int)(x-radius*4/2), (int)(y-radius*4/2),(int)(radius*4),(int)(radius*4),null);
		}
		
		//g.drawImage(image,rotate(image,getAngle()-5),null);
	}
	
	protected BufferedImage rotateImage(BufferedImage image,double degrees){
		double rotationRequired = Math.toRadians (degrees);
       // double locationX = image.getWidth() / 2 - 6;
     //   double locationY = image.getHeight() / 2 ;
		 double locationX = radius*4-6;
	       double locationY = radius*4;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC); //TYPE_BILINEAR
        
        return op.filter(image, null);
	}
	protected AffineTransform rotate(BufferedImage image,double degrees){
		double rotationRequired = Math.toRadians (degrees);
       // double locationX = image.getWidth() / 2 - 6;
     //   double locationY = image.getHeight() / 2 ;
		 double locationX = radius*4-6;
	       double locationY = radius*4;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        
       return tx;
	}
	
	/*public double getAngle(){
		//System.out.println(mouseX + " mouse y  " + mouseY);
		double dx = mouseX - x;
		double dy = mouseY - y;
		double angle = Math.atan2(dy, dx);
		
		return Math.toDegrees(angle);
	}*/
	public double getAngle(){
		return angle;
	}
	public void setAngle(double x2,double y2){
		double dx = mouseX - x;
		double dy = mouseY - y;
		 angle = Math.toDegrees(Math.atan2(dy, dx));
	}
	private double angle;
	public void setAngle(double ang){
		angle = Math.toDegrees(ang);
	}
	
	public void addWeaponToArsenal(Weapon w){
		weapons.add(w);
	}
	public void setWeapon(Weapon w){
		/*if(weapons.contains(w)){
			int weaponIndex = weapons.indexOf(w);
			this.weapon = weapons.get(weaponIndex);
		}*/
		
		for(int i=0;i<weapons.size();i++){
			if(w.getName().equals(weapons.get(i).getName())){
				int weaponIndex = i;
				this.weapon = weapons.get(i);
			}
		}
	}
	
	public void switchWeapons(){
		if(weapons.size() > 1){
		int weaponIndex = weapons.indexOf(weapon);
		if(weaponIndex == weapons.size() -1){
			weaponIndex =0;
		}else{
			weaponIndex++;
		}
		weapon = weapons.get(weaponIndex);
		}
		System.out.println(weapon.getName() + " bullets left in clip " + weapon.currentClipSize);
	}
	
	public String getCurrentWeaponName(){
		return weapon.getName();
	}
	public Weapon getCurrentWeapon(){
		return weapon;
	}
	
	public boolean isRecovering(){
		return recovering;
	}
	public void addCash(int c){
		this.cash += c;
		System.out.println("You picked up some cash!");
	}
	public int getCash(){
		return cash;
	}
	public void setFiring(boolean b){
		firing = b;
	}
	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public void setLeft(boolean b){
		left = b;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}
	
	public double getMaxHealth(){
		return maxHealth;
	}
	public int getLives(){
		return lives;
	}
	public long getReloadTimeElapsed(){
		return reloadTimeElapsed;
	}
	
	public ArrayList<Weapon> getWeapons(){
		return weapons;
	}
	public boolean isFiring(){
		return firing;
	}
	public boolean equals(Player that){
		if(super.equals(that) && this.health == that.getHealth()){
			return true;
		}else {
			return false;
		}
	}
	
	public String toString(){
		return "--PLAYER-- x: " + x + " y: " + y + " health: " + health;
	}


	
	
	
	public void setMousePos(int x,int y){
		mouseX = x;
		mouseY = y;
		setAngle(mouseX,mouseY);
	}

	@Override
	public boolean updateB() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void printAllInfo(){
		String indent = "-------> ";
		super.printAllInfo();
		log(indent + "directions-- left: " + isLeft() +" right: " + isRight() + " up: " + isUp() + " down: " + isDown());
		log(indent + "Angle faceing: " + getAngle());
		log(indent + " Health: "+getHealth());
		log(indent + "Cash: " + getCash());
		log(indent + "Current Weapon Name: "+ getCurrentWeaponName());
		log(indent + "Bullets left in magazine: " + getCurrentWeapon().currentClipSize);
		log(indent + "Lives left: " + getLives() );
		log(indent + "isDead: " + isDead());
		log(indent + "All weapons: " );
		for(int i=0;i<weapons.size();i++){
			log(indent + indent + weapons.get(i).getName());
		}
		log(indent + "isFiring: " + isFiring());
		
	}
}

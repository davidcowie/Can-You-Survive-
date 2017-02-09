
public class PlayerDemo extends Player {
	
	private double shootx,shooty; // where to shoot at
	private long moveDelay = 1000,moveStartTime,moveElapsedTime;
	private long whenToFireDelay = 1000,whenToFireStart;
	public PlayerDemo(double x, double y) {
		super(x, y);
		moveStartTime = System.nanoTime();
		whenToFireStart = System.nanoTime();
		firing = true;
	}
	
	//thinking maybe move kinda randomly but shoot at targets
	@Override
	public void update(){
		moveElapsedTime = (System.nanoTime() - moveStartTime)/1000000;
		if(moveElapsedTime >= moveDelay){ // pick a new direction
			pickNewRandomDirection();
			moveStartTime = System.nanoTime();
		}
		
		x += dx;
		y+=dy;
		
		if(x < radius){
			x = radius;
			dx = -dx;
		}else if(x +radius > GamePanel.WIDTH){
			x = GamePanel.WIDTH - radius;
			dx = -dx;
		}
		if(y < radius){
			y = radius;
			dy = -dy;
		}else if(y + radius > GamePanel.HEIGHT){
			y = GamePanel.HEIGHT - radius;
			dy = -dy;
		}
		setWhereToShoot();
		long elapsed = (System.nanoTime() - whenToFireStart)/1000000;
		if(elapsed >= whenToFireDelay){
			firing = true;
		}
		//firing = true;
		shoot();
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
					Level1State.bullets.add(new Bullet(getShootAngle(shootx,shooty),x,y,weapon.getBulletSpeed()));
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
	
	private void setWhereToShoot(){
		if(Level1State.enemies.size() > 0){
		shootx = Level1State.enemies.get(0).getX();
		shooty = Level1State.enemies.get(0).getY();
		}
	}
	
	
	private double getShootAngle(double shootToX,double shootToY){
		this.shootx = shootToX;
		this.shooty = shootToY;
		double dx = shootx - x;
		double dy = shooty - y;
		
		double angle =Math.atan2(dy, dx);
		return Math.toDegrees(angle);
	}
	
	private void pickNewRandomDirection(){
		int rand = (int)(Math.random()*3+1);
		if( rand == 1){
			dx = speed;
		}else if(rand == 2){
			dx = -speed;
		}else {
			dx = 0;
		}
		
		rand = (int)(Math.random()*3);
		if(rand == 1){
			dy = speed;
		}else if(rand == 2){
			dy = -speed;
		}else {
			dy =0;
		}
	}

	
	
}

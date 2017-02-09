import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Level1State extends GameState {
	
	private final boolean DEBUGSTATEMENTS = false;
	
	private GameStateManager gsm;
	
	public Player player;
	public static ArrayList<Bullet> bullets;
	public static ArrayList<Enemy> enemies;
	public ArrayList<PowerUp> powerups;
	public ArrayList<Explosion> explosions;
	public Inventory inventory;
	public  ArrayList<Bomb> bombs;
	private ArrayList<Text> texts;
	
	private int waveNumber;
	//drawing the wave number timer
	private long waveStartTime,waveDelay = 5000;
	private boolean waveStart;
	
	private boolean isDemoMode;
	
	/*
	 * maybe want an equip, like create a class state
	 * then can pick grenades or like c4
	 */
	public Level1State(GameStateManager gsm, int waveNumber,boolean isDemoMode){
		this.gsm = gsm;
		this.waveNumber = waveNumber;
		this.isDemoMode = isDemoMode;
		init();
	}

	@Override
	public void update() {
		
		
		player.update();
		//update the enemies
		for(int i =0; i<enemies.size();i++){
			boolean remove = enemies.get(i).updateB();
			if(remove){
				powerups.add(new PowerUp(enemies.get(i).getX(),enemies.get(i).getY(),PowerUp.CASH));
				explosions.add(new Explosion(enemies.get(i).getX(),enemies.get(i).getY()));
				enemies.remove(i);
				i--;
				if(DEBUGSTATEMENTS){
				log("YOU KILLED AN ENEMY YAY!");
				}
			}
		}
		//bullet update
		for(int i=0;i<bullets.size();i++){
			boolean remove = bullets.get(i).updateB();
			if(remove){
				bullets.remove(i);
				i--;
			}
		}
		//explosion update
		for(int i =0;i<explosions.size();i++){
			boolean remove = explosions.get(i).update();
			if(remove){
				explosions.remove(i);
				i--;
			}
		}
		//bomb update
		  for(int i=0;i<bombs.size();i++){
	        	boolean remove =bombs.get(i).update();
	        	if(remove){
	        		bombs.remove(i);
	        		i--;
	        	}
	        }
		 //text update
		 for(int i=0;i<texts.size();i++){
	       boolean remove = texts.get(i).update();
	       if(remove){
	    	   texts.remove(i);
	    	   i--;
	       }
	       }
		
		if(!player.isRecovering()){
			checkPlayerEnemyCollision(player,enemies);
		}
		checkEnemyBulletCollision();
		//checkEnemyBulletCollisionBoundingBox();
		checkPlayerPowerUpCollision();
		if(bombs.size() > 0){
			checkBombExplosionEnemyCollision();
		}
		
		if(enemies.size() == 0){
			if(DEBUGSTATEMENTS){
			log("you killed all the enemies in that wave");
			}
			
			if(!isDemoMode){
				StoredInfo.totalCash += player.getCash();
				StoredInfo.storedWeapons = player.getWeapons();
				StoredInfo.score += player.getCash()*3;
				
				//StoredInfo.storedWeapons.add(new SMG());
				if(DEBUGSTATEMENTS){
				log("YOU are now in the store");
				}
				//gsm.setState(GameStateManager.LOADSTATE); // it works but it is just slow, could make it look cooler and maybe faster. 
				gsm.setState(gsm.SHOPSTATE);
			}else{
				if(DEBUGSTATEMENTS){
				log("THE DEMO WON AND NOW YOU ARE BACK AT THE MAIN MENU");
				}
				gsm.setState(GameStateManager.MENUSTATE);
			}
			
		}
		
		
		if(player.isDead()){
			log("You,the player, died! FOREVER!! sucks!");
			FileHandler.saveHighScores();
		}
	}
	
	public void checkBombExplosionEnemyCollision(){
		for(int j=0;j<bombs.size();j++){
		if(bombs.get(j).isDetonated()){
			for(int i=0;i<enemies.size();i++){
				Enemy e = enemies.get(i);
				double ex = e.getX();
				double ey = e.getY();
				double er = e.getRadius();
				
				double dx = ex - bombs.get(j).getX();
				double dy = ey - bombs.get(j).getY();
				double dist = Math.sqrt(dx*dx+dy*dy);
				if(dist < er + bombs.get(j).getExplosionRadius()){
					e.hit(bombs.get(j).getDamage());
					if(DEBUGSTATEMENTS){
					log("YOU HIT AN ENEMY WITH A BOMB");
					}
				}
			}
		}
		}
	}
	
	public void checkEnemyBulletCollision(){
		for(int i=0;i<bullets.size();i++){
			Bullet b = bullets.get(i);
			double bx = b.getX();
			double by = b.getY();
			double br = b.getRadius();
			for(int j=0;j<enemies.size();j++){
				Enemy e = enemies.get(j);
				double ex = e.getX();
				double ey = e.getY();
				double er = e.getRadius();
				
				double dx = bx - ex;
				double dy = by - ey;
				double dist = Math.sqrt(dx*dx + dy*dy);
				if(dist < br + er){ // bullet hit an enemy
					e.hit(player.getCurrentWeapon().getPower()); // maybe have it be the players damage or have the damage associated with the bullet which associates with the weapon
					bullets.remove(i);
					i--;
					if(DEBUGSTATEMENTS){
					System.out.println("YOU SHOT AN ENEMY! HORRAY!");
					}
					break;
				}
			}
		}
	}
	
	// this definately has some bugs
	public void checkEnemyBulletCollisionBoundingBox(){
		for(int i=0;i<bullets.size();i++){
			for(int j =0;j<enemies.size();j++){
				if(bullets.get(i).getBoundingBox().intersects(enemies.get(j).getBoundingBox())){
					enemies.get(i).hit(player.getCurrentWeapon().getPower());
					bullets.remove(i);
					i--;
					System.out.println("YOU SHOT AN ENEMY WITH A RECTANGLE");
					break;
				}
			}
		}
	}
	
	public void checkPlayerPowerUpCollision(){
		for(int i=0; i<powerups.size();i++){
			PowerUp p = powerups.get(i);
			double px = p.getX();
			double py = p.getY();
			double pr = p.getRadius();
			
			double dx = px - player.getX();
			double dy = py - player.getY();
			double dist = Math.sqrt(dx*dx+dy*dy);
			if(dist < pr + player.getRadius()){
				if(p.getType() == PowerUp.CASH){
					player.addCash(5);
				}
				powerups.remove(i);
				i--;
			}
		}
	}
	
	public void checkPlayerEnemyCollision(Player p, ArrayList<Enemy> enemies){
		for(int i=0;i<enemies.size();i++){
			Enemy e = enemies.get(i);
			double ex = e.getX();
			double ey = e.getY();
			double er = e.getRadius();
			
			double dx = ex - p.getX();
			double dy = ey - p.getY();
			double dist = Math.sqrt(dx*dx + dy*dy);
			if(dist < er + p.getRadius()){
				p.hit(e.getDamage());
				if(DEBUGSTATEMENTS){
				System.out.println("PLAYER HAS BEEN HIT BY AN ENEMY");
				}
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {
        g.setColor(new Color(195, 195, 195));
        g.fillRect(0,0, GamePanel.WIDTH,GamePanel.HEIGHT);
        g.drawImage(Images.background, 0, 0, GamePanel.WIDTH,GamePanel.HEIGHT,null);
        
        for(int i=0;i<powerups.size();i++){
        	powerups.get(i).draw(g);
        }
        for(int i=0;i<explosions.size();i++){
        	explosions.get(i).draw(g);
        }
        for(int i=0;i<bombs.size();i++){
        	bombs.get(i).draw(g);
        }
        player.draw(g);
        for(int i=0;i<enemies.size();i++){
        	enemies.get(i).draw(g);
        }
        for(int i=0;i<bullets.size();i++){
        	bullets.get(i).draw(g);
        }
        
        
        inventory.draw(g);
        
        
        g.setColor(Color.white);
       g.setFont(new Font("Century Gothic",Font.PLAIN,12));
       String cash = ""+ player.getCash();
       int length = (int) (g.getFontMetrics().getStringBounds(cash, g).getWidth());
        g.drawString(cash, GamePanel.WIDTH-15 - length, 25);
        g.drawImage(Images.coinIcon, GamePanel.WIDTH-15 - length - 25, 10, null);
        
        //drawing the remaining enemeies
        g.drawString("Enemies left: " + enemies.size(), 5, 10);
        
        //draw gun type and amo
        g.drawString("Gun: " + player.getCurrentWeaponName(), GamePanel.WIDTH-75, GamePanel.HEIGHT-50);
        g.drawString("Clip: " + player.getCurrentWeapon().currentClipSize, GamePanel.WIDTH-75, GamePanel.HEIGHT-25);
        if(player.getCurrentWeapon().currentClipSize == 0){
        	g.setColor(Color.white);
        	g.fillRect(GamePanel.WIDTH-75, GamePanel.HEIGHT-10, (int)(50 - 50*player.getReloadTimeElapsed()/player.getCurrentWeapon().getReloadTime()), 5);
        }
        
        //health bar
        g.setColor(Color.red);
        g.drawRect(15, GamePanel.HEIGHT-30,(int) player.getMaxHealth()*10, 10);
        g.fillRect(15, GamePanel.HEIGHT-30, (int)player.getHealth()*10, 10);
        //draw the number of lives
        int liveIconSize = 6;
        for(int i=0;i<StoredInfo.maxLives;i++){
        	if(i<StoredInfo.lives){
        		/*g.setColor(new Color(255,0,0,150));
        		g.fillOval(20 +i*(liveIconSize*2 + 5) , GamePanel.HEIGHT- 45, liveIconSize*2, liveIconSize*2);
        		BufferedImage image = Images.heartIcon;
        		g.drawImage(image,100,200,null);
        		g.drawImage(image,20 +i*(liveIconSize*2 + 5) , GamePanel.HEIGHT- 45, liveIconSize*2, liveIconSize*2,null);
        	*/}
        		/*g.setColor(new Color(255,0,0));
        		g.setStroke(new BasicStroke(2));
        		g.drawOval(20 +i*(liveIconSize*2 + 5) , GamePanel.HEIGHT- 45, liveIconSize*2, liveIconSize*2);*/
        	if(i<StoredInfo.lives){
        	BufferedImage image = Images.heartIcon;
    		g.drawImage(image,20 +i*(liveIconSize*2 + 5) , GamePanel.HEIGHT- 45, liveIconSize*2, liveIconSize*2,null);
        	}
    		g.setStroke(new BasicStroke(1));
        }
        //draw the number of bombs
        int bombIconSize = 4;
        for(int i=0;i<StoredInfo.maxBombs;i++){
        	/*if(i<StoredInfo.numBombs){
        		g.setColor(Color.gray);
        		g.fillOval(20 +i*(bombIconSize*2 + 5) , GamePanel.HEIGHT- 60, bombIconSize*2, bombIconSize*2);
        	}
        		g.setColor(Color.BLACK);
        		g.setStroke(new BasicStroke(2));
        		g.drawOval(20 +i*(bombIconSize*2 + 5) , GamePanel.HEIGHT- 60, bombIconSize*2, bombIconSize*2);
    		g.setStroke(new BasicStroke(1));
    		*/
        	if(i<StoredInfo.numBombs){
    		g.drawImage(Images.grenade, GamePanel.WIDTH - 100 + i*(bombIconSize*3 + 5), GamePanel.HEIGHT - 100, Images.grenade.getWidth()/2,Images.grenade.getHeight()/2,null);
        	}
        }
        
        //draw all the texts
        for(int i=0;i<texts.size();i++){
        	texts.get(i).draw(g);
        }
        
        int drawLocationX = 300;
        int drawLocationY = 300;

        // Rotation information
        //BufferedImage image =rotateImage(Images.flamethrowerIcon,45);
        
        degrees++;
        // Drawing the rotated image at the required drawing locations
      //  g.drawImage(rotateImage(Images.flamethrowerIcon,degrees), drawLocationX, drawLocationY, null);

        //g.drawImage(Images.flamethrowerIcon, 100, 100, null);
        
        
	}
	private int degrees = 0;
	
	private BufferedImage rotateImage(BufferedImage image,double degrees){
		double rotationRequired = Math.toRadians (degrees);
        double locationX = image.getWidth() / 2;
        double locationY = image.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        
        return op.filter(image, null);
	}

	@Override
	public void init() {
		if(!isDemoMode){
			player = new Player(GamePanel.WIDTH/2,GamePanel.HEIGHT/2);
		}else{
			player = new PlayerDemo(GamePanel.WIDTH/2,GamePanel.HEIGHT/2);
		}
		enemies = new ArrayList<>();
		bullets = new ArrayList<>();
		powerups = new ArrayList<>();
		explosions = new ArrayList<>();
		inventory = new Inventory();
		bombs = new ArrayList<>();
		texts = new ArrayList<>();
		addEnemies();
		
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursor = toolkit.getImage("res/Scope.png");
		Point cursorHotSpot = new Point(16,16);
		Cursor customCursor = toolkit.createCustomCursor(cursor,cursorHotSpot,"Cursor");
		Frame.frame.setCursor(customCursor);
	}
	
	private void addEnemies(){
		int numEnemies=5;
		if(waveNumber == 1 || waveNumber == 2){
			numEnemies =10;
		}else if(waveNumber == 3){
			numEnemies = 7;
		}else if(waveNumber == 4){
			numEnemies = 15;
		}else{
			numEnemies = waveNumber *waveNumber;
		}
			for(int i=0; i < numEnemies;i++){
				int x,y;
				if((int)(Math.random()*2) == 1){
					if((int)(Math.random()*2) == 1){
						y = GamePanel.HEIGHT -1;
					}else{
					y = 1;
					}
					x = (int) (Math.random()*(GamePanel.WIDTH-50))+50;
				}else {
					if((int)(Math.random()*2) == 1){
						x = GamePanel.WIDTH -1;
					}else{
					x = 1;
					}
					y = (int) (Math.random()*(GamePanel.HEIGHT-50))+50;
				}
				
				if(waveNumber == 1){
					enemies.add(new BasicEnemy(x,y,StoredInfo.ENEMYNORMALSIZE,1));
					//enemies.add(new WeavingEnemy(x,y,5,1));
				}else if(waveNumber == 2){
					if(i < 3){
						enemies.add(new BasicEnemy(x,y,StoredInfo.ENEMYNORMALSIZE,1));
					}else{
						enemies.add(new FollowPlayerEnemy(x,y,StoredInfo.ENEMYNORMALSIZE,1,player));
					}
				}else if(waveNumber == 3){
					if(i < 5){
						enemies.add(new BasicEnemy(x,y,StoredInfo.ENEMYLARGESIZE,StoredInfo.ENEMYLARGESPEED,StoredInfo.ENEMYLARGEHEALTH,StoredInfo.ENEMYLARGEDAMAGE));
					}else {
						enemies.add(new FollowPlayerEnemy(x,y,StoredInfo.ENEMYNORMALSIZE,1,player));
					}
				}else if(waveNumber == 4){
					if(i < 5){
						enemies.add(new BasicEnemy(x,y,StoredInfo.ENEMYLARGESIZE,StoredInfo.ENEMYLARGESPEED,StoredInfo.ENEMYLARGEHEALTH,StoredInfo.ENEMYLARGEDAMAGE));
					}else if(i < 10){
						enemies.add(new BasicEnemy(x,y,StoredInfo.ENEMYNORMALSIZE,StoredInfo.ENEMYNORMALSPEED,StoredInfo.ENEMYNORMALHEALTH,StoredInfo.ENEMYNORMALDAMAGE));
					}
					else {
						enemies.add(new FollowPlayerEnemy(x,y,StoredInfo.ENEMYNORMALSIZE,1,player));
					}
				}
				else {
					int size = (int)(Math.random()*10 + 1);
					if(size < 5){
						enemies.add(new BasicEnemy(x,y,StoredInfo.ENEMYNORNAL));
					}else if(size < 8){
						enemies.add(new BasicEnemy(x,y,StoredInfo.ENEMYLARGE));
					}else if(size == 9){
						enemies.add(new BasicEnemy(x,y,StoredInfo.ENEMYSMALL));
					}else {
						enemies.add(new FollowPlayerEnemy(x,y,StoredInfo.ENEMYNORMALSIZE,1,player));
					}
				}
					//enemies.add(new WeavingEnemy(x,y,5,1));
				
			}
		texts.add(new Text("-WAVE " + StoredInfo.waveNumber+"-",GamePanel.WIDTH/2,GamePanel.HEIGHT/2,3000));
		waveStart = true;
		waveStartTime = System.nanoTime();
	}
	
	public void log(String s){
		FileHandler.writeFile(FileHandler.debugInfo, true,s);
		System.out.println(s);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == e.VK_SPACE){
			//player.fire();
			player.setFiring(true);
		}
		if(key == e.VK_W){
			player.setUp(true);
		}else if(key == e.VK_D){
			player.setRight(true);
		}else if(key == e.VK_A){
			player.setLeft(true);
		}else if(key == e.VK_S){
			player.setDown(true);
		}
		
		if(key == e.VK_P){
			gsm.setPauseState(new PauseState(gsm));
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == e.VK_SPACE){
			player.setFiring(false);
		}
		if(key == e.VK_W){
			player.setUp(false);
		}else if(key == e.VK_D){
			player.setRight(false);
		}else if(key == e.VK_A){
			player.setLeft(false);
		}else if(key == e.VK_S){
			player.setDown(false);
		}
		
		if(key == e.VK_Q){
			player.switchWeapons();
			inventory.setSelection(player.getCurrentWeapon());
		}
		if(key == KeyEvent.VK_B){
			if(StoredInfo.numBombs > 0){
			bombs.add(new Grenade(player.getX(),player.getY(),player.getAngle()));
			StoredInfo.numBombs--;
			}
		}
		
		if(key == KeyEvent.VK_V){
			/*if(bombs.size() > 0){
				bombs.get(0).setDetonate(true);
			}*/
			for(int i=0;i<bombs.size();i++){
				if(!(bombs.get(i) instanceof Grenade)){
					bombs.get(0).setDetonate(true);
				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		player.setMousePos(mx,my);
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int wheelMovement = e.getWheelRotation();
		//log(wheelMovement+"");
		inventory.setWheelMovement(wheelMovement);
	}
	
	public Player getPlayer(){
		return player;
	}
	public ArrayList getEnemies(){
		return enemies;
	}
	public ArrayList getBullets(){
		return bullets;
	}
	
	public void printAllInfo(){
		player.printAllInfo();
		for(int i=0;i<enemies.size();i++){
			enemies.get(i).printAllInfo();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}

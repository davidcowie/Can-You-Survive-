import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class LoadScreenState extends GameState {

	//i want a total time to load the time like a delay 
	// and also need an elapsed timer
	private long loadTime = 5000, loadElapsed, loadStart;
	private int loadBarLength = 400, loadBarHeight = 30;
	private int fillBarLength;
	private int startX,startY;
	private int alpha; // so it fades out
	
	public LoadScreenState(GameStateManager gsm) {
		this.gsm = gsm;
		loadStart = System.nanoTime();
		startX = GamePanel.WIDTH/2 - loadBarLength/2;
		startY = GamePanel.HEIGHT/2 - loadBarHeight/2;
		alpha =255;
	}
	
	@Override
	public void update() {
		loadElapsed = (System.nanoTime() - loadStart )/ 1000000;
		
	
		/*if(fillBarLength > loadBarLength){
			fillBarLength = loadBarLength;
		}else {
			fillBarLength = (int) ((int) (loadElapsed/(loadBarLength))*(loadBarLength/(loadTime/(loadBarLength))));
		}*/
		if(loadElapsed <=loadTime){
			fillBarLength = (int) ((int) (loadElapsed/(loadBarLength))*(loadBarLength/(loadTime/(loadBarLength))));
			
		}
		//fillBarLength = (int) (loadBarLength/(loadElapsed) )*10+1;
		log("" + fillBarLength);
		
		
	}

	@Override
	public void draw(Graphics2D g) {
		
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		g.setColor(Color.blue);
		g.drawRect(startX,startY,loadBarLength, loadBarHeight);
		
		g.setColor(new Color(0,0,255,alpha));
		g.fillRect(startX, startY,fillBarLength, loadBarHeight);
		
		if(loadElapsed >= loadTime){
			alpha -= 10;
			//set the desired state
			if(alpha <=20){
				//gsm.setState(GameStateManager.HIGHSCORESTATE);
				//gsm.setNextWaveState(GameStateManager.LEVEL1STATE,StoredInfo.waveNumber);
				gsm.setStateNoInit(gsm.SHOPSTATE);
			}
		}
	}

	@Override
	public void init() {
		loadStart = System.nanoTime();
		startX = GamePanel.WIDTH/2 - loadBarLength/2;
		startY = GamePanel.HEIGHT/2 - loadBarHeight/2;
		alpha =255;
		gsm.initState(GameStateManager.SHOPSTATE);
		gsm.startShopState();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}

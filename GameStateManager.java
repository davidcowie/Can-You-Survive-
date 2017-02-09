import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class GameStateManager {

	ArrayList<GameState> gameStates;
	private int currentState = 0;
	public static final int MENUSTATE =0, LEVEL1STATE = 1,PAUSESTATE = 2,SHOPSTATE = 3,HIGHSCORESTATE = 4,INSTRUCTIONSTATE = 5,LOADSTATE = 6;
	
	public GameStateManager(){
		gameStates = new ArrayList<>();
		StoredInfo.storedWeapons.add(new Pistol());
		StoredInfo.allWeaponsOwned.add(new Pistol());
		gameStates.add(new MenuState(this));
		gameStates.add(new Level1State(this,1,false));
		gameStates.add(new PauseState(this));
		gameStates.add(new ShopState(this,MENUSTATE));
		gameStates.add(new HighScoreState(this));
		gameStates.add(new InstructionState(this));
		gameStates.add(new LoadScreenState(this));
		
	
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursor = toolkit.getImage("res/basicBlueCursor.png");
		Point cursorHotSpot = new Point(16,16);
		Cursor customCursor = toolkit.createCustomCursor(cursor,cursorHotSpot,"Cursor");
		Frame.frame.setCursor(customCursor);
	
	}
	
	public void setState(int s) {
		StoredInfo.prevState = currentState;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursor;
		if(s ==SHOPSTATE){
			cursor = toolkit.getImage("res/Scope.png");
		}else {
			cursor = toolkit.getImage("res/basicBlueCursor.png");
		}
		//Image cursor = toolkit.getImage("res/basicBlueCursor.png");
		Point cursorHotSpot = new Point(16,16);
		Cursor customCursor = toolkit.createCustomCursor(cursor,cursorHotSpot,"Cursor");
		Frame.frame.setCursor(customCursor);
		currentState = s;
		gameStates.get(currentState).init();
	}
	public void initState(int s){
		gameStates.get(s).init();
	}
	public void startShopState(){
		gameStates.get(SHOPSTATE).start();
	}
	public void setNextWaveState(int s,int waveNumber){
		StoredInfo.prevState = currentState;
		currentState = s;
		gameStates.set(LEVEL1STATE, new Level1State(this,waveNumber,false));
	}
	public void setShopState(int s){
		StoredInfo.prevState = currentState;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursor;
		if(s ==SHOPSTATE){
			cursor = toolkit.getImage("res/Scope.png");
		}else {
			cursor = toolkit.getImage("res/basicBlueCursor.png");
		}
		//Image cursor = toolkit.getImage("res/basicBlueCursor.png");
		Point cursorHotSpot = new Point(16,16);
		Cursor customCursor = toolkit.createCustomCursor(cursor,cursorHotSpot,"Cursor");
		Frame.frame.setCursor(customCursor);
		currentState = s;
		gameStates.get(currentState).start();
	}
	public void setStateNoInit(int s){
		StoredInfo.prevState = currentState;
		currentState = s;
	}
	public void setDemoMode(){
		currentState = LEVEL1STATE;
		gameStates.set(LEVEL1STATE, new Level1State(this,1,true));
	}
	public void setPauseState(PauseState ps){
		currentState = PAUSESTATE;
		gameStates.set(PAUSESTATE, ps);
		gameStates.get(currentState).init();
	}
	
	public void update(){
		gameStates.get(currentState).update();
	}
	
	public void draw(Graphics2D g){
		gameStates.get(currentState).draw(g);
	}
	
	public void keyPressed(KeyEvent e){
		gameStates.get(currentState).keyPressed(e);
	}
	public void keyReleased(KeyEvent e){
		gameStates.get(currentState).keyReleased(e);
	}
	public void mouseMoved(MouseEvent e){
		gameStates.get(currentState).mouseMoved(e);
	}
	public void mouseWheelMoved(MouseWheelEvent e){
		gameStates.get(currentState).mouseWheelMoved(e);
	}
	public void mouseClicked(MouseEvent e){
		gameStates.get(currentState).mouseClicked(e);
	}
	public int getState(){
		return currentState;
	}
}

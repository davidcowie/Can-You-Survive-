import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public abstract class GameState {

	protected GameStateManager gsm;
	
	public abstract void update();
	public abstract void draw(Graphics2D g);
	public abstract void init();
	public abstract void keyPressed(KeyEvent e);
	public abstract void keyReleased(KeyEvent e);
	public abstract void mouseMoved(MouseEvent e);
	public abstract void mouseClicked(MouseEvent e);
	public abstract void mouseWheelMoved(MouseWheelEvent e);
	public void log(String s){
		System.out.println(s);
	}
	public void start() {
		// TODO Auto-generated method stub
		
	}
}

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class HighScoreState extends GameState {

	public HighScoreState(GameStateManager gsm){
		this.gsm = gsm;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.lightGray);
		g.fillRect(0,0,GamePanel.WIDTH,GamePanel.HEIGHT);
		
		g.drawImage(Images.instructionBackground, 0, 0, GamePanel.WIDTH,GamePanel.HEIGHT,null);
		
		g.setColor(Color.white);
		String high = "High Scores:";
		g.drawString(high, GamePanel.WIDTH/2-80, 50);
		
		for(int i=0;i<StoredInfo.highScores.size();i++){
			g.setColor(Color.WHITE);
			//System.out.println(StoredInfo.highScores.get(i));
			g.drawString("Score " + (i+1) + " : " + StoredInfo.highScores.get(i), GamePanel.WIDTH/2-80, 100 + 50*i);
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_ENTER){
			gsm.setState(GameStateManager.MENUSTATE);
		}
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
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		gsm.setState(GameStateManager.MENUSTATE);
	}

	
}

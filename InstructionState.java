import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class InstructionState extends GameState{

	
	private String[] controls = {"'wasd' to move","'q' to switch weapons","'b' to throw grenades","'space' to shoot"};
	
	public InstructionState(GameStateManager gsm){
		this.gsm = gsm;
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		g.drawImage(Images.instructionBackground, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		
		g.setColor(Color.white);
		g.setFont(new Font("Chalkboard",Font.BOLD,40));
		int length1 = (int) g.getFontMetrics().getStringBounds("Controls", g).getWidth();
		int height1 = (int) g.getFontMetrics().getStringBounds("Controls", g).getHeight();
		g.drawString("Controls", GamePanel.WIDTH/2 - length1/2, 50);
		
		for(int i=0;i<controls.length;i++){
			g.setFont(new Font("Arial",Font.PLAIN,24));
			int length = (int) g.getFontMetrics().getStringBounds(controls[i], g).getWidth();
			int height = (int) g.getFontMetrics().getStringBounds(controls[i], g).getHeight();
			g.drawString(controls[i], GamePanel.WIDTH/2 - length/2, GamePanel.HEIGHT/4 + 30*i);
		}
		
		
		g.drawString("Press ENTER to return to the main menu", GamePanel.WIDTH/2 - 200, GamePanel.HEIGHT - 100);
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
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		
	}

}

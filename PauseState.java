import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

public class PauseState extends GameState{

	
	private int selection;
	private String[] options = {"RESUME","MAIN MENU","QUIT"};
	private ArrayList<Text> texts = new ArrayList<>();
	
	public PauseState(GameStateManager gsm){
		this.gsm = gsm;
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g) {

		/*g.setColor(Color.red);
		g.drawString("PAUSED",, 50);*/
		texts.get(0).draw(g);
		
		for(int i=0;i<options.length;i++){
			if(i == selection){
				g.setColor(Color.red);
				//texts.get(i).setColor(Color.red);
			}else{
				g.setColor(Color.CYAN);
				//texts.get(i).setColor(Color.CYAN);
			}
			g.drawString(options[i], GamePanel.WIDTH/2 - 50, GamePanel.HEIGHT/2 + 30*i);
			
			//texts.get(i).draw(g);
		}
		
		
	}

	@Override
	public void init() {
		selection = 0;
		texts.add(new Text("PAUSED",GamePanel.WIDTH/2,50,new Font("Arial",Font.PLAIN,24)));
		texts.get(0).setColor(Color.RED);
		for(int i=0; i<options.length;i++){
			texts.add(new Text(options[i], GamePanel.WIDTH/2, GamePanel.HEIGHT/2 + 30*i,new Font("Arial",Font.PLAIN,18)));
		}
	}
	
	private void select(){
		if(selection == 0){
			gsm.setStateNoInit(gsm.LEVEL1STATE);
		}else if(selection == 1){
			gsm.setState(GameStateManager.MENUSTATE);
		}else if(selection == 2){
			System.exit(0);
		}
	}
	private void increaseSelection(){
		selection++;
		if(selection >= options.length){
			selection =0;
		}
		System.out.println(options[selection]);
	}
	private void decreaseSelection(){
		selection--;
		if(selection <0){
			selection = options.length-1;
		}

		System.out.println(options[selection]);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == e.VK_ENTER){
			select();
		}
		if(key == KeyEvent.VK_W){
			decreaseSelection();
		}else if(key == KeyEvent.VK_S){
			increaseSelection();
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
		
	}

	
}

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuState extends GameState{
	private boolean DEBUG = false;

	private int selection = 0;
	private final int PLAY = 0, SHOP = 1, QUIT = 5,DEMO = 2,HIGHSCORES = 3,CONTROLS = 4;
	private String[] options = {"Play!","Shop","Demo Mode","High Scores","Controls","Quit"};
	private Map<String,Rectangle> boundingBoxes = new HashMap<>();
	private List<Rectangle> rectangles = new ArrayList<>();
	private boolean firstRun;
	
	public MenuState(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	private void select(){
		if(selection == PLAY){
			gsm.setNextWaveState(GameStateManager.LEVEL1STATE,1);
			//gsm.setState(GameStateManager.LOADSTATE);
		}else if(selection == SHOP){
			gsm.setState(GameStateManager.SHOPSTATE);
	}else if(selection == QUIT){
			System.exit(0);
		}else if(selection == DEMO){
			gsm.setDemoMode();
		}else if(selection == HIGHSCORES){
			gsm.setState(GameStateManager.HIGHSCORESTATE);
		}else if(selection == CONTROLS){
			gsm.setState(GameStateManager.INSTRUCTIONSTATE);
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.drawImage(Images.mainMenuBackground,0,0,GamePanel.WIDTH,GamePanel.HEIGHT,null);
		g.drawImage(Images.titleImage, GamePanel.WIDTH/2 - Images.titleImage.getWidth()/2, 150- Images.titleImage.getWidth()/2, null);
		
		if(firstRun){
			g.setFont(new Font("Arial",Font.PLAIN,24));
			for(int i =0;i<options.length;i++){
			int length = (int) g.getFontMetrics().getStringBounds(options[i], g).getWidth();
			int height = (int) g.getFontMetrics().getStringBounds(options[i], g).getHeight();
			rectangles.add(new Rectangle(GamePanel.WIDTH/2 - length/2 , GamePanel.HEIGHT/2 + 30*(i) - 15, length, height));
			}
			firstRun = false;
		}
		
		for(int i=0;i<options.length;i++){
			if(i == selection){
				g.setColor(Color.red);
			}else{
				g.setColor(Color.CYAN);
			}
			g.setFont(new Font("Arial",Font.PLAIN,24));
			int length = (int) g.getFontMetrics().getStringBounds(options[i], g).getWidth();
			int height = (int) g.getFontMetrics().getStringBounds(options[i], g).getHeight();
			g.drawString(options[i], GamePanel.WIDTH/2 - length/2, GamePanel.HEIGHT/2 + 30*i);
			g.setColor(Color.red);
			if(DEBUG){
			g.drawRect(rectangles.get(i).x ,rectangles.get(i).y, rectangles.get(i).width, rectangles.get(i).height);
			}
		}
		
		
	}

	@Override
	public void init() {
		selection = 0;
		for(int i=0;i<options.length;i++){
			//boundingBoxes.put(options[i],new Rectangle());
		}
		firstRun = true;
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
	
	private void checkBoxCollision(boolean select){
		for(int i=0;i<rectangles.size();i++){
			if(rectangles.get(i).contains(new Point(mouseX,mouseY))){
				selection = i;
				if(select){
					select();
				}
				break;
			}
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_W){
			decreaseSelection();
		}else if(key == KeyEvent.VK_S){
			increaseSelection();
		}
		
		if(key == KeyEvent.VK_ENTER){
			select();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
		checkBoxCollision(false);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		
	}
	private int mouseX,mouseY;
	@Override
	public void mouseClicked(MouseEvent e) {
		checkBoxCollision(true);
	}

}

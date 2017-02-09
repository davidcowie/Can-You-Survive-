import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable,KeyListener,MouseListener,MouseMotionListener,MouseWheelListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 700, HEIGHT = 700;
	
	private Thread thread;
	private Graphics2D g;
	private BufferedImage image;
	private boolean running = false;
	
	private GameStateManager gsm = new GameStateManager();
	
	public GamePanel(){
		super();
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setFocusable(true);
		requestFocus();
		//FileHandler.writeFile(false, 5,4,3,1,2);
		FileHandler.readSave();
		FileHandler.writeFile(FileHandler.debugInfo, false,"");
		Images.loadImages();
	}
	
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
		addMouseWheelListener(this);
	}

	public void gameUpdate(){
		gsm.update();
	}
	public void gameDraw(){
		gsm.draw(g);
	}
	
	public void drawGameToScreen(){
		Graphics g2 = this.getGraphics();
        g2.drawImage(image,0,0,null);
        g2.dispose();
	}
	
	public void init(){
		running = true;
		 image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	     g = (Graphics2D) image.getGraphics(); // g is the paint brush to the image
	     
	     g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	     g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}
	
	@Override
	public void run() {
		init();
		
		while(running){
			gameUpdate();
			gameDraw();
			drawGameToScreen();
			
			try{
				Thread.sleep(15);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		gsm.mouseClicked(arg0);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		gsm.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		gsm.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		gsm.mouseMoved(e);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		gsm.mouseWheelMoved(e);
		
	}
}

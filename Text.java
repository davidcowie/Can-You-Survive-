import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Text {

	private int x,y,length;
	private int alpha=1;
	private String text;
	private long timeOnScreen,timeStart,timeElapsed;
	private long fadeTimeDelay = 1000,fadeTimeStart;
	private boolean fadeIn,fadeOut;
	private Color textColor;
	private Font font;
	public Text(String text,double x,double y,long timeOnScreen){
		this.text = text;
		this.x = (int) x;
		this.y = (int) y;
		this.timeOnScreen = timeOnScreen;
		//timeStart = System.nanoTime();
		
		textColor = Color.white;
		font = new Font("Arial",Font.PLAIN,24);
		fadeTimeStart = System.nanoTime();
		fadeIn = true;
	}
	public Text(String text,double x,double y){
		this.text = text;
		this.x = (int) x;
		this.y = (int) y;
		font = new Font("Arial",Font.PLAIN,24);
	}
	public Text(String text,double x,double y,Font font){
		this.text = text;
		this.x = (int) x;
		this.y = (int) y;
		this.font = font;
	}
	
	
	public boolean update(){
		
		if(fadeIn){
			long elapsed = (System.nanoTime()-fadeTimeStart)/1000000;
			if(elapsed > fadeTimeDelay){
				fadeIn = false;
				timeStart = System.nanoTime();
			}
			alpha++;
			if(alpha >255){
				alpha = 255;
			}
		}
		if(!fadeIn){
			timeElapsed = (System.nanoTime() - timeStart)/1000000;
			if(timeElapsed > timeOnScreen){
				fadeOut = true;
			}
		}
		if(fadeOut){
			alpha--;
			if(alpha < 0){
				alpha = 0;
			}
		}
		
		
		if(alpha == 0){
			return true;
		}
		
		return false;
	}
	
	public void draw(Graphics2D g){
		g.setFont(font);
		length = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
		if(timeOnScreen != 0){
		g.setColor(new Color(0,0,0,alpha));
		}else{
			g.setColor(textColor);
		}
		g.drawString(text, x - length/2, y);
	}
	public void setColor(Color c){
		textColor = c;
	}
}

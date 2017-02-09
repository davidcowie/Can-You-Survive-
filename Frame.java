/*import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;*/

import javax.swing.JFrame;

public class Frame {

	public static final JFrame frame = new JFrame("Can You Survive?");
	public static void main(String[] args) {
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new GamePanel());
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		/*Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursor = toolkit.getImage("res/Scope.png");
		Point cursorHotSpot = new Point(16,16);
		Cursor customCursor = toolkit.createCustomCursor(cursor,cursorHotSpot,"Cursor");
		frame.setCursor(customCursor);*/
	}

}

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public abstract class Entity {

	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	protected double radius;
	protected double speed;
	protected double width;
	protected double height;
	
	public Entity(double x,double y){
		this.x = x;
		this.y = y;
	}
	public Entity(Entity e){
		this(e.getX(),e.getY());
	}
	
	public abstract void update();
	public void draw(Graphics2D g,Color color){
		g.setColor(color);
		g.fillRect((int)(x-radius), (int)(y-radius), (int)radius*2,(int) radius*2);
		
		
		/*else{
		g.fillRect((int)(x-width/2),(int)(y-height/2),(int) width,(int)height);
		}*/
	}
	
	protected BufferedImage rotateImage(BufferedImage image1,double degrees){
		BufferedImage image = image1;
		double rotationRequired = Math.toRadians (degrees);
        double locationX = image.getWidth() / 2;
        double locationY = image.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        
       
        
        return op.filter(image, null);
	}
	
	public Rectangle getBoundingBox(){
		return new Rectangle((int)x,(int)y,(int)width,(int)height);
	}
	public abstract boolean updateB();
	
	
	public double getX(){
		return x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public double getDy() {
		return dy;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double r) {
		this.radius = r;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	public boolean equals(Entity that){
		if(this.x == that.getX() && this.y == that.getY() && this.radius == that.getRadius()){
			return true;
		}else {
			return false;
		}
	}
	protected void log(String s){
		//FileHandler.writeFile(FileHandler.debugInfo, true,s);
		System.out.println(s);
	}
	
	public void printAllInfo(){
		String indent = "------> ";
		log(getClass() + " Information:");
		log(indent + "X: " + getX() + " Y: " + getY());
		log(indent + " radius: " + getRadius());
		log(indent + " Speed : " + getSpeed());
		log(indent + " Dx: " + getDx() +" Dy: " + getDy());
	}
	
}

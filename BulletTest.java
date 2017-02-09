import static org.junit.Assert.*;

import org.junit.Test;

public class BulletTest {

	@Test
	public void testConstructor(){
		Bullet b = new Bullet(45,10,30,4);
		assertEquals("--BULLET-- x: " + "10.0" + " y: " + "30.0",b.toString());
		assertEquals(10,(int)b.getX());
		assertEquals(1,(int)b.getDx());
	}
	
	@Test
	public void testMove(){
		Bullet b = new Bullet(45,10,30,4);
		b.update();
		assertEquals(11,(int)b.getX() );
		assertEquals(31,(int)b.getY());
	}
	
	@Test
	public void testEquals(){
		Bullet b = new Bullet(45,10,30,4);
		Bullet b2 = new Bullet(b);
		assertTrue(b.equals(b2));
		assertTrue(b2.equals(b));
	}
	@Test
	public void testNotEquals(){
		Bullet b = new Bullet(45,10,30,4);
		Bullet b2 = new Bullet(30,20,100,4);
		assertFalse(b.equals(b2));
		assertFalse(b2.equals(b));
	}

}

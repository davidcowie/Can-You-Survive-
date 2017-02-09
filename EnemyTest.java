import static org.junit.Assert.*;

import org.junit.Test;

public class EnemyTest {

	@Test
	public void testConstructor() {
		BasicEnemy e = new BasicEnemy(30,50,4,1);
		assertEquals("--ENEMY-- x: " + "30.0" + " y: " + "50.0" + " health: " + "1.0" + " id: 1",e.toString());
	}
	@Test
	public void testUpdate(){
		BasicEnemy e = new BasicEnemy(30,50,4,1);
		e.update();
		assertTrue(e.getX() != 30);
		assertTrue(e.getY() != 50);
	}
	@Test
	public void testHit(){
		BasicEnemy e = new BasicEnemy(30,50,4,1);
		assertEquals(1,(int)e.getHealth());
		e.hit(1);
		assertEquals(0, (int)e.getHealth());
		assertTrue(e.isDead());
	}
	
	@Test
	public void testEquals(){
		BasicEnemy e = new BasicEnemy(30,50,4,1);
		BasicEnemy e2 = new BasicEnemy(e);
		assertTrue(e.equals(e2));
		assertTrue(e2.equals(e));
	}
	@Test
	public void testNotEqual(){
		BasicEnemy e = new BasicEnemy(30,50,4,1);
		BasicEnemy e2 = new BasicEnemy(20,50,4,1);
		assertFalse(e.equals(e2));
		assertFalse(e2.equals(e));
	}

}

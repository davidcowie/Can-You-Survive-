
public class PlayerTest {

	public static void main(String[] args) {

		Player player = new Player(100,200);
		
		System.out.println(player);
		
		int counter =0;
		while(counter < 50){
			if(counter <10){
				player.setLeft(true);
				
			}else if(counter < 20){
				player.setRight(true);
			}else if(counter < 30){
				player.setUp(true);
			}else if(counter < 40){
				player.setDown(true);
			}
			if(counter %10 == 0){
				player.hit(1);
			}
			
			player.update();
			System.out.println(player);
			counter++;
			player.setRight(false);
			player.setLeft(false);
			player.setUp(false);
			player.setDown(false);
		}
		
		Player player2 = new Player(player);
		System.out.println(player2);
		System.out.println(player2.equals(player)); // should be true
		
		player.hit(5);
		System.out.println("health should be 0: " + player.getHealth());
		System.out.println("and should be dead " + " is player dead? " + player.isDead());
	}

}

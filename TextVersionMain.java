import java.util.Scanner;

import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;

public class TextVersionMain {
	
	private static Level1State levelState;
	private static boolean showBullet = true;
	private static boolean showPlayer = true;
	private static boolean showEnemies = true;
	private static boolean displayEachTimeStep;
	private static int numTimeSteps = 1;
	private static boolean error = false;
	
	/*
	 * the text based version how-to
	 * It starts by displaying the initial values of the player and the initial enemies
	 * the user is then prompted to enter a command. The command must be entered exactly as
	 * shown or an exception will be thrown.
	 * Then based on the command input it will then request further input if required
	 * then it will print out all of the information about the entities the user selected to be 
	 * displayed.
	 * 
	 * the user can:
	 * shoot-which fires bullets
	 * move- then you can pick a direction to move
	 * set- allows the user to access some setters to adjust the player in a desired way
	 * number of time steps- change the number of time steps to advance.
	 * hit- either have the player get hit or have the enemies get hit
	 * quit - quits the program
	 * 
	 * It allows the user to select what information they want displayed
	 * and enter the number of time steps to advance each time.
	 * 
	 * while the game itself updates it will print out some information on its own such as:
	 * collisions, and other relevant information
	 */

	public static void main(String[] args) {
		FileHandler.readSave();
		FileHandler.clearInfo(FileHandler.debugInfo);
		Images.loadImages();
		levelState = new Level1State(new GameStateManager(),1,false);
		Scanner scanner = new Scanner(System.in);
		
		log("InitialValue");
		printAllInfo();
		String userInput = "";
		while(!userInput.equals("quit")){
			error = false;
			log("Enter Command(quit,shoot,move,set,number of time steps,hit)"); // set firing 
			try{
				userInput = validateCommand(scanner.nextLine().toLowerCase());
				
				if(userInput.equals("quit")){
					break;
				}
				if(userInput.equals("move")){
					log("which direction do you want to move(right,left,up,down)");
					String direction = scanner.nextLine().toLowerCase();
					setDirection(direction);
					
				}else if(userInput.equals("shoot")){
					log("enter the angle you want to shoot ");
					double angle = scanner.nextDouble();
					levelState.player.setAngle(angle);
					levelState.player.setFiring(true);
					levelState.player.shoot();
					log("if you want to have the bullet info displayed you have to change that");
				}else if(userInput.equals("number of time steps")){
					log("enter the number of time steps you want");
					
					numTimeSteps=  validateTimeSteps(scanner.nextInt());
					
				}else if(userInput.equals("set")){
					log("What do you want to set?(firing,dead,health,cash,currentWeapon,lives,speed)");
					String input = scanner.nextLine();
					setSomething(input,scanner);
					
				}else if(userInput.equals("hit")){
					log("What entity do you want to hit?(enemy or player");
					String input = scanner.nextLine();
					hitEntity(input,scanner);
				}
				
				log("do you want to change the information displayed?(y/n)"); //only actually care if the input is 'y' so I dont bother throwing an exception
				String put = scanner.nextLine();
				if(put.equals("y")){
					showBullet = false;
					showPlayer = false;
					showEnemies = false;
					log("what information do you want to be displayed for the current number of timesteps which is: " + numTimeSteps);
					log("bullets(b),player(p),enemies(e),without spaces");
					changeInfoDisplayed(scanner.nextLine());
					
					
					log("do you want to see the info after each Timestep or at the end?(enter 'y' for each step or 'n' for at the end(or anything that isnt just a 'y'))");
					if(scanner.nextLine().equals("y")){
						displayEachTimeStep = true;
					}else {
						displayEachTimeStep = false;
					}
				}
			}catch(InvalidUserInputException e){
				error = true;
				log("" + e.getMessage());
			}
			
			if(!error){
				for(int i=0;i<numTimeSteps;i++){
					levelState.update();
					if(displayEachTimeStep){
						printAllInfo();
					}
				}
				if(!displayEachTimeStep){
					printAllInfo();
				}
			}
		}
	}
	
	private static void hitEntity(String s,Scanner scanner) throws InvalidUserInputException {
		if(!s.equals("player") && !s.equals("enemy")){
			throw new InvalidUserInputException("INVALID INPUT: have to hit a player of an enemy. Not a " + s);
		}
		
		if(s.equals("player")){
			log("how much damage do you want to deal to the player?(int)");
			int dam;
			if(scanner.hasNextInt()){
				 dam = scanner.nextInt();
			}else{
				throw new InvalidUserInputException("INVALID INPUT: have to enter an integer");	
			}
			levelState.player.hit(dam);
		}else if(s.equals("enemy")){
			log("which enemy do you want to hit? There are " + levelState.enemies.size() + " still alive(pick one: 1- " + levelState.enemies.size() + ")");
			int index;
			if(scanner.hasNextInt()){
				 index = scanner.nextInt();
				if(index < 1 || index > levelState.enemies.size()){
					throw new InvalidUserInputException("INVALID INPUT: need to enter a valid enemy. Not " + index);
				}
			}else{
				throw new InvalidUserInputException("INVALID INPUT: Type Mismatch- need to enter an int");
			}
			levelState.enemies.get(index).hit(levelState.player.getCurrentWeapon().getPower());
		}
	}
	private static void changeInfoDisplayed(String options) throws InvalidUserInputException{
		if(options.indexOf("b") == -1 && options.indexOf("p") == -1 && options.indexOf("e") == -1){
			throw new InvalidUserInputException("INVALID INPUT: CHANGE INFO DISPLAYED: needs a 'b' or a 'p' or a 'e'");
		}
		if(options.indexOf("b") != -1){
			showBullet = true;
		}
		if(options.indexOf("p") != -1){
			showPlayer = true;
		}
		if(options.indexOf("e") != -1){
			showEnemies = true;
		}
	}
	private static int validateTimeSteps(int time) throws InvalidUserInputException{
		if(time <=0){
			throw new InvalidUserInputException("INVALID INPUT: Number of times steps has to be greater than 0 ");
			
		}else {
			return time;
		}
	}
	private static void setSomething(String input,Scanner scanner) throws InvalidUserInputException{
		
		
		if(input.equals("firing") || input.equals("dead")){
			log("set to true?(y/n)");
			boolean b;
			if(scanner.next().equals("y")){
				b = true;
			}else {
				b = false;
			}
			if(input.equals("firing")){
				levelState.player.setFiring(b);
			}else if(input.equals("dead")){
				levelState.player.setDead(b);
			}
		}else if(input.equals("currentWeapon")){
			log("What weapon do you want to equip(smg,pistol,sniper,shotgun");
			String option = scanner.nextLine().toLowerCase();
			switch(option){
			case "smg":
				levelState.player.addWeaponToArsenal(new SMG());
				levelState.player.setWeapon(new SMG());
				break;
			case "pistol":
				levelState.player.addWeaponToArsenal(new Pistol());
				levelState.player.setWeapon(new Pistol());
				break;
			case "sniper":
				levelState.player.addWeaponToArsenal(new SniperRifle());
				levelState.player.setWeapon(new SniperRifle());
				break;
			case "shotgun":
				levelState.player.addWeaponToArsenal(new Shotgun());
				levelState.player.setWeapon(new Shotgun());
				break;
			 default:
				levelState.player.addWeaponToArsenal(new Pistol());
				levelState.player.setWeapon(new Pistol());
			}
		}else if(input.equals("health") || input.equals("cash") || input.equals("lives") || input.equals("speed") ) { 
			log("What do you want to set this to?");
			int amount = scanner.nextInt();
			if(input.equals("health")){
			levelState.player.setHealth(amount);
			}else if(input.equals("cash")){
			//	levelState.player.setCash(amount);
			}else if(input.equals("lives")){
				
			}else if(input.equals("speed")){
				
			}
		}else {
			throw new InvalidUserInputException("INVALID INPUT: You can't set that value");
		}
	}
	private static String validateCommand(String userInput) throws InvalidUserInputException {
		if(userInput.equals("quit")  ||userInput.equals("move") || userInput.equals("shoot") || userInput.equals("number of time steps") || userInput.equals("set") ){
			return userInput;
		}else {
			throw new InvalidUserInputException("INVALID COMMAND");
		}
	}
	private static void setDirection(String s) throws InvalidUserInputException{
		if(!s.equals("up") &&!s.equals("down") && !s.equals("left") && !s.equals("right")){
			throw new InvalidUserInputException("INVALID INPUT: DIRECTION: " + s);
		}
		
		switch(s){
		case "left":
			levelState.player.setLeft(true);
			levelState.player.setRight(false);
			break;
		case "right":
			levelState.player.setRight(true);
			levelState.player.setLeft(false);
			break;
		case "up":
			levelState.player.setUp(true);
			levelState.player.setDown(false);
			break;
		case "down":
			levelState.player.setDown(true);
			levelState.player.setUp(false);
		}
				
	}
	
	private static void printAllInfo(){
		
		if(showPlayer){
			levelState.player.printAllInfo();
		}
		if(showEnemies){
			for(int i=0;i<levelState.enemies.size();i++){
				levelState.enemies.get(i).printAllInfo();
			}
		}if(showBullet){
			for(int i=0;i<levelState.bullets.size();i++){
				levelState.bullets.get(i).printAllInfo();
			}
		}
		//levelState.printAllInfo();
		
	}
	
	
	private static void log(String s){
		System.out.println(s);
	}

}

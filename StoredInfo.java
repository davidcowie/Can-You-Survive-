import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class StoredInfo {

	public static int totalCash = 5000;
	public static ArrayList<Weapon> storedWeapons = new ArrayList<>();
	public static ArrayList<Weapon> allWeaponsOwned = new ArrayList<>();
	public static int lives= 3;
	public final static int maxLives = 3;
	
	public static ArrayList<Integer> highScores = new ArrayList<>();
	public static int score = 15;
	public final static int maxBombs = 5;
	public static int numBombs = maxBombs;
	
	public static int prevState;
	public static int waveNumber = 1;
	
	public static final int inventorySquareSize = 20;
	public static final int barracksSquareSize = 25;
	public static final int invBarNumber = 3;
	public static final int barracksTileHeight = 3;
	
	
	public static final int ENEMYNORMALSIZE = 15;
	public static final int ENEMYLARGESIZE = 25;
	public static final int ENEMYSMALLSIZE = 10;
	
	public static final int ENEMYNORMALHEALTH = 1; // maybe just turn this into an array and pass that into the enemy constructor
	public static final int ENEMYNORMALSPEED = 3;
	public static final int ENEMYNORMALDAMAGE = 2;
	public static final int[] ENEMYNORNAL = {ENEMYNORMALSIZE,ENEMYNORMALHEALTH,ENEMYNORMALSPEED,ENEMYNORMALDAMAGE};
	
	public static final int ENEMYLARGEHEALTH = 5;
	public static final int ENEMYLARGESPEED = 2;
	public static final int ENEMYLARGEDAMAGE = 3;
	public static final int[] ENEMYLARGE = { ENEMYLARGESIZE,ENEMYLARGEHEALTH,ENEMYLARGESPEED,ENEMYLARGEDAMAGE};
	
	public static final int ENEMYSMALLHEALTH = 1;
	public static final int ENEMYSMALLSPEED = 4;
	public static final int ENEMYSMALLDAMAGE = 1;
	public static final int[] ENEMYSMALL = {ENEMYSMALLSIZE,ENEMYSMALLHEALTH,ENEMYSMALLSPEED,ENEMYSMALLDAMAGE};
	
	public static final Weapon[] allWeapons = {new Pistol(),new SMG(),new SniperRifle(),new Shotgun(),new Revolver(),new Minigun()};
	public static final BufferedImage[] allWeaponImages = {Images.pistolIcon,Images.smgIcon,Images.sniperIcon,Images.shotgunIcon,Images.revolverIcon,Images.minigunIcon};
	public static final int emptyID = 0;
	public static final int PISTOLID = 1;
	public static final int SMGID = 2;
	public static final int SNIPERID = 3;
	public static final int SHOTGUNID = 4;
	public static final int REVOLVERID = 5;
	public static final int MINIGUNID = 6;
	
	public static final Weapon PISTOL = new Pistol();
	public static final Pistol pistol = new Pistol();
	public static final Weapon SMG = new SMG();
	
}

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Images {
	// http://en.wikigta.org/wiki/Weapons_(GTA_Chinatown_Wars) 
	
	public static final int shopTileSize = 25;
	

	public static BufferedImage background;
	public static BufferedImage mainMenuBackground;
	public static BufferedImage titleImage;
	public static BufferedImage shopBackground;
	public static BufferedImage instructionBackground;
	public static BufferedImage[] pistolShopTileSet = new BufferedImage[2];
	public static BufferedImage[] smgShopTileSet = new BufferedImage[2];
	public static BufferedImage[] sniperShopTileSet = new BufferedImage[2];
	public static BufferedImage[] shotgunShopTileSet = new BufferedImage[2];
	public static BufferedImage revolverIcon;
	public static BufferedImage shotgunIcon;
	public static BufferedImage pistolIcon;
	public static BufferedImage sniperIcon;
	public static BufferedImage minigunIcon;
	public static BufferedImage flamethrowerIcon;
	public static BufferedImage player;
	public static BufferedImage zombie;
	public static BufferedImage smgIcon;
	public static BufferedImage heartIcon;
	public static BufferedImage coinIcon;
	public static BufferedImage grenade;
	public static BufferedImage fireRing;
	public static BufferedImage bullet;
	public static BufferedImage cashIcon;
	public static BufferedImage[] shopIconTileSet = new BufferedImage[2];
	public static BufferedImage tile_cell;
	
	public static void loadImages(){
		for(int i=0;i<2;i++){
			/*pistolShopTileSet[i] = new ImageIcon("res/pistolShopTile.png").getImage();
			pistolShopTileSet[i] = createImage(new FilteredImageSource(pistolShopTileSet[i].getSource(),new CropImageFilter(i*shopTileSize,0,shopTileSize,shopTileSize)));*/
			try {
				//pistolShopTileSet[i] = ImageIO.read(new File("res/pistolShopTile.png")).getSubimage(i*shopTileSize, 0, shopTileSize, shopTileSize);
				//smgShopTileSet[i] = ImageIO.read(new File("res/smgShopTile.png")).getSubimage(i*shopTileSize, 0, shopTileSize, shopTileSize);
				//sniperShopTileSet[i] = ImageIO.read(new File("res/sniperShopTile.png")).getSubimage(i*shopTileSize, 0, shopTileSize, shopTileSize);
				//shotgunShopTileSet[i] = ImageIO.read(new File("res/shotgunShopTile.png")).getSubimage(i*shopTileSize, 0, shopTileSize, shopTileSize);
				shopIconTileSet[i] = ImageIO.read(new File("res/shopTileSet.png")).getSubimage(i*shopTileSize, 0, shopTileSize, shopTileSize);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		try {
			revolverIcon =  ImageIO.read(new File("res/revolverIcon.png"));
			shotgunIcon = ImageIO.read(new File("res/shotgunIcon.png"));
			pistolIcon = ImageIO.read(new File("res/pistolIcon.png"));
			smgIcon = ImageIO.read(new File("res/smgIcon.png"));
			sniperIcon = ImageIO.read(new File("res/sniperIcon.png"));
			minigunIcon = ImageIO.read(new File("res/minigunIcon.png"));
			flamethrowerIcon = ImageIO.read(new File("res/flamethrowerIcon.png"));
			player = ImageIO.read(new File("res/player.jpg"));
			heartIcon = ImageIO.read(new File("res/heart.png"));
			coinIcon = ImageIO.read(new File("res/coinIcon.png"));
			grenade = ImageIO.read(new File("res/grenadeIcon.png"));
			fireRing = ImageIO.read(new File("res/explosion.png"));
			bullet = ImageIO.read(new File("res/bullet.png"));
			background = ImageIO.read(new File("res/background1.jpg"));
			mainMenuBackground = ImageIO.read(new File("res/mainMenuBackground.png"));
			titleImage = ImageIO.read(new File("res/title1.gif"));
			shopBackground = ImageIO.read(new File("res/shopBackground2.png"));
			cashIcon = ImageIO.read(new File("res/cashIcon.png"));
			tile_cell = ImageIO.read(new File("res/tile_cell.png"));
			zombie = ImageIO.read(new File("res/zombie1.gif"));//.getSubimage(0, 0, 46, 40);
			instructionBackground = ImageIO.read(new File("res/ironGratedBackground.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

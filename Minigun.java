
public class Minigun extends Weapon{

	public Minigun(){
		super();
		fireRate = 1000/25;
		clipSize = 120;
		currentClipSize = clipSize;
		power = 1;
		reloadTime = 5000; // 1 second
		name = "minigun";
		cost = 1000;
		bulletSpeed = 4;
		shopImages = Images.pistolShopTileSet;
		//image = Images.pistolIcon;
	}
}

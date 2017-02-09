
public class Pistol extends Weapon {

	
	public Pistol(){
		super();
		fireRate = 1000/5;
		clipSize = 12;
		currentClipSize = clipSize;
		power = 1;
		reloadTime = 1000; // 1 second
		name = "pistol";
		cost = 10;
		bulletSpeed = 4;
		shopImages = Images.pistolShopTileSet;
		image = Images.pistolIcon;
	}
}

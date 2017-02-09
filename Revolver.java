
public class Revolver extends Weapon{

	public Revolver(){
		super();
		fireRate = 1000/4;
		clipSize = 6;
		currentClipSize = clipSize;
		power = 3;
		reloadTime = 1000; // 1 second
		name = "revolver";
		cost = 10;
		bulletSpeed = 5;
		shopImages = Images.pistolShopTileSet;
		//image = Images.pistolIcon;
	}
}

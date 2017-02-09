
public class SniperRifle extends Weapon{

	public SniperRifle(){
		//super();
		fireRate = 1000/1;
		clipSize = 8;
		currentClipSize = clipSize;
		power = 5;
		reloadTime = 3000; // 1 second
		name = "sniperRifle";
		cost = 1;
		bulletSpeed = 7;
		shopImages = Images.sniperShopTileSet;
		image = Images.sniperIcon;
	}
}

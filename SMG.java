
public class SMG extends Weapon{

	
	public SMG(){
		fireRate = 1000/8;
		power = 1;
		clipSize = 18;
		currentClipSize = clipSize;
		reloadTime = 1000;
		name = "smg";
		cost = 1;
		bulletSpeed = 4;
		shopImages = Images.smgShopTileSet;
		image = Images.smgIcon;
	}
}

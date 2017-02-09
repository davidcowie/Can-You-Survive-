
public class Shotgun extends Weapon{
	
	public Shotgun(){
		fireRate = 1000/2;
		clipSize = 36;
		currentClipSize = clipSize;
		power = 3;
		reloadTime = 2000; // 1 second
		name = "shotgun";
		cost = 1;
		bulletSpeed = 4;
		shopImages = Images.shotgunShopTileSet;
		image = Images.shotgunIcon;
	}

}

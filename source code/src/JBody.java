import java.awt.Image;
import java.awt.Point;

public class JBody {
	int HP;
	int MS; //Move Speed
	int DMG; //ATK DAMAGE
	Point site;
	Image CI;
	
	boolean is_right; //True is seeing right
	
	public JBody(int HP, int MS, int DMG, int x, int y) {
		this.HP = HP;
		this.MS = MS;
		this.DMG = DMG;
		site = new Point(x, y);
	}
}

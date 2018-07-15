import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;


public class JMainObject {
	int HP;
	Point site;
	Image OI;
	
	JMainObject(int HP, Point site){
		Toolkit tk = Toolkit.getDefaultToolkit();
		this.HP = HP;
		this.site = site;
		OI = tk.getImage(".\\Graphic\\Castle.png");
	}
	
	public boolean DMG(){
		HP -= 1;
		if(HP <= 0){
			return true;
		}
		return false;
	}
}

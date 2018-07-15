import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;


public class JMonster extends JBody{
	int rewardMana; // how much mana gave when killed
	ArrayList<Point> nextPoint = new ArrayList<Point>();
	int now;
	
	JMonster(int HP, int MS, int DMG, int x, int y, int stage, int rew){
		super(HP,MS,DMG, x, y);
		rewardMana = rew;
		Toolkit tk = Toolkit.getDefaultToolkit();
		now = 0;
		nextPoint.add(new Point(408,86)); // ±‚¡ÿ
		nextPoint.add(new Point(408,186));
		nextPoint.add(new Point(308,186));
		nextPoint.add(new Point(308,236));
		nextPoint.add(new Point(158,236));
		nextPoint.add(new Point(158,186));
		nextPoint.add(new Point(58,186));
		nextPoint.add(new Point(58,386));
		nextPoint.add(new Point(158,386));
		nextPoint.add(new Point(158,336));
		nextPoint.add(new Point(258,336));
		nextPoint.add(new Point(258,436));
		nextPoint.add(new Point(408,436));
		nextPoint.add(new Point(408,286));
		nextPoint.add(new Point(458,286));
		
		if(stage <= 9){
			if((stage%3) == 1){
				CI = tk.getImage(".\\Graphic\\Level1.png");
			}
			else if((stage%3) == 2){
				CI = tk.getImage(".\\Graphic\\Level2.png");
			}
			else{
				CI = tk.getImage(".\\Graphic\\Level3.png");
			}
		}
		else{
			CI = tk.getImage(".\\Graphic\\Level4.png");
		}
	}
	
	
	
	public void move(){
		if(site.equals(nextPoint.get(now)) && nextPoint.size() > now){
			now ++;
		}
		else{
			if(site.x < nextPoint.get(now).x)
				site.x += MS;
			else if(site.x > nextPoint.get(now).x)
				site.x -= MS;
			if(site.y < nextPoint.get(now).y)
				site.y += MS;
			else if(site.y > nextPoint.get(now).y)
				site.y -= MS;
		}
	}
}

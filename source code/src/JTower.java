import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

public class JTower{
	int ID = 0;
	int DMG = 0;
	int AS = 0; //atk speed
	Point site = new Point();
	JMonster target;
	Image IMG;
	
	//int upgradeCount_AS;
	static int upgradeCount_DMG;
	static int towerCost = 2*(upgradeCount_DMG+1);
	boolean boom; // bullet type
	
	JTower(int type, Point pt){
		towerCost = 2*(upgradeCount_DMG+1);
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		IMG = tk.getImage(".\\Graphic\\tower.png");
		site.x=pt.x;
		site.y=pt.y;
		ID=type;
		
		switch(ID){
		case 1:
			this.AS=1;
			this.DMG=1*(upgradeCount_DMG+1);
			boom = false;
			break;
		case 2:
			this.AS=2;
			this.DMG=2*(upgradeCount_DMG+1);
			boom = false;
			break;
		case 3:
			this.AS=1;
			this.DMG=2*(upgradeCount_DMG+1);
			boom = false;
			break;
		case 4:
			this.AS=2;
			this.DMG=4*(upgradeCount_DMG+1);
			boom = false;
			break;
		}
		target = null;
	}
	JTower(){
		Toolkit tk = Toolkit.getDefaultToolkit();
		IMG = tk.getImage(".\\Graphic\\tower.png");
	}
	
	public void setTarget(JMonster ob){
		
		if(target == null){
			if(ID == 1 || ID == 2){
				if(14400 >= ((ob.site.x - site.x)*(ob.site.x - site.x)) + ((site.y - ob.site.y)*(site.y - ob.site.y))){
					target = ob;
				}
			}
			else{
				if(64000 >= ((ob.site.x - site.x)*(ob.site.x - site.x)) + ((site.y - ob.site.y)*(site.y - ob.site.y))){
					target = ob;
				}
			}
		}
		else{
			if(ID == 1 || ID == 2){
				if(14400 < ((target.site.x - site.x)*(target.site.x - site.x)) + ((site.y - target.site.y)*(site.y - target.site.y))){
					target = null;
				}
			}
			else{
				if(64000 < ((target.site.x - site.x)*(target.site.x - site.x)) + ((site.y - target.site.y)*(site.y - target.site.y))){
					target = null;
				}
			}
		}
	}
	public void TowerDMG_Upgrade() {
		// TODO Auto-generated method stub
		switch(ID){
			case 1:
				this.DMG=3*(upgradeCount_DMG+1);
				boom = false;
				break;
			case 2:
				this.DMG=6*(upgradeCount_DMG+1);
				boom = false;
				break;
			case 3:
				this.DMG=2*(upgradeCount_DMG+1);
				boom = false;
				break;
			case 4:
				this.DMG=4*(upgradeCount_DMG+1);
				boom = false;
				break;
		}

		towerCost = 2*(upgradeCount_DMG+1);
	}
	public static void TDMG_UP() {
		// TODO Auto-generated method stub
		upgradeCount_DMG++;

		towerCost = 2*(upgradeCount_DMG+1);
	}
}

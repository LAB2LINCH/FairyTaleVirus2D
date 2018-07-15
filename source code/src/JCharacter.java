import java.awt.Toolkit;


public class JCharacter extends JBody{
	Toolkit tk = Toolkit.getDefaultToolkit();
	JCharacter(int HP, int MS, int DMG, int x, int y){
		super(HP,MS,DMG, x, y);
		CI = tk.getImage(".\\Graphic\\Character.png");
		mana = 10;
		stage = 1;
		upgradeCount_HP=0;
		upgradeCount_DMG=0;
	}
	
	JCharacter(int HP, int MS, int DMG, int x, int y,int mana, int stage){
		super(HP,MS,DMG, x, y);
		CI = tk.getImage(".\\Graphic\\Character.png");
		this.mana = mana;
		this.stage = stage;
		upgradeCount_HP=0;
		upgradeCount_DMG=0;
	}
	
	private int upgradeCount_HP;
	private int upgradeCount_DMG;
	int mana;
	int stage;
		
	public void upgrade_HP(){
		HP += 5;
	}
	
	public void upgrade_DMG(){
		DMG += 1;
	}
	
	public int return_upgradeCount_HP(){
		return upgradeCount_HP;
	}
	
	public int return_upgradeCount_DMG(){
		return upgradeCount_DMG;
	}
	
	public void move(int x, int y){
		site.x += x;
		site.y += y;
	}
	public void read_up_count(int a, int b){
		upgradeCount_DMG=a;
		upgradeCount_HP=b;
	}
}

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

public class JBullet {
	Point site;
	Point bsite; // where is shot 
	Point lock;
	double angle;
	int BS; //bullet speed
	boolean whos; // true = user, false = monster
	boolean IsBoom; // true = multi atk, false = single target
	int dam;
	Image IMG;
	
	JBullet(Point site, Point bsite, Point lock, int BulletSpeed, boolean whos, boolean IsBoom, int dam){
		this.site = site;
		this.bsite = bsite;
		this.lock = lock;
		this.BS = BulletSpeed;
		this.whos = whos;
		this.IsBoom = IsBoom;
		this.dam=dam;
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		if(whos)
			this.IMG = tk.getImage(".\\Graphic\\Alias.png");
		else
			this.IMG = tk.getImage(".\\Graphic\\Enermy.png");
		
		angle = Math.atan2(site.x - lock.x, lock.y - site.y);
	}
	
	public boolean move(){
		site.x -= BS*Math.sin(angle);
		site.y += BS*Math.cos(angle);
		if(bsite.distance(site) > 100 && IsBoom){
			return true;
		}
		else if(bsite.distance(site) > 200){
			return true;
		}
		return false;
	}
	public damage shot(Point ob,int type){

		 //if(ob.x-25<site.x+5 && ob.x+25>site.x-5 && ob.y-25<site.y+5 && ob.x+25>site.x-5){
		if(1500 >= ((ob.x - site.x)*(ob.x - site.x)) + ((site.y - ob.y)*(site.y - ob.y))){
			 return new damage(this.dam,true);
		 }
		 else{
			 return new damage(this.dam,false);
		 }
	}
}
class damage {
	int dam;
	boolean hit;
	damage(int i, boolean a){
		this.dam=i;
		this.hit=a;
	}
	/*public damage shot(Point bu,Point ob ){
			 if(ob.x-25<bu.x+5 && ob.x+25>bu.x-5 && ob.y-25<bu.y+5 &&ob.x+25>bu.x-5){
				 return new damage(this.dam,true);
			 }
			 else{
				 return new damage(this.dam,false);
			 }
		 }*/
}
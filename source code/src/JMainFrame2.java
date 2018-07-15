import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
 
@SuppressWarnings("serial")
public class JMainFrame2 extends JFrame implements Runnable, KeyListener, MouseMotionListener, MouseListener, WindowListener {
	JUseObject useobject;
	private int keyStatus = 0;
 
 char map_type[][] = {
		 {'W','W','W','W','W','W','W','W','W','W'},
		 {'S','R','R','R','R','R','R','R','R','W'},
		 {'W','G','G','G','G','G','G','G','R','W'},
		 {'W','R','R','R','G','G','R','R','R','W'},
		 {'W','R','G','R','R','R','R','G','G','W'},
		 {'W','R','G','G','G','G','G','G','R','E'},
		 {'W','R','G','R','R','R','G','G','R','W'},
		 {'W','R','R','R','G','R','G','G','R','W'},
		 {'W','G','G','G','G','R','R','R','R','W'},
		 {'W','W','W','W','W','W','W','W','W','W'}
 };
 JGround map[][] = new JGround[10][10];
 int map_id[][] = new int[10][10];
 BackGroundImage BGI = new BackGroundImage();
 Image tower1, tower2, tower3, tower4, upgrade, savei, loadi, exiti;
 int times, timem, timeh, timest, monstime;
 Calendar cal, cal2;
 JMainObject castle;
 Point mouseP = new Point();
 int mousemode=0;//마우스 이벤트 상태
 int createtower=0;//타워 건설 타입
 boolean gameover;
 
 public JMainFrame2() {
	 
	 setLocation(500,200);
	 
	 gameover = false;
	 
	 cal = Calendar.getInstance();
	 castle = new JMainObject(5, new Point(461,280));
	 
	 monstime = 0;
	 times = 0;
	 timem = 0;
	 timeh = 0;
	 timest = 5;
	 
	 Toolkit tk = this.getToolkit();
	 
	 tower1 = tk.getImage(".\\Graphic\\buy tower1.png");
	 tower2 = tk.getImage(".\\Graphic\\buy tower2.png");
	 tower3 = tk.getImage(".\\Graphic\\buy tower3.png");
	 tower4 = tk.getImage(".\\Graphic\\buy tower4.png");
	 upgrade = tk.getImage(".\\Graphic\\Upgrade.png");
	 savei = tk.getImage(".\\Graphic\\Save.png");
	 exiti = tk.getImage(".\\Graphic\\Exit.png");
	 loadi = tk.getImage(".\\Graphic\\Load.png");
	 
	 this.setSize(1010, 520);
	 this.setTitle("Fairytale Virus");
	 this.setResizable(false);
	 this.setLayout(null);
	 
	 Container backgroundPane = new Container();
	 this.add(backgroundPane);
	 backgroundPane.setLayout(null);
	 backgroundPane.setBounds(0,0,1020,560);
	 backgroundPane.add(BGI);
	 
	 Container groundPane = new Container();
	 this.add(groundPane);
	 GridLayout layout = new GridLayout(10,10);
	 groundPane.setLayout(layout);
	 groundPane.setBounds(10, 10, 500, 500);
	 
	 for(int j=0; j<10; j++){
		 for(int i=0; i<10; i++){
			 map_id[i][j]=-10;
			 map[i][j] = new JGround(10+50*i, 35+50*j, map_type[j][i]);
			 groundPane.add(map[i][j]);
		 }
	 }
	 
	 Container towerbuyPane = new Container();
	 this.add(towerbuyPane);
	 towerbuyPane.setBounds(450, 5, 570, 555);
	 towerbuyPane.setLayout(null);

	 this.addKeyListener(this);
	 this.addMouseMotionListener(this);
	 this.addMouseListener(this);
	 this.addWindowListener(this);
	 
	 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 this.setVisible(true);
	 
 }
 
 public void run() {
  try {
   while(true) {
	//Move
    if((keyStatus & 0x0001) == 0x0001 ) {
      if ( useobject.mc.site.x < 413 ) useobject.mc.site.x +=2;
    }
    if((keyStatus & 0x0010) == 0x0010 ){
      if ( useobject.mc.site.x > 60 ) useobject.mc.site.x -=2;
    }
    if((keyStatus & 0x0100) == 0x0100 ){
      if ( useobject.mc.site.y > 85 ) useobject.mc.site.y -=2;
    }
    if((keyStatus & 0x1000) == 0x1000 ){
      if ( useobject.mc.site.y < 438 ) useobject.mc.site.y +=2;
    }
    //MonsterMove
    if(!useobject.monsterList.isEmpty()){
    	for(int i=0; i<useobject.monsterList.size(); i++){
    		useobject.monsterList.get(i).move();
			if(useobject.monsterList.get(i).nextPoint.size() == useobject.monsterList.get(i).now){
				if(castle != null){
					if(castle.DMG()){
						gameover = true;
		    			windowClosing(null);
					}
				}
				for(int j=0; j<useobject.top.size(); j++){
					if(useobject.top.get(j).target == useobject.monsterList.get(i)){
						useobject.top.get(j).target = null;
					}
				}
				useobject.monsterList.remove(i);
			}
    	}
    }
    //OutRangeBullet Delete
    for(int ThreadCount=0; ThreadCount<useobject.bl.size(); ThreadCount++){
    	if(useobject.bl.get(ThreadCount).move()){
    		useobject.bl.remove(ThreadCount);
    		ThreadCount--;
    	}
    }
    
    //check Bullet - Monster or Player
    for(int ThreadCount1=0; ThreadCount1<useobject.bl.size(); ThreadCount1++){
    	if(useobject.bl.get(ThreadCount1).whos){ // true
    		for(int count=0; count< useobject.monsterList.size(); count++){
    			damage pr = useobject.bl.get(ThreadCount1).shot(useobject.monsterList.get(count).site, 1);
    			if(pr.hit){
    				useobject.bl.remove(ThreadCount1);
    				ThreadCount1--;
    				useobject.monsterList.get(count).HP-=pr.dam;
    				if(useobject.monsterList.get(count).HP<=0){
    					for(int i=0; i<useobject.top.size(); i++){
    						if(useobject.top.get(i).target == useobject.monsterList.get(count)){
    							useobject.top.get(i).target = null;
    						}
    					}
    					useobject.mc.mana += useobject.monsterList.get(count).rewardMana;
    					useobject.monsterList.remove(count);
    				}
    				else {
    					//크앙크앙
    				}
    				break;
    			}
    			else{
    				continue;
    			}
    		}
    	}
    	else{
    		damage pr = useobject.bl.get(ThreadCount1).shot(useobject.mc.site,1);
    		if(pr.hit){
				useobject.bl.remove(ThreadCount1);
				ThreadCount1--;
    			useobject.mc.HP-= 1;
    			if(useobject.mc.HP <= 0){
    				gameover = true;
    				windowClosing(null);
    			}
    		}
    	}
    }

    
    cal2 = Calendar.getInstance();

	if(cal2.get(Calendar.SECOND) > cal.get(Calendar.SECOND)){
		times += cal2.get(Calendar.SECOND) - cal.get(Calendar.SECOND); 
		if(times >= 60){
			times = 0;
			timem += 1;
			if(timem >= 60){
				timem = 0;
				timeh += 1;
			}
		}
		timest -= cal2.get(Calendar.SECOND) - cal.get(Calendar.SECOND); 
		if(timest < 0){
			if(monstime < 5){
				timest = 1;

				if(useobject.mc.stage < 10){
					useobject.monsterList.add(new JMonster(((4*useobject.mc.stage)+(useobject.mc.stage*(useobject.mc.stage-1))), 1, useobject.mc.stage, -40, 86, useobject.mc.stage, 2));
					monstime ++;
				}
				else{
					useobject.monsterList.add(new JMonster(((4*useobject.mc.stage)+(useobject.mc.stage*(useobject.mc.stage-1))*4), 2, 10, -40, 86, useobject.mc.stage, useobject.mc.stage));
					monstime += 5;
				}
			}
			else{
				useobject.mc.stage += 1;
				monstime = 0;
				timest = 5;
			}
		}
		/*if(useobject.AS1T < 0){
			useobject.AS1T = 1;
			for(int as1=0; as1<useobject.top.size(); as1++){
				if(useobject.top.get(as1).target != null){
					useobject.bl.add(new JBullet(new Point(useobject.top.get(as1).site.x, useobject.top.get(as1).site.y), useobject.top.get(as1).site, useobject.top.get(as1).target.site, 8, true, useobject.top.get(as1).boom, useobject.top.get(as1).DMG));
					if(useobject.top.get(as1).AS == 1)
						useobject.bl.add(new JBullet(new Point(useobject.top.get(as1).site.x, useobject.top.get(as1).site.y), useobject.top.get(as1).site, useobject.top.get(as1).target.site, 8, true, useobject.top.get(as1).boom, useobject.top.get(as1).DMG));
				}
			}
		}
	}
	for(int ThreadCount2=0; ThreadCount2<useobject.top.size(); ThreadCount2++){
	   	for(int count=0; count< useobject.monsterList.size(); count++){
	   		useobject.top.get(ThreadCount2).setTarget(useobject.monsterList.get(count));
	   	}
	}*/
	}
	cal = Calendar.getInstance();
	
    repaint();
    Thread.sleep(20);
   }
  }catch (Exception e){}
 }
  
 
 public void paint(Graphics g) {
	 Image buf = createImage(1020, 600);
	 Graphics back = buf.getGraphics();
	 back.clearRect(10, 35, 1020, 600);
	 
	for(int i=0; i<10; i++){
		 for(int j=0; j<10; j++){
			 back.drawImage(map[i][j].img, map[i][j].x, map[i][j].y, this);
		 }
	 }	
	
	back.drawImage(tower1, 550, 35, tower1.getWidth(this), tower1.getHeight(this), Color.WHITE, this);
	back.drawImage(tower2, 650, 35, tower1.getWidth(this), tower1.getHeight(this), Color.WHITE, this);
	back.drawImage(tower3, 550, 135, tower1.getWidth(this), tower1.getHeight(this), Color.WHITE, this);
	back.drawImage(tower4, 650, 135, tower1.getWidth(this), tower1.getHeight(this), Color.WHITE, this);
	//back.drawImage(upgrade, )
	String str = null;
	if(castle != null){
		back.drawImage(castle.OI, castle.site.x, castle.site.y, castle.OI.getWidth(this), castle.OI.getHeight(this),this);
		str = String.format("성 내구도 : %3d", castle.HP);
		back.drawString(str, 800, 110);
	}
	else{
		str = String.format("성 내구도 : %3d", 0);
		back.drawString(str, 800, 110);
	}
	
	str = String.format("게임 진행 시간 : %2d : %2d : %2d", timeh,timem,times);
	back.drawString(str, 800, 50);
	str = String.format("다음 스테이지 : %3d 초", timest);
	back.drawString(str, 800, 70);
	str = String.format("스테이지 : %2d", useobject.mc.stage);
	back.drawString(str, 800, 90);
	if(castle != null){
		back.drawImage(upgrade, 800, 170, upgrade.getWidth(this), upgrade.getHeight(this), this);
		str = String.format("성 내구도 : %1d", 5);
		back.drawString(str, 925, 200);
		str = String.format("%3d   ===> %3d", castle.HP, castle.HP+1);
		back.drawString(str, 920, 235);
	}
	str = String.format("HP : %2d", useobject.mc.HP);
	back.drawString(str, 800, 130);
	str = String.format("마나 : %2d", useobject.mc.mana);
	back.drawString(str, 800, 150);

	back.drawImage(upgrade, 550, 260, upgrade.getWidth(this), upgrade.getHeight(this), this);
	str = String.format("캐릭터 공격력 : %3d", 10);
	back.drawString(str, 675, 290);
	str = String.format("%3d   ===> %3d", useobject.mc.DMG, useobject.mc.DMG+1);
	back.drawString(str, 690, 328);
	back.drawImage(upgrade, 550, 340, upgrade.getWidth(this), upgrade.getHeight(this), this);
	str = String.format("캐릭터 체력 회복 : %2d", 5);
	back.drawString(str, 670, 370);
	str = String.format("%3d   ===> %3d", useobject.mc.HP, useobject.mc.HP+5);
	back.drawString(str, 690, 408);
	back.drawImage(upgrade, 550, 420, upgrade.getWidth(this), upgrade.getHeight(this), this);
	str = String.format("타워 업그레이드 : %2d", useobject.top.size()*(JTower.upgradeCount_DMG+1)*2);
	back.drawString(str, 670, 450);
	str = String.format("%3d   ===> %3d", JTower.upgradeCount_DMG, JTower.upgradeCount_DMG+1);
	back.drawString(str, 690, 488);
	
	back.drawImage(savei, 800, 260, upgrade.getWidth(this), upgrade.getHeight(this), this);
	back.drawImage(loadi, 800, 340, upgrade.getWidth(this), upgrade.getHeight(this), this);
	back.drawImage(exiti, 800, 420, upgrade.getWidth(this), upgrade.getHeight(this), this);
	
	for(int j=0; j<useobject.bl.size(); j++){
		back.drawImage(useobject.bl.get(j).IMG, useobject.bl.get(j).site.x, useobject.bl.get(j).site.y, useobject.bl.get(j).IMG.getWidth(this), useobject.bl.get(j).IMG.getHeight(this), this);
	}
	
	for(int i=0; i<useobject.monsterList.size(); i++){
		back.drawImage(useobject.monsterList.get(i).CI, useobject.monsterList.get(i).site.x, useobject.monsterList.get(i).site.y, useobject.monsterList.get(i).CI.getWidth(this), useobject.monsterList.get(i).CI.getHeight(this), this);
	}
		
	for(int i=0; i<useobject.top.size(); i++){
		back.drawImage(useobject.top.get(i).IMG, useobject.top.get(i).site.x, useobject.top.get(i).site.y, useobject.top.get(i).IMG.getWidth(this), useobject.top.get(i).IMG.getHeight(this), this);
	}

	back.drawImage(useobject.mc.CI, useobject.mc.site.x, useobject.mc.site.y, useobject.mc.CI.getWidth(this), useobject.mc.CI.getHeight(this), this);
	
	g.drawImage(buf, 0, 0, this);
 }
 
 public void keyPressed(KeyEvent e){
  if(e.getKeyCode()==KeyEvent.VK_D) keyStatus |= 0x0001;
  if(e.getKeyCode()==KeyEvent.VK_A)  keyStatus |= 0x0010;
  if(e.getKeyCode()==KeyEvent.VK_W)    keyStatus |= 0x0100;
  if(e.getKeyCode()==KeyEvent.VK_S)  keyStatus |= 0x1000;
 }
 
 public void keyReleased(KeyEvent e)
 {
  if(e.getKeyCode()==KeyEvent.VK_D) keyStatus -= 0x0001;
  if(e.getKeyCode()==KeyEvent.VK_A)  keyStatus -= 0x0010;
  if(e.getKeyCode()==KeyEvent.VK_W)    keyStatus -= 0x0100;
  if(e.getKeyCode()==KeyEvent.VK_S)  keyStatus -= 0x1000;
  if(e.getKeyCode()==KeyEvent.VK_SPACE) useobject.bl.add(new JBullet(new Point(useobject.mc.site.x, useobject.mc.site.y), new Point(useobject.mc.site.x, useobject.mc.site.y), new Point(mouseP.x, mouseP.y), 3, true, false, useobject.mc.DMG));
  //미사일 발싸 !
 }
 
 public void keyTyped(KeyEvent ke) {}
 
 /*public static void main(String[] args) {
	 Thread t = new Thread(new JMainFrame2());
	 t.start();
	 //t2.start();
 }*/

@Override
public void mouseDragged(MouseEvent arg0) {
	// TODO Auto-generated method stub
}

@Override
public void mouseMoved(MouseEvent arg0) {
	// TODO Auto-generated method stub
	mouseP = arg0.getPoint();
	/*String str;
	str = String.format("%d", mousemode);
	System.out.println(str);*/
}

@Override
public void mouseEntered(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseExited(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mousePressed(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseReleased(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	// TODO Auto-generated method stub
		int x=e.getX();
		int y=e.getY();
		if(e.getButton() == MouseEvent.BUTTON2){
			mousemode = 0;
			createtower = 0;
		}
		switch(mousemode){
		case 0 ://초기 상태
			if(useobject.mc.mana>=JTower.towerCost){
				if(mcheck(x,y,new Point(550,35),tower1.getWidth(this),tower1.getHeight(this))){
					this.mousemode=1;
					this.createtower=1;
				}
				if(mcheck(x,y,new Point(650,35),tower1.getWidth(this),tower1.getHeight(this))){
					this.mousemode=1;
					this.createtower=2;
				}
				if(mcheck(x,y,new Point(550,135),tower1.getWidth(this),tower1.getHeight(this))){
					this.mousemode=1;
					this.createtower=3;
				}
				if(mcheck(x,y,new Point(650,135),tower1.getWidth(this),tower1.getHeight(this))){
					this.mousemode=1;
					this.createtower=4;
				}
			}
			if( mcheck(x,y, new Point(550,260), upgrade.getWidth(this), upgrade.getHeight(this)) ){
				if(useobject.mc.mana>=((useobject.mc.return_upgradeCount_DMG()+1))*10){
					useobject.mc.mana-=((useobject.mc.return_upgradeCount_DMG()+1))*10;
					useobject.mc.upgrade_DMG();
				}
			}
			else if( mcheck(x,y, new Point(550,340), upgrade.getWidth(this), upgrade.getHeight(this)) ){
				if(useobject.mc.mana>=5){
					useobject.mc.mana-=5;
					useobject.mc.upgrade_HP();
				}
			}
			else if( mcheck(x,y, new Point(550,420), upgrade.getWidth(this), upgrade.getHeight(this)) ){
				if(useobject.mc.mana>=useobject.top.size()*((JTower.upgradeCount_DMG+1)*2)){
					if(useobject.top.size()>0){
						useobject.mc.mana-=useobject.top.size()*((JTower.upgradeCount_DMG+1)*2);
						JTower.TDMG_UP();
						for(int toco =0; toco <useobject.top.size(); toco++){
							useobject.top.get(toco).TowerDMG_Upgrade();
						}						
					}
				}
			}
			else if( mcheck(x,y, new Point(800,170), upgrade.getWidth(this), upgrade.getHeight(this)) ){
				if(useobject.mc.mana>=5 && castle != null){
					useobject.mc.mana-=5;
					castle.HP += 1;
				}
			}
			else if( mcheck(x,y, new Point(800,260), upgrade.getWidth(this), upgrade.getHeight(this)) ){
				save();
			}
			else if( mcheck(x,y, new Point(800,340), upgrade.getWidth(this), upgrade.getHeight(this)) ){
				open();
			}
			else if( mcheck(x,y, new Point(800,420), upgrade.getWidth(this), upgrade.getHeight(this)) ){
				windowClosing(null);
			}
			break;
		case 1 ://타워 생성
			if(10<x && 35<y && x<10+500 && y<35+500){//맵 영역 부터 확인 
				for(int gi=0; gi<10; gi++){
					if(mousemode == 0) break;
					for(int gj=0; gj<10; gj++){
						if(mousemode == 0) break;
						if(mcheck(x-25,y-25,32+50*gi, 60+50*gj,50,50) && map_type[gj][gi]=='G'){
							boolean is = false;
							for(int i=0;i<useobject.top.size();i++){
								is=mcheck(10+50*gi, 35+50*gj,useobject.top.get(i).site);
								if(is){
									break;
								}
								else{
									continue;
								}
							}
							if(!is){
								useobject.mc.mana-=JTower.towerCost;
								mousemode=0;
								useobject.top.add(new JTower(createtower, new Point(10+(50*gi), 35+(50*gj))));
								createtower =0;
							}
						}
					}
				}
			}
			else{
				//잠시 보류
			}
		}

	}

	class BackGroundImage extends JPanel {
		public void paint(Graphics g){
			Toolkit tk = this.getToolkit();
			Image image = tk.getImage(".\\Graphic\\bgi.png");
			g.drawImage(image,  0,  0,  this);
		}
	}
	
	public boolean mcheck(int x, int y, Point site, int width, int hegiht){
		 if(site.x<x&&site.y<y&&site.x+width>x&&site.y+hegiht>y)
			 return true;
		 else 
			 return false;
	}
	
	public boolean mcheck(int x, int y, Point site){
		 if(x==site.x&&y==site.y){
		 return true;
		 }
		 else{
			 return false;
		 }
	}
	
	public boolean mcheck(int x, int y,int x0, int y0, int wid, int hegiht){
		if(x<x0&&y<y0&&x+wid>x0&&y+hegiht>y0)
			return true;
		else
			return false;
	}
	
	@SuppressWarnings("static-access")
	public void save(){
		DataOutputStream wr=null;
		int tower_count=useobject.top.size();
		try {
			wr = new DataOutputStream(new FileOutputStream("FairySave.dat"));
			wr.writeInt(tower_count);
			
			//wr.writeInt(JTower.upgradeCount_AS);
			wr.writeInt(JTower.upgradeCount_DMG);
			
			for(int ct=0; ct<tower_count; ct++){
				wr.writeInt(useobject.top.get(ct).DMG);
				wr.writeInt(useobject.top.get(ct).AS);
				wr.writeInt(useobject.top.get(ct).site.x);
				wr.writeInt(useobject.top.get(ct).site.y);
				wr.writeInt(useobject.top.get(ct).ID);
				wr.writeInt(useobject.top.get(ct).towerCost);
				wr.writeBoolean(useobject.top.get(ct).boom);
				//wr.writeInt(top.get(ct).upgradeCount_AS);
				//wr.writeInt(top.get(ct).upgradeCount_DMG);
			}
			
			wr.writeInt(useobject.mc.DMG);
			wr.writeInt(useobject.mc.HP);
			wr.writeInt(useobject.mc.mana);
			wr.writeInt(useobject.mc.stage);
			wr.writeInt(useobject.mc.site.x);
			wr.writeInt(useobject.mc.site.y);
			wr.writeInt(useobject.mc.MS);
			wr.writeInt(useobject.mc.return_upgradeCount_DMG());
			wr.writeInt(useobject.mc.return_upgradeCount_HP());
			
			wr.writeInt(times);
			wr.writeInt(timem);
			wr.writeInt(timeh);
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			System.out.println("저장 실패");
		}
		finally{
			try{
				wr.close();
			}catch(Exception e1){
			}
		}
	}
	@SuppressWarnings("static-access")
	public void open(){
		DataInputStream wr=null;
		try {
			wr = new DataInputStream(new FileInputStream("FairySave.dat"));
			int tower_count=wr.readInt();
			
			useobject.top.clear();
			useobject.bl.clear();
			useobject.monsterList.clear();
			
			timest = 5;
			monstime = 0;
			
			for(int read=0; read<tower_count;read++){
				useobject.top.add(new JTower());
			}
			
			//JTower.upgradeCount_AS=wr.readInt();
			JTower.upgradeCount_DMG=wr.readInt();
			
			for(int ct=0; ct<tower_count; ct++){
				useobject.top.get(ct).DMG= wr.readInt();
				useobject.top.get(ct).AS= wr.readInt();
				useobject.top.get(ct).site.x= wr.readInt();
				useobject.top.get(ct).site.y= wr.readInt();
				useobject.top.get(ct).ID= wr.readInt();
				useobject.top.get(ct).towerCost=wr.readInt();
				useobject.top.get(ct).boom =wr.readBoolean();
				//top.get(ct).upgradeCount_AS=wr.readInt();
				//top.get(ct).upgradeCount_DMG=wr.readInt();
			}
			
			useobject.mc.DMG=wr.readInt();
			useobject.mc.HP=wr.readInt();
			useobject.mc.mana=wr.readInt();
			useobject.mc.stage=wr.readInt();
			useobject.mc.site.x=wr.readInt();
			useobject.mc.site.y=wr.readInt();
			useobject.mc.MS=wr.readInt();
			
			useobject.mc.read_up_count(wr.readInt(), wr.readInt());
			
			times=wr.readInt();
			timem=wr.readInt();
			timeh=wr.readInt();
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			System.out.println("읽기 오류");
		}
		finally{
			try{
				wr.close();
			}catch(Exception e1){
			}
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		if(gameover){
				JOptionPane.showMessageDialog(null, "GAME OVER" );
				System.exit(0); 
			}
		else
			if(JOptionPane.showConfirmDialog(null, "종료하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION){
				System.exit(0); 
			}
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "WASD로 이동하며 스페이스로 공격, 마우스로 조준 및 타워를 건설합니다.");
	}
}
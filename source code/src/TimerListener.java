import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TimerListener implements ActionListener {
	JUseObject uo = new JUseObject();
	int cnt;
	
	TimerListener(JUseObject uo){
		this.uo = uo;
		cnt = 0;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
				
		for(int i=0; i<uo.top.size(); i++){
		   	for(int count=0; count< uo.monsterList.size(); count++){
		   		uo.top.get(i).setTarget(uo.monsterList.get(count));
		   	}
		}
		
		if((cnt = (cnt+1)%2) == 0){
			for(int as1=0; as1<uo.top.size(); as1++){
				if(uo.top.get(as1).target != null && uo.top.get(as1).AS == 1)
					uo.bl.add(new JBullet(new Point(uo.top.get(as1).site.x, uo.top.get(as1).site.y), uo.top.get(as1).site, new Point(uo.top.get(as1).target.site.x, uo.top.get(as1).target.site.y), 12, true, uo.top.get(as1).boom, uo.top.get(as1).DMG));
			}
		}
		else{
			for(int as1=0; as1<uo.top.size(); as1++){
					if(uo.top.get(as1).target != null){
						uo.bl.add(new JBullet(new Point(uo.top.get(as1).site.x, uo.top.get(as1).site.y), uo.top.get(as1).site, new Point(uo.top.get(as1).target.site.x, uo.top.get(as1).target.site.y), 12, true, uo.top.get(as1).boom, uo.top.get(as1).DMG));
						System.out.println(uo.top.get(as1).target.site);
					}
			}
			
			for(int as2=0; as2<uo.monsterList.size(); as2++){
				uo.bl.add(new JBullet(new Point(uo.monsterList.get(as2).site.x,uo.monsterList.get(as2).site.y), uo.monsterList.get(as2).site, new Point(uo.mc.site.x, uo.mc.site.y), 4, false, false, 1));
			}
		}
	}
}

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

@SuppressWarnings("serial")
class JGround extends JPanel
{
	int x;
	int y;
	char type = 'n';
	Image img;

	JGround(int x, int y , char t)
	{
		this.x = x;
		this.y = y;
		type = t;
		Toolkit toolit = Toolkit.getDefaultToolkit();
		try{
			if (t == 'S' || t == 'E' || t == 'R')
				img = toolit.getImage("./Graphic/road.png");
		else if (t == 'G')
				img = toolit.getImage("./Graphic/ground.png");		
		else if (t == 'W')
			img = toolit.getImage("./Graphic/wall.png");	
		}catch(Exception e){
			System.out.println(e);
		}		
	}
	
}
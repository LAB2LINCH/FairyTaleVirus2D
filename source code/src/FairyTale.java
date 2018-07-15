import javax.swing.Timer;


public class FairyTale {
	public static void main(String[] args) {
		//JMainFrame t2 = new JMainFrame();
		JMainFrame2 t = new JMainFrame2();
		Thread t3 = new Thread(t);
		 JUseObject useo = new JUseObject();

		 TimerListener tim = new TimerListener(useo);
		 Timer timer = new Timer(1000, tim);
		 timer.start();
		 
		 t.useobject = useo;
		// t2.useobject = useo;
		 t3.start();
		 //t2.start();
	 }
}

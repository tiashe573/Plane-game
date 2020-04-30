import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

public class MyGameFrame extends Frame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Image bg = GameUtil.getImage("image/universe.JPG");
	Image planeImage = GameUtil.getImage("image/feiji.png");
	
	Plane plane = new Plane (planeImage,250,250);
	shell [] shells = new shell[50];
	Explode bao;
	Date startTime = new Date ();
	Date endTime;
	int period;
	
	
	@Override
	public void paint (Graphics g) {
		g.drawImage(bg, 0, 0, null);
		plane.drawSelf(g);
		for (int i = 0; i < shells.length; i++) {
			if (shells[i] != null) {
				shells[i].draw(g);
				boolean peng = shells[i].getRect().intersects(plane.getRect());
				if (peng) {
					plane.live = false;
					if(bao==null){
	                    bao = new Explode(plane.x,plane.y);
	                    endTime = new Date ();
	                    period = (int)(endTime.getTime() - startTime.getTime())/1000;
	                }
					
	                bao.draw(g);
				}
				if (!plane.live) {
					Font f = new Font("Time New Roman", Font.BOLD, 50);
					g.setFont(f);
					g.drawString("Time :" + period + "s", 200, 200);
				}
				
			}
			}
		
	}
	public void launchFrame() {
		this.setTitle("work by Tianhao Shen");
		this.setVisible(true);
		this.setSize(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);
		this.setLocation(300,300);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		new PaintThread().start();
		addKeyListener(new KeyMonitor());
		
		for (int i = 0; i < shells.length; i++) {
			shells[i] = new shell();
		}
	}
	class PaintThread extends Thread {
        public void run(){
            while(true){
                repaint();
                try {
                    Thread.sleep(100); //1s = 1000ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }   
            }
        }
    }
	class KeyMonitor extends KeyAdapter {
		@Override 
		public void keyPressed(KeyEvent e) {
			plane.addDirection(e);;
		}
		
		@Override 
		public void keyReleased(KeyEvent e) {
			plane.minusDirection(e);;
		}
	}
	
	
	private Image offScreenImage = null;
	 
	public void update(Graphics g) {
	    if(offScreenImage == null)
	        offScreenImage = this.createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);
	     
	    Graphics gOff = offScreenImage.getGraphics();
	    paint(gOff);
	    g.drawImage(offScreenImage, 0, 0, null);
	}  
	public static void main (String [] args) {
		MyGameFrame f = new MyGameFrame();
		f.launchFrame();
	}
	
	
}

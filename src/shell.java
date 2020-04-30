import java.awt.Color;
import java.awt.Graphics;

public class shell extends GameObject{
	
	double degree;
	
	public shell() {
		x = 100;
		y = 100;
		width = 10;
		height = 10;
		speed = 10;
		degree = Math.random()*Math.PI*2;
		
	}
	
	public void draw (Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.yellow);
		g.fillOval((int)x, (int)y, width, height);
		x += Math.cos(degree) * speed;
		y += Math.sin(degree) * speed;
		if (x < 0 || x > Constant.GAME_WIDTH - width) {
			degree = Math.PI - degree;
		}
		if (y < 30 || y > Constant.GAME_HEIGHT - height) {
			degree = - degree;
		}
		g.setColor(c);
	}
}

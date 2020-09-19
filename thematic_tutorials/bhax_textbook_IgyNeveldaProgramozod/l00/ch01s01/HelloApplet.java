import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;

public class HelloApplet extends Applet{

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLUE);
		g.fillRect(10, 10, 50, 20);
	}
}

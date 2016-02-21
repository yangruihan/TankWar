
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * 坦克大战客户端
 * @author Yrh
 *
 */
public class TankClient extends JFrame {
	
	public void lanchFrame() {
		this.setSize(800, 600);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		TankClient tankClient = new TankClient();
		tankClient.lanchFrame();
	}
}

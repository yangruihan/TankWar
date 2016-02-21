
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * 坦克大战客户端
 * @author Yrh
 *
 */
public class TankClient extends JFrame {
	
	public void lanchFrame() {
		// 设置窗口大小
		this.setSize(800, 600);
		// 设置标题文字
		this.setTitle("TankWar");
		// 设置默认关闭操作
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// 设置窗口大小不可变
		this.setResizable(false);
		// 设置窗口是否可见
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		TankClient tankClient = new TankClient();
		tankClient.lanchFrame();
	}
}

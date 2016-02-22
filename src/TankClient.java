
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * 坦克大战客户端
 * 
 * @author Yrh
 *
 */
public class TankClient extends JFrame {

	public static final int GAME_WIDTH = 800; // 窗口宽度
	public static final int GAME_HEIGHT = 600; // 窗口高度
	public static final Color GAME_BACKGROUND_COLOR = Color.GREEN; // 窗口背景颜色

	private int x = 50; // 坦克 x 坐标
	private int y = 50; // 坦克 y 坐标
	private int speed = 5; // 坦克速度

	private Image offScreenImage = null; // 缓冲图片

	/*
	 * 绘制方法
	 */
	@Override
	public void paint(Graphics g) {
		// 保存当前颜色
		Color c = g.getColor();

		// 设置颜色并绘制背景
		g.setColor(GAME_BACKGROUND_COLOR);
		g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

		// 设置颜色并画圆
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);

		// 还原颜色
		g.setColor(c);
	}

	/*
	 * 覆写 update 方法 实现双缓冲
	 */
	@Override
	public void update(Graphics g) {
		// 判断缓冲图片是否存在
		if (offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}

		// 获得图片画笔
		Graphics gOffScreen = offScreenImage.getGraphics();

		// 重绘背景
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(GAME_BACKGROUND_COLOR);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);

		// 绘制图形
		paint(gOffScreen);

		// 将图片绘制到显示器上
		g.drawImage(offScreenImage, 0, 0, null);

	}

	/*
	 * 加载 Frame 方法
	 */
	public void lanchFrame() {
		// 设置窗口大小
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		// 设置标题文字
		this.setTitle("TankWar");
		// 设置默认关闭操作
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// 设置窗口大小不可变
		this.setResizable(false);
		// 设置窗口是否可见
		this.setVisible(true);
		
		// 添加键盘监听器
		this.addKeyListener(new KeyMonitor());

		new Thread(new PaintThread()).start();
	}

	public static void main(String[] args) {
		TankClient tankClient = new TankClient();
		tankClient.lanchFrame();
	}

	/**
	 * 绘制线程类
	 * 
	 * @author Yrh
	 *
	 */
	private class PaintThread implements Runnable {

		@Override
		public void run() {
			while (true) {
				// 重绘整个窗口
				repaint();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 键盘控制类
	 * 
	 * @author Yrh
	 *
	 */
	private class KeyMonitor extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			switch (key) {
			// 点击左键或者A键
			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:
				x -= speed;
				break;
				
			// 点击右键或者D键
			case KeyEvent.VK_D:
			case KeyEvent.VK_RIGHT:
				x += speed;
				break;
				
			// 点击上键或者W键
			case KeyEvent.VK_W:
			case KeyEvent.VK_UP:
				y -= speed;
				break;
				
			// 点击下键或者S键
			case KeyEvent.VK_S:
			case KeyEvent.VK_DOWN:
				y += speed;
				break;
			default:
				break;
			}
		}

	}
}

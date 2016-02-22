
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * 坦克大战客户端
 * 
 * @author Yrh
 *
 */
public class TankClient extends JFrame {

	private static int WIDTH = 800; // 窗口宽度
	private static int HEIGHT = 600; // 窗口高度
	private static Color BACKGROUND_COLOR = Color.GREEN; // 窗口背景颜色

	private int x = 50; // 坦克 x 坐标
	private int y = 50; // 坦克 y 坐标
	
	
	private Image offScreenImage = null; // 缓冲图片

	/*
	 * 绘制方法
	 */
	@Override
	public void paint(Graphics g) {
		// 保存当前颜色
		Color c = g.getColor();

		// 设置颜色并绘制背景
		g.setColor(BACKGROUND_COLOR);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// 设置颜色并画圆
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);

		// 还原颜色
		g.setColor(c);

		y += 5;
	}
	
	/*
	 * 覆写 update 方法 实现双缓冲
	 */
	@Override
	public void update(Graphics g) {
		// 判断缓冲图片是否存在
		if (offScreenImage == null) {
			offScreenImage = this.createImage(WIDTH, HEIGHT);
		}
		
		// 获得图片画笔
		Graphics gOffScreen = offScreenImage.getGraphics();
		
		// 重绘背景
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(BACKGROUND_COLOR);
		gOffScreen.fillRect(0, 0, WIDTH, HEIGHT);
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
		this.setSize(WIDTH, HEIGHT);
		// 设置标题文字
		this.setTitle("TankWar");
		// 设置默认关闭操作
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// 设置窗口大小不可变
		this.setResizable(false);
		// 设置窗口是否可见
		this.setVisible(true);

		new Thread(new PaintThread()).start();
	}

	public static void main(String[] args) {
		TankClient tankClient = new TankClient();
		tankClient.lanchFrame();
	}

	/**
	 * 绘制线程类
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
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

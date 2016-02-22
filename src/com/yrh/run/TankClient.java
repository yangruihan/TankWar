package com.yrh.run;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.yrh.entity.Tank;

/**
 * 坦克大战客户端
 * 
 * @author Yrh
 *
 */
public class TankClient extends JFrame {

	public static final int GAME_WIDTH = 800; // 窗口宽度
	public static final int GAME_HEIGHT = 600; // 窗口高度
	public static final Color GAME_BACKGROUND_COLOR = Color.WHITE; // 窗口背景颜色
	public static final int GAME_FRAME = 17; // 默认17(60hz)

	private Image offScreenImage = null; // 缓冲图片
	
	private Tank playerTank = new Tank(this); // 实例化一个玩家坦克对象
	
	private ArrayList<Tank> tankList = new ArrayList<>(); // 坦克数组
	
	/**
	 * 绘制方法
	 */
	@Override
	public void paint(Graphics g) {
		// 保存当前颜色
		Color c = g.getColor();
		// 设置颜色并绘制背景
		g.setColor(GAME_BACKGROUND_COLOR);
		g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		// 还原颜色
		g.setColor(c);

		// 绘制坦克
		for (int i = 0; i < tankList.size(); i++) {
			// 如果坦克已经被击中且屏幕上没有它的子弹了，则将其删除
			if (tankList.get(i).isLive() == false && tankList.get(i).getMissileList().size() == 0) {
				tankList.remove(i);
			} else {
				tankList.get(i).draw(g);
			}
		}
	}

	/**
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

	/**
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
		
		Tank enemyTank = new Tank(200, 200, false, this); // 实例化一个敌方坦克
		this.tankList.add(enemyTank);
		this.tankList.add(playerTank);
		
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
					Thread.sleep(GAME_FRAME);
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
			// 处理按键事件
			playerTank.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// 处理按键释放事件
			playerTank.keyReleased(e);
		}
	}

	
	public ArrayList<Tank> getTankList() {
		return tankList;
	}

	public void setTankList(ArrayList<Tank> tankList) {
		this.tankList = tankList;
	}

}

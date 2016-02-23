package com.yrh.entity;

import java.awt.Color;
import java.awt.Graphics;

import com.yrh.run.TankClient;

/**
 * 爆炸类
 * 
 * @author Yrh
 *
 */
public class Explode {

	public static Color EXPLODE_COLOR = Color.ORANGE; // 默认爆炸颜色
	public static int INTERVAL_TIME = 80; // 默认爆炸范围更改间隔时间

	private int x; // 爆炸的X坐标
	private int y; // 爆炸的Y坐标

	private boolean live = true; // 生死标记

	private TankClient tc; // 坦克客户端引用

	private int[] diameter = { 3, 9, 17, 25, 34, 49, 28, 12, 2 }; // 直径的变化
	private int step = 0; // 记录直径变化到哪一步

	public Explode(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
		
		new Thread(new ExplodeThread()).start();
	}

	public void draw(Graphics g) {
		if (!live) {
			return;
		}

		if (step == diameter.length) {
			live = false;
			return;
		}

		Color c = g.getColor();
		g.setColor(EXPLODE_COLOR);
		g.fillOval(x, y, diameter[step], diameter[step]);
		g.setColor(c);
	}

	/**
	 * 爆炸线程
	 * 
	 * @author Yrh
	 *
	 */
	private class ExplodeThread implements Runnable {

		@Override
		public void run() {
			while (step < diameter.length) {
				try {
					Thread.sleep(INTERVAL_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				step++;
			}
		}

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public TankClient getTc() {
		return tc;
	}

	public void setTc(TankClient tc) {
		this.tc = tc;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}
}

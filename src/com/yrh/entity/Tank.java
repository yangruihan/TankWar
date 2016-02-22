package com.yrh.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 * 坦克实体类
 * @author Yrh
 *
 */
public class Tank {
	private int x; // 坦克 x 坐标
	private int y; // 坦克 y 坐标
	private int speed; // 坦克速度
	
	/**
	 * 默认构造方法
	 */
	public Tank() {
		this(50, 50, 5);
	}

	/**
	 * 带两个参数的构造方法
	 * @param x 坦克出生的x坐标
	 * @param y 坦克出生的y坐标
	 */
	public Tank(int x, int y) {
		this(x, y, 5);
	}

	/**
	 * 带三个参数的构造方法
	 * @param x 坦克出生的x坐标
	 * @param y 坦克出生的y坐标
	 * @param speed 坦克出生的速度
	 */
	public Tank(int x, int y, int speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	
	/**
	 * 绘制自身方法
	 * @param g 画笔
	 */
	public void draw(Graphics g) {
		// 保存当前颜色
		Color c = g.getColor();
		// 设置颜色并画圆
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);
		// 还原颜色
		g.setColor(c);
	}
	
	/**
	 * 处理按键方法
	 * @param e 按键事件
	 */
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
	
	/* setter and getter */
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

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}

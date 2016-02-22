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
	
	public static final int DEFAULT_SPEED = 1;
	
	// 定义Tank的八个方向
	public enum Direction {LEFT, LEFT_UP, UP, RIGHT_UP, RIGHT, RIGHT_DOWN, DOWN, LEFT_DOWN, STOP};
	
	private int x; // 坦克 x 坐标
	private int y; // 坦克 y 坐标
	private int xSpeed; // 坦克x轴速度
	private int ySpeed; // 坦克y轴速度
	
	private Direction direction; // 坦克行走方向
	
	// 用四个boolean值记录用户按键
	private boolean bLeft = false;
	private boolean bRight = false;
	private boolean bUp = false;
	private boolean bDown = false;
	
	/**
	 * 默认构造方法
	 */
	public Tank() {
		this(50, 50, DEFAULT_SPEED, DEFAULT_SPEED);
	}

	/**
	 * 带两个参数的构造方法
	 * @param x 坦克出生的x坐标
	 * @param y 坦克出生的y坐标
	 */
	public Tank(int x, int y) {
		this(x, y, DEFAULT_SPEED, DEFAULT_SPEED);
	}

	/**
	 * 带四个参数的构造方法
	 * @param x 坦克出生的x坐标
	 * @param y 坦克出生的y坐标
	 * @param xSpeed 坦克出生的x轴速度
	 * @param ySpeed 坦克出生的x轴速度
	 */
	public Tank(int x, int y, int xSpeed, int ySpeed) {
		this(x, y, xSpeed, ySpeed, Direction.STOP);
	}
	
	/**
	 * 带四个参数的构造方法
	 * @param x 坦克出生的x坐标
	 * @param y 坦克出生的y坐标
	 * @param xSpeed 坦克出生的x轴速度
	 * @param ySpeed 坦克出生的x轴速度
	 * @param direction 坦克出生的方向
	 */
	public Tank(int x, int y, int xSpeed, int ySpeed, Direction direction) {
		this.x = x;
		this.y = y;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.direction = direction;
	}
	
	/**
	 * 绘制自身方法
	 * @param g 画笔
	 */
	public void draw(Graphics g) {
		// 移动坦克
		move();
		
		// 保存当前颜色
		Color c = g.getColor();
		// 设置颜色并画圆
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);
		// 还原颜色
		g.setColor(c);
	}
	
	/**
	 * 根据方向移动坦克
	 */
	public void move() {
		if (this.direction == Direction.LEFT) {
			x -= xSpeed;
		} else if (this.direction == Direction.LEFT_UP) {
			x -= xSpeed;
			y -= ySpeed;
		} else if (this.direction == Direction.UP) {
			y -= ySpeed;
		} else if (this.direction == Direction.RIGHT_UP) {
			x += xSpeed;
			y -= ySpeed;
		} else if (this.direction == Direction.RIGHT) {
			x += xSpeed;
		} else if (this.direction == Direction.RIGHT_DOWN) {
			x += xSpeed;
			y += ySpeed;
		} else if (this.direction == Direction.DOWN) {
			y += ySpeed;
		} else if (this.direction == Direction.LEFT_DOWN) {
			x -= xSpeed;
			y += ySpeed;
		}
	}
	
	/**
	 * 处理按键释放方法
	 * @param e
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		// 点击左键或者A键
		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			bLeft = false;
			break;
			
		// 点击右键或者D键
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			bRight = false;
			break;
			
		// 点击上键或者W键
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			bUp = false;
			break;
			
		// 点击下键或者S键
		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			bDown = false;
			break;
		default:
			break;
		}
		// 设置方向
		setDirection();
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
			bLeft = true;
			break;
			
		// 点击右键或者D键
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			bRight = true;
			break;
			
		// 点击上键或者W键
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			bUp = true;
			break;
			
		// 点击下键或者S键
		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			bDown = true;
			break;
		default:
			break;
		}
		// 设置方向
		setDirection();
	}
	
	/**
	 * 根据按键设置方向
	 */
	public void setDirection() {
		if (!bLeft && !bRight && !bUp && !bDown) {
			this.direction = Direction.STOP;
		} else if (bLeft && !bRight && !bUp && !bDown) {
			this.direction = Direction.LEFT;
		} else if (bLeft && !bRight && bUp && !bDown) {
			this.direction = Direction.LEFT_UP;
		} else if (!bLeft && !bRight && bUp && !bDown) {
			this.direction = Direction.UP;
		} else if (!bLeft && bRight && bUp && !bDown) {
			this.direction = Direction.RIGHT_UP;
		} else if (!bLeft && bRight && !bUp && !bDown) {
			this.direction = Direction.RIGHT;
		} else if (!bLeft && bRight && !bUp && bDown) {
			this.direction = Direction.RIGHT_DOWN;
		} else if (!bLeft && !bRight && !bUp && bDown) {
			this.direction = Direction.DOWN;
		} else if (bLeft && !bRight && !bUp && bDown) {
			this.direction = Direction.LEFT_DOWN;
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

	public int getxSpeed() {
		return xSpeed;
	}

	public void setxSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}

	public int getySpeed() {
		return ySpeed;
	}

	public void setySpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

}
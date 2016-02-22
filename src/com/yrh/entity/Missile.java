package com.yrh.entity;

import java.awt.Color;
import java.awt.Graphics;

import com.yrh.constants.Direction;
import com.yrh.run.TankClient;

/**
 * 子弹实体类
 * 
 * @author Yrh
 *
 */
public class Missile {
	public static final int DEFAULT_SPEED = 5; // 子弹默认速度
	public static final int MISSILE_WIDTH = 10; // 子弹默认宽度
	public static final int MISSILE_HEIGHT = 10; // 子弹默认高度
	
	private int x; // 子弹 x 坐标
	private int y; // 子弹 y 坐标
	private int xSpeed; // 子弹x轴速度
	private int ySpeed; // 子弹y轴速度
	private Direction direction; // 子弹运动方向
	
	private boolean live = true; // 子弹是否存活
	
	/**
	 * 三个参数构造方法
	 * @param x 子弹 x 坐标
	 * @param y 子弹 y 坐标
	 * @param direction 子弹运动方向
	 */
	public Missile(int x, int y, Direction direction) {
		this(x, y, DEFAULT_SPEED, DEFAULT_SPEED, direction);
	}

	/**
	 * 五个参数构造方法
	 * @param x 子弹 x 坐标
	 * @param y 子弹 y 坐标
	 * @param xSpeed 子弹x轴速度
	 * @param ySpeed 子弹y轴速度
	 * @param direction 子弹运动方向
	 */
	public Missile(int x, int y, int xSpeed, int ySpeed, Direction direction) {
		super();
		this.x = x;
		this.y = y;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.direction = direction;
	}

	/**
	 * 绘制方法
	 * @param g
	 */
	public void draw(Graphics g) {
		// 保存当前颜色
		Color c = g.getColor();
		// 设置颜色并画圆
		g.setColor(Color.BLACK);
		g.fillOval(x, y, MISSILE_WIDTH, MISSILE_HEIGHT);
		// 还原颜色
		g.setColor(c);
		
		// 移动子弹
		move();
	}

	/**
	 * 计算子弹移动的方法
	 */
	private void move() {
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
		
		if (x < 0 || x > TankClient.GAME_WIDTH || y < 0 || y > TankClient.GAME_HEIGHT) {
			live = false;
		}
	}

	
	/* getter and setter */
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

	public boolean isLive() {
		return live;
	}
}

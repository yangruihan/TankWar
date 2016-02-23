package com.yrh.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import com.yrh.constants.Direction;
import com.yrh.run.TankClient;

/**
 * 子弹实体类
 * 
 * @author Yrh
 *
 */
public class Missile {
	public static final int DEFAULT_SPEED = 5; // 子弹默认速度 默认为5
	public static final int MISSILE_WIDTH = 10; // 子弹默认宽度
	public static final int MISSILE_HEIGHT = 10; // 子弹默认高度
	
	private int x; // 子弹 x 坐标
	private int y; // 子弹 y 坐标
	private int xSpeed; // 子弹x轴速度
	private int ySpeed; // 子弹y轴速度
	private Direction direction; // 子弹运动方向
	
	private boolean live = true; // 子弹是否存活
	
	private boolean good = true; // 用于区分敌我的值 true 我方 false 敌方
	
	private TankClient tc; // 坦克客户端引用
	
	/**
	 * 三个参数构造方法
	 * @param x 子弹 x 坐标
	 * @param y 子弹 y 坐标
	 * @param direction 子弹运动方向
	 */
	public Missile(int x, int y, Direction direction, TankClient tc, boolean good) {
		this(x, y, DEFAULT_SPEED, DEFAULT_SPEED, direction, tc, good);
	}

	/**
	 * 五个参数构造方法
	 * @param x 子弹 x 坐标
	 * @param y 子弹 y 坐标
	 * @param xSpeed 子弹x轴速度
	 * @param ySpeed 子弹y轴速度
	 * @param direction 子弹运动方向
	 */
	public Missile(int x, int y, int xSpeed, int ySpeed, Direction direction, TankClient tc, boolean good) {
		this.x = x;
		this.y = y;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.direction = direction;
		this.tc = tc;
		this.good = good;
	}

	/**
	 * 绘制方法
	 * @param g
	 */
	public void draw(Graphics g) {
		// 如果已经没有生命了直接return
		if (!live) {
			return;
		}
		
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
	
	/**
	 * 用于检测子弹是否击中坦克
	 * @param tank 需要检测的坦克
	 * @return 击中返回 true 没击中返回 false
	 */
	public boolean hitTank(Tank tank) {
		if (tank.isLive() && this.getRect().intersects(tank.getRect())) {
			// 设置子弹和坦克的死亡
			this.live = false;
			tank.setLive(false);
			// 新建一个爆炸实例
			Explode explode = new Explode(x, y, tc);
			tc.getExplodeList().add(explode);
			return true;
		}
		return false;
	}
	
	/**
	 * 获得包装在子弹外面的矩形，方便进行碰撞检测
	 * @return Rectangle2D 包裹子弹外边框的矩形
	 */
	public Rectangle2D getRect() {
		return new Rectangle2D.Float(x, y, MISSILE_WIDTH, MISSILE_HEIGHT);
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

	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}
}

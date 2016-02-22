package com.yrh.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import com.yrh.constants.Direction;
import com.yrh.run.TankClient;

/**
 * 坦克实体类
 * 
 * @author Yrh
 *
 */
public class Tank {

	public static final int DEFAULT_SPEED = 1; // 坦克默认速度
	public static final int TANK_WIDTH = 30; // 坦克默认宽度
	public static final int TANK_HEIGHT = 30; // 坦克默认高度
	public static final Color GOOD_TANK_COLOR = Color.RED; // 我方坦克默认颜色
	public static final Color ENEMY_TANK_COLOR = Color.BLUE; // 敌方坦克默认颜色

	private TankClient tc; // 坦克客户端的引用

	private int x; // 坦克 x 坐标
	private int y; // 坦克 y 坐标
	private int xSpeed; // 坦克x轴速度
	private int ySpeed; // 坦克y轴速度
	private boolean good; // 用于区分敌我的值
	private boolean live = true; // 坦克是否存活

	private Direction direction; // 坦克行走方向
	private Direction gunBarrelDirection = Direction.RIGHT; // 用来记录坦克炮筒的方向，默认为向右

	// 用五个boolean值记录用户按键
	private boolean bLeft = false;
	private boolean bRight = false;
	private boolean bUp = false;
	private boolean bDown = false;
	private boolean bFire = false;

	// 用于记录是否可以开火，使开火有一定的间隔
	private boolean canFire = true;
	private int fireSpeed = 1; // 开火速度，默认为1

	// 用数组记录Tank发出的子弹
	private ArrayList<Missile> missileList = new ArrayList<>();

	public Tank(TankClient tc) {
		this(50, 50, DEFAULT_SPEED, DEFAULT_SPEED, true, tc);
	}

	public Tank(int x, int y, boolean good, TankClient tc) {
		this(x, y, DEFAULT_SPEED, DEFAULT_SPEED, good, tc);
	}

	public Tank(int x, int y, int xSpeed, int ySpeed, boolean good, TankClient tc) {
		this(x, y, xSpeed, ySpeed, Direction.STOP, good, tc);
	}

	/**
	 * 七个参数的构造方法
	 * 
	 * @param x
	 *            坦克初始的x坐标
	 * @param y
	 *            坦克初始的y坐标
	 * @param xSpeed
	 *            坦克初始的x轴速度
	 * @param ySpeed
	 *            坦克初始的y轴速度
	 * @param direction
	 *            坦克初始的方向
	 * @param good
	 *            坦克身份
	 * @param tc
	 *            坦克客户端引用
	 */
	public Tank(int x, int y, int xSpeed, int ySpeed, Direction direction, boolean good, TankClient tc) {
		this.x = x;
		this.y = y;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.direction = direction;
		this.good = good;
		this.tc = tc;
	}

	/**
	 * 绘制自身方法
	 * 
	 * @param g
	 *            画笔
	 */
	public void draw(Graphics g) {
		// 绘制所有子弹
		for (int i = 0; i < missileList.size(); i++) {
			if (missileList.get(i).isLive() == false) {
				missileList.remove(i);
			} else {
				missileList.get(i).draw(g);
				// 遍历所有敌方坦克看是否击中
				for (Tank tank : tc.getTankList()) {
					// 自己的子弹不能击中自己
					if (tank != this) {
						missileList.get(i).hitTank(tank);
					}
				}
			}
		}
		
		// 如果已经没有生命了，直接return
		if (!live) {
			return;
		}
		
		// 发射炮弹
		if (bFire) {
			fire();
		}

		// 保存当前颜色
		Color c = g.getColor();
		// 根据敌我标记设置颜色并画圆
		if (good) {
			g.setColor(GOOD_TANK_COLOR);
		} else {
			g.setColor(ENEMY_TANK_COLOR);
		}
		g.fillOval(x, y, TANK_WIDTH, TANK_HEIGHT);
		// 还原颜色
		g.setColor(c);

		// 绘制炮筒
		drawGunBarrel(g);

		// 移动坦克
		move();
	}

	/**
	 * 绘制炮筒
	 * 
	 * @param g
	 *            画笔
	 */
	private void drawGunBarrel(Graphics g) {
		Color c = g.getColor();

		g.setColor(Color.BLACK);
		if (this.gunBarrelDirection == Direction.LEFT) {
			g.drawLine(x + TANK_WIDTH / 2, y + TANK_HEIGHT / 2, x, y + TANK_HEIGHT / 2);
		} else if (this.gunBarrelDirection == Direction.LEFT_UP) {
			g.drawLine(x + TANK_WIDTH / 2, y + TANK_HEIGHT / 2, x, y);
		} else if (this.gunBarrelDirection == Direction.UP) {
			g.drawLine(x + TANK_WIDTH / 2, y + TANK_HEIGHT / 2, x + TANK_WIDTH / 2, y);
		} else if (this.gunBarrelDirection == Direction.RIGHT_UP) {
			g.drawLine(x + TANK_WIDTH / 2, y + TANK_HEIGHT / 2, x + TANK_WIDTH, y);
		} else if (this.gunBarrelDirection == Direction.RIGHT) {
			g.drawLine(x + TANK_WIDTH / 2, y + TANK_HEIGHT / 2, x + TANK_WIDTH, y + TANK_HEIGHT / 2);
		} else if (this.gunBarrelDirection == Direction.RIGHT_DOWN) {
			g.drawLine(x + TANK_WIDTH / 2, y + TANK_HEIGHT / 2, x + TANK_WIDTH, y + TANK_HEIGHT);
		} else if (this.gunBarrelDirection == Direction.DOWN) {
			g.drawLine(x + TANK_WIDTH / 2, y + TANK_HEIGHT / 2, x + TANK_WIDTH / 2, y + TANK_HEIGHT);
		} else if (this.gunBarrelDirection == Direction.LEFT_DOWN) {
			g.drawLine(x + TANK_WIDTH / 2, y + TANK_HEIGHT / 2, x, y + TANK_HEIGHT);
		}

		g.setColor(c);
	}

	/**
	 * 根据方向移动坦克
	 */
	public void move() {
		if (this.direction == Direction.LEFT && x - xSpeed > 0) {
			x -= xSpeed;
		} else if (this.direction == Direction.LEFT_UP && x - xSpeed > 0 && y - ySpeed > Tank.TANK_HEIGHT) {
			x -= xSpeed;
			y -= ySpeed;
		} else if (this.direction == Direction.UP && y - ySpeed > Tank.TANK_HEIGHT) {
			y -= ySpeed;
		} else if (this.direction == Direction.RIGHT_UP && x + xSpeed < TankClient.GAME_WIDTH - Tank.TANK_WIDTH
				&& y - ySpeed > Tank.TANK_HEIGHT) {
			x += xSpeed;
			y -= ySpeed;
		} else if (this.direction == Direction.RIGHT && x + xSpeed < TankClient.GAME_WIDTH - Tank.TANK_WIDTH) {
			x += xSpeed;
		} else if (this.direction == Direction.RIGHT_DOWN && x + xSpeed < TankClient.GAME_WIDTH - Tank.TANK_WIDTH
				&& y + ySpeed < TankClient.GAME_HEIGHT - Tank.TANK_WIDTH) {
			x += xSpeed;
			y += ySpeed;
		} else if (this.direction == Direction.DOWN && y + ySpeed < TankClient.GAME_HEIGHT - Tank.TANK_HEIGHT) {
			y += ySpeed;
		} else if (this.direction == Direction.LEFT_DOWN && x - xSpeed > 0
				&& y + ySpeed < TankClient.GAME_HEIGHT - Tank.TANK_HEIGHT) {
			x -= xSpeed;
			y += ySpeed;
		}
	}

	/**
	 * 处理按键释放方法
	 * 
	 * @param e
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		// 释放空格键发射炮弹
		case KeyEvent.VK_SPACE:
			bFire = false;
			break;

		// 释放左键或者A键
		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			bLeft = false;
			break;

		// 释放右键或者D键
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			bRight = false;
			break;

		// 释放上键或者W键
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			bUp = false;
			break;

		// 释放下键或者S键
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
	 * 
	 * @param e
	 *            按键事件
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		// 点击空格键发射炮弹
		case KeyEvent.VK_SPACE:
			bFire = true;
			break;

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
	 * 创建一颗子弹
	 */
	private void fire() {
		if (canFire) {
			Missile missile = new Missile(x + TANK_WIDTH / 2 - Missile.MISSILE_WIDTH / 2,
					y + TANK_WIDTH / 2 - Missile.MISSILE_HEIGHT / 2, gunBarrelDirection);
			missileList.add(missile);
			// 创建一个设置开火间隔的进程
			new Thread(new FireThread()).start();
		}
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

		if (this.direction != Direction.STOP) {
			this.gunBarrelDirection = this.direction;
		}
	}

	/**
	 * 用于为开火设置间隔的线程
	 * 
	 * @author Yrh
	 *
	 */
	class FireThread implements Runnable {

		@Override
		public void run() {
			canFire = false;
			int sleepTime = fireSpeed * 1000;
			try {
				Thread.sleep(sleepTime);
				canFire = true;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获得包装在坦克外面的矩形，方便进行碰撞检测
	 * 
	 * @return Rectangle2D 包裹坦克外边框的矩形
	 */
	public Rectangle2D getRect() {
		return new Rectangle2D.Float(x, y, TANK_WIDTH, TANK_HEIGHT);
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

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public ArrayList<Missile> getMissileList() {
		return missileList;
	}
}

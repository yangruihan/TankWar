package com.yrh.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
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

	private int x; // 坦克 x 坐标
	private int y; // 坦克 y 坐标
	private int xSpeed; // 坦克x轴速度
	private int ySpeed; // 坦克y轴速度

	private Direction direction; // 坦克行走方向
	private Direction gunBarrelDirection = Direction.RIGHT; // 用来记录坦克炮筒的方向，默认为向右

	// 用四个boolean值记录用户按键
	private boolean bLeft = false;
	private boolean bRight = false;
	private boolean bUp = false;
	private boolean bDown = false;

	// 用于记录是否可以开火，使开火有一定的间隔
	private boolean canFire = true;
	private int fireSpeed = 1; // 开火速度，默认为1

	// 用数组记录Tank发出的子弹
	private ArrayList<Missile> missileList = new ArrayList<>();

	/**
	 * 默认构造方法
	 */
	public Tank() {
		this(50, 50, DEFAULT_SPEED, DEFAULT_SPEED);
	}

	/**
	 * 带两个参数的构造方法
	 * 
	 * @param x
	 *            坦克出生的x坐标
	 * @param y
	 *            坦克出生的y坐标
	 */
	public Tank(int x, int y) {
		this(x, y, DEFAULT_SPEED, DEFAULT_SPEED);
	}

	/**
	 * 带四个参数的构造方法
	 * 
	 * @param x
	 *            坦克出生的x坐标
	 * @param y
	 *            坦克出生的y坐标
	 * @param xSpeed
	 *            坦克出生的x轴速度
	 * @param ySpeed
	 *            坦克出生的x轴速度
	 */
	public Tank(int x, int y, int xSpeed, int ySpeed) {
		this(x, y, xSpeed, ySpeed, Direction.STOP);
	}

	/**
	 * 带四个参数的构造方法
	 * 
	 * @param x
	 *            坦克出生的x坐标
	 * @param y
	 *            坦克出生的y坐标
	 * @param xSpeed
	 *            坦克出生的x轴速度
	 * @param ySpeed
	 *            坦克出生的x轴速度
	 * @param direction
	 *            坦克出生的方向
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
	 * 
	 * @param g
	 *            画笔
	 */
	public void draw(Graphics g) {
		// 移动坦克
		move();

		// 保存当前颜色
		Color c = g.getColor();
		// 设置颜色并画圆
		g.setColor(Color.RED);
		g.fillOval(x, y, TANK_WIDTH, TANK_HEIGHT);
		// 还原颜色
		g.setColor(c);

		// 绘制炮筒
		drawGunBarrel(g);

		// 绘制所有子弹
		ArrayList<Missile> willDestoryMissileList = new ArrayList<>();
		for (Missile missile : missileList) {
			// 如果子弹已经飞出屏幕，则记录将清除
			if (missile.getX() < 0 || missile.getX() > TankClient.GAME_WIDTH || missile.getY() < 0
					|| missile.getY() > TankClient.GAME_HEIGHT) {
				willDestoryMissileList.add(missile);
			} else {
				missile.draw(g);
			}
		}

		// 清除子弹
		for (Missile missile : willDestoryMissileList) {
			missileList.remove(missile);
			missile = null;
		}
		willDestoryMissileList.clear();
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
	 * 
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
	 * 
	 * @param e
	 *            按键事件
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		// 点击空格键发射炮弹
		case KeyEvent.VK_SPACE:
			// 创建子弹
			fire();
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

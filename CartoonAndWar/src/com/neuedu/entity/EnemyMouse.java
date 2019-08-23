package com.neuedu.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import javax.crypto.spec.RC2ParameterSpec;

import com.neuedu.action.ActionAble;
import com.neuedu.client.GameClient;
import com.neuedu.util.GetImageUtil;

/**
* @ClassName: EnemyMouse
* @Description: 敌鼠类
* @author neuedu_wangying
* @date 2019年8月19日 下午9:50:53
*
*/
public class EnemyMouse extends Mouse implements ActionAble{
	private Integer enemyType;
	
	private Integer speed;
	
	private GameClient gc;
	

	public static Image[] imgs1 = {
		GetImageUtil.getImg("enemy/BOSS_01.png"),	
		GetImageUtil.getImg("enemy/BOSS_02.png"),
		GetImageUtil.getImg("enemy/BOSS_03.png"),
		GetImageUtil.getImg("enemy/BOSS_04.png"),
		GetImageUtil.getImg("enemy/BOSS_05.png"),
	};
	public EnemyMouse() {
		
	}
	public EnemyMouse(int x,int y,int enemyType,GameClient gc,boolean isGood) {
		this.x = x;
		this.y = y;
		this.enemyType = enemyType;
		this.speed = 2;
		this.gc = gc;
		this.isGood = isGood;
	}
	@Override
	public void move() {
		x -= speed;	
		
		}
	int count = 0;
	@Override
	public void draw(Graphics g) {
		if(count>4) {
			count = 0;
		}
		g.drawImage(imgs1[count++], x, y, null);
		move();
		
		if (random.nextInt(500)>490) {
			fire();
		}
//		fire();
	}
	// 随机数
	Random random = new Random();
	// 敌军发火
	public void fire() {
		
		Bullet bullet = new Bullet(x, y+imgs1[0].getHeight(null)/2+20, "bullet/enemyplane_03_bullet.png", gc,false);
		gc.bullets.add(bullet);
	}
	
	// 获取到子弹的矩形
		public Rectangle getRec() {
			return new Rectangle(x,y,this.imgs1[0].getWidth(null),this.imgs1[0].getHeight(null));
		}
}

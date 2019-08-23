package com.neuedu.entity;

import java.awt.Graphics;
import java.awt.List;
import java.awt.Rectangle;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import com.neuedu.action.ActionAble;
import com.neuedu.client.GameClient;
import com.neuedu.constant.Constant;
import com.neuedu.util.GetImageUtil;
import com.neuedu.util.SinglePlay;

/**
* @ClassName: Bullet
* @Description: 子弹类
* @author neuedu_wangying
* @date 2019年8月19日 下午5:03:22
*
*/
public class Bullet extends GameObj implements ActionAble{
	
	// 单次播放音乐的对象
	SinglePlay singlePlay = new SinglePlay();
	
	// 创建速度属性
	private Integer speed;
	// 拿到客户端
	public GameClient gc;
	// 子弹类型
	public boolean isGood;
	
	public Bullet() {
		
	}
	public Bullet(int x,int y,String imgName,GameClient gc,boolean isGood) {
		this.x = x;
		this.y = y;
		this.img = GetImageUtil.getImg(imgName);
		this.speed =50;
		this.gc = gc;
		this.isGood = isGood;
	}
	@Override
	public void move() {
		if (isGood) {
			x += speed;
		}else {
			x -= speed;
		}
		
		
	}
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, x, y,null);
		move();
		outOfBounds();
	}
	// 子弹越界销毁
	public void outOfBounds() {
		if (x>Constant.GAME_WIDTH||x<0) {
			gc.bullets.remove(this);
		}
	}
	// 随机生成道具
	Random random = new Random();
	
	
	// 子弹打一个怪物
	public boolean hitMonster(Mouse mouse) {
		Boom boom = new Boom(mouse.x,mouse.y,gc, false);
		if (this.getRec().intersects(mouse.getRec())&&this.isGood!=mouse.isGood) {
			if (mouse.isGood) {
				singlePlay.play("com/neuedu/sound/SOUND_ENEMY_CHARGEDEATH_01.mp3");
				mouse.blood -= 10;
				if (mouse.blood == 0) {
					System.out.println("销毁自身");
				// 销毁自身
				gc.mouses.remove(mouse);
				
				}
				// 移除子弹
				gc.bullets.remove(this);
			}else {
				singlePlay.play("com/neuedu/sound/SOUND_INGAMEITEM_COIN_01.mp3");
				if (mouse instanceof Boss) {
					mouse.blood -= 100;
					if (mouse.blood <= 0) {
						gc.bosss.remove(mouse);
					// 移除子弹
					gc.bullets.remove(this);
					}
					
				}else {
					// 移除打中敌人
					gc.enemys.remove(mouse);
					// 移除子弹
					gc.bullets.remove(this);
					// 随机生成一个道具出来
					if (random.nextInt(500)>400) {
						if (mouse instanceof EnemyMouse) {
							EnemyMouse enemyMouse = (EnemyMouse)mouse;
							Prop prop = new Prop(mouse.x+EnemyMouse.imgs1[0].getWidth(null)/2,mouse.y+EnemyMouse.imgs1[0].getHeight(null)/2,"prop/prop.png");
							gc.props.add(prop);
						}
						
					}
					
				}
			}
				
				
			// 添加爆炸
				gc.booms.add(boom);
			return true;
		}

		return false;
	}
	// 子弹打多个怪物
	public boolean hitMonsters (ArrayList<Mouse> monsters) {
		if (monsters==null) {
			return false;
		}
		for (int i = 0; i < monsters.size(); i++) {
			if (hitMonster(monsters.get(i))) {
				return true;
			}
		}
		return false;
	}
	// 获取到子弹的矩形
	public Rectangle getRec() {
		return new Rectangle(x,y,this.img.getWidth(null),this.img.getHeight(null));
	}
	
}

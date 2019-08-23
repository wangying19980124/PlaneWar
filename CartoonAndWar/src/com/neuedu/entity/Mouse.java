package com.neuedu.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.neuedu.action.ActionAble;
import com.neuedu.client.GameClient;
import com.neuedu.constant.Constant;
import com.neuedu.util.GetImageUtil;
import com.neuedu.util.SinglePlay;

/**
* @ClassName: Mouse
* @Description: ����һ��������
* @author neuedu_wangying
* @date 2019��8��18�� ����10:11:40
*
*/
public class Mouse extends GameObj implements ActionAble{
	SinglePlay play = new SinglePlay();
	// �ٶ�
	private int speed;
	// ���򲼶�����
	private boolean left,up,right,down;
	// �ͻ����ù���
	public GameClient gc;
	// �ж��Ƿ����Ҿ����ǵо�
	public boolean isGood;
	// ��ӷɻ��ӵ��ȼ�����
	public int BulletLevel = 1;
	// ���Ѫֵ
	public int blood;
	// �ж��Ƿ���
	public boolean isLive;
	public Mouse() {
		
	}
	public Mouse(int x,int y,String imgName,GameClient gc,boolean isGood,boolean isLive)
	{
		this.x = x;
		this.y = y;
		this.img = GetImageUtil.getImg(imgName);
		this.speed = 20;
		this.gc = gc;
		this.isGood = isGood;
		this.blood = 100;
		this.isLive = isGood;
	}
	// �ƶ��ķ���
	@Override
	public void move() {
		if (left) {
			x -= speed;
		}
		if(up) {
			y -= speed;
		}
		if(right) {
			x += speed;
		}
		if (down) {
			y += speed;
		}
		outOfBound();
	}
	// ��һ���������
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, x, y, null);
		move();
		g.drawString("��ǰѪ����"+blood,10,200);
	}
	// �������ı߽�����
	public void outOfBound() {
		if (y<=20) {
			y = 20;
		}
		if (x<=5) {
			x = 5;
		}
		if (x>=Constant.GAME_WIDTH - img.getWidth(null)) {
			x = Constant.GAME_WIDTH - img.getWidth(null);
		}
		if (y>=Constant.GAME_HEIGHT - img.getHeight(null)) {
			y=Constant.GAME_HEIGHT - img.getHeight(null);
		}
	}
	// �������
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			left = true;
			break;
		case KeyEvent.VK_W:
			up = true;
			break;
		case KeyEvent.VK_D:
			right = true;
			break;
		case KeyEvent.VK_S:
			down = true;
			break;
		
		default:
			break;
		}
	}
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			left = false;
			break;
		case KeyEvent.VK_W:
			up = false;
			break;
		case KeyEvent.VK_D:
			right = false;
			break;
		case KeyEvent.VK_S:
			down = false;
			break;
		case KeyEvent.VK_M:
				fire();
			break;
		default:
			break;
		}
	}
	// �ҷ�����Ŀ���
	public void fire() {
		play.play("com/neuedu/sound/SOUND_INGAMEITEM_POWERUP_02.mp3");
		Bullet b = new Bullet(x+this.img.getWidth(null)/2,y+this.img.getHeight(null)/2-20,"bullet/BULLET_CHAR_SOPHIA_0"+BulletLevel+".png",gc,true);
		gc.bullets.add(b);
	}
	// ��ȡ���ӵ��ľ���
		public Rectangle getRec() {
			return new Rectangle(x,y,this.img.getWidth(null),this.img.getHeight(null));
		}
	// ����ҷ���ɫ��ײ������
		public void containItem(Prop prop) {
			if (this.getRec().intersects(prop.getRect())) {
				// �Ƴ�����
				gc.props.remove(prop);
				if (BulletLevel>7) {
					BulletLevel = 8;
					return;
				}
				// �����ӵ��ȼ�
				this.BulletLevel += 1;
			}
		}
		// ����ҷ���ɫ��ײ�������
		public void containItems(ArrayList<Prop> props) {
			if (props==null) {
				return;
			}else {
				for (int i=0;i<props.size();i++) {
					containItem(props.get(i));
				}
			}
		}
}

package com.neuedu.util;

import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
* @ClassName: SoundPlayer
* @Description: 播放背景音乐
* @author neuedu_wangying
* @date 2019年8月20日 下午9:22:34
*
*/
public class SoundPlayer extends Thread {
	
	private String mp3Name;
	
	public SoundPlayer() {
		
	}
	public SoundPlayer(String mp3Name) {
		this.mp3Name = mp3Name;
	}
	@Override
	public void run() {
		for (;;) {
			InputStream resourceAsStream = SoundPlayer.class.getClassLoader().getResourceAsStream(mp3Name);	
			try {
				AdvancedPlayer advancedPlayer = new AdvancedPlayer(resourceAsStream);
				advancedPlayer.play();
			} catch (JavaLayerException e) {
				e.printStackTrace();
			}
			}	
	}
	// 内部类

}

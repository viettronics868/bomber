package com.namdungphuong.bomber.Quaivat;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;
import android.os.SystemClock;

import com.namdungphuong.bomber.MainGame.MainGameActivity;
import com.namdungphuong.bomber.Tools.Tools;

public class Quaivat_1 //extends Quaivat
{
	//Biến tạo hiệu ứng
	public AnimatedSprite Quaivat_1_AnimatedSprite;
	
	private Scene mScene;
	
	//Xác định tốc độ di chuyển của các quái vật
	public int speed = 2;
	
	//Khai báo đối tượng quái vật
	private Quaivat quaivat;
	
	//Xác định xem quái vật này là loại nào. Hiện tại có 7 loại
	private int loai = 0;

	private Context mContext;
	//=============================================================================
	/**
	 * Phương thức khởi dựng có tham số. 
	 * @param quaivat
	 * @param mScene
	 * @param pX
	 * @param pY
	 * @param Quaivat_TiledTextureRegion
	 */
	public Quaivat_1(Quaivat quaivat,
					 Scene mScene,
					 float pX,
					 float pY,
					 TiledTextureRegion Quaivat_TiledTextureRegion)
	{
		this.mContext= mContext;
		this.quaivat = quaivat;
		this.mScene = mScene;

		loai = Tools.getRandomIndex(0, 6);
		Quaivat_1_AnimatedSprite = new AnimatedSprite(
				pX,
				pY,
				Quaivat_TiledTextureRegion,
				((MainGameActivity)mContext).getVertexBufferObjectManager());
		statusMoveLeft();//Ban đầu thì toàn bộ các quái vật sẽ di chuyển sang hướng trái
		mScene.attachChild(Quaivat_1_AnimatedSprite);
	}
	//==========================================================================
	/**
	 * Phương thức statusMoveUp
	 */
	public void statusMoveUp(){
		if(loai == 0)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},3,5,true);
		else if(loai == 1)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},6,8,true);
		else if(loai == 2)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},9,11,true);
		else if(loai == 3)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},48,50,true);
		else if(loai == 4)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},51,53,true);
		else if(loai == 5)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},54,56,true);
		else if(loai == 6)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},57,59,true);
	}
	//=============================================================================
	/**
	 * Phương thức statusMoveRight
	 */
	public void statusMoveRight(){
		if(loai == 0)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},15,17,true);
		else if(loai == 1)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},18,20,true);
		else if(loai == 2)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},21,23,true);
		else if(loai == 3)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},60,62,true);
		else if(loai == 4)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},63,65,true);
		else if(loai == 5)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},66,68,true);
		else if(loai == 6)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},69,71,true);
	}
	//=============================================================================
	/**
	 * Phương thức statusMoveDown
	 */
	public void statusMoveDown(){
		if(loai == 0)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},27,29,true);
		else if(loai == 1)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},30,32,true);
		else if(loai == 2)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},33,35,true);
		else if(loai == 3)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},72,74,true);
		else if(loai == 4)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},75,77,true);
		else if(loai == 5)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},78,80,true);
		else if(loai == 6)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},81,83,true);
	}
	//=============================================================================
	/**
	 * Phương thức statusMoveLeft
	 */
	public void statusMoveLeft(){
		if(loai == 0)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},39,41,true);
		else if(loai == 1)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},42,44,true);
		else if(loai == 2)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},45,47,true);
		else if(loai == 3)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},84,86,true);
		else if(loai == 4)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},87,89,true);
		else if(loai == 5)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},90,92,true);
		else if(loai == 6)
			Quaivat_1_AnimatedSprite.animate(new long[]{100,100,100},93,95,true);
	}
	//=============================================================================
	/**
	 * Di chuyển quái vật tới vị trí pX, pY nếu mà không có va chạm với vật cản
	 */
	public void moveXY(float pX, float pY){
		if(!quaivat.collidesWith(pX, pY))
			Quaivat_1_AnimatedSprite.setPosition(pX, pY);
	}
	//=============================================================================
	public void moveRelativeXY(float pX, float pY){
		Quaivat_1_AnimatedSprite.setPosition(Quaivat_1_AnimatedSprite.getX()+pX, Quaivat_1_AnimatedSprite.getY()+pY);
	}
	//=============================================================================
	/**
	 * Di chuyển 1 cách ngẫu nhiên trên màn hình
	 */
	long time = SystemClock.elapsedRealtime();
	int huong = 0;
	public void moveRandom(){
		if(SystemClock.elapsedRealtime() - time > 3000){//Cứ sau 3s thì đối tượng quái vật tự xác định lại đường đi
			huong = Tools.getRandomIndex(0, 3);
			time = SystemClock.elapsedRealtime();
			if(huong == 0)
				statusMoveLeft();
			else if(huong == 1)
				statusMoveRight();
			else if(huong == 2)
				statusMoveUp();
			else if(huong == 3)
				statusMoveDown();
		}
		
		if(huong == 0){//Trái
			if(!quaivat.collidesWith(Quaivat_1_AnimatedSprite.getX() - speed, Quaivat_1_AnimatedSprite.getY()+(Quaivat_1_AnimatedSprite.getHeight()/2)))
				moveRelativeXY(-speed,0);
		}else if(huong == 1){//Phải
			if(!quaivat.collidesWith(Quaivat_1_AnimatedSprite.getX()+ Quaivat_1_AnimatedSprite.getWidth() + speed, Quaivat_1_AnimatedSprite.getY()+(Quaivat_1_AnimatedSprite.getHeight()/2)))
				moveRelativeXY(speed,0);
		}else if(huong == 2){//Lên
			if(!quaivat.collidesWith(Quaivat_1_AnimatedSprite.getX() + (Quaivat_1_AnimatedSprite.getWidth()/2), Quaivat_1_AnimatedSprite.getY() - speed))
				moveRelativeXY(0,-speed);
		}else if(huong == 3){//Xuống
			if(!quaivat.collidesWith(Quaivat_1_AnimatedSprite.getX() + + (Quaivat_1_AnimatedSprite.getWidth()/2), Quaivat_1_AnimatedSprite.getY() + Quaivat_1_AnimatedSprite.getWidth() + speed))
				moveRelativeXY(0,speed);
		}
	}
	//=============================================================================
	/**
	 * Xóa bỏ quái vật
	 */
	public void deleteQuaivat_1(){
		this.mScene.detachChild(Quaivat_1_AnimatedSprite);
	}
	//============================================================================
	long time_reset_begin = 0;
	public boolean bool_reset = false;
	public void reset(){
		if(bool_reset){
			if(!this.Quaivat_1_AnimatedSprite.isVisible() && time_reset_begin == 0)
				time_reset_begin = SystemClock.elapsedRealtime();
			
			if(time_reset_begin != 0 && SystemClock.elapsedRealtime() - time_reset_begin > 5000){
				//Khởi tạo lại quái vật tại vị trí ban đầu và cho hiện thị
				this.Quaivat_1_AnimatedSprite.setPosition(416,128);
				this.Quaivat_1_AnimatedSprite.setVisible(true);
				time_reset_begin = 0;
				bool_reset = false;
			}
		}
	}
}

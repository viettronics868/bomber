package com.namdungphuong.bomber.Other;

import java.io.IOException;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.debug.Debug;

import com.namdungphuong.bomber.ClassStatic.ControlerStatic;
import com.namdungphuong.bomber.InterfaceSprite.InterfaceSprite;
import com.namdungphuong.bomber.MainGame.MainGameActivity;
import com.namdungphuong.bomber.Player.Player;
import com.namdungphuong.bomber.Tools.Tools;
import android.content.Context;
import android.os.SystemClock;

public class Hopqua implements InterfaceSprite{

	 private BitmapTextureAtlas HopquaBitmapTextureAtlas;
	 private TextureRegion HopquaTextureRegion;
	 private Sprite HopquaSprite;
	 
	 private Sound sound_thuong;
	 
	 public Hopqua(){}
	
	@Override
	public void onLoadResources(Engine mEngine, Context mContext) {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Image/");
        this.HopquaBitmapTextureAtlas = new BitmapTextureAtlas(
				((MainGameActivity)mContext).getTextureManager(),
				256,
				256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.HopquaTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.HopquaBitmapTextureAtlas, mContext, "hopqua.png", 0, 0);
        //mEngine.getTextureManager().loadTexture(this.HopquaBitmapTextureAtlas);
		((MainGameActivity)mContext).getTextureManager().loadTexture(this.HopquaBitmapTextureAtlas);
        
        SoundFactory.setAssetBasePath("mfx/");
        try {
        	sound_thuong = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mContext, "thuong.wav");
        } catch (final IOException e) {
                Debug.e(e);
        }
		
	}

	@Override
	public void onLoadScene(Scene mScene) {		
		this.HopquaSprite = new Sprite(
				-100,
				-100,
				this.HopquaTextureRegion,
				((MainGameActivity)mContext).getVertexBufferObjectManager());
		mScene.attachChild(HopquaSprite);
		mScene.registerTouchArea(HopquaSprite);
	}

	long time_random = 0, time_start = 0;
	int loai = -1;//Cho biết hộp quà là gì:
	long time_start_ht = 0;//Xác định thời gian sẽ hiện thị hộp quà
	long time_end_ht = 0;//Sau bao nhiêu s thì ẩn hộp quà
	// 1: Thêm 1 mạng
	// 2: Thêm 1 quả bom
	// 3: Tăng thêm 1 cấp bom nữa
	public void hienThi(TMXTiledMap mTMXTiledMap, TMXLayer VatCanTMXLayer){
		if(time_random == 0){//Lần đầu tiên gọi hàm
			//Xác định thời gian chờ để xuất hiện
			time_random = Tools.getRandomIndex(5000, 20000);//Trong khoảng 5-20s sẽ hiện thị 1 lần
			loai = Tools.getRandomIndex(1, 3);
			time_start = SystemClock.elapsedRealtime();
		}else{
			if(SystemClock.elapsedRealtime() - time_start > time_random && time_start != 0){				
				//Hiện thị hộp quà
				//Tìm vị trí
				while(true && time_start_ht == 0){
					int x = Tools.getRandomIndex(192, 416);
					int y = Tools.getRandomIndex(64, 256);
					
					if(!collidesWith(mTMXTiledMap,VatCanTMXLayer,x+HopquaSprite.getWidth()/2,y+HopquaSprite.getHeight()/2)){
						//Được phép hiện thị. 
						//Di chuyển hộp quà tới vị trí
						HopquaSprite.setPosition(x,y);			
						
						//Xác định thời gian hiện thị của hộp quà
						time_start_ht = SystemClock.elapsedRealtime();
						time_end_ht = Tools.getRandomIndex(3000, 10000);//Thời gian hiện thị nằm trong khoảng từ 3s-10s
						break;
					}
				}
				HopquaSprite.setRotation(10);
				//Bắt đầu tính thời gian hiện thị
				if(time_start_ht != 0 && SystemClock.elapsedRealtime() - time_start_ht > time_end_ht){
					//Hết thời gian hiện thị của hộp quà, ta di chuyển hộp quà đến vị trí ngoài màn hình
					HopquaSprite.setPosition(-100,-100);
					
					//Tiếp đó là ta xác định lại thời gian chờ cho hộp quà
					time_random = Tools.getRandomIndex(5000, 20000);//Trong khoảng 5-20s sẽ hiện thị 1 lần
					loai = Tools.getRandomIndex(1, 3);
					time_start = SystemClock.elapsedRealtime();
					
					time_start_ht = 0;
					time_end_ht = 0;
				}
			}
		}
	}
	
	//Phương thức kiểm tra xem player có va chạm với hộp quà trong thời gian hộp quà còn xuất hiện trên màn hình 
	//hay không. Nếu có va chạm thì tác xác định xem hộp quà là loại gì và tăng các giá trị đó cho player
	
	public void collidesWith(Player player){
		if(HopquaSprite.getX() > 0 && player.getAnimatedSprite().collidesWith(HopquaSprite)){
			if(ControlerStatic.SOUND)
				sound_thuong.play();
			
			//Có xự va chạm giữa hộp quà và player
			if(this.loai == 1){//Thêm 1 mạng
				//Giới hạn số mạng là nhỏ hơn 11
				if(player.getHeart() < 10)
					player.setHeart(player.getHeart()+1);
				System.out.println("Tăng thêm mạng");
			}else if(loai == 2){//Thêm 1 quả bom
				//Không thêm vượt quá 10 quả bom
				if(player.current_quabom < 10)
					player.setCurrent_quabom(player.getCurrent_quabom() + 1);
				System.out.println("Thêm bom");
			}else if(loai ==3 ){//Tăng 1 cấp bom
				//Không tăng vượt quá cấp 5
				if(player.getCurrent_cap_quabom() < 5)
					player.setCurrent_cap_quabom(player.getCurrent_cap_quabom() + 1);
				System.out.println("Tăng cấp bom");
			}
			
			HopquaSprite.setPosition(-100,-100);
			
			//Tiếp đó là ta xác định lại thời gian chờ cho hộp quà
			time_random = Tools.getRandomIndex(5000, 20000);//Trong khoảng 5-20s sẽ hiện thị 1 lần
			loai = Tools.getRandomIndex(1, 3);
			time_start = SystemClock.elapsedRealtime();
		}
	}
	public int getLoai(){
		return loai;
	}
	
	public boolean collidesWith(TMXTiledMap mTMXTiledMap, TMXLayer VatCanTMXLayer, float pX, float pY){
		TMXTile mTMXTile = VatCanTMXLayer.getTMXTileAt(pX,pY);
    	try{
			if(mTMXTile == null){
			}
			else{
				TMXProperties<TMXTileProperty> mTMXProperties= mTMXTile.getTMXTileProperties(mTMXTiledMap);
				TMXTileProperty mTMXTileProperty = mTMXProperties.get(0);
				if(mTMXTileProperty.getName().equals("vatcan")){
				}
			}
			
			return true;
		}catch(Exception e){
			return false;
		}
	}
}

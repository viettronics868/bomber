package com.namdungphuong.bomber.Quaivat;

import org.andengine.engine.Engine;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import com.namdungphuong.bomber.InterfaceSprite.InterfaceSprite;
import com.namdungphuong.bomber.MainGame.MainGameActivity;
import com.namdungphuong.bomber.Tools.Tools;
import android.content.Context;

public class Quaivat implements InterfaceSprite{

	//Xác định mức tối da mà số quái vật có.
	public int max_quai = 10; 
	
	//Cấp phát 1 array chứa toàn bộ các quái.
	public Quaivat_1[] quaivat_1;
	
	//Các biến load và lưu ảnh
	private TiledTextureRegion[] Quaivat_TiledTextureRegion;
	private BitmapTextureAtlas Quaivat_BitmapTextureAtlas;	
	

	//Maps
	private TMXTiledMap mTMXTiledMap;
	private TMXLayer VatCanTMXLayer;
	
	private Scene mScene;
	private Context mContext;
	//==================================================================================
	/**
	 * Phương thức khởi dựng không có tham số
	 */
	public Quaivat(int max_quai){
		this.max_quai = max_quai;
		quaivat_1 = new Quaivat_1[this.max_quai];
		Quaivat_TiledTextureRegion = new TiledTextureRegion[this.max_quai];
		this.mContext= mContext;
	}
	//==================================================================================
	/**
	 * Phương thức onLoadResources
	 */
	@Override
	public void onLoadResources(Engine mEngine, Context mContext) {
		this.Quaivat_BitmapTextureAtlas = new BitmapTextureAtlas(
				((MainGameActivity)mContext).getTextureManager(),
				512,
				512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Quaivat/");		
		for(int i=0;i<this.max_quai;i++)
			this.Quaivat_TiledTextureRegion[i] = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.Quaivat_BitmapTextureAtlas, mContext, "quai_vat.png", 0, 0, 12, 8);
		//mEngine.getTextureManager().loadTexture(this.Quaivat_BitmapTextureAtlas);
		((MainGameActivity)mContext).getTextureManager().loadTexture(this.Quaivat_BitmapTextureAtlas);
			
	}
	//==================================================================================
	/**
	 * Phương thức onLoadScene
	 */
	@Override
	public void onLoadScene(Scene mScene) {
		this.mScene = mScene;
		for(int i=0;i<this.max_quai;i++){	
			quaivat_1[i] = new Quaivat_1(this, mScene,-100,-100,this.Quaivat_TiledTextureRegion[i]);
		}
			
	}
	
	/**
	 * Khởi tạo vị trí ngẫu nhiên
	 */
	public void reset(){
		for(int i=0;i<this.max_quai;i++){			
			while(true){
				int x = Tools.getRandomIndex(192, 416);
				int y = Tools.getRandomIndex(64, 256);
				if(!this.collidesWith(x, y)){
					quaivat_1[i] = new Quaivat_1(this, this.mScene,x,y,this.Quaivat_TiledTextureRegion[i]);
					break;
					}				
			}
		}
	}
	
	//==================================================================================
	/**
	 * Phương thức set TMXTiledMap
	 */
	public void setTMXTiledMap(TMXTiledMap mTMXTiledMap){
		this.mTMXTiledMap = mTMXTiledMap;
	}
	//==================================================================================
	/**
	 * Phương thức set TMXLayer
	 */
	public void setTMXLayer(TMXLayer VatCanTMXLayer){
		this.VatCanTMXLayer = VatCanTMXLayer;
	}
	//==================================================================================
	/**
	 * Phương thức kiểm tra xem tại vị trí pX, pY có phải thuộc vào vùng không được di chuyển.
	 * Nếu thuộc vùng không được di chuyển thì return true.
	 * Nếu không thuộc thì return false. (Đối với cả player và quái vật đều được di chuyển nếu điều return false)
	 */
	public boolean collidesWith(float pX, float pY){
		TMXTile mTMXTile = VatCanTMXLayer.getTMXTileAt(pX,pY);
    	try{
			if(mTMXTile == null){
				System.out.println("mTMXTile = null");
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
	//==================================================================================
	//Khi quái vật bị chết thì chờ trong thời gian là 5s sẽ được hiện thị lại.
	public void reset(Quaivat_1 quai_vat_1){
		quai_vat_1.Quaivat_1_AnimatedSprite.setVisible(true);
		quai_vat_1.Quaivat_1_AnimatedSprite.setPosition(416,128);
	}
}

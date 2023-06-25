package com.namdungphuong.bomber.Bom;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import com.namdungphuong.bomber.InterfaceSprite.InterfaceSprite;
import com.namdungphuong.bomber.MainGame.MainGameActivity;

import android.content.Context;

public class No implements InterfaceSprite{
	
	//Cho biết đối tượng này được hiện thị hay không được hiện thị.
	//Nếu visiable = true thì được hiện thị. Ngược lại là ẩn
	public boolean visiable = true;
	
	//2 giá trị là vị trí của đối tượng nổ
	public float pX = 0, pY = 0;
	
	//Biến này để tạo ra hiệu ứng nổ
	public AnimatedSprite No_AnimatedSprite;
	
	//Các biến dùng để load ảnh và lưu ảnh vào bộ nhớ.
	private TiledTextureRegion No_TiledTextureRegion;
	private BitmapTextureAtlas No_BitmapTextureAtlas;	
	
	//==========================================================================================
	/**
	 * Phương thức khởi dựng
	 * @param pX : vị trí hiện thị của đối tượng Nổ
	 * @param pY : vị trí hiện thị của đối tượng Nổ
	 */
	public No(float pX, float pY){
		this.pX = pX;
		this.pY = pY;
	}
	//==========================================================================================
	/**
	 * Phương thức onLoadResources
	 */
	@Override
	public void onLoadResources(Engine mEngine, Context mContext) {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Bom/");
		this.No_BitmapTextureAtlas = new BitmapTextureAtlas(
				((MainGameActivity)mContext).getTextureManager(),
				256,
				256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.No_TiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.No_BitmapTextureAtlas, mContext, "no.png", 0, 0, 5, 5);
		//mEngine.getTextureManager().loadTexture(this.No_BitmapTextureAtlas);
		((MainGameActivity)mContext).getTextureManager().loadTexture(this.No_BitmapTextureAtlas);
		
	}

	//==========================================================================================
	/**
	 * Phương thức onLoadScene
	 */
	@Override
	public void onLoadScene(Scene mScene) {
		No_AnimatedSprite = new AnimatedSprite(
				pX,
				pY,
				No_TiledTextureRegion,
				((MainGameActivity)mContext).getVertexBufferObjectManager());
		No_AnimatedSprite.animate(60);
		mScene.attachChild(No_AnimatedSprite);
		
	}
	//==========================================================================================
	/**
	 * Phương thức sẽ di chuyển đối tượng nổ đến vị trí pX, pY. Khi di chuyển đến vị trí đó mặc định ban đầu là trạng
	 * thái ẩn.
	 */
	public void moveNewXY(float pX, float pY){
		this.pX = pX;
		this.pY = pY;
		No_AnimatedSprite.setPosition(pX, pY);
		No_AnimatedSprite.setVisible(false);//Đặt trạng thái ẩn
	}
}

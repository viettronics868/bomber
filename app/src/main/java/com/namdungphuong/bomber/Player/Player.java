package com.namdungphuong.bomber.Player;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import com.namdungphuong.bomber.Bom.QuaBom;
import com.namdungphuong.bomber.InterfaceSprite.InterfaceSprite;
import com.namdungphuong.bomber.MainGame.MainGameActivity;

import android.content.Context;

public class Player implements InterfaceSprite{

	private int STATUS_PALYER = StatusPlayer.UN_MOVE_DOWN;//Trạng thái ban đầu của nhân vật là nhìn về phía trước
	
	//Tạo hiệu ứng khi di chuyển cho player
	public AnimatedSprite player_AnimatedSprite;
	
	//Các biến lưu và load ảnh
	private TiledTextureRegion player_TiledTextureRegion;
	private BitmapTextureAtlas player_BitmapTextureAtlas;

	private int heart = 3;//Số mạng của player. Mặc định ban đầu là có 3 lượt chơi
	
	//Vị trí của Player
	private float positionX = 0;
	private float positionY = 0;
	
	private int max_quabom = 10;//Tổng số quả bom mà player có thể có được.
	public int current_quabom = 1;// Số lượng quả bom mà player hiện có. Mặc định  = 1 quả
	private int max_cap_quabom = 5;//Là cấp độ lớn nhất mà 1 quả bom có được.
	public int current_cap_quabom = 1;//Là cấp độ bom hiện tại của Player. Mặc định là cấp 1
	
	//Danh sách qua bom
	public QuaBom[] MyQuaBom;//Khai báo với mảng với số lượng bom tối đa
	//=======================================|| Player ||================================
	/**
	 * Phương thức khởi dựng không có tham số.
	 * @max_quabom : Tổng số quả bom mà player có thể có được.
	 * @current_quabom : Số lượng quả bom mà player hiện có. Mặc định  = 1 quả
	 * @max_cap_quabom : Là cấp độ lớn nhất mà 1 quả bom có được.
	 * @current_cap_quabom : Là cấp độ bom hiện tại của Player. Mặc định là cấp 1
	 */
	public Player(int max_quabom,int current_quabom, int max_cap_quabom, int current_cap_quabom){
		this.max_quabom = max_quabom;
		this.current_quabom = current_quabom;
		this.max_cap_quabom = max_cap_quabom;
		this.current_cap_quabom = current_cap_quabom;
		MyQuaBom = new QuaBom[this.max_quabom];

	}
	
	//=======================================|| onLoadResources ||================================
	/**
	 * Phương thức onLoadResources
	 */
	@Override
	public void onLoadResources(Engine mEngine, Context mContext) {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Player/");	
		this.player_BitmapTextureAtlas = new BitmapTextureAtlas(
				((MainGameActivity)mContext).getTextureManager(),
				512,
				512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.player_TiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.player_BitmapTextureAtlas, mContext, "nhan_vat_chinh.png", 0, 0, 9, 8);
		//mEngine.getTextureManager().loadTexture(this.player_BitmapTextureAtlas);
		((MainGameActivity)mContext).getTextureManager().loadTexture(this.player_BitmapTextureAtlas);
		
		//Thực hiện load toàn bộ quả bom
		for(int i=0;i<max_quabom;i++){
			MyQuaBom[i] = new QuaBom();			
			MyQuaBom[i].cap = max_cap_quabom;//Ban đầu thì ta load toàn bộ bom với cấp độ là max
			MyQuaBom[i].onLoadResources(mEngine, mContext);
			
			//Khi ta load xong thì ta đặt lại cấp bom cho đối tượng quabom
			MyQuaBom[i].cap = this.current_cap_quabom;
			}
	}
	//=======================================|| onLoadScene ||================================
	/**
	 * Phương thức onLoadScene
	 */
	@Override
	public void onLoadScene(Scene mScene) {
		//this.mScene = mScene;

		//Đặt vị trí ban đầu của player 
		player_AnimatedSprite = new AnimatedSprite(
				this.positionX,
				this.positionY,
				this.player_TiledTextureRegion,
				((MainGameActivity)mContext).getVertexBufferObjectManager());// my code
		//Phương thức này cho biết player sẽ hiện thị lần đầu tiên với trạng thái gì
		showPlayerStatus();
		mScene.attachChild(player_AnimatedSprite);
		//Ban đầu thì load toàn bộ bom và cho nó ở ngoài màn hình
		for(int i=0;i<max_quabom;i++){
			MyQuaBom[i].onLoadScene(mScene);//Đặt toàn bộ các quả bom ngoài màn hình
		}
	}

	//=======================================|| Position ||================================
	/**
	 * Phương thức cho ta đặt vị trí của player theo trục X.
	 * @param positionX
	 */
	public void setPositionX(float positionX){
		Player.this.positionX = positionX;
	}
	
	/**
	 * Get vị trí x xủa player
	 * @return
	 */
	public float getPositionX(){
		return Player.this.positionX;
	}
	/**
	 * Phương thức cho ta đạt vị trí của player theo trục Y
	 * @param positionY
	 */
	public void setPositionY(float positionY){
		Player.this.positionY = positionY;
	}
	
	/**
	 * Get vị trí y của player
	 * @return
	 */
	public float getPositionY(){
		return Player.this.positionY;
	}
	
	/**
	 * Đặt vị trí khởi tạo cho player
	 * @param positionX
	 * @param positionY
	 */
	public void setPositionXY(float positionX, float positionY){
		Player.this.positionX = positionX;
		Player.this.positionY = positionY;
	}
	//=======================================|| setStatusPlayer ||================================
	/**
	 * Phương thức set trạng thái cho Player
	 * @param: statusplayer là trạng thái bạn muốn đặt. Các trạng thái bạn có thể
	 * lấy bằng cách gọi StatusPlayer. (chấm) để tham chiếu đến các trạng thái
	 */
	public void setStatusPlayer(int statusplayer){
		Player.this.STATUS_PALYER = statusplayer;
		//Nếu setStatusPlayer thì ta sẽ cập nhật xem player sẽ hiện thị AnimatedSprite nào.
		showPlayerStatus();
	}
	//=======================================|| getStatusPlayer ||================================
	/**
	 * Phương thức get trạng thái cho Player
	 */
	public int getStatusPlayer(){
		return Player.this.STATUS_PALYER;
	}
	//=======================================|| showPlayerStatus ||================================
	/**
	 * Phương thức xác định xem Player đang ở trạng thái nào để hiện thị
	 */
	private void showPlayerStatus(){
		
		switch(this.STATUS_PALYER){
			case StatusPlayer.MOVE_LEFT:{
				player_AnimatedSprite.animate(new long[]{100,100,100}, new int[]{12,21,30}, 1000);
				break;
			}
			case StatusPlayer.MOVE_RIGHT:{
				player_AnimatedSprite.animate(new long[]{100,100,100}, new int[]{4,13,22}, 1000);
				break;
			}
			case StatusPlayer.MOVE_UP:{
				player_AnimatedSprite.animate(new long[]{100,100,100}, new int[]{3,31,5}, 1000);
				break;
			}
			case StatusPlayer.MOVE_DOWN:{
				player_AnimatedSprite.animate(new long[]{100,100,100}, new int[]{14,23,32}, 1000);
				break;
			}	
			case StatusPlayer.UN_MOVE_LEFT:{
				player_AnimatedSprite.animate(new long[]{100,100}, new int[]{21,21}, 2);
				break;
			}
			case StatusPlayer.UN_MOVE_RIGHT:{
				player_AnimatedSprite.animate(new long[]{100,100}, new int[]{4,4}, 2);
				break;
			}
			case StatusPlayer.UN_MOVE_UP:{
				player_AnimatedSprite.animate(new long[]{100,100}, new int[]{3,3}, 2);
				break;
			}
			case StatusPlayer.UN_MOVE_DOWN:{
				player_AnimatedSprite.animate(new long[]{100,100}, new int[]{14,14}, 2);
				break;
			}
		}
	}
	//=======================================|| movePlayer ||================================
	/**
	 * Move player tới vị trí X
	 * @param moveX
	 */
	public void moveX(float moveX){
		this.positionX = moveX;
		movePlayer();
	}
	/**
	 * Move player tới vị trí Y
	 * @param moveY
	 */
	public void moveY(float moveY){
		this.positionY = moveY;
		movePlayer();
	}
	/**
	 * Move player tới vị trí X,Y
	 * @param moveX
	 * @param moveY
	 */
	public void moveXY(float moveX, float moveY){
		this.positionX = moveX;
		this.positionY = moveY;
		movePlayer();
	}
	/**
	 * Move player tại vị trí hiện tại và cộng thêm 1 giá trị moveRelativeX
	 * @param moveRelativeX
	 */
	public void moveRelativeX(float moveRelativeX){
		this.positionX += moveRelativeX;
		movePlayer();
	}
	/**
	 * Move player tại vị trí hiện tại và cộng thêm 1 giá trị moveRelativeY
	 * @param moveRelativeY
	 */
	public void moveRelativeY(float moveRelativeY){
		this.positionY += moveRelativeY;
		movePlayer();
	}
	/**
	 * Move player tại vị trí hiện tại và cộng thêm 1 giá trị moveRelativeX, moveRelativeY
	 * @param moveRelativeX
	 * @param moveRelativeY
	 */
	public void moveRelativeXY(float moveRelativeX, float moveRelativeY){
		this.positionX += moveRelativeX;
		this.positionY += moveRelativeY;
		movePlayer();
	}
	
	/**
	 * Move player
	 */
	private void movePlayer(){
		player_AnimatedSprite.setPosition(this.positionX, this.positionY);
	}
	//=======================================|| sum_bom ||================================
	/**
	 * Đặt số lượng bom cho player
	 */
	public void setCurrent_quabom(int current_quabom){
		this.current_quabom = current_quabom;
	}
	/**
	 * Nhận số lượng bom mà palyer có
	 * @return
	 */
	public int getCurrent_quabom(){
		return this.current_quabom;
	}
	//=======================================|| Heart ||================================
	/**
	 * Đặt số lượng bom cho heart
	 */
	public void setHeart(int heart){
		this.heart = heart;
	}
	/**
	 * Nhận số lượng heart mà nhân vật có
	 * @return
	 */
	public int getHeart(){
		return this.heart;
	}
	public AnimatedSprite getAnimatedSprite(){
		return this.player_AnimatedSprite;
	}
	
	public void setCurrent_cap_quabom(int current_cap_quabom){
		this.current_cap_quabom = current_cap_quabom;
		for(int i=0;i<max_quabom;i++){
			MyQuaBom[i].cap = this.current_cap_quabom;
			}
	}
	
	public int getCurrent_cap_quabom(){
		return this.current_cap_quabom;
	}
}



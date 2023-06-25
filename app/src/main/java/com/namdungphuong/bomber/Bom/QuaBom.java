package com.namdungphuong.bomber.Bom;

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
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.util.debug.Debug;

import com.namdungphuong.bomber.ClassStatic.ControlerStatic;
import com.namdungphuong.bomber.InterfaceSprite.InterfaceSprite;
import android.content.Context;
import android.os.SystemClock;

public class QuaBom implements InterfaceSprite{
	
	//Ban đầu toàn bộ các quả bom đặt tại vị trí -100,-100. Ngoài màn hình
	public float pX = -100, pY = -100;
	
	//Đối tượng bom
	private Bom bom;
	
	//Cho biết cấp độ của bom là cấp độ nào
	public int cap = 1;
	
	//Array đối tượng Nổ để tạo ra vụ nổ khi quả bom được đặt xuống
	public No[] no;
	
	//Thời gian xác định bắt đầu đặt bom xuống
	public long time  = 0;
	
	long time_no = 0;//Thời gian xác định lúc nổ xong
	
	//Khi vụ nổ kết thúc thì no_end = true. Ban đầu là flase vì bom chưa nổ
	public boolean no_end = false;
	
	//Biến cho biết khi nào thì bom được kích hoạt nổ. Khi bom được kích hoạt nổ thì begin_no = true
	public boolean begin_no = false;
	
	//2 biến dùng để xét va chạm với maps, xác định vùng được di chuyển và không được di chuyển
	private TMXTiledMap mTMXTiledMap;
	private TMXLayer VatCanTMXLayer;
	
	private Sound sound_no;
	private boolean bool_sound_no = false;
	
	//============================================================================
	/**
	 * Phương thức khởi dựng
	 */
	public QuaBom(){}
	//============================================================================
	/**
	 * Phương thức set TMXTiledMap
	 * @param mTMXTiledMap
	 */
	public void setTMXTiledMap(TMXTiledMap mTMXTiledMap){
		this.mTMXTiledMap = mTMXTiledMap;
	}
	//============================================================================
	/**
	 * Phương thức set TMXLayer
	 */
	public void setTMXLayer(TMXLayer VatCanTMXLayer){
		this.VatCanTMXLayer = VatCanTMXLayer;
	}
	//============================================================================
	/**
	 * Phương thức kiểm tra xem tại vị trí pX, pY có phải thuộc vào vùng không được di chuyển.
	 * Nếu thuộc vùng không được di chuyển thì return true.
	 * Nếu không thuộc thì return false. (Đối với cả player và quái vật đều được di chuyển nếu điều return false)
	 */
	public boolean collidesWith(float pX, float pY){
		TMXTile mTMXTile = VatCanTMXLayer.getTMXTileAt(pX,pY);
    	try{
			if(mTMXTile == null){
				System.out.println("mTMXTile = null");//Đi ra ngoài bản đồ
			}
			else{
				TMXProperties<TMXTileProperty> mTMXProperties= mTMXTile.getTMXTileProperties(mTMXTiledMap);
				TMXTileProperty mTMXTileProperty = mTMXProperties.get(0);
				if(mTMXTileProperty.getName().equals("vatcan")){
				}
			}			
			return true;//Có va chạm
		}catch(Exception e){
			return false;//Không va chạm
		}
	}
	//============================================================================
	/**
	 * Khởi tạo lại các biến với giá trị mặc định ban đầu
	 */
	public void init(){
		no_end = false;
		time = 0;
		time_no = 0;
		begin_no = false;
	}
	//============================================================================
	/**
	 * Phương thức onLoadResources.
	 * Load ảnh và dặt tại vị trí mặc định
	 */
	@Override
	public void onLoadResources(Engine mEngine, Context mContext) {
		no = new No[(cap*4)+1];//Mỗi cấp có 4 đối tượng nổ, 1 đối tượng nổ sẽ nằm tại vị trí đặt bom
		//Load Bom
		bom = new Bom(pX,pY);//Đặt vị trí ban đầu cho bom
		bom.onLoadResources(mEngine, mContext);
		
		int j=0;//Cho biết đối tượng nổ sẽ nằm ở vị trí nào so với bom
		int lop = 1;
		
		for(int i=0;i<no.length-1;i++){
			if(j == 0){//Phải
				no[i] = new No(((bom.Bom_TiledTextureRegion.getWidth()/4)*lop + pX),pY);
				no[i].onLoadResources(mEngine, mContext);
			}else if(j == 1){//Trái
				no[i] = new No((pX - (bom.Bom_TiledTextureRegion.getWidth()/4)*lop),pY);
				no[i].onLoadResources(mEngine, mContext);
			}
			else if(j == 2){//Trên
				no[i] = new No(pX,pY - (bom.Bom_TiledTextureRegion.getHeight()*lop));
				no[i].onLoadResources(mEngine, mContext);			
			}
			else if(j == 3){
				no[i] = new No(pX,pY + (bom.Bom_TiledTextureRegion.getHeight()*lop));
				no[i].onLoadResources(mEngine, mContext);
				lop++;
			}
			j++;
			if(j >= 4)
				j=0;
		}
		//Đối tượng cuối cùng sẽ đặt tại vị trí của quả bom
		no[no.length-1] = new No(pX,pY);
		no[no.length-1].onLoadResources(mEngine, mContext);
		
		SoundFactory.setAssetBasePath("mfx/");
        try {
                sound_no = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mContext, "no1.wav");
        } catch (final IOException e) {
                Debug.e(e);
        }
	}
	//============================================================================
	/**
	 * Phương thức onLoadScene
	 */
	@Override
	public void onLoadScene(Scene mScene) {
		//Ban đầu thì toạn bộ ở trạng thái ẩn
		//Bom
		bom.onLoadScene(mScene);
		bom.Bom_AnimatedSprite.setVisible(false);
		//No
		for(int i=0;i<no.length;i++){			
			no[i].onLoadScene(mScene);
			no[i].No_AnimatedSprite.setVisible(false);
		}
	}
	//============================================================================
	/**
	 * Khi 1 quả bom được đặt xuống thì nó sẽ chờ trong 3s sau đó nó phát nổ trong 1s.
	 * Khi nó bắt đầu chờ thì time != 0, khi nó bắt đầu nổ thì time_no != 0. Khi nổ kết thúc thì 
	 * no_end = true.Khi mới bắt đầu nổ thì begin_no = true.
	 */
	public void delayNo(){
		if(no_end)//Khi bom đã nổ rồi thì không gọi nữa
			return;
		//Khi mà bom được đặt xuống thì ta bắt đầu tính thời gian
		if(time == 0)
			time = SystemClock.elapsedRealtime();
		
		if(SystemClock.elapsedRealtime() - time > 3000){//Chờ đủ 3s thì ta cho phép nổ
			//Đối với các đối tượng không được nổ thì không được hiện thị
			for(int i=0;i<no.length-1;i++){				
				if(i>=cap*4)
					no[i].No_AnimatedSprite.setVisible(false);
				else {
					if(no[i].visiable)
						no[i].No_AnimatedSprite.setVisible(true);
					else no[i].No_AnimatedSprite.setVisible(false);
				}
			}
			no[no.length-1].No_AnimatedSprite.setVisible(true);
			
			//Bắt đầu nổ.
			begin_no = true;
			if(!bool_sound_no && ControlerStatic.SOUND){
				sound_no.play();
				bool_sound_no = true;
			}
			//Bắt đầu tính thời gian nổ
			if(time_no == 0)
				time_no = SystemClock.elapsedRealtime();
			if(SystemClock.elapsedRealtime() - time_no > 1000){//Cho phép nổ trong 1s
				//Hết 1s ta cho ẩn toàn bộ và cho di chuyển đến vị trị -100,-100
				for(int i=0;i<no.length;i++){
					no[i].No_AnimatedSprite.setVisible(false);
					no[i].moveNewXY(-100, -100);
				}
				bom.Bom_AnimatedSprite.setPosition(-100, -100);
				bom.Bom_AnimatedSprite.setVisible(false);
				init();
				no_end = true;
			}
		}
	}

	//============================================================================
	/**
	 * Di chuyển toàn bộ đến vị trí X,Y mới
	 * 
	 */
	public void moveNewXY(float pX, float pY){
		init();//khởi tạo lại các biến mạc định
		
		this.pX = pX;
		this.pY = pY;
		
		bom.moveNewXY(pX, pY);//Khi gọi move thì bom sẽ được hiện thị
		
		int j=0;
		int lop = 1;
		
		boolean left = true, right = true, up = true, down = true;
		
		float x = 0, y = 0;
		for(int i=0;i<no.length-1;i++){
			if(j == 0){//Phải					
				no[i].moveNewXY(((bom.Bom_TiledTextureRegion.getWidth()/4)*lop + pX), pY);
				if(!right){
					no[i].visiable = false;//Không cho hiện thị các quả bom mà nằm trên vật cản
				}		
				else{
					x = (bom.Bom_TiledTextureRegion.getWidth()/4)*lop + pX + (bom.Bom_TiledTextureRegion.getWidth()/4)/2;
					y = (bom.Bom_TiledTextureRegion.getHeight()/2) + pY;
					if(this.collidesWith(x,y)){
						//Khi gọi move thì toạn bộ ở trạng thái ẩn					
						no[i].visiable = false;
						right = false;
					}else no[i].visiable = true;
				}				
							
			}else if(j == 1){//Trái					
				no[i].moveNewXY((pX - (bom.Bom_TiledTextureRegion.getWidth()/4)*lop), pY);
				if(!left){
					no[i].visiable = false;
				}		
				else{
					x = (pX - (bom.Bom_TiledTextureRegion.getWidth()/4)*lop) -  (bom.Bom_TiledTextureRegion.getWidth()/4)/2;
					y = pY + (bom.Bom_TiledTextureRegion.getHeight()/2);
					if(this.collidesWith(x,y)){
						//Khi gọi move thì toạn bộ ở trạng thái ẩn					
						no[i].visiable = false;
						left = false;
					}else no[i].visiable = true;
				}
				
			}
			else if(j == 2){//Trên				
				no[i].moveNewXY(pX, pY - (bom.Bom_TiledTextureRegion.getHeight()*lop));
				if(!up){
					no[i].visiable = false;
				}		
				else{
					x = (bom.Bom_TiledTextureRegion.getWidth()/4)/2 + pX;
					y = pY - (bom.Bom_TiledTextureRegion.getHeight()*lop) - (bom.Bom_TiledTextureRegion.getHeight()/2);
					if(this.collidesWith(x,y)){
						//Khi gọi move thì toạn bộ ở trạng thái ẩn					
						no[i].visiable = false;
						up = false;
					}else no[i].visiable = true;
				}
				
			}
			else if(j == 3){				
				no[i].moveNewXY(pX, pY + (bom.Bom_TiledTextureRegion.getHeight()*lop));
				if(!down){
					no[i].visiable = false;
				}		
				else{
					x = (bom.Bom_TiledTextureRegion.getWidth()/4)/2 + pX;
					y = pY + (bom.Bom_TiledTextureRegion.getHeight()*lop) + (bom.Bom_TiledTextureRegion.getHeight()/2);
					if(this.collidesWith(x,y)){
						//Khi gọi move thì toạn bộ ở trạng thái ẩn					
						no[i].visiable = false;
						down = false;
					}else no[i].visiable = true;
				}
				
				lop++;
			}
			j++;
			if(j >= 4)
				j=0;
		}
		no[no.length-1].moveNewXY(pX, pY);
		
		bool_sound_no = false;
	}
	//============================================================================
	/**
	 * kiểm tra xem 1 animated có va chạm với nổ không. Nếu có va chạm thì return true.
	 * ngược lại là false
	 */
	public boolean collidesWith(AnimatedSprite animatedSprite){
		for(int i=0;i<no.length;i++){
			if(no[i].No_AnimatedSprite.isVisible() && no[i].No_AnimatedSprite.collidesWith(animatedSprite))
				return true;//Có va chạm
		}
		return false;//Không có va chạm
	}
}

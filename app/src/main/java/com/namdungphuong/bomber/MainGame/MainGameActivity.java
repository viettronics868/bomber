package com.namdungphuong.bomber.MainGame;

import java.io.IOException;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.IOnSceneTouchListener;
//import org.andengine.entity.scene.background.ColorBackground;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
//import org.andengine.entity.text.ChangeableText;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;
import org.andengine.input.touch.controller.MultiTouch;
import org.andengine.input.touch.controller.MultiTouchController;

import com.namdungphuong.bomber.BomActivity;
import com.namdungphuong.bomber.Bom.QuaBom;
import com.namdungphuong.bomber.ClassStatic.ControlerStatic;
import com.namdungphuong.bomber.ClassStatic.ScreenStatic;
import com.namdungphuong.bomber.Database.Database;
import com.namdungphuong.bomber.Dialog.DialogExit;
import com.namdungphuong.bomber.Maps.Maps;
import com.namdungphuong.bomber.Other.Hopqua;
import com.namdungphuong.bomber.Player.Player;
import com.namdungphuong.bomber.Player.StatusPlayer;
import com.namdungphuong.bomber.Quaivat.Quaivat;

import android.content.Context;
import android.content.Intent;
//import android.graphics.Color;
import org.andengine.util.adt.color.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;

public class MainGameActivity extends BaseGameActivity implements	IOnSceneTouchListener,
																	IOnMenuItemClickListener
{
	
	private Player MyPlayer;
	public ArrayList<QuaBom> ArrayListQuaBom = new ArrayList<QuaBom>();
	
	private Quaivat MyQuaivat;
	
	private Hopqua MyHopqua;
	
	//Maps
	private TMXTiledMap mTMXTiledMap;
	//public TMXTiledMap mTMXTiledMap;
	private TMXLayer VatCanTMXLayer;
	
	//Biến quản lý màn hình
	private Camera MyCamera;
	private Scene MyScene;
	
	private Scene SceneLoading;
	
	
	private DigitalOnScreenControl digitalOnScreenControl;
	private int status_digitalOnScreenControl = -1;
	private BitmapTextureAtlas mOnScreenControlTexture;
    private TextureRegion mOnScreenControlBaseTextureRegion;
    private TextureRegion mOnScreenControlKnobTextureRegion;
    
    private BitmapTextureAtlas IconBomBitmapTextureAtlas;
    private TextureRegion IconBomTextureRegion;
    private Sprite IconBomSprite;
    
    private BitmapTextureAtlas CapBomBitmapTextureAtlas;
    private TextureRegion CapBomTextureRegion;
    private Sprite CapBomSprite;
    private Text TextCapBom;
    
    private BitmapTextureAtlas TiepTucBitmapTextureAtlas;
    private TextureRegion TiepTucTextureRegion;
    private Sprite TiepTucSprite;
    
    private BitmapTextureAtlas HeartBitmapTextureAtlas;
    private TextureRegion HeartTextureRegion;
    private Sprite HeartSprite;
    private Text TextHeart;
    
    private BitmapTextureAtlas PauseBitmapTextureAtlas;
    private TextureRegion PauseTextureRegion;
    private Sprite PauseSprite;
    
    private BitmapTextureAtlas SoundBitmapTextureAtlas;
    private TextureRegion SoundOnTextureRegion;
    private TextureRegion SoundOffTextureRegion;
    private Sprite SoundOnSprite;
    private Sprite SoundOffSprite;
    
    //private ChangeableText current_quabom_ChangeableText;//Text hiện thị số lượng quả bom hiện có của player
	private Text current_quabom_ChangeableText;
    private Font mFont;//Font hiện thị số lượng quả bom hiện có của palyer
    private BitmapTextureAtlas mFontTexture;
    
    
    private int DIEM = 0;//Điểm của người chơi.
    private int diem1 = 0;
    //private ChangeableText TextDiem;
	private Text TextDiem;
    //private ChangeableText TextLevel;
	private Text TextLevel;
    private BitmapTextureAtlas ABBitmapTextureAtlas;
    private TextureRegion ATextureRegion;
    private TextureRegion BTextureRegion;
    private Sprite A, B;
    
    protected static final int CONTINUE = 0;
    protected static final int NEWGAME = CONTINUE + 1;
    protected static final int MAINMENU = NEWGAME + 1;
    protected static final int MENU_QUIT = MAINMENU + 1;
    
    protected MenuScene mMenuScene;
    
    private Database db = new Database(this);
    
    private boolean OVERGAME = false;
    private boolean WIN = false;
    private boolean NEXT_LEVEL = false;
    
    private Sound beep, outch;
    private Sound sound_resetplayer;
    
    
    
    
    private int LEVEL = 1;//ứng với mỗi level ta load maps theo level
    //Quái vật
    private int MAX_SO_QUAI_VAT = 5;
    private int SO_QUAI_CAN_TIEU_DIET = 10;//Là số quái vật mà người chơi phải tiêu diệt
    //Khi tiêu diệt đủ số quái thì sẽ được tăng level
    private int DEM_SO_QUAI_BI_TIEU_DIET = 0;
    
    //Maps
    private String TEN_MAPS = "";
    
    //Player
    private int MAX_QUABOM = 10;
    private int CURRENT_QUABOM = 1;
    private int MAX_CAP_QUABOM = 5;
    private int CURRENT_CAP_QUABOM = 1;
    
    private int HEART = 3;


	public Camera mCamera = null;
	public Context mContext;
	public Engine mEngine = null;
	public Scene mScene = null;


	//****************************************


	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions)
	{
		//super.onCreateEngine(pEngineOptions);
		return new LimitedFPSEngine(pEngineOptions, 60);

	}

	@Override
	public EngineOptions onCreateEngineOptions()
	{

		mCamera = new Camera(0, 0, ScreenStatic.CAMERA_WIDTH, ScreenStatic.CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(
				true,
				ScreenOrientation.LANDSCAPE_FIXED,
				new FillResolutionPolicy(),
				this.mCamera);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engineOptions.getRenderOptions().getConfigChooserOptions().setRequestedMultiSampling(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);

//********************************************************//my code
		 mEngine =	 new Engine(engineOptions);
		try {
			if(MultiTouch.isSupported(this)) {
				mEngine.setTouchController(new MultiTouchController());
				if(MultiTouch.isSupportedDistinct(this)) {  //Thiết bị có hỗ trợ.
				}else {}
			} else {}
		} catch (Exception e) {}
		//return engine;

		mContext=this;
		//mEngine=this;

		Bundle b = getIntent().getExtras();
		if(b != null){
			LEVEL = b.getInt("LEVEL");
			MAX_SO_QUAI_VAT = b.getInt("MAX_SO_QUAI_VAT");
			SO_QUAI_CAN_TIEU_DIET = b.getInt("SO_QUAI_CAN_TIEU_DIET");
			TEN_MAPS = b.getString("TEN_MAPS");

			CURRENT_QUABOM = b.getInt("CURRENT_QUABOM");
			CURRENT_CAP_QUABOM = b.getInt("CURRENT_CAP_QUABOM");

			DIEM = b.getInt("DIEM");

			HEART = b.getInt("HEART");
		}else{
			//Nếu không có giá trị được truyền theo thì ta coi như level 1
			LEVEL = 1;

			//Quái vật
			MAX_SO_QUAI_VAT = 5;
			SO_QUAI_CAN_TIEU_DIET = 5;

			//Maps
			TEN_MAPS = "maps_1.tmx";
		}
		DEM_SO_QUAI_BI_TIEU_DIET = 0;

		MyPlayer = new Player(MAX_QUABOM, CURRENT_QUABOM, MAX_CAP_QUABOM, CURRENT_CAP_QUABOM);
		MyPlayer.setCurrent_quabom(CURRENT_QUABOM);
		MyPlayer.setHeart(HEART);

		MyQuaivat = new Quaivat(MAX_SO_QUAI_VAT);
		MyHopqua = new Hopqua();
//***********************************************************

		return engineOptions;
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException
	{
		//ResourcesManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager());
		//pOnCreateResourcesCallback.onCreateResourcesFinished();

		onLoadResources();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException
	{
		//SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);

		onLoadScene();//my code
		pOnCreateSceneCallback.onCreateSceneFinished(mScene);//my code
	}

	public void onPopulateScene(org.andengine.entity.scene.Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException
	{
/*
        mEngine.registerUpdateHandler(
                new TimerHandler(2f, new ITimerCallback() {
                    public void onTimePassed(final TimerHandler pTimerHandler) {
                        mEngine.unregisterUpdateHandler(pTimerHandler);
                        SceneManager.getInstance().createMenuScene();
                    }
                }));

        pOnPopulateSceneCallback.onPopulateSceneFinished();

*/
	}


	//******************************************



	//=======================================|| onLoadEngine ||================================
/*	public Engine onLoadEngine() {
		Bundle b = getIntent().getExtras();
		if(b != null){
			LEVEL = b.getInt("LEVEL");
			MAX_SO_QUAI_VAT = b.getInt("MAX_SO_QUAI_VAT");	
			SO_QUAI_CAN_TIEU_DIET = b.getInt("SO_QUAI_CAN_TIEU_DIET");
			TEN_MAPS = b.getString("TEN_MAPS");
			
			CURRENT_QUABOM = b.getInt("CURRENT_QUABOM");
			CURRENT_CAP_QUABOM = b.getInt("CURRENT_CAP_QUABOM");
			
			DIEM = b.getInt("DIEM");
			
			HEART = b.getInt("HEART");
		}else{
			//Nếu không có giá trị được truyền theo thì ta coi như level 1
			LEVEL = 1;
			
			//Quái vật
			MAX_SO_QUAI_VAT = 5;
			SO_QUAI_CAN_TIEU_DIET = 5;
			
			//Maps
			TEN_MAPS = "maps_1.tmx";
		}
		DEM_SO_QUAI_BI_TIEU_DIET = 0;
		
		MyPlayer = new Player(MAX_QUABOM, CURRENT_QUABOM, MAX_CAP_QUABOM, CURRENT_CAP_QUABOM);		
		MyPlayer.setCurrent_quabom(CURRENT_QUABOM);
		MyPlayer.setHeart(HEART);
		
		MyQuaivat = new Quaivat(MAX_SO_QUAI_VAT);
		MyHopqua = new Hopqua();
		
		
		//Khởi tạo vùng hiện thị là 480*320
		this.MyCamera = new Camera(0, 0, ScreenStatic.CAMERA_WIDTH, ScreenStatic.CAMERA_HEIGHT);
		//Yêu cầu màn hình hiện thị nằm ngang ScreenOrientation.LANDSCAPE
		//Engine engine =	 new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
		//			new RatioResolutionPolicy(ScreenStatic.CAMERA_WIDTH, ScreenStatic.CAMERA_HEIGHT),
		//			this.MyCamera).setNeedsSound(true).setNeedsMusic(true));
			
		//Kiểm tra xem thiết bị có hỗ trộ cảm ứng đa điểm hay không.
		try {
             if(MultiTouch.isSupported(this)) {
                     engine.setTouchController(new MultiTouchController());
                     if(MultiTouch.isSupportedDistinct(this)) {  //Thiết bị có hỗ trợ.                       
                     }else {}
             } else {}
	     } catch (Exception e) {}	
	     return engine;
	}
*/
	//=======================================|| HIỆN THỊ PHẦN LOADING ||========================
	
	private BitmapTextureAtlas BoomLoadingBitmapTextureAtlas;
    private TextureRegion BoomLoadingTextureRegion;
    private Sprite BoomLoadingSprite;
    
	public void onLoadResources(){



		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Bom/");
        this.BoomLoadingBitmapTextureAtlas = new BitmapTextureAtlas(
				((MainGameActivity)mContext).getTextureManager(),
				512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.BoomLoadingTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.BoomLoadingBitmapTextureAtlas,
				this,
				"boom_loading.png",
				0,
				0);
        //this.mEngine.getTextureManager().loadTextures(this.BoomLoadingBitmapTextureAtlas);//original
		((MainGameActivity)mContext).getTextureManager().loadTexture(this.BoomLoadingBitmapTextureAtlas);

		Debug.e("load booom");

		loadResources();

		Debug.e("load resource");
	}
	public Scene onLoadScene(){
		SceneLoading = new Scene();
		SceneLoading.setBackgroundEnabled(false);
		BoomLoadingSprite = new Sprite(
				0,
				0,
				BoomLoadingTextureRegion,
				((MainGameActivity)mContext).getVertexBufferObjectManager());
		SceneLoading.attachChild(BoomLoadingSprite);

		Debug.e("attach boom");

		loadScenes();

		Debug.e("load scene");

//************************// my code
/*		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
			public void onTimePassed(TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				loadResources();
				loadScenes();

				Debug.e("load resource and load Scene");

				mEngine.setScene(MyScene);    //Khi load xong thì ta chờ cho load phần MyScene
				//Khi MyScene load xong ta cho hiện thị lên màn hình
			}
		}));
*/
		return SceneLoading;	
	}
/*
	public void onLoadComplete() {
		mEngine.registerUpdateHandler(new TimerHandler(0.01f, new ITimerCallback() {			
			public void onTimePassed(TimerHandler pTimerHandler) {
				 mEngine.unregisterUpdateHandler(pTimerHandler);
                 loadResources();
                 loadScenes();
                 mEngine.setScene(MyScene);	//Khi load xong thì ta chờ cho load phần MyScene
                 //Khi MyScene load xong ta cho hiện thị lên màn hình
			}
		}));
			
	}
*/
	//=======================================|| HẾT PHẦN HIỆN THỊ PHẦN LOADING ||================

	//=======================================|| onLoadResources ||================================
	public void loadResources() {



		try {
			final TMXLoader tmxLoader = new TMXLoader(
					mContext.getAssets(),
					((MainGameActivity)mContext).getTextureManager(),
					TextureOptions.BILINEAR_PREMULTIPLYALPHA,
					((MainGameActivity)mContext).getVertexBufferObjectManager(),
					new TMXLoader.ITMXTilePropertiesListener() {
						public void onTMXTileWithPropertiesCreated(
								TMXTiledMap pTMXTiledMap,
								TMXLayer pTMXLayer,
								TMXTile pTMXTile,
								TMXProperties<TMXTileProperty> pTMXTileProperties) {
						}
					});
			mTMXTiledMap = (tmxLoader.loadFromAsset("tmx/world1.tmx"));//Load từ thư mục Assets/tmx/tên map

		} catch (final TMXLoadException tmxle) {
			Debug.e(tmxle);
		}




		//onLoadResources Player
		MyPlayer.onLoadResources(MainGameActivity.this.mEngine, MainGameActivity.this);

		//onLoadResources Quaivat
		MyQuaivat.onLoadResources(MainGameActivity.this.mEngine, MainGameActivity.this);
		
		//onLoadResources Hopqua
		MyHopqua.onLoadResources(MainGameActivity.this.mEngine, MainGameActivity.this);
		
		
		
		//Load phần điều khiển
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Control/");
        this.mOnScreenControlTexture = new BitmapTextureAtlas(
				((MainGameActivity)mContext).getTextureManager(),
				256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.mOnScreenControlTexture,
				this,
				"ControlBase.png",
				0, 0);
        this.mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.mOnScreenControlTexture,
				this,
				"ControlKnob.png",
				128, 0);
        //this.mEngine.getTextureManager().loadTextures(this.mOnScreenControlTexture);//original
		((MainGameActivity)mContext).getTextureManager().loadTexture(this.mOnScreenControlTexture);
        
        //Load icon bom
        this.IconBomBitmapTextureAtlas = new BitmapTextureAtlas(
				((MainGameActivity)mContext).getTextureManager(),
				64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.IconBomTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.IconBomBitmapTextureAtlas,
				this,
				"icon_bom.png",
				0, 0);
        //this.mEngine.getTextureManager().loadTextures(this.IconBomBitmapTextureAtlas);//original
		((MainGameActivity)mContext).getTextureManager().loadTexture(this.IconBomBitmapTextureAtlas);
        
        //Load Pause
        this.PauseBitmapTextureAtlas = new BitmapTextureAtlas(
				((MainGameActivity)mContext).getTextureManager(),
				64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.PauseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.PauseBitmapTextureAtlas,
				this,
				"pause.png",
				0, 0);
        //this.mEngine.getTextureManager().loadTextures(this.PauseBitmapTextureAtlas);//original
		((MainGameActivity)mContext).getTextureManager().loadTexture(this.PauseBitmapTextureAtlas);
        
        //Load Sound
        this.SoundBitmapTextureAtlas = new BitmapTextureAtlas(
				((MainGameActivity)mContext).getTextureManager(),
				64, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.SoundOnTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.SoundBitmapTextureAtlas,
				this,
				"sound_on.png",
				0, 0);
        this.SoundOffTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.SoundBitmapTextureAtlas,
				this,
				"sound_off.png",
				32, 0);
        //this.mEngine.getTextureManager().loadTextures(this.SoundBitmapTextureAtlas);//original
		((MainGameActivity)mContext).getTextureManager().loadTexture(this.SoundBitmapTextureAtlas);
        
        //Load Heart
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Player/");
        this.HeartBitmapTextureAtlas = new BitmapTextureAtlas(
				((MainGameActivity)mContext).getTextureManager(),
				64, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.HeartTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.HeartBitmapTextureAtlas,
				this,
				"heart.png",
				0, 0);
        //this.mEngine.getTextureManager().loadTextures(this.HeartBitmapTextureAtlas);//original
		((MainGameActivity)mContext).getTextureManager().loadTexture(this.HeartBitmapTextureAtlas);
        
        //Load CapBom
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Image/");
        this.CapBomBitmapTextureAtlas = new BitmapTextureAtlas(
				((MainGameActivity)mContext).getTextureManager(),
				32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.CapBomTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.CapBomBitmapTextureAtlas,
				this,
				"capbom.png",
				0, 0);
        //this.mEngine.getTextureManager().loadTextures(this.CapBomBitmapTextureAtlas);//original
		((MainGameActivity)mContext).getTextureManager().loadTexture(this.CapBomBitmapTextureAtlas);
        
        this.TiepTucBitmapTextureAtlas = new BitmapTextureAtlas(
				((MainGameActivity)mContext).getTextureManager(),
				256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.TiepTucTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.TiepTucBitmapTextureAtlas,
				this,
				"tieptuc.png",
				0, 0);
        //this.mEngine.getTextureManager().loadTexture(this.TiepTucBitmapTextureAtlas);//original
		((MainGameActivity)mContext).getTextureManager().loadTexture(this.TiepTucBitmapTextureAtlas);
        
        this.ABBitmapTextureAtlas = new BitmapTextureAtlas(
				((MainGameActivity)mContext).getTextureManager(),
				32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.ATextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.ABBitmapTextureAtlas,
				this,
				"A.png",
				0,
				0);
        this.BTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.ABBitmapTextureAtlas,
				this,
				"B.png",
				16, 0);
        //this.mEngine.getTextureManager().loadTextures(this.ABBitmapTextureAtlas);//original
		((MainGameActivity)mContext).getTextureManager().loadTexture(this.ABBitmapTextureAtlas);
        
        //Load font
        this.mFontTexture = new BitmapTextureAtlas(
				((MainGameActivity)mContext).getTextureManager(),
				256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        FontFactory.setAssetBasePath("font/");
        this.mFont = FontFactory.createFromAsset(
				((MainGameActivity)mContext).getFontManager(),
				this.mFontTexture,
				//this,
				((MainGameActivity)mContext).getAssets(),
				"UVNHanViet.TTF",
				34,
				true,
				//Color.WHITE);
				1);// what is color????
        //this.mEngine.getTextureManager().loadTexture(this.mFontTexture);//original
        //this.mEngine.getFontManager().loadFont(this.mFont);// original

		((MainGameActivity)mContext).getTextureManager().loadTexture(this.mFontTexture);
		((MainGameActivity)mContext).getFontManager().loadFont(this.mFont);
        
        SoundFactory.setAssetBasePath("mfx/");
        try {
                beep = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "iFLogInAlert.wav");
                outch = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "outch.wav");
                sound_resetplayer = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "resetplayer.wav");
        } catch (final IOException e) {
                Debug.e(e);
        }
	}

	//=======================================|| onLoadScene ||================================
	public void loadScenes() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		MyScene = new Scene();
		mContext= this;
		this.mEngine= mEngine;
		//mTMXTiledMap=new TMXTiledMap();


		//MyScene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));//original
		MyScene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		MyScene.setOnAreaTouchTraversalFrontToBack();
		MyScene.setOnSceneTouchListener(SceneTouchListener);
		//MyScene.setTouchAreaBindingEnabled(true);//original
		MyScene.setTouchAreaBindingOnActionDownEnabled(true);
		MyScene.registerUpdateHandler(UpdateHandler);
		
		
		//Load maps
		//this.mTMXTiledMap = Maps.getTMXTiledMap(MyScene, mEngine, mContext, TEN_MAPS);

/*
		try {
			final TMXLoader tmxLoader = new TMXLoader(
					mContext.getAssets(),
					((MainGameActivity)mContext).getTextureManager(),
					TextureOptions.BILINEAR_PREMULTIPLYALPHA,
					((MainGameActivity)mContext).getVertexBufferObjectManager(),
					new TMXLoader.ITMXTilePropertiesListener() {
						public void onTMXTileWithPropertiesCreated(
								TMXTiledMap pTMXTiledMap,
								TMXLayer pTMXLayer,
								TMXTile pTMXTile,
								TMXProperties<TMXTileProperty> pTMXTileProperties) {
						}
					});
			mTMXTiledMap = new TMXTiledMap(tmxLoader.loadFromAsset("tmx/"+TEN_MAPS));//Load từ thư mục Assets/tmx/tên map

		} catch (final TMXLoadException tmxle) {
			Debug.e(tmxle);
		}
*/

		ArrayList<TMXLayer> mapLayers = mTMXTiledMap.getTMXLayers();
		for(TMXLayer layer : mapLayers){
			if(layer.getName().equals("vatcan")){
				VatCanTMXLayer = layer;//Nếu là vật cản thì không cho hiện thị
				System.out.println("vật cản");
				continue;
			}
			layer.detachSelf();
			MyScene.attachChild(layer);
		}

/*
		for (int i = 0; i < this.mTMXTiledMap.getTMXLayers().size(); i++) {
			TMXLayer layer = this.mTMXTiledMap.getTMXLayers().get(i);

			if(layer.getName().equals("vatcan")){
				VatCanTMXLayer = layer;//Nếu là vật cản thì không cho hiện thị
				System.out.println("vật cản");
				//continue;
			} else {

				layer.detachSelf();// my code
				//layer.attachChild(this);//my code is not good
				MyScene.attachChild(layer);//original// it is ok
			}
		}
*/



		//onLoadScene Player
		MyPlayer.setPositionXY(64, 64);
		MyPlayer.onLoadScene(MyScene);
		MyPlayer.setCurrent_cap_quabom(CURRENT_CAP_QUABOM);
		for(int i=0;i<MyPlayer.MyQuaBom.length;i++){
			MyPlayer.MyQuaBom[i].setTMXLayer(VatCanTMXLayer);
			MyPlayer.MyQuaBom[i].setTMXTiledMap(mTMXTiledMap);
		}
		
		//onLoadScene Quaivat
		MyQuaivat.onLoadScene(MyScene);
		MyQuaivat.setTMXTiledMap(mTMXTiledMap);
		MyQuaivat.setTMXLayer(VatCanTMXLayer);
		
		//Thực hiện hiện thị các quái vật ngẫu nhiên trên màn hình
		MyQuaivat.reset();
		
		MyHopqua.onLoadScene(MyScene);
		
		//--------------------------------------|| BẮT ĐẦU PHẦN ĐIỀU KHIỂN NHÂN VẬT CHÍNH ||----------------------------------
		digitalOnScreenControl = new DigitalOnScreenControl(
				0,
				ScreenStatic.CAMERA_HEIGHT - this.mOnScreenControlBaseTextureRegion.getHeight(),
				this.MyCamera,
				this.mOnScreenControlBaseTextureRegion,
				this.mOnScreenControlKnobTextureRegion,
				0.1f,
				((MainGameActivity)mContext).getVertexBufferObjectManager(),
				new IOnScreenControlListener() {
			 float pX = 0, pY = 0;
             public void onControlChange(
					 final BaseOnScreenControl pBaseOnScreenControl,
					 final float pValueX,
					 final float pValueY) {

            	if(pValueX > 0 && MyPlayer.getStatusPlayer() != StatusPlayer.MOVE_RIGHT){
					MyPlayer.setStatusPlayer(StatusPlayer.MOVE_RIGHT);
					status_digitalOnScreenControl = StatusPlayer.MOVE_RIGHT;
					pX = MyPlayer.player_AnimatedSprite.getWidth() + MyPlayer.getPositionX();
					pY = MyPlayer.getPositionY() + (MyPlayer.player_AnimatedSprite.getHeight() / 2);
					}
					
				else if(pValueX < 0  &&MyPlayer.getStatusPlayer() != StatusPlayer.MOVE_LEFT){
					MyPlayer.setStatusPlayer(StatusPlayer.MOVE_LEFT);
					status_digitalOnScreenControl = StatusPlayer.MOVE_LEFT;
					pX = MyPlayer.getPositionX();
					pY = MyPlayer.getPositionY() + (MyPlayer.player_AnimatedSprite.getHeight() / 2);
				}
				
				else if(pValueY > 0 && MyPlayer.getStatusPlayer() != StatusPlayer.MOVE_DOWN){
					MyPlayer.setStatusPlayer(StatusPlayer.MOVE_DOWN);
					status_digitalOnScreenControl = StatusPlayer.MOVE_DOWN;					
					pX = MyPlayer.getPositionX() + (MyPlayer.player_AnimatedSprite.getWidth()/2);
					pY = MyPlayer.getPositionY() + MyPlayer.player_AnimatedSprite.getHeight();
				}
				
				else if(pValueY < 0 && MyPlayer.getStatusPlayer() != StatusPlayer.MOVE_UP){
					MyPlayer.setStatusPlayer(StatusPlayer.MOVE_UP);
					status_digitalOnScreenControl = StatusPlayer.MOVE_UP;
					pX = MyPlayer.getPositionX() + (MyPlayer.player_AnimatedSprite.getWidth()/2);
					pY = MyPlayer.getPositionY();
				}
            	
				else{
					switch(status_digitalOnScreenControl){
						case StatusPlayer.MOVE_RIGHT: MyPlayer.setStatusPlayer(StatusPlayer.UN_MOVE_RIGHT); break;
						case StatusPlayer.MOVE_LEFT: MyPlayer.setStatusPlayer(StatusPlayer.UN_MOVE_LEFT); break;
						case StatusPlayer.MOVE_UP: MyPlayer.setStatusPlayer(StatusPlayer.UN_MOVE_UP); break;
						case StatusPlayer.MOVE_DOWN: MyPlayer.setStatusPlayer(StatusPlayer.UN_MOVE_DOWN); break;
						default: break;
					}
				}
            	
            	if(pValueX != 0 || pValueY != 0){
	            	TMXTile mTMXTile = VatCanTMXLayer.getTMXTileAt(pX + pValueX * 7,pY + pValueY * 7);
	            	try{
						if(mTMXTile == null){
						}
						else{
							TMXProperties<TMXTileProperty> mTMXProperties= mTMXTile.getTMXTileProperties(mTMXTiledMap);
							
							TMXTileProperty mTMXTileProperty = mTMXProperties.get(0);
							if(mTMXTileProperty.getName().equals("vatcan")){
								
							}
						}
					}catch(Exception e){
						MyPlayer.moveRelativeXY(pValueX * 7, pValueY * 7);
					}
            	}
             }
             
		 });
		digitalOnScreenControl.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		digitalOnScreenControl.getControlBase().setAlpha(0.7f);
		digitalOnScreenControl.getControlBase().setScaleCenter(0, 128);
		digitalOnScreenControl.getControlBase().setScale(0.8f);
		digitalOnScreenControl.getControlKnob().setScale(0.8f);
		digitalOnScreenControl.refreshControlKnobPosition();

        MyScene.setChildScene(digitalOnScreenControl);
       //--------------------------------------|| HẾT PHẦN ĐIỀU KHIỂN NHÂN VẬT CHÍNH ||----------------------------------
        this.IconBomSprite = new Sprite(
				ScreenStatic.CAMERA_WIDTH - this.IconBomTextureRegion.getWidth(),
				ScreenStatic.CAMERA_HEIGHT - this.IconBomTextureRegion.getHeight(),
				this.IconBomTextureRegion,
				((MainGameActivity)mContext).getVertexBufferObjectManager()){
        	@Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
        		
        		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN){
        			
        			if(MyPlayer.current_quabom - ArrayListQuaBom.size() != 0 && ControlerStatic.SOUND)
        				beep.play();//Âm thanh khi đặt quả bom xuống với điều kiện là còn bom
        			
        			//Nếu số quả bom có trong ArrayList mà nhiều hơn số quả bom thì không add vào nữa.
        			//Nếu số quả bom có trong ArrayList mà nhiều hơn số quả bom cho phép thì không add nữa.
        			if(ArrayListQuaBom.size() >=  MyPlayer.MyQuaBom.length ||
        					ArrayListQuaBom.size() >=  MyPlayer.current_quabom)
        				return true;
        			
	        		//Nếu có 1 quả bom nào đó được kích hoạt thì cho nó vào ArrayList
	        		for(QuaBom quabom : MyPlayer.MyQuaBom){
	        			if(quabom.time == 0){	
	        				quabom.moveNewXY(MyPlayer.player_AnimatedSprite.getX(),MyPlayer.player_AnimatedSprite.getY());
			        		ArrayListQuaBom.add(quabom);
			        		break;
		        		}
	        		}
        		}
        		return true;
            }
        };        
        this.IconBomSprite.setAlpha(0.5f);
        MyScene.attachChild(IconBomSprite);
        MyScene.registerTouchArea(IconBomSprite);
        
        
        this.CapBomSprite = new Sprite(
				ScreenStatic.CAMERA_WIDTH - this.CapBomTextureRegion.getWidth()-5,
        		ScreenStatic.CAMERA_HEIGHT - IconBomSprite.getHeight() - this.CapBomTextureRegion.getHeight(),
				this.CapBomTextureRegion,
				((MainGameActivity)mContext).getVertexBufferObjectManager());
        MyScene.attachChild(CapBomSprite);
        MyScene.registerTouchArea(CapBomSprite);

        TextCapBom = new Text(
				ScreenStatic.CAMERA_WIDTH - this.CapBomTextureRegion.getWidth()-5,
        		ScreenStatic.CAMERA_HEIGHT - IconBomSprite.getHeight() - this.CapBomTextureRegion.getHeight() - 5,
        		this.mFont,
				String.valueOf(MyPlayer.getCurrent_cap_quabom()),
				2,
				((MainGameActivity)mContext).getVertexBufferObjectManager());//Hiện thị tối đa 2 ký tự
        MyScene.attachChild(TextCapBom);
        
        
        this.PauseSprite = new Sprite(ScreenStatic.CAMERA_WIDTH - 32, 1, this.PauseTextureRegion,
				((MainGameActivity)mContext).getVertexBufferObjectManager()){
        	@Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
        		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN){
        			if(ControlerStatic.SOUND)
        				outch.play();
        			//Hiện thị menu
	                MainGameActivity.this.MyScene.setChildScene(MainGameActivity.this.mMenuScene, false, true, true);
        		}
        		return true;
            }
        };        
        this.PauseSprite.setAlpha(0.5f);
        MyScene.attachChild(PauseSprite);
        MyScene.registerTouchArea(PauseSprite);
        

        this.SoundOnSprite = new Sprite(ScreenStatic.CAMERA_WIDTH - 64, 1, this.SoundOnTextureRegion,
				((MainGameActivity)mContext).getVertexBufferObjectManager()){
        	@Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
        		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN){
        			if(ControlerStatic.SOUND)
        				outch.play(); 
        			ControlerStatic.SOUND = !ControlerStatic.SOUND;
        			SoundOffSprite.setPosition(ScreenStatic.CAMERA_WIDTH - 64, 1);
        			SoundOnSprite.setPosition(-100, -100);
        		}
        		return true;
            }
        };        
        this.SoundOnSprite.setAlpha(0.5f);
        MyScene.attachChild(SoundOnSprite);
        MyScene.registerTouchArea(SoundOnSprite);
        
        this.SoundOffSprite = new Sprite(ScreenStatic.CAMERA_WIDTH - 64, 1, this.SoundOffTextureRegion,
				((MainGameActivity)mContext).getVertexBufferObjectManager()){
        	@Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
        		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN){
        			if(ControlerStatic.SOUND)
        				outch.play();   
        			ControlerStatic.SOUND = !ControlerStatic.SOUND;
        			SoundOnSprite.setPosition(ScreenStatic.CAMERA_WIDTH - 64, 1);
        			SoundOffSprite.setPosition(-100, -100);
        		}
        		return true;
            }
        };        
        this.SoundOffSprite.setAlpha(0.5f);
        MyScene.attachChild(SoundOffSprite);
        MyScene.registerTouchArea(SoundOffSprite);
        
        if(ControlerStatic.SOUND){
        	SoundOnSprite.setPosition(ScreenStatic.CAMERA_WIDTH - 64, 1);
        	SoundOffSprite.setPosition(-100, -100);
        }else{
        	SoundOnSprite.setPosition(-100, -100);
        	SoundOffSprite.setPosition(ScreenStatic.CAMERA_WIDTH - 64, 1);
        }
        
        
        
        this.HeartSprite = new Sprite(ScreenStatic.CAMERA_WIDTH/2 - this.HeartTextureRegion.getWidth()/2, 0, this.HeartTextureRegion,
				((MainGameActivity)mContext).getVertexBufferObjectManager());
        MyScene.attachChild(HeartSprite);
        MyScene.registerTouchArea(HeartSprite);
        

        TextHeart = new Text(this.HeartSprite.getX() - 20,-10,this.mFont,String.valueOf(MyPlayer.getHeart()),2,
				((MainGameActivity)mContext).getVertexBufferObjectManager());//Hiện thị tối đa 2 ký tự
        MyScene.attachChild(TextHeart);
        
        
        current_quabom_ChangeableText = new Text(IconBomSprite.getX()+IconBomSprite.getWidth()/2,
        		IconBomSprite.getY() + IconBomSprite.getHeight()/2 - 10, this.mFont, "Text",
				((MainGameActivity)mContext).getVertexBufferObjectManager());
        MyScene.attachChild(current_quabom_ChangeableText);
        
        TextDiem = new Text(0,-10,this.mFont,String.valueOf(DIEM),10,
				((MainGameActivity)mContext).getVertexBufferObjectManager());//Hiện thị tối đa 10 ký tự
        MyScene.attachChild(TextDiem);
        
        TextLevel = new Text(ScreenStatic.CAMERA_WIDTH/2 - 50,ScreenStatic.CAMERA_HEIGHT-42,this.mFont,"Level "+String.valueOf(LEVEL),10,
				((MainGameActivity)mContext).getVertexBufferObjectManager());//Hiện thị tối đa 10 ký tự
        MyScene.attachChild(TextLevel);
        
        
        
        this.TiepTucSprite = new Sprite(ScreenStatic.CAMERA_WIDTH/2 - this.TiepTucTextureRegion.getWidth()/2,
        		ScreenStatic.CAMERA_HEIGHT/2 - this.TiepTucTextureRegion.getHeight()/2, this.TiepTucTextureRegion,
				((MainGameActivity)mContext).getVertexBufferObjectManager()){
        	@Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
        		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN && TiepTucSprite.isVisible()){
        			if(ControlerStatic.SOUND)
        				outch.play();   
        			//Chiến thắng
					if(LEVEL == ControlerStatic.TONG_SO_LEVEL){
						Intent i = new Intent(MainGameActivity.this, ChienThang.class);
						i.putExtra("diem", DIEM);
						MainGameActivity.this.startActivity(i);
						MainGameActivity.this.finish();		
					}else
						nextLevel();
        		}
        		return true;
            }
        };
        TiepTucSprite.setVisible(false);
        MyScene.attachChild(TiepTucSprite);
        MyScene.registerTouchArea(TiepTucSprite);
        
        
        this.A = new Sprite(-100, -100 , this.ATextureRegion,
				((MainGameActivity)mContext).getVertexBufferObjectManager());
        A.setVisible(false);
        MyScene.attachChild(A);
        MyScene.registerTouchArea(A);
        
        this.B = new Sprite(-100, -100 , this.BTextureRegion,
				((MainGameActivity)mContext).getVertexBufferObjectManager());
        B.setVisible(false);
        MyScene.attachChild(B);
        MyScene.registerTouchArea(B);
        
        //Menu
        this.mMenuScene = this.createMenuScene();
	}
	
	//=======================================|| UpdateHandler ||================================
	ArrayList<QuaBom> ArrayListRemoveQuaBom = new ArrayList<QuaBom>();
	IUpdateHandler UpdateHandler = new IUpdateHandler(){
		public void reset() {
		}
		public void onUpdate(float pSecondsElapsed) {
			if(!OVERGAME){
				if(!WIN){
					try {
						Thread.sleep(20);//Ngủ trong 20ms
						for(int i=0;i<MyQuaivat.max_quai;i++){
							if(MyQuaivat.quaivat_1[i].Quaivat_1_AnimatedSprite.isVisible())
								MyQuaivat.quaivat_1[i].moveRandom();
							
							//Nếu quái vật nào mà ở trạng thái ẩn thì ta cho hiện thị lên.
							//Với điều kiện là số quái vật cần phải tiêu diệt lơn hơn so với tổng số quái vật có
							if(SO_QUAI_CAN_TIEU_DIET - DEM_SO_QUAI_BI_TIEU_DIET >= MAX_SO_QUAI_VAT){
								if(!MyQuaivat.quaivat_1[i].Quaivat_1_AnimatedSprite.isVisible())
									MyQuaivat.quaivat_1[i].bool_reset = true;
								}
							
							if((SO_QUAI_CAN_TIEU_DIET*50) - diem1 >= (MAX_SO_QUAI_VAT*50)){
								if(!MyQuaivat.quaivat_1[i].Quaivat_1_AnimatedSprite.isVisible())
									MyQuaivat.quaivat_1[i].bool_reset = true;
								}
							
							
							MyQuaivat.quaivat_1[i].reset();
							//Kiểm tra xem quái vật có va chạm với player không. Nếu có va chạm thì player sẽ bị reset và
							//bị mất 1 mạng
							
							if(vaCham( MyQuaivat.quaivat_1[i].Quaivat_1_AnimatedSprite, MyPlayer.player_AnimatedSprite) && 
									MyQuaivat.quaivat_1[i].Quaivat_1_AnimatedSprite.isVisible()){
								if(ControlerStatic.SOUND)
									sound_resetplayer.play();
								//Có va chạm
								//Khởi tạo lại vị trí
								MyPlayer.moveXY(64, 64);
								//Bị trừ đi 1 mạng
								MyPlayer.setHeart(MyPlayer.getHeart() - 1);
								
								//Cập nhật và hiện thị số mạng còn lại
								TextHeart.setText(String.valueOf(MyPlayer.getHeart()));
							}
						}
						
						try{
						if(ArrayListQuaBom.size() != 0){
							for(QuaBom quabom : ArrayListQuaBom){
								if(!quabom.no_end){
									quabom.delayNo();
									if(quabom.begin_no){//Khi quả bom đang trong trạng thái nổ thì kiểm tra xem có quái vật nào
										//va chạm với bom không. Nếu quái vật va chạm với quả bom thì quái vật đó sẽ bị biến mất
										for(int i=0;i<MyQuaivat.max_quai;i++){
											if(vaCham(quabom, MyQuaivat.quaivat_1[i].Quaivat_1_AnimatedSprite)){
																				
												//Mỗi 1 quái vật bị chết sẽ cộng thêm 50 điểm vào số điểm
												if(MyQuaivat.quaivat_1[i].Quaivat_1_AnimatedSprite.isVisible()){
														DIEM = DIEM + 50;
														diem1 = diem1 + 50;
														
														DEM_SO_QUAI_BI_TIEU_DIET++;
					
														if(SO_QUAI_CAN_TIEU_DIET == DEM_SO_QUAI_BI_TIEU_DIET){
															//Đã tiêu diệt xong quái vật/
															//Hiện thông báo vượt qua level và chuyển sang level tiếp theo
															System.out.println("Thành công. Đã tiêu diệt xong.");
															
															WIN = true;															
														}
														
														if(SO_QUAI_CAN_TIEU_DIET - DEM_SO_QUAI_BI_TIEU_DIET >= MAX_SO_QUAI_VAT){
															if(!MyQuaivat.quaivat_1[i].Quaivat_1_AnimatedSprite.isVisible())
																MyQuaivat.quaivat_1[i].bool_reset = true;
															}
														
														if((SO_QUAI_CAN_TIEU_DIET*50) - diem1 >= (MAX_SO_QUAI_VAT*50)){
															if(!MyQuaivat.quaivat_1[i].Quaivat_1_AnimatedSprite.isVisible())
																MyQuaivat.quaivat_1[i].bool_reset = true;
															}
													}											
												MyQuaivat.quaivat_1[i].Quaivat_1_AnimatedSprite.setVisible(false);	
												MyQuaivat.quaivat_1[i].Quaivat_1_AnimatedSprite.setPosition(-100, -100);
												//Hiện thị điểm
												//TextDiem.setText(String.valueOf(DIEM), true);//Nếu nhiều hơn 10 ký tự thì hiện thị ...
												TextDiem.setText(String.valueOf(DIEM));
												}
											//Kiểm tra với player. Nếu chạm vào player thì player sẽ khởi tạo lại vị trí ban đầu
											if(vaCham(quabom, MyPlayer.player_AnimatedSprite)){
												if(ControlerStatic.SOUND)
													sound_resetplayer.play();
												
												MyPlayer.moveXY(64, 64);
												//Khi bị chết thì ta trừ đi 1 mạng
												MyPlayer.setHeart(MyPlayer.getHeart() - 1);	
												
												//trừ đi 1 cấp bom
												if(MyPlayer.getCurrent_cap_quabom() - 1 != 0)
													MyPlayer.setCurrent_cap_quabom(MyPlayer.getCurrent_cap_quabom() - 1);
												//trừ đi 1 quả bom
												if(MyPlayer.getCurrent_quabom() - 1 != 0)
													MyPlayer.setCurrent_quabom(MyPlayer.getCurrent_quabom() - 1);
												}
										}
									}
								}else{
									ArrayListRemoveQuaBom.add(quabom);						
								}
							}
						}
						}catch(Exception e){
							System.out.println("CÓ thể có lỗi: "+e.toString());
						}
						
						if(ArrayListRemoveQuaBom.size() != 0 && ArrayListQuaBom.size() != 0){
							for(QuaBom quabom : ArrayListRemoveQuaBom){
								ArrayListQuaBom.remove(quabom);
							}
							ArrayListRemoveQuaBom.removeAll(ArrayListRemoveQuaBom);
						}
						
						//Text
						current_quabom_ChangeableText.setText(String.valueOf(MyPlayer.current_quabom - ArrayListQuaBom.size()));
		
						//Cập nhật và hiện thị số mạng còn lại
						TextHeart.setText(String.valueOf(MyPlayer.getHeart()));
						
						//Cập nhật và hiện thị số cấp bom
						TextCapBom.setText(String.valueOf(MyPlayer.getCurrent_cap_quabom()));
						
						
						if(MyPlayer.getHeart() <= 0){
							//Game over
							System.out.println("GameOver");
							OVERGAME = true;//OVERGAME							
						}
						
						
						MyHopqua.hienThi(mTMXTiledMap, VatCanTMXLayer);
						MyHopqua.collidesWith(MyPlayer);
						
						
						checkIsVisiable();//Cứ sau 15s check 1 lần
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				//=============KHI NGƯỜI CHƠI DÀNH CHIẾN THẮNG===============
				}else{
					//Vượt qua level
					if(!NEXT_LEVEL){
						TiepTucSprite.setVisible(true);	
						float pX = TiepTucSprite.getX()+1;
						if(pX > 180)
							pX = ScreenStatic.CAMERA_WIDTH/2 - TiepTucSprite.getWidth()/2;
						TiepTucSprite.setPosition(pX, TiepTucSprite.getY());
						
					}else{
					}
				}
			}
			//OVERGAME
			else{
				//Kiểm tra xem số điểm của người chơi có cao hơn số điểm của những người chơi khác không
				//Nếu cao hơn ta chuyển qua phần lưu tên người chơi.
				if(db.kt_luu(DIEM)){
					Intent i = new Intent(MainGameActivity.this, Luudiem.class);
					i.putExtra("diem", DIEM);
					MainGameActivity.this.startActivity(i);
					MainGameActivity.this.finish();		
				}
				//Nếu không được lưu tên ta quay lại menu chính để người chơi có thể chọn play để chơi lại từ đầu.
				else{					
					Intent i = new Intent(MainGameActivity.this, Gameover.class);//Chuyển sang activity Gameover
					MainGameActivity.this.startActivity(i);
					MainGameActivity.this.finish();					
				}
			}
		}
	};
	//=======================================|| IOnSceneTouchListener ||================================
	IOnSceneTouchListener SceneTouchListener = new 	IOnSceneTouchListener() {
		public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {			
			if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN){
				System.out.println("MainGameActivity.this.onSceneTouchEvent TouchEvent.ACTION_DOWN");
				if(MyPlayer.getStatusPlayer()+1 > StatusPlayer.SUM)
					MyPlayer.setStatusPlayer(0);
				else MyPlayer.setStatusPlayer(MyPlayer.getStatusPlayer()+1);				
			}
			return false;
		}
	};
	//=======================================|| onLoadComplete ||================================
	//public void onLoadComplete() {
		//System.out.println("MainGameActivity.this.onLoadComplete");
		
	//}
	//=======================================|| onResumeGame ||================================
	@Override	
	public void onResumeGame() {
		System.out.println("MainGameActivity.this.onResumeGame");
	};
	//=======================================|| onDestroy ||================================
	@Override
	protected void onDestroy() {		
		if(beep != null)
			beep.release();
		if(outch != null)
			outch.release();
		
		MainGameActivity.this.finish();
		System.out.println("MainGameActivity.this.finish");
		super.onDestroy();
	}
	//=======================================|| onSceneTouchEvent ||================================
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		return false;
	}
	
	//=======================================|| MENU ||============================================
	protected MenuScene createMenuScene() {
        final MenuScene menuScene = new MenuScene(MainGameActivity.this.MyCamera);
       
        final IMenuItem resetMenuItem = new ColorMenuItemDecorator(
				new TextMenuItem(
						CONTINUE,
						this.mFont,
						"TIẾP TỤC",
						((MainGameActivity)mContext).getVertexBufferObjectManager()),
				//1.0f,0.0f,0.0f, 255.0f,255.0f,255.0f);
				new Color(1,0,0),
				new Color(0,0,0));

/*
		final IMenuItem resetMenuItem = new ColorMenuItemDecorator(
				new TextMenuItem(
						MENU_RESET,
						this.mFont,
						"RESET",
						this.getVertexBufferObjectManager()),
				new Color(1,0,0),
				new Color(0,0,0));

*/

        resetMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        menuScene.addMenuItem(resetMenuItem);
        
        final IMenuItem NewGameMenuItem = new ColorMenuItemDecorator(
				new TextMenuItem(
						NEWGAME,
						MainGameActivity.this.mFont,
						"CHƠI LẠI",
						((MainGameActivity)mContext).getVertexBufferObjectManager()),
				//1.0f,0.0f,0.0f, 255.0f,255.0f,255.0f);
				new Color(1,0,0),
				new Color(0,0,0));
        NewGameMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        menuScene.addMenuItem(NewGameMenuItem);
        
        final IMenuItem MainMenuMenuItem = new ColorMenuItemDecorator(
				new TextMenuItem(
						MAINMENU,
						MainGameActivity.this.mFont,
						"MENU CHÍNH",
						((MainGameActivity)mContext).getVertexBufferObjectManager()),
				//1.0f,0.0f,0.0f, 255.0f,255.0f,255.0f);
				new Color(1,0,0),
				new Color(0,0,0));
        MainMenuMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        menuScene.addMenuItem(MainMenuMenuItem);

        final IMenuItem quitMenuItem = new ColorMenuItemDecorator(
				new TextMenuItem(MENU_QUIT,
						this.mFont,
						"THOÁT",
						((MainGameActivity)mContext).getVertexBufferObjectManager()),
				//1.0f,0.0f,0.0f, 255.0f,255.0f,255.0f);
				new Color(1,0,0),
				new Color(0,0,0));
        quitMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        menuScene.addMenuItem(quitMenuItem);

        menuScene.buildAnimations();
        menuScene.setBackgroundEnabled(false);
        menuScene.setOnMenuItemClickListener(this);
        return menuScene;
	}
	//=================================================|| onMenuItemClicked ||=========================================
	@Override
    public boolean onMenuItemClicked(final MenuScene pMenuScene,
									 final IMenuItem pMenuItem,
									 final float pMenuItemLocalX,
									 final float pMenuItemLocalY) {
            switch(pMenuItem.getID()) {
                    case CONTINUE:
	                    	if(ControlerStatic.SOUND)
	                    		outch.play();
                    		MainGameActivity.this.MyScene.back();                    	
                            this.mMenuScene.reset();
                            this.MyScene.setChildScene(digitalOnScreenControl);
                            return true;
                    case NEWGAME:
	                    	if(ControlerStatic.SOUND)
	                    		outch.play();
	                    	Intent i = new Intent(MainGameActivity.this, MainGameActivity.class);
	                    	MainGameActivity.this.startActivity(i);
	                    	MainGameActivity.this.finish();
	                        return true;
                    case MAINMENU:
	                    	if(ControlerStatic.SOUND)
	                    		outch.play();
	                    	Intent intent_BomActivity = new Intent(MainGameActivity.this, BomActivity.class);
	                    	MainGameActivity.this.startActivity(intent_BomActivity);
	                    	MainGameActivity.this.finish();
	                        return true;
                    case MENU_QUIT:
	                    	if(ControlerStatic.SOUND)
	                    		outch.play();
	                    	DialogExit dialogexit = new DialogExit(MainGameActivity.this, MainGameActivity.this);
	            			dialogexit.show();
                            return true;
                    default:
                            return false;
            }
    }
	
	//=================================================|| onKeyDown ||=========================================
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//Khi bấm nút back thì hiện thị menu xem có tiếp tục chơi hay thoát
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			DialogExit dialogexit = new DialogExit(MainGameActivity.this, MainGameActivity.this);
			dialogexit.show();
		}
		return false;
	}
	
	//=================================================|| nextLevel ||=========================================
	private void nextLevel(){
		Intent intent_BomActivity = new Intent(MainGameActivity.this, MainGameActivity.class);
		
		if(LEVEL+1 < ControlerStatic.TONG_SO_LEVEL)
			LEVEL++;
		dinhNghiaCacBienThayDoi();
		
		CURRENT_CAP_QUABOM = MyPlayer.getCurrent_cap_quabom();
		CURRENT_QUABOM = MyPlayer.getCurrent_quabom();
		HEART = MyPlayer.getHeart();
		
		intent_BomActivity.putExtra("CURRENT_CAP_QUABOM", CURRENT_CAP_QUABOM);
		intent_BomActivity.putExtra("CURRENT_QUABOM", CURRENT_QUABOM);
		intent_BomActivity.putExtra("DIEM", DIEM);
		intent_BomActivity.putExtra("HEART", HEART + 3);//Mỗi lần tăng level người chơi được thưởng thêm 3 lượt chơi
		
		intent_BomActivity.putExtra("LEVEL", LEVEL);
		intent_BomActivity.putExtra("MAX_SO_QUAI_VAT", MAX_SO_QUAI_VAT);
		intent_BomActivity.putExtra("SO_QUAI_CAN_TIEU_DIET", SO_QUAI_CAN_TIEU_DIET);
		intent_BomActivity.putExtra("TEN_MAPS", TEN_MAPS + ".tmx");
		
    	MainGameActivity.this.startActivity(intent_BomActivity);
    	MainGameActivity.this.finish();
	}
	
	//=================================================|| dinhNghiaCacBienThayDoi ||=========================================
	//Định nghĩa các giá trị thay đổi theo level
	public void dinhNghiaCacBienThayDoi(){
		switch(LEVEL){
		case 1:
			MAX_SO_QUAI_VAT = 5;
			SO_QUAI_CAN_TIEU_DIET = 5;
			TEN_MAPS = "maps_" + LEVEL;
			break;
		case 2:
			MAX_SO_QUAI_VAT = 5;
			SO_QUAI_CAN_TIEU_DIET = 10;
			TEN_MAPS = "maps_" + LEVEL;
			break;
		case 3:
			MAX_SO_QUAI_VAT = 5;
			SO_QUAI_CAN_TIEU_DIET = 15;
			TEN_MAPS = "maps_" + LEVEL;
			break;
		case 4:
			MAX_SO_QUAI_VAT = 10;
			SO_QUAI_CAN_TIEU_DIET = 20;
			TEN_MAPS = "maps_" + LEVEL;
			break;
		case 5:
			MAX_SO_QUAI_VAT = 10;
			SO_QUAI_CAN_TIEU_DIET = 25;
			TEN_MAPS = "maps_" + LEVEL;
			break;
		case 6:
			MAX_SO_QUAI_VAT = 15;
			SO_QUAI_CAN_TIEU_DIET = 30;
			TEN_MAPS = "maps_" + LEVEL;
			break;
		case 7:
			MAX_SO_QUAI_VAT = 15;
			SO_QUAI_CAN_TIEU_DIET = 35;
			TEN_MAPS = "maps_" + LEVEL;
			break;
		case 8:
			MAX_SO_QUAI_VAT = 15;
			SO_QUAI_CAN_TIEU_DIET = 40;
			TEN_MAPS = "maps_" + LEVEL;
			break;
		case 9:
			MAX_SO_QUAI_VAT = 20;
			SO_QUAI_CAN_TIEU_DIET = 45;
			TEN_MAPS = "maps_" + LEVEL;
			break;
		case 10:
			MAX_SO_QUAI_VAT = 20;
			SO_QUAI_CAN_TIEU_DIET = 50;
			TEN_MAPS = "maps_" + LEVEL;
			break;
		case 11:
			MAX_SO_QUAI_VAT = 20;
			SO_QUAI_CAN_TIEU_DIET = 55;
			TEN_MAPS = "maps_" + LEVEL;
			break;
		case 12:
			MAX_SO_QUAI_VAT = 25;
			SO_QUAI_CAN_TIEU_DIET = 60;
			TEN_MAPS = "maps_" + LEVEL;
			break;
		case 13:
			MAX_SO_QUAI_VAT = 25;
			SO_QUAI_CAN_TIEU_DIET = 70;
			TEN_MAPS = "maps_" + LEVEL;
			break;
		case 14:
			MAX_SO_QUAI_VAT = 25;
			SO_QUAI_CAN_TIEU_DIET = 80;
			TEN_MAPS = "maps_" + LEVEL;
			break;
		case 15:
			MAX_SO_QUAI_VAT = 25;
			SO_QUAI_CAN_TIEU_DIET = 90;
			TEN_MAPS = "maps_" + LEVEL;
			break;
		case 16:
			MAX_SO_QUAI_VAT = 25;
			SO_QUAI_CAN_TIEU_DIET = 100;
			TEN_MAPS = "maps_" + LEVEL;
			break;
		case 17:
			MAX_SO_QUAI_VAT = 30;
			SO_QUAI_CAN_TIEU_DIET = 120;
			TEN_MAPS = "maps_" + LEVEL;
			break;
		case 18:
			MAX_SO_QUAI_VAT = 30;
			SO_QUAI_CAN_TIEU_DIET = 150;
			TEN_MAPS = "maps_" + LEVEL;
			break;
		case 19:
			MAX_SO_QUAI_VAT = 30;
			SO_QUAI_CAN_TIEU_DIET = 170;
			TEN_MAPS = "maps_" + LEVEL;
			break;
		case 20:
			MAX_SO_QUAI_VAT = 30;
			SO_QUAI_CAN_TIEU_DIET = 200;
			TEN_MAPS = "maps_" + LEVEL;
			break;
		}
	}
	//=================================================|| vaCham ||=========================================
	public boolean vaCham(AnimatedSprite a, AnimatedSprite b){		
		A.setPosition(a.getX() + 8,a.getY() + 8);
		B.setPosition(b.getX() + 8,b.getY() + 8);
		if(A.collidesWith(B))
			return true;
		return false;
	}
	//=================================================|| vaCham ||=========================================
	public boolean vaCham(QuaBom quabom, AnimatedSprite b){		
		for(int i=0;i<quabom.no.length;i++){
			if(quabom.no[i].No_AnimatedSprite.isVisible()){
				A.setPosition(quabom.no[i].No_AnimatedSprite.getX() + 8,quabom.no[i].No_AnimatedSprite.getY() + 8);
				B.setPosition(b.getX() + 8,b.getY() + 8);
				if(A.collidesWith(B))
					return true;
			}
		}
		return false;
	}
	
	//=================================================|| checkIsVisiable ||=========================================
	long time_checkIsVisiable = 0;
	public boolean checkIsVisiable(){
		try{
		if(time_checkIsVisiable == 0)
			time_checkIsVisiable = SystemClock.elapsedRealtime();
		if(SystemClock.elapsedRealtime() - time_checkIsVisiable > 15000){
			time_checkIsVisiable = 0;
			if(diem1 != (SO_QUAI_CAN_TIEU_DIET*50)){
				for(int i=0;i<MyQuaivat.quaivat_1.length;i++){
					if(MyQuaivat.quaivat_1[i].Quaivat_1_AnimatedSprite.isVisible())
						return true;
				}	
				if(!MyQuaivat.quaivat_1[0].Quaivat_1_AnimatedSprite.isVisible())
					MyQuaivat.quaivat_1[0].bool_reset = true;
				return false;
			}
		}
		}catch (Exception e){
			System.out.println("e = "+e.toString());
		}
		return false;
	}
}

package com.namdungphuong.bomber.InterfaceSprite;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;

import android.content.Context;

import com.namdungphuong.bomber.MainGame.MainGameActivity;

public interface InterfaceSprite {

	public Camera mCamera = null;
	public Context mContext = null;
	public Engine mEngine = null;
	public Scene mScene = null;
	public MainGameActivity mGame = null;


	public void onLoadResources(Engine mEngine, Context mContext);
	public void onLoadScene(Scene mScene);
}

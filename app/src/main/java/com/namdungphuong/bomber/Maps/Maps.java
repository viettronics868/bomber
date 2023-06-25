package com.namdungphuong.bomber.Maps;

import org.andengine.engine.Engine;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.util.debug.Debug;

import android.content.Context;

import com.namdungphuong.bomber.MainGame.MainGameActivity;

public class Maps {

	/*
	 * Load maps với tên được chuyền vào. Return TMXTiledMap
	 */
	public static TMXTiledMap getTMXTiledMap(Scene MyScene, Engine mEngine,  Context mContext, String maps_name){


		 TMXTiledMap mTMXTiledMap ;
		 try {
	            final TMXLoader tmxLoader = new TMXLoader(
						((MainGameActivity)mContext).getAssets(),
						((MainGameActivity)mContext).getTextureManager(),
						TextureOptions.BILINEAR_PREMULTIPLYALPHA,
						((MainGameActivity)mContext).getVertexBufferObjectManager(),
						new ITMXTilePropertiesListener() {
					public void onTMXTileWithPropertiesCreated(
							TMXTiledMap pTMXTiledMap,
							TMXLayer pTMXLayer,
							TMXTile pTMXTile,
							TMXProperties<TMXTileProperty> pTMXTileProperties) {
					}                    
	            });
	            mTMXTiledMap = tmxLoader.loadFromAsset("tmx/"+maps_name);//Load từ thư mục Assets/tmx/tên map

			 	Debug.e("load map");

	            return mTMXTiledMap;
	    } catch (final TMXLoadException tmxle) {
	            Debug.e(tmxle);
	    }
	    return null;
		//return mTMXTiledMap;
	}
}

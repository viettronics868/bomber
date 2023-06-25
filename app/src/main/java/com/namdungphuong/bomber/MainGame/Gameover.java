package com.namdungphuong.bomber.MainGame;

import com.namdungphuong.bomber.BomActivity;
import com.namdungphuong.bomber.R;
import com.namdungphuong.bomber.Dialog.DialogExit;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Gameover extends Activity{
	
	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.gameover);
		
		Button button_gameover_choilai = (Button)findViewById(R.id.button_gameover_choilai);
		Button button_gameover_mainmenu = (Button)findViewById(R.id.button_gameover_mainmenu);
		Button button_gameover_thoat = (Button)findViewById(R.id.button_gameover_thoat);
		
		button_gameover_choilai.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Gameover.this, MainGameActivity.class);
				Gameover.this.startActivity(i);
				Gameover.this.finish();
			}
		});
		
		button_gameover_mainmenu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Gameover.this, BomActivity.class);
				Gameover.this.startActivity(i);
				Gameover.this.finish();
			}
		});

		button_gameover_thoat.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				DialogExit dialogexit = new DialogExit(Gameover.this,Gameover.this);
				dialogexit.show();
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			DialogExit dialogexit = new DialogExit(Gameover.this, Gameover.this);
			dialogexit.show();
		}
		return super.onKeyDown(keyCode, event);
	}
}

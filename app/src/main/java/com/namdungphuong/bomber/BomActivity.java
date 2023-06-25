package com.namdungphuong.bomber;

import com.namdungphuong.bomber.ClassStatic.ControlerStatic;
import com.namdungphuong.bomber.Database.Database;
import com.namdungphuong.bomber.Dialog.DialogExit;
import com.namdungphuong.bomber.Dialog.DialogInfor;
import com.namdungphuong.bomber.MainGame.Huongdan;
import com.namdungphuong.bomber.Sound.Sound;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

public class BomActivity extends Activity {
	Animation animation_xoay;
	Animation animation_alpha;
	int index = 0;
	
	// Biến để làm việc với csdl
	public Database db;

	Sound nhac_nen;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.main);
		animation_xoay = AnimationUtils.loadAnimation(this, R.anim.anim_xoay);
		animation_alpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
		
		nhac_nen = new Sound(this, R.raw.nhac_nen_menu);
		if(ControlerStatic.SOUND)
			nhac_nen.play();
		
		csdlMau();
		
		final ImageButton ImageButton_play = (ImageButton) findViewById(R.id.imageButton_play);
		ImageButton_play.startAnimation(animation_xoay);
		ImageButton_play.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(BomActivity.this, Huongdan.class);
				BomActivity.this.startActivity(i);
				BomActivity.this.finish();
			}
		});

		final ImageButton ImageButton_infor = (ImageButton) findViewById(R.id.imageButton_infor);
		ImageButton_infor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				DialogInfor dialoginfor = new DialogInfor(BomActivity.this);
				dialoginfor.show();
			}
		});

		final ImageButton ImageButton_exit = (ImageButton) findViewById(R.id.imageButton_exit);
		ImageButton_exit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				DialogExit dialogexit = new DialogExit(BomActivity.this, BomActivity.this);
				dialogexit.show();
			}
		});

		final ImageView imageView_sound = (ImageView)findViewById(R.id.imageView_sound);
		imageView_sound.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(ControlerStatic.SOUND){//Đang mở. Yêu cầu tắt
					imageView_sound.setImageResource(R.drawable.sound_off);
					ControlerStatic.SOUND = false;
					if(nhac_nen.mPlaying)
						nhac_nen.stop();
				}else{//Đang tắt. Yêu cầu mở
					imageView_sound.setImageResource(R.drawable.sound_on);
					ControlerStatic.SOUND = true;
					if(!nhac_nen.mPlaying)
						nhac_nen.play();
				}
			}
		});
		
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if(nhac_nen.complet && ControlerStatic.SOUND){
					nhac_nen.complet = false;
					nhac_nen.replay();
					System.out.println("replay");
				}
				switch (index) {
					case 0:
						ImageButton_exit.clearAnimation();
						ImageButton_infor.clearAnimation();
						ImageButton_play.startAnimation(animation_xoay);
						break;
					case 1:
						ImageButton_play.clearAnimation();
						ImageButton_infor.clearAnimation();
						ImageButton_exit.startAnimation(animation_xoay);
						break;
					case 2:
						ImageButton_exit.clearAnimation();
						ImageButton_play.clearAnimation();
						ImageButton_infor.startAnimation(animation_xoay);
						break;
					default: break;
				}
				super.handleMessage(msg);
			}
		};

		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(8000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					index++;
					if (index > 2)
						index = 0;
					Message msg = handler.obtainMessage();
					handler.sendMessage(msg);
				}
			}
		});
		th.start();
	}
	@Override
	protected void onDestroy() {
		if(nhac_nen != null)
			nhac_nen.release();
		super.onDestroy();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			DialogExit dialogexit = new DialogExit(BomActivity.this, BomActivity.this);
			dialogexit.show();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	// --------------------------------------------------------------------------
	// Nếu là lần chơi đầu tiên thì ta tạo ra 1 csdl giả
	public void csdlMau() {
		// Lần đầu khi bắt đầu chơi
		// Giả lập dữ liệu mẫu
		db = new Database(this);
		// Dữ liệu mẫu gồm 10 người
		try {
			db.open();
			Cursor c = db.getAllRows();
			if (!c.moveToFirst()) {
				db.isertRow("Player", 100);
				db.isertRow("Player", 100);
				db.isertRow("Player", 100);
				db.isertRow("Player", 100);
				db.isertRow("Player", 100);
				db.isertRow("Player", 100);
				db.isertRow("Player", 100);
				db.isertRow("Player", 100);
				db.isertRow("Player", 100);
				db.isertRow("Player", 100);
			}
			c.close();
			db.close();
		} catch (SQLiteException e) {
			e.printStackTrace();
		}

	}
}
package com.namdungphuong.bomber.MainGame;

import com.namdungphuong.bomber.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class Huongdan extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.huongdan);
		
		ImageView imageView = (ImageView)findViewById(R.id.button1);
		imageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Huongdan.this, MainGameActivity.class);
				Huongdan.this.startActivity(i);
				Huongdan.this.finish();
			}
		});
		
	}
}

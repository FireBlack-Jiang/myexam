package abc.myexam;

import fda.jkl.iew.AdManager;
import fda.jkl.iew.st.SplashView;
import fda.jkl.iew.st.SpotDialogListener;
import fda.jkl.iew.st.SpotManager;
import abc.myexam.*;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SplashSpotActivity extends Activity {

	SplashView splashView;
	Context context;
	View splash;
	RelativeLayout splashLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		context = this;
	
		
		AdManager.getInstance(context).init("8eaf55588df70fd3", "5d5c9cbbb43803ab");
	
		splashView = new SplashView(context, null);
	
		splashView.setShowReciprocal(true);
	
		splashView.hideCloseBtn(true);

		Intent intent = new Intent(context, MainActivity.class);
		splashView.setIntent(intent);
		splashView.setIsJumpTargetWhenFail(true);

		splash = splashView.getSplashView();
		setContentView(R.layout.splash_activity);
		splashLayout = ((RelativeLayout) findViewById(R.id.splashview));
		splashLayout.setVisibility(View.GONE);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-1, -1);
		params.addRule(RelativeLayout.ABOVE, R.id.cutline);
		splashLayout.addView(splash, params);

		SpotManager.getInstance(context).showSplashSpotAds(context, splashView,
				new SpotDialogListener() {

					@Override
					public void onShowSuccess() {
						splashLayout.setVisibility(View.VISIBLE);
						splashLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.pic_enter_anim_alpha));
						Log.d("youmisdk", "开屏成功");
					}

					@Override
					public void onShowFailed() {
						Log.d("youmisdk", "开屏失败");
					}

					@Override
					public void onSpotClosed() {
						Log.d("youmisdk", "开屏关闭");
					}

					@Override
					public void onSpotClick(boolean isWebPath) {
						Log.i("YoumiAdDemo", "点击插屏");
					}
				});

	//简单调用
		// SpotManager.getInstance(context).showSplashSpotAds(context,
		// MainActivity.class);
		
		      
		  

	}

	@Override
	public void onBackPressed() {
		// 鍙栨秷鍚庨��閿�
	}

	@Override
	protected void onResume() {

		/**
		 * 璁剧疆涓虹珫灞�
		 */
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		super.onResume();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// land
		} else if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			// port
		}
	}

}

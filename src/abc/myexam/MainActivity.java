package abc.myexam;

import java.io.IOException;
import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import abc.myexam.wxapi.WXEntryActivity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Canvas;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import fda.jkl.iew.AdManager;
import fda.jkl.iew.br.AdSize;
import fda.jkl.iew.br.AdView;
import fda.jkl.iew.br.AdViewListener;
import fda.jkl.iew.st.SpotDialogListener;
import fda.jkl.iew.st.SpotManager;
import fda.jkl.iew.update.AppUpdateInfo;
import fda.jkl.iew.update.CheckAppUpdateCallBack;


public class MainActivity extends SlidingFragmentActivity implements OnClickListener,CallBack,CheckAppUpdateCallBack /*implements OnGestureListener*/ {

	private static final String TAG="MainActivity";
	//滑动界面
	public String my_db;
    private Fragment mContent;
    SlidingMenu sm = null;
   private CanvasTransformer mTransformer;  
   private CanvasTransformer mTransformer2;  
   Context context;
   
   private ImageButton button_main_open_left;
   private ImageButton button_main_open_right;
   private TextView main_one;

	/** Called when the activity is first created. */
   public static final int sanjiben_db=1;  //考试数据库
   public static final int kjzg_db=2;
   public static final int khjl_db_c=3;
   public static final int khjl_db_p=4;
   public static final int xxxt_db=5;
  
	public static final int OPTION_ORDER = 1;
	public static final int OPTION_RDM = 2;
	public static final int OPTION_TEST = 3;
	public static final int OPTION_WRONGEXERCISE = 4;
	public static final int MODE = MODE_PRIVATE;
	public static final String PREFERENCE_NAME = "SaveSetting";
	public static final String CONFIG_AUTOCHECK = "config_autocheck";
	public static final String CONFIG_AUTO2NEXT = "config_auto2next";
	public static final String CONFIG_AUTO2ADDWRONGSET = "config_auto2addwrongset";
	public static final String CONFIG_SOUND = "config_SOUND";
	public static final String CONFIG_CHECKBYRANDOM = "config_checkbyrandom";

	private TextView tv = null;
	private ImageButton btn_order = null;
	private ImageButton btn_rdm = null;
	private ImageButton btn_test = null;
	private ImageButton btn_myWAset = null;
	private ImageButton btn_option = null;
    private ImageButton btn_duo=null;
    private ImageButton btn_dan=null;
    private ImageButton btn_pan=null;
	private ImageButton btn_about = null;
	private ImageButton btn_exit = null;
	private ImageButton search_click=null;
    private ImageButton ImageButton_share=null;
	Dialog dialog;
	private SetClass settablenameClass;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
	        setContentView(R.layout.layout_content);
	         context = this;//广告函数线索
	        
	    	//UpdateActivity my_update=new UpdateActivity();//检查更新
	         /**
	          * 调用本方法当检查更新结束时会回调CheckAppUpdateCallBack接口中的回调方法
	          */	      
	        AdManager.getInstance(this).asyncCheckAppUpdate(this);
	    	button_main_open_left = (ImageButton) findViewById(R.id.button_main_open_left);
	        button_main_open_right = (ImageButton) findViewById(R.id.button_main_open_right);
	        main_one = (TextView) findViewById(R.id.main_one);
	        button_main_open_left.setOnClickListener(this);
	        button_main_open_right.setOnClickListener(this);
	        setBehindContentView(R.layout.menu_frame);//加载左侧界面
	        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new MenuFragment()).commit();
	        //主界面动画
	        
	        mTransformer = new CanvasTransformer() {
				
				@Override
				public void transformCanvas(Canvas canvas, float percentOpen) {
//					float scale = (float) (percentOpen*0.25 + 0.75); 
					float scale = (float) (1 - percentOpen * 0.25);
	                canvas.scale(scale, scale, canvas.getWidth()/2, canvas.getHeight()/2);              
				}
			};
			//背景层动画
			mTransformer2 = new CanvasTransformer() {
				
				@Override
				public void transformCanvas(Canvas canvas, float percentOpen) {
					float scale = (float) (percentOpen * 0.25 + 0.75);
	                canvas.scale(scale, scale, 0,
	                        canvas.getHeight() / 2);           
				}
			};
			/**
			以下是slidingmenu惯用操作
			 */
	        sm = getSlidingMenu();
	        sm.setSecondaryMenu(R.layout.menu_frame2);////加载右侧界面
   
	        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame2, new MenuFragment2()).commit();
	        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);//SlidingMenu划出时主页面显示的剩余宽度
	        sm.setFadeEnabled(false);
	        sm.setBehindScrollScale(0.25f);
	        sm.setFadeDegree(0.75f);
	        sm.setMode(SlidingMenu.LEFT_RIGHT);
      //  sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	        sm.setBackgroundImage(R.drawable.rootblock_default_bg);
	        sm.setBehindWidth((int)(getWindowManager().getDefaultDisplay().getWidth() / 1));//1代表全屏
	        sm.setBehindCanvasTransformer(mTransformer2);
	        sm.setAboveCanvasTransformer(mTransformer);
	       
	    //以上是滑动布局初始化
	
		
		
		//super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		btn_order = (ImageButton) findViewById(R.id.btn_order);
		btn_rdm = (ImageButton) findViewById(R.id.btn_rdm);
		btn_test = (ImageButton) findViewById(R.id.btn_test);
		btn_myWAset = (ImageButton) findViewById(R.id.btn_myWAset);
		btn_option = (ImageButton) findViewById(R.id.btn_option);
        btn_duo=(ImageButton) findViewById(R.id.ImageButton05);
        btn_dan=(ImageButton) findViewById(R.id.ImageButton07);
        btn_pan=(ImageButton) findViewById(R.id.ImageButton06);
		search_click = (ImageButton) findViewById(R.id.search_click);
		 btn_exit = (ImageButton) findViewById(R.id.btn_exit);
 		 ImageButton_share=(ImageButton) findViewById(R.id.ImageButton01);
		judgeTheFirstTime2Run();
	     init_db();
	     //广告
	   
	  // 加载插播资源
	  			//setSpotAd();
	   //广告初始化
			showBanner();
			

		btn_order.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				String data=main_one.getText().toString();
				change_db(data);
				intent.putExtra("data", my_db);
				intent.putExtra("option", OPTION_ORDER);
				intent.setClass(MainActivity.this, ExerciseActivity.class);
				startActivity(intent);
			}
		});
		btn_order.setVisibility(View.GONE);

		btn_rdm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				String data=main_one.getText().toString();
				change_db(data);
				intent.putExtra("data", my_db);
				intent.putExtra("option", OPTION_RDM);
				intent.setClass(MainActivity.this, ExerciseActivity.class);
				startActivity(intent);
			}
		});

		/*
		 * 我的错误集
		 */
		btn_myWAset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//= "My_WrongSet.txt"
				Intent intent = new Intent();
				String data=main_one.getText().toString();
				change_db(data);
				intent.putExtra("data", my_db);
				intent.putExtra("data_name", data);
				intent.setClass(MainActivity.this, WrongSetShowList.class);
				startActivity(intent);
			}
		});

		btn_test.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				String data=main_one.getText().toString();
				change_db(data);
				intent.putExtra("data", my_db);
				intent.putExtra("data_name", data);
				intent.putExtra("option", OPTION_TEST);
				intent.setClass(MainActivity.this, ExamActivity.class);
				startActivity(intent);
			}
		});
		/*
		 * 设置
		 */
		btn_option.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent().setClass(MainActivity.this,
						OptionActivity.class));
			}
		});
		btn_exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				exitdialog();
				//startActivity(new Intent().setClass(MainActivity.this,
						//Exam_add_table.class));
			}
		});

		search_click.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				String data=main_one.getText().toString();
				change_db(data);
				intent.putExtra("data", my_db);
				intent.putExtra("data_name", data);
				intent.setClass(MainActivity.this, Search_Main.class);
				startActivity(intent);
			}
		});
		//3多选1单选2判断专项练习按钮
		btn_duo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				String data=main_one.getText().toString();
				change_db(data);
				intent.putExtra("data", my_db);
				intent.putExtra("option", 3);
				intent.setClass(MainActivity.this, E_zhuanxiang.class);
				startActivity(intent);
			}
		});
		//单选
		btn_dan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				String data=main_one.getText().toString();
				change_db(data);
				intent.putExtra("data", my_db);
				intent.putExtra("option", 1);
				intent.setClass(MainActivity.this, E_zhuanxiang.class);
				startActivity(intent);
			}
		});
		btn_pan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				String data=main_one.getText().toString();
				change_db(data);
				intent.putExtra("data", my_db);
				intent.putExtra("option", 2);
				intent.setClass(MainActivity.this, E_zhuanxiang.class);
				startActivity(intent);
			}
		});
		//微信分享
		ImageButton_share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, WXEntryActivity.class);
				startActivity(intent);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void judgeTheFirstTime2Run() {
		// TODO 更新题库 修改testype
		DBAdapter dbAdapter;
		Cursor cursor;
		try {
			dbAdapter = new DBAdapter(this,"ABC_TEST");
			dbAdapter.open();
			cursor = dbAdapter.getAllData();
			cursor.moveToFirst();
			 Log.i("GetAllData","初始化");
				String Version=cursor.getString(cursor.getColumnIndex(DBAdapter.TESTTPYE));
				//myopencount 读取testanswer数据用做计数
				int myopencount=cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTANSWER));
				int isshared=cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTID));//isshare留做备用，默认isshare为1表示未分享。
				
				//每打开程序两次，插屏一次。打开次数超过10次后才显示插屏广告
				if (myopencount%2==0&&isshared!=0&&myopencount>=10) {
					setSpotAd();
					myopencount++;
				}
				ContentValues vv=new ContentValues();
				vv.put("TestAnswer", myopencount+"");
				dbAdapter.update_data(vv);	
			if (Version.compareTo("13")!=0) {		
				ContentValues v1 = new ContentValues();
				v1.put("TestType", "13");				
				dbAdapter.update_data(v1);					
				DBManager dbhelp1 = new DBManager(this);
					dbhelp1.createDataBase();
					dbhelp1.close();
					new AlertDialog.Builder(this)
						.setTitle("注意！")
						.setMessage("配置数据，按确定后请稍等5秒……")
						.setPositiveButton(
								"确定",
								new android.content.DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// DataTrans();
									}
								}).create().show();

			}
			dbAdapter.close();
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this, "配置数据中......请支持分享，谢谢", Toast.LENGTH_LONG).show();
			DBManager dbhelp = new DBManager(this);
			try {
				dbhelp.createDataBase();
				dbhelp.close();
				} catch (IOException m) {
				// TODO Auto-generated catch block
				m.printStackTrace();
			}
		}
		
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			exitdialog();
		}
		return false;
	}

	protected void exitdialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("确认退出吗？");

		builder.setTitle("提示");

		builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface v, int which) {
						// TODO Auto-generated method stub
						// dialog.dismiss();
						finish();
					}
				});
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	private void sharedPreferencesInit() {
		// TODO Auto-generated method stub
		SharedPreferences sharedPreferences = getSharedPreferences(
				PREFERENCE_NAME, MODE);
		try {
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putBoolean(CONFIG_AUTOCHECK, false);
			editor.putBoolean(CONFIG_AUTO2NEXT, false);
			editor.putBoolean(CONFIG_AUTO2ADDWRONGSET, false);
			editor.putBoolean(CONFIG_SOUND, false);
			editor.putBoolean(CONFIG_CHECKBYRANDOM, false);
			editor.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// Toast.makeText(this, "修改错误", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button_main_open_left:
			
			//通过，ABC_TEST的字段testtips临时存放数据（当前使用的数据表）
			change_db(main_one.getText().toString());
			 DBAdapter dataAdapter;
			 ContentValues vv=new ContentValues();
			 vv.put("TestTips", my_db+"");
			 vv.put("ImageName", main_one.getText().toString());
			 dataAdapter=new DBAdapter(this,"ABC_TEST");
			 dataAdapter.open();
			 dataAdapter.update_data(vv);
			 dataAdapter.close();
			setToggle();
			break;
		case R.id.button_main_open_right:
			sm.showSecondaryMenu();
			break;

		default:
			break;
		}
	}

	//SHOW CONTENT 鎴朚ENU
	public void setToggle(){
		toggle();
	}
	@Override
	public void SuccessText(String text) {
		// TODO Auto-generated method stub
		show_total(text);
	}
	
	
	//广告函数
	
	///广告类函数
	
	private void setSpotAd() {
		// 插播接口调用

		// 加载插播资源
		SpotManager.getInstance(context).loadSpotAds();
		// 插屏出现动画效果，0:ANIM_NONE为无动画，1:ANIM_SIMPLE为简单动画效果，2:ANIM_ADVANCE为高级动画效果
		SpotManager.getInstance(context).setAnimationType(
				SpotManager.ANIM_ADVANCE);
		// 设置插屏动画的横竖屏展示方式，如果设置了横屏，则在有广告资源的情况下会是优先使用横屏图。
		SpotManager.getInstance(context).setSpotOrientation(
				SpotManager.ORIENTATION_PORTRAIT);
		
		
			

				// 展示插播广告，可以不调用loadSpot独立使用
				SpotManager.getInstance(context).showSpotAds(context,
						new SpotDialogListener() {
							@Override
							public void onShowSuccess() {
								Log.i("YoumiAdDemo", "展示成功");
							}

							@Override
							public void onShowFailed() {
								Log.i("YoumiAdDemo", "展示失败");
							}

							@Override
							public void onSpotClosed() {
								Log.i("YoumiAdDemo", "展示关闭");
							}

							@Override
							public void onSpotClick(boolean isWebPath) {
								Log.i("YoumiAdDemo", "插屏点击");
							}

						});

			
	
	}

	private void showBanner() {

		// 实例化LayoutParams(重要)
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		// 设置广告条的悬浮位置
		layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT; // 这里示例为右下角
		// 实例化广告条
		AdView adView = new AdView(context, AdSize.FIT_SCREEN);
		// 调用Activity的addContentView函数

		// 监听广告条接口
		adView.setAdListener(new AdViewListener() {

			@Override
			public void onSwitchedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "广告条切换");
			}

			@Override
			public void onReceivedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "请求广告成功");
			}

			@Override
			public void onFailedToReceivedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "请求广告失败");
			}
		});
		((Activity) context).addContentView(adView, layoutParams);
	}

		@Override
		public void onBackPressed() {
			// 如果有需要，可以点击后退关闭插播广告。
			if (!SpotManager.getInstance(this).disMiss()) {
				// 弹出退出窗口，可以使用自定义退屏弹出和回退动画,参照demo,若不使用动画，传入-1
				super.onBackPressed();
			}
		}

		@Override
		protected void onStop() {
			// 如果不调用此方法，则按home键的时候会出现图标无法显示的情况。
			SpotManager.getInstance(this).onStop();
			super.onStop();
		}
		@Override
		protected void onDestroy() {
			SpotManager.getInstance(this).onDestroy();
			super.onDestroy();
		}

	public void change_db(String string_db)
	{
		this.change(string_db);
	}
	public void show_total(String text)
	{
		this.check_db(text);
	}
	public void init_db()
	{
		//对数据库情况摸底初始化，ABC_TEST存放表目录
		 DBAdapter dbAdapter;
			Cursor cursor_old;
		String db_name;
		    dbAdapter=new DBAdapter(this,"ABC_TEST");
			dbAdapter.open();
			cursor_old=dbAdapter.getAllData();
			Log.i("getAllData", "main");
			cursor_old.moveToPosition(0);
			db_name=cursor_old.getString(cursor_old.getColumnIndex(DBAdapter.EXPR1));
			Log.i("chushihua", db_name+"");
			dbAdapter.close();
		if(db_name=="")
		{
			db_name="三基本（2016）";
		}
		main_one.setText(db_name);
		this.check_db(db_name);
	}
	//
	
	public void update_dbname(String name)
	{
		DBAdapter dbAdapter1=new DBAdapter(this,"ABC_TEST");
		dbAdapter1.open();			
		ContentValues v = new ContentValues();
		v.put("EXPR1", name);
		dbAdapter1.update_data(v);
		dbAdapter1.close();
	}
	//text是数据库的中文名称
    public void check_db(String text)
    {
    	 DBAdapter dbAdapter = new DBAdapter(this);
			//dbAdapter.changetable("StudySysterm");
			dbAdapter.open();
			Cursor cursor;
			String db_name="";
			cursor=dbAdapter.querytestdb(text);
			if(cursor.moveToFirst())
			{
					main_one.setText(text);
					update_dbname(main_one.getText().toString());
					db_name = cursor.getString(cursor. getColumnIndex("test_db"));		
					cursor.close();
					dbAdapter.close();
					if(db_name==""||db_name==null)
					{
						Toast.makeText(this, text+"目前还未开发", Toast.LENGTH_LONG).show();
					}
					else
					{
						DBAdapter dbAdapter2 = new DBAdapter(this,db_name);
						 dbAdapter2.open();
						 Cursor cursor2;
						 cursor2 = dbAdapter2.getAllData();
						 Toast.makeText(this, text+"题库共计"+cursor2.getCount()+"题", Toast.LENGTH_LONG).show();
						 dbAdapter2.close();
					}
					
			}
			else
			{
				Toast.makeText(this, text+"目前还未开发", Toast.LENGTH_LONG).show();
			}
				
	}
    //简化chang_db
public void change(String text)
{
	 DBAdapter dbAdapter = new DBAdapter(this);
		//dbAdapter.changetable("StudySysterm");
		dbAdapter.open();
		Cursor cursor;
		String db_name="";
		cursor=dbAdapter.querytestdb(text);
		if(cursor.moveToFirst())
		{
				main_one.setText(text);
				update_dbname(main_one.getText().toString());
				db_name = cursor.getString(cursor. getColumnIndex("test_db"));
				my_db=db_name;
				cursor.close();
				dbAdapter.close();
		}
		else
		{
			Toast.makeText(this, "切换题库失败", Toast.LENGTH_LONG).show();
		}
	}
//更新响应函数
@Override
public void onCheckAppUpdateFinish(AppUpdateInfo updateInfo) {
    // 检查更新回调，注意，这里是在 UI 线程回调的，因此您可以直接与 UI 交互，但不可以进行长时间的操作（如在这里访问网络是不允许的）
    if (updateInfo == null || updateInfo.getUrl() == null) {
        // 当前已经是最新版本
    	//finish();
    }
    else {
        // 有更新信息，开发者应该在这里实现下载新版本
    	final String  my_url=updateInfo.getUrl().toString();
    	 new AlertDialog.Builder(context)
         .setTitle("发现新版本")
         .setMessage(updateInfo.getUpdateTips()) // 这里是版本更新信息
         .setNegativeButton("马上升级",
             new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse(my_url) );
                     intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     context.startActivity(intent);
                     // ps：这里示例点击“马上升级”按钮之后简单地调用系统浏览器进行新版本的下载，
                     // 但强烈建议开发者实现自己的下载管理流程，这样可以获得更好的用户体验。
                 }
         })
         .setPositiveButton("下次再说",
             new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     dialog.cancel();
                 }
         }).create().show();
    }
}
}

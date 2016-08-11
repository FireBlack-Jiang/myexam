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
	//��������
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
   public static final int sanjiben_db=1;  //�������ݿ�
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
	         context = this;//��溯������
	        
	    	//UpdateActivity my_update=new UpdateActivity();//������
	         /**
	          * ���ñ������������½���ʱ��ص�CheckAppUpdateCallBack�ӿ��еĻص�����
	          */	      
	        AdManager.getInstance(this).asyncCheckAppUpdate(this);
	    	button_main_open_left = (ImageButton) findViewById(R.id.button_main_open_left);
	        button_main_open_right = (ImageButton) findViewById(R.id.button_main_open_right);
	        main_one = (TextView) findViewById(R.id.main_one);
	        button_main_open_left.setOnClickListener(this);
	        button_main_open_right.setOnClickListener(this);
	        setBehindContentView(R.layout.menu_frame);//����������
	        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new MenuFragment()).commit();
	        //�����涯��
	        
	        mTransformer = new CanvasTransformer() {
				
				@Override
				public void transformCanvas(Canvas canvas, float percentOpen) {
//					float scale = (float) (percentOpen*0.25 + 0.75); 
					float scale = (float) (1 - percentOpen * 0.25);
	                canvas.scale(scale, scale, canvas.getWidth()/2, canvas.getHeight()/2);              
				}
			};
			//�����㶯��
			mTransformer2 = new CanvasTransformer() {
				
				@Override
				public void transformCanvas(Canvas canvas, float percentOpen) {
					float scale = (float) (percentOpen * 0.25 + 0.75);
	                canvas.scale(scale, scale, 0,
	                        canvas.getHeight() / 2);           
				}
			};
			/**
			������slidingmenu���ò���
			 */
	        sm = getSlidingMenu();
	        sm.setSecondaryMenu(R.layout.menu_frame2);////�����Ҳ����
   
	        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame2, new MenuFragment2()).commit();
	        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);//SlidingMenu����ʱ��ҳ����ʾ��ʣ����
	        sm.setFadeEnabled(false);
	        sm.setBehindScrollScale(0.25f);
	        sm.setFadeDegree(0.75f);
	        sm.setMode(SlidingMenu.LEFT_RIGHT);
      //  sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	        sm.setBackgroundImage(R.drawable.rootblock_default_bg);
	        sm.setBehindWidth((int)(getWindowManager().getDefaultDisplay().getWidth() / 1));//1����ȫ��
	        sm.setBehindCanvasTransformer(mTransformer2);
	        sm.setAboveCanvasTransformer(mTransformer);
	       
	    //�����ǻ������ֳ�ʼ��
	
		
		
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
	     //���
	   
	  // ���ز岥��Դ
	  			//setSpotAd();
	   //����ʼ��
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
		 * �ҵĴ���
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
		 * ����
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
		//3��ѡ1��ѡ2�ж�ר����ϰ��ť
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
		//��ѡ
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
		//΢�ŷ���
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
		// TODO ������� �޸�testype
		DBAdapter dbAdapter;
		Cursor cursor;
		try {
			dbAdapter = new DBAdapter(this,"ABC_TEST");
			dbAdapter.open();
			cursor = dbAdapter.getAllData();
			cursor.moveToFirst();
			 Log.i("GetAllData","��ʼ��");
				String Version=cursor.getString(cursor.getColumnIndex(DBAdapter.TESTTPYE));
				//myopencount ��ȡtestanswer������������
				int myopencount=cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTANSWER));
				int isshared=cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTID));//isshare�������ã�Ĭ��isshareΪ1��ʾδ����
				
				//ÿ�򿪳������Σ�����һ�Ρ��򿪴�������10�κ����ʾ�������
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
						.setTitle("ע�⣡")
						.setMessage("�������ݣ���ȷ�������Ե�5�롭��")
						.setPositiveButton(
								"ȷ��",
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
			Toast.makeText(this, "����������......��֧�ַ���лл", Toast.LENGTH_LONG).show();
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
		builder.setMessage("ȷ���˳���");

		builder.setTitle("��ʾ");

		builder.setPositiveButton("ȷ��",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface v, int which) {
						// TODO Auto-generated method stub
						// dialog.dismiss();
						finish();
					}
				});
		builder.setNegativeButton("ȡ��",
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
			// Toast.makeText(this, "�޸Ĵ���", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button_main_open_left:
			
			//ͨ����ABC_TEST���ֶ�testtips��ʱ������ݣ���ǰʹ�õ����ݱ�
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

	//SHOW CONTENT 或MENU
	public void setToggle(){
		toggle();
	}
	@Override
	public void SuccessText(String text) {
		// TODO Auto-generated method stub
		show_total(text);
	}
	
	
	//��溯��
	
	///����ຯ��
	
	private void setSpotAd() {
		// �岥�ӿڵ���

		// ���ز岥��Դ
		SpotManager.getInstance(context).loadSpotAds();
		// �������ֶ���Ч����0:ANIM_NONEΪ�޶�����1:ANIM_SIMPLEΪ�򵥶���Ч����2:ANIM_ADVANCEΪ�߼�����Ч��
		SpotManager.getInstance(context).setAnimationType(
				SpotManager.ANIM_ADVANCE);
		// ���ò��������ĺ�����չʾ��ʽ����������˺����������й����Դ������»�������ʹ�ú���ͼ��
		SpotManager.getInstance(context).setSpotOrientation(
				SpotManager.ORIENTATION_PORTRAIT);
		
		
			

				// չʾ�岥��棬���Բ�����loadSpot����ʹ��
				SpotManager.getInstance(context).showSpotAds(context,
						new SpotDialogListener() {
							@Override
							public void onShowSuccess() {
								Log.i("YoumiAdDemo", "չʾ�ɹ�");
							}

							@Override
							public void onShowFailed() {
								Log.i("YoumiAdDemo", "չʾʧ��");
							}

							@Override
							public void onSpotClosed() {
								Log.i("YoumiAdDemo", "չʾ�ر�");
							}

							@Override
							public void onSpotClick(boolean isWebPath) {
								Log.i("YoumiAdDemo", "�������");
							}

						});

			
	
	}

	private void showBanner() {

		// ʵ����LayoutParams(��Ҫ)
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		// ���ù����������λ��
		layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT; // ����ʾ��Ϊ���½�
		// ʵ���������
		AdView adView = new AdView(context, AdSize.FIT_SCREEN);
		// ����Activity��addContentView����

		// ����������ӿ�
		adView.setAdListener(new AdViewListener() {

			@Override
			public void onSwitchedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "������л�");
			}

			@Override
			public void onReceivedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "������ɹ�");
			}

			@Override
			public void onFailedToReceivedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "������ʧ��");
			}
		});
		((Activity) context).addContentView(adView, layoutParams);
	}

		@Override
		public void onBackPressed() {
			// �������Ҫ�����Ե�����˹رղ岥��档
			if (!SpotManager.getInstance(this).disMiss()) {
				// �����˳����ڣ�����ʹ���Զ������������ͻ��˶���,����demo,����ʹ�ö���������-1
				super.onBackPressed();
			}
		}

		@Override
		protected void onStop() {
			// ��������ô˷�������home����ʱ������ͼ���޷���ʾ�������
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
		//�����ݿ�������׳�ʼ����ABC_TEST��ű�Ŀ¼
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
			db_name="��������2016��";
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
	//text�����ݿ����������
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
						Toast.makeText(this, text+"Ŀǰ��δ����", Toast.LENGTH_LONG).show();
					}
					else
					{
						DBAdapter dbAdapter2 = new DBAdapter(this,db_name);
						 dbAdapter2.open();
						 Cursor cursor2;
						 cursor2 = dbAdapter2.getAllData();
						 Toast.makeText(this, text+"��⹲��"+cursor2.getCount()+"��", Toast.LENGTH_LONG).show();
						 dbAdapter2.close();
					}
					
			}
			else
			{
				Toast.makeText(this, text+"Ŀǰ��δ����", Toast.LENGTH_LONG).show();
			}
				
	}
    //��chang_db
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
			Toast.makeText(this, "�л����ʧ��", Toast.LENGTH_LONG).show();
		}
	}
//������Ӧ����
@Override
public void onCheckAppUpdateFinish(AppUpdateInfo updateInfo) {
    // �����»ص���ע�⣬�������� UI �̻߳ص��ģ����������ֱ���� UI �������������Խ��г�ʱ��Ĳ���������������������ǲ�����ģ�
    if (updateInfo == null || updateInfo.getUrl() == null) {
        // ��ǰ�Ѿ������°汾
    	//finish();
    }
    else {
        // �и�����Ϣ��������Ӧ��������ʵ�������°汾
    	final String  my_url=updateInfo.getUrl().toString();
    	 new AlertDialog.Builder(context)
         .setTitle("�����°汾")
         .setMessage(updateInfo.getUpdateTips()) // �����ǰ汾������Ϣ
         .setNegativeButton("��������",
             new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse(my_url) );
                     intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     context.startActivity(intent);
                     // ps������ʾ�������������������ť֮��򵥵ص���ϵͳ����������°汾�����أ�
                     // ��ǿ�ҽ��鿪����ʵ���Լ������ع������̣��������Ի�ø��õ��û����顣
                 }
         })
         .setPositiveButton("�´���˵",
             new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     dialog.cancel();
                 }
         }).create().show();
    }
}
}

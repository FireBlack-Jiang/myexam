package abc.myexam;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.graphics.Color;
import android.graphics.Paint;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MotionEvent;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class E_zhuanxiang extends Activity {
	public  int problemLimit;
	public int problemless;
	public String WAsetFilename;
	public static final String label = "label";
	int curIndex;
	String myAnswer;
	String data;
	
	List<Integer> myWAset1=new ArrayList<Integer>();
		List problemTurn1=new ArrayList();
	int Option;// 表示是随机 or 顺序
	int labelProblemID;
    int ex_record; //记录做题记录
	String TESTSUBJECT;
	String TESTANSWER;
	String ANSWERA;
	String ANSWERB;
	String ANSWERC;
	String ANSWERD;
	String ANSWERE;
	String ANSWERF;
	String IMAGENAME;

	int TESTTPYE;
	int TESTBELONG;
	int EXPR1;
    float textsizevalue;
    LinearLayout new_exerciselayout_mylinearlayout;
	boolean autoCheck;
	boolean auto2next;
	boolean auto2addWAset;
	EditText editText;
	TextView proTextView;
	ImageButton myfabButton;
	ImageView imageview;
	RadioButton radioA;
	RadioButton radioB;
	RadioButton radioC;
	RadioButton radioD;
	RadioGroup radioGroup;
	CheckBox CheckBoxA;
	CheckBox CheckBoxB;
	CheckBox CheckBoxC;
	CheckBox CheckBoxD;
	CheckBox CheckBoxE;
	CheckBox CheckBoxF;
	ScrollView myscroll;
	Button forword_btn;
	Button next_btn;
	Button check_btn;
	Button addWAset_btn;
	TextView promptText;
	Button title_count;
    TextView title_text;
	Cursor cursor;
	Cursor cursor_order;
	DBAdapter dbAdapter;
   Button fankuiButton;
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;
	// InputStream in;
	// BufferedReader br;
	FileInputStream fis;
	FileOutputStream fos;
	//手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
		float x1 = 0;
		float x2 = 0;
		float y1 = 0;
		float y2 = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//setContentView(R.layout.exerciselayout);
		setContentView(R.layout.new_exerciselayout);
		Init();// 图像、数据初始化
		settingInit();// 配置设定
		OnPaint();// 重绘
		forword_btn.setOnClickListener(new OnClickListener() {
			// 上一题
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (curIndex == 0) {
					ShowToast("当前为第一题");
				} else {
						curIndex--;
						title_int();
						OnPaint();
				}
			}

		});
		/*
		 * 下一题
		 */
		next_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (curIndex == problemless - 1) {
					ShowToast("当前为最后一题");
				} else {
						curIndex++;
						title_int();
						OnPaint();
				}
			}
		});
		/*
		 * 悬浮按钮
		 */
		myfabButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			try {
				dbAdapter.close();
			} catch (Exception e) {
				// TODO: handle exception
			}				
				Intent to_mdf_exam_table=new Intent();
				to_mdf_exam_table.putExtra("mydbtable",data);
				to_mdf_exam_table.putExtra("mydbname", title_name(data));
				to_mdf_exam_table.putExtra("indexNO", curIndex);
				to_mdf_exam_table.putExtra("Option", Option);
				to_mdf_exam_table.setClass(E_zhuanxiang.this, Mdf_exam_table.class);
				//startActivity(to_mdf_exam_table);
				startActivityForResult(to_mdf_exam_table, 1);
			}
		});
		/*
		 * 确认按钮
		 */
		check_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int answerNum = radioGroup.getCheckedRadioButtonId();
				if(TESTTPYE==3)
				{
					myAnswer="";
				 if(CheckBoxA.isChecked())
				 { 
					 myAnswer=myAnswer+"A";
				 }
				 if(CheckBoxB.isChecked())
				 { 
					 myAnswer=myAnswer+"B";
				 }
				 if(CheckBoxC.isChecked())
				 { 
					 myAnswer=myAnswer+"C";
				 }
				 if(CheckBoxD.isChecked())
				 { 
					 myAnswer=myAnswer+"D";
				 }	 
				 if(CheckBoxE.isChecked())
				 { 
					 myAnswer=myAnswer+"E";
				 }	
				 if(CheckBoxF.isChecked())
				 { 
					 myAnswer=myAnswer+"F";
				 }
				}
				else
				{
					switch (answerNum) {
					case R.id.radioA:
						myAnswer = "A";
						break;
					case R.id.radioB:
						myAnswer = "B";
						break;
					case R.id.radioC:
						myAnswer = "C";
						break;
					case R.id.radioD:
						myAnswer = "D";
						break;
					case -1:
						myAnswer = "";
					default:
						myAnswer = "";
						break;
					}
				
				}
				if (TESTTPYE==2) {
					if (myAnswer == "A") {
						myAnswer = "对";
					} else if(myAnswer == "C"){
						myAnswer = "错";
					} 
					else 
					{
						myAnswer="";
					}
				}
				// ShowToast(myAnswer + " " + TESTANSWER);
				if (myAnswer.compareTo(TESTANSWER) == 0) {
					promptText.setText(R.string.prompt_right);
					promptText.setVisibility(View.VISIBLE);
					promptText.setTextColor(Color.GREEN);
					if (auto2next) {
						next_btn.performClick();
						
					}
				} else {
					promptText.setText(R.string.prompt_wrong);
					String tips="";
					String flag="true";
					try
					{
						tips=cursor.getString(cursor.getColumnIndex(DBAdapter.TESTTIP));
					}	
					catch (Exception e)
					{
						tips="";
						flag="false";
					}
					if(tips==""||flag=="false"||tips==null)
					{
						
						promptText.setText(promptText.getText().toString()
								+ TESTANSWER);
					}
					else
						{
						promptText.setText(promptText.getText().toString()
								+ TESTANSWER+"(答案依据："+tips+")");
						}
					promptText.setVisibility(View.VISIBLE);
					promptText.setTextColor(Color.RED);
					if (Option != MainActivity.OPTION_WRONGEXERCISE
							&& auto2addWAset) {
						addWAset_btn.performClick();
					}
				}
				ex_record=curIndex;//记录
			}
		});

		/*
		 * 加入错题库
		 */
		addWAset_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String m=(String)problemTurn1.get(curIndex).toString();
			    // int my_index=Integer.valueOf(m);
			     int xuhao=cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTID));
			     int my_index=xuhao-1;
				if (Option == MainActivity.OPTION_WRONGEXERCISE) {					

					//myWAset[my_index] = 0;
					myWAset1.set(my_index, 0);
					saveWaset();
					ShowToast("移除成功");
				} 
				else {
					//myWAset[my_index] = 1;
					//myWAset1.add(my_index, (int)1);
					//myWAset1.add(my_index);
					//saveWaset();
					myWAset1.set(my_index, 1);
					ShowToast("加入成功");
					saveWaset();
				}
			}
		});

		/*
		 * 选择radio
		 */
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						if (autoCheck
								&& (radioA.isChecked() || radioB.isChecked()
										|| radioC.isChecked() || radioD
										.isChecked())) {
							check_btn.performClick();
						}
					}

				});
		title_count.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
					ShowDialog2JumpByIndex();
				
			}});
		fankuiButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			try
			{
				String phone="13689674738";
				int fankui_type;
				String message="";
				fankui_type=cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE));
				if(fankui_type==1)
				{
					message="（单选题）：";
				}
				else if(fankui_type==2)
				{
					message="（判断题）：";
				}
				else
				{
					message="（多选题）：";
				}
				message="反馈>>"+getVersion()+"题库："+title_name(data)+"题型："+message;
				String fankui_num=cursor.getString(cursor.getColumnIndex(DBAdapter.TESTID));
				message=message+"第"+fankui_num+"题.."+cursor.getString(cursor.getColumnIndex(DBAdapter.TESTSUBJECT))+"----经本人鉴定有误，请更正";
				Uri uri = Uri.parse("smsto:" + phone);
			    Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
			    sendIntent.putExtra("sms_body", message);
			    startActivity(sendIntent);
			}	
			catch (Exception e) {
				//客户关闭短信发送权限反馈若失败提示
				ShowToast("权限不够，反馈失败，请到微信公众号反馈");
			}
			}
			});
	}
	/**
	 * 获取版本号
	 * @return 当前应用的版本号
	 */
	public String getVersion() {
	    try {
	        PackageManager manager = this.getPackageManager();
	        PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
	        String version = info.versionName;
	        return this.getString(R.string.app_name) + version;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return this.getString(R.string.err_getversion);
	    }
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		if (Option == MainActivity.OPTION_ORDER) {
			menu.add(0, 1, 1, "跳转到指定题号");
			menu.add(0, 2, 2, "跳转到标签");
			menu.add(0, 3, 3, "存为标签");
		}
		menu.add(0, 4, 4, "设置");
		return super.onCreateOptionsMenu(menu);
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == 1) {
			ShowDialog2JumpByIndex();
		} else if (item.getItemId() == 2) {
			jumpAction(labelProblemID);
		} else if (item.getItemId() == 3) {
			labelProblemID = curIndex + 1;
		} else if (item.getItemId() == 4) {
			startActivity(new Intent().setClass(E_zhuanxiang.this,
					OptionActivity.class));
			// settingInit();//注意是并行操作的，就是说不是等回来后在执行的，然后并行的，所以这个
			// 方法应该在restart时才执行才对。
		}
		return super.onOptionsItemSelected(item);
	}

	public boolean jumpAction(int jump2ID) {
		if (jump2ID > 0 && jump2ID <= problemless) {
			curIndex = jump2ID - 1;
			title_int();
			OnPaint();
			return true;
		} else {
			return false;
		}

	}

	public void ShowDialog2JumpByIndex() {
		editText = new EditText(this);
		// editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
		// editText.setInputType("number");
		editText.setKeyListener(new DigitsKeyListener(false, true));
		editText.setHint("请输入要跳转的题号");
		new AlertDialog.Builder(this)
				.setTitle("请输入")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setView(editText)
				.setPositiveButton("确定",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

								if (!jumpAction(Integer.parseInt(editText
										.getText().toString()))) {
									ShowToast("指定题号不存在");
								}
							}
						}).setNegativeButton("取消", null).show();
	}

	public void ShowToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	public void Init() {
	
		Bundle bundle = getIntent().getExtras();
		Option = bundle.getInt("option");
		 data=bundle.getString("data");//题库选择
		 problemLimit=t_count(data);
			//1单选2判断3多选
		if(Option ==3)
		{			
		    dbAdapter=new DBAdapter(this,data);
			dbAdapter.open();
			cursor_order=dbAdapter.getdatabytype(""+Option);
			cursor_order.moveToPosition(0);
			problemless=cursor_order.getCount();
			int i=cursor_order.getInt(cursor_order.getColumnIndex(DBAdapter.EXPR1));
			curIndex=i;
		}//单选
		if (Option == 1) {
			
			 dbAdapter=new DBAdapter(this,data);
				dbAdapter.open();
				cursor_order=dbAdapter.getdatabytype(""+Option);
				cursor_order.moveToPosition(0);
				problemless=cursor_order.getCount();
				int i=cursor_order.getInt(cursor_order.getColumnIndex(DBAdapter.EXPR1));
				curIndex=i;
			}//判断
		if (Option == 2) {	
			    dbAdapter=new DBAdapter(this,data);
				dbAdapter.open();
				cursor_order=dbAdapter.getdatabytype(""+Option);
				cursor_order.moveToPosition(0);
				problemless=cursor_order.getCount();
				int i=cursor_order.getInt(cursor_order.getColumnIndex(DBAdapter.EXPR1));
				curIndex=i;
			}
		//错题库初始化
		init_WAset();
		WAsetFilename=data;
		proTextView = (TextView) findViewById(R.id.pro_text);
		imageview = (ImageView) findViewById(R.id.imageview);
		radioA = (RadioButton) findViewById(R.id.radioA);
		radioB = (RadioButton) findViewById(R.id.radioB);
		radioC = (RadioButton) findViewById(R.id.radioC);
		radioD = (RadioButton) findViewById(R.id.radioD);
		CheckBoxA=(CheckBox) findViewById(R.id.CheckBoxA);
		CheckBoxB=(CheckBox) findViewById(R.id.CheckBoxB);
		CheckBoxC=(CheckBox) findViewById(R.id.CheckBoxC);
		CheckBoxD=(CheckBox) findViewById(R.id.CheckBoxD);
		CheckBoxE=(CheckBox) findViewById(R.id.CheckBoxE);
		CheckBoxF=(CheckBox) findViewById(R.id.CheckBoxF);
		forword_btn = (Button) findViewById(R.id.forwordBtn);
		next_btn = (Button) findViewById(R.id.nextBtn);
		check_btn = (Button) findViewById(R.id.checkBtn);
		addWAset_btn = (Button) findViewById(R.id.addWAsetBtn);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		promptText = (TextView) findViewById(R.id.promptText);
		fankuiButton=(Button) findViewById(R.id.fankuiBtn);
		myscroll=(ScrollView)findViewById(R.id.myScrollView);//myScrollView
		myfabButton=(ImageButton)findViewById(R.id.customFAB2);
		new_exerciselayout_mylinearlayout=(LinearLayout)findViewById(R.id.new_exerciselayout_mylinearlayout);
		for (int i = 0; i <problemless; i++) {
			problemTurn1.add(i);
			
		}
	
		//配置获取编辑器
				try {
		dbAdapter=new DBAdapter(this,data);
	    dbAdapter.open();
	   sharedPreferences = getSharedPreferences(
	MainActivity.PREFERENCE_NAME, MainActivity.MODE);// SharedPreferences存储方式:为0只能被本程序访问
	editor = sharedPreferences.edit();
					// SharedPreferences.Editor editor = sharedPreferences.edit();
				} catch (Exception e) {
					// TODO: handle exception
		Log.i("Init", "初始化配置数据失败!");
		}
				//错题读取
				try {
					String Text = "";
					fis = openFileInput(WAsetFilename);
					byte[] readBytes = new byte[fis.available()];
					while (fis.read(readBytes) !=-1&&fis.read(readBytes) !=0) {
						Log.i("fis read result", fis.read(readBytes)+"");
						Text = new String(readBytes);
					}
					String[] tmp_waset = Text.split("#");
					String tmpString;
					if (tmp_waset[0].compareTo("") != 0) {
						for (int i = 0; i < tmp_waset.length; i++) {
							tmpString = tmp_waset[i].substring(0,tmp_waset[i].indexOf('.'));
							// ShowToast(tmpString);
							myWAset1.set(Integer.parseInt(tmpString) - 1, 1);
							
							//myWAset[Integer.parseInt(tmpString) - 1] = 1;
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					// ShowToast(e.toString());
				}

				  dbAdapter=new DBAdapter(this,data);
				dbAdapter.open();
				cursor=dbAdapter.getdatabytype(""+Option);
				this.title_int();
	} 

	public void settingInit() {
		try
		{
			autoCheck = sharedPreferences.getBoolean(MainActivity.CONFIG_AUTOCHECK,
					false);// 自动确认，从配置文件读取。
			labelProblemID = sharedPreferences.getInt(label, 0);// 到时候从配置文件读
			auto2next = sharedPreferences.getBoolean(MainActivity.CONFIG_AUTO2NEXT,
					false);
			auto2addWAset = sharedPreferences.getBoolean(
					MainActivity.CONFIG_AUTO2ADDWRONGSET, false);
			textsizevalue=sharedPreferences.getFloat(MainActivity.CONFIG_TEXTSIZE, 43);
		}
		catch(Exception ex){
			ex.printStackTrace();
			}

	}

	public void OnPaint() {
		if (cursor.getCount() == 0) {
			Toast.makeText(this, "我靠，没有题，玩个屁！", Toast.LENGTH_LONG).show();
		} 
		else 
		{
			/*
			 * 初始化View
			 */
		     String m=(String)problemTurn1.get(curIndex).toString();
		     Log.i("m", m);
		    int my_index=Integer.valueOf(m);
			cursor.moveToPosition(my_index);	
			radioGroup.clearCheck();
			CheckBoxA.setChecked(false);
			CheckBoxC.setChecked(false);
			CheckBoxB.setChecked(false);
			CheckBoxD.setChecked(false);
			CheckBoxE.setChecked(false);
			CheckBoxF.setChecked(false);
			TESTSUBJECT = cursor.getString(cursor.getColumnIndex(DBAdapter.TESTSUBJECT));
			TESTSUBJECT = TESTSUBJECT.replace("“|”", "下图");
			TESTANSWER = cursor.getString(cursor
					.getColumnIndex(DBAdapter.TESTANSWER));
			IMAGENAME = cursor.getString(cursor
					.getColumnIndex(DBAdapter.IMAGENAME));
			TESTTPYE = cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE));
			proTextView
					.setText((my_index + 1) + "." + TESTSUBJECT);
			// addWAset_btn.setText("结果");

			promptText.setVisibility(View.GONE);
			promptText.setText("");
			// Toast.makeText(this, IMAGENAME+"--"+IMAGENAME.length(),
			// Toast.LENGTH_LONG).show();

			// 图片处理
			if (IMAGENAME.compareTo("image") != 0) {
				InputStream inputStream;
				try {
					IMAGENAME = IMAGENAME.replace('-', '_');
					// Toast.makeText(this, IMAGENAME,
					// Toast.LENGTH_LONG).show();
					inputStream = super.getAssets().open(IMAGENAME);
					imageview.setImageDrawable(Drawable.createFromStream(
							inputStream, "assets"));
					imageview.setVisibility(View.VISIBLE);
					// imageview.setImageDrawable(Drawable.createFromPath("res.drawable."+IMAGENAME+".jpg"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			} else {
				imageview.setVisibility(View.GONE);
			}
			ANSWERA = cursor
					.getString(cursor.getColumnIndex(DBAdapter.ANSWERA));
			
			
			ANSWERB = cursor
					.getString(cursor.getColumnIndex(DBAdapter.ANSWERB));
			ANSWERC = cursor
					.getString(cursor.getColumnIndex(DBAdapter.ANSWERC));
			ANSWERD = cursor
					.getString(cursor.getColumnIndex(DBAdapter.ANSWERD));
			ANSWERE = cursor
					.getString(cursor.getColumnIndex(DBAdapter.ANSWERE));
			ANSWERF = cursor
					.getString(cursor.getColumnIndex(DBAdapter.ANSWERF));
			//当类型不是判断的时候
			if (cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE))!= 2) {
				if (ANSWERA.substring(0, 1).compareTo("A")==0) {
					ANSWERA=ANSWERA.substring(2);
				}
				if (ANSWERB!=""&&ANSWERB.substring(0, 1).compareTo("B")==0) {
					ANSWERB=ANSWERB.substring(2);
				}
				if (ANSWERC!=""&&ANSWERC.substring(0, 1).compareTo("C")==0) {
					ANSWERC=ANSWERC.substring(2);
				}
				if (ANSWERD!=""&&ANSWERD.substring(0, 1).compareTo("D")==0) {
					ANSWERD=ANSWERD.substring(2);
				}
			}
			
			
			 if(cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE)) == 1) {
				// 选择题
				radioA.setText("A." + ANSWERA);
				radioB.setText("B." + ANSWERB);
				radioC.setText("C." + ANSWERC);
				radioD.setText("D." + ANSWERD);
				radioA.setVisibility(View.VISIBLE);
				radioB.setVisibility(View.VISIBLE);
				radioC.setVisibility(View.VISIBLE);
				radioD.setVisibility(View.VISIBLE);
				CheckBoxA.setVisibility(View.GONE);
				CheckBoxB.setVisibility(View.GONE);
				CheckBoxC.setVisibility(View.GONE);
				CheckBoxD.setVisibility(View.GONE);
				CheckBoxE.setVisibility(View.GONE);
				CheckBoxF.setVisibility(View.GONE);
			}

			 else if(cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE)) == 3)
			{
				CheckBoxA.setText("A."+ANSWERA);
				CheckBoxB.setText("B."+ANSWERB);
				CheckBoxC.setText("C."+ANSWERC);
				CheckBoxD.setText("D."+ANSWERD);
				CheckBoxE.setText("E."+ANSWERE);
				CheckBoxF.setText("F."+ANSWERF);
				radioA.setVisibility(View.GONE);
				radioB.setVisibility(View.GONE);
				radioC.setVisibility(View.GONE);
				radioD.setVisibility(View.GONE);
				CheckBoxA.setVisibility(View.VISIBLE);
				CheckBoxB.setVisibility(View.VISIBLE);
				CheckBoxC.setVisibility(View.VISIBLE);
				CheckBoxD.setVisibility(View.VISIBLE);
				if(ANSWERE==null||ANSWERE.compareTo("") == 0)
				{
					CheckBoxE.setVisibility(View.GONE);
					CheckBoxF.setVisibility(View.GONE);
				}
				else if(ANSWERF==null||ANSWERF.compareTo("") == 0)
				{
					CheckBoxE.setVisibility(View.VISIBLE);
					CheckBoxF.setVisibility(View.GONE);
				}
				else 
				{
					CheckBoxE.setVisibility(View.VISIBLE);
					CheckBoxF.setVisibility(View.VISIBLE);
				}
				
			}
			 else 
				 
				// if (cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE)) == 2) 
				 {
				// 判断题
				radioA.setText("对");
				radioC.setText("错");
				radioA.setVisibility(View.VISIBLE);
				radioC.setVisibility(View.VISIBLE);
				radioB.setVisibility(View.GONE);
				radioD.setVisibility(View.GONE);
				CheckBoxA.setVisibility(View.GONE);
				CheckBoxB.setVisibility(View.GONE);
				CheckBoxC.setVisibility(View.GONE);
				CheckBoxD.setVisibility(View.GONE);
				CheckBoxE.setVisibility(View.GONE);
				CheckBoxF.setVisibility(View.GONE);
			}
		}
		CommanOperation.ChangeTextSizeOp(this, new_exerciselayout_mylinearlayout, textsizevalue+"");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		settingInit();
		super.onRestart();
	}

	public void saveWaset() {
		try {
			DBAdapter dbAdapter_s=new DBAdapter(this,data);
				dbAdapter_s.open();
				Cursor C_S=dbAdapter_s.getAllData();
				String text = "";
			fos = openFileOutput(WAsetFilename, MODE_PRIVATE);
			for (int i = 0; i < problemLimit; i++) {
				if (list_myWAset(i)==1) {
					// cursor.moveToPosition(i);
					C_S.moveToPosition(i);
				 int xuhao=C_S.getInt(C_S.getColumnIndex(DBAdapter.TESTID));
				String m_text="";
				m_text=C_S.getString(C_S.getColumnIndex(DBAdapter.TESTSUBJECT));
					text += (xuhao)+ "."+m_text+ "#";
				}
			}
			if (text.compareTo("") == 0)	
				{text = "#";}
			
			
			fos.write(text.getBytes());
			dbAdapter_s.close();
		} catch (Exception e) {
			// TODO: handle exception
			ShowToast(e.toString());
		} finally {
			if (fos != null) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void onDestroy() {
		this.savejindu();//保存进度
		super.onDestroy();
	}
	
	
	public boolean onTouchEvent1(MotionEvent event)  
	  
	    {  
  
		//继承了Activity的onTouchEvent方法，直接监听点击事件
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			//当手指按下的时候
			x1 = event.getX();
			y1 = event.getY();
		}
		if(event.getAction() == MotionEvent.ACTION_UP) {
			//当手指离开的时候
			x2 = event.getX();
			y2 = event.getY();
			
			if(x1 - x2 > 110) {
				next_btn.performClick();
				//Toast.makeText(ExerciseActivity.this, "向左滑", Toast.LENGTH_SHORT).show();
				return true;
			} 
			else if(x2 - x1 > 110) {
				forword_btn.performClick();
				return true;
				//Toast.makeText(ExerciseActivity.this, "向右滑", Toast.LENGTH_SHORT).show();
			}
			else if(y2 - y1 > 110) {
				return false;
			//	Toast.makeText(ExerciseActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
			} else if(y1 - y2 >110) {
					//return super.onTouchEvent(true);
			return false;
					//Toast.makeText(ExerciseActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
				} 
				
		}
		//return super.onTouchEvent(event);
		return false;
	  
	   } 
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)  
	{
		if(this.onTouchEvent1(ev))
		{
			return false;
		}
		return super.dispatchTouchEvent(ev);
			
	}
	 public static void shuffle(List list)
	  {
	   int N = list.size();
	   for (int i = 0; i < N; i++)
	   {
	    int r = (new Random().nextInt(N)); // between
	    exch(list, i, r);
	   }
	   
	  }
	  public static void exch(List list, int i, int j)
	  {

	   String swap = (String)list.get(i).toString();
	   list.add(i, list.get(j).toString());
	   list.add(j, swap);
	  }
public  int list_myWAset(int tindex)
    {
	String m=(String)myWAset1.get(tindex).toString();
    int my_index=Integer.valueOf(m);
	return my_index;
	}
public int t_count(String data)
    {
	  dbAdapter=new DBAdapter(this,data);
		dbAdapter.open();
		cursor_order=dbAdapter.getAllData();
		int t=(int)cursor_order.getCount();
		
		Log.i("problemless", t+"");
		dbAdapter.close();
		return t;
	}
public void init_WAset()
{
	try {			
			for (int i = 0; i < problemLimit; i++) {	
				myWAset1.add(0);				
			}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		// ShowToast(e.toString());
	}
	}
private CharSequence title_name(String data2) {
	// TODO Auto-generated method stub
	CharSequence my_title = "";
	DBAdapter dbAdapter_name = new DBAdapter(this);
	//dbAdapter.changetable("StudySysterm");
	dbAdapter_name.open();
	Cursor cursor_name;
	cursor_name=dbAdapter_name.querytest_name(data2);
	if(cursor_name.moveToFirst())
	{
			
		my_title = cursor_name.getString(cursor_name. getColumnIndex("testname"));
			cursor_name.close();
			dbAdapter_name.close();
	}
	else
	{
		Toast.makeText(this, "获取题库失败", Toast.LENGTH_LONG).show();
	}
	return my_title;
}
public void title_int()
{
	title_text=(TextView)findViewById(R.id.title_text);
	title_count=(Button)findViewById(R.id.title_button);
	
	title_text.setText(title_name(data));
	title_count.setText((1+curIndex)+" / "+cursor.getCount());
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
	builder.setMessage("当前进度为："+(curIndex+1)+"。系统自动记录进度，确定退出吗？");

	builder.setTitle("退出提示");

	builder.setPositiveButton("确定",
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
public void savejindu()
{
	saveWaset();
    cursor_order.moveToFirst();
	
	int jindu=cursor.getColumnIndex(DBAdapter.TESTID);
	dbAdapter.close();
	
		Log.i("ex_record is", curIndex+"");
		Log.i("jindu is", jindu+"");
		if(jindu-1!=curIndex)
		{//ex_record
			//String i="";
			DBAdapter dbAdapter1=new DBAdapter(this,data);
			dbAdapter1.open();	
			cursor_order=null;
			cursor_order=dbAdapter1.getdatabytype(""+Option);
			cursor_order.moveToPosition(0);
			int i=cursor_order.getInt(cursor_order.getColumnIndex(DBAdapter.TESTID));
			String sql="update "+data+" set Expr1=? where TestID=?" ;
			String[] canshu={curIndex+"",i+""};
			dbAdapter1.update_myjindu(sql, canshu);
			dbAdapter1.close();	
		}
	
	}
//复写onactivityresult方法，重新装载数据；
   @Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	super.onActivityResult(requestCode, resultCode, data);
	if (resultCode==2) {
		if (requestCode==1) {
			dbAdapter.open();
			cursor=dbAdapter.getdatabytype(""+Option);
			OnPaint();
		}
		
	}
}

}


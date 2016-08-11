package abc.myexam;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.graphics.Color;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MotionEvent;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
  
public class ExerciseActivity extends Activity  {

	public  int problemLimit;
	public String WAsetFilename;
	public static final String label = "label";
	int curIndex;
	String myAnswer;
	String data;
	// int[] WAset = new int[900];// ��ǰ����
	//int[] myWAset = new int[problemLimit];// ��������
	List<Integer> myWAset1=new ArrayList<Integer>();
	// String[] mySelect = new String[900];// �����¼
	//int[] problemTurn = new int[problemLimit];
	List problemTurn1=new ArrayList();
	int Option;// ��ʾ����� or ˳��
	int labelProblemID;
    int ex_record; //��¼�����¼
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

	boolean autoCheck;
	boolean auto2next;
	boolean auto2addWAset;
	EditText editText;
	TextView proTextView;
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
	
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;
	// InputStream in;
	// BufferedReader br;
	FileInputStream fis;
	FileOutputStream fos;
	//��ָ���µĵ�Ϊ(x1, y1)��ָ�뿪��Ļ�ĵ�Ϊ(x2, y2)
		float x1 = 0;
		float x2 = 0;
		float y1 = 0;
		float y2 = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.exerciselayout);
		
		Init();// ͼ�����ݳ�ʼ��
		settingInit();// �����趨
		OnPaint();// �ػ�
		//myscroll.set
		//this.onMenuOpened();
		//this.onMenuOpened(BIND_AUTO_CREATE, (Menu) this);
		forword_btn.setOnClickListener(new OnClickListener() {
			// ��һ��
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (curIndex == 0) {
					ShowToast("��ǰΪ��һ��");
				} else {
					if (Option == MainActivity.OPTION_WRONGEXERCISE) {
						int tindex = curIndex;
						while (--tindex >= 0) {
							if (list_myWAset(tindex) == 1) {
								curIndex = tindex;
								title_int();
								OnPaint();
								return;
							}
						}
						ShowToast("��ǰΪ��һ��");
						return;
					} else {
						curIndex--;
						title_int();
						OnPaint();
					}
				}
			}

		});
		/*
		 * ��һ��
		 */
		next_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (curIndex == problemLimit - 1) {
					ShowToast("��ǰΪ���һ��");
				} else {
					if (Option == MainActivity.OPTION_WRONGEXERCISE) {
						int tindex = curIndex;
						while (++tindex < problemLimit) {
							
							if (list_myWAset(tindex) == 1) {
								curIndex = tindex;
								title_int();
								OnPaint();
								return;
							}
						}
						ShowToast("��ǰΪ���һ��");
						return;
					} else {
						curIndex++;
						title_int();
						OnPaint();
					}
				}
			}
		});

		/*
		 * ȷ�ϰ�ť
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
						myAnswer = "��";
					} else if(myAnswer == "C"){
						myAnswer = "��";
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
				} 
				else {
					promptText.setText(R.string.prompt_wrong);
					promptText.setVisibility(View.VISIBLE);
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
								+ TESTANSWER+"(�����ݣ�"+tips+")");
						}
					promptText.setTextColor(Color.RED);
					if (Option != MainActivity.OPTION_WRONGEXERCISE
							&& auto2addWAset) {
						addWAset_btn.performClick();
					}
				}
				
				ex_record=curIndex;//��¼
			}
		});

		/*
		 * ��������
		 */
		addWAset_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String m=(String)problemTurn1.get(curIndex).toString();
			     int my_index=Integer.valueOf(m);
			  
				if (Option == MainActivity.OPTION_WRONGEXERCISE) {					

					//myWAset[my_index] = 0;
					myWAset1.set(my_index, 0);
					saveWaset();
					ShowToast("�Ƴ��ɹ�");
				} 
				else {
					//myWAset[my_index] = 1;
					//myWAset1.add(my_index, (int)1);
					//myWAset1.add(my_index);
					//saveWaset();
					myWAset1.set(my_index, 1);
					ShowToast("����ɹ�");
				}
			}
		});

		/*
		 * ѡ��radio
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
				if(true)
				{
					ShowDialog2JumpByIndex();
				}
				else
				{
					ShowToast("�㲻��O(��_��)O");
				}
			}});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		if (Option == MainActivity.OPTION_ORDER) {
			menu.add(0, 1, 1, "��ת��ָ�����");
			menu.add(0, 2, 2, "��ת����ǩ");
			menu.add(0, 3, 3, "��Ϊ��ǩ");
		}
		menu.add(0, 4, 4, "����");
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
			startActivity(new Intent().setClass(ExerciseActivity.this,
					OptionActivity.class));
			// settingInit();//ע���ǲ��в����ģ�����˵���ǵȻ�������ִ�еģ�Ȼ���еģ��������
			// ����Ӧ����restartʱ��ִ�вŶԡ�
		}
		return super.onOptionsItemSelected(item);
	}

	public boolean jumpAction(int jump2ID) {
		if (jump2ID > 0 && jump2ID <= problemLimit) {
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
		editText.setHint("������Ҫ��ת�����");
		new AlertDialog.Builder(this)
				.setTitle("������")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setView(editText)
				.setPositiveButton("ȷ��",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

								if(editText.getText().toString()=="")
								{
									if (!jumpAction(Integer.parseInt(editText
											.getText().toString()))) {
										ShowToast("ָ����Ų�����");
									}
								}
								else
								{
									ShowToast("���������");
								}
								
							}
						}).setNegativeButton("ȡ��", null).show();
	}

	public void ShowToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	public void Init() {
	
		Bundle bundle = getIntent().getExtras();
		Option = bundle.getInt("option");
		 data=bundle.getString("data");//���ѡ��
			problemLimit=t_count(data);
			init_WAset();
			WAsetFilename=data;
			//cursor_orderֻΪ��ѯ
		if(Option == MainActivity.OPTION_ORDER)
		{			
		    dbAdapter=new DBAdapter(this,data);
			dbAdapter.open();
			cursor_order=dbAdapter.getAllData();
			 Log.i("GetAllData","EXERCISE");
			cursor_order.moveToPosition(0);
			int i=cursor_order.getInt(cursor_order.getColumnIndex(DBAdapter.EXPR1));
			curIndex=i;
		//	Log.println(i, "i", "i");
			Log.i("chushihua", i+"");
			dbAdapter.close();
		}
		//��������ȡ�༭��
		try {
			
			
			dbAdapter=new DBAdapter(this,data);
			dbAdapter.open();
			sharedPreferences = getSharedPreferences(
					MainActivity.PREFERENCE_NAME, MainActivity.MODE);// SharedPreferences�洢��ʽ
			editor = sharedPreferences.edit();
			// SharedPreferences.Editor editor = sharedPreferences.edit();

		} catch (Exception e) {
			// TODO: handle exception
			Log.i("Init", "��ʼ�����������ʧ��!");
		}
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
		
		myscroll=(ScrollView)findViewById(R.id.myScrollView);//myScrollView
	
		for (int i = 0; i <problemLimit; i++) {
			// WAset[i] = 0;
			// mySelect[i] = "";
			//problemTurn[i] = i;
			problemTurn1.add(i);
			
		}

		if (Option == MainActivity.OPTION_RDM) {
			Random r = new Random();
			int t, rt1, rt2;
			for (int i = 0; i < problemLimit; i++) {
				rt1 = r.nextInt(problemLimit-1);
				rt2 = r.nextInt(problemLimit-1);
			exch(problemTurn1,rt1,rt2);
			}
			curIndex = 0;
			}
		
		
		if (Option == MainActivity.OPTION_WRONGEXERCISE) {
			curIndex = bundle.getInt("startfrom");
			addWAset_btn.setText("�Ƴ������");
			addWAset_btn.setBackgroundResource(R.drawable.yichu);// ����ͼƬid
		
		}
		/*
		 * ������Ŀ��ȡ
		 */
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
		cursor = dbAdapter.getAllData();
		Log.i("Count", cursor.getCount() + "");
		Log.i("Count", cursor.getCount() + "");
		
		this.title_int();
	} 

	public void settingInit() {
		autoCheck = sharedPreferences.getBoolean(MainActivity.CONFIG_AUTOCHECK,
				false);// �Զ�ȷ�ϣ��������ļ���ȡ��
		labelProblemID = sharedPreferences.getInt(label, 0);// ��ʱ��������ļ���
		auto2next = sharedPreferences.getBoolean(MainActivity.CONFIG_AUTO2NEXT,
				false);
		auto2addWAset = sharedPreferences.getBoolean(
				MainActivity.CONFIG_AUTO2ADDWRONGSET, false);
	}

	public void OnPaint() {
		if (cursor.getCount() == 0) {
			Toast.makeText(this, "�ҿ���û���⣬���ƨ��", Toast.LENGTH_LONG).show();
		} else {
			/*
			 * ��ʼ��View
			 */
			//cursor.moveToPosition(problemTurn[curIndex]);
			//cursor.moveToPosition((int)problemTurn1.get(curIndex));
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
			TESTSUBJECT = cursor.getString(cursor
					.getColumnIndex(DBAdapter.TESTSUBJECT));
			TESTSUBJECT = TESTSUBJECT.replace("��|��", "��ͼ");
			TESTANSWER = cursor.getString(cursor
					.getColumnIndex(DBAdapter.TESTANSWER));
			IMAGENAME = cursor.getString(cursor
					.getColumnIndex(DBAdapter.IMAGENAME));
			TESTTPYE = cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE));
			proTextView
					.setText((my_index + 1) + "." + TESTSUBJECT);
			// addWAset_btn.setText("���");

			promptText.setVisibility(View.GONE);
			promptText.setText("");
			// Toast.makeText(this, IMAGENAME+"--"+IMAGENAME.length(),
			// Toast.LENGTH_LONG).show();

			// ͼƬ����
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
			//�����Ͳ����жϵ�ʱ��
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
				// ѡ����
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
				// �ж���
				radioA.setText("��");
				radioC.setText("��");
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
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		settingInit();
		super.onRestart();
	}

	public void saveWaset() {
		try {
			String text = "";
			fos = openFileOutput(WAsetFilename, MODE_PRIVATE);
			for (int i = 0; i < problemLimit; i++) {
				if (list_myWAset(i) == 1) {
					// cursor.moveToPosition(i);
					cursor.moveToPosition(i);
					text += (i + 1)
							+ "."
							+ cursor.getString(cursor
									.getColumnIndex(DBAdapter.TESTSUBJECT))
							+ "#";
				}
			}
			if (text.compareTo("") == 0)
				text = "#";
			fos.write(text.getBytes());
		} catch (Exception e) {
			// TODO: handle exception
			// ShowToast(e.toString());
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
		// ��������
		saveWaset();
		//˳����ϰ�������
		cursor_order.moveToFirst();
		
		int jindu=cursor.getColumnIndex(DBAdapter.EXPR1);
		dbAdapter.close();
		if(Option == MainActivity.OPTION_ORDER)
		{
			Log.i("ex_record is", ex_record+"");
			Log.i("jindu is", jindu+"");
			if(jindu!=ex_record)
			{//ex_record
				//String i=""; 
				DBAdapter dbAdapter1=new DBAdapter(this,data);
				dbAdapter1.open();			
				ContentValues v = new ContentValues();
				v.put("EXPR1", ex_record);
				dbAdapter1.update_data(v);
				dbAdapter1.close();
				
			}
		}
	
		super.onDestroy();
	}
	
	
	public boolean onTouchEvent1(MotionEvent event)  
	  
	    {  
  
		//�̳���Activity��onTouchEvent������ֱ�Ӽ�������¼�
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			//����ָ���µ�ʱ��
			x1 = event.getX();
			y1 = event.getY();
		}
		if(event.getAction() == MotionEvent.ACTION_UP) {
			//����ָ�뿪��ʱ��
			x2 = event.getX();
			y2 = event.getY();
			
			if(x1 - x2 > 110) {
				next_btn.performClick();
				//Toast.makeText(ExerciseActivity.this, "����", Toast.LENGTH_SHORT).show();
				return true;
			} 
			else if(x2 - x1 > 110) {
				forword_btn.performClick();
				return true;
				//Toast.makeText(ExerciseActivity.this, "���һ�", Toast.LENGTH_SHORT).show();
			}
			else if(y2 - y1 > 110) {
				return false;
			//	Toast.makeText(ExerciseActivity.this, "���»�", Toast.LENGTH_SHORT).show();
			} else if(y1 - y2 >110) {
					//return super.onTouchEvent(true);
			return false;
					//Toast.makeText(ExerciseActivity.this, "���ϻ�", Toast.LENGTH_SHORT).show();
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
		 Log.i("GetAllData","EXERCISE");
	int	t=(int)cursor_order.getCount();
		
		Log.i("problemLimit", t+"");
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
		Toast.makeText(this, "��ȡ���ʧ��", Toast.LENGTH_LONG).show();
	}
	return my_title;
}
public void title_int()
{
	title_text=(TextView)findViewById(R.id.title_text);
	title_count=(Button)findViewById(R.id.title_button);
	
	title_text.setText(title_name(data));
	if(Option == MainActivity.OPTION_ORDER)
	{
		title_count.setText((1+curIndex)+" / "+cursor.getCount());
	}
	else
	{
		title_count.setVisibility(View.INVISIBLE);
	}
	}
}


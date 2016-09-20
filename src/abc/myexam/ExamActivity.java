package abc.myexam;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ExamActivity extends Activity {
 
	public static final int TESTLIMIT = 100;
	public  String WAsetFilename ;
	public int t_limit;
	int curIndex;
	String myAnswer;
	public int problemLimit;
	//int[] myWAset = new int[t_limit];
	List<Integer> myWAset1=new ArrayList<Integer>();// ��������
	String[] mySelect = new String[100];// �����¼
	int[] testTurn = new int[100];
	
	String[] testAnswer = new String[100];
	double resultInt;
	boolean isHandIn;// ��ʾ�����
	int minutes, seconds;
	String data="";
	String TESTSUBJECT;
	String TESTANSWER;
	String ANSWERA;
	String ANSWERB;
	String ANSWERC;
	String ANSWERD;
	String ANSWERE;
	String ANSWERF;
	String IMAGENAME;
	boolean CA;
	boolean CB;
	boolean CC;
	boolean CD;
	boolean CE;
	boolean CF;
	int TESTTPYE;
	int TESTBELONG;
	int EXPR1;
	//��ָ���µĵ�Ϊ(x1, y1)��ָ�뿪��Ļ�ĵ�Ϊ(x2, y2)
			float x1 = 0;
			float x2 = 0;
			float y1 = 0;
			float y2 = 0;

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
	Button forword_btn;
	Button next_btn;
	Button check_btn;
	Button addWAset_btn;
	TextView promptText;
	Chronometer chronometer;

	Cursor cursor;
	DBAdapter dbAdapter;

	// InputStream in;
	// BufferedReader br;
	FileInputStream fis;
	FileOutputStream fos;
	Chronometer exam_chronometer;
	TextView title_text;
	Button title_count;
	LinearLayout examlayout_linearlayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.examlayout);
		Init();
		OnPaint();

		forword_btn.setOnClickListener(new OnClickListener() {
			// ��һ��
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (curIndex == 0) {
					ShowToast("��ǰΪ��һ��");
				} else {
					if (isHandIn) {// �����
						int tindex = curIndex;
						while (--tindex >= 0) {
							if (mySelect[tindex] != testAnswer[tindex]) {
								curIndex = tindex;
								 title_int();
								OnPaint();
								return;
							}
						}
						ShowToast("��ǰΪ��һ��");
						return;
					} 
					
					else {
						Multil_Check();
						//ShowToast("��"+curIndex+"�⣬��ѡ���ǣ�"+mySelect[curIndex]);
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
				if (curIndex == TESTLIMIT - 1) {
					ShowToast("��ǰΪ���һ��");
				} else {
					if (isHandIn) {
						int tindex = curIndex;
						while (++tindex < TESTLIMIT) {
							if (mySelect[tindex] != testAnswer[tindex]) {
								curIndex = tindex;
								 title_int();
								OnPaint();
								return;
							}
						}
						ShowToast("��ǰΪ���һ��");
						return;
					} else {
						Multil_Check();
						//ShowToast("��"+curIndex+"�⣬��ѡ���ǣ�"+mySelect[curIndex]);
						curIndex++;
						 title_int();
						OnPaint();
					}
				}
			}
		});

		/*
		 * ����
		 */
		check_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showHandInAgainDialog();
			}
		});

		/*
		 * ��������
		 */
		addWAset_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//myWAset[testTurn[curIndex]] = 1;
				myWAset1.set(testTurn[curIndex], 1);
				saveWaset();
				ShowToast("����ɹ�");
			}
		});
		/*
		 * ѡ��radio
		 */
		radioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						if (!isHandIn) {
							if (radioA.isChecked() && mySelect[curIndex] != "A") {
								mySelect[curIndex] = "A";
								//next_btn.performClick();
							} else if (radioB.isChecked()
									&& mySelect[curIndex] !="B") {
								mySelect[curIndex] ="B";
							//	next_btn.performClick();
							} else if (radioC.isChecked()
									&& mySelect[curIndex] !="C") {
								mySelect[curIndex] ="C";
								//next_btn.performClick();
							} else if (radioD.isChecked()
									&& mySelect[curIndex] != "D") {
								mySelect[curIndex] = "D";
								//next_btn.performClick();
							}
						}
					}
				});
		CheckBoxA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(!isHandIn)
				{
					if(isChecked)
					{
						//mySelect[curIndex]=mySelect[curIndex]+"A";
						CA=true;
					}
					else
					{
						//mySelect[curIndex]=mySelect[curIndex].replace("A", "");	
						//mySelect[curIndex]=mySelect[curIndex];
						CA=false;
					}
				}
				
			}
		});
		//CheckBoxB.setOnClickListener(new OnClickListener())
		CheckBoxB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(!isHandIn)
				{
					if(isChecked)
					{
						CB=true;
						//mySelect[curIndex]=mySelect[curIndex]+"B";
					}
					else
					{
						CB=false;
						//mySelect[curIndex]=mySelect[curIndex].replace("B", "");	
					}
				}
			
			}
			
		});
		CheckBoxC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(!isHandIn)
				{
					if(isChecked)
				{
						CC=true;
					//mySelect[curIndex]=mySelect[curIndex]+"C";
				}
				else
				{
					CC=false;
					//mySelect[curIndex]=mySelect[curIndex].replace("C", "");	
				}
				}
				
			}
			
		});
		CheckBoxD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(!isHandIn)
				{
					if(isChecked)
					{
						CD=true;
						//mySelect[curIndex]=mySelect[curIndex]+"D";
					}
					else
					{
						CD=false;
						//mySelect[curIndex]=mySelect[curIndex].replace("D", "");	
					}	
				}
				
			}
			
		});
		CheckBoxE.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
		
				if(!isHandIn)
				{if(isChecked)
				{
					CE=true;
					//mySelect[curIndex]=mySelect[curIndex]+"E";
				}
				else
				{
					//mySelect[curIndex]=mySelect[curIndex].replace("E", "");	
					CE=false;
				}}
				
			}
			
		});
		CheckBoxF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(!isHandIn)
				{
					if(isChecked)
					{
						//mySelect[curIndex]=mySelect[curIndex]+"F";
						CF=true;
					}
					else
					{
						//mySelect[curIndex]=mySelect[curIndex].replace("F", "");	
						CF=false;
					}
				}
				
			}
			
		});
		// ÿ��ʱ��ı�
		chronometer.setOnChronometerTickListener(new OnChronometerTickListener() {

					@Override
					public void onChronometerTick(Chronometer chronometer) {
						// TODO Auto-generated method stub
						seconds--;
						if (seconds == -1) {
							minutes--;
							seconds = 59;
						}
						if (minutes < 0) {
							chronometer.stop();
							// ֱ�ӽ���
							handlerAfterHandIn();
						} else {
							if (minutes < 5) {
								chronometer.setTextColor(Color.RED);
								chronometer.setText(nowtime());
							} else {
								chronometer.setTextColor(Color.GREEN);
								chronometer.setText(nowtime());
							}
						}
					}
				});
		title_count.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					ShowDialog2JumpByIndex();
				
			}});
	}

	protected void showHandInAgainDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("ȷ�Ͻ�����");
		builder.setTitle("��ʾ");

		builder.setPositiveButton("ȷ��",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface v, int which) {
						// TODO Auto-generated method stub
						resultInt=0;
						handlerAfterHandIn();
					}
				});
		builder.setNegativeButton("ȡ��", null);
		builder.create().show();
	}

	// �������
	protected void handlerAfterHandIn() {
		// TODO Auto-generated method stub
		/*
		 * �ɼ�ͳ�� ����ͳ�� isHandIn��־�޸� ʱ���־�ĳɼ��������־ �������ɴ����������
		 */
		chronometer.setVisibility(View.GONE);
		addWAset_btn.setVisibility(View.VISIBLE);
		check_btn.setEnabled(false);
		isHandIn = true;

		String tmpanswer;
		for (int i = 99; i >= 0; i--) {
			cursor.moveToPosition(testTurn[i]);
			tmpanswer = cursor.getString(cursor
					.getColumnIndex(DBAdapter.TESTANSWER));
			int m=cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE));
			testAnswer[i]="";
			if(m==1||m==2)// �жϻ�ѡ
			{
				if (tmpanswer.compareTo("��") == 0) {
					testAnswer[i] = "A";
				} else if (tmpanswer.compareTo("��") == 0) {
					testAnswer[i] = "C";
				} else if (tmpanswer.compareTo("A") == 0) {
					testAnswer[i] = "A";
				} else if (tmpanswer.compareTo("B") == 0) {
					testAnswer[i] = "B";
				} else if (tmpanswer.compareTo("C") == 0) {
					testAnswer[i] = "C";
				} else if (tmpanswer.compareTo("D") == 0) {
					testAnswer[i] = "D";
				}
				if (testAnswer[i] == mySelect[i]) {
					if(m==2)
					{
						resultInt=(double) (resultInt+0.5);
					}
					else
						{resultInt++;}
				}
			}
			else
			{//testAnswer[i].indexOf(mySelect[i].toString())>=0&&mySelect[i].indexOf(testAnswer[i])>=0
				String mys="";
				
				if (mySelect[i].indexOf("A")>=0) {
					mys+="A";
				}
				if (mySelect[i].indexOf("B")>=0) {
					mys+="B";
				}
				if (mySelect[i].indexOf("C")>=0) {
					mys+="C";
				}
				if (mySelect[i].indexOf("D")>=0) {
					mys+="D";
				}
				if (mySelect[i].indexOf("E")>=0) {
					mys+="E";
				}
				if (mySelect[i].indexOf("F")>=0) {
					mys+="F";
				}
				mySelect[i]="";
				mySelect[i]=mys;
				testAnswer[i]=tmpanswer;
				if (mySelect[i].compareTo(tmpanswer)==0) {
					resultInt=(double) (resultInt+1.5);
				}
			}
			
		}
		showScore();
		curIndex = 0;
		for (int i = 0; i <=99; i++) {
			if (mySelect[i] != testAnswer[i]) {
				curIndex = i;
				break;
			}
		}
		OnPaint();
	}

	private void showScore() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new Builder(this);

		builder.setTitle("���");
		if (resultInt == 100) {
			builder.setMessage("�ۣ����֣����������");
		} else if (resultInt >= 90) {
			builder.setMessage("�ϸ��ˣ��ɼ�Ϊ�� " + resultInt + ",���ٽ�����");
		} else {
			builder.setMessage("���ϸ񣡳ɼ�Ϊ��" + resultInt + ",�����Ŭ��");
		}
		builder.setPositiveButton("ȷ��", null);
		builder.create().show();
	}

	// ����ʱ����

	public void ShowToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	private void Init() {
		// TODO Auto-generated method stub
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
		//CheckBoxGroup cbg=new CheckBoxGroup();  
		forword_btn = (Button) findViewById(R.id.forwordBtn);
		next_btn = (Button) findViewById(R.id.nextBtn);
		check_btn = (Button) findViewById(R.id.checkBtn);
		addWAset_btn = (Button) findViewById(R.id.addWAsetBtn);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		promptText = (TextView) findViewById(R.id.promptText);
		chronometer = (Chronometer) findViewById(R.id.exam_chronometer);
		examlayout_linearlayout=(LinearLayout)findViewById(R.id.examlayout_linearlayout);
		check_btn.setText("����");
		addWAset_btn.setVisibility(View.GONE);
		minutes = 45;
		seconds = 0;
		chronometer.setText(nowtime());
		chronometer.setVisibility(View.VISIBLE);
		chronometer.start();
		isHandIn = false;
		resultInt = 0;
		Bundle bundle = getIntent().getExtras();
	
		 data=bundle.getString("data");//���ѡ��
		 problemLimit=t_count(data);
		 WAsetFilename=data;
		 title_int();
		 int[] problemTurn = new int[problemLimit];
		for (int i = 0; i < problemLimit; i++) {
			// WAset[i] = 0;
			// mySelect[i] = "";
			problemTurn[i] = i;
			myWAset1.add(0);
		}

		/*
		 * ������Ŀ��ȡ
		 */
		try {
			String Text = "";
			fis = openFileInput(WAsetFilename);
			byte[] readBytes = new byte[fis.available()];
			while (fis.read(readBytes) != -1) {
				
				Text = new String(readBytes);
			}
			String[] tmp_waset = Text.split("#");
			String tmpString;
			if (tmp_waset[0].compareTo("") != 0) {
				for (int i = 0; i < tmp_waset.length; i++) {
					tmpString = tmp_waset[i].substring(0,
							tmp_waset[i].indexOf('.'));
					// ShowToast(tmpString);
					//myWAset[Integer.parseInt(tmpString) - 1] = 1;
					myWAset1.set(Integer.parseInt(tmpString) - 1, 1);

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			// ShowToast(e.toString());
		}

		Random r = new Random();
		curIndex = 0;
		try {
			dbAdapter = new DBAdapter(this,data);
			dbAdapter.open();
			cursor = dbAdapter.getAllData();
			int cnt = 0;
			int sj;
			while(cnt<30)
			{
				sj=r.nextInt(problemLimit);
				cursor.moveToPosition(problemTurn[sj]);
				if (cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE)) == 2&&!serch_my_p(problemTurn[sj],testTurn))
				{// �ж���
					mySelect[cnt] = "";
					testTurn[cnt] = problemTurn[sj];
					cnt++;}
			}
			while(cnt<70)
			{
				sj=r.nextInt(problemLimit);
				cursor.moveToPosition(problemTurn[sj]);
				if (cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE)) == 1&&!serch_my_p(problemTurn[sj],testTurn))
				{// �ж���
					mySelect[cnt] = "";
					testTurn[cnt] = problemTurn[sj];
					cnt++;}
			}
			while(cnt<100)
			{
				sj=r.nextInt(problemLimit);
				cursor.moveToPosition(problemTurn[sj]);
				if (cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE)) == 3&&!serch_my_p(problemTurn[sj],testTurn))
				{// �ж���
					mySelect[cnt] = "";
					testTurn[cnt] = problemTurn[sj];
					cnt++;}
			}
		} catch (Exception e) {
			//ShowToast(e.toString());
			 ShowToast("����������");
		}
	}

	// ʣ��ʱ��string
	private String nowtime() {
		// TODO Auto-generated method stub
		if (seconds < 10) {
			return (minutes + ":0" + seconds);
		} else {
			return (minutes + ":" + seconds);
		}
	}

	public void OnPaint() {
		if (cursor.getCount() == 0) {
			Toast.makeText(this, "û���⣬����������", Toast.LENGTH_LONG).show();
		} 
		
		else {
			/*
			 * ��ʼ��View
			 */
			cursor.moveToPosition(testTurn[curIndex]);
			if (mySelect[curIndex] == "") {
				radioGroup.clearCheck();
				CheckBoxA.setChecked(false);
				CheckBoxC.setChecked(false);
				CheckBoxB.setChecked(false);
				CheckBoxD.setChecked(false);
				CheckBoxE.setChecked(false);
				CheckBoxF.setChecked(false);
				
			}
			else 
			{
				if(cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE)) == 3)
				{
				CheckBoxA.setChecked(false);
				CheckBoxC.setChecked(false);
				CheckBoxB.setChecked(false);
				CheckBoxD.setChecked(false);
				CheckBoxE.setChecked(false);
				CheckBoxF.setChecked(false);
			    String zanshi=mySelect[curIndex];
				//mySelect[curIndex]="";
					if (zanshi.indexOf("A")>=0) {
						CheckBoxA.setChecked(true);
					}
					if (zanshi.indexOf("B")>=0) {
						CheckBoxB.setChecked(true);
					}
					if (zanshi.indexOf("C")>=0) {
						CheckBoxC.setChecked(true);
					}
					if (zanshi.indexOf("D")>=0) {
						CheckBoxD.setChecked(true);
					}
					if (zanshi.indexOf("E")>=0) {
						CheckBoxE.setChecked(true);
					}
					if (zanshi.indexOf("F")>=0) {
						CheckBoxF.setChecked(true);
					}
					//mySelect[curIndex]=zanshi;
				}
				else
				{
					if(mySelect[curIndex]=="A")
					{
						radioGroup.check(radioA.getId());
					}
					if(mySelect[curIndex]=="B")
					{
						radioGroup.check(radioB.getId());
					}
					if(mySelect[curIndex]=="C")
					{
						radioGroup.check(radioC.getId());
					}if(mySelect[curIndex]=="D")
					{
						radioGroup.check(radioD.getId());
					}
				}
			
			}
			TESTSUBJECT = cursor.getString(cursor
					.getColumnIndex(DBAdapter.TESTSUBJECT));
			TESTSUBJECT = TESTSUBJECT.replace("��|��", "��ͼ");
			TESTANSWER = cursor.getString(cursor
					.getColumnIndex(DBAdapter.TESTANSWER));
			IMAGENAME = cursor.getString(cursor
					.getColumnIndex(DBAdapter.IMAGENAME));
			TESTTPYE = cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE));
			proTextView.setText((curIndex + 1) + "." + TESTSUBJECT);
			// addWAset_btn.setText("���");

			if (!isHandIn) {
				promptText.setVisibility(View.GONE);
				promptText.setText("");
			} else {
				promptText.setVisibility(View.VISIBLE);
				String zqda=testAnswer[curIndex];
				String my_da=mySelect[curIndex].toString();
				if(cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE)) == 2)
				{
					if(zqda.compareTo("A")==0)
					{
						zqda="��";
					}
					else
					{zqda="��";}
					if(mySelect[curIndex].compareTo("A")==0)
					{my_da="��";}
					else if(mySelect[curIndex].compareTo("C")==0)
					{
						my_da="��";
					}
					else
					{
						my_da="©ѡ";
					}
				}
				promptText.setText("��ȷ��Ϊ: " + zqda+"  ��Ĵ��ǣ�"+my_da);
				promptText.setTextSize(20);
				promptText.setTextColor(Color.RED); 
			}
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
					Toast.makeText(this, e.toString(), Toast.LENGTH_LONG)
							.show();
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
			if (cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE)) == 2) {
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
			} else if(cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE)) == 1) {
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
		}
		CommanOperation.ChangeTextSizeOp(this, examlayout_linearlayout, gettextsize());
	}

	/*
	 * ��������;
	 */
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
		dbAdapter.close();
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
				} else if(y1 - y2 > 110) {
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
public int t_count(String data)
{
  dbAdapter=new DBAdapter(this,data);
	dbAdapter.open();
	Cursor cursor_order;
	cursor_order=dbAdapter.getAllData();
	problemLimit=(int)cursor_order.getCount();
	
	Log.i("problemLimit", problemLimit+"");
	dbAdapter.close();
	return problemLimit;
}
//����Ƿ��Ѿ����ڸ��⣨Ϊ�����ظ����⣩
public boolean serch_my_p(int data ,int[] myp)
{    boolean flag = false;
    try
    {
    	for(int i=0;i<myp.length;i++)
    	{
    		if(data==myp[i])
    		{
    			flag=true;
    		}
    		else
    			flag=false;
    	}
	}
    catch (Exception e) {
		e.printStackTrace();
	}
	return flag;
	}
public  int list_myWAset(int tindex)
{
String m=(String)myWAset1.get(tindex).toString();
int my_index=Integer.valueOf(m);
return my_index;
}
public boolean onCreateOptionsMenu(Menu menu) {
	// TODO Auto-generated method stub
	
	menu.add(0, 1, 1, "��ת��ָ�����");
	menu.add(0, 4, 4, "����");
	return super.onCreateOptionsMenu(menu);
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
	// TODO Auto-generated method stub
	if (item.getItemId() == 1) {
		ShowDialog2JumpByIndex();
	}  else if (item.getItemId() == 4) {
		startActivity(new Intent().setClass(ExamActivity.this,
				OptionActivity.class));
		// settingInit();//ע���ǲ��в����ģ�����˵���ǵȻ�������ִ�еģ�Ȼ���еģ��������
		// ����Ӧ����restartʱ��ִ�вŶԡ�
	}
	if (item.getItemId() == 5) {
		ShowDialog2JumpByIndex();
	}
	return super.onOptionsItemSelected(item);
}

public boolean jumpAction(int jump2ID) {
	if (jump2ID > 0 && jump2ID < 100) {
		curIndex = jump2ID - 1;
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

							if (!jumpAction(Integer.parseInt(editText
									.getText().toString()))) {
								ShowToast("ָ����Ų�����");
							}
						}
					}).setNegativeButton("ȡ��", null).show();
}
public void Multil_Check()
{
	if(cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE)) == 3)
	{
		mySelect[curIndex]="";
		if(CA)
		{
			mySelect[curIndex]=mySelect[curIndex]+"A";
		}
		if(CB)
		{
			mySelect[curIndex]=mySelect[curIndex]+"B";
		}
		if(CC)
		{
			mySelect[curIndex]=mySelect[curIndex]+"C";
		}
		if(CD)
		{
			mySelect[curIndex]=mySelect[curIndex]+"D";
		}
		if(CE)
		{
			mySelect[curIndex]=mySelect[curIndex]+"E";
		}
		if(CF)
		{
			mySelect[curIndex]=mySelect[curIndex]+"F";
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

public void title_int()
{
	title_text=(TextView)findViewById(R.id.title_text);
	title_count=(Button)findViewById(R.id.title_button);
	
	title_text.setText(title_name(data));
		title_count.setText("��ת");
		//title_count.setVisibility(View.INVISIBLE);
	
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
private String gettextsize() {
	SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFERENCE_NAME, MainActivity.MODE);
	float refloatstring=sharedPreferences.getFloat(MainActivity.CONFIG_TEXTSIZE, 45);
	return refloatstring+"";
}

}

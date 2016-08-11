package abc.myexam;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import abc.myexam.R.integer;
import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Mdf_exam_table extends Activity {

	public  int problemLimit;
	public String WAsetFilename;
	public static final String label = "label";
	int curIndex;
	String myAnswer;
	String data;
	String mydbnameString;
	String mydb;
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
	EditText E_answerA;
	EditText E_answerB;
	EditText E_answerC;
	EditText E_answerD;
	EditText E_answerE;
	EditText E_answerF;
	EditText proTextView;
	TextView xuhaoTextView;
	TextView radioA;
	TextView radioB;
	TextView radioC;
	TextView radioD;
	TextView radioE;
	TextView radioF;
	TextView myanswerTextView;
	EditText myanswerEditText;
	TextView mytips;
	EditText mytipsEditText;
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
	//��ָ���µĵ�Ϊ(x1, y1)��ָ�뿪��Ļ�ĵ�Ϊ(x2, y2)
		float x1 = 0;
		float x2 = 0;
		float y1 = 0;
		float y2 = 0;
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.mdf_exam_table);
			
			Init();// ͼ�����ݳ�ʼ��
			settingInit();// �����趨
			OnPaint();// �ػ�		
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
			check_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ContentValues updateContentValues=new ContentValues();
					updateContentValues.put("TestSubject", proTextView.getText().toString());
					updateContentValues.put("TestAnswer", myanswerEditText.getText().toString());
					updateContentValues.put("AnswerA", E_answerA.getText().toString());
					updateContentValues.put("AnswerB", E_answerB.getText().toString());
					updateContentValues.put("AnswerC", E_answerC.getText().toString());
					updateContentValues.put("AnswerD", E_answerD.getText().toString());
					updateContentValues.put("AnswerE", E_answerE.getText().toString());
					updateContentValues.put("AnswerF", E_answerF.getText().toString());
				
					//updateContentValues.put("Expr1", curIndex+"");
					updateContentValues.put("TestTips", mytipsEditText.getText().toString());
					try {
						//dbAdapter.update_data2(updateContentValues, (curIndex+1)+"");
						dbAdapter.update_data2(updateContentValues,cursor.getString(cursor.getColumnIndex(DBAdapter.TESTID)));
					} catch (Exception e) {
						// TODO: handle exception
						ShowToast("�����쳣");
					}
					ShowToast("����ɹ�");
				storage_data();
					OnPaint();
				}
			});
		}
		//�洢���ݣ��ر����ݿ��򿪣���������޸ĺ�ʵʱ���µ�����
		public void storage_data() {
			dbAdapter.close();
			dbAdapter=new DBAdapter(this, data);
			dbAdapter.open();
			
		cursor=dbAdapter.getdatabytype(Option+"");
		
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// TODO Auto-generated method stub
			if (Option == MainActivity.OPTION_ORDER) {
				menu.add(0, 1, 1, "��ת��ָ�����");
				/*menu.add(0, 2, 2, "��ת����ǩ");
				menu.add(0, 3, 3, "��Ϊ��ǩ");*/
			}
			menu.add(0, 2, 2, "����");
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
				startActivity(new Intent().setClass(this,
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
										jumpAction(Integer.parseInt(editText
												.getText().toString()));
										//ShowToast("���������"+editText.getText().toString());
									}
									
								}
							}).setNegativeButton("ȡ��", null).show();
		}

		public void ShowToast(String str) {
			Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
		}

		public void Init() {
		
			Bundle bundle = getIntent().getExtras();
			mydb=bundle.getString("mydbtable");
			mydbnameString=bundle.getString("mydbname");
			//option���ܴ�����������,���δ�����㴦��
			Option =bundle.getInt("Option");
			//û�л�ȡ��OPTION
			if (Option!=1&&Option!=2&&Option!=3) {
				Option=0;
			}
			int myNumber=0;
			int numberflag=0;
			//ȡ��ţ�
			try {
				myNumber=bundle.getInt("indexNO");//debug����������ֵ��mynumberΪ0��
			} catch (Exception e) {
				// TODO: handle exception
				numberflag=1;
			}
			
			 data=mydb;//bundle.getString("data");//���ѡ��
				problemLimit=t_count(data);	
				//��ʼ�����
			if(numberflag==1||myNumber==0)
			{			
			    dbAdapter=new DBAdapter(this,data);
				dbAdapter.open();
				//������ȡ
				cursor_order=dbAdapter.getdatabytype(Option+"");
				 Log.i("GetAllData","EXERCISE");
				cursor_order.moveToPosition(0);
				int i;
				try {
					 i=cursor_order.getInt(cursor_order.getColumnIndex(DBAdapter.EXPR1));
				} catch (Exception e) {
					// TODO: handle exception
					i=1;
				}				
				curIndex=i;
				dbAdapter.close();
			}
			else {
				curIndex=myNumber;
			}
			proTextView = (EditText) findViewById(R.id.pro_text);	
			xuhaoTextView=(TextView)findViewById(R.id.tiganxuhao);
			radioA = (TextView) findViewById(R.id.radioA);
			radioB = (TextView) findViewById(R.id.radioB);
			radioC = (TextView) findViewById(R.id.radioC);
			radioD = (TextView) findViewById(R.id.radioD);
			radioE = (TextView) findViewById(R.id.radioE);
			radioF = (TextView) findViewById(R.id.radioF);
			E_answerA=(EditText)findViewById(R.id.E_answerA);
			E_answerB=(EditText)findViewById(R.id.E_answerB);
			E_answerC=(EditText)findViewById(R.id.E_answerC);
			E_answerD=(EditText)findViewById(R.id.E_answerD);
			E_answerE=(EditText)findViewById(R.id.E_answerE);
			E_answerF=(EditText)findViewById(R.id.E_answerF);
			forword_btn = (Button) findViewById(R.id.forwordBtn);
			next_btn = (Button) findViewById(R.id.nextBtn);
			check_btn = (Button) findViewById(R.id.checkBtn);		
			promptText = (TextView) findViewById(R.id.promptText);	
			myanswerTextView=(TextView)findViewById(R.id.MyAnswer);
			myanswerEditText=(EditText)findViewById(R.id.E_MyAnswer);
			mytips=(TextView)findViewById(R.id.MyTips);
			mytipsEditText=(EditText)findViewById(R.id.E_MyTips);
			myscroll=(ScrollView)findViewById(R.id.myScrollView);//myScrollView
		
			for (int i = 0; i <problemLimit; i++) {			
				problemTurn1.add(i);
				
			}

			  dbAdapter=new DBAdapter(this,data);
				dbAdapter.open();
			cursor = dbAdapter.getdatabytype(Option+"");
			Log.i("Count", cursor.getCount() + "");
			Log.i("Count", cursor.getCount() + "");
			
			this.title_int();
		} 

		public void settingInit() {
			autoCheck = false;
			labelProblemID = 0;
			auto2next = false;			
		}

		public void OnPaint() {
			if (cursor.getCount() == 0) {
				Toast.makeText(this, "�ҿ���û���⣬���ƨ��", Toast.LENGTH_LONG).show();
			} else {
				
			     String m=(String)problemTurn1.get(curIndex).toString();
			     Log.i("m", m);
			     int my_index=Integer.valueOf(m);
				cursor.moveToPosition(my_index);			
				TESTSUBJECT = cursor.getString(cursor
						.getColumnIndex(DBAdapter.TESTSUBJECT));
				TESTSUBJECT = TESTSUBJECT.replace("��|��", "��ͼ");
				TESTANSWER = cursor.getString(cursor
						.getColumnIndex(DBAdapter.TESTANSWER));
				myanswerEditText.setText(TESTANSWER);
				myanswerEditText.setTextColor(Color.RED);
				IMAGENAME = cursor.getString(cursor
						.getColumnIndex(DBAdapter.IMAGENAME));
				TESTTPYE = cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE));
				String tpyename="";
				switch (TESTTPYE) {
				case 1:
					tpyename="��ѡ��";
					break;
                 case 2:
                	 tpyename="�ж���";
					break;
					case 3:
						tpyename="��ѡ��";
					break;
				default:
					tpyename="�ж���";
					break;
				}
				xuhaoTextView.setText("�}Ŀ"+(my_index + 1)+"("+tpyename+"):");
				proTextView
						.setText(TESTSUBJECT);
				mytipsEditText.setText(cursor.getString(cursor.getColumnIndex(DBAdapter.TESTTIP)));
				promptText.setVisibility(View.GONE);
				promptText.setText("");				
					E_answerA.setText(cursor
							.getString(cursor.getColumnIndex(DBAdapter.ANSWERA)));
					E_answerB.setText(cursor
							.getString(cursor.getColumnIndex(DBAdapter.ANSWERB)));
					E_answerC.setText(cursor
							.getString(cursor.getColumnIndex(DBAdapter.ANSWERC)));
					E_answerD.setText(cursor
							.getString(cursor.getColumnIndex(DBAdapter.ANSWERD)));
					E_answerE.setText(cursor
							.getString(cursor.getColumnIndex(DBAdapter.ANSWERE)));	
					E_answerF.setText(cursor
							.getString(cursor.getColumnIndex(DBAdapter.ANSWERF)));	
				 if(TESTTPYE== 1) {
				
					radioA.setVisibility(View.VISIBLE);
					radioB.setVisibility(View.VISIBLE);
					radioC.setVisibility(View.VISIBLE);
					radioD.setVisibility(View.VISIBLE);	
					E_answerA.setVisibility(View.VISIBLE);
					E_answerB.setVisibility(View.VISIBLE);
					E_answerC.setVisibility(View.VISIBLE);
					E_answerD.setVisibility(View.VISIBLE);
					radioE.setVisibility(View.GONE);
					radioF.setVisibility(View.GONE);
					E_answerE.setVisibility(View.GONE);
					E_answerF.setVisibility(View.GONE);
				}
			    if(TESTTPYE == 3)
				{
					if(ANSWERE==null||ANSWERE.compareTo("") == 0)
					{
						radioA.setVisibility(View.VISIBLE);
						radioB.setVisibility(View.VISIBLE);
						radioC.setVisibility(View.VISIBLE);
						radioD.setVisibility(View.VISIBLE);	
						E_answerA.setVisibility(View.VISIBLE);
						E_answerB.setVisibility(View.VISIBLE);
						E_answerC.setVisibility(View.VISIBLE);
						E_answerD.setVisibility(View.VISIBLE);
						radioE.setVisibility(View.GONE);
						radioF.setVisibility(View.GONE);
						E_answerE.setVisibility(View.GONE);
						E_answerF.setVisibility(View.GONE);
					}
					else if(ANSWERF==null||ANSWERF.compareTo("") == 0)
					{
						radioA.setVisibility(View.VISIBLE);
						radioB.setVisibility(View.VISIBLE);
						radioC.setVisibility(View.VISIBLE);
						radioD.setVisibility(View.VISIBLE);	
						E_answerA.setVisibility(View.VISIBLE);
						E_answerB.setVisibility(View.VISIBLE);
						E_answerC.setVisibility(View.VISIBLE);
						E_answerD.setVisibility(View.VISIBLE);
						radioE.setVisibility(View.VISIBLE);
						E_answerE.setVisibility(View.VISIBLE);
						radioF.setVisibility(View.GONE);	
						E_answerF.setVisibility(View.GONE);
					}
					else 
					{
						radioA.setVisibility(View.VISIBLE);
						radioB.setVisibility(View.VISIBLE);
						radioC.setVisibility(View.VISIBLE);
						radioD.setVisibility(View.VISIBLE);	
						E_answerA.setVisibility(View.VISIBLE);
						E_answerB.setVisibility(View.VISIBLE);
						E_answerC.setVisibility(View.VISIBLE);
						E_answerD.setVisibility(View.VISIBLE);
						radioE.setVisibility(View.VISIBLE);
						E_answerE.setVisibility(View.VISIBLE);
						radioF.setVisibility(View.VISIBLE);
						E_answerF.setVisibility(View.VISIBLE);
					}
					
				}
				
					 
			    if (TESTTPYE == 2) 
					 {
					radioA.setVisibility(View.GONE);
					radioC.setVisibility(View.GONE);
					radioB.setVisibility(View.GONE);
					radioD.setVisibility(View.GONE);
					radioE.setVisibility(View.GONE);
					radioF.setVisibility(View.GONE);
					E_answerA.setVisibility(View.GONE);
					E_answerB.setVisibility(View.GONE);
					E_answerC.setVisibility(View.GONE);
					E_answerD.setVisibility(View.GONE);
					E_answerE.setVisibility(View.GONE);
					E_answerF.setVisibility(View.GONE);
				}
			}
		}

		@Override
		protected void onRestart() {
			// TODO Auto-generated method stub
			settingInit();
			super.onRestart();
		}

		@Override
		protected void onDestroy() {
			dbAdapter.close();
			finish();
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
				
				if(x1 - x2 > 150) {
					next_btn.performClick();
					//Toast.makeText(ExerciseActivity.this, "����", Toast.LENGTH_SHORT).show();
					return true;
				} 
				else if(x2 - x1 > 150) {
					forword_btn.performClick();
					return true;
					//Toast.makeText(ExerciseActivity.this, "���һ�", Toast.LENGTH_SHORT).show();
				}
				else if(y2 - y1 > 150) {
					return false;
				//	Toast.makeText(ExerciseActivity.this, "���»�", Toast.LENGTH_SHORT).show();
				} else if(y1 - y2 >150) {
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent returnbackIntent=new Intent();
			setResult(2, returnbackIntent);
			dbAdapter.close();
			finish();
		}
		return false;
	}

}

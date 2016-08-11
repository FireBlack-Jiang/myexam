package abc.myexam;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Search_detail extends Activity {
	int curIndex;
	String data;
	List<Integer> myWAset1=new ArrayList<Integer>();
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
	int my_turn;

	boolean autoCheck;
	boolean auto2next;
	boolean auto2addWAset;
	EditText editText;
	TextView proTextView;
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
	
	Cursor cursor;
	Cursor cursor_order;
	DBAdapter dbAdapter;
    Button title_count;
    TextView title_text;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.my_item);
		
		Init();// 图像、数据初始化
		OnPaint();// 重绘
	}
	private void OnPaint() {
		// TODO Auto-generated method stub
		if (cursor.getCount() == 0) {
			Toast.makeText(this, "我靠，没有题，玩个屁！", Toast.LENGTH_LONG).show();
		} else {
			/*
			 * 初始化View
			 */
		int my_index=my_turn;
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
			TESTSUBJECT = TESTSUBJECT.replace("“|”", "下图");
			TESTANSWER = cursor.getString(cursor
					.getColumnIndex(DBAdapter.TESTANSWER));
			TESTTPYE = cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE));
			proTextView.setText((my_index + 1) + "." + TESTSUBJECT);
			// addWAset_btn.setText("结果");
			this.checkfalse();
			promptText.setVisibility(View.VISIBLE);
			promptText.setText("正确答案是："+this.TESTANSWER);
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
	
	}
	public void Init() {
		
		Bundle bundle = getIntent().getExtras();
		 data=bundle.getString("data");//题库选择	
		 my_turn =bundle.getInt("my");
			Log.i("data", data);	
			dbAdapter=new DBAdapter(this,data);
			dbAdapter.open();
			cursor = dbAdapter.getAllData();
			Log.i(" shousuo Count", cursor.getCount() + "");
			proTextView = (TextView) findViewById(R.id.pro_text);
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
			radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
			promptText=(TextView)findViewById(R.id.promptText);
			title_text=(TextView)findViewById(R.id.title_text);
			title_count=(Button)findViewById(R.id.title_button);
			title_count.setText((1+my_turn)+" / "+cursor.getCount());
			title_text.setText(title_name(data));
			
	}
	private CharSequence title_name(String data2) 
	{
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
			Toast.makeText(this, "获取题目失败", Toast.LENGTH_LONG).show();
		}
		return my_title;
	}
	public void checkfalse()
	{
		int t_type=cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE));
		  String zanshi=TESTANSWER;
		if(t_type == 3)
		{
	  
		//TESTANSWER="";
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
			//TESTANSWER=zanshi;
		}
		else if(t_type == 1)
            {
			if(zanshi.compareTo("A")==0)
			{
				radioGroup.check(radioA.getId());
				//radioA.isChecked();
			}
			if(zanshi.compareTo("B")==0)
			{
				radioGroup.check(radioB.getId());
				//radioB.isChecked();
			}
			if(zanshi.compareTo("C")==0)
			{
				radioGroup.check(radioC.getId());
				//radioC.isChecked();
				
			}
			if(zanshi.compareTo("D")==0)
			{
				radioGroup.check(radioD.getId());
				//radioD.isChecked();
			}
		}
		else 
			//if(cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE)) == 2)
		{
			if(zanshi.compareTo("对")==0)
			{
				radioGroup.check(radioA.getId());
				//radioA.isChecked();
			}
			if("错".indexOf(zanshi)==0)
			{
				radioGroup.check(radioC.getId());
				//radioA.isChecked();
				
			}
		}
		radioA.setClickable(false);
		radioB.setClickable(false);
		radioC.setClickable(false);
		radioD.setClickable(false);
		CheckBoxA.setClickable(false);
		CheckBoxB.setClickable(false);
		CheckBoxC.setClickable(false);
		CheckBoxD.setClickable(false);
		CheckBoxE.setClickable(false);
		CheckBoxF.setClickable(false);
	}
}

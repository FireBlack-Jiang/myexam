package abc.myexam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.text.method.TextKeyListener.Capitalize;
public class Operation_table extends Activity {	
	Button button1;
	TextView tview1,main_text,main_onetext;
	EditText editText;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.operationtablelayout);						
		main_text=(TextView)findViewById(R.id.textView3);
		button1=(Button)findViewById(R.id.Button1);
		Bundle bundle = getIntent().getExtras();
		String filename = bundle.getString("filename");
		String exception_infString=bundle.getString("exception_inf");
		if(exception_infString.compareTo("")!=0){
			exception_infString=exception_infString.substring(1,exception_infString.length());
		}
		int count_w=bundle.getInt("count_w");
		int count_r=bundle.getInt("count_r");
		String info="ʶ���ļ����ƣ�"+filename+
				"  \r\nʶ����Ŀ����"+(count_r+count_w)+
				"    \r\n���гɹ���"+count_r+
				"    \r\n�쳣ʧ�ܣ�"+count_w+
				"    \r\n�����쳣��ţ�"+exception_infString+
				"  \r\n  \r\n����ɹ��󣬽������½������������ݼ��ء�  \r\n\r\n";
		main_text.setText("�����������"+info);
		ShowDialog();
		button1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				finish();
			}
		});
	}
	public void ShowDialog() {
		editText = new EditText(this);
		// editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
		// editText.setInputType("number");
		editText.setKeyListener(new TextKeyListener(Capitalize.NONE,false));
		editText.setHint(init_db());
		new AlertDialog.Builder(this)
				.setTitle("�������")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setView(editText)
				.setPositiveButton("ȷ��",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
                           String mytitlename=editText.getText().toString();
								if(mytitlename!="")
								{
									update_dingzhi_dbname(mytitlename);
								}
								else
								{
									ShowToast("�������������");
								}
								
							}
						}).setNegativeButton("ȡ��", null).show();
	}
	public void update_dingzhi_dbname(String mydbname)
	{		
		DBAdapter dbAdapter1=new DBAdapter(Operation_table.this,"TEST_LIST");
		dbAdapter1.open();
		ContentValues initialValues=new ContentValues();						
		initialValues.put("testname", mydbname);
		int m=dbAdapter1.update_name("ABC_MYDZTK", initialValues);
		dbAdapter1.close();
		//���³�ʼ�����
		DBAdapter dbAdapter2=new DBAdapter(this,"ABC_TEST");
		dbAdapter2.open();			
		ContentValues v = new ContentValues();
		v.put("EXPR1", mydbname);
		dbAdapter2.update_data(v);
		dbAdapter2.close();
	}
	public void ShowToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
	public String init_db()
	{
		//�����ݿ�������׳�ʼ����ABC_TEST��ű�Ŀ¼
		 DBAdapter dbAdapter11;
			
		String db_name;
		    dbAdapter11=new DBAdapter(Operation_table.this,"TEST_LIST");
			dbAdapter11.open();
			
			db_name=dbAdapter11.GetName("ABC_MYDZTK");
			Log.i("chushihua", db_name+"");
			
			dbAdapter11.close();
		if(db_name=="")
		{
			db_name="���������";
		}
		return db_name;
	}
}

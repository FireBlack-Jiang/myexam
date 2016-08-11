package abc.myexam;

import java.io.IOException;

import abc.myexam.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
public class Edit_exam_table extends Activity {
	public TextView titleTextView=null;
	public String mydbString;
	public String mydbnameString;
	Button button_newButton;
	Button button_delButton;
	Button button_mdfButton;
	Button resetdatabaseButton;
	Button insertSingle;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mydatabase);
		insertSingle=(Button) findViewById(R.id.table_insert_single);
		resetdatabaseButton=(Button) findViewById(R.id.table_reset);
		button_delButton=(Button) findViewById(R.id.table_del);
		button_newButton=(Button) findViewById(R.id.table_insert);
		button_mdfButton=(Button) findViewById(R.id.table_mdf);
		titleTextView=(TextView) findViewById(R.id.edit_table_name);
		Bundle bundle = getIntent().getExtras();
		mydbnameString = bundle.getString("dbname");
		mydbString=bundle.getString("dbtable");
		String title_name="";
		/*DBAdapter dbAdapter2=new DBAdapter(this,"ABC_MYDZTK");
		dbAdapter2.open();//查询test_list表的数据库表的表名
		Cursor cursor_dbname=dbAdapter2.querytest_name("ABC_MYDZTK");
		cursor_dbname.moveToFirst();
		title_name=cursor_dbname.getString(cursor_dbname.getColumnIndex("testname"));
		cursor_dbname.close();
		dbAdapter2.close();*/
		titleTextView.setText(mydbnameString);
		//单笔新增
		insertSingle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showtoast("还未开发");
			}
		});
		//重置题库
		resetdatabaseButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowResetDialog();
			
			}
		});
		//批量删除
		button_delButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowDialog_del_table();
			}
		});
		button_mdfButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mdf_table();
			}
		});
		button_newButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent().setClass(Edit_exam_table.this,Exam_add_table.class));
			}
		});
	}

	public void ShowDialog_del_table(){
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("重置后题库将回滚到安装状态并且无法撤销哦！！");

		builder.setTitle("警告");

		builder.setPositiveButton("确定",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface v, int which) {
						// TODO Auto-generated method stub
						// dialog.dismiss();
					del_table();
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
	public void mdf_table(){
		//Toast.makeText(this, "有待開發......", Toast.LENGTH_LONG).show();
		Intent mdfIntent=new Intent();
		mdfIntent.putExtra("mydbname", mydbnameString);
		mdfIntent.putExtra("mydbtable", mydbString);
		mdfIntent.setClass(Edit_exam_table.this,Mdf_exam_table.class);
		startActivity(mdfIntent);
	}
	public boolean restDB() {
		DBManager dbhelp = new DBManager(this);
		try {
			dbhelp.createDataBase();
			dbhelp.close();
			return true;
			} catch (IOException m) {
			// TODO Auto-generated catch block
			m.printStackTrace();
			return false;
		}
	}
	public void showtoast(String st) {
		Toast.makeText(this, st, Toast.LENGTH_LONG).show();
	}
	public void ShowResetDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("重置后题库将回滚到安装状态并且无法撤销哦！！");

		builder.setTitle("警告");

		builder.setPositiveButton("确定",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface v, int which) {
						// TODO Auto-generated method stub
						// dialog.dismiss();
						if (restDB()) {
							showtoast("重置成功");
						}
						else {
							showtoast("重置失败");
						}
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
	public void del_table() {
		DBAdapter dbAdapter2=new DBAdapter(this,mydbString);
		dbAdapter2.open();//查询test_list表的数据库表的表名
		int flag=dbAdapter2.table_del(mydbString);
		dbAdapter2.close();
		if(flag>0){
			Toast.makeText(this, "成功刪除"+flag+"題", Toast.LENGTH_LONG).show();
		}
		else {
			Toast.makeText(this, "刪除异常", Toast.LENGTH_LONG).show();
		}
		//更新初始化题库
		
		DBAdapter dbAdapter1=new DBAdapter(this,"ABC_TEST");
			 dbAdapter1.open();			
				ContentValues v = new ContentValues();
				v.put("EXPR1", "三基本（2016）");
				dbAdapter1.update_data(v);
				dbAdapter1.close();
	}
}

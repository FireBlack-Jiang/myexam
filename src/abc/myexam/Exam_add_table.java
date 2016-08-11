package abc.myexam;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;

import fda.jkl.iew.update.AppUpdateInfo;

import abc.myexam.R.string;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.DialogPreference;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import jxl.*;

public class Exam_add_table extends Activity {
	// jxl.*包是读写excel表格的文件包
	DBAdapter dbAdapter;
	private static final String FIlESUFFIX = ".xls";
	private ListView filesList;
	private File sdcardDir;
	private ArrayAdapter<String> adapter = null;
	private ArrayList strFiles = new ArrayList();
	AlertDialog ad;
	private ProgressDialog mydialog;
	private Workbook wb = null;
	private Sheet[] list = null;

	TextView tview1;
	public int count_r = 0;// 计算题库导入成功和失败数
	public int count_w = 0;
public String myfilename_newString=null;//传输给下个界面的文件名称
public String exception_inf="";
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.exam_add_table);
		tview1 = (TextView) findViewById(R.id.textView1);
		filesList = (ListView) findViewById(R.id.listView1);

		if (isSdCardExist()) {
			// 获取外部sdcard目录
			sdcardDir = Environment.getExternalStorageDirectory();
			tview1.setText(" 1、请先下载题库的模板文件（微信公众号有分享--回复“模板题库“下载）" + "\r\n"
					+ " 2、请把题库XLS标准文件按照模板规范制作好，拷贝到SD卡根目录下(不要在任何文件夹里面)："
					+ "\r\n"
					+ " 3、文件必须放在指定目录，否则系统不能自动识别，如添加自定义题库有问题，可以卸载程序后重新再试"
					+ "\r\n" + " 4、如有其他不解问题，请公众好截图留言");
			// 返回文件名LIST
			strFiles = getXls();
			adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_expandable_list_item_1, strFiles);
			filesList.setAdapter(adapter);

		}
		filesList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(Exam_add_table.this,
						getFileName(strFiles.get(arg2).toString()),
						Toast.LENGTH_SHORT).show();
				openDialog(strFiles.get(arg2).toString());

			}
		});

	}

	private boolean isSdCardExist() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	// 获取文件名的列表
	private ArrayList getXls() {
		ArrayList<String> xlsList = new ArrayList<String>();
		if (sdcardDir != null) {
			String[] files = sdcardDir.list(new FilenameFilter() {

				@Override
				public boolean accept(File arg0, String arg1) {
					// TODO Auto-generated method stub
					// 返回.xls文件文件名
					return arg1.endsWith(FIlESUFFIX);
				}
			});
			for (String str : files) {
				xlsList.add(str);
			}

		} else {
			Toast m = Toast.makeText(this, "抱歉，寻找存储卡发生错误", Toast.LENGTH_LONG);
			m.show();
		}
		return xlsList;
	}

	private String getFileName(String fileName) {
		int i = fileName.indexOf(".xls");
		return fileName.substring(0, i);
	}

	private void openDialog(final String title) {

		/*
		 * 测试
		 */
		mydialog = new ProgressDialog(this, ProgressDialog.STYLE_HORIZONTAL);
		mydialog.setTitle("进度提示");
		mydialog.setMessage("正在转换，请稍后........");
		mydialog.setCancelable(false);
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("提示");
		builder.setMessage("是否导入" + title + "(    !!!!点击确定后需要耐心等待1-4分钟，不要操作手机)");
		builder.setPositiveButton("确定", new OnClickListener() {

			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				new myTranslate().execute(title);
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub

				finish();
			}
		});
		ad = builder.create();
		ad.show();
	}

	private void startActivity(String filename) {
		Intent intent = new Intent();
		// String data=main_one.getText().toString();
		intent.putExtra("filename", filename);
		intent.putExtra("count_w", count_w);
		intent.putExtra("count_r", count_r);
		intent.putExtra("option", count_w);
		intent.setClass(Exam_add_table.this, Operation_table.class);
		startActivity(intent);
		finish();
	}

	public class myTranslate extends AsyncTask<String, Integer, String> {
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			mydialog.show();
		}

		@Override
		protected String doInBackground(String... title) {
			// TODO Auto-generated method stub
			if (!isExistTitle(title[0])) {
				// 完成题库的导入详细过程
				// 打开excel
				try {
					String ssss = Environment.getExternalStorageDirectory()
							+ File.separator + title[0];
					File f = new File(ssss);
					wb = Workbook.getWorkbook(f);
				} catch (Exception e) {
					Log.e(title[0], "打开excel出错！");
				}
				myfilename_newString=title[0].toString();
				if (wb != null) {

					list = wb.getSheets();
					Sheet[] sheet = wb.getSheets();
					int rows = sheet[0].getRows();// 获取总的行数
					int cols = sheet[0].getColumns();// 获取总的列数
					String[][] str = new String[rows][cols];// 定义一个二维数组

					for (int i = 0; i < str.length; i++) {// 读取单元格内容并存放到二维数组中
															// 默认从第一行第一列读取
						for (int j = 0; j < str[i].length; j++) {
							Cell cell = sheet[0].getCell(j, i);
							str[i][j] = cell.getContents().replace(",", "")
									.trim();
						}
					}

					int valuePut = 0;
					// 定义数据连接，打开数据库
				
						dbAdapter = new DBAdapter(Exam_add_table.this,
								"ABC_MYDZTK");
						dbAdapter.open();
						for (int x = 1; x < str.length; x++) {
							ContentValues initialValues = new ContentValues();
							initialValues.put("TestID", (x) + "");
							initialValues.put("TestSubject", str[x][1]);
							initialValues.put("TestAnswer", str[x][2]);
							if (str[x][3].compareTo("单选题") == 0) {
								initialValues.put("TestType", "1");
								count_r++;
							} else if (str[x][3].compareTo("多选题") == 0) {
								initialValues.put("TestType", "3");
								count_r++;
							} else if (str[x][3].compareTo("判断题") == 0) {
								initialValues.put("TestType", "2");
								count_r++;
							} else {
								count_w++;
								exception_inf=exception_inf+"，"+x;
							}

							initialValues.put("TestBelong", "");
							initialValues.put("AnswerA", str[x][4]);
							initialValues.put("AnswerB", str[x][5]);
							initialValues.put("AnswerC", str[x][6]);
							initialValues.put("AnswerD", str[x][7]);
							initialValues.put("AnswerE", str[x][8]);
							initialValues.put("AnswerF", str[x][9]);
							initialValues.put("ImageName", "image");
							initialValues.put("Expr1", "0");
							initialValues.put("TestTips", "");
							try {
							dbAdapter.DBInsert(initialValues);
							valuePut = (int) ((x / str.length) * 100);
							publishProgress(valuePut);
							} catch (Exception e) {
								count_w++;
								exception_inf=exception_inf+"，"+x;
								Log.e("open()", "数据库建立错！");
							}
						}

						dbAdapter.close();
					

					wb.close();

					finish();
				}

			}
			return "OK";
		}

		// 此处留作增加判断用
		public boolean isExistTitle(String title) {
			DBAdapter dbAdapter1 = new DBAdapter(Exam_add_table.this,
					"ABC_MYDZTK");
			dbAdapter1.open();
			int m = dbAdapter1.getAllData().getCount();
			dbAdapter1.close();
			if (m > 0) {
				Toast Msg = Toast.makeText(Exam_add_table.this,
						"抱歉，自定义题库已存在，已终止", Toast.LENGTH_LONG);
				Msg.show();
				//startActivity(title);
				finish();
				return false;// 暂时修改哈，，发布时应该为true

			} else
				return false;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result.compareTo("OK") == 0) {
			//	Exam_add_table.this.startActivity(title[0]);
				//Exam_add_table.this.startActivity(getIntent());
				Intent intent = new Intent();				
				intent.putExtra("count_r", count_r);
				intent.putExtra("count_w", count_w);
				intent.putExtra("filename", myfilename_newString);
				intent.putExtra("exception_inf", exception_inf);
				intent.setClass(Exam_add_table.this, Operation_table.class);
				startActivity(intent);
			}
		}

	}
}

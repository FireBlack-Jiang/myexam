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
	// jxl.*���Ƕ�дexcel�����ļ���
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
	public int count_r = 0;// ������⵼��ɹ���ʧ����
	public int count_w = 0;
public String myfilename_newString=null;//������¸�������ļ�����
public String exception_inf="";
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.exam_add_table);
		tview1 = (TextView) findViewById(R.id.textView1);
		filesList = (ListView) findViewById(R.id.listView1);

		if (isSdCardExist()) {
			// ��ȡ�ⲿsdcardĿ¼
			sdcardDir = Environment.getExternalStorageDirectory();
			tview1.setText(" 1��������������ģ���ļ���΢�Ź��ں��з���--�ظ���ģ����⡰���أ�" + "\r\n"
					+ " 2��������XLS��׼�ļ�����ģ��淶�����ã�������SD����Ŀ¼��(��Ҫ���κ��ļ�������)��"
					+ "\r\n"
					+ " 3���ļ��������ָ��Ŀ¼������ϵͳ�����Զ�ʶ��������Զ�����������⣬����ж�س������������"
					+ "\r\n" + " 4�����������������⣬�빫�ںý�ͼ����");
			// �����ļ���LIST
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

	// ��ȡ�ļ������б�
	private ArrayList getXls() {
		ArrayList<String> xlsList = new ArrayList<String>();
		if (sdcardDir != null) {
			String[] files = sdcardDir.list(new FilenameFilter() {

				@Override
				public boolean accept(File arg0, String arg1) {
					// TODO Auto-generated method stub
					// ����.xls�ļ��ļ���
					return arg1.endsWith(FIlESUFFIX);
				}
			});
			for (String str : files) {
				xlsList.add(str);
			}

		} else {
			Toast m = Toast.makeText(this, "��Ǹ��Ѱ�Ҵ洢����������", Toast.LENGTH_LONG);
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
		 * ����
		 */
		mydialog = new ProgressDialog(this, ProgressDialog.STYLE_HORIZONTAL);
		mydialog.setTitle("������ʾ");
		mydialog.setMessage("����ת�������Ժ�........");
		mydialog.setCancelable(false);
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("��ʾ");
		builder.setMessage("�Ƿ���" + title + "(    !!!!���ȷ������Ҫ���ĵȴ�1-4���ӣ���Ҫ�����ֻ�)");
		builder.setPositiveButton("ȷ��", new OnClickListener() {

			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				new myTranslate().execute(title);
			}
		});
		builder.setNegativeButton("ȡ��", new OnClickListener() {

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
				// ������ĵ�����ϸ����
				// ��excel
				try {
					String ssss = Environment.getExternalStorageDirectory()
							+ File.separator + title[0];
					File f = new File(ssss);
					wb = Workbook.getWorkbook(f);
				} catch (Exception e) {
					Log.e(title[0], "��excel����");
				}
				myfilename_newString=title[0].toString();
				if (wb != null) {

					list = wb.getSheets();
					Sheet[] sheet = wb.getSheets();
					int rows = sheet[0].getRows();// ��ȡ�ܵ�����
					int cols = sheet[0].getColumns();// ��ȡ�ܵ�����
					String[][] str = new String[rows][cols];// ����һ����ά����

					for (int i = 0; i < str.length; i++) {// ��ȡ��Ԫ�����ݲ���ŵ���ά������
															// Ĭ�ϴӵ�һ�е�һ�ж�ȡ
						for (int j = 0; j < str[i].length; j++) {
							Cell cell = sheet[0].getCell(j, i);
							str[i][j] = cell.getContents().replace(",", "")
									.trim();
						}
					}

					int valuePut = 0;
					// �����������ӣ������ݿ�
				
						dbAdapter = new DBAdapter(Exam_add_table.this,
								"ABC_MYDZTK");
						dbAdapter.open();
						for (int x = 1; x < str.length; x++) {
							ContentValues initialValues = new ContentValues();
							initialValues.put("TestID", (x) + "");
							initialValues.put("TestSubject", str[x][1]);
							initialValues.put("TestAnswer", str[x][2]);
							if (str[x][3].compareTo("��ѡ��") == 0) {
								initialValues.put("TestType", "1");
								count_r++;
							} else if (str[x][3].compareTo("��ѡ��") == 0) {
								initialValues.put("TestType", "3");
								count_r++;
							} else if (str[x][3].compareTo("�ж���") == 0) {
								initialValues.put("TestType", "2");
								count_r++;
							} else {
								count_w++;
								exception_inf=exception_inf+"��"+x;
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
								exception_inf=exception_inf+"��"+x;
								Log.e("open()", "���ݿ⽨����");
							}
						}

						dbAdapter.close();
					

					wb.close();

					finish();
				}

			}
			return "OK";
		}

		// �˴����������ж���
		public boolean isExistTitle(String title) {
			DBAdapter dbAdapter1 = new DBAdapter(Exam_add_table.this,
					"ABC_MYDZTK");
			dbAdapter1.open();
			int m = dbAdapter1.getAllData().getCount();
			dbAdapter1.close();
			if (m > 0) {
				Toast Msg = Toast.makeText(Exam_add_table.this,
						"��Ǹ���Զ�������Ѵ��ڣ�����ֹ", Toast.LENGTH_LONG);
				Msg.show();
				//startActivity(title);
				finish();
				return false;// ��ʱ�޸Ĺ���������ʱӦ��Ϊtrue

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

package abc.myexam;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class Search_Main extends Activity implements
		SearchView.OnQueryTextListener {
	public String Key_Words="";
	List<String> list;
	Cursor cursor;
	ListView listView;
	SearchView searchView;
	Object[] names;
	ArrayAdapter<String> adapter;
	public ArrayList<String> mAllList = new ArrayList<String>();
   String m;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_main);
		initActionbar();
		names = loadData();
		listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(new ArrayAdapter<Object>(getApplicationContext(),
				R.layout.my_list_item, names));

		// listView.setTextFilterEnabled(true);
		searchView.setOnQueryTextListener(this);
		searchView.setSubmitButtonEnabled(false);

		// SearchViewȥ�����޸ģ�������ı��� �޸Ĺ��
		// setSearchViewBackground(searchView);

		int txt_id = searchView.getContext().getResources()
				.getIdentifier("android:id/search_src_text", null, null);
		TextView textView = (TextView) searchView.findViewById(txt_id);
		textView.setTextColor(Color.WHITE);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//String tmp = list.get(arg2);
				//tmp = tmp.substring(0, tmp.indexOf('.'));
				//String mx=list.get(1);textView
				String tmp;
				Intent intent = new Intent();
				if(Key_Words.compareTo("")!=0)
				{
					
					Object[] obj = searchItem(Key_Words);
					listView.setFilterText(Key_Words);
					 tmp=obj[arg2].toString();
				
					//String tmp = (String)list.get(arg2);
					tmp=tmp.substring(0, tmp.indexOf('.'));
				}
				else
				{
					tmp=1+arg2+"";
				}
				
				intent.putExtra("data",m );
				//intent.putExtra("option", MainActivity.OPTION_WRONGEXERCISE);
				intent.putExtra("my", Integer.parseInt(tmp)-1);
				intent.setClass(Search_Main.this, Search_detail.class);
				startActivity(intent);
			}
		});
	}

	public void initActionbar() {
		// �Զ��������
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowCustomEnabled(true);
		LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View mTitleView = mInflater.inflate(R.layout.custom_action_bar_layout,
				null);
		getActionBar().setCustomView(
				mTitleView,
				new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT));
		searchView = (SearchView) mTitleView.findViewById(R.id.search_view);
	}

	public Object[] loadData() {
		int limit = 0;
		Bundle bundle = getIntent().getExtras();
		String data = bundle.getString("data");// ���ѡ��
		String ct_name = bundle.getString("data_name");
		 m=bundle.getString("data");
		try {
			DBAdapter dbAdapter = new DBAdapter(this, data);
			dbAdapter.open();
			cursor = dbAdapter.getAllData();
			limit = cursor.getCount();
		} catch (Exception e) {
			// TODO: handle exception
		}
		try{
			for (int i = 0; i < limit; i++) {
				cursor.moveToPosition(i);
				mAllList.add(i
						+ 1
						+ "."
						+ cursor.getString(cursor
								.getColumnIndex(DBAdapter.TESTSUBJECT)));
			}
		}catch (Exception e){
			
		}
		
		return mAllList.toArray();
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		Object[] obj = searchItem(newText);
		updateLayout(obj);
		Key_Words=newText;
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		return false;
	}

	public Object[] searchItem(String name) {
		ArrayList<String> mSearchList = new ArrayList<String>();
		for (int i = 0; i < mAllList.size(); i++) {
			int index = mAllList.get(i).indexOf(name);
			// ����ƥ�������
			if (index != -1) {
				mSearchList.add(mAllList.get(i));
			}
		}
		return mSearchList.toArray();
	}

	public void updateLayout(Object[] obj) {
		listView.setAdapter(new ArrayAdapter<Object>(getApplicationContext(),
				R.layout.my_list_item, obj));
	}

	// android4.0 SearchViewȥ�����޸ģ�������ı��� �޸Ĺ��
	public void setSearchViewBackground(SearchView searchView) {
		try {
			Class<?> argClass = searchView.getClass();
			// ָ��ĳ��˽������
			Field ownField = argClass.getDeclaredField("mSearchPlate"); 
			// ע��mSearchPlate�ı�����stateListDrawable(��ͬ״̬��ͬ��ͼƬ)
																		// ���Բ�����BitmapDrawable
			// setAccessible �������������Ƿ���Ȩ�޷��ʷ������е�˽�����Եģ�ֻ������Ϊtrueʱ�ſ��Է��ʣ�Ĭ��Ϊfalse
			ownField.setAccessible(true);
			View mView = (View) ownField.get(searchView);
			mView.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.ic_menu_search));
			int id = searchView.getContext().getResources()
					.getIdentifier("android:id/search_src_text", null, null);
			TextView textView = (TextView) searchView.findViewById(id);
			textView.setTextColor(Color.WHITE);

			// ָ��ĳ��˽������
			Field mQueryTextView = argClass.getDeclaredField("mQueryTextView");
			mQueryTextView.setAccessible(true);
			Class<?> mTextViewClass = mQueryTextView.get(searchView).getClass().getSuperclass().getSuperclass().getSuperclass();
			// mCursorDrawableRes���ͼƬId������
			// ���������TextView�����ԣ�����Ҫ��mQueryTextView��SearchAutoComplete���ĸ��ࣨAutoCompleteTextView���ĸ�
			// ��( EditText���ĸ���(TextView)
			Field mCursorDrawableRes = mTextViewClass.getDeclaredField("mCursorDrawableRes");
			// setAccessible �������������Ƿ���Ȩ�޷��ʷ������е�˽�����Եģ�ֻ������Ϊtrueʱ�ſ��Է��ʣ�Ĭ��Ϊfalse
			mCursorDrawableRes.setAccessible(true);
			mCursorDrawableRes.set(mQueryTextView.get(searchView),
	R.drawable.ic_action_search);// ע���һ�����������������(mQueryTextView)�Ķ���(mSearchView)
// ��������һ��ͼƬ��������ɫ����Ϊ���������ͼƬ��һ���ǵ�һ�λ�ý����ʱ�����˸��ͼƬ��һ���Ǻ��������ʱ���ͼƬ���������ɫ���Ļ����ͻ�ʧȥ��˸������ͼƬ����ɫ���Ļ��������ֺ͹��ľ��루ĳЩ��ĸ�ᱳ��긲��һ���֣���
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

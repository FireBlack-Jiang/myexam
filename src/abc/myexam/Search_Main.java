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

		// SearchView去掉（修改）搜索框的背景 修改光标
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
		// 自定义标题栏
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
		String data = bundle.getString("data");// 题库选择
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
			// 存在匹配的数据
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

	// android4.0 SearchView去掉（修改）搜索框的背景 修改光标
	public void setSearchViewBackground(SearchView searchView) {
		try {
			Class<?> argClass = searchView.getClass();
			// 指定某个私有属性
			Field ownField = argClass.getDeclaredField("mSearchPlate"); 
			// 注意mSearchPlate的背景是stateListDrawable(不同状态不同的图片)
																		// 所以不能用BitmapDrawable
			// setAccessible 它是用来设置是否有权限访问反射类中的私有属性的，只有设置为true时才可以访问，默认为false
			ownField.setAccessible(true);
			View mView = (View) ownField.get(searchView);
			mView.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.ic_menu_search));
			int id = searchView.getContext().getResources()
					.getIdentifier("android:id/search_src_text", null, null);
			TextView textView = (TextView) searchView.findViewById(id);
			textView.setTextColor(Color.WHITE);

			// 指定某个私有属性
			Field mQueryTextView = argClass.getDeclaredField("mQueryTextView");
			mQueryTextView.setAccessible(true);
			Class<?> mTextViewClass = mQueryTextView.get(searchView).getClass().getSuperclass().getSuperclass().getSuperclass();
			// mCursorDrawableRes光标图片Id的属性
			// 这个属性是TextView的属性，所以要用mQueryTextView（SearchAutoComplete）的父类（AutoCompleteTextView）的父
			// 类( EditText）的父类(TextView)
			Field mCursorDrawableRes = mTextViewClass.getDeclaredField("mCursorDrawableRes");
			// setAccessible 它是用来设置是否有权限访问反射类中的私有属性的，只有设置为true时才可以访问，默认为false
			mCursorDrawableRes.setAccessible(true);
			mCursorDrawableRes.set(mQueryTextView.get(searchView),
	R.drawable.ic_action_search);// 注意第一个参数持有这个属性(mQueryTextView)的对象(mSearchView)
// 光标必须是一张图片不能是颜色，因为光标有两张图片，一张是第一次获得焦点的时候的闪烁的图片，一张是后边有内容时候的图片，如果用颜色填充的话，就会失去闪烁的那张图片，颜色填充的会缩短文字和光标的距离（某些字母会背光标覆盖一部分）。
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package abc.myexam;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MenuFragment extends Fragment implements SetClass{
	private static final String TAG="MenuFragment";
	private CallBack callBack;
	private RelativeLayout layout_one;
	public	String is_newdatabaseString="";
	public String my_table;
	public TextView TextThr;
	public TextView TextOne;
	/*@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		callBack = (CallBack) getActivity();
	}*/
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		callBack = (CallBack) getActivity();
	}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.layout_menu, null);
    	
    	final TextView TextTwo = (TextView) view.findViewById(R.id.TextTwo);
    	
    	TextOne = (TextView) view.findViewById(R.id.TextOne);
    	//撤掉TextThr
  	    ImageView image1=(ImageView)view.findViewById(R.id.one);
  	   // image1.setVisibility(View.GONE);
  		TextThr = (TextView) view.findViewById(R.id.TextThr);
    	TextThr.setVisibility(View.GONE);
    	ImageView image3=(ImageView)view.findViewById(R.id.three);
    	image3.setVisibility(View.GONE);   	  
    	RelativeLayout layout4=(RelativeLayout)view.findViewById(R.id.layoutfour);
    	layout4.setVisibility(View.GONE);
    	RelativeLayout layout5=(RelativeLayout)view.findViewById(R.id.layoutfive);
    	layout5.setVisibility(View.GONE);
    	
    	final TextView TextFour = (TextView) view.findViewById(R.id.TextFour);
    	final TextView TextFive = (TextView) view.findViewById(R.id.TextFive);
    	final TextView TextSix = (TextView) view.findViewById(R.id.TextSix);
    	final TextView TextSeven = (TextView) view.findViewById(R.id.TextSeven);
    	final TextView TextEight = (TextView) view.findViewById(R.id.TextEight);
    	final TextView TextNine = (TextView) view.findViewById(R.id.TextNine);
    	final TextView TextTen = (TextView) view.findViewById(R.id.TextTen);
    	final TextView TextEleven = (TextView) view.findViewById(R.id.TextElenven);
    	final TextView TextTwelve=(TextView) view.findViewById(R.id.TextTwelve);
    	final TextView TextThirteen=(TextView)view.findViewById(R.id.TextThirteen);
    	final TextView TextFurteen=(TextView)view.findViewById(R.id.TextFurteen);
    	final TextView TextFifteen=(TextView)view.findViewById(R.id.TextFifteen);
      	//订制题库
   	 layout_one=(RelativeLayout)view.findViewById(R.id.myTextdingzhi);
   	 TextView dBManagerTextView=(TextView)view.findViewById(R.id.Textdingzhi);
   final	 TextView TextSixteen=(TextView)view.findViewById(R.id.TextSixteeth);
   	DBAdapter dbAdapter1=new DBAdapter(getActivity(),"ABC_MYDZTK");
		dbAdapter1.open();
		int m=dbAdapter1.getAllData().getCount();
		dbAdapter1.close();
		if(m>0)
		{
			dBManagerTextView.setText("题库管理");
			String title_name="";
			DBAdapter dbAdapter2=new DBAdapter(getActivity(),"ABC_MYDZTK");
			dbAdapter2.open();//查询test_list表的数据库表的表名
			Cursor cursor_dbname=dbAdapter2.querytest_name("ABC_MYDZTK");
			cursor_dbname.moveToFirst();
			title_name=cursor_dbname.getString(cursor_dbname.getColumnIndex("testname"));
			cursor_dbname.close();
			dbAdapter2.close();
			TextSixteen.setText(title_name);
		}
		else
		{	
			layout_one.setVisibility(View.GONE);
		}
	
	    is_newdatabaseString=dBManagerTextView.getText().toString().trim();
	    dBManagerTextView.setOnClickListener(new OnClickListener(){
			@Override
public void onClick(View v)
			{				
					Intent intent = new Intent();
					DBAdapter dbAdapter;
					Cursor cursor;
					dbAdapter = new DBAdapter(getActivity(),"ABC_TEST");
					dbAdapter.open();
					cursor = dbAdapter.getAllData();
					cursor.moveToFirst();
					my_table=cursor.getString(cursor.getColumnIndex(DBAdapter.TESTTIP));
					String data=cursor.getString(cursor.getColumnIndex(DBAdapter.IMAGENAME));
					dbAdapter.close();			
					if (data!=null||data!="") {
						intent.putExtra("dbname", data);
						intent.putExtra("dbtable", my_table);
						intent.setClass(getActivity(), Edit_exam_table.class);
						startActivity(intent);	
					}					
				
				close();
				
			}
		});
		//设置view监听
		TextSixteen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextSixteen.getText();
				callBack.SuccessText(TextSixteen.getText().toString());
				Log.i(TAG, "==-->callBack.SuccessText(TextOne.getText().toString())");
				close();
			}
		});	
    	TextOne.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextOne.getText();
				callBack.SuccessText(TextOne.getText().toString());
				Log.i(TAG, "==-->callBack.SuccessText(TextOne.getText().toString())");
				close();
			}
		});
    	TextTwo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callBack.SuccessText(TextTwo.getText().toString());
				close();
			}

			
		});
    	//撤销第三个
    	/*TextThr.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callBack.SuccessText(TextThr.getText().toString());
				close();
			}
		});*/
    	TextFour.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callBack.SuccessText(TextFour.getText().toString());
				close();
			}
		});
    	TextFive.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callBack.SuccessText(TextFive.getText().toString());
				close();
			}
		});
    	TextSix.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callBack.SuccessText(TextSix.getText().toString());
				close();
			}
		});
    	TextSeven.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callBack.SuccessText(TextSeven.getText().toString());
				close();
			}
		});
    	TextEight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callBack.SuccessText(TextEight.getText().toString());
				close();
			}
		});
    	TextNine.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callBack.SuccessText(TextNine.getText().toString());
				close();
			}
		});
TextTen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callBack.SuccessText(TextTen.getText().toString());
				close();
			}
		});
TextEleven.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		callBack.SuccessText(TextEleven.getText().toString());
		close();
	}
});
TextTwelve.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		callBack.SuccessText(TextTwelve.getText().toString());
		close();
	}
});
//河南
TextThirteen.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		callBack.SuccessText(TextThirteen.getText().toString());
		close();
	}
});
//安徽全版
TextFurteen.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		callBack.SuccessText(TextFurteen.getText().toString());
		close();
	}
});
//安徽精简版
TextFifteen.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		callBack.SuccessText(TextFifteen.getText().toString());
		close();
	}
});
        return view;
    }
    private void close() {
		if (getActivity() instanceof MainActivity) {
			MainActivity activity  = (MainActivity) getActivity(); 
			activity.setToggle();
		}
	}
    @Override
   public void setDBTable(String my_db)
    {  	
    	
    }
}

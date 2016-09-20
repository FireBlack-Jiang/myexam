package abc.myexam;

import fda.jkl.iew.AdManager;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public  class CommanOperation extends Activity{
	protected void onCreate(Bundle savedInstanceState)
	{
		  super.onCreate(savedInstanceState);
	}
	//获得程序名和版本号
	public static String getVersion(Context newContext) {
		try {
	        PackageManager manager = newContext.getPackageManager();
	        PackageInfo info = manager.getPackageInfo(newContext.getPackageName(), 0);
	        String version = info.versionName;
	        return newContext.getString(R.string.app_name) + version;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return newContext.getString(R.string.err_getversion);
	    }
	}
	//Toast 显示
	public static void MyToast(Context newContext,String textString) {
		
		Toast.makeText(newContext, textString, Toast.LENGTH_LONG).show();
	}
	//获取在线参数（有米） 同步调用方法获取，有失败可能。
	public static String GetOnlineConfig(Context context,String my_keywords) {
		String returnString="";          
		// 方法一：同步调用，务必在非 UI 线程中调用，否则可能会失败。	
	    returnString = AdManager.getInstance(context).syncGetOnlineConfig(my_keywords, "");
//	    if (returnString!="") {
//			return returnString;
//		}
//	    else {
//	    	// 方法二： 异步调用（可在任意线程中调用）
//			AdManager.getInstance(context).asyncGetOnlineConfig(my_keywords, new OnlineConfigCallBack() {
//			    @Override
//			    public void onGetOnlineConfigSuccessful(String 
//			    		key, String value) {
//			        // TODO Auto-generated method stub
//			        // 获取在线参数成功
//			 Handler getstirngHandler= new Handler();
//			 Message message=getstirngHandler.obtainMessage();
//			 message.obj=value;
//			 getstirngHandler.sendMessage(message);
//			 Looper.prepare();
//			 Looper.loop();
//			//returnString=message.obj.toString();
//			    }	    
//			    @Override
//			    public void onGetOnlineConfigFailed(String key) {
//			        // TODO Auto-generated method stub
//			        // 获取在线参数失败，可能原因有：键值未设置或为空、网络异常、服务器异常
//			    	//show_total("获取在线参数失败");
//			    }
//			});
//			return returnString;
//		}
	    return returnString;
	}	
	//LinearLayout字体大小函数
	public static void ChangeTextSizeOp(Context myconteContext,LinearLayout mylinearLayout,String flag) {
		for(int i=0;i<mylinearLayout.getChildCount();i++)
		{   
		Object v=mylinearLayout.getChildAt(i);
		 if (v instanceof LinearLayout)
		 {
			 ChangeTextSizeOp(myconteContext,(LinearLayout)v,flag);
		 }
		 else if (v instanceof RadioGroup)
		 {
			 ChangeTextSizeOp(myconteContext,(RadioGroup)v,flag);
		 }
		 else if (v instanceof RelativeLayout)
		 {
			 //ChangeTextSizeOp(myconteContext, (RelativeLayout)v, flag);
		 }
		 else if (v instanceof CheckBox)
		 {
			 CheckBox  tv=(CheckBox)v;
			 ChangeTextSizeOp(myconteContext, tv, flag);
		 }
		 else if (v instanceof TextView)
		 { 
			   TextView  tv=(TextView)v;
			   ChangeTextSizeOp(myconteContext, tv, flag);
		 }
		}	
	}
	public static void ChangeTextSizeOp(Context myconteContext,RadioGroup myRadioGroup,String flag) 
	{
		for(int i=0;i<myRadioGroup.getChildCount();i++)
		{
			ChangeTextSizeOp(myconteContext,(RadioButton)myRadioGroup.getChildAt(i),flag);
		}
	}
	//TextView字体大小函数
	public static void ChangeTextSizeOp(Context context,TextView tv,String flag) {
		 float cur_textsize=tv.getTextSize();	
			if (flag=="+") {
				cur_textsize++;cur_textsize++;
				tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, cur_textsize);
			} 
			else if (flag=="-") {
				cur_textsize--;cur_textsize--;
				tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,cur_textsize);
			} 
			else {
				tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,Float.valueOf(flag));
			}
	}
	//CheckBox字体大小函数
	public static void ChangeTextSizeOp(Context context,CheckBox tv,String flag) {
		
		 float cur_textsize=tv.getTextSize();	
			if (flag=="+") {
				cur_textsize++;cur_textsize++;
				tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, cur_textsize);
			} 
			else if (flag=="-") {
				cur_textsize--;cur_textsize--;
				tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,cur_textsize);
			} 
			else {
				tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,Float.valueOf(flag));
			}
	}
	//RadioButton字体大小函数
	public static void ChangeTextSizeOp(Context context,RadioButton tv,String flag) {
		 float cur_textsize=tv.getTextSize();	
			if (flag=="+") {
				cur_textsize++;cur_textsize++;
				tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, cur_textsize);
			} 
			else if (flag=="-") {
				cur_textsize--;cur_textsize--;
				tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,cur_textsize);
			} 
			else {
				tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,Float.valueOf(flag));
			}
	}
	//获取xml文件存储的字体大小
	public  float get_textsizeconfig() {
		SharedPreferences sharedPreferences = getSharedPreferences(
				MainActivity.PREFERENCE_NAME, MainActivity.MODE);
		return sharedPreferences.getFloat(MainActivity.CONFIG_TEXTSIZE, 45);
	}
}

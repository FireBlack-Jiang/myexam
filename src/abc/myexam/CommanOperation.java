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
	//��ó������Ͱ汾��
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
	//Toast ��ʾ
	public static void MyToast(Context newContext,String textString) {
		
		Toast.makeText(newContext, textString, Toast.LENGTH_LONG).show();
	}
	//��ȡ���߲��������ף� ͬ�����÷�����ȡ����ʧ�ܿ��ܡ�
	public static String GetOnlineConfig(Context context,String my_keywords) {
		String returnString="";          
		// ����һ��ͬ�����ã�����ڷ� UI �߳��е��ã�������ܻ�ʧ�ܡ�	
	    returnString = AdManager.getInstance(context).syncGetOnlineConfig(my_keywords, "");
//	    if (returnString!="") {
//			return returnString;
//		}
//	    else {
//	    	// �������� �첽���ã����������߳��е��ã�
//			AdManager.getInstance(context).asyncGetOnlineConfig(my_keywords, new OnlineConfigCallBack() {
//			    @Override
//			    public void onGetOnlineConfigSuccessful(String 
//			    		key, String value) {
//			        // TODO Auto-generated method stub
//			        // ��ȡ���߲����ɹ�
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
//			        // ��ȡ���߲���ʧ�ܣ�����ԭ���У���ֵδ���û�Ϊ�ա������쳣���������쳣
//			    	//show_total("��ȡ���߲���ʧ��");
//			    }
//			});
//			return returnString;
//		}
	    return returnString;
	}	
	//LinearLayout�����С����
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
	//TextView�����С����
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
	//CheckBox�����С����
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
	//RadioButton�����С����
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
	//��ȡxml�ļ��洢�������С
	public  float get_textsizeconfig() {
		SharedPreferences sharedPreferences = getSharedPreferences(
				MainActivity.PREFERENCE_NAME, MainActivity.MODE);
		return sharedPreferences.getFloat(MainActivity.CONFIG_TEXTSIZE, 45);
	}
}

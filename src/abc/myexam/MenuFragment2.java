package abc.myexam;


import abc.myexam.wxapi.WXEntryActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class MenuFragment2 extends Fragment {
	private CallBack callBack;
	public TextView versionTextView;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		callBack = (CallBack) getActivity();
	}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.layout_menu2, null);
    	versionTextView=(TextView) view.findViewById(R.id.TextView012);
    	versionTextView.setText("3.����Ŀǰ�汾:"+getVersion()+"�����������������£����������ܻ��й�棬�޲����������صĹ�����ע��رշ��Ͷ��ŵ�Ȩ�ޣ�����Ҫ����������ÿ��ԣ����໥����");
    	final TextView TextRight = (TextView) view.findViewById(R.id.TextRight);
    	final TextView TextLeft = (TextView) view.findViewById(R.id.TextLeft);
    	TextLeft.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//callBack.SuccessText(TextRight.getText().toString());
				Intent intent = new Intent();
				intent.setClass((MainActivity) getActivity(), WXEntryActivity.class);
				startActivity(intent);		
			}

			
		});
    	TextRight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//callBack.SuccessText(TextRight.getText().toString());
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
    /**
	 * ��ȡ�汾��
	 * @return ��ǰӦ�õİ汾��
	 */
	public String getVersion() {
	    try {
	        PackageManager manager = getActivity().getPackageManager();
	        PackageInfo info = manager.getPackageInfo(getActivity().getPackageName(), 0);
	        String version = info.versionName;
	        return version;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return this.getString(R.string.err_getversion);
	    }
	}
}

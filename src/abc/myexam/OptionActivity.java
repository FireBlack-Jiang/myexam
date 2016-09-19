package abc.myexam;

import abc.myexam.R.integer;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class OptionActivity extends Activity {

	CheckBox chk_autocheck;
	CheckBox chk_auto2next;
	CheckBox chk_auto2addWAset;
	CheckBox chk_sound;
	CheckBox chk_checkbyrdm;

	Button btn_saveSetting;
	Button btn_return;
    Button bt_changesizebigger;
    Button bt_changesizesmaller;
    TextView tView;
    LinearLayout lv;
    public  float textsizevalue;//字体大小默认值
    SeekBar myseekBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.optionlayout);
        tView=(TextView)findViewById(R.id.mychangeTextviewid);
        lv=(LinearLayout)findViewById(R.id.mylinearLayout);
		chk_autocheck = (CheckBox) findViewById(R.id.chk_autocheck);
		chk_auto2next = (CheckBox) findViewById(R.id.chk_auto2next);
		chk_auto2addWAset = (CheckBox) findViewById(R.id.chk_auto2addWAset);
		chk_sound = (CheckBox) findViewById(R.id.chk_sound);
		chk_checkbyrdm = (CheckBox) findViewById(R.id.chk_checkbyrdm);
		bt_changesizebigger=(Button)findViewById(R.id.btn_changgesizebigger);
		bt_changesizesmaller=(Button)findViewById(R.id.btn_changgesizesmaller);
		btn_saveSetting = (Button) findViewById(R.id.btn_savesetting);
		btn_return = (Button) findViewById(R.id.btn_return);
		myseekBar=(SeekBar)findViewById(R.id.seekBar1);
		//CONGIG初始化
		configInit();
		//int defaultvalue=Integer.valueOf(textsizevalue+"");
		myseekBar.setProgress(50);
		CommanOperation.ChangeTextSizeOp(this, lv, textsizevalue+"");
		myseekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				textsizevalue=tView.getTextSize();
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				CommanOperation.ChangeTextSizeOp(OptionActivity.this,lv , progress+"");
			}
		});
		bt_changesizebigger.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CommanOperation.ChangeTextSizeOp(OptionActivity.this,lv , "+");
				textsizevalue=tView.getTextSize();
			}
		});
		bt_changesizesmaller.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CommanOperation.ChangeTextSizeOp(OptionActivity.this,lv , "-");
				textsizevalue=tView.getTextSize();
			}
		});
		btn_saveSetting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveSettingAction();
				finish();
			}
		});
		
		btn_return.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	public void saveSettingAction(){
		try {
			SharedPreferences sharedPreferences = getSharedPreferences(
					MainActivity.PREFERENCE_NAME, MainActivity.MODE);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putBoolean(MainActivity.CONFIG_AUTOCHECK, chk_autocheck.isChecked());
			editor.putBoolean(MainActivity.CONFIG_AUTO2NEXT, chk_auto2next.isChecked());
			editor.putBoolean(MainActivity.CONFIG_AUTO2ADDWRONGSET, chk_auto2addWAset.isChecked());
			editor.putBoolean(MainActivity.CONFIG_SOUND, chk_sound.isChecked());
			editor.putBoolean(MainActivity.CONFIG_CHECKBYRANDOM, chk_checkbyrdm.isChecked());
			editor.putFloat(MainActivity.CONFIG_TEXTSIZE, textsizevalue);
			editor.commit();
			ShowToast("保存配置成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ShowToast("保存配置错误");
			e.printStackTrace();
		}
	}
	public void ShowToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	private void configInit() {
		// TODO Auto-generated method stub
		SharedPreferences sharedPreferences = getSharedPreferences(
				MainActivity.PREFERENCE_NAME, MainActivity.MODE);
		chk_autocheck.setChecked(sharedPreferences.getBoolean(MainActivity.CONFIG_AUTOCHECK, false));
		chk_auto2next.setChecked(sharedPreferences.getBoolean(MainActivity.CONFIG_AUTO2NEXT, false));
		chk_auto2addWAset.setChecked(sharedPreferences.getBoolean(MainActivity.CONFIG_AUTO2ADDWRONGSET, false));
		chk_sound.setChecked(sharedPreferences.getBoolean(MainActivity.CONFIG_SOUND, false));
		chk_checkbyrdm.setChecked(sharedPreferences.getBoolean(MainActivity.CONFIG_CHECKBYRANDOM, false));
		textsizevalue=sharedPreferences.getFloat(MainActivity.CONFIG_TEXTSIZE, 45);
		
	}
}

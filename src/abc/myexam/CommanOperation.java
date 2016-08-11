package abc.myexam;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

public  class CommanOperation extends Activity{
	protected void onCreate(Bundle savedInstanceState)
	{
		  super.onCreate(savedInstanceState);
	}
	//获得程序名和版本号
	public String getVersion() {
		try {
	        PackageManager manager = this.getPackageManager();
	        PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
	        String version = info.versionName;
	        return this.getString(R.string.app_name) + version;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return this.getString(R.string.err_getversion);
	    }
	}
}

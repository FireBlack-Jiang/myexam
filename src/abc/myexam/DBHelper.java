package abc.myexam;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	// 1.DatabaseHelper是SQLiteOpenHelper的继承类
	// 2.要复写onCreate 和 onUpgrade

	public final static String DATABSE_NAME = "DTSS_DB";//Driving theory simulation system
	public final static int DATABASE_VERSION = 1;
	public  String DATABSE_TABLE;
	public final static String DATABSE_TABLE2="order_num";
	
	
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	public DBHelper(Context context){
		super(context,DATABSE_NAME,null,DATABASE_VERSION);
		//if(DATABSE_TABLE.compareTo("")==0)
		//{
			DATABSE_TABLE="TestSubject";
		//}
	}
	public DBHelper(Context context,String s)
	{
		super(context,DATABSE_NAME,null,DATABASE_VERSION);
		DATABSE_TABLE=s;
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//当创建的时候调用
		String DATABASE_CREATE = "create table " + DATABSE_TABLE + " (TestID integer primary key autoincrement,"  
			    + "TestSubject text not null, TestAnswer text not null, TestType integer,TestBelong integer,AnswerA text," +
			    		"AnswerB text,AnswerC text,AnswerD text,AnswerE text,AnswerF text,ImageName text,Expr1 integer,TestTips text);"; 
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		//当修改数据库的时候调用
		  //db.execSQL("DROP TABLE IF EXISTS " + DATABSE_TABLE);   
	      //onCreate(db);   
	}
	public void changetable(String s)
	{
		DATABSE_TABLE=s;
	}

}

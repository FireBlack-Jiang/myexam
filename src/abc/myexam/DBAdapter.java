package abc.myexam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


/**
 *这个方法是关于数据库的操作
 * @author jiangshixi
 *
 */
//private final static String DATABASE_CREATE = "create table " + DATABSE_TABLE + " (TestID integer primary key autoincrement,"  
//+ "TestSubject text not null, TestAnswer text not null, TestType integer,TestBelong integer,AnswerA text," +
//		"AnswerB text,AnswerC text,AnswerD text,ImageName text,Expr1 integer);"; 

public class DBAdapter {
	public final static String DATABSE_TABLE2="order_num";
	public  String DATABSE_TABLE;
	public static final String TESTID = "TestID";
	public static final String TESTSUBJECT = "TestSubject";
	public static final String TESTANSWER = "TestAnswer";
	public static final String ANSWERA = "AnswerA";
	public static final String ANSWERB = "AnswerB";
	public static final String ANSWERC = "AnswerC";
	public static final String ANSWERD = "AnswerD";
	public static final String ANSWERE = "AnswerE";
	public static final String ANSWERF = "AnswerF";
	public static final String TESTTIP="TestTips";
	public static final String TESTTPYE = "TestType";
	public static final String TESTBELONG = "TestBelong";
	public static final String EXPR1 = "Expr1";
	public static final String IMAGENAME = "ImageName";
	
	public static final String order_num="order_num";
	public static final String begin_id="id";
	
//一个DataBaseHelper类实例
	private DBHelper dataBaseHelper;
	//Context
	private Context context;
	//SQLiteDatabase;
	SQLiteDatabase sqLiteDatabase;
	
	
	public DBAdapter(Context context){
		//获得上下文信息context
		this.context = context;
		//if(DATABSE_TABLE.compareTo("")==0)
		//{
			DATABSE_TABLE="TestSubject";
		//}
	}
	public DBAdapter(Context context,String s)
	{
		this.context = context;
		
			DATABSE_TABLE=s;
	}
	/*
	 * Open the Database;
	 */
	public void open(){
		if(DATABSE_TABLE.compareTo("TestSubject")==0)
		{dataBaseHelper = new DBHelper(context);}
		else
		{dataBaseHelper = new DBHelper(context,DATABSE_TABLE);}
		
		try {
			sqLiteDatabase = dataBaseHelper.getWritableDatabase();
		} catch (Exception e) {
			// TODO: handle exception
			sqLiteDatabase = dataBaseHelper.getReadableDatabase();
			Log.i("open-->",e.toString());
		}
	}
	 // Close the Database
	public void close(){
		sqLiteDatabase.close();
	}
	public void changetable(String s_table)
	{
		DATABSE_TABLE=s_table;
		dataBaseHelper.changetable(s_table);
	}
	/*
	 * 
	 */
	public long DBInsert(ContentValues cv){
		return sqLiteDatabase.insert(DATABSE_TABLE,null,cv);
	}
	public int table_del(String tablenameString){
		return sqLiteDatabase.delete(tablenameString, null, null);
	}
	 public Cursor getAllData(){  
		 	//System.out.println("ASD");
	        String[] searchResult =  {TESTID,TESTSUBJECT, TESTANSWER,TESTTPYE, TESTBELONG
	        		,ANSWERA,ANSWERB,ANSWERC,ANSWERD,ANSWERE,ANSWERF,IMAGENAME,EXPR1,TESTTIP};  
//	        Cursor tcursor = sqLiteDatabase.query(dataBaseHelper.DATABSE_TABLE, searchResult, null, null, null, null, null); 
//	        System.out.println(tcursor.getString(tcursor
//					.getColumnIndex(NotepadDbAdapter.TITLE)));
//	        System.out.println(tcursor.getString(tcursor
//					.getColumnIndex(NotepadDbAdapter.CREATED)));
	        Log.i("GetAllData","YES");
	        return sqLiteDatabase.query(DATABSE_TABLE, searchResult, null, null, null, null, null);   
	 
	//	 String searchSQL = "select id , title , body ,created from "+ dataBaseHelper.DATABSE_TABLE;  
		// System.out.println(searchSQL);
	    //   	    Cursor tcursor = sqLiteDatabase.rawQuery(searchSQL, null);  
    //  tcursor.moveToFirst();
	      //    System.out.println(tcursor.getString(tcursor
			//			.getColumnIndex(NotepadDbAdapter.TITLE)));
		   //     System.out.println(tcursor.getString(tcursor
				//		.getColumnIndex(NotepadDbAdapter.CREATED)))
	       //   return  tcursor;;
		 } 
	 public Cursor getsomedata(String sqlstring)
	 {
		 String[] searchResult =  {TESTID,TESTSUBJECT, TESTANSWER,TESTTPYE, TESTBELONG
	        		,ANSWERA,ANSWERB,ANSWERC,ANSWERD,ANSWERE,ANSWERF,IMAGENAME,EXPR1,TESTTIP};  
	        Log.i("GetAllData","YES");
	        return sqLiteDatabase.query(DATABSE_TABLE, searchResult, null, null, null, null, null);   
	 
	 }
	 public Cursor get_order()
	 {
		 String[] searchResult ={begin_id,order_num}; 
		 return sqLiteDatabase.query(DBHelper.DATABSE_TABLE2, searchResult, null, null, null, null, null);
	 }
	 public void update_data(ContentValues cv)//MAIN函数更新数据库用
	 {
		  sqLiteDatabase.update(DATABSE_TABLE, cv, "TestID=?", new String[]{"1"});
	 }
	 public void update_data2(ContentValues cv,String testid)//MAIN函数更新数据库用
	 {
		  sqLiteDatabase.update(DATABSE_TABLE, cv, "TestID=?", new String[]{testid});
	 }
	 public void update_myjindu(String sql,String[] canshu)
	 {
		 sqLiteDatabase.execSQL(sql, canshu);
	 }
	 public boolean insert_data()
	 {
		
		 
		 return true;
	 }
	
	
	// 判断表TEST_LIST中的是否包含某个NAME_TEXT的记录
     public Boolean havetestdb(String name_text) {
          Boolean b = false;
          Cursor cursor = sqLiteDatabase.query("TEST_LIST", null,"testname"
                   + "=?", new String[]{name_text}, null, null, null );
          b = cursor.moveToFirst();
          Log. e("HaveUserInfo", b.toString());
          cursor.close();
           return b;
     }
     public String GetName(String table_db) {
         String b = "";
         Cursor cursor = sqLiteDatabase.query("TEST_LIST", null,"test_db"
                  + "=?", new String[]{table_db}, null, null, null );
         if(cursor.getColumnCount()==0)
         {
        	 return b;
         }
         else
         {
        	 cursor.moveToFirst();
             b=cursor.getString(cursor.getColumnIndex("testname"));
            Log. e("Get Table name", b.toString()); 
         }
        
         cursor.close();
          return b;
    }
     //按表名查查表
     public Cursor querytestdb(String name_text) {
        
         Cursor cursor = sqLiteDatabase.query("TEST_LIST", null,"testname"
                  + "=?", new String[]{name_text}, null, null, null );
          return cursor;
    }
     //按表查表名
     public Cursor querytest_name(String name_db) {
         
         Cursor cursor = sqLiteDatabase.query("TEST_LIST", null,"test_db"
                  + "=?", new String[]{name_db}, null, null, null );
          return cursor;
    }
     //按表更新表名
     public int update_name(String name_db, ContentValues values)
     {
    	 int cursor=sqLiteDatabase.update("TEST_LIST", values, "test_db"+"=?",new String[]{name_db});
    	
    	 return cursor;
     }
 public Cursor getdatabytype(String type) {
     
	 Cursor cursor = null;
String sql="select * from "+DATABSE_TABLE+" where TestType="+"?";
	 
String arg[]={type};
//当传入0为查询全部
if (type.compareTo("0")==0) {
	 sql="select * from "+DATABSE_TABLE+" where TestType<>"+"?";
}
try
        {
          cursor = sqLiteDatabase.rawQuery(sql,arg);  
            
        }
	 catch(Exception ex){
			ex.printStackTrace();
			cursor = null;
			}

	 return cursor;
    }
 
}

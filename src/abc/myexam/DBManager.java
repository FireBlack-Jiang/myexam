package abc.myexam;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/* 将把assets下的数据库文件直接复制到DB_PATH，但数据库文件大小限制在1M以下
* 如果有超过1M的大文件，则需要先分割为N个小文件，然后使用copyBigDatabase()替换copyDatabase()
*/
public class DBManager extends SQLiteOpenHelper {
//用户数据库文件的版本
private static final int DB_VERSION = 1;
//数据库文件目标存放路径为系统默认位置，com.rys.lb 是你的包名
private static String DB_PATH = "/data/data/abc.myexam/databases/";

//如果你想把数据库文件存放在SD卡的话
// private static String DB_PATH = android.os.Environment.getExternalStorageDirectory().getAbsolutePath()
// + "/arthurcn/drivertest/packfiles/";

private static String DB_NAME = "DTSS_DB";
private static String ASSETS_NAME = "DTSS_DB.db";

private SQLiteDatabase myDataBase;
private final Context myContext;

/** 
* 如果数据库文件较大，使用FileSplit分割为小于1M的小文件
* 此例中分割为 data.db.100 data.db.101 data.db.102....
*/
//第一个文件名后缀
private static final int ASSETS_SUFFIX_BEGIN = 100;
//最后一个文件名后缀
private static final int ASSETS_SUFFIX_END = 110;

/**
* 在SQLiteOpenHelper的子类当中，必须有该构造函数
* @param context 上下文对象
* @param name 数据库名称
* @param factory 一般都是null
* @param version 当前数据库的版本，值必须是整数并且是递增的状态
*/
public DBManager(Context context, String name, CursorFactory factory, int version) {
//必须通过super调用父类当中的构造函数
super(context, name, null, version);
this.myContext = context;
}

public DBManager(Context context, String name, int version){
this(context,name,null,version);
}

public DBManager(Context context, String name){
this(context,name,DB_VERSION);
}
public DBManager (Context context) {
this(context, DB_PATH + DB_NAME);
}


public void createDataBase() throws IOException{
boolean dbExist = checkDataBase();
//if(dbExist){
//数据库已存在，do nothing.

//System.out.println("数据库已经存在");

//}else{
//创建数据库
try {
File dir = new File(DB_PATH);
if(!dir.exists()){
dir.mkdirs();
}
File dbf = new File(DB_PATH + DB_NAME);
if(dbf.exists()){
dbf.delete();
}
SQLiteDatabase.openOrCreateDatabase(dbf, null);
// 复制asseets中的db文件到DB_PATH下
//copyDataBase();
copyDataBase();
} catch (IOException e) {
throw new Error("数据库创建失败");
}
//}
}

//检查数据库是否有效
private boolean checkDataBase(){
SQLiteDatabase checkDB = null;
String myPath = DB_PATH + DB_NAME;
try{ 
checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
}catch(SQLiteException e){
//database does't exist yet.
}
if(checkDB != null){
checkDB.close();
System.out.println("关闭");
}
return checkDB != null ? true : false;
}
public DBManager open1(){
String myPath = DB_PATH + DB_NAME;
System.out.println("数据库已经...");
myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
System.out.println("数据库打开");
return this;

}
/**
* Copies your database from your local assets-folder to the just created empty database in the
* system folder, from where it can be accessed and handled.
* This is done by transfering bytestream.
* */
private void copyDataBase() throws IOException{
//Open your local db as the input stream
InputStream myInput = myContext.getAssets().open(ASSETS_NAME);
// Path to the just created empty db
String outFileName = DB_PATH + DB_NAME;
//Open the empty db as the output stream
OutputStream myOutput = new FileOutputStream(outFileName);
//transfer bytes from the inputfile to the outputfile
byte[] buffer = new byte[1024];
int length;
while ((length = myInput.read(buffer))>0){
myOutput.write(buffer, 0, length);
}
//Close the streams
myOutput.flush();
myOutput.close();
myInput.close();
}

//复制assets下的大数据库文件时用这个
private void copyBigDataBase() throws IOException{
InputStream myInput;
String outFileName = DB_PATH + DB_NAME;
OutputStream myOutput = new FileOutputStream(outFileName);
for (int i = ASSETS_SUFFIX_BEGIN; i < ASSETS_SUFFIX_END+1; i++) {
myInput = myContext.getAssets().open(ASSETS_NAME + "." + i);
byte[] buffer = new byte[1024];
int length;
while ((length = myInput.read(buffer))>0){
myOutput.write(buffer, 0, length);
}
myOutput.flush();
myInput.close();
}
myOutput.close();
System.out.println("数据库已经复制");
}

@Override
public synchronized void close() {
if(myDataBase != null){
myDataBase.close();
System.out.println("关闭成功1");
}
super.close();
System.out.println("关闭成功2");
}

/**
* 该函数是在第一次创建的时候执行，
* 实际上是第一次得到SQLiteDatabase对象的时候才会调用这个方法
*/
@Override
public void onCreate(SQLiteDatabase db) {
}

/**
* 数据库表结构有变化时采用
*/
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
}
public void open(){
SQLiteDatabase DataBase=this.openOrCreateDatabase("DTSS_DB",

null); 
}
private SQLiteDatabase openOrCreateDatabase(String string, Object object) {
// TODO Auto-generated method stub
return null;
}
}


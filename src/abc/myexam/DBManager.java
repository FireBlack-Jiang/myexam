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

/* ����assets�µ����ݿ��ļ�ֱ�Ӹ��Ƶ�DB_PATH�������ݿ��ļ���С������1M����
* ����г���1M�Ĵ��ļ�������Ҫ�ȷָ�ΪN��С�ļ���Ȼ��ʹ��copyBigDatabase()�滻copyDatabase()
*/
public class DBManager extends SQLiteOpenHelper {
//�û����ݿ��ļ��İ汾
private static final int DB_VERSION = 1;
//���ݿ��ļ�Ŀ����·��ΪϵͳĬ��λ�ã�com.rys.lb ����İ���
private static String DB_PATH = "/data/data/abc.myexam/databases/";

//�����������ݿ��ļ������SD���Ļ�
// private static String DB_PATH = android.os.Environment.getExternalStorageDirectory().getAbsolutePath()
// + "/arthurcn/drivertest/packfiles/";

private static String DB_NAME = "DTSS_DB";
private static String ASSETS_NAME = "DTSS_DB.db";

private SQLiteDatabase myDataBase;
private final Context myContext;

/** 
* ������ݿ��ļ��ϴ�ʹ��FileSplit�ָ�ΪС��1M��С�ļ�
* �����зָ�Ϊ data.db.100 data.db.101 data.db.102....
*/
//��һ���ļ�����׺
private static final int ASSETS_SUFFIX_BEGIN = 100;
//���һ���ļ�����׺
private static final int ASSETS_SUFFIX_END = 110;

/**
* ��SQLiteOpenHelper�����൱�У������иù��캯��
* @param context �����Ķ���
* @param name ���ݿ�����
* @param factory һ�㶼��null
* @param version ��ǰ���ݿ�İ汾��ֵ���������������ǵ�����״̬
*/
public DBManager(Context context, String name, CursorFactory factory, int version) {
//����ͨ��super���ø��൱�еĹ��캯��
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
//���ݿ��Ѵ��ڣ�do nothing.

//System.out.println("���ݿ��Ѿ�����");

//}else{
//�������ݿ�
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
// ����asseets�е�db�ļ���DB_PATH��
//copyDataBase();
copyDataBase();
} catch (IOException e) {
throw new Error("���ݿⴴ��ʧ��");
}
//}
}

//������ݿ��Ƿ���Ч
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
System.out.println("�ر�");
}
return checkDB != null ? true : false;
}
public DBManager open1(){
String myPath = DB_PATH + DB_NAME;
System.out.println("���ݿ��Ѿ�...");
myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
System.out.println("���ݿ��");
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

//����assets�µĴ����ݿ��ļ�ʱ�����
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
System.out.println("���ݿ��Ѿ�����");
}

@Override
public synchronized void close() {
if(myDataBase != null){
myDataBase.close();
System.out.println("�رճɹ�1");
}
super.close();
System.out.println("�رճɹ�2");
}

/**
* �ú������ڵ�һ�δ�����ʱ��ִ�У�
* ʵ�����ǵ�һ�εõ�SQLiteDatabase�����ʱ��Ż�����������
*/
@Override
public void onCreate(SQLiteDatabase db) {
}

/**
* ���ݿ��ṹ�б仯ʱ����
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


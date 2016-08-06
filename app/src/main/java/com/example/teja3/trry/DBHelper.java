package com.example.teja3.trry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by teja3 on 13-07-2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DBName.db";
    public static final String CONTACTS_TABLE_NAME = "backend";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_RESULT = "result";
    public static final String CONTACTS_COLUMN_PRODUCT = "product";
    public static final String CONTACTS_COLUMN_COMPANY = "company";
    public static final String CONTACTS_COLUMN_LATITUDE = "latitude";
    public static final String CONTACTS_COLUMN_LONGITUDE = "longitude";

    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table backend " +
                        "(id integer primary key autoincrement,result text , product text,company text,latitude text,longitude text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS backend");
        onCreate(db);
    }

    public boolean insertContact  (String result,String product, String company, String latitude,String longitude)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("result", result);
        contentValues.put("product", product);
        contentValues.put("company", company);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);

        db.insert("backend", null, contentValues);
        return true;
    }
    public boolean clear(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS backend");
        onCreate(db);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from backend where id="+id+"", null );
        return res;
    }

    public Cursor getDataU(String resut){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res1 =  db.rawQuery("select * from backend where result='" + resut + "'", null);
        //Cursor findEntry = db.query("backend", , "owner=? and price=?", new String[]{owner, price}, null, null, null);
        //Cursor findEntry1 = db.query("backend", , "result=?", new String[] { resut }, null, null, null);
        res1.moveToFirst();
        return res1;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updatebackend (String result, String product, String company, String latitude, String longitude)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("result", result);
        contentValues.put("product", product);
        contentValues.put("company", company);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        //contentValues.put("result", result);
        String con="result='"+result+"'";
        //db.update("backend", contentValues, "result = ? ", new String[] { result } );
        db.update("backend", contentValues, "result='"+result+"'", null );
        return true;
    }

    public void deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+CONTACTS_TABLE_NAME+" where 1");
        //id='"+id+"'
    }

    public ArrayList<String> getAllCotacts()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from backend", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_RESULT)));
            res.moveToNext();
        }
        return array_list;
    }

    public int present(String res){
        SQLiteDatabase db = this.getReadableDatabase();
        int count= (int)DatabaseUtils.queryNumEntries(db, "backend",
                "result=?", new String[] {res});
        return count;
    }
}


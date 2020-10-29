package com.example.to_dolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VESRION=1;
    private static final String DATABASE_NAME="Notes_db";
    private static final String TABLE_NAME="tb1_notes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";

    public DBHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VESRION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

       String  query = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY, Title TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

  public void addNotes(String title){
      SQLiteDatabase db = this .getWritableDatabase();
      ContentValues contentValues=new ContentValues();
      contentValues.put("Title",title);
      db.insert(TABLE_NAME,null,contentValues);
      db.close();
  }

  public ArrayList<NoteModel>getNotes(){
        ArrayList<NoteModel>arrayList=new ArrayList<>();
        String select_query="SELECT *FROM " + TABLE_NAME;
      SQLiteDatabase db = this .getWritableDatabase();
      Cursor cursor = db.rawQuery(select_query, null);
      if (cursor.moveToFirst()){
          do {
              NoteModel noteModel=new NoteModel();
              noteModel.setId(cursor.getString(0));
              noteModel.setTitle(cursor.getString(1));
              arrayList.add(noteModel);
          }while (cursor.moveToNext());
      }
        return arrayList;
  }
    public void delete(String id){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, "ID=" + id, null);
        sqLiteDatabase.close();
    }
    public void update(String title,String ID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put("Title", title);
        sqLiteDatabase.update(TABLE_NAME, values, "ID=" + ID, null);
        sqLiteDatabase.close();


    }
    }


package com.example.banco.databasesample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Banco on 08/01/2019.
 */

public class DBtestiMail extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "TestiMail.db";
    public static final String TESTI_TBL_NAME = "TestiMail";
    public static final String TESTI_COL_ID = "id";
    public static final String TESTI_COL_NAME = "nome";
    public static final String TESTI_COL_INTEST = "intestazione";
    public static final String TESTI_COL_CORPO = "corpo";
    public static final String TESTI_COL_FINE = "conclusione";
    public static final String TESTI_COL_CAMPO1 = "campo1";
    public static final String TESTI_COL_CAMPO2 = "campo2";
    public static final String TESTI_COL_CAMPO3 = "campo3";

    public DBtestiMail(Context context) {
        super(context,DATABASE_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table "+TESTI_TBL_NAME+" " + "("+
                        TESTI_COL_ID+" integer primary key, "+
                        TESTI_COL_NAME+" text, "+
                        TESTI_COL_INTEST+" text, "+
                        TESTI_COL_CORPO+" text, "+
                        TESTI_COL_FINE+" text, "+
                        TESTI_COL_CAMPO1+" text, "+
                        TESTI_COL_CAMPO2+" text, "+
                        TESTI_COL_CAMPO3+" text)"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TESTI_TBL_NAME);
        onCreate(db);
    }
    public boolean insertMail(String nome, String intest, String corpo, String fine, String campo1, String campo2, String campo3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TESTI_COL_NAME, nome);
        contentValues.put(TESTI_COL_INTEST, intest);
        contentValues.put(TESTI_COL_CORPO,corpo);
        contentValues.put(TESTI_COL_FINE, fine);
        contentValues.put(TESTI_COL_CAMPO1, campo1);
        contentValues.put(TESTI_COL_CAMPO2, campo2);
        contentValues.put(TESTI_COL_CAMPO3, campo3);
        db.insert(TESTI_TBL_NAME, null, contentValues);
        return true;
    }
    public Cursor getMail(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TESTI_TBL_NAME+" where "+TESTI_COL_ID+"="+id+"", null );
        return res;
    }
    public Cursor getAllMail() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TESTI_TBL_NAME, null );
        return res;
    }
    public boolean updateMAIL (Integer id,String nome, String intest, String corpo, String fine, String campo1, String campo2, String campo3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TESTI_COL_NAME, nome);
        contentValues.put(TESTI_COL_INTEST, intest);
        contentValues.put(TESTI_COL_CORPO,corpo);
        contentValues.put(TESTI_COL_FINE, fine);
        contentValues.put(TESTI_COL_CAMPO1, campo1);
        contentValues.put(TESTI_COL_CAMPO2, campo2);
        contentValues.put(TESTI_COL_CAMPO3, campo3);
        db.update(TESTI_TBL_NAME, contentValues, TESTI_COL_ID+" = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public Integer deleteMail (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TESTI_TBL_NAME,
                TESTI_COL_ID+" = ? ",
                new String[] { Integer.toString(id) });
    }
}

package com.example.banco.kalodikulo;

//insertAlimento
import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "kalodikulo.db";
    public static final String ALIMENTI_TABLE_NAME = "Alimenti";
    public static final String ALIM_CMP_ID = "id";
    public static final String ALIM_CMP_NOME = "nome";
    public static final String ALIM_CMP_CLASSE = "classe";
    public static final String ALIM_CMP_KCAL = "kcal";
    public static final String ALIM_CMP_GRASSI = "grassi";
    public static final String ALIM_CMP_PROTEINE = "proteine";
    public static final String ALIM_CMP_GLUCIDI = "glucidi";
    public static final String ALIM_CMP_FIBRE = "fibre";
    public static final String ALIM_CMP_SODIO = "sodio";
    public static final String ALIM_CMP_POTASSIO = "potassio";
    public static final String ALIM_CMP_CALCIO = "calcio";
    public static final String ALIM_CMP_FERRO = "ferro";
    public static final String ALIM_CMP_VITA = "vitaminaa";
    public static final String ALIM_CMP_VITB = "vitaminab";
    public static final String ALIM_CMP_VITC = "vitaminac";
    public static final String ALIM_CMP_VITD = "vitaminad";
    public static final String ALIM_CMP_VITE = "vitaminae";
    public static final String ALIM_CMP_CAMPO1 = "riserva1";
    public static final String ALIM_CMP_CAMPO2 = "riserva2";

    public static final String DIETA_TBL_NAME = "Dieta";
    public static final String DIETA_CMP_ID="id";
    public static final String DIETA_CMP_IDALIM = "idalim";
    public static final String DIETA_CMP_QTA = "qta";
    public static final String DIETA_CMP_COLPRACEN = "colpracen";
    public static final String DIETA_CMP_GIORNO = "giorno";
    public static final String DIETA_CMP_CAMPO1 = "riserva1";
    public static final String DIETA_CMP_CAMPO2 = "riserva2";

    public static final String PASTI_TBL_NAME = "Pasti";
    public static final String PASTI_CMP_ID="id";
    public static final String PASTI_CMP_NOME = "nome";

    public static final String VOCIPASTI_TBL_NAME = "VociPasti";
    public static final String VOCIPASTI_CMP_ID="id";
    public static final String VOCIPASTI_CMP_IDPASTO="idpasto";
    public static final String VOCIPASTI_CMP_IDALIM = "idalim";
    public static final String VOCIPASTI_CMP_QTA = "qta";
    public static final String VOCIPASTI_CMP_CAMPO1 = "riserva1";
    public static final String VOCIPASTI_CMP_CAMPO2 = "riserva2";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context,DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("create table " +ALIMENTI_TABLE_NAME+" "+"(" +
                        ALIM_CMP_ID+" integer primary key, " +
                        ALIM_CMP_NOME+" text, " +
                        ALIM_CMP_CLASSE+" text, " +
                        ALIM_CMP_KCAL+ " double, " +
                        ALIM_CMP_GRASSI+" double, " +
                        ALIM_CMP_PROTEINE+" double, " +
                        ALIM_CMP_GLUCIDI+" double, " +
                        ALIM_CMP_FIBRE+" double, " +
                        ALIM_CMP_SODIO+" double, " +
                        ALIM_CMP_POTASSIO+" double, " +
                        ALIM_CMP_CALCIO+" double, " +
                        ALIM_CMP_FERRO+" double, " +
                        ALIM_CMP_VITA+" double, " +
                        ALIM_CMP_VITB+" double, " +
                        ALIM_CMP_VITC+" double, " +
                        ALIM_CMP_VITD+" double, " +
                        ALIM_CMP_VITE+" double, " +
                        ALIM_CMP_CAMPO1+" text, "+
                        ALIM_CMP_CAMPO2+" text)");
        db.execSQL("create table " +DIETA_TBL_NAME+" "+"(" +
                DIETA_CMP_ID+" integer primary key, " +
                DIETA_CMP_IDALIM+" integer, " +
                DIETA_CMP_QTA+" double, "+
                DIETA_CMP_COLPRACEN+" integer, " + //1-Colazione 2-Pranzo 3-Cena
                DIETA_CMP_GIORNO+" text, " +
                DIETA_CMP_CAMPO1+" text, "+
                DIETA_CMP_CAMPO2+" text)");
        db.execSQL("create table " +PASTI_TBL_NAME+" "+"(" +
                PASTI_CMP_ID+" integer primary key, " +
                PASTI_CMP_NOME+" text)");

        db.execSQL("create table " +VOCIPASTI_TBL_NAME+" "+"(" +
                VOCIPASTI_CMP_ID+" integer primary key, " +
                VOCIPASTI_CMP_IDPASTO+" integer, " +
                VOCIPASTI_CMP_IDALIM+" integer, " +
                VOCIPASTI_CMP_QTA+" double, "+
                VOCIPASTI_CMP_CAMPO1+" text, "+
                VOCIPASTI_CMP_CAMPO2+" text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+ALIMENTI_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+DIETA_TBL_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+PASTI_TBL_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+VOCIPASTI_TBL_NAME);
        onCreate(db);
    }

    public ArrayList<String> getAllalimenti() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ALIMENTI_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(ALIM_CMP_NOME)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<Integer> getAllID() {
        ArrayList<Integer> array_list = new ArrayList<Integer>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ALIMENTI_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getInt(res.getColumnIndex(ALIM_CMP_ID)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<String> getAllalimentiDaCerca(String DaTrovare) {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ALIMENTI_TABLE_NAME+" where "+ALIM_CMP_NOME+" LIKE '%"+DaTrovare+"%'", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(ALIM_CMP_NOME)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<Integer> getAllalimentiIDdaCerca(String DaTrovare) {
        ArrayList<Integer> array_list = new ArrayList<Integer>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ALIMENTI_TABLE_NAME+" where "+ALIM_CMP_NOME+" LIKE "+"'%"+DaTrovare+"%'", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getInt(res.getColumnIndex(ALIM_CMP_ID)));
            res.moveToNext();
        }
        return array_list;
    }
    public Cursor getAlimento(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ALIMENTI_TABLE_NAME+" where "+ALIM_CMP_ID+"="+id+"", null );
        return res;
    }
    public Integer deleteAlimento (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ALIMENTI_TABLE_NAME,
                ALIM_CMP_ID+" = ? ",
                new String[] { Integer.toString(id) });
    }
    public boolean insertAlimento  (String Nome,String Classe, Double Kcal, Double Grassi, Double Proteine,Double Glucidi,
                                    Double Fibre, Double Sodio, Double Potassio, Double Calcio, Double Ferro,
                                    Double VitA,Double VitB,Double VitC,Double VitD,Double VitE, String Campo1, String Campo2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ALIM_CMP_NOME, Nome);
        contentValues.put(ALIM_CMP_CLASSE, Classe);
        contentValues.put(ALIM_CMP_KCAL, Kcal);
        contentValues.put(ALIM_CMP_GRASSI, Grassi);
        contentValues.put(ALIM_CMP_PROTEINE, Proteine);
        contentValues.put(ALIM_CMP_GLUCIDI, Glucidi);
        contentValues.put(ALIM_CMP_FIBRE, Fibre);
        contentValues.put(ALIM_CMP_SODIO, Sodio);
        contentValues.put(ALIM_CMP_POTASSIO, Potassio);
        contentValues.put(ALIM_CMP_CALCIO, Calcio);
        contentValues.put(ALIM_CMP_FERRO, Ferro);
        contentValues.put(ALIM_CMP_VITA, VitA);
        contentValues.put(ALIM_CMP_VITB, VitB);
        contentValues.put(ALIM_CMP_VITC, VitC);
        contentValues.put(ALIM_CMP_VITD, VitD);
        contentValues.put(ALIM_CMP_VITE, VitE);
        contentValues.put(ALIM_CMP_CAMPO1, Campo1);
        contentValues.put(ALIM_CMP_CAMPO2, Campo2);
        long nuovoid=db.insert(ALIMENTI_TABLE_NAME, null, contentValues);
        return true;
    }
    public boolean UpdateAlimento  (Integer id, String Nome,String Classe, Double Kcal, Double Grassi, Double Proteine,Double Glucidi,
                                    Double Fibre, Double Sodio, Double Potassio, Double Calcio, Double Ferro,
                                    Double VitA,Double VitB,Double VitC,Double VitD,Double VitE, String Campo1, String Campo2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ALIM_CMP_NOME, Nome);
        contentValues.put(ALIM_CMP_CLASSE, Classe);
        contentValues.put(ALIM_CMP_KCAL, Kcal);
        contentValues.put(ALIM_CMP_GRASSI, Grassi);
        contentValues.put(ALIM_CMP_PROTEINE, Proteine);
        contentValues.put(ALIM_CMP_GLUCIDI, Glucidi);
        contentValues.put(ALIM_CMP_FIBRE, Fibre);
        contentValues.put(ALIM_CMP_SODIO, Sodio);
        contentValues.put(ALIM_CMP_POTASSIO, Potassio);
        contentValues.put(ALIM_CMP_CALCIO, Calcio);
        contentValues.put(ALIM_CMP_FERRO, Ferro);
        contentValues.put(ALIM_CMP_VITA, VitA);
        contentValues.put(ALIM_CMP_VITB, VitB);
        contentValues.put(ALIM_CMP_VITC, VitC);
        contentValues.put(ALIM_CMP_VITD, VitD);
        contentValues.put(ALIM_CMP_VITE, VitE);
        contentValues.put(ALIM_CMP_CAMPO1, Campo1);
        contentValues.put(ALIM_CMP_CAMPO2, Campo2);
        db.update(ALIMENTI_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public ArrayList<String> getAllPasti() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+PASTI_TBL_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(PASTI_CMP_NOME)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<Integer> getAllIDPasti() {
        ArrayList<Integer> array_list = new ArrayList<Integer>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+PASTI_TBL_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getInt(res.getColumnIndex(PASTI_CMP_ID)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<String> getAllPastiDaCerca(String DaTrovare) {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+PASTI_TBL_NAME+" where "+PASTI_CMP_NOME+" LIKE '%"+DaTrovare+"%'", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(PASTI_CMP_NOME)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<Integer> getAllPastiIDdaCerca(String DaTrovare) {
        ArrayList<Integer> array_list = new ArrayList<Integer>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+PASTI_TBL_NAME+" where "+PASTI_CMP_NOME+" LIKE "+"'%"+DaTrovare+"%'", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getInt(res.getColumnIndex(PASTI_CMP_ID)));
            res.moveToNext();
        }
        return array_list;
    }
    public Cursor getPasto(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+PASTI_TBL_NAME+" where "+PASTI_CMP_ID+"="+id+"", null );
        return res;
    }
    public Cursor getVociPasto(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+VOCIPASTI_TBL_NAME+" where "+VOCIPASTI_CMP_IDPASTO+"="+id+"", null );
        return res;
    }
    public Integer deletePasto (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(VOCIPASTI_TBL_NAME,
                VOCIPASTI_CMP_IDPASTO+" = ? ",
                new String[] { Integer.toString(id) });
        return db.delete(PASTI_TBL_NAME,
                PASTI_CMP_ID+" = ? ",
                new String[] { Integer.toString(id) });
    }
    public Integer insertPasto  ( String nome) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PASTI_CMP_NOME, nome);
        Integer nuovoid=(int)db.insert(PASTI_TBL_NAME, null, contentValues);
        return nuovoid;
    }
    public boolean UpdatePasto  (Integer id,String nome) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PASTI_CMP_NOME, nome);
        db.update(PASTI_TBL_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public boolean insertVociPasto  ( Integer idpasto,Integer idalim,Double qta, String campo1, String campo2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VOCIPASTI_CMP_IDPASTO, idpasto);
        contentValues.put(VOCIPASTI_CMP_IDALIM, idalim);
        contentValues.put(VOCIPASTI_CMP_QTA, qta);
        contentValues.put(VOCIPASTI_CMP_CAMPO1, campo1);
        contentValues.put(VOCIPASTI_CMP_CAMPO2, campo2);
        long nuovoid=db.insert(VOCIPASTI_TBL_NAME, null, contentValues);
        return true;
    }
    public boolean UpdateVociPasto  (Integer id,Integer idpasto,Integer idalim,Double qta, String campo1, String campo2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VOCIPASTI_CMP_IDPASTO, idpasto);
        contentValues.put(VOCIPASTI_CMP_IDALIM, idalim);
        contentValues.put(VOCIPASTI_CMP_QTA, qta);
        contentValues.put(VOCIPASTI_CMP_CAMPO1, campo1);
        contentValues.put(VOCIPASTI_CMP_CAMPO2, campo2);
        db.update(VOCIPASTI_TBL_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public boolean UpdateQtaPasto  (Integer id, Double qta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(VOCIPASTI_CMP_QTA, qta);

        db.update(VOCIPASTI_TBL_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public Integer deleteVocePasto (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(VOCIPASTI_TBL_NAME,
                VOCIPASTI_CMP_ID+" = ? ",
                new String[] { Integer.toString(id) });
    }
    public Cursor getAllColaz(String Giorno) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+DIETA_TBL_NAME+" where "+DIETA_CMP_GIORNO+"='"+Giorno+"' and "+DIETA_CMP_COLPRACEN+"=1", null );
        return res;
    }
    public Cursor getAllPranzo(String Giorno) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+DIETA_TBL_NAME+" where "+DIETA_CMP_GIORNO+"='"+Giorno+"' and "+DIETA_CMP_COLPRACEN+"=2", null );
        return res;
    }public Cursor getAllCena(String Giorno) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+DIETA_TBL_NAME+" where "+DIETA_CMP_GIORNO+"='"+Giorno+"' and "+DIETA_CMP_COLPRACEN+"=3", null );
        return res;
    }
    public Cursor getAllGiorno(String Giorno) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+DIETA_TBL_NAME+" where "+DIETA_CMP_GIORNO+"='"+Giorno+"'", null );
        return res;
    }
    public boolean insertDieta  (Integer idalim, Double qta, Integer cpc, String giorno, String Campo1, String Campo2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DIETA_CMP_IDALIM, idalim);
        contentValues.put(DIETA_CMP_QTA, qta);
        contentValues.put(DIETA_CMP_COLPRACEN, cpc);
        contentValues.put(DIETA_CMP_GIORNO, giorno);
        contentValues.put(DIETA_CMP_CAMPO1, Campo1);
        contentValues.put(DIETA_CMP_CAMPO2, Campo2);
        long nuovoid=db.insert(DIETA_TBL_NAME, null, contentValues);
        return true;
    }
    public boolean UpdateDieta  (Integer id,Integer idalim, Double qta, Integer cpc, String giorno, String Campo1, String Campo2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DIETA_CMP_IDALIM, idalim);
        contentValues.put(DIETA_CMP_QTA, qta);
        contentValues.put(DIETA_CMP_COLPRACEN, cpc);
        contentValues.put(DIETA_CMP_GIORNO, giorno);
        contentValues.put(DIETA_CMP_CAMPO1, Campo1);
        contentValues.put(DIETA_CMP_CAMPO2, Campo2);
        db.update(DIETA_TBL_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public boolean UpdateQtaDieta  (Integer id, Double qta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DIETA_CMP_QTA, qta);

        db.update(DIETA_TBL_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public Integer deleteDieta (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DIETA_TBL_NAME,
                DIETA_CMP_ID+" = ? ",
                new String[] { Integer.toString(id) });
    }
}
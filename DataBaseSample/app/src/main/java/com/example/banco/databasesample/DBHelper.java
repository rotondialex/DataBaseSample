package com.example.banco.databasesample;
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

    public static String nomeDB;
    public static final String DATABASE_NAME = "Hichem.db";
    public static final String CONTACTS_TABLE_NAME = "Fornitori";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "nome";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_STREET = "indirizzo";
    public static final String CONTACTS_COLUMN_AGENT = "agente";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    public static final String MP_TABLE_NAME="Materieprime";
    public static final String MP_COL_ID="idmp";
    public static final String MP_COL_NOME="nomemp";
    public static final String MP_COL_PREZZO="prezzomp";
    public static final String MP_COL_FORN="fornmp";
    public static final String MP_COL_ALTRIFORN="altrifornmp";
    public static final String MP_COL_ALTRIPREZZI="altriprezzimp";
    public static final String MP_COL_QUANTITA="quantmp";
    public static final String MP_COL_LIVELLOMIN="livellominmp";
    public static final String MP_COL_ULTIMAMOD="ultimamodifica";
    public static final String MP_COL_CAS="cas";
    public static final String MP_COL_CAMPO1="campo1";
    public static final String MP_COL_CAMPO2="campo2";
    public static final String IMB_TABLE_NAME="Imballaggi";
    public static final String IMB_COL_ID="idimb";
    public static final String IMB_COL_NOME="nomeimb";
    public static final String IMB_COL_PREZZO="prezzoimb";
    public static final String IMB_COL_QUANTITA="quantimb";
    public static final String IMB_COL_FORN="fornimb";
    public static final String IMB_COL_ULTIMAMOD="ultimamodifica";
    public static final String IMB_COL_CAMPO1="campo1";
    public static final String IMB_COL_CAMPO2="campo2";

    public static final String FORM_TABLE_NAME="Formule";
    public static final String FORM_COL_ID="idfrm";
    public static final String FORM_COL_NAME="nomefrm";
    public static final String FORM_COL_INV="inventsino";
    public static final String FORM_COL_ULTIMAMOD="ultimamodifica";
    public static final String FORM_COL_CAMPO1="campo1";
    public static final String FORM_COL_CAMPO2="campo2";


    public static final String COMP_TABLE_NAME="Componenti";
    public static final String COMP_COL_IDFRM="idfrm";
    public static final String COMP_COL_IDMPR="idmpr";
    public static final String COMP_COL_PERCENT="percent";
    public static final String COMP_COL_CAMPO1="campo1";
    public static final String COMP_COL_CAMPO2="campo2";

    public static final String CONF_TABLE_NAME="Confezioni";
    public static final String CONF_COL_ID="idconf";
    public static final String CONF_COL_NAME="nomeconf";
    public static final String CONF_COL_QPLT="qplt";
    public static final String CONF_COL_QCT="qct";
    public static final String CONF_COL_KGPZ="kgpz";
    public static final String CONF_COL_CAMPO1="campo1";
    public static final String CONF_COL_CAMPO2="campo2";

    public static final String IMBINCONF_TABLE_NAME="ImballaggiInConf";
    public static final String IMBINCONF_COL_IDCONF="idconf";
    public static final String IMBINCONF_COL_IDIMB="idimb";
    public static final String IMBINCONF_COL_QIDIMB="qidimb";

    public static final String CONFDIFRM_TABLE_NAME="ConfezioniFormule";
    public static final String CONFDIFRM_COL_IDFRM="idfrm";
    public static final String CONFDIFRM_COL_IDCONF="idconf";

    public static final String CONFDIFRM2_TABLE_NAME="Altrifornitori";
    public static final String CONFDIFRM2_COL_IDFRM="idmpr";
    public static final String CONFDIFRM2_COL_IDCONF="idforn";
    public static final String CONFDIFRM2_COL_QUANT="prezzo";
    public static final String CONFDIFRM2_COL_ULTIMAMOD="ultimamodifica";

    private HashMap hp;

    public DBHelper(Context context, String nome) {
        super(context,nome , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table Fornitori " +
                        "(id integer primary key, nome text,phone text,email text, indirizzo text, agente text)"
        );
        db.execSQL(
                "create table Materieprime " +
                        "(idmp integer primary key, nomemp text,prezzomp double,fornmp integer, quantmp double, altrifornmp text, altriprezzimp text, livellominmp double," +
                        "ultimamodifica text, cas text, campo1 text, campo2 text)"
        );
        db.execSQL(
                "create table Imballaggi " +
                        "(idimb integer primary key, nomeimb text,prezzoimb double,fornimb integer, quantimb double, ultimamodifica text, campo1 text, campo2 text)"
        );
        db.execSQL(
                "create table "+FORM_TABLE_NAME+" " + "("+
                        FORM_COL_ID+" integer primary key, "+
                        FORM_COL_NAME+" text, "+
                        FORM_COL_INV+" text, "+
                        FORM_COL_ULTIMAMOD+" text, "+
                        FORM_COL_CAMPO1+" text, "+
                        FORM_COL_CAMPO2+" text)"
        );
        db.execSQL(
                "create table "+COMP_TABLE_NAME+" " + "("+
                        COMP_COL_IDFRM+" integer, "+
                        COMP_COL_IDMPR+" integer, "+
                        COMP_COL_PERCENT+" double, "+
                        COMP_COL_CAMPO1+" text, "+
                        COMP_COL_CAMPO2+" text)"
        );
        db.execSQL(
                "create table "+CONF_TABLE_NAME+" " + "("+
                        CONF_COL_ID+" integer primary key, "+
                        CONF_COL_NAME+" text, "+
                        CONF_COL_QPLT+" integer, "+
                        CONF_COL_QCT+" integer, "+
                        CONF_COL_KGPZ+" double, "+
                        CONF_COL_CAMPO1+" text, "+
                        CONF_COL_CAMPO2+" text)"
        );
        db.execSQL(
                "create table "+IMBINCONF_TABLE_NAME+" " + "("+
                        IMBINCONF_COL_IDCONF+" integer , "+
                        IMBINCONF_COL_IDIMB+" integer, "+
                        IMBINCONF_COL_QIDIMB+" integer)"
        );
        db.execSQL(
                "create table "+CONFDIFRM_TABLE_NAME+" " + "("+
                        CONFDIFRM_COL_IDFRM+" integer , "+
                        CONFDIFRM_COL_IDCONF+" integer)"
        );
        db.execSQL(
                "create table "+CONFDIFRM2_TABLE_NAME+" " + "("+
                        CONFDIFRM2_COL_IDFRM+" integer , "+
                        CONFDIFRM2_COL_IDCONF+" integer, "+
                        CONFDIFRM2_COL_QUANT+" double, "+
                        CONFDIFRM2_COL_ULTIMAMOD+" text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS Fornitori");
        db.execSQL("DROP TABLE IF EXISTS Materieprime");
        db.execSQL("DROP TABLE IF EXISTS Imballaggi");
        db.execSQL("DROP TABLE IF EXISTS "+FORM_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+COMP_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+CONF_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+IMBINCONF_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+CONFDIFRM_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+CONFDIFRM2_TABLE_NAME);
        onCreate(db);
    }
    public boolean insertComponente(Integer idfrm, Integer idmpr, Double percent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMP_COL_IDFRM, idfrm);
        contentValues.put(COMP_COL_IDMPR, idmpr);
        contentValues.put(COMP_COL_PERCENT, percent);
        contentValues.put(COMP_COL_CAMPO1, " ");
        contentValues.put(COMP_COL_CAMPO2, " ");
        db.insert(COMP_TABLE_NAME, null, contentValues);
        return true;
    }
    public boolean insertConfInFormula(Integer idfrm, Integer idmpr, Double percent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONFDIFRM2_COL_IDFRM, idfrm);
        contentValues.put(CONFDIFRM2_COL_IDCONF, idmpr);
        contentValues.put(CONFDIFRM2_COL_QUANT, percent);
        Calendar today= Calendar.getInstance();
        String oggi= DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY).format(today.getTime());
        contentValues.put(CONFDIFRM2_COL_ULTIMAMOD, oggi);
        db.insert(CONFDIFRM2_TABLE_NAME, null, contentValues);
        return true;
    }
    public boolean insertComponenteIMB(Integer idconf, Integer idimb, Double percent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IMBINCONF_COL_IDCONF, idconf);
        contentValues.put(IMBINCONF_COL_IDIMB, idimb);
        contentValues.put(IMBINCONF_COL_QIDIMB, percent);
        db.insert(IMBINCONF_TABLE_NAME, null, contentValues);
        return true;
    }
    public String getDbPath(){
        String percorso;
        SQLiteDatabase db = this.getWritableDatabase();
        percorso=db.getPath();
        return percorso;
    }
    public Integer insertFormula(String name, String inventario, Bundle componenti) {
        Integer newintid;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FORM_COL_NAME, name);
        contentValues.put(FORM_COL_INV, inventario);
        Calendar today= Calendar.getInstance();
        String oggi= DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY).format(today.getTime());
        contentValues.put(FORM_COL_ULTIMAMOD, oggi);
        contentValues.put(FORM_COL_CAMPO1, " ");
        contentValues.put(FORM_COL_CAMPO2, " ");
        Long newid= db.insert(FORM_TABLE_NAME, null, contentValues);
        newintid=newid.intValue();
        return newintid;
    }

    public Integer insertConf(String name, String pezzi, String litri, Bundle componenti) {
        Integer newintid;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONF_COL_NAME, name);
        contentValues.put(CONF_COL_QCT,pezzi);
        contentValues.put(CONF_COL_QPLT,litri);
        Calendar today= Calendar.getInstance();
        String oggi= DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY).format(today.getTime());
        contentValues.put(CONF_COL_CAMPO1, oggi);
        contentValues.put(CONF_COL_CAMPO2, " ");
        Long newid= db.insert(CONF_TABLE_NAME, null, contentValues);
        newintid=newid.intValue();
        return newintid;
    }
    public boolean insertMateriaPrima(String name, Double prezzo, Double quant, Integer forn,String altriforn,String altriprezzi,Double livellominmp, String cas) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MP_COL_NOME, name);
        contentValues.put(MP_COL_PREZZO, prezzo);
        contentValues.put(MP_COL_QUANTITA, quant);
        contentValues.put(MP_COL_FORN, forn);
        contentValues.put(MP_COL_ALTRIFORN, altriforn);
        contentValues.put(MP_COL_ALTRIPREZZI, altriprezzi);
        contentValues.put(MP_COL_LIVELLOMIN, livellominmp);
        Calendar today= Calendar.getInstance();
        String oggi= DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY).format(today.getTime());
        contentValues.put(MP_COL_ULTIMAMOD, oggi);
        contentValues.put(MP_COL_CAS, cas);
        contentValues.put(MP_COL_CAMPO1, " ");
        contentValues.put(MP_COL_CAMPO2, " ");
        db.insert(MP_TABLE_NAME, null, contentValues);
        return true;
    }
    public boolean insertImballaggio(String name, Double prezzo, Double quant, Integer forn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IMB_COL_NOME, name);
        contentValues.put(IMB_COL_PREZZO, prezzo);
        contentValues.put(IMB_COL_QUANTITA, quant);
        contentValues.put(IMB_COL_FORN, forn);
        Calendar today= Calendar.getInstance();
        String oggi= DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY).format(today.getTime());
        contentValues.put(IMB_COL_ULTIMAMOD, oggi);
        contentValues.put(IMB_COL_CAMPO1, " ");
        contentValues.put(IMB_COL_CAMPO2, " ");
        db.insert(IMB_TABLE_NAME, null, contentValues);
        return true;
    }
    public boolean insertContact  (String name, String phone, String email, String street,String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_NAME, name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("indirizzo", street);
        contentValues.put("agente", place);
        db.insert("Fornitori", null, contentValues);
        return true;
    }
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Fornitori where id="+id+"", null );
        return res;
    }
    public Cursor getMateriaprima(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Materieprime where idmp="+id+"", null );
        return res;
    }
    public Cursor getImballaggio(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Imballaggi where idimb="+id+"", null );
        return res;
    }

    public Cursor getFormula(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Formule where "+FORM_COL_ID+"="+id+"", null );
        return res;
    }
    public Cursor getConfezione(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CONF_TABLE_NAME+" where idconf="+id+"", null );
        return res;
    }
    public Boolean cegiaComp(int idfrm, int idmpr) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+COMP_TABLE_NAME+" where "+COMP_COL_IDFRM+"="+idfrm+" and "+COMP_COL_IDMPR+"="+idmpr, null );
        res.moveToFirst();
        if (!res.isAfterLast()) {
            return true;
        } else{
            return false;
        }
    }
    public Boolean cegiaConfInFormula(int idfrm, int idmpr) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CONFDIFRM2_TABLE_NAME+" where "+CONFDIFRM2_COL_IDFRM+"="+idfrm+" and "+CONFDIFRM2_COL_IDCONF+"="+idmpr, null );
        res.moveToFirst();
        if (!res.isAfterLast()) {
            return true;
        } else{
            return false;
        }
    }
    public Boolean cegiaCompIMB(int idconf, int idimb) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+IMBINCONF_TABLE_NAME+" where "+IMBINCONF_COL_IDCONF+"="+idconf+" and "+IMBINCONF_COL_IDIMB+"="+idimb, null );
        res.moveToFirst();
        if (!res.isAfterLast()) {
            return true;
        } else{
            return false;
        }
    }

    public ArrayList<String> getCompFormula(int id) {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+COMP_TABLE_NAME+" where "+COMP_COL_IDFRM+"="+id+"", null );
        res.moveToFirst();


        while(res.isAfterLast() == false){
            Integer mpr=Integer.parseInt(res.getString(res.getColumnIndex(COMP_COL_IDMPR)));
            Cursor res2 =  db.rawQuery( "select * from "+MP_TABLE_NAME+" where "+MP_COL_ID+"="+mpr+"", null );
            res2.moveToFirst();
            if (res2.isAfterLast() == false) {
                array_list.add(res2.getString(res2.getColumnIndex(MP_COL_NOME)));
                array_list.add(res.getString(res.getColumnIndex(COMP_COL_PERCENT)));
            }
            res2.close();
            res.moveToNext();
        }
        return array_list;
    }
    public Boolean duplicaFormula(int id,int idNew) {


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+COMP_TABLE_NAME+" where "+COMP_COL_IDFRM+"="+id+"", null );
        res.moveToFirst();


        while(res.isAfterLast() == false){
            Integer mpr=Integer.parseInt(res.getString(res.getColumnIndex(COMP_COL_IDMPR)));
            Cursor res2 =  db.rawQuery( "select * from "+MP_TABLE_NAME+" where "+MP_COL_ID+"="+mpr+"", null );
            res2.moveToFirst();
            if (res2.isAfterLast() == false) {
                SQLiteDatabase db2 = this.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(COMP_COL_IDFRM, idNew);
                contentValues.put(COMP_COL_IDMPR, mpr);
                contentValues.put(COMP_COL_PERCENT, res.getString(res.getColumnIndex(COMP_COL_PERCENT)));
                contentValues.put(COMP_COL_CAMPO1, " ");
                contentValues.put(COMP_COL_CAMPO2, " ");
                db2.insert(COMP_TABLE_NAME, null, contentValues);
            }
            res2.close();
            res.moveToNext();
        }
        return true;
    }
    public ArrayList<String> getCONFdiFormula(int id) {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CONFDIFRM2_TABLE_NAME+" where "+CONFDIFRM2_COL_IDFRM+"="+id+"", null );
        res.moveToFirst();


        while(res.isAfterLast() == false){
            Integer mpr=Integer.parseInt(res.getString(res.getColumnIndex(CONFDIFRM2_COL_IDCONF)));
            Cursor res2 =  db.rawQuery( "select * from "+CONF_TABLE_NAME+" where "+CONF_COL_ID+"="+mpr+"", null );
            res2.moveToFirst();
            array_list.add(res2.getString(res2.getColumnIndex(CONF_COL_NAME)));
            res2.close();
            array_list.add(res.getString(res.getColumnIndex(CONFDIFRM2_COL_QUANT)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<String> getCONFdiFormulaINV(int id) {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CONFDIFRM2_TABLE_NAME+" where "+CONFDIFRM2_COL_IDFRM+"="+id+"", null );
        res.moveToFirst();


        while(res.isAfterLast() == false){
            Integer mpr=Integer.parseInt(res.getString(res.getColumnIndex(CONFDIFRM2_COL_IDCONF)));
            Cursor res2 =  db.rawQuery( "select * from "+CONF_TABLE_NAME+" where "+CONF_COL_ID+"="+mpr+"", null );
            res2.moveToFirst();
            array_list.add(res2.getString(res2.getColumnIndex(CONF_COL_NAME)));
            res2.close();
            array_list.add(res.getString(res.getColumnIndex(CONFDIFRM2_COL_QUANT)));
            res.moveToNext();
            array_list.add(mpr.toString()); //id conf
        }
        return array_list;
    }
    public ArrayList<String> getCompConf(int id) {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+IMBINCONF_TABLE_NAME+" where "+IMBINCONF_COL_IDCONF+"="+id+"", null );
        res.moveToFirst();


        while(res.isAfterLast() == false){
            Integer imb=Integer.parseInt(res.getString(res.getColumnIndex(IMBINCONF_COL_IDIMB)));
            Cursor res2 =  db.rawQuery( "select * from "+IMB_TABLE_NAME+" where "+IMB_COL_ID+"="+imb+"", null );
            res2.moveToFirst();
            array_list.add(res2.getString(res2.getColumnIndex(IMB_COL_NOME)));
            res2.close();
            array_list.add(res.getString(res.getColumnIndex(IMBINCONF_COL_QIDIMB)));
            res.moveToNext();
        }
        return array_list;
    }


    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }
    public boolean updateFormula (Integer id,String name,String inventario, Bundle Componenti) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FORM_COL_NAME, name);
        contentValues.put(FORM_COL_INV, inventario);
        Calendar today= Calendar.getInstance();
        String oggi= DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY).format(today.getTime());
        contentValues.put(FORM_COL_ULTIMAMOD, oggi);
        contentValues.put(FORM_COL_CAMPO1, " ");
        contentValues.put(FORM_COL_CAMPO2, " ");
        db.update(FORM_TABLE_NAME, contentValues, "idfrm = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public boolean updateComponente(Integer idfrm, Integer idmpr, Double percent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMP_COL_PERCENT, percent);
        contentValues.put(COMP_COL_CAMPO1, " ");
        contentValues.put(COMP_COL_CAMPO2, " ");
        db.update(COMP_TABLE_NAME, contentValues,"idfrm = ? AND idmpr= ?", new String[] { Integer.toString(idfrm),Integer.toString(idmpr) });
        return true;
    }

    public boolean UpdateConfInFormula(Integer idfrm, Integer idmpr, Double percent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONFDIFRM2_COL_QUANT, percent);
        Calendar today= Calendar.getInstance();
        String oggi= DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY).format(today.getTime());
        contentValues.put(CONFDIFRM2_COL_ULTIMAMOD, oggi);
        db.update(CONFDIFRM2_TABLE_NAME, contentValues,CONFDIFRM2_COL_IDFRM+" = ? AND "+CONFDIFRM2_COL_IDCONF+"= ?", new String[] { Integer.toString(idfrm),Integer.toString(idmpr) });
        return true;
    }
    public boolean updateConfezione (Integer id,String name,String pezzi,String litri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONF_COL_NAME, name);
        contentValues.put(CONF_COL_QCT, pezzi);
        contentValues.put(CONF_COL_QPLT, litri);
        Calendar today= Calendar.getInstance();
        String oggi= DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY).format(today.getTime());
        contentValues.put(CONF_COL_CAMPO1, oggi);
        contentValues.put(CONF_COL_CAMPO2, " ");
        db.update(CONF_TABLE_NAME, contentValues, "idconf = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public boolean updateMateriaprima (Integer id,String name, Double prezzo, Double quant, Integer forn,String altriforn,String altriprezzi,Double livellominmp, String cas) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MP_COL_NOME, name);
        contentValues.put(MP_COL_PREZZO, prezzo);
        contentValues.put(MP_COL_QUANTITA, quant);
        contentValues.put(MP_COL_FORN, forn);
        contentValues.put(MP_COL_ALTRIFORN, altriforn);
        contentValues.put(MP_COL_ALTRIPREZZI, altriprezzi);
        contentValues.put(MP_COL_LIVELLOMIN, livellominmp);
        Calendar today= Calendar.getInstance();
        String oggi= DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY).format(today.getTime());
        contentValues.put(MP_COL_ULTIMAMOD, oggi);
        contentValues.put(MP_COL_CAS, cas);
        contentValues.put(MP_COL_CAMPO1, " ");
        contentValues.put(MP_COL_CAMPO2, " ");
        db.update(MP_TABLE_NAME, contentValues, "idmp = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public boolean updateImballaggio (Integer id,String name, Double prezzo, Double quant, Integer forn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IMB_COL_NOME, name);
        contentValues.put(IMB_COL_PREZZO, prezzo);
        contentValues.put(IMB_COL_QUANTITA, quant);
        contentValues.put(IMB_COL_FORN, forn);
        Calendar today= Calendar.getInstance();
        String oggi= DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY).format(today.getTime());
        contentValues.put(IMB_COL_ULTIMAMOD, oggi);
        contentValues.put(IMB_COL_CAMPO1, " ");
        contentValues.put(IMB_COL_CAMPO2, " ");
        db.update(IMB_TABLE_NAME, contentValues, "idimb = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_NAME, name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("indirizzo", street);
        contentValues.put("agente", place);
        db.update("Fornitori", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public Integer deleteMateriaprima (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(MP_TABLE_NAME,
                "idmp = ? ",
                new String[] { Integer.toString(id) });
    }
    public Integer deleteImballaggio (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(IMB_TABLE_NAME,
                "idimb = ? ",
                new String[] { Integer.toString(id) });
    }
    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Fornitori",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
    public Integer deleteFormula (Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+COMP_TABLE_NAME+ " where "+COMP_COL_IDFRM+"="+id+"", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){

            db.delete(COMP_TABLE_NAME,
                COMP_COL_IDFRM+" = ? and "+COMP_COL_IDMPR+" = ? ",
                new String[] { Integer.toString(id),res.getString(res.getColumnIndex(COMP_COL_IDMPR)) });
            res.moveToNext();
        }
        if (!res.isClosed()) res.close();
        return db.delete("Formule",
                "idfrm = ? ",
                new String[] { Integer.toString(id) });
    }
    public Integer deleteConfezione (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Confezioni",
                "idconf = ? ",
                new String[] { Integer.toString(id) });
    }
    public Integer deleteComponente (Integer idfrm, Integer idmpr) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(COMP_TABLE_NAME,
                COMP_COL_IDFRM+" = ? and "+COMP_COL_IDMPR+" = ? ",
                new String[] { Integer.toString(idfrm),Integer.toString(idmpr) });
    }
    public Integer deleteConfdiFRM (Integer idfrm, Integer idmpr) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CONFDIFRM2_TABLE_NAME,
                CONFDIFRM2_COL_IDFRM+" = ? and "+CONFDIFRM2_COL_IDCONF+" = ? ",
                new String[] { Integer.toString(idfrm),Integer.toString(idmpr) });
    }
    public Integer deleteComponenteConf (Integer idconf, Integer idimb) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(IMBINCONF_TABLE_NAME,
                IMBINCONF_COL_IDCONF+" = ? and "+IMBINCONF_COL_IDIMB+" = ? ",
                new String[] { Integer.toString(idconf),Integer.toString(idimb) });
    }
    public ArrayList<String> getAllMaterieprime() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Materieprime", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(MP_COL_NOME)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getAllMaterieprimeForn(int id) {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Materieprime where fornmp="+id+"", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(MP_COL_NOME)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Integer> getAllMPID() {
        ArrayList<Integer> array_list = new ArrayList<Integer>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Materieprime", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getInt(res.getColumnIndex(MP_COL_ID)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<String> getAllMaterieprimeDaCerca(String DaTrovare) {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Materieprime where "+MP_COL_NOME+" LIKE '%"+DaTrovare+"%'", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(MP_COL_NOME)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<Integer> getAllMPIDdaCerca(String DaTrovare) {
        ArrayList<Integer> array_list = new ArrayList<Integer>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Materieprime where "+MP_COL_NOME+" LIKE "+"'%"+DaTrovare+"%'", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getInt(res.getColumnIndex(MP_COL_ID)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<String> getAllIMBDaCerca(String DaTrovare) {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Imballaggi where "+IMB_COL_NOME+" LIKE '%"+DaTrovare+"%'", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(IMB_COL_NOME)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<Integer> getAllIMBIDdaCerca(String DaTrovare) {
        ArrayList<Integer> array_list = new ArrayList<Integer>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Imballaggi where "+IMB_COL_NOME+" LIKE "+"'%"+DaTrovare+"%'", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getInt(res.getColumnIndex(IMB_COL_ID)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<String> getAllContactsDaCerca(String DaTrovare) {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Fornitori where "+CONTACTS_COLUMN_NAME+" LIKE '%"+DaTrovare+"%'", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<Integer> getAllContactsIDdaCerca(String DaTrovare) {
        ArrayList<Integer> array_list = new ArrayList<Integer>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Fornitori where "+CONTACTS_COLUMN_NAME+" LIKE "+"'%"+DaTrovare+"%'", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getInt(res.getColumnIndex(CONTACTS_COLUMN_ID)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<String> getAllFormuleDaCerca(String DaTrovare) {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Formule where "+FORM_COL_NAME+" LIKE '%"+DaTrovare+"%'", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(FORM_COL_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<Integer> getAllFormuleIDdaCerca(String DaTrovare) {
        ArrayList<Integer> array_list = new ArrayList<Integer>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Formule where "+FORM_COL_NAME+" LIKE "+"'%"+DaTrovare+"%'", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getInt(res.getColumnIndex(FORM_COL_ID)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Integer> getIDCompFormula(int idfrm) {
        ArrayList<Integer> array_list = new ArrayList<Integer>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+COMP_TABLE_NAME+ " where "+COMP_COL_IDFRM+"="+idfrm+"", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getInt(res.getColumnIndex(COMP_COL_IDMPR)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<Integer> getIDCONFdiFormula(int idfrm) {
        ArrayList<Integer> array_list = new ArrayList<Integer>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CONFDIFRM2_TABLE_NAME+ " where "+CONFDIFRM2_COL_IDFRM+"="+idfrm+"", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getInt(res.getColumnIndex(CONFDIFRM2_COL_IDCONF)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<Integer> getIDCompConf(int idconf) {
        ArrayList<Integer> array_list = new ArrayList<Integer>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+IMBINCONF_TABLE_NAME+ " where "+IMBINCONF_COL_IDCONF+"="+idconf+"", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getInt(res.getColumnIndex(IMBINCONF_COL_IDIMB)));
            res.moveToNext();
        }
        return array_list;
    }
    public Double CostoAlLitroFormula(int idfrm) {
        Double res;
        Double prezzo;
        Double percent;
        Integer IdMpr;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor curs =  db.rawQuery( "select * from "+COMP_TABLE_NAME+ " where "+COMP_COL_IDFRM+"="+idfrm+"", null );
        curs.moveToFirst();
        res=0.0;
        while(curs.isAfterLast() == false){
            percent=curs.getDouble(curs.getColumnIndex(COMP_COL_PERCENT));
            IdMpr=curs.getInt(curs.getColumnIndex(COMP_COL_IDMPR));
            Cursor curs2 =  db.rawQuery( "select * from Materieprime where "+MP_COL_ID+"="+IdMpr, null );
            curs2.moveToFirst();
            if(curs2.isAfterLast() == false) {
                prezzo=curs2.getDouble(curs2.getColumnIndex(MP_COL_PREZZO));
                res=res+percent*prezzo;
            }

            curs2.close();
            curs.moveToNext();
        }
        res=res/100;
        return res;
    }
    public Double CostoAlLitroConfezione(int idconf, double LitriConf) {
        Double res;
        Double prezzo;
        Double pezzi;
        Integer IdImb;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor curs =  db.rawQuery( "select * from "+IMBINCONF_TABLE_NAME+ " where "+IMBINCONF_COL_IDCONF+"="+idconf+"", null );
        curs.moveToFirst();
        res=0.0;
        while(curs.isAfterLast() == false){
            pezzi=curs.getDouble(curs.getColumnIndex(IMBINCONF_COL_QIDIMB));
            IdImb=curs.getInt(curs.getColumnIndex(IMBINCONF_COL_IDIMB));
            Cursor curs2 =  db.rawQuery( "select * from Imballaggi where "+IMB_COL_ID+"="+IdImb, null );
            curs2.moveToFirst();
            prezzo=curs2.getDouble(curs2.getColumnIndex(IMB_COL_PREZZO));
            curs2.close();
            res=res+pezzi*prezzo;
            curs.moveToNext();
        }
        res=res/LitriConf;
        return res;
    }
    public ArrayList<String> getAllFormule() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Formule", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(FORM_COL_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<Integer> getAllFORMID() {
        ArrayList<Integer> array_list = new ArrayList<Integer>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Formule", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getInt(res.getColumnIndex(FORM_COL_ID)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<String> getAllConf() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Confezioni", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONF_COL_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<Integer> getAllConfID() {
        ArrayList<Integer> array_list = new ArrayList<Integer>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Confezioni", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getInt(res.getColumnIndex(CONF_COL_ID)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getAllCotacts() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Fornitori", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<Integer> getAllID() {
        ArrayList<Integer> array_list = new ArrayList<Integer>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Fornitori", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getInt(res.getColumnIndex(CONTACTS_COLUMN_ID)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<String> getAllImballaggi() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Imballaggi", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(IMB_COL_NOME)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<String> getAllImballaggiForn(int id) {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Imballaggi where fornimb="+id+"", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(IMB_COL_NOME)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Integer> getAllIMBID() {
        ArrayList<Integer> array_list = new ArrayList<Integer>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Imballaggi", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getInt(res.getColumnIndex(IMB_COL_ID)));
            res.moveToNext();
        }
        return array_list;
    }

}
package com.example.banco.databasesample;

/**
 * Created by Banco on 08/03/2017.
 */


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.StringTokenizer;

public class settings extends AppCompatActivity {
    public final static String SELEZFORN = "SELEZFORN";
    private ListView obj;
    public ArrayList<String> array_list;
    public ArrayList<Integer> array_ID;
    private ArrayAdapter arrayAdapter;
    DBHelper mydb;
    public EditText cerca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setHomeButtonEnabled(true);
        mydb = new DBHelper(this,"Hichem.db");
    }
    private boolean shouldAskPermission(){

        return(Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP_MR1);

    }

    public void backupDatabase(View view) {
        String dbPath;
        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};

        int permsRequestCode = 200;
        if (shouldAskPermission()) {
            requestPermissions(perms, permsRequestCode);
        }
        //if (null != mydb) {
            dbPath= mydb.getDbPath();//mCountriesDb è la classe estesa che permette la gestione del database.

            if (dbPath == null) {
                Toast.makeText(getApplicationContext(), "Database vuoto!", Toast.LENGTH_LONG).show();
                return;
            }
            try {
                File sd = Environment.getExternalStorageDirectory();

                String path = sd.getAbsolutePath() + "";
                Calendar today= Calendar.getInstance();
                String oggi= DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY).format(today.getTime());
                final String _backupFileName="HichemBackup"+oggi+".db";
                final File backupDB = new File(path,_backupFileName);
                if (!backupDB.exists()) {

                    backupDB.createNewFile();

                }
                //if (backupDB.canWrite()) {
                File currentDB = new File(dbPath);//path del db su telefono


                FileChannel src = new FileInputStream(currentDB).getChannel();//apriamo un filechannel sul db e sul file di destinazione
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());//trasferiamo il contenuto
                src.close();
                dst.close();
                new AlertDialog.Builder(this)

                        .setIcon(R.drawable.icon)

                        .setTitle("Backup database")
                        .setMessage("Il database è stato salvato con il nome:\n\r"+
                               _backupFileName+"\n\r"+
                                "Inviare per e-mail?")
                        .setNegativeButton("NO",

                                new DialogInterface.OnClickListener() {


                                    @Override

                                    public void onClick(DialogInterface dialog, int which) {


                                    }

                                })
                        .setPositiveButton("SI",

                                new DialogInterface.OnClickListener() {

                                    final String nomebackup=_backupFileName;
                                    final String filelocation=backupDB.getAbsolutePath();
                                    @Override

                                    public void onClick(DialogInterface dialog, int which) {
                                        String emai ="";
                                        String testo="In allegato "+nomebackup;
                                        Intent intenzione = new Intent();
                                        intenzione.setAction(Intent.ACTION_SEND);
                                        intenzione.putExtra(Intent.EXTRA_STREAM, Uri.parse( "file://"+filelocation));
                                        intenzione.putExtra(Intent.EXTRA_EMAIL, new String[]{emai});
                                        intenzione.putExtra(Intent.EXTRA_SUBJECT, "BACKUP DATABASE "+nomebackup);
                                        intenzione.putExtra(Intent.EXTRA_TEXT, testo);
                                        intenzione.setType("text/plain");
                                        startActivity(intenzione);

                                    }

                                }).show();

                //} else {
                //   Log.e("Permission denied", "Can't write to SD card, add permission");
                // }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        //}

    }
    public void restoreDatabase(View view) {
        String dbPath;
        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};

        int permsRequestCode = 200;
        if (shouldAskPermission()) {
            requestPermissions(perms, permsRequestCode);
        }
        //if (null != mydb) {
            //mCountriesDb è la classe estesa che permette la gestione del database.

            Intent i = new Intent(this, AndroidExplorer.class);
            startActivityForResult(i, 1);

        //}

    }
    public void VaiAmail (View view){

        Intent intent = new Intent(this, TestoEmail.class);

        startActivity(intent);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String dbPath= mydb.getDbPath();
                String nomefile = data.getStringExtra("nomefile");
                ArrayList<String> array_list;
                Boolean filevalido=true;

                try {
                    DBHelper testbackup=new DBHelper(this,nomefile);
                    array_list = testbackup.getAllCotacts();
                    String [] listafornitori=array_list.toArray(new String[0]);
                    Integer lunghezza=listafornitori.length;
                    if (listafornitori.length<=0) {
                        filevalido=false;
                        new AlertDialog.Builder(this)

                                .setIcon(R.drawable.icon)

                                .setTitle("Errore!")
                                .setMessage("File non valido o database vuoto")

                                .setPositiveButton("OK",

                                        new DialogInterface.OnClickListener() {



                                            @Override

                                            public void onClick(DialogInterface dialog, int which) {


                                            }

                                        }).show();

                    }
                } catch (Exception e) {
                    filevalido=false;
                    new AlertDialog.Builder(this)

                            .setIcon(R.drawable.icon)

                            .setTitle("Errore!")
                            .setMessage("File non valido o database vuoto")

                            .setPositiveButton("OK",

                                    new DialogInterface.OnClickListener() {



                                        @Override

                                        public void onClick(DialogInterface dialog, int which) {


                                        }

                                    }).show();
                }
                if (filevalido) {
                    try {
                        File sd = Environment.getExternalStorageDirectory();

                        String path = sd.getAbsolutePath() + "";
                        File backupDB = new File(nomefile);
                        if (!backupDB.exists()) {

                            backupDB.createNewFile();

                        }
                        //if (backupDB.canWrite()) {
                        File currentDB = new File(dbPath);//path del db su telefono


                        FileChannel src = new FileInputStream(backupDB).getChannel();//apriamo un filechannel sul db e sul file di destinazione
                        FileChannel dst = new FileOutputStream(currentDB).getChannel();
                        dst.transferFrom(src, 0, src.size());//trasferiamo il contenuto
                        src.close();
                        dst.close();
                        new AlertDialog.Builder(this)

                                .setIcon(R.drawable.icon)

                                .setTitle("Restore Database")
                                .setMessage("Database importato con successo!")

                                .setPositiveButton("OK",

                                        new DialogInterface.OnClickListener() {



                                            @Override

                                            public void onClick(DialogInterface dialog, int which) {


                                            }

                                        }).show();

                        //} else {
                        //   Log.e("Permission denied", "Can't write to SD card, add permission");
                        // }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}



package com.example.banco.databasesample;

/**
 * Created by Banco on 16/03/2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TestoEmail extends AppCompatActivity {
    TextView quan;
    TextView unmis;
    TextView tipoMail;
    EditText inizio, fine;
    String testoInizio,testoFine;
    DBtestiMail testimail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testo_email);
        inizio=(EditText)findViewById(R.id.editTestoInizio);
        fine=(EditText)findViewById(R.id.editTestoFine);
        tipoMail=(TextView) findViewById(R.id.textView);
        tipoMail.setText("Mail Ordini a Fornitore");
        inizio.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // disabilita la gestione dell'evento tocco da parte del
                // viewgroup di cui fa parte il text permettendo lo scroll della view corrente
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        fine.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        testimail=new DBtestiMail(this);
        Cursor mailOrdini=testimail.getAllMail();
        mailOrdini.moveToFirst();
        if (!mailOrdini.isAfterLast()){
            testoInizio=mailOrdini.getString(mailOrdini.getColumnIndex(DBtestiMail.TESTI_COL_INTEST));
            testoFine=mailOrdini.getString(mailOrdini.getColumnIndex(DBtestiMail.TESTI_COL_FINE));
            inizio.setText(testoInizio);
            fine.setText(testoFine);
        }
    }

    public void SalvaEmail(View view) {
        Integer idMailFornitori;
        testimail=new DBtestiMail(this);
        inizio=(EditText)findViewById(R.id.editTestoInizio);
        fine=(EditText)findViewById(R.id.editTestoFine);
        Cursor mailOrdini=testimail.getAllMail();
        mailOrdini.moveToFirst();
        idMailFornitori=mailOrdini.getInt(mailOrdini.getColumnIndex(DBtestiMail.TESTI_COL_ID));
        testimail.updateMAIL(idMailFornitori,"Ordini Fornitore",inizio.getText().toString(),"",fine.getText().toString(),"","","");
        mailOrdini.close();
        finish();
    }
    public void AnnullaEmail(View view) {

        finish();
    }

}


package com.example.banco.databasesample;

/**
 * Created by Banco on 16/03/2017.
 */

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class TestoEmailQuot extends AppCompatActivity {
    TextView quan;
    TextView unmis, tipoMail;
    EditText inizio, fine;
    String testoInizio,testoFine,Nome;
    DBtestiMail testimail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testo_email);
        inizio=(EditText)findViewById(R.id.editTestoInizio);
        fine=(EditText)findViewById(R.id.editTestoFine);
        tipoMail=(TextView) findViewById(R.id.textView);
        tipoMail.setText("Mail Quotazioni a Fornitore");
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
            Nome=mailOrdini.getString(mailOrdini.getColumnIndex(DBtestiMail.TESTI_COL_NAME));
            if (!Nome.equals("Quotazioni Fornitori")) {
                mailOrdini.moveToNext();
                testoInizio=mailOrdini.getString(mailOrdini.getColumnIndex(DBtestiMail.TESTI_COL_INTEST));
                testoFine=mailOrdini.getString(mailOrdini.getColumnIndex(DBtestiMail.TESTI_COL_FINE));
                inizio.setText(testoInizio);
                fine.setText(testoFine);
            }
        }

    }

    public void SalvaEmail(View view) {
        Integer idMailFornitori;
        testimail=new DBtestiMail(this);
        inizio=(EditText)findViewById(R.id.editTestoInizio);
        fine=(EditText)findViewById(R.id.editTestoFine);
        Cursor mailOrdini=testimail.getAllMail();
        mailOrdini.moveToFirst();
        mailOrdini.moveToNext();
        idMailFornitori=mailOrdini.getInt(mailOrdini.getColumnIndex(DBtestiMail.TESTI_COL_ID));
        testimail.updateMAIL(idMailFornitori,"Quotazioni Fornitori",inizio.getText().toString(),"",fine.getText().toString(),"","","");
        mailOrdini.close();
        finish();
    }
    public void AnnullaEmail(View view) {

        finish();
    }

}


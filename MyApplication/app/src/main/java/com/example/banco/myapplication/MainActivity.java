package com.example.banco.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] nameproducts = new String[] { "Anagrafica Fornitori","Materie Prime", "Imballaggi","Formule" ,"Esci" };

        // definisco un ArrayList
        final ArrayList<String> listp = new ArrayList<String>();
        for (int i = 0; i < nameproducts.length; ++i) {
            listp.add(nameproducts[i]);
        }
        // recupero la lista dal layout
        final ListView mylist = (ListView) findViewById(R.id.Campi);

        // creo e istruisco l'adattatore
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listp);

        // inietto i dati
        mylist.setAdapter(adapter);
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView adapter, final View nameproducts, int pos, long id ){
                final String titoloriga= (String) adapter.getItemAtPosition(pos);
                Toast.makeText(getApplicationContext(),titoloriga,Toast.LENGTH_SHORT).show();
                if (titoloriga=="Esci") {
                    ESCIselected();
                }
                if (titoloriga=="Anagrafica Fornitori") {
                    FORNselected();
                }
                if (titoloriga=="Materie Prime") {
                    MPselected();
                }
                if (titoloriga=="Imballaggi") {
                    IMBselected();
                }
                if (titoloriga=="Formule") {
                    FORMULEselected();
                }
            }
        });
    }

    /** Called when the user clicks the Send button */
    public void ESCIselected() {
        String messaggio= ("Ciao");
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        intent.putExtra(EXTRA_MESSAGE, messaggio);
        startActivity(intent);
    }
    public void MPselected() {
        String messaggio= ("Ciao");
        Intent intent = new Intent(this, MateriePrime.class);
        intent.putExtra(EXTRA_MESSAGE, messaggio);
        startActivity(intent);
    }
    public void IMBselected() {
        String messaggio= ("Ciao");
        Intent intent = new Intent(this, Imballaggi.class);
        intent.putExtra(EXTRA_MESSAGE, messaggio);
        startActivity(intent);
    }
    public void FORNselected() {
        String messaggio= ("Ciao");
        Intent intent = new Intent(this, Fornitori.class);
        intent.putExtra(EXTRA_MESSAGE, messaggio);
        startActivity(intent);
    }
    public void FORMULEselected() {
        String messaggio= ("Ciao");
        Intent intent = new Intent(this, Formule.class);
        intent.putExtra(EXTRA_MESSAGE, messaggio);
        startActivity(intent);
    }

}

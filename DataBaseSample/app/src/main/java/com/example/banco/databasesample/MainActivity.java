package com.example.banco.databasesample;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public final static String INTEST_ORDINI = "Buongiorno,\ncon la presente siamo a richiedervi fornitura"+
            " per quanto segue:\n\n";
    public final static String FINE_ORDINI = "Cordiali saluti\nRotondi Alessandro\nHichem srl\n\n"+
            "Hichem srl - Via Risorgimento, 34 - 20030 Senago\nLe informazioni contenute in questo messaggio"+
            "sono da considerarsi strettamente riservate e confidenziali, dirette esclusivamente al destinatario"+
            "indicato, unico soggetto autorizzato alla lettura, alla copiatura e, sotto la propria responsabilità, "+
            "alla diffusione.\n\nQualora non foste i destinatari, ai sensi del GDPR. 2016/679 Vi informiamo che è "+
            "assolutamente vietata qualsiasi forma di riproduzione o diffusione; siete pregati di eliminare il "+
            "messaggio, inviando gentilmente comunicazione al seguente indirizzo: hichem@detergenti.info\n\nThe "+
            "information contained in this message is strictly privileged and confidential and is only destined to "+
            "the addressee(s) identified above, the only one who can read, copy and, under his responsibility, "+
            "disseminate it.\n\nIf you are not the address(es), to the senses of D.Lgs. GDPR. 2016/679, we inform "+
            "you that any copying or dissemination is strictly forbidden; please, erase this message and contact "+
            "immediately the sender: hichem@detergenti.info";
    Button backup;
    DBHelper mydb;
    DBtestiMail testimail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new DBHelper(this,"Hichem.db");
        testimail= new DBtestiMail(this);

        String[] nameproducts = new String[]{"Anagrafica Fornitori", "Materie Prime", "Imballaggi", "Formule", "Confezioni","Inventario e costi","Impostazioni", "Esci"};
        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
        Cursor mail=testimail.getAllMail();
        mail.moveToFirst();
        if (mail.isAfterLast()){
           testimail.insertMail("Ordini Fornitori",INTEST_ORDINI,"",FINE_ORDINI,"","","");
        }
        mail.close();
        int permsRequestCode = 200;
        if (shouldAskPermission()) {
            requestPermissions(perms, permsRequestCode);
        }
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
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapter, final View nameproducts, int pos, long id) {
                String titoloriga = (String) adapter.getItemAtPosition(pos);
                if (titoloriga == "Esci") {
                    titoloriga = "Ciao";
                }
                Toast.makeText(getApplicationContext(), titoloriga, Toast.LENGTH_SHORT).show();

                if (titoloriga == "Anagrafica Fornitori") {
                    FORNselected();
                }
                if (titoloriga == "Materie Prime") {
                    MPselected();
                }
                if (titoloriga == "Imballaggi") {
                    IMBselected();
                }
                if (titoloriga == "Formule") {
                    FORMULEselected();
                }
                if (titoloriga == "Inventario e costi") {
                    INVENTARIOselected();
                }
                if (titoloriga == "Impostazioni") {
                    SETTINGselected();
                }

                if (titoloriga == "Confezioni") {
                    CONFselected();
                }

            }
        });
    }

    /**
     * Called when the user clicks the Send button
     */

    public void MPselected() {
        String messaggio = ("Ciao");
        Intent intent = new Intent(this, MateriePrime.class);
        intent.putExtra(EXTRA_MESSAGE, messaggio);
        startActivity(intent);
    }

    public void IMBselected() {
        String messaggio = ("Ciao");
        Intent intent = new Intent(this, Imballaggi.class);
        intent.putExtra(EXTRA_MESSAGE, messaggio);
        startActivity(intent);
    }

    public void FORNselected() {
        String messaggio = ("MAIN");
        Intent intent = new Intent(this, Fornitori.class);
        intent.putExtra("mex", messaggio);
        startActivity(intent);
    }

    public void FORMULEselected() {
        String messaggio = ("Ciao");
        Intent intent = new Intent(this, Formule.class);
        intent.putExtra(EXTRA_MESSAGE, messaggio);
        startActivity(intent);
    }
    public void INVENTARIOselected() {
        String messaggio = ("Ciao");
        Intent intent = new Intent(this, Inventario.class);
        intent.putExtra(EXTRA_MESSAGE, messaggio);
        startActivity(intent);
    }

    public void SETTINGselected() {
        String messaggio = ("SETTINGS");
        Intent intent = new Intent(this, settings.class);
        intent.putExtra(EXTRA_MESSAGE, messaggio);
        startActivity(intent);
    }

    public void CONFselected() {
        String messaggio = ("MAIN");
        Intent intent = new Intent(this, Confezioni.class);
        intent.putExtra(EXTRA_MESSAGE, messaggio);
        startActivity(intent);
    }

    private boolean shouldAskPermission(){

        return(Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP_MR1);

    }

}

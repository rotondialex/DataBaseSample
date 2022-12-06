package com.example.banco.kalodikulo;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    DBHelper mydb;
    public ArrayList<String> array_list;
    private static Boolean FraseGiaFatta=false;
    public static Calendar giornodioggi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        String[] nameproducts = new String[]{"Tabella Alimenti", "Dieta Settimanale", "Pasti"};

        if (!FraseGiaFatta) {
            giornodioggi= Calendar.getInstance();
            InputStream frasi = getResources().openRawResource(R.raw.frasidelgiorno);
            BufferedReader buffFrasi = new BufferedReader(new InputStreamReader(frasi));
            String frase = "";
            Integer numfrasi = 0;
            try {
                while ((frase = buffFrasi.readLine()) != null) {
                    numfrasi++;
                }
            } catch (IOException e) {
            }
            Random r = new Random();
            int frasedelgiorno = r.nextInt(numfrasi) + 1;
            numfrasi = 0;
            frasi = getResources().openRawResource(R.raw.frasidelgiorno);
            buffFrasi = new BufferedReader(new InputStreamReader(frasi));
            try {
                while (numfrasi < frasedelgiorno) {
                    frase = buffFrasi.readLine();
                    numfrasi++;
                }
            } catch (IOException e) {
            }
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            //imposto il titolo ed il messaggio dell'alert dialog, prendendolo dalle Resources
            alertDialogBuilder
                    .setTitle("BISCOTTO DI FOLTUNA");
            alertDialogBuilder
                    .setMessage(frase);
            //questo dialog non verrà annullato tappando sullo schermo al di fuori del dialog stesso
            alertDialogBuilder.setCancelable(false);
            //imposto il clicklistener del pulsante "yes" ed il suo testo sempre dalle Resources

            alertDialogBuilder.setNegativeButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(
                                DialogInterface dialogInterface,
                                int i) {
                            //in caso di pressione del tasto "no" (negative) chiudo il dialog senza alcuna azione
                            dialogInterface.cancel();
                        }
                    });
            //dopo averne dichiarato le proprietà creo l'istanza del dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            //lo mostro
            alertDialog.show();
            FraseGiaFatta=true;
        }



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.item1) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void ALIMselected(View view) {
        String messaggio = ("Ciao");
        Intent intent = new Intent(this, Alimenti.class);
        intent.putExtra(EXTRA_MESSAGE, messaggio);
        startActivity(intent);
    }
    public void DIETAselected(View view) {
        String messaggio = ("Ciao");
        Intent intent = new Intent(this, DisplayDietaGiorno.class);
        intent.putExtra(EXTRA_MESSAGE, messaggio);
        startActivity(intent);
    }

    public void PASTIselected(View view) {
        String messaggio = ("Ciao");
        Intent intent = new Intent(this, Pasti.class);
        intent.putExtra(EXTRA_MESSAGE, messaggio);
        startActivity(intent);
    }

}

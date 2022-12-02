package com.example.banco.databasesample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DisplayMateriePrime extends AppCompatActivity {
    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb ;

    TextView name ;
    TextView prezzo;
    TextView quant;
    TextView quantmin;
    TextView forn;
    TextView numforn;
    TextView ultimod;
    TextView ncas;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_materieprime);
        getSupportActionBar().setHomeButtonEnabled(true);
        name = (TextView) findViewById(R.id.editTextName);
        prezzo = (TextView) findViewById(R.id.editTextPrezzo);
        quant = (TextView) findViewById(R.id.editTextQuant);
        quantmin = (TextView) findViewById(R.id.editTextQuantMin);
        forn = (TextView) findViewById(R.id.editTextForn);
        numforn=(TextView) findViewById(R.id.FornitoreSelez);
        ultimod=(TextView) findViewById(R.id.UltimaModifica);
        ncas=(TextView) findViewById(R.id.editNCas);

        mydb = new DBHelper(this,"Hichem.db");

        Bundle extras = getIntent().getExtras();
        TextView f = (TextView) findViewById(R.id.FornitoreSelez);
        f.setVisibility(View.INVISIBLE);
        if(extras !=null) {
            forn.setFocusable(false);
            forn.setClickable(false);
            numforn.setText("0");
            quant.setText("0");
            quantmin.setText("0");
            prezzo.setText("0");
            Calendar today= Calendar.getInstance();
            String oggi= DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY).format(today.getTime());
            ultimod.setText(oggi);
            int Value = extras.getInt("idmp");
            Button b = (Button)findViewById(R.id.button1);
            b.setText("Aggiungi");
            if(Value>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getMateriaprima(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String nam = rs.getString(rs.getColumnIndex(DBHelper.MP_COL_NOME));
                Double prez = rs.getDouble(rs.getColumnIndex(DBHelper.MP_COL_PREZZO));
                Double qua = rs.getDouble(rs.getColumnIndex(DBHelper.MP_COL_QUANTITA));
                Double quamin = rs.getDouble(rs.getColumnIndex(DBHelper.MP_COL_LIVELLOMIN));
                Integer fornit = rs.getInt(rs.getColumnIndex(DBHelper.MP_COL_FORN));
                String ultmod = rs.getString(rs.getColumnIndex(DBHelper.MP_COL_ULTIMAMOD));
                String ncastring = rs.getString(rs.getColumnIndex(DBHelper.MP_COL_CAS));


                if (!rs.isClosed())  {
                    rs.close();
                }

                b.setVisibility(View.INVISIBLE);
                Button b2 = (Button)findViewById(R.id.selezForn);
                b2.setVisibility(View.INVISIBLE);
                ListView l = (ListView) findViewById(R.id.LisForn);
                l.setVisibility(View.INVISIBLE);

                name.setText((CharSequence)nam);
                name.setFocusable(false);
                name.setClickable(false);

                ncas.setText(ncastring);
                ncas.setFocusable(false);
                ncas.setClickable(false);

                String tuaString = Double.toString(prez);
                prezzo.setText(tuaString);
                prezzo.setFocusable(false);
                prezzo.setClickable(false);

                tuaString = Double.toString(qua);
                quant.setText(tuaString);
                quant.setFocusable(false);
                quant.setClickable(false);

                tuaString = Double.toString(quamin);
                quantmin.setText(tuaString);
                quantmin.setFocusable(false);
                quantmin.setClickable(false);

                ultimod.setText(ultmod);

                if (fornit>0) {
                    final ArrayList<String> array_list = mydb.getAllCotacts();
                    final ArrayList<Integer> array_ID = mydb.getAllID();

                    fornit = array_ID.get(fornit-1 );
                    tuaString = Integer.toString(fornit);
                    numforn.setText(tuaString);
                    Cursor rs2 = mydb.getData(fornit);
                    rs2.moveToFirst();

                    tuaString = rs2.getString(rs2.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
                    rs2.close();
                    forn.setText(tuaString);
                    forn.setFocusable(false);
                    forn.setClickable(false);
                }

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            int Value = extras.getInt("idmp");
            if(Value>0){
                getMenuInflater().inflate(R.menu.display_matprim, menu);
            } else{
                //getMenuInflater().inflate(R.menu.menu_main,menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.Edit_Contact:
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.VISIBLE);
                b.setText("Modifica");
                Button b2 = (Button)findViewById(R.id.selezForn);
                b2.setVisibility(View.VISIBLE);
                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);

                ncas.setEnabled(true);
                ncas.setFocusableInTouchMode(true);
                ncas.setClickable(true);

                prezzo.setEnabled(true);
                prezzo.setFocusableInTouchMode(true);
                prezzo.setClickable(true);

                quant.setEnabled(true);
                quant.setFocusableInTouchMode(true);
                quant.setClickable(true);

                forn.setEnabled(false);
                forn.setFocusableInTouchMode(false);
                forn.setClickable(false);

                return true;
            case R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteMateriaprima(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Cancellazione Avvenuta",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

                AlertDialog d = builder.create();
                d.setTitle("Sicuro");
                d.show();

                return true;
            case R.id.Ordina_a_fornitore:
                Integer Fornit=Integer.parseInt(numforn.getText().toString());
                if (Fornit>0) {
                    Intent i = new Intent(this, Quantita.class);
                    i.putExtra("qualeattivita","Ordine");
                    startActivityForResult(i, 1);

                }
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        if (numforn.getText().toString().isEmpty() || name.getText().toString().isEmpty()) {
            if (name.getText().toString().isEmpty()){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Inserire un nome valido")
                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

                AlertDialog d = builder.create();
                d.setTitle("Attenzione!");
                d.show();
            }

        }
        else {
            if (extras != null) {
                int Value = extras.getInt("idmp");
                if (quant.getText().toString().equals("")) quant.setText("0.0");
                if (prezzo.getText().toString().equals("")) prezzo.setText("0.0");
                if (quantmin.getText().toString().equals("")) quantmin.setText("0.0");
                if (Value > 0) {

                    if (mydb.updateMateriaprima(id_To_Update, name.getText().toString(),
                            Double.parseDouble(prezzo.getText().toString()), Double.parseDouble(quant.getText().toString()),
                            Integer.parseInt(numforn.getText().toString()), "1", prezzo.getText().toString(), Double.parseDouble(quantmin.getText().toString()), ncas.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Aggiornato", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Non Aggiornato", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (mydb.insertMateriaPrima(name.getText().toString(),
                            Double.parseDouble(prezzo.getText().toString()), Double.parseDouble(quant.getText().toString()),
                            Integer.parseInt(numforn.getText().toString()), "1", prezzo.getText().toString(), Double.parseDouble(quantmin.getText().toString()), ncas.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Aggiunto",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Non Aggiunto",
                                Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        }
    }
    public void ScegliFornitore(View view) {
        ListView b = (ListView) findViewById(R.id.LisForn);
        b.setVisibility(View.VISIBLE);
        final ArrayList<String> array_list = mydb.getAllCotacts();
        final ArrayList<Integer> array_ID = mydb.getAllID();
        // Ordino Alfabeticamente
        String Supporto;
        Integer r,i,Supp;
        String [] Perordinare=array_list.toArray(new String[0]);
        boolean Nonfinito=true;
        while (Nonfinito) {
            i=0;
            Nonfinito=false;
            while (i<Perordinare.length-1){
                r=Perordinare[i].compareTo(Perordinare[i+1]);
                if (r>0){
                    Nonfinito=true;
                    Supporto=Perordinare[i];
                    Perordinare[i]=Perordinare[i+1];
                    Perordinare[i+1]=Supporto;
                    Supporto=array_list.get(i);
                    array_list.set(i,array_list.get(i+1));
                    array_list.set(i+1,Supporto);
                    Supp=array_ID.get(i);
                    array_ID.set(i,array_ID.get(i+1));
                    array_ID.set(i+1,Supp);
                }
                i=i+1;
            }
        }
        // Fine routine ordinamento
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);
        b.setAdapter(arrayAdapter);
        b.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                // TODO Auto-generated method stub
                int id_To_Search = arg2;
                id_To_Search=array_ID.get(id_To_Search);
                String tuaString = Integer.toString(id_To_Search);
                forn = (TextView) findViewById(R.id.FornitoreSelez);
                forn.setText(tuaString);

                Cursor rs2 = mydb.getData(id_To_Search);
                rs2.moveToFirst();

                tuaString = rs2.getString(rs2.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
                rs2.close();
                forn = (TextView) findViewById(R.id.editTextForn);
                forn.setText(tuaString);
                forn.setFocusable(false);
                forn.setClickable(false);
                ListView b = (ListView) findViewById(R.id.LisForn);
                b.setVisibility(View.INVISIBLE);

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Integer Fornit=Integer.parseInt(numforn.getText().toString());
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                    DBtestiMail mails;
                    String inizio,fine;
                    Double result = data.getDoubleExtra("result", 0);
                    String unmis = data.getStringExtra("unmis");
                    Integer intResult=(int)(result*1);
                    Cursor rs = mydb.getData(Fornit);
                    rs.moveToFirst();
                    String emai = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMAIL));
                    rs.close();
                    // Istruisco Mail
                mails=new DBtestiMail(this);
                Cursor testiMail=mails.getAllMail();
                testiMail.moveToFirst();
                inizio=testiMail.getString(testiMail.getColumnIndex(DBtestiMail.TESTI_COL_INTEST));
                fine=testiMail.getString(testiMail.getColumnIndex(DBtestiMail.TESTI_COL_FINE));
                testiMail.close();
                    Intent intenzione = new Intent();
                    intenzione.setAction(Intent.ACTION_SEND);
                    intenzione.putExtra(Intent.EXTRA_EMAIL, new String[]{emai});
                    intenzione.putExtra(Intent.EXTRA_SUBJECT, "Ordine");
                    intenzione.putExtra(Intent.EXTRA_TEXT, inizio +
                            "  - " + Integer.toString(intResult) + " " + unmis + " di " + name.getText().toString() + "\n\r" +
                            "\n\r" +
                            fine);
                    intenzione.setType("text/plain");
                    startActivity(intenzione);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult
}
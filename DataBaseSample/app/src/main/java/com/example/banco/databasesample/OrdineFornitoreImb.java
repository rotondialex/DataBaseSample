package com.example.banco.databasesample;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Banco on 16/03/2017.
 */

public class OrdineFornitoreImb extends AppCompatActivity {
    private ListView obj;
    DBHelper mydb;
    TextView ProdSel;
    Integer Value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordinefornitore);
        getSupportActionBar().setHomeButtonEnabled(true);
        mydb = new DBHelper(this,"Hichem.db");
        ProdSel=(TextView) findViewById(R.id.editProdottoselez);
        ProdSel.setText("");
        ProdSel.setFocusable(false);
        ProdSel.setClickable(false);
        Bundle extras = getIntent().getExtras();
        Value = extras.getInt("id");
        String email = extras.getString("email");
        TextView emma=(TextView) findViewById(R.id.editMail);
        emma.setText(email);
        final ArrayList<String> array_list = mydb.getAllImballaggiForn(Value);

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);

        obj = (ListView)findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);
        obj = (ListView)findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub

                String Prodotto= array_list.get(arg2);
                ProdSel.setText(Prodotto);

            }
        });
    }
    public void InserisciInOrdine(View view) {
        TextView Prod;
        TextView UM;
        TextView Quant;
        TextView Ord;
        Prod = (TextView) findViewById(R.id.editProdottoselez);
        UM = (TextView) findViewById(R.id.editText);
        Quant = (TextView) findViewById(R.id.editText2);
        Ord = (TextView) findViewById(R.id.textView10);

        String Ordine=Ord.getText().toString();
        String Riga=" - "+Quant.getText().toString()+" "+UM.getText().toString()+" di "+ Prod.getText().toString();
        if (Ordine.contains("Niente in ordine")) {
            Ord.setText(Riga);
        }
        else {
            Ord.setText(Ord.getText().toString()+"\n\r"+Riga);
        }


    }
    public void LanciaMail(View view){
        TextView Ord;
        DBtestiMail mails;
        String inizio,fine;
        Ord = (TextView) findViewById(R.id.textView10);
        TextView email=(TextView) findViewById(R.id.editMail);
        // Istruisco Mail
        mails=new DBtestiMail(this);
        Cursor testiMail=mails.getAllMail();
        testiMail.moveToFirst();
        inizio=testiMail.getString(testiMail.getColumnIndex(DBtestiMail.TESTI_COL_INTEST));
        fine=testiMail.getString(testiMail.getColumnIndex(DBtestiMail.TESTI_COL_FINE));
        testiMail.close();
        String emai=email.getText().toString();
        Calendar today= Calendar.getInstance();
        if(mydb.insertOrdine( Ord.getText().toString(),Value, DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY).format(today.getTime())," "," ")){
            Toast.makeText(getApplicationContext(), "Ordine Aggiunto",
                    Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(getApplicationContext(), "Ordine Non Aggiunto",
                    Toast.LENGTH_SHORT).show();
        }
        Intent intenzione = new Intent();
        intenzione.setAction(Intent.ACTION_SEND);
        intenzione.putExtra(Intent.EXTRA_EMAIL, new String[]{emai});
        intenzione.putExtra(Intent.EXTRA_SUBJECT, "Ordine");
        intenzione.putExtra(Intent.EXTRA_TEXT, inizio + Ord.getText().toString()+"\n\n"+ fine);
        intenzione.setType("text/plain");
        startActivity(intenzione);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", Value);

                Intent intent = new Intent(getApplicationContext(),DisplayContact.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
                return true;
        }
        return true;
    }
}

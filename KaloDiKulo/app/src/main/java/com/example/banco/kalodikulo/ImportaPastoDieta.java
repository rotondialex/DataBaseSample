package com.example.banco.kalodikulo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ImportaPastoDieta extends AppCompatActivity {
    TextView Giorno;TextView Pasto;Double Quantita;TextView Alimento;
    Integer IDpasto;
    Integer IDalim;
    String pasto;
    public ArrayList<String> array_list;
    public ArrayList<Integer> array_ID;
    private ArrayAdapter arrayAdapter;
    DBHelper mydb;
    private ListView obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inserisci_pasto_in_dieta);
        getSupportActionBar().setHomeButtonEnabled(true);
        Bundle extras = getIntent().getExtras();
        String giorno=extras.getString("giorno");
        pasto=extras.getString("pasto");
        Giorno=(TextView) findViewById(R.id.giorno);
        Pasto=(TextView) findViewById(R.id.pasto);
        Alimento=(TextView) findViewById(R.id.alimento);
        Giorno.setText(giorno);
        Pasto.setText(pasto);
        mydb = new DBHelper(this);
        array_list = mydb.getAllPasti();
        array_ID = mydb.getAllIDPasti();

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

        obj = (ListView)findViewById(R.id.Listapasti);
        obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
                // TODO Auto-generated method stub
                int id_To_Search = arg2;
                IDpasto = array_ID.get(id_To_Search);
                Alimento.setText(array_list.get(arg2));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            /*case R.id.item1:Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                Intent intent = new Intent(getApplicationContext(),DisplayAlimento .class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void inserisciindieta(View view){
        Integer cpc;
        mydb = new DBHelper(this);
        Giorno=(TextView) findViewById(R.id.giorno);
        if (IDpasto!=null)
        {
            switch (pasto)
            {
                case "COLAZIONE":
                    cpc=1;
                    break;
                case "PRANZO":
                    cpc=2;
                    break;
                case "CENA":
                    cpc=3;
                    break;
                default: cpc=0;
            }
            Cursor vociPasto =mydb.getVociPasto(IDpasto);
            vociPasto.moveToFirst();
            while (!vociPasto.isAfterLast()) {
                IDalim=vociPasto.getInt(vociPasto.getColumnIndex(DBHelper.VOCIPASTI_CMP_IDALIM));
                Quantita=vociPasto.getDouble(vociPasto.getColumnIndex(DBHelper.VOCIPASTI_CMP_QTA));
                mydb.insertDieta(IDalim,Quantita, cpc, Giorno.getText().toString(), "", "");
                vociPasto.moveToNext();
            }

            Intent intent = new Intent(this, DisplayDietaGiorno.class);
            startActivity(intent);
        }
    }
    public void annulla (View view){
        finish();
    }
}
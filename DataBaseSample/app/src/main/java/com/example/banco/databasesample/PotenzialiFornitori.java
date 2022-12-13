package com.example.banco.databasesample;

/**
 * Created by Banco on 08/03/2017.
 */


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PotenzialiFornitori extends AppCompatActivity {
    public final static String SELEZFORN = "SELEZFORN";
    private ListView obj,obj2;
    public ArrayList<String> array_list;
    public ArrayList<Integer> array_ID;
    public ArrayList<String> array_AF;
    public ArrayList<Integer> array_IDAF;
    private ArrayAdapter arrayAdapter;
    DBHelper mydb;
    public EditText cerca,TXfornsel;
    public String idforn,altrif;
    public TextView idfornsel,altriforn;
    public Button insOtogli;
    public Integer MatPrima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pot_fornitori);
        getSupportActionBar().setHomeButtonEnabled(false);
        cerca = (EditText) findViewById(R.id.editCerca);
        TXfornsel = (EditText) findViewById(R.id.editfornselez);
        idfornsel=(TextView) findViewById(R.id.IDfornsel);
        altriforn=(TextView) findViewById(R.id.altrifornitori);
        insOtogli=(Button) findViewById(R.id.buttonQuant);

        mydb = new DBHelper(this,"Hichem.db");
        array_list = mydb.getAllCotacts();
        array_ID = mydb.getAllID();
        Bundle extras = getIntent().getExtras();
        MatPrima = extras.getInt("materiaprima");
        // recupero la materia prima
        if (extras!=null) {

            Cursor rs = mydb.getMateriaprima(MatPrima);
            rs.moveToFirst();
            altrif = rs.getString(rs.getColumnIndex(DBHelper.MP_COL_ALTRIFORN));
            altriforn.setText(altrif);
            array_AF = new ArrayList<String>();
            array_IDAF = new ArrayList<Integer>();
            if (!altrif.equals("0")) {
                String[] Fornit = altrif.split(",");
                Integer i;
                i = 0;
                Cursor rs2;
                String nomeforn;
                Integer idfornitore;
                while (i < Fornit.length) {
                    rs2 = mydb.getData(Integer.parseInt(Fornit[i]));
                    rs2.moveToFirst();
                    nomeforn = rs2.getString(rs2.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
                    idfornitore = rs2.getInt(rs2.getColumnIndex(DBHelper.CONTACTS_COLUMN_ID));
                    array_AF.add(nomeforn);
                    array_IDAF.add(idfornitore);
                    i++;
                }
            }
        }
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
        cerca.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String testo = cerca.getText().toString();
                array_list = mydb.getAllContactsDaCerca(testo);
                array_ID = mydb.getAllContactsIDdaCerca(testo);
                // Ordino Alfabeticamente
                String Supporto;
                Integer r,x,Supp;
                String [] Perordinare=array_list.toArray(new String[0]);
                boolean Nonfinito=true;
                while (Nonfinito) {
                    x=0;
                    Nonfinito=false;
                    while (x<Perordinare.length-1){
                        r=Perordinare[x].compareTo(Perordinare[x+1]);
                        if (r>0){
                            Nonfinito=true;
                            Supporto=Perordinare[x];
                            Perordinare[x]=Perordinare[x+1];
                            Perordinare[x+1]=Supporto;
                            Supporto=array_list.get(x);
                            array_list.set(x,array_list.get(x+1));
                            array_list.set(x+1,Supporto);
                            Supp=array_ID.get(x);
                            array_ID.set(x,array_ID.get(x+1));
                            array_ID.set(x+1,Supp);
                        }
                        x=x+1;
                    }
                }
                // Fine routine ordinamento
                arrayAdapter=new ArrayAdapter(getBaseContext(),R.layout.rowfornit, array_list);
                obj = (ListView)findViewById(R.id.listView1);
                obj.setAdapter(arrayAdapter);

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }

        });
         ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.rowfornit, array_list);

        obj = (ListView)findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                // TODO Auto-generated method stub

                    int id_To_Search = arg2;
                    TXfornsel.setText(array_list.get(id_To_Search));
                    id_To_Search = array_ID.get(id_To_Search);
                    idfornsel.setText(String.valueOf(id_To_Search));
                    insOtogli.setText(R.string.freccegiu);

            }
        });
        ArrayAdapter arrayAdapter2=new ArrayAdapter(this,R.layout.rowfornit, array_AF);

        obj2 = (ListView)findViewById(R.id.potView1);
        obj2.setAdapter(arrayAdapter2);
        obj2.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                // TODO Auto-generated method stub

                int id_To_Search = arg2;
                TXfornsel.setText(array_AF.get(id_To_Search));
                idfornsel.setText(String.valueOf(array_IDAF.get(id_To_Search)));
                insOtogli.setText(R.string.freccesu);

            }
        });
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            case R.id.item1:Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                Intent intent = new Intent(getApplicationContext(),DisplayContact.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
*/
    public void AFfatto (View view){
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("idmp", MatPrima);

        Intent intent = new Intent(getApplicationContext(),DisplayMateriePrime.class);

        intent.putExtras(dataBundle);
        startActivity(intent);
    }
    public void aggtogliforn (View view){

        Boolean trovato;
        Integer idselez,i,supporto;
        String newAltriF;
        String togliMetti=insOtogli.getText().toString();
        idselez=Integer.parseInt(idfornsel.getText().toString());
        if (togliMetti.equals("↑↑↑")){
            i=0;supporto=0;
            while (i<array_IDAF.size()){
                supporto=array_IDAF.get(i);
                if (idselez.equals(supporto)){
                    array_IDAF.remove(supporto);
                }
                i++;
            }
            if (array_IDAF.size()>0){
                i=0;newAltriF="";
                while (i<array_IDAF.size()){
                    newAltriF=newAltriF+array_IDAF.get(i);
                    i++;
                    if (i<array_IDAF.size()) newAltriF=newAltriF+",";

                }
            }
            else newAltriF="0";
            mydb.updateAltriforn(MatPrima,newAltriF);
            Intent y = new Intent(this, PotenzialiFornitori.class);
            y.putExtra("materiaprima",MatPrima);
            startActivity(y);
        }
        else{
            i=0;supporto=0;trovato=false;
            while (i<array_IDAF.size()){
                supporto=array_IDAF.get(i);
                if (idselez.equals(supporto)){
                    trovato=true;
                }
                i++;
            }
            if (!trovato) {
                array_IDAF.add(idselez);
                i = 0;
                newAltriF = "";
                while (i < array_IDAF.size()) {
                    newAltriF = newAltriF + array_IDAF.get(i);
                    i++;
                    if (i < array_IDAF.size()) newAltriF = newAltriF + ",";

                }
                mydb.updateAltriforn(MatPrima,newAltriF);
                Intent y = new Intent(this, PotenzialiFornitori.class);
                y.putExtra("materiaprima",MatPrima);
                startActivity(y);
            }
        }
    }
}

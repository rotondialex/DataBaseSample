package com.example.banco.databasesample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Banco on 30/03/2017.
 */

public class ConfDiFormule extends AppCompatActivity {
    private ListView obj;
    DBHelper mydb;
    TextView ProdSel;
    TextView Percent;
    TextView idmpr;
    Integer idfrm;
    EditText cerca;
    private ArrayAdapter arrayAdapter;
    private ArrayList<String> array_list;
    private ArrayList<Integer> array_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf_diformule);
        getSupportActionBar().setHomeButtonEnabled(true);
        ProdSel= (TextView) findViewById(R.id.EditComp);
        Percent= (TextView) findViewById(R.id.EditQuant);
        idmpr= (TextView) findViewById(R.id.IdMpr);
        cerca = (EditText) findViewById(R.id.editCerca);
        Percent.setText("0");
        Bundle extras = getIntent().getExtras();
        idfrm=extras.getInt("IdFrm");
        mydb = new DBHelper(this,"Hichem.db");
        array_list = mydb.getAllConf();
        array_ID = mydb.getAllConfID();
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

        obj = (ListView)findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                // TODO Auto-generated method stub
                int id_To_Search = arg2;
                id_To_Search=array_ID.get(id_To_Search);
                idmpr.setText(String.valueOf(id_To_Search));
                String Prodotto= array_list.get(arg2);
                ProdSel.setText(Prodotto);
            }
        });
    }

    public void RestituisciQuantImb(View view) {

            if (Integer.parseInt(idmpr.getText().toString()) > -1) {
                if (mydb.cegiaConfInFormula(idfrm,Integer.parseInt(idmpr.getText().toString()))) {
                    Toast.makeText(getApplicationContext(), "Confezione già presente!",
                            Toast.LENGTH_SHORT).show();
                }else {
                    if (mydb.insertConfInFormula(idfrm, Integer.parseInt(idmpr.getText().toString()), Double.parseDouble(Percent.getText().toString()))) {
                        Toast.makeText(getApplicationContext(), "Aggiunto",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Non Aggiunto",
                                Toast.LENGTH_SHORT).show();
                    }
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("componente", ProdSel.getText().toString());
                    returnIntent.putExtra("percentuale", Percent.getText().toString());
                    returnIntent.putExtra("idmpr", idmpr.getText().toString());
                    returnIntent.putExtra("qualeattivita","CONFdiFRM");
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("idfrm", idfrm);

                Intent intent = new Intent(getApplicationContext(),DisplayFormule.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            return true;
        }
        return true;
    }
}

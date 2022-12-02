package com.example.banco.databasesample;

/**
 * Created by Banco on 08/03/2017.
 */


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Formule extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "MESSAGE";
    private ListView obj;
    public ArrayList<String> array_list;
    public ArrayList<Integer> array_ID;
    private ArrayAdapter arrayAdapter;
    DBHelper mydb;
    public EditText cerca;
    Integer posizFormula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formule);
        getSupportActionBar().setHomeButtonEnabled(true);
        mydb = new DBHelper(this,"Hichem.db");
        array_list = mydb.getAllFormule();
        array_ID = mydb.getAllFORMID();
        cerca = (EditText) findViewById(R.id.editCerca);

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
                array_list = mydb.getAllFormuleDaCerca(testo);
                array_ID = mydb.getAllFormuleIDdaCerca(testo);
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
                arrayAdapter=new ArrayAdapter(getBaseContext(),android.R.layout.simple_list_item_1, array_list);
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
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);

        obj = (ListView)findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);
        registerForContextMenu(obj);
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                int id_To_Search = arg2;
                id_To_Search=array_ID.get(id_To_Search);
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("idfrm", id_To_Search);

                Intent intent = new Intent(getApplicationContext(),DisplayFormule.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
    }

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
                dataBundle.putInt("idfrm", 0);

                Intent intent = new Intent(getApplicationContext(),DisplayFormule.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        ListView listaFormule= (ListView) findViewById(R.id.listView1);
        if (v==listaFormule){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            posizFormula=info.position;
            menu.setHeaderTitle(listaFormule.getItemAtPosition(posizFormula).toString().toString());
            menu.add(0, v.getId(), 0, "Duplica Formula");
            menu.add(0, v.getId(), 0, "Elimina Formula");
        }


    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {


        if (item.getTitle() == "Duplica Formula") {
            int id_To_Search;
            id_To_Search = array_ID.get(posizFormula);
            Intent i = new Intent(this, DuplicaFormula.class);
            i.putExtra("IdFrm", id_To_Search);
            startActivity(i);
            return true;
        }
        else if (item.getTitle() == "Elimina Formula") {
            final int id_To_Search;
            id_To_Search = array_ID.get(posizFormula);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.deleteContact)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mydb.deleteFormula(id_To_Search);
                            Toast.makeText(getApplicationContext(), "Cancellazione Avvenuta",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Formule.class);
                            intent.putExtra("Pino", "ciao");
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });

            AlertDialog d = builder.create();
            d.setTitle("Sei sicuro di eliminare?");
            d.show();
            return true;
        }
        else {  return false; }
    }
}

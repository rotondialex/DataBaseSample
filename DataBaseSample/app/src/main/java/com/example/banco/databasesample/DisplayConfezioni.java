package com.example.banco.databasesample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DisplayConfezioni extends AppCompatActivity {
    private DBHelper mydb ;

    TextView name ;
    TextView ultimod;
    TextView pezzi;
    TextView litri,costolitro;
    GridView obj;
    int id_To_Update = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_conf);
        getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String pez,ltconf;
        name = (TextView) findViewById(R.id.editTextName);
        pezzi = (TextView) findViewById(R.id.editPezzi);
        litri = (TextView) findViewById(R.id.editLitri);
        ultimod = (TextView) findViewById(R.id.UltimaModifica);
        costolitro = (TextView) findViewById(R.id.CostoLitro);
        mydb = new DBHelper(this,"Hichem.db");

        Bundle extras = getIntent().getExtras();
        Button b2 = (Button)findViewById(R.id.aggiungi);
        b2.setVisibility(View.INVISIBLE);
        if(extras !=null) {
            int Value = extras.getInt("idconf");

            if(Value>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getConfezione(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String nam = rs.getString(rs.getColumnIndex(DBHelper.CONF_COL_NAME));
                pez=rs.getString(rs.getColumnIndex(DBHelper.CONF_COL_QCT));
                if(pez.equals("")){pez="0";}
                ltconf=rs.getString(rs.getColumnIndex(DBHelper.CONF_COL_QPLT));
                if(ltconf.equals("")){ltconf="0";}
                String ultmod = rs.getString(rs.getColumnIndex(DBHelper.CONF_COL_CAMPO1));

                if (!rs.isClosed())  {
                    rs.close();
                }
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);

                b2.setVisibility(View.INVISIBLE);

                name.setText((CharSequence)nam);
                name.setFocusable(false);
                name.setClickable(false);

                pezzi.setText((CharSequence)pez);
                pezzi.setFocusable(false);
                pezzi.setClickable(false);

                litri.setText((CharSequence)ltconf);
                litri.setFocusable(false);
                litri.setClickable(false);
                ultimod.setText(ultmod);
                Double Costo=(double)((int)(mydb.CostoAlLitroConfezione(id_To_Update,Double.parseDouble(ltconf))*100))/100;
                costolitro.setText(String.valueOf(Costo));
                ArrayList<String> array_list = mydb.getCompConf(id_To_Update);


                ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);

                obj = (GridView)findViewById(R.id.componenti);
                obj.setAdapter(arrayAdapter);
                obj.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
                    @Override
                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        //Creazione dell'alert dialog
                        final Integer index=arg2;
                        final ArrayList<Integer> arrayIDmpr = mydb.getIDCompConf(id_To_Update);
                        final Integer idcomponente=arrayIDmpr.get(index/2);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                DisplayConfezioni.this);
                        //imposto il titolo ed il messaggio dell'alert dialog, prendendolo dalle Resources
                        alertDialogBuilder
                                .setTitle("Elimina");
                        alertDialogBuilder
                                .setMessage("Vuoi cancellare questo componente?");
                        //questo dialog non verrà annullato tappando sullo schermo al di fuori del dialog stesso
                        alertDialogBuilder.setCancelable(false);
                        //imposto il clicklistener del pulsante "yes" ed il suo testo sempre dalle Resources
                        alertDialogBuilder.setPositiveButton(R.string.yes,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            DialogInterface dialogInterface,
                                            int i) {
                                        //rimuovo l'oggetto dalla posizione "position" (quella attualmente selezionata)
                                        Bundle Componenti= getIntent().getExtras();// TODO
                                        mydb.deleteComponenteConf(id_To_Update,idcomponente);
                                        mydb.updateConfezione(id_To_Update,name.getText().toString(),pezzi.getText().toString(),litri.getText().toString());
                                        Toast.makeText(getApplicationContext(), "Cancellazione Avvenuta",
                                                Toast.LENGTH_SHORT).show();
                                        Bundle dataBundle = new Bundle();
                                        dataBundle.putInt("idconf", id_To_Update);
                                        Intent intent = new Intent(getApplicationContext(),DisplayConfezioni.class);
                                        intent.putExtras(dataBundle);
                                        startActivity(intent);

                                    }
                                });
                        alertDialogBuilder.setNegativeButton(R.string.no,
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

                        return true;
                    }
                });

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            int Value = extras.getInt("idconf");
            if(Value>0){
                getMenuInflater().inflate(R.menu.display_confezioni, menu);
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
                Button b2 = (Button)findViewById(R.id.aggiungi);
                b2.setVisibility(View.VISIBLE);

                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);

                pezzi.setEnabled(true);
                pezzi.setFocusableInTouchMode(true);
                pezzi.setClickable(true);

                litri.setEnabled(true);
                litri.setFocusableInTouchMode(true);
                litri.setClickable(true);

                Calendar today= Calendar.getInstance();
                String oggi= DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY).format(today.getTime());
                ultimod.setText(oggi);

                return true;
            case R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteConfezione(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Cancellazione Avvenuta",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),Confezioni.class);
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

            case R.id.Aggiungi_Comp:
                Intent i = new Intent(this, ImbQuant.class);
                i.putExtra("IdConf",id_To_Update);
                startActivityForResult(i, 1);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
    public void Aggiungi_Imb(View view){
        Intent i = new Intent(this, ImbQuant.class);
        i.putExtra("IdConf",id_To_Update);
        startActivityForResult(i, 1);

    }
    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        Bundle Componenti= getIntent().getExtras();// TODO
        if(extras !=null) {
            int Value = extras.getInt("idconf");
            if(Value>0){
                if( mydb.updateConfezione(id_To_Update,name.getText().toString(),pezzi.getText().toString(),litri.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Aggiornato", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "Non Aggiornato", Toast.LENGTH_SHORT).show();
                }
            } else{
                id_To_Update=mydb.insertConf(name.getText().toString(),pezzi.getText().toString(),litri.getText().toString(),Componenti);
                Toast.makeText(getApplicationContext(), "Aggiunto",
                Toast.LENGTH_SHORT).show();

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("idconf", id_To_Update);

                Intent intent = new Intent(getApplicationContext(),DisplayConfezioni.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){


                Integer quanto=data.getIntExtra("result",0);
                ArrayList<String> array_list = mydb.getCompConf(id_To_Update);
                ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_list);



                    obj = (GridView) findViewById(R.id.componenti);
                    obj.setAdapter(arrayAdapter);
                    obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                            // TODO Auto-generated method stub
                            //int id_To_Search = arg2;
                            //id_To_Search=array_ID.get(id_To_Search);
                            // Bundle dataBundle = new Bundle();
                            //dataBundle.putInt("idfrm", id_To_Search);

                            //Intent intent = new Intent(getApplicationContext(),DisplayFormule.class);

                            //intent.putExtras(dataBundle);
                            //startActivity(intent);
                        }
                    });

                mydb.updateConfezione(id_To_Update,name.getText().toString(),pezzi.getText().toString(),litri.getText().toString());
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}

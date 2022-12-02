package com.example.banco.kalodikulo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class NuovaVocePasto extends AppCompatActivity {
    TextView Giorno;TextView Pasto;TextView Quantita;
    DBHelper mydb;
    Integer IDalim;
    Integer IDpasto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inserisci_voce_in_dieta);
        getSupportActionBar().setHomeButtonEnabled(true);
        TextView Titolo=(TextView)findViewById(R.id.labeltitoloinserisci);
        Titolo.setText("Inserisci in pasto");
        Bundle extras = getIntent().getExtras();
        String giorno=extras.getString("giorno");
        IDpasto=extras.getInt("idpasto");
        Giorno=(TextView) findViewById(R.id.giorno);
        Pasto=(TextView) findViewById(R.id.pasto);
        Giorno.setText(giorno);
        mydb = new DBHelper(this);
        Cursor rs=mydb.getPasto(IDpasto);
        rs.moveToFirst();
        String nomepasto=rs.getString(rs.getColumnIndex(DBHelper.PASTI_CMP_NOME));
        Pasto.setText(nomepasto);
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
            case android.R.id.home:
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", IDpasto);
                dataBundle.putString("chimichiama", "pasti");
                Intent intent = new Intent(getApplicationContext(),DisplayPasto.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void ottienialimento (View view){
        Toast.makeText(getApplicationContext(), "ATTENDERE CARICAMENTO ALIMENTI", Toast.LENGTH_LONG).show();
        Bundle dataBundle = new Bundle();
        dataBundle.putString("chimichiama", "ottienialimento");
        Intent y = new Intent(getApplicationContext(),Alimenti.class);
        y.putExtras(dataBundle);
        startActivityForResult(y, 1);
    }
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        TextView alimento = (TextView) findViewById(R.id.alimento);
        mydb = new DBHelper(this);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle extras = data.getExtras();
                IDalim=extras.getInt("id");
                Cursor rs = mydb.getAlimento(IDalim);
                rs.moveToFirst();
                String nam = rs.getString(rs.getColumnIndex(DBHelper.ALIM_CMP_NOME));
                alimento.setText(nam);
            }
        }
    }
    public void inserisciindieta(View view){
        Integer cpc;
        mydb = new DBHelper(this);
        Quantita=(TextView) findViewById(R.id.editqta);
        Giorno=(TextView) findViewById(R.id.giorno);
        if (IDalim!=null)
        {

            if (mydb.insertVociPasto(IDpasto,IDalim,Double.parseDouble(Quantita.getText().toString()),"",""))
                Toast.makeText(getApplicationContext(), "Inserito", Toast.LENGTH_SHORT).show();
            else Toast.makeText(getApplicationContext(), "Non Inserito", Toast.LENGTH_SHORT).show();
        }
    }
}
package com.example.banco.kalodikulo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.view.KeyEvent.ACTION_UP;

public class NuovaVoceDieta extends AppCompatActivity {
    TextView Giorno;TextView Pasto;TextView Quantita;
    DBHelper mydb;
    Integer IDalim;
    String pasto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inserisci_voce_in_dieta);
        getSupportActionBar().setHomeButtonEnabled(true);
        Bundle extras = getIntent().getExtras();
        String giorno=extras.getString("giorno");
        pasto=extras.getString("pasto");
        Giorno=(TextView) findViewById(R.id.giorno);
        Pasto=(TextView) findViewById(R.id.pasto);
        Giorno.setText(giorno);
        Pasto.setText(pasto);
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
            if (mydb.insertDieta(IDalim,Double.parseDouble(Quantita.getText().toString()),cpc,Giorno.getText().toString(),"",""))
                Toast.makeText(getApplicationContext(), "Inserito", Toast.LENGTH_SHORT).show();
            else Toast.makeText(getApplicationContext(), "Non Inserito", Toast.LENGTH_SHORT).show();
        }
    }
}
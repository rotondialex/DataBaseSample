package com.example.banco.databasesample;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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

public class ModifPercent extends AppCompatActivity {

    DBHelper mydb;
    TextView ProdSel;
    TextView Percent;
    TextView idmpr;
    TextView titolo,labelconf,labelperc;
    Integer idcomp;
    Integer idfrm;
    String chiChiama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chg_percent);
        getSupportActionBar().setHomeButtonEnabled(true);
        titolo= (TextView) findViewById(R.id.textView);
        labelconf= (TextView) findViewById(R.id.textView18);
        labelperc= (TextView) findViewById(R.id.textView20);
        ProdSel= (TextView) findViewById(R.id.EditComp);
        Percent= (TextView) findViewById(R.id.EditQuant);
        idmpr= (TextView) findViewById(R.id.IdMpr);
        mydb = new DBHelper(this,"Hichem.db");
        Percent.setText("0");
        Bundle extras = getIntent().getExtras();
        chiChiama=extras.getString("qualeattivita");
        idfrm=extras.getInt("IdFrm");
        idcomp=extras.getInt("IdComp");
        idmpr.setText(String.valueOf(idcomp));

        if (chiChiama.equals("Modifica")) {
            titolo.setText("Nuova Percentuale");
            labelconf.setText("Componente Selezionato");
            labelperc.setText("Percentuale");
            Cursor rs = mydb.getMateriaprima(idcomp);
            rs.moveToFirst();
            ProdSel.setText(rs.getString(rs.getColumnIndex(DBHelper.MP_COL_NOME)));

        }
        if (chiChiama.equals("ModificaQuant")) {
            titolo.setText("Nuova Quantità");
            labelconf.setText("Confezione Selezionata");
            labelperc.setText("Quantità");
            Cursor rs = mydb.getConfezione(idcomp);
            rs.moveToFirst();
            ProdSel.setText(rs.getString(rs.getColumnIndex(DBHelper.CONF_COL_NAME)));

        }


    }


    public void ModificaQuant(View view) {
        if (titolo.getText().toString().equals("Nuova Percentuale")) {
            if (Integer.parseInt(idmpr.getText().toString()) > -1) {

                if (mydb.updateComponente(idfrm, Integer.parseInt(idmpr.getText().toString()), Double.parseDouble(Percent.getText().toString()))) {
                    Toast.makeText(getApplicationContext(), "Aggiornato",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Non Aggiornato",
                            Toast.LENGTH_SHORT).show();
                }
                Intent returnIntent = new Intent();
                returnIntent.putExtra("componente", ProdSel.getText().toString());
                returnIntent.putExtra("percentuale", Percent.getText().toString());
                returnIntent.putExtra("idmpr", idmpr.getText().toString());
                returnIntent.putExtra("qualeattivita", "Modifica");
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

            }
        }
        if (titolo.getText().toString().equals("Nuova Quantità")) {
            if (Integer.parseInt(idmpr.getText().toString()) > -1) {

                if (mydb.UpdateConfInFormula(idfrm, Integer.parseInt(idmpr.getText().toString()), Double.parseDouble(Percent.getText().toString()))) {
                    Toast.makeText(getApplicationContext(), "Aggiornato",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Non Aggiornato",
                            Toast.LENGTH_SHORT).show();
                }
                Intent returnIntent = new Intent();
                returnIntent.putExtra("componente", ProdSel.getText().toString());
                returnIntent.putExtra("percentuale", Percent.getText().toString());
                returnIntent.putExtra("idmpr", idmpr.getText().toString());
                returnIntent.putExtra("qualeattivita", "ModificaQuant");
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

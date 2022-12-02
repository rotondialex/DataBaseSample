package com.example.banco.databasesample;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Banco on 30/03/2017.
 */

public class DuplicaFormula extends AppCompatActivity {

    DBHelper mydb;

    TextView  idFRM, nomeOriginale, inventTRUEfalse;
    EditText nuovoNome;
    Integer idfrm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duplicaformula);
        getSupportActionBar().setHomeButtonEnabled(true);
        nuovoNome= (EditText) findViewById(R.id.EditNuovoNome);
        nomeOriginale= (TextView) findViewById(R.id.textFRMoriginale);
        inventTRUEfalse= (TextView) findViewById(R.id.textInvent);
        idFRM= (TextView) findViewById(R.id.IdFRM);
        mydb = new DBHelper(this,"Hichem.db");
        Bundle extras = getIntent().getExtras();

        idfrm=extras.getInt("IdFrm");
        idFRM.setText(String.valueOf(idfrm));
        Cursor rs = mydb.getFormula(idfrm);
        rs.moveToFirst();

        String nam = rs.getString(rs.getColumnIndex(DBHelper.FORM_COL_NAME));
        String inventSINO = rs.getString(rs.getColumnIndex(DBHelper.FORM_COL_INV));
        nomeOriginale.setText("Formula Originale ->  "+nam.toString());
        inventTRUEfalse.setText(inventSINO.toString());
        rs.close();

    }


    public void Duplica(View view) {
        if (nuovoNome.getText().toString().equals("")) {


        }
        else
        {
            Bundle Componenti=getIntent().getExtras();
            Integer newIdFRM;
            newIdFRM=mydb.insertFormula(nuovoNome.getText().toString(),inventTRUEfalse.getText().toString(),Componenti);
            mydb.duplicaFormula(idfrm,newIdFRM);
            Intent intent = new Intent(this, Formule.class);
            intent.putExtra("Pino", "ciao");
            startActivity(intent);
            }
    }
    public void Annulla(View view) {
        finish();
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

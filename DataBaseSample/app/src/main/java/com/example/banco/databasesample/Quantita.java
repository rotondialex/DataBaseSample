package com.example.banco.databasesample;

/**
 * Created by Banco on 16/03/2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Quantita extends Activity {
    TextView quan;
    TextView unmis;
    String chimichiama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantita);
        unmis= (TextView) findViewById(R.id.editUM);
        Bundle extras = getIntent().getExtras();
        chimichiama=extras.getString("qualeattivita");
        if (chimichiama.equals("Produzione")){
            unmis.setText("Q.li");
            unmis.setFocusable(false);
        }
        if (chimichiama.equals("Ordine")){
            unmis.setText("");
            unmis.setFocusable(true);
        }
    }

    public void RestituisciQuant(View view) {
        quan = (TextView) findViewById(R.id.editQuant);
        unmis= (TextView) findViewById(R.id.editUM);
        Double result=Double.parseDouble(quan.getText().toString());
        String UnitaMis=unmis.getText().toString();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",result);
        returnIntent.putExtra("unmis",UnitaMis);
        returnIntent.putExtra("qualeattivita",chimichiama);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}

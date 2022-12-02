package com.example.banco.databasesample;

/**
 * Created by Banco on 16/03/2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CostoConfezione extends Activity {
    TextView Titolo, testo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costoconfez);
        Bundle extras = getIntent().getExtras();
        Titolo = (TextView) findViewById(R.id.titolo);
        testo= (TextView) findViewById(R.id.testo);
        Titolo.setText((extras.get("Titolo").toString()));
        testo.setText((extras.get("Testo").toString()));
    }
    protected void Close(View view){
        finish();
    }
}

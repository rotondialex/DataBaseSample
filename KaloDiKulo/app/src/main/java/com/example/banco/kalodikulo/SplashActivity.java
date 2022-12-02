package com.example.banco.kalodikulo;

/**
 * Created by Banco on 14/02/2018.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;


import static com.example.banco.kalodikulo.MainActivity.giornodioggi;

public class SplashActivity extends AppCompatActivity {
    DBHelper mydb;
    public ArrayList<String> array_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.splashscreeen);
        super.onCreate(savedInstanceState);
        giornodioggi= Calendar.getInstance();
        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};


        int permsRequestCode = 200;
        if (shouldAskPermission()) {
            requestPermissions(perms, permsRequestCode);
        }
        final TextView loading= (TextView)findViewById(R.id.LabelLoading);
        final Button continua=(Button)findViewById(R.id.ButContinua);
        try{
            InputStream fis = getResources().openRawResource(R.raw.tabalim);
            //FileInputStream fis= new FileInputStream(f);
            //creiamo il nostro lettore di buffer
            BufferedReader br= new BufferedReader(new InputStreamReader(fis));
            //creiamo una variabile dove salvare di volta in volta le varie righe dello stream
            String line="";
            //creiamo un ciclo che va avanti fino a che non legge tutto lo stream
            //la funzione readLine ritorna una linea dallo stream, ovvero una stringa fino al prossimo \n
            mydb = new DBHelper(this);
            array_list=mydb.getAllalimenti();
            if (array_list.isEmpty()) {
                while ((line = br.readLine()) != null) {
                    //stampiamo la riga
                    String[] Campi = line.split(";");
                    if (!Campi[1].equals("Kcal")) {


                        if (mydb.insertAlimento(Campi[0], Campi[3], Double.parseDouble(Campi[1]),
                                Double.parseDouble(Campi[2]), Double.parseDouble(Campi[6]),
                                Double.parseDouble(Campi[7]), Double.parseDouble(Campi[5]), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, "0", "0")){
                        }


                    }
                }
            }
        } catch (IOException e){}

                continua.setVisibility(View.VISIBLE);
                loading.setVisibility(View.INVISIBLE);


    }
    public void LanciaMain (View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    private boolean shouldAskPermission(){

        return(Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP_MR1);

    }
}

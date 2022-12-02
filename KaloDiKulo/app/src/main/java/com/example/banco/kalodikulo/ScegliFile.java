package com.example.banco.kalodikulo;



import android.app.*;
import android.database.Cursor;
import android.os.*;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.DoubleBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class ScegliFile extends Activity
{
    TextView percorsofile;
    DBHelper mydb;
    public ArrayList<String> array_list;

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sceglifile);
        percorsofile= (TextView) findViewById(R.id.testofile);
        mydb = new DBHelper(this);

    }
    public void FILEselected(View view) {
        Intent y = new Intent(this, AndroidExplorer.class);
        startActivityForResult(y, 1);
    }
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        percorsofile= (TextView) findViewById(R.id.testofile);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                percorsofile.setText(data.getStringExtra("file"));
                File f= new File("tabalimenticsv");
                try{
                    InputStream fis = getResources().openRawResource(R.raw.tabalim);
                    //FileInputStream fis= new FileInputStream(f);
                    //creiamo il nostro lettore di buffer
                    BufferedReader br= new BufferedReader(new InputStreamReader(fis));
                    //creiamo una variabile dove salvare di volta in volta le varie righe dello stream
                    String line="";
                    //creiamo un ciclo che va avanti fino a che non legge tutto lo stream
                    //la funzione readLine ritorna una linea dallo stream, ovvero una stringa fino al prossimo \n
                    array_list=mydb.getAllalimenti();
                    if (array_list.isEmpty()) {
                        while ((line = br.readLine()) != null) {
                            //stampiamo la riga
                            String[] Campi = line.split(";");
                            if (!Campi[1].equals("Kcal")) {


                                if (mydb.insertAlimento(Campi[0], Campi[3], Double.parseDouble(Campi[1]),
                                        Double.parseDouble(Campi[2]), Double.parseDouble(Campi[6]),
                                        Double.parseDouble(Campi[7]), Double.parseDouble(Campi[5]), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, "0", "0"))
                                    ;

                            }
                        }
                    }
                } catch (IOException e){}

            }
        }
    }

}

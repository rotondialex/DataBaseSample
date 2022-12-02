package com.example.banco.databasesample;

/**
 * Created by Banco on 08/03/2017.
 */


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Date;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Inventario extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "MESSAGE";
    private ListView obj;
    private CheckBox siNOquant, siNOprezzi;
    public ArrayList<String> arrayMatPrime, arrayImb, arrayPF,arrayConfDiPF;
    public ArrayList<Integer> array_ID,array_ID_MP,array_ID_IMB;
    private ArrayAdapter arrayAdapter;
    public boolean siQuant,siPrezzo;
    DBHelper mydb;
    public EditText cerca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);
        getSupportActionBar().setHomeButtonEnabled(true);
        siNOquant=(CheckBox) findViewById(R.id.checkBox);
        siNOquant.setOnCheckedChangeListener(new myCheckBoxChangeQuantita());
        siNOprezzi=(CheckBox) findViewById(R.id.checkBoxPrezzi);
        siNOprezzi.setOnCheckedChangeListener(new myCheckBoxChangePrezzi());
        // Fine routine ordinamento

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
    class myCheckBoxChangePrezzi implements CheckBox.OnCheckedChangeListener
    {

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // TODO Auto-generated method stub

            // Toast.makeText(CheckBoxCheckedDemo.this, &quot;Checked =&gt; &quot;+isChecked, Toast.LENGTH_SHORT).show();

            if(isChecked) {
                siNOquant=(CheckBox) findViewById(R.id.checkBox);
                siNOquant.setChecked(true);
            }
        }
    }
    class myCheckBoxChangeQuantita implements CheckBox.OnCheckedChangeListener
    {

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // TODO Auto-generated method stub

            // Toast.makeText(CheckBoxCheckedDemo.this, &quot;Checked =&gt; &quot;+isChecked, Toast.LENGTH_SHORT).show();

            if(!isChecked) {
                siNOprezzi=(CheckBox) findViewById(R.id.checkBoxPrezzi);
                siNOprezzi.setChecked(false);
            }
        }
    }
    public void esportaCosti (View view) {
        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};

        int permsRequestCode = 200;
        if (shouldAskPermission()) {
            requestPermissions(perms, permsRequestCode);
        }
        mydb = new DBHelper(this, "Hichem.db");
        arrayPF = mydb.getAllFormule();
        array_ID=mydb.getAllFORMID();
        // Ordino Alfabeticamente

        String Supporto;
        Integer r,i,Supp;
        String [] Perordinare=arrayPF.toArray(new String[0]);
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
                    Supporto=arrayPF.get(i);
                    arrayPF.set(i,arrayPF.get(i+1));
                    arrayPF.set(i+1,Supporto);
                    Supp=array_ID.get(i);
                    array_ID.set(i,array_ID.get(i+1));
                    array_ID.set(i+1,Supp);
                }
                i=i+1;
            }
        }
        try {
            File sd = Environment.getExternalStorageDirectory();
            Calendar today= Calendar.getInstance();
            String oggi= DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY).format(today.getTime());
            String filenamePF="CostiProdottiFiniti"+oggi+".txt";
            String path = sd.getAbsolutePath() + "";
            File PF = new File(path,filenamePF);
            FileWriter fw = null;
            BufferedWriter bw = null;
            if (PF.exists()) PF.delete();
            PF.createNewFile();

            String [] ListaPF=arrayPF.toArray(new String[0]),ListaConf;
            String riga;
            i=0;
            fw = new FileWriter(PF, true);
            bw = new BufferedWriter(fw);
            bw.write("COSTI PRODOTTI FINITI AL "+ oggi+"\n\n");
            while (i<ListaPF.length-1) {

                Supp=array_ID.get(i);
                arrayConfDiPF=mydb.getCONFdiFormulaINV(Supp);
                ListaConf=arrayConfDiPF.toArray(new String[0]);

                r=0;
                while (r<ListaConf.length-1) {
                    Double CostoForm=(double)((int)(mydb.CostoAlLitroFormula(Supp)*100))/100;
                    final ArrayList<Integer> arrayIDmpr = mydb.getIDCONFdiFormula(Supp);
                    Cursor rs = mydb.getConfezione(Integer.parseInt(ListaConf[r+2]));
                    rs.moveToFirst();
                    String nam = rs.getString(rs.getColumnIndex(DBHelper.CONF_COL_NAME));
                    String pez=rs.getString(rs.getColumnIndex(DBHelper.CONF_COL_QCT));
                    String ltconf = rs.getString(rs.getColumnIndex(DBHelper.CONF_COL_QPLT));
                    Double Costo=(double)((int)(mydb.CostoAlLitroConfezione(Integer.parseInt(ListaConf[r+2]),Double.parseDouble(ltconf))*100))/100;
                    Costo=Costo+CostoForm;
                    Costo=(double)((int)(Costo*100))/100;
                    Double costoCF=Costo*Double.parseDouble(ltconf);
                    costoCF=(double)((int)(costoCF*100))/100;
                    riga = ListaPF[i] +" "+ListaConf[r];
                    riga =riga+"||"+costoCF.toString()+"€||\n";
                    bw.write(riga);
                    r=r+3;
                }
                i=i+1;
            }
            bw.close();
            fw.close();
        }
        catch (Exception e) {
        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
         }
    }
    public void esportaElenchi (View view){
        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};

        int permsRequestCode = 200;
        if (shouldAskPermission()) {
            requestPermissions(perms, permsRequestCode);
        }
        mydb = new DBHelper(this,"Hichem.db");


        arrayMatPrime = mydb.getAllMaterieprime();
        array_ID_MP = mydb.getAllMPID();
        // Ordino Alfabeticamente
        String Supporto;
        Integer r,i,Supp;
        String [] Perordinare=arrayMatPrime.toArray(new String[0]);
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
                    Supporto=arrayMatPrime.get(i);
                    arrayMatPrime.set(i,arrayMatPrime.get(i+1));
                    arrayMatPrime.set(i+1,Supporto);
                    Supp=array_ID_MP.get(i);
                    array_ID_MP.set(i,array_ID_MP.get(i+1));
                    array_ID_MP.set(i+1,Supp);
                }
                i=i+1;
            }
        }
        arrayImb = mydb.getAllImballaggi();
        array_ID_IMB = mydb.getAllIMBID();
        // Ordino Alfabeticamente

        Perordinare=arrayImb.toArray(new String[0]);
        Nonfinito=true;
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
                    Supporto=arrayImb.get(i);
                    arrayImb.set(i,arrayImb.get(i+1));
                    arrayImb.set(i+1,Supporto);
                    Supp=array_ID_IMB.get(i);
                    array_ID_IMB.set(i,array_ID_IMB.get(i+1));
                    array_ID_IMB.set(i+1,Supp);

                }
                i=i+1;
            }
        }
        arrayPF = mydb.getAllFormule();
        array_ID=mydb.getAllFORMID();
        // Ordino Alfabeticamente

        Perordinare=arrayPF.toArray(new String[0]);
        Nonfinito=true;
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
                    Supporto=arrayPF.get(i);
                    arrayPF.set(i,arrayPF.get(i+1));
                    arrayPF.set(i+1,Supporto);
                    Supp=array_ID.get(i);
                    array_ID.set(i,array_ID.get(i+1));
                    array_ID.set(i+1,Supp);
                }
                i=i+1;
            }
        }
        try {
            File sd = Environment.getExternalStorageDirectory();
            Calendar today= Calendar.getInstance();
            String oggi= DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY).format(today.getTime());
            //MATERIE PRIME
            String filenameMatPrime="MateriePrime"+oggi+".txt";
            String path = sd.getAbsolutePath() + "";
            File matPrime = new File(path,filenameMatPrime);
            if (matPrime.exists()) matPrime.delete();
            matPrime.createNewFile();

            FileWriter fw = null;
            BufferedWriter bw = null;
            String [] ListMatPrime=arrayMatPrime.toArray(new String[0]);
            String riga;
            i=0;r=0;
            fw = new FileWriter(matPrime, true);
            bw = new BufferedWriter(fw);
            bw.write("MATERIE PRIME\n\n");
            siQuant=siNOquant.isChecked();
            siPrezzo=siNOprezzi.isChecked();
            while (i<ListMatPrime.length-1) {
                r=array_ID_MP.get(i);
                Cursor rs=mydb.getMateriaprima(r);
                rs.moveToFirst();
                Double qua = rs.getDouble(rs.getColumnIndex(DBHelper.MP_COL_QUANTITA));
                Double prez=rs.getDouble(rs.getColumnIndex(DBHelper.MP_COL_PREZZO));
                rs.close();
                siQuant=siNOquant.isChecked();
                riga=ListMatPrime[i];
                if (siQuant) {
                    riga=riga+";" +qua.toString();
                    if (siPrezzo){
                        riga=riga+";"+prez.toString()+";\n";
                    }
                    else{
                        riga=riga+";\n";
                    }
                }
                else{
                    riga=riga+"  _________\n\n";
                }

                    bw.write(riga);

                i=i+1;
            }
            bw.close();
            fw.close();
            Toast.makeText(getApplicationContext(), filenameMatPrime, Toast.LENGTH_LONG).show();
            //IMBALLAGGI
            String filenameiMB="Imballaggi"+oggi+".txt";
            path = sd.getAbsolutePath() + "";
            File matImb = new File(path,filenameiMB);
            if (matImb.exists()) matImb.delete();
            matImb.createNewFile();

            String [] ListImb=arrayImb.toArray(new String[0]);
            i=0;
            fw = new FileWriter(matImb, true);
            bw = new BufferedWriter(fw);
            bw.write("IMBALLAGGI\n\n");
            while (i<ListImb.length-1) {
                r=array_ID_IMB.get(i);
                Cursor rs=mydb.getImballaggio(r);
                rs.moveToFirst();
                Double qua = rs.getDouble(rs.getColumnIndex(DBHelper.IMB_COL_QUANTITA));
                Double prez = rs.getDouble(rs.getColumnIndex(DBHelper.IMB_COL_PREZZO));
                rs.close();
                riga=ListImb[i];
                if (siQuant) {
                    riga=riga+";" +qua.toString();
                    if (siPrezzo){
                        riga=riga+";"+prez.toString()+";\n";
                    }
                    else{
                        riga=riga+";\n";
                    }
                }
                else{
                    riga=riga+"  _________\n\n";
                }


                bw.write(riga);

                i=i+1;
            }
            bw.close();
            fw.close();
            Toast.makeText(getApplicationContext(), filenameiMB, Toast.LENGTH_LONG).show();
            //PRODOTTI FINITI
            String filenamePF="ProdottiFiniti"+oggi+".txt";
            path = sd.getAbsolutePath() + "";
            File PF = new File(path,filenamePF);
            if (PF.exists()) PF.delete();
            PF.createNewFile();

            String [] ListaPF=arrayPF.toArray(new String[0]),ListaConf;

            i=0;
            fw = new FileWriter(PF, true);
            bw = new BufferedWriter(fw);
            bw.write("PRODOTTI FINITI\n\n");
            while (i<ListaPF.length-1) {

                Supp=array_ID.get(i);
                arrayConfDiPF=mydb.getCONFdiFormulaINV(Supp);
                ListaConf=arrayConfDiPF.toArray(new String[0]);

                r=0;
                while (r<ListaConf.length-1) {
                    Double CostoForm=(double)((int)(mydb.CostoAlLitroFormula(Supp)*100))/100;
                    final ArrayList<Integer> arrayIDmpr = mydb.getIDCONFdiFormula(Supp);
                    Cursor rs = mydb.getConfezione(Integer.parseInt(ListaConf[r+2]));
                    rs.moveToFirst();
                    String nam = rs.getString(rs.getColumnIndex(DBHelper.CONF_COL_NAME));
                    String pez=rs.getString(rs.getColumnIndex(DBHelper.CONF_COL_QCT));
                    String ltconf = rs.getString(rs.getColumnIndex(DBHelper.CONF_COL_QPLT));
                    Double Costo=(double)((int)(mydb.CostoAlLitroConfezione(Integer.parseInt(ListaConf[r+2]),Double.parseDouble(ltconf))*100))/100;
                    Costo=Costo+CostoForm;
                    Costo=(double)((int)(Costo*100))/100;
                    Double costoCF=Costo*Double.parseDouble(ltconf);
                    costoCF=(double)((int)(costoCF*100))/100;
                    riga = ListaPF[i] +" "+ListaConf[r];
                    if (siQuant) {
                        riga =riga+";"+ListaConf[r+1];
                        if (siPrezzo){
                            riga =riga+";"+costoCF.toString()+";\n";
                        }
                        else{
                            riga =riga+";\n";
                        }
                    }
                    else{
                        riga =riga+ "  _________\n\n";
                    }


                    bw.write(riga);
                    r=r+3;
                }
                i=i+1;
            }
            bw.close();
            fw.close();
            Toast.makeText(getApplicationContext(), filenamePF, Toast.LENGTH_LONG).show();

            //} else {
            //   Log.e("Permission denied", "Can't write to SD card, add permission");
            // }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }
    public void valorizzaInventario (View view){

        Intent intent = new Intent(this, TestoEmail.class);

        startActivity(intent);
    }
    private boolean shouldAskPermission(){

        return(Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP_MR1);

    }
}

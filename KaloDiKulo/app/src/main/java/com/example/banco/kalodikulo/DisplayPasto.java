package com.example.banco.kalodikulo;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class DisplayPasto extends AppCompatActivity {

    public ArrayList<String> array_list;
    public ArrayList<Integer> array_ID;
    private ArrayAdapter arrayAdapter;
    private Button txt;
    private static final Integer BUTTONIDCOLAZIONE=10;
    private static final Integer BUTTONIDPRANZO=20;
    private static final Integer BUTTONIDCENA=30;
    private static  Integer[] IDbuttonMOD;
    private static Integer[] IDbuttonDEL;
    private static Integer[] IDAlimOggi;
    private static Integer[] IDeditqta;
    private static Integer numVociOggi;
    private static String chimichiama;
    //public Calendar today;
    TextView Giorno;
    DBHelper mydb;
    private static int id_To_Update = 0;
    private static String nomepasto="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_pasto);
        getSupportActionBar().setHomeButtonEnabled(true);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        chimichiama=extras.getString("chimichiama");
        if(extras !=null) {
            int Value = extras.getInt("id");

            if (Value > 0) {
                //means this is the view part not the add contact part.
                TextView nome = (TextView) findViewById(R.id.editNome);
                Cursor rs = mydb.getPasto(Value);
                rs.moveToFirst();
                id_To_Update = Value;
                Button butAddPasto= (Button) findViewById(R.id.BtnNewPasto);
                butAddPasto.setVisibility(View.INVISIBLE);
                nomepasto= rs.getString(rs.getColumnIndex(DBHelper.PASTI_CMP_NOME));
                nome.setText(nomepasto);
                riempipasto(id_To_Update,nomepasto);
            }
            else
            {
                TextView nome = (TextView) findViewById(R.id.editNome);
                nome.setText("");
                Button butAddPasto= (Button) findViewById(R.id.BtnNewPasto);
                butAddPasto.setVisibility(View.VISIBLE);
                Button butAddVoce= (Button) findViewById(R.id.BtnPasto);
                butAddVoce.setVisibility(View.INVISIBLE);
                Button butsave= (Button) findViewById(R.id.ButSalva);
                butsave.setVisibility(View.INVISIBLE);
                Button butann= (Button) findViewById(R.id.ButAnnulla);
                butann.setVisibility(View.INVISIBLE);
                TextView totali = (TextView) findViewById(R.id.TotaliPasto);
                totali.setVisibility(View.INVISIBLE);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");

            if (Value > 0) {
                getMenuInflater().inflate(R.menu.display_pasto, menu);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            case R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Elimina")
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deletePasto(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Cancellazione Avvenuta",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),Pasti.class);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public boolean nuova_voce(String giorno, String pasto) {

        Bundle dataBundle = new Bundle();
        dataBundle.putInt("idpasto", id_To_Update);
        dataBundle.putString("giorno", pasto);

        Intent intent = new Intent(getApplicationContext(),NuovaVocePasto.class);
        intent.putExtras(dataBundle);

        startActivity(intent);
        return true;
    }

    public void riempipasto(Integer idpasto, String nomep){
        TableRow row;
        TextView TotaliParz;

        mydb = new DBHelper(this);
        Cursor VOCIPasto =mydb.getVociPasto(idpasto);
        numVociOggi=VOCIPasto.getCount();
        IDbuttonMOD= new Integer[numVociOggi];
        IDbuttonDEL= new Integer[numVociOggi];
        IDAlimOggi= new Integer[numVociOggi];
        IDeditqta= new Integer[numVociOggi];
        Button btn,btn2;
        Integer arrotonda;
        Integer i=0;
        // sezione Colazione
        TableLayout tl= (TableLayout)findViewById(R.id.tablePasto);
        btn= (Button) findViewById(R.id.BtnPasto);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuova_voce("oggi","PASTO");
            }
        });

        Integer numerorighe=tl.getChildCount();
        Double ToaleKcalColaz=0.0;Double ToaleGrassiColaz=0.0;Double ToaleZuccheriColaz=0.0;
        Double ToaleProteineColaz=0.0;Double ToaleFibreColaz=0.0;

        for(int y=0; y < numerorighe; y++) tl.removeViewAt(0);
        Cursor Colazione =mydb.getVociPasto(idpasto);
        Colazione.moveToFirst();
        // Creo riga Intestazioni
        row = new TableRow(this);
        tl.addView(row);
        Integer margineleft=15;
        if (Colazione.getCount()==0) margineleft=70;
        TextView base=new TextView(this);
        base.setText("Alimento");
        base.setTextSize(12);
        base.setMaxWidth(290);
        base.setMaxHeight(50);
        base.setTextColor(getColor(R.color.nero));
        row.addView(base);
        base=new TextView(this);
        base.setText("Kcal(100gr) |");
        base.setPadding(margineleft,0,0,0);
        base.setTextSize(10);
        base.setTextColor(getColor(R.color.nero));
        row.addView(base);
        base=new TextView(this);
        base.setText("Q.tà(gr) |");
        base.setTextSize(10);
        base.setTextColor(getColor(R.color.nero));
        row.addView(base);
        base=new TextView(this);
        base.setText("Kcal(tot) |");
        base.setTextSize(10);
        base.setTextColor(getColor(R.color.nero));
        row.addView(base);
        row.setBackgroundColor(getColor(R.color.colonne));
        // popolamento voci
        while (!Colazione.isAfterLast())
        {
            row = new TableRow(this);
            tl.addView(row);
            Cursor rs = mydb.getAlimento(Colazione.getInt(Colazione.getColumnIndex(DBHelper.VOCIPASTI_CMP_IDALIM)));
            rs.moveToFirst();
            IDAlimOggi[i]=Colazione.getInt(Colazione.getColumnIndex(DBHelper.VOCIPASTI_CMP_ID));
            TextView txt2=new TextView(this);
            txt2.setText(rs.getString(rs.getColumnIndex(DBHelper.ALIM_CMP_NOME)));
            txt2.setTextSize(12);
            txt2.setMaxWidth(390);
            txt2.setMaxHeight(45);
            row.addView(txt2);
            txt2=new TextView(this);
            txt2.setText(rs.getString(rs.getColumnIndex(DBHelper.ALIM_CMP_KCAL)));
            txt2.setPadding(margineleft,0,0,0);
            row.addView(txt2);
            txt2=new EditText(this);
            txt2.setId(View.generateViewId());
            IDeditqta[i]=txt2.getId();
            txt2.setText(Colazione.getString(Colazione.getColumnIndex(DBHelper.VOCIPASTI_CMP_QTA)));
            txt2.setTextSize(13);
            row.addView(txt2);
            txt2=new TextView(this);
            Double Totkacl=Colazione.getDouble(Colazione.getColumnIndex(DBHelper.VOCIPASTI_CMP_QTA))*rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_KCAL))/100;
            Double Totgrassi=Colazione.getDouble(Colazione.getColumnIndex(DBHelper.VOCIPASTI_CMP_QTA))*rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_GRASSI))/100;
            Double Totglucidi=Colazione.getDouble(Colazione.getColumnIndex(DBHelper.VOCIPASTI_CMP_QTA))*rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_GLUCIDI))/100;
            Double Totproteine=Colazione.getDouble(Colazione.getColumnIndex(DBHelper.VOCIPASTI_CMP_QTA))*rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_PROTEINE))/100;
            Double Totfibre=Colazione.getDouble(Colazione.getColumnIndex(DBHelper.VOCIPASTI_CMP_QTA))*rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_FIBRE))/100;
            txt2.setText(Totkacl.toString());
            ToaleKcalColaz=ToaleKcalColaz+Totkacl;ToaleGrassiColaz=ToaleGrassiColaz+Totgrassi;ToaleZuccheriColaz=ToaleZuccheriColaz+Totglucidi;
            ToaleProteineColaz=ToaleProteineColaz+Totproteine;ToaleFibreColaz=ToaleFibreColaz+Totfibre;
            row.addView(txt2);
            Colazione.moveToNext();
            btn2=new Button(this);
            btn2.setBackgroundResource(R.drawable.ic_mode_edit_black_24dp);
            btn2.setId(View.generateViewId());
            IDbuttonMOD[i]=btn2.getId();
            btn2.setTextSize(8);
            btn2.setMinHeight(0);
            btn2.setMinWidth(0);
            btn2.setMinimumHeight(10);
            btn2.setMinimumWidth(10);
            btn2.setHeight(0);
            btn2.setWidth(0);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer IDpulsante=view.getId();
                    Integer y=0; Boolean trovato=false;
                    while (y<numVociOggi && !trovato){
                        if (IDbuttonMOD[y].equals(IDpulsante)) {
                            EditText modq=(EditText) findViewById((int)IDeditqta[y]) ;
                            mydb.UpdateQtaPasto(IDAlimOggi[y],Double.parseDouble(modq.getText().toString()));
                            trovato=true;
                        }
                        y++;
                    }
                    if (trovato) riempipasto(id_To_Update,nomepasto);
                }
            });
            row.addView(btn2);
            btn2=new Button(this);
            btn2.setBackgroundResource(R.drawable.ic_clear_black_24dp);
            btn2.setId(View.generateViewId());
            IDbuttonDEL[i]=btn2.getId();
            btn2.setTextSize(8);
            btn2.setMinHeight(0);
            btn2.setMinWidth(0);
            btn2.setMinimumHeight(10);
            btn2.setMinimumWidth(10);
            btn2.setHeight(0);
            btn2.setWidth(0);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer IDpulsante=view.getId();
                    Integer y=0; Boolean trovato=false;
                    while (y<numVociOggi && !trovato){
                        if (IDbuttonDEL[y].equals(IDpulsante)) {mydb.deleteVocePasto(IDAlimOggi[y]);trovato=true;}
                        y++;
                    }
                    if (trovato) riempipasto(id_To_Update,nomepasto);
                }
            });
            row.addView(btn2);
            i=i+1;
        }
        // pulsante +
        /*row = new TableRow(this);
        tl.addView(row);
        Button btn=new Button(this);
        btn.setBackgroundResource(R.drawable.aggiungi);
        btn.setMaxHeight(10);
        btn.setMaxWidth(10);
        btn.setMinimumHeight(50);
        btn.setMinimumWidth(50);/*
        btn.setHeight(50);
        btn.setWidth(50);
        //ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
              //  ViewGroup.LayoutParams.WRAP_CONTENT);
        //btn.setLayoutParams(params);
        //btn.setGravity(Gravity.CENTER_HORIZONTAL);
        btn.setTextSize(8);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuova_voce(oggi,"COLAZIONE");
            }
        });
        row.addView(btn);*/
        // Totali Colazione
        TotaliParz=(TextView) findViewById(R.id.TotaliPasto);
        arrotonda= (int) (ToaleKcalColaz*100);
        ToaleKcalColaz=(double) (arrotonda/100);
        arrotonda= (int) (ToaleGrassiColaz*100);
        ToaleGrassiColaz=(double) (arrotonda/100);
        arrotonda= (int) (ToaleZuccheriColaz*100);
        ToaleZuccheriColaz=(double) (arrotonda/100);
        arrotonda= (int) (ToaleProteineColaz*100);
        ToaleProteineColaz=(double) (arrotonda/100);
        arrotonda= (int) (ToaleFibreColaz*100);
        ToaleFibreColaz=(double) (arrotonda/100);
        TotaliParz.setText("TOT PASTO Kcal="+ToaleKcalColaz.toString()+"   GR="+ToaleGrassiColaz.toString()+"  ZUC="+ToaleZuccheriColaz.toString()+
                "   PR="+ToaleProteineColaz.toString()+"   FIB="+ToaleFibreColaz.toString());


        ImageView maiale1=(ImageView)findViewById(R.id.maialino1);
        ImageView maiale2=(ImageView)findViewById(R.id.maialino2);
        ImageView maiale3=(ImageView)findViewById(R.id.maialino3);
        if (ToaleKcalColaz>1500){
            maiale1.setVisibility(View.VISIBLE);
        }
        else{
            maiale1.setVisibility(View.INVISIBLE);
        }
        if (ToaleKcalColaz>2000){
            maiale2.setVisibility(View.VISIBLE);
        }
        else{
            maiale2.setVisibility(View.INVISIBLE);
        }
        if (ToaleKcalColaz>2500){
            maiale3.setVisibility(View.VISIBLE);
        }
        else{
            maiale3.setVisibility(View.INVISIBLE);
        }
    }
    public void AggiungiPasto (View view){
        TextView nome=(TextView) findViewById(R.id.editNome);
        String nam=nome.getText().toString();
        Integer nuovopasto=0;
        if (!nam.equals("")) {
            nuovopasto= mydb.insertPasto(nam);
        }
        if (nuovopasto>0){
            Bundle dataBundle = new Bundle();
            dataBundle.putInt("id", nuovopasto);

            Intent intent = new Intent(getApplicationContext(), DisplayPasto.class);

            intent.putExtras(dataBundle);
            startActivity(intent);
        }
        finish();
    }
    public void salvapasto (View view){
        TextView nome=(TextView) findViewById(R.id.editNome);
        String nam=nome.getText().toString();
        if (!nam.equals("") && !nam.equals("NUOVO PASTO")) {
            mydb.UpdatePasto(id_To_Update,nam);
            if (chimichiama.equals("esporta")){
                Intent intent = new Intent(this, DisplayDietaGiorno.class);
                startActivity(intent);
            }
            else{

                Intent intent = new Intent(this, Pasti.class);
                startActivity(intent);
            }
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Nome pasto non valido!")

                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });

            AlertDialog d = builder.create();
            d.setTitle("ATTENZIONE!!!");
            d.show();
        }
    }
    public void annullapasto (View view){
        if (chimichiama.equals("esporta")){
            mydb.deletePasto(id_To_Update);

            Intent intent = new Intent(this, DisplayDietaGiorno.class);
            startActivity(intent);
        }
        else{
            finish();
        }
    }
}

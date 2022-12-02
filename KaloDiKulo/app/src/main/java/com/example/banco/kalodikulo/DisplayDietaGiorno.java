package com.example.banco.kalodikulo;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.view.KeyEvent.ACTION_UP;
import static com.example.banco.kalodikulo.MainActivity.giornodioggi;


public class DisplayDietaGiorno extends AppCompatActivity {

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

    TextView Giorno;
    DBHelper mydb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_dieta_giorno);
        getSupportActionBar().setHomeButtonEnabled(true);

        Giorno=(TextView) findViewById(R.id.giorno);
        mydb = new DBHelper(this);
        riempidieta(giornodioggi);

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
            case R.id.item1:Bundle dataBundle = new Bundle();
               /* dataBundle.putInt("id", 0);

                Intent intent = new Intent(getApplicationContext(),DisplayAlimento .class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public boolean nuova_voce(String giorno, String pasto) {

        Bundle dataBundle = new Bundle();
        dataBundle.putString("giorno", giorno);
        dataBundle.putString("pasto", pasto);

        Intent intent = new Intent(getApplicationContext(),NuovaVoceDieta.class);
        intent.putExtras(dataBundle);

        startActivity(intent);
        return true;
    }
    public void importapasto(String qualegiorno,String pasto){
        Bundle dataBundle = new Bundle();
        dataBundle.putString("giorno", qualegiorno);
        dataBundle.putString("pasto", pasto);

        Intent intent = new Intent(getApplicationContext(),ImportaPastoDieta.class);
        intent.putExtras(dataBundle);

        startActivity(intent);

    }
    public void esportapasto(String qualegiorno,String pasto){
        Integer nuovopasto;Integer IDalim;
        Double Quantita;
        Cursor pastoDAesp;
        nuovopasto= mydb.insertPasto("NUOVO PASTO");
        switch (pasto)
        {
            case "COLAZIONE":
                pastoDAesp=mydb.getAllColaz(Giorno.getText().toString());
                break;
            case "PRANZO":
                pastoDAesp=mydb.getAllPranzo(Giorno.getText().toString());
                break;
            case "CENA":
                pastoDAesp=mydb.getAllCena(Giorno.getText().toString());
                break;
            default: pastoDAesp=null;
        }
        pastoDAesp.moveToFirst();
        while (!pastoDAesp.isAfterLast()) {
            IDalim = pastoDAesp.getInt(pastoDAesp.getColumnIndex(DBHelper.DIETA_CMP_IDALIM));
            Quantita = pastoDAesp.getDouble(pastoDAesp.getColumnIndex(DBHelper.DIETA_CMP_QTA));
            mydb.insertVociPasto(nuovopasto, IDalim, Quantita, "", "");
            pastoDAesp.moveToNext();
        }
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("id", nuovopasto);
        dataBundle.putString("chimichiama", "esporta");

        Intent intent = new Intent(getApplicationContext(), DisplayPasto.class);

        intent.putExtras(dataBundle);
        startActivity(intent);
    }
    public void giornoavanti(View view){
        giornodioggi.roll(Calendar.DAY_OF_YEAR,1);
        riempidieta(giornodioggi);
    }
    public void giornoindietro(View view){
        giornodioggi.roll(Calendar.DAY_OF_YEAR,-1);
        riempidieta(giornodioggi);
    }
    public void riempidieta(final Calendar qualegiorno){
        TableRow row;
        TextView TotaliParz;
        Giorno=(TextView) findViewById(R.id.giorno);
        final String oggi=  DateFormat.getDateInstance(DateFormat.FULL, Locale.ITALY).format(qualegiorno.getTime());
        Giorno.setText(oggi);
        mydb = new DBHelper(this);
        Cursor PastiOdierni =mydb.getAllGiorno(Giorno.getText().toString());
        Button impPastoCol=(Button)findViewById(R.id.BtnImpPasto);
        Button impPastoPra=(Button)findViewById(R.id.BtnImpPastoPranzo);
        Button impPastoCen=(Button)findViewById(R.id.BtnImpPastoCena);
        Button espPastoCol=(Button)findViewById(R.id.BtnExpPasto);
        Button espPastoPra=(Button)findViewById(R.id.BtnExpPastoPranzo);
        Button espPastoCen=(Button)findViewById(R.id.BtnExpPastoCena);
        impPastoCol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                importapasto(oggi,"COLAZIONE");
            }
        });
        impPastoPra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                importapasto(oggi,"PRANZO");
            }
        });
        impPastoCen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                importapasto(oggi,"CENA");
            }
        });
        espPastoCol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                esportapasto(oggi,"COLAZIONE");
            }
        });
        espPastoPra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                esportapasto(oggi,"PRANZO");
            }
        });
        espPastoCen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                esportapasto(oggi,"CENA");
            }
        });
        numVociOggi=PastiOdierni.getCount();
        IDbuttonMOD= new Integer[numVociOggi];
        IDbuttonDEL= new Integer[numVociOggi];
        IDAlimOggi= new Integer[numVociOggi];
        IDeditqta= new Integer[numVociOggi];
        Button btn,btn2;
        Integer arrotonda;
        Integer i=0;
        // sezione Colazione
        TableLayout tl= (TableLayout)findViewById(R.id.tableColazione);
        btn= (Button) findViewById(R.id.BtnColazione);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuova_voce(oggi,"COLAZIONE");
            }
        });
        btn= (Button) findViewById(R.id.BtnPranzo);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuova_voce(oggi,"PRANZO");
            }
        });
        btn= (Button) findViewById(R.id.BtnCena);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuova_voce(oggi,"CENA");
            }
        });
        Integer numerorighe=tl.getChildCount();
        Double ToaleKcalColaz=0.0;Double ToaleGrassiColaz=0.0;Double ToaleZuccheriColaz=0.0;
        Double ToaleProteineColaz=0.0;Double ToaleFibreColaz=0.0;
        Double ToaleKcalPranzo=0.0;Double ToaleGrassiPranzo=0.0;Double ToaleZuccheriPranzo=0.0;
        Double ToaleProteinePranzo=0.0;Double ToaleFibrePranzo=0.0;
        Double ToaleKcalCena=0.0;Double ToaleGrassiCena=0.0;Double ToaleZuccheriCena=0.0;
        Double ToaleProteineCena=0.0;Double ToaleFibreCena=0.0;
        for(int y=0; y < numerorighe; y++) tl.removeViewAt(0);
        Cursor Colazione=mydb.getAllColaz(Giorno.getText().toString());
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
            Cursor rs = mydb.getAlimento(Colazione.getInt(Colazione.getColumnIndex(DBHelper.DIETA_CMP_IDALIM)));
            rs.moveToFirst();
            IDAlimOggi[i]=Colazione.getInt(Colazione.getColumnIndex(DBHelper.DIETA_CMP_ID));
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
            txt2.setText(Colazione.getString(Colazione.getColumnIndex(DBHelper.DIETA_CMP_QTA)));
            txt2.setTextSize(13);
            row.addView(txt2);
            txt2=new TextView(this);
            Double Totkacl=Colazione.getDouble(Colazione.getColumnIndex(DBHelper.DIETA_CMP_QTA))*rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_KCAL))/100;
            Double Totgrassi=Colazione.getDouble(Colazione.getColumnIndex(DBHelper.DIETA_CMP_QTA))*rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_GRASSI))/100;
            Double Totglucidi=Colazione.getDouble(Colazione.getColumnIndex(DBHelper.DIETA_CMP_QTA))*rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_GLUCIDI))/100;
            Double Totproteine=Colazione.getDouble(Colazione.getColumnIndex(DBHelper.DIETA_CMP_QTA))*rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_PROTEINE))/100;
            Double Totfibre=Colazione.getDouble(Colazione.getColumnIndex(DBHelper.DIETA_CMP_QTA))*rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_FIBRE))/100;
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
                            mydb.UpdateQtaDieta(IDAlimOggi[y],Double.parseDouble(modq.getText().toString()));
                            trovato=true;
                        }
                        y++;
                    }
                    if (trovato) riempidieta(qualegiorno);
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
                        if (IDbuttonDEL[y].equals(IDpulsante)) {mydb.deleteDieta(IDAlimOggi[y]);trovato=true;}
                        y++;
                    }
                    if (trovato) riempidieta(qualegiorno);
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
        TotaliParz=(TextView) findViewById(R.id.TotaliColazione);
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
        TotaliParz.setText("TOT COLAZIONE Kcal="+ToaleKcalColaz.toString()+"   GR="+ToaleGrassiColaz.toString()+"  ZUC="+ToaleZuccheriColaz.toString()+
                "   PR="+ToaleProteineColaz.toString()+"   FIB="+ToaleFibreColaz.toString());

        // sezione Pranzo
        tl= (TableLayout)findViewById(R.id.tablePranzo);
        numerorighe=tl.getChildCount();
        ToaleKcalPranzo=0.0;
        for(int y=0; y < numerorighe; y++) tl.removeViewAt(0);
        Cursor Pranzo=mydb.getAllPranzo(Giorno.getText().toString());
        Pranzo.moveToFirst();
        // Creo riga Intestazioni
        row = new TableRow(this);
        tl.addView(row);
        margineleft=15;
        if (Pranzo.getCount()==0) margineleft=70;
        base=new TextView(this);
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
        while (!Pranzo.isAfterLast())
        {
            row = new TableRow(this);
            tl.addView(row);
            Cursor rs = mydb.getAlimento(Pranzo.getInt(Pranzo.getColumnIndex(DBHelper.DIETA_CMP_IDALIM)));
            rs.moveToFirst();
            IDAlimOggi[i]=Pranzo.getInt(Pranzo.getColumnIndex(DBHelper.DIETA_CMP_ID));
            TextView txt2=new TextView(this);
            txt2.setText(rs.getString(rs.getColumnIndex(DBHelper.ALIM_CMP_NOME)));
            txt2.setTextSize(12);
            txt2.setMaxWidth(390);
            txt2.setMaxHeight(50);
            row.addView(txt2);
            txt2=new TextView(this);
            txt2.setText(rs.getString(rs.getColumnIndex(DBHelper.ALIM_CMP_KCAL)));
            txt2.setPadding(margineleft,0,0,0);
            row.addView(txt2);
            txt2=new EditText(this);
            txt2.setId(View.generateViewId());
            IDeditqta[i]=txt2.getId();
            txt2.setText(Pranzo.getString(Pranzo.getColumnIndex(DBHelper.DIETA_CMP_QTA)));
            txt2.setTextSize(13);
            row.addView(txt2);
            txt2=new TextView(this);
            Double Totkacl=Pranzo.getDouble(Pranzo.getColumnIndex(DBHelper.DIETA_CMP_QTA))*rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_KCAL))/100;
            Double Totgrassi=Pranzo.getDouble(Pranzo.getColumnIndex(DBHelper.DIETA_CMP_QTA))*rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_GRASSI))/100;
            Double Totglucidi=Pranzo.getDouble(Pranzo.getColumnIndex(DBHelper.DIETA_CMP_QTA))*rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_GLUCIDI))/100;
            Double Totproteine=Pranzo.getDouble(Pranzo.getColumnIndex(DBHelper.DIETA_CMP_QTA))*rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_PROTEINE))/100;
            Double Totfibre=Pranzo.getDouble(Pranzo.getColumnIndex(DBHelper.DIETA_CMP_QTA))*rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_FIBRE))/100;
            txt2.setText(Totkacl.toString());
            ToaleKcalPranzo=ToaleKcalPranzo+Totkacl;ToaleGrassiPranzo=ToaleGrassiPranzo+Totgrassi;ToaleZuccheriPranzo=ToaleZuccheriPranzo+Totglucidi;
            ToaleProteinePranzo=ToaleProteinePranzo+Totproteine;ToaleFibrePranzo=ToaleFibrePranzo+Totfibre;
            row.addView(txt2);
            Pranzo.moveToNext();
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
                            mydb.UpdateQtaDieta(IDAlimOggi[y],Double.parseDouble(modq.getText().toString()));
                            trovato=true;
                        }
                        y++;
                    }
                    if (trovato) riempidieta(qualegiorno);
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
                        if (IDbuttonDEL[y].equals(IDpulsante)) {mydb.deleteDieta(IDAlimOggi[y]);trovato=true;}
                        y++;
                    }
                    if (trovato) riempidieta(qualegiorno);
                }
            });
            row.addView(btn2);
            i=i+1;
        }
        // pulsante +

        // Totali Colazione
        TotaliParz=(TextView) findViewById(R.id.TotaliPranzo);
        arrotonda= (int) (ToaleKcalPranzo*100);
        ToaleKcalPranzo=(double) (arrotonda/100);
        arrotonda= (int) (ToaleGrassiPranzo*100);
        ToaleGrassiPranzo=(double) (arrotonda/100);
        arrotonda= (int) (ToaleZuccheriPranzo*100);
        ToaleZuccheriPranzo=(double) (arrotonda/100);
        arrotonda= (int) (ToaleProteinePranzo*100);
        ToaleProteinePranzo=(double) (arrotonda/100);
        arrotonda= (int) (ToaleFibrePranzo*100);
        ToaleFibrePranzo=(double) (arrotonda/100);
        TotaliParz.setText("TOT PRANZO  Kcal="+ToaleKcalPranzo.toString()+"   GR="+ToaleGrassiPranzo.toString()+"  ZUC="+ToaleZuccheriPranzo.toString()+
                "   PR="+ToaleProteinePranzo.toString()+"   FIB="+ToaleFibrePranzo.toString());
        // sezione cena
        tl= (TableLayout)findViewById(R.id.tableCena);
        numerorighe=tl.getChildCount();
        ToaleKcalCena=0.0;
        for(int y=0; y < numerorighe; y++) tl.removeViewAt(0);
        Cursor Cena=mydb.getAllCena(Giorno.getText().toString());
        Cena.moveToFirst();
        // Creo riga Intestazioni
        row = new TableRow(this);
        tl.addView(row);
        margineleft=15;
        if (Cena.getCount()==0) margineleft=70;
        base=new TextView(this);
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
        while (!Cena.isAfterLast())
        {
            row = new TableRow(this);
            tl.addView(row);
            Cursor rs = mydb.getAlimento(Cena.getInt(Cena.getColumnIndex(DBHelper.DIETA_CMP_IDALIM)));
            rs.moveToFirst();
            IDAlimOggi[i]=Cena.getInt(Cena.getColumnIndex(DBHelper.DIETA_CMP_ID));
            TextView txt2=new TextView(this);
            txt2.setText(rs.getString(rs.getColumnIndex(DBHelper.ALIM_CMP_NOME)));
            txt2.setTextSize(12);
            txt2.setMaxWidth(390);
            txt2.setMaxHeight(50);
            row.addView(txt2);
            txt2=new TextView(this);
            txt2.setText(rs.getString(rs.getColumnIndex(DBHelper.ALIM_CMP_KCAL)));
            txt2.setPadding(margineleft,0,0,0);
            row.addView(txt2);
            txt2=new EditText(this);
            txt2.setId(View.generateViewId());
            IDeditqta[i]=txt2.getId();
            txt2.setText(Cena.getString(Cena.getColumnIndex(DBHelper.DIETA_CMP_QTA)));
            txt2.setTextSize(13);
            row.addView(txt2);
            txt2=new TextView(this);
            Double Totkacl=Cena.getDouble(Cena.getColumnIndex(DBHelper.DIETA_CMP_QTA))*rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_KCAL))/100;
            Double Totgrassi=Cena.getDouble(Cena.getColumnIndex(DBHelper.DIETA_CMP_QTA))*rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_GRASSI))/100;
            Double Totglucidi=Cena.getDouble(Cena.getColumnIndex(DBHelper.DIETA_CMP_QTA))*rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_GLUCIDI))/100;
            Double Totproteine=Cena.getDouble(Cena.getColumnIndex(DBHelper.DIETA_CMP_QTA))*rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_PROTEINE))/100;
            Double Totfibre=Cena.getDouble(Cena.getColumnIndex(DBHelper.DIETA_CMP_QTA))*rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_FIBRE))/100;
            txt2.setText(Totkacl.toString());
            ToaleKcalCena=ToaleKcalCena+Totkacl;ToaleGrassiCena=ToaleGrassiCena+Totgrassi;ToaleZuccheriCena=ToaleZuccheriCena+Totglucidi;
            ToaleProteineCena=ToaleProteineCena+Totproteine;ToaleFibreCena=ToaleFibreCena+Totfibre;
            row.addView(txt2);
            Cena.moveToNext();
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
                            mydb.UpdateQtaDieta(IDAlimOggi[y],Double.parseDouble(modq.getText().toString()));
                            trovato=true;
                        }
                        y++;
                    }
                    if (trovato) riempidieta(qualegiorno);
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
                        if (IDbuttonDEL[y].equals(IDpulsante)) {mydb.deleteDieta(IDAlimOggi[y]);trovato=true;}
                        y++;
                    }
                    if (trovato) riempidieta(qualegiorno);
                }
            });
            row.addView(btn2);
            i=i+1;
        }
        // pulsante +

        // Totali Cena
        TotaliParz=(TextView) findViewById(R.id.TotaliCena);
        arrotonda= (int) (ToaleKcalCena*100);
        ToaleKcalCena=(double) (arrotonda/100);
        arrotonda= (int) (ToaleGrassiCena*100);
        ToaleGrassiCena=(double) (arrotonda/100);
        arrotonda= (int) (ToaleZuccheriCena*100);
        ToaleZuccheriCena=(double) (arrotonda/100);
        arrotonda= (int) (ToaleProteineCena*100);
        ToaleProteineCena=(double) (arrotonda/100);
        arrotonda= (int) (ToaleFibreCena*100);
        ToaleFibreCena=(double) (arrotonda/100);
        TotaliParz.setText("TOT CENA  Kcal="+ToaleKcalCena.toString()+"   GR="+ToaleGrassiCena.toString()+"  ZUC="+ToaleZuccheriCena.toString()+
                "   PR="+ToaleProteineCena.toString()+"   FIB="+ToaleFibreCena.toString());
        // Totali Giornalieri
        TotaliParz=(TextView) findViewById(R.id.TotaliGiorno);

        ToaleKcalCena=ToaleKcalCena+ToaleKcalColaz+ToaleKcalPranzo;
        arrotonda= (int) (ToaleKcalCena*100);
        ToaleKcalCena=(double) (arrotonda/100);
        ToaleGrassiCena=ToaleGrassiCena+ToaleGrassiColaz+ToaleGrassiPranzo;
        ToaleZuccheriCena=ToaleZuccheriCena+ToaleZuccheriColaz+ToaleZuccheriPranzo;
        arrotonda= (int) (ToaleGrassiCena*100);
        ToaleGrassiCena=(double) (arrotonda/100);
        arrotonda= (int) (ToaleZuccheriCena*100);
        ToaleZuccheriCena=(double) (arrotonda/100);
        ToaleProteineCena=ToaleProteineCena+ToaleProteineColaz+ToaleProteinePranzo;
        arrotonda= (int) (ToaleProteineCena*100);
        ToaleProteineCena=(double) (arrotonda/100);
        ToaleFibreCena=ToaleFibreCena+ToaleFibreColaz+ToaleFibrePranzo;
        arrotonda= (int) (ToaleFibreCena*100);
        ToaleFibreCena=(double) (arrotonda/100);
        TotaliParz.setText("TOT GIORNO  Kcal="+ToaleKcalCena.toString() +"  GR="+ToaleGrassiCena.toString()+"  ZUC="+ToaleZuccheriCena.toString()+
                "  PR="+ToaleProteineCena.toString()+"  FIB="+ToaleFibreCena.toString());


        ImageView maiale1=(ImageView)findViewById(R.id.maialino1);
        ImageView maiale2=(ImageView)findViewById(R.id.maialino2);
        ImageView maiale3=(ImageView)findViewById(R.id.maialino3);
        if (ToaleKcalCena>1500){
            maiale1.setVisibility(View.VISIBLE);
        }
        else{
            maiale1.setVisibility(View.INVISIBLE);
        }
        if (ToaleKcalCena>2000){
            maiale2.setVisibility(View.VISIBLE);
        }
        else{
            maiale2.setVisibility(View.INVISIBLE);
        }
        if (ToaleKcalCena>2500){
            maiale3.setVisibility(View.VISIBLE);
        }
        else{
            maiale3.setVisibility(View.INVISIBLE);
        }
    }

}

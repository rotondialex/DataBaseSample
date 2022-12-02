package com.example.banco.kalodikulo;

import android.net.Uri;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;

/**
 * Created by Banco on 16/02/2018.
 */

public class DisplayAlimento  extends AppCompatActivity {
    private DBHelper mydb ;

    TextView nome ;TextView classe;TextView kcal;
    TextView grassi; TextView proteine; TextView glucidi; TextView fibre;
    TextView sodio;TextView potassio;TextView calcio;TextView ferro;
    TextView vita;TextView vitb;TextView vitc;TextView vitd;TextView vite;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_alimento);
        getSupportActionBar().setHomeButtonEnabled(true);
        nome = (TextView) findViewById(R.id.editNome);classe = (TextView) findViewById(R.id.editClasse);kcal = (TextView) findViewById(R.id.editKcal);
        grassi = (TextView) findViewById(R.id.editGrassi);proteine = (TextView) findViewById(R.id.editProteine);glucidi = (TextView) findViewById(R.id.editGlucidi);
        fibre = (TextView) findViewById(R.id.editFibre);
        sodio = (TextView) findViewById(R.id.editSodio);potassio = (TextView) findViewById(R.id.editPotassio);calcio = (TextView) findViewById(R.id.editCalcio);
        ferro = (TextView) findViewById(R.id.editFerro);
        vita = (TextView) findViewById(R.id.editVitA);vitb = (TextView) findViewById(R.id.editVitB);
        vitc = (TextView) findViewById(R.id.editVitC);vitd = (TextView) findViewById(R.id.editVitD);vite = (TextView) findViewById(R.id.editVitE);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");

            if(Value>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getAlimento(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String nam = rs.getString(rs.getColumnIndex(DBHelper.ALIM_CMP_NOME));
                String clas = rs.getString(rs.getColumnIndex(DBHelper.ALIM_CMP_CLASSE));
                String kca = String.valueOf(rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_KCAL)));
                String gras = String.valueOf(rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_GRASSI)));
                String prot = String.valueOf(rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_PROTEINE)));
                String gluc = String.valueOf(rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_GLUCIDI)));
                String fibr = String.valueOf(rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_FIBRE)));
                String sod = String.valueOf(rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_SODIO)));
                String pot = String.valueOf(rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_POTASSIO)));
                String calc = String.valueOf(rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_CALCIO)));
                String fer = String.valueOf(rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_FERRO)));
                String via = String.valueOf(rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_VITA)));
                String vib = String.valueOf(rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_VITB)));
                String vic = String.valueOf(rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_VITC)));
                String vid = String.valueOf(rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_VITD)));
                String vie = String.valueOf(rs.getDouble(rs.getColumnIndex(DBHelper.ALIM_CMP_VITE)));


                if (!rs.isClosed())  {
                    rs.close();
                }
                Button b = (Button)findViewById(R.id.button1);
                b.setEnabled(false);

                nome.setText(nam);nome.setFocusable(false);nome.setClickable(false);
                classe.setText(clas);classe.setFocusable(false);classe.setClickable(false);
                kcal.setText(kca);kcal.setFocusable(false);kcal.setClickable(false);
                grassi.setText(gras);grassi.setFocusable(false);grassi.setClickable(false);
                proteine.setText(prot);proteine.setFocusable(false);proteine.setClickable(false);
                glucidi.setText(gluc);glucidi.setFocusable(false);glucidi.setClickable(false);
                fibre.setText(fibr);fibre.setFocusable(false);fibre.setClickable(false);

                sodio.setText(sod);sodio.setFocusable(false);sodio.setClickable(false);
                potassio.setText(pot);potassio.setFocusable(false);potassio.setClickable(false);
                calcio.setText(calc);calcio.setFocusable(false);calcio.setClickable(false);
                ferro.setText(fer);ferro.setFocusable(false);ferro.setClickable(false);

                vita.setText(via);vita.setFocusable(false);vita.setClickable(false);
                vitb.setText(vib);vitb.setFocusable(false);vitb.setClickable(false);
                vitc.setText(vic);vitc.setFocusable(false);vitc.setClickable(false);
                vitd.setText(vid);vitd.setFocusable(false);vitd.setClickable(false);
                vite.setText(vie);vite.setFocusable(false);vite.setClickable(false);


/*
                kcal.setLongClickable(true);
                kcal.setOnLongClickListener(new View.OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View v) {
                        // TODO Auto-generated method stub
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                DisplayAlimento.this);
                        //imposto il titolo ed il messaggio dell'alert dialog, prendendolo dalle Resources
                        alertDialogBuilder
                                .setTitle("Chiamare");
                        alertDialogBuilder
                                .setMessage("Vuoi chiamare questo contatto?");
                        //questo dialog non verrà annullato tappando sullo schermo al di fuori del dialog stesso
                        alertDialogBuilder.setCancelable(false);
                        //imposto il clicklistener del pulsante "yes" ed il suo testo sempre dalle Resources
                        alertDialogBuilder.setPositiveButton(R.string.yes,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            DialogInterface dialogInterface,
                                            int i) {

                                        Intent intenzione = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+kcal.getText().toString()));
                                        startActivity(intenzione);
                                    }
                                });
                        alertDialogBuilder.setNegativeButton(R.string.no,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            DialogInterface dialogInterface,
                                            int i) {
                                        //in caso di pressione del tasto "no" (negative) chiudo il dialog senza alcuna azione
                                        dialogInterface.cancel();
                                    }
                                });
                        //dopo averne dichiarato le proprietà creo l'istanza del dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        //lo mostro
                        alertDialog.show();

                        return true;
                    }
                });
*/
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(R.menu.display_alimento, menu);
            } else{
                //getMenuInflater().inflate(R.menu.menu_main,menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.Edit_Contact:
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.VISIBLE);
                b.setEnabled(true);
                b.setText("Modifica");

                nome.setEnabled(true);nome.setFocusableInTouchMode(true);nome.setClickable(true);
                classe.setEnabled(true);classe.setFocusableInTouchMode(true);classe.setClickable(true);
                kcal.setEnabled(true);kcal.setFocusableInTouchMode(true);kcal.setClickable(true);

                grassi.setEnabled(true);grassi.setFocusableInTouchMode(true);grassi.setClickable(true);
                proteine.setEnabled(true);proteine.setFocusableInTouchMode(true);proteine.setClickable(true);
                glucidi.setEnabled(true);glucidi.setFocusableInTouchMode(true);glucidi.setClickable(true);
                fibre.setEnabled(true);fibre.setFocusableInTouchMode(true);fibre.setClickable(true);

                sodio.setEnabled(true);sodio.setFocusableInTouchMode(true);sodio.setClickable(true);
                potassio.setEnabled(true);potassio.setFocusableInTouchMode(true);potassio.setClickable(true);
                calcio.setEnabled(true);calcio.setFocusableInTouchMode(true);calcio.setClickable(true);
                ferro.setEnabled(true);ferro.setFocusableInTouchMode(true);ferro.setClickable(true);

                vita.setEnabled(true);vita.setFocusableInTouchMode(true);vita.setClickable(true);
                vitb.setEnabled(true);vitb.setFocusableInTouchMode(true);vitb.setClickable(true);
                vitc.setEnabled(true);vitc.setFocusableInTouchMode(true);vitc.setClickable(true);
                vitd.setEnabled(true);vitd.setFocusableInTouchMode(true);vitd.setClickable(true);
                vite.setEnabled(true);vite.setFocusableInTouchMode(true);vite.setClickable(true);

                return true;
            case R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Elimina")
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteAlimento(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Cancellazione Avvenuta",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
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

    public void aggiungi(View view) {
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");
            if (kcal.getText().toString().equals("")) kcal.setText("0.0");
            if (grassi.getText().toString().equals("")) grassi.setText("0.0");
            if (proteine.getText().toString().equals("")) proteine.setText("0.0");
            if (glucidi.getText().toString().equals("")) glucidi.setText("0.0");
            if (fibre.getText().toString().equals("")) fibre.setText("0.0");
            if (sodio.getText().toString().equals("")) sodio.setText("0.0");
            if (potassio.getText().toString().equals("")) potassio.setText("0.0");
            if (calcio.getText().toString().equals("")) calcio.setText("0.0");
            if (ferro.getText().toString().equals("")) ferro.setText("0.0");
            if (vita.getText().toString().equals("")) vita.setText("0.0");
            if (vitb.getText().toString().equals("")) vitb.setText("0.0");
            if (vitc.getText().toString().equals("")) vitc.setText("0.0");
            if (vitd.getText().toString().equals("")) vitd.setText("0.0");
            if (vite.getText().toString().equals("")) vite.setText("0.0");
            if(Value>0){
                if(mydb.UpdateAlimento(id_To_Update,
                        nome.getText().toString(),classe.getText().toString(),Double.parseDouble(kcal.getText().toString()),
                        Double.parseDouble(grassi.getText().toString()),Double.parseDouble(proteine.getText().toString()),Double.parseDouble(glucidi.getText().toString()),Double.parseDouble(fibre.getText().toString()),
                        Double.parseDouble(sodio.getText().toString()),Double.parseDouble(potassio.getText().toString()),Double.parseDouble(calcio.getText().toString()),Double.parseDouble(ferro.getText().toString()),
                        Double.parseDouble(vita.getText().toString()),Double.parseDouble(vitb.getText().toString()),Double.parseDouble(vitc.getText().toString()),
                        Double.parseDouble(vitd.getText().toString()),Double.parseDouble(vite.getText().toString()),"0","0") ){
                    Toast.makeText(getApplicationContext(), "Aggiornato", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "Non Aggiornato", Toast.LENGTH_SHORT).show();
                }
            } else{
                if(mydb.insertAlimento(nome.getText().toString(),classe.getText().toString(),Double.parseDouble(kcal.getText().toString()),
                        Double.parseDouble(grassi.getText().toString()),Double.parseDouble(proteine.getText().toString()),Double.parseDouble(glucidi.getText().toString()),Double.parseDouble(fibre.getText().toString()),
                        Double.parseDouble(sodio.getText().toString()),Double.parseDouble(potassio.getText().toString()),Double.parseDouble(calcio.getText().toString()),Double.parseDouble(ferro.getText().toString()),
                        Double.parseDouble(vita.getText().toString()),Double.parseDouble(vitb.getText().toString()),Double.parseDouble(vitc.getText().toString()),
                        Double.parseDouble(vitd.getText().toString()),Double.parseDouble(vite.getText().toString()),"0","0")){
                    Toast.makeText(getApplicationContext(), "Aggiunto",
                            Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "Non Aggiunto",
                            Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        }
    }
}

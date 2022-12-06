package com.example.banco.databasesample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DisplayFormule extends AppCompatActivity {
    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb ;

    TextView name ;
    CheckBox inv;
    TextView ultimod;
    TextView costolitro;
    TextView Note;
    TextView email;
    TextView street;
    TextView place;
    GridView obj,obj2;
    public ArrayList<String> array_list;
    public ArrayList<Integer> array_ID;
    private ArrayAdapter arrayAdapter;
    int id_To_Update = 0;
    Integer Pos_Grid_componenti;
    Integer Pos_Grid_confezioni;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_formule);
        getSupportActionBar().setHomeButtonEnabled(true);
        name = (TextView) findViewById(R.id.editTextName);
        inv = (CheckBox) findViewById(R.id.inventario);
        ultimod = (TextView) findViewById(R.id.UltimaModifica);
        costolitro = (TextView) findViewById(R.id.CostoLitro);
        Note = (TextView) findViewById(R.id.editTestoNote);

        mydb = new DBHelper(this,"Hichem.db");

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("idfrm");

            if(Value>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getFormula(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String nam = rs.getString(rs.getColumnIndex(DBHelper.FORM_COL_NAME));
                String invent=rs.getString(rs.getColumnIndex(DBHelper.FORM_COL_INV));
                String ultmod = rs.getString(rs.getColumnIndex(DBHelper.FORM_COL_ULTIMAMOD));
                String note = rs.getString(rs.getColumnIndex(DBHelper.FORM_COL_CAMPO1));

                if (!rs.isClosed())  {
                    rs.close();
                }
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);

                name.setText((CharSequence)nam);
                name.setFocusable(false);
                name.setClickable(false);
                Note.setText((CharSequence)note);
                Note.setFocusable(false);
                Note.setClickable(false);

                inv.setChecked(Boolean.parseBoolean(invent));
                inv.setFocusable(false);
                inv.setClickable(false);
                ultimod.setText(ultmod);
                Double Costo=(double)((int)(mydb.CostoAlLitroFormula(id_To_Update)*100))/100;
                costolitro.setText(String.valueOf(Costo));
                array_list = mydb.getCompFormula(id_To_Update);


                ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);

                obj = (GridView)findViewById(R.id.componenti);
                obj.setOnTouchListener(new View.OnTouchListener() {
                    // Setting on Touch Listener for handling the touch inside ScrollView
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // Disallow the touch request for parent scroll on touch of child view
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        return false;
                    }
                });
                obj.setAdapter(arrayAdapter);


                registerForContextMenu(obj);
                array_list = mydb.getCONFdiFormula(id_To_Update);

                arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);

                obj = (GridView)findViewById(R.id.confezioni);
                obj.setOnTouchListener(new View.OnTouchListener() {
                    // Setting on Touch Listener for handling the touch inside ScrollView
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // Disallow the touch request for parent scroll on touch of child view
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        return false;
                    }
                });
                obj.setAdapter(arrayAdapter);
                registerForContextMenu(obj);
                obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        // TODO Auto-generated method stub
                        final ArrayList<Integer> arrayIDmpr = mydb.getIDCONFdiFormula(id_To_Update);
                        Pos_Grid_confezioni=(arg2/2)*2;
                        Intent i = new Intent(getApplicationContext(), ModifPercent.class);
                        i.putExtra("IdFrm",id_To_Update);
                        Integer xy=arrayIDmpr.get(Pos_Grid_confezioni/2);
                        i.putExtra("IdComp",arrayIDmpr.get(Pos_Grid_confezioni/2));
                        i.putExtra("qualeattivita","ModificaQuant");
                        startActivityForResult(i, 1);
                    }
                });
                /*obj.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
                    @Override
                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        //Creazione dell'alert dialog
                        final Integer index=arg2;
                        GridView confezioni= (GridView) findViewById(R.id.confezioni);
                        final ArrayList<Integer> arrayIDmpr = mydb.getIDCONFdiFormula(id_To_Update);
                        final Integer idcomponente=arrayIDmpr.get(index/2);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                DisplayFormule.this);
                        //imposto il titolo ed il messaggio dell'alert dialog, prendendolo dalle Resources
                        alertDialogBuilder
                                .setTitle("Elimina");
                        alertDialogBuilder
                                .setMessage(confezioni.getItemAtPosition(arg2).toString()+"\n\r" +"Vuoi cancellare questa confezione?");
                        //questo dialog non verrà annullato tappando sullo schermo al di fuori del dialog stesso
                        alertDialogBuilder.setCancelable(false);
                        //imposto il clicklistener del pulsante "yes" ed il suo testo sempre dalle Resources
                        alertDialogBuilder.setPositiveButton(R.string.yes,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            DialogInterface dialogInterface,
                                            int i) {
                                        //rimuovo l'oggetto dalla posizione "position" (quella attualmente selezionata)
                                        Bundle Componenti= getIntent().getExtras();// TODO
                                        String inventSIno=String.valueOf(inv.isChecked());
                                        mydb.deleteConfdiFRM(id_To_Update,idcomponente);
                                        mydb.updateFormula(id_To_Update,name.getText().toString(),inventSIno,Componenti);
                                        Toast.makeText(getApplicationContext(), "Cancellazione Avvenuta",
                                                Toast.LENGTH_SHORT).show();
                                        Bundle dataBundle = new Bundle();
                                        dataBundle.putInt("idfrm", id_To_Update);
                                        Intent intent = new Intent(getApplicationContext(),DisplayFormule.class);
                                        intent.putExtras(dataBundle);
                                        startActivity(intent);

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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        GridView componenti= (GridView) findViewById(R.id.componenti);
        GridView confezioni = (GridView) findViewById(R.id.confezioni);
        if (v==componenti){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            Pos_Grid_componenti=(info.position/2)*2;
            menu.setHeaderTitle(componenti.getItemAtPosition(Pos_Grid_componenti).toString()+" = "+componenti.getItemAtPosition(Pos_Grid_componenti+1).toString());
            menu.add(0, v.getId(), 0, "Modifica Percentuale");
            menu.add(0, v.getId(), 0, "Elimina Componente");
        }
        if (v==confezioni){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            Pos_Grid_confezioni=(info.position/2)*2;
            menu.setHeaderTitle(confezioni.getItemAtPosition(Pos_Grid_confezioni).toString()+" Pz "+confezioni.getItemAtPosition(Pos_Grid_confezioni+1).toString());
            menu.add(0, v.getId(), 0, "Visualizza Costo");
            menu.add(0, v.getId(), 0, "Modifica Quantità");
            menu.add(0, v.getId(), 0, "Elimina Confezione");
        }

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String Titolo=item.getTitle().toString();

        if(item.getTitle()=="Modifica Percentuale"){
            final ArrayList<Integer> arrayIDmpr = mydb.getIDCompFormula(id_To_Update);
            Intent i = new Intent(this, ModifPercent.class);
            i.putExtra("IdFrm",id_To_Update);
            i.putExtra("IdComp",arrayIDmpr.get(Pos_Grid_componenti/2));
            i.putExtra("qualeattivita","Modifica");
            startActivityForResult(i, 1);
            return true;
        }
        else if(item.getTitle()=="Visualizza Costo") {
            String testo="";
            Double CostoForm=(double)((int)(mydb.CostoAlLitroFormula(id_To_Update)*100))/100;
            final ArrayList<Integer> arrayIDmpr = mydb.getIDCONFdiFormula(id_To_Update);
            final Integer idcomponente=arrayIDmpr.get(Pos_Grid_confezioni/2);
            Cursor rs = mydb.getConfezione(idcomponente);
            rs.moveToFirst();
            String nam = rs.getString(rs.getColumnIndex(DBHelper.CONF_COL_NAME));
            String pez=rs.getString(rs.getColumnIndex(DBHelper.CONF_COL_QCT));
            String ltconf = rs.getString(rs.getColumnIndex(DBHelper.CONF_COL_QPLT));
            Double Costo=(double)((int)(mydb.CostoAlLitroConfezione(idcomponente,Double.parseDouble(ltconf))*100))/100;
            Costo=Costo+CostoForm;
            Costo=(double)((int)(Costo*100))/100;
            Double costoPz=Costo*Double.parseDouble(ltconf)/Double.parseDouble(pez);
            costoPz=(double)((int)(costoPz*100))/100;
            Double costoCF=Costo*Double.parseDouble(ltconf);
            testo="  Costo al Litro "+String.valueOf(Costo)+"\n";
            testo=testo+"  Costo al Pezzo "+String.valueOf(costoPz)+"\n";
            testo=testo+"  Costo alla Confezione "+String.valueOf(costoCF)+"\n";
            Intent intenzione = new Intent(this, CostoConfezione.class);
            intenzione.putExtra("Titolo", name.getText().toString() + " "+nam);
            intenzione.putExtra("Testo", testo);
            startActivity(intenzione);
        }
        else if(item.getTitle()=="Elimina Componente"){
            final ArrayList<Integer> arrayIDmpr = mydb.getIDCompFormula(id_To_Update);
            final Integer idcomponente=arrayIDmpr.get(Pos_Grid_componenti/2);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    DisplayFormule.this);
            //imposto il titolo ed il messaggio dell'alert dialog, prendendolo dalle Resources
            alertDialogBuilder
                    .setTitle("Elimina");
            alertDialogBuilder
                    .setMessage("Vuoi cancellare questo componente?");
            //questo dialog non verrà annullato tappando sullo schermo al di fuori del dialog stesso
            alertDialogBuilder.setCancelable(false);
            //imposto il clicklistener del pulsante "yes" ed il suo testo sempre dalle Resources
            alertDialogBuilder.setPositiveButton(R.string.yes,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(
                                DialogInterface dialogInterface,
                                int i) {
                            //rimuovo l'oggetto dalla posizione "position" (quella attualmente selezionata)
                            Bundle Componenti= getIntent().getExtras();// TODO
                            String inventSIno=String.valueOf(inv.isChecked());
                            mydb.deleteComponente(id_To_Update,idcomponente);
                            mydb.updateFormula(id_To_Update,name.getText().toString(),inventSIno,Componenti,Note.getText().toString());
                            Toast.makeText(getApplicationContext(), "Cancellazione Avvenuta",
                                    Toast.LENGTH_SHORT).show();
                            Bundle dataBundle = new Bundle();
                            dataBundle.putInt("idfrm", id_To_Update);
                            Intent intent = new Intent(getApplicationContext(),DisplayFormule.class);
                            intent.putExtras(dataBundle);
                            startActivity(intent);

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
        }
        else if(item.getTitle()=="Modifica Quantità"){
            final ArrayList<Integer> arrayIDmpr = mydb.getIDCONFdiFormula(id_To_Update);
            Intent i = new Intent(this, ModifPercent.class);
            i.putExtra("IdFrm",id_To_Update);
            Integer xy=arrayIDmpr.get(Pos_Grid_confezioni/2);
            i.putExtra("IdComp",arrayIDmpr.get(Pos_Grid_confezioni/2));
            i.putExtra("qualeattivita","ModificaQuant");
            startActivityForResult(i, 1);
            return true;
        }
        else if(item.getTitle()=="Elimina Confezione"){
            final ArrayList<Integer> arrayIDmpr = mydb.getIDCONFdiFormula(id_To_Update);
            final Integer idcomponente=arrayIDmpr.get(Pos_Grid_confezioni/2);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    DisplayFormule.this);
            //imposto il titolo ed il messaggio dell'alert dialog, prendendolo dalle Resources
            alertDialogBuilder
                    .setTitle("Elimina");
            alertDialogBuilder
                    .setMessage("Vuoi cancellare questa confezione?");
            //questo dialog non verrà annullato tappando sullo schermo al di fuori del dialog stesso
            alertDialogBuilder.setCancelable(false);
            //imposto il clicklistener del pulsante "yes" ed il suo testo sempre dalle Resources
            alertDialogBuilder.setPositiveButton(R.string.yes,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(
                                DialogInterface dialogInterface,
                                int i) {
                            //rimuovo l'oggetto dalla posizione "position" (quella attualmente selezionata)
                            Bundle Componenti= getIntent().getExtras();// TODO
                            String inventSIno=String.valueOf(inv.isChecked());
                            mydb.deleteConfdiFRM(id_To_Update,idcomponente);
                            mydb.updateFormula(id_To_Update,name.getText().toString(),inventSIno,Componenti,Note.getText().toString());
                            Toast.makeText(getApplicationContext(), "Cancellazione Avvenuta",
                                    Toast.LENGTH_SHORT).show();
                            Bundle dataBundle = new Bundle();
                            dataBundle.putInt("idfrm", id_To_Update);
                            Intent intent = new Intent(getApplicationContext(),DisplayFormule.class);
                            intent.putExtras(dataBundle);
                            startActivity(intent);

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
        }
        else {return false;}
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            int Value = extras.getInt("idfrm");
            if(Value>0){
                getMenuInflater().inflate(R.menu.display_formule, menu);
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
                b.setText("Modifica");
                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);
                Note.setEnabled(true);
                Note.setFocusableInTouchMode(true);
                Note.setClickable(true);

                inv.setEnabled(true);
                inv.setFocusableInTouchMode(true);
                inv.setClickable(true);

                Calendar today= Calendar.getInstance();
                String oggi= DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY).format(today.getTime());
                ultimod.setText(oggi);

                return true;
            case R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteFormula(id_To_Update);
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

            case R.id.Aggiungi_Comp:
                Intent i = new Intent(this, MprPercent.class);
                i.putExtra("IdFrm",id_To_Update);
                i.putExtra("qualeattivita","Aggiungi");
                startActivityForResult(i, 1);
                return true;
            case R.id.Aggiungi_Conf:
                Intent j = new Intent(this, ConfDiFormule.class);
                j.putExtra("IdFrm",id_To_Update);
                j.putExtra("qualeattivita","CONFdiFRM");
                startActivityForResult(j, 1);
                return true;
            case R.id.Produci:
                Intent y = new Intent(this, Quantita.class);
                y.putExtra("qualeattivita","Produzione");
                startActivityForResult(y, 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        Bundle Componenti= getIntent().getExtras();// TODO
        if(extras !=null) {
            int Value = extras.getInt("idfrm");
            String inventSIno=String.valueOf(inv.isChecked());
            if(Value>0){
                if(mydb.updateFormula(id_To_Update,name.getText().toString(),inventSIno,Componenti,Note.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Aggiornato", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "Non Aggiornato", Toast.LENGTH_SHORT).show();
                }
            } else{
                id_To_Update=mydb.insertFormula(name.getText().toString(),inventSIno,Componenti,Note.getText().toString());
                    Toast.makeText(getApplicationContext(), "Aggiunto",
                            Toast.LENGTH_SHORT).show();

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("idfrm", id_To_Update);

                Intent intent = new Intent(getApplicationContext(),DisplayFormule.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){

                String chimichiama=data.getStringExtra("qualeattivita");
                Double quanto=data.getDoubleExtra("result",0);
                ArrayList<String> array_list = mydb.getCompFormula(id_To_Update);
                String [] Perordinare=array_list.toArray(new String[0]);
                ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_list);
                ArrayList<String> array_list2 = mydb.getCONFdiFormula(id_To_Update);
                ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_list2);
                if (chimichiama.equals("Aggiungi") || chimichiama.equals("Modifica")|| chimichiama.equals("ModificaQuant")) {
                    obj = (GridView) findViewById(R.id.componenti);
                    obj.setAdapter(arrayAdapter);
                    obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                            // TODO Auto-generated method stub
                            //int id_To_Search = arg2;
                            //id_To_Search=array_ID.get(id_To_Search);
                            // Bundle dataBundle = new Bundle();
                            //dataBundle.putInt("idfrm", id_To_Search);

                            //Intent intent = new Intent(getApplicationContext(),DisplayFormule.class);

                            //intent.putExtras(dataBundle);
                            //startActivity(intent);
                        }
                    });
                    obj2 = (GridView) findViewById(R.id.confezioni);
                    obj2.setAdapter(arrayAdapter2);

                }
                if (chimichiama.equals("CONFdiFRM")) {


                    obj2 = (GridView) findViewById(R.id.confezioni);
                    obj2.setAdapter(arrayAdapter2);
                    obj2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                            // TODO Auto-generated method stub
                            //int id_To_Search = arg2;
                            //id_To_Search=array_ID.get(id_To_Search);
                            // Bundle dataBundle = new Bundle();
                            //dataBundle.putInt("idfrm", id_To_Search);

                            //Intent intent = new Intent(getApplicationContext(),DisplayFormule.class);

                            //intent.putExtras(dataBundle);
                            //startActivity(intent);
                        }
                    });
                }
                if (chimichiama.equals("Produzione"))  {


                        Integer i=0;
                        String testo="\n\r", titolo="";
                        Integer valore=0,cm;
                        Double acqua=(double) quanto*100;
                        while (i<=Perordinare.length-2){
                            valore=(int)(Double.parseDouble(Perordinare[i+1])*quanto*100);
                            testo=testo+Perordinare[i]+" => "+(double)valore/100+ " Kg\n\r";
                            acqua=acqua-(double)valore/100;
                            i=i+2;
                        }
                    acqua=(double)((int)(acqua*100))/100;
                    testo = testo + "ACQUA " + acqua + " Kg\n\r";
                    if (acqua>220) {
                        cm = (int) ((acqua - 200) / 17.5 * 100);
                        testo = testo + "cm in vasca " + (double) cm / 100 + "\n\r";
                    }
                    Intent intenzione = new Intent(this, Produzione.class);
                    intenzione.putExtra("Titolo", name.getText().toString()+" "+quanto+" q.li");
                    intenzione.putExtra("Testo", testo);
                    startActivity(intenzione);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}